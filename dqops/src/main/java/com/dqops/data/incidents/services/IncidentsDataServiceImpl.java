/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.data.incidents.services;

import com.dqops.core.configuration.DqoIncidentsConfigurationProperties;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.services.CheckResultsDataService;
import com.dqops.data.checkresults.models.CheckResultEntryModel;
import com.dqops.data.checkresults.models.IncidentHistogramFilterParameters;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.checkresults.models.CheckResultListFilterParameters;
import com.dqops.data.checkresults.models.IncidentIssueHistogramModel;
import com.dqops.data.incidents.models.IncidentListFilterParameters;
import com.dqops.data.incidents.models.IncidentModel;
import com.dqops.rest.models.common.SortDirection;
import com.dqops.data.incidents.models.IncidentsPerConnectionModel;
import com.dqops.data.incidents.snapshot.IncidentsSnapshot;
import com.dqops.data.incidents.snapshot.IncidentsSnapshotFactory;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.data.storage.ParquetPartitionId;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Data quality incident management service. Supports reading incidents from parquet tables.
 */
@Service
public class IncidentsDataServiceImpl implements IncidentsDataService {
    private IncidentsSnapshotFactory incidentsSnapshotFactory;
    private CheckResultsDataService checkResultsDataService;
    private UserHomeContextFactory userHomeContextFactory;
    private DqoIncidentsConfigurationProperties dqoIncidentsConfigurationProperties;

    /**
     * Creates a new incident data service, given all required dependencies.
     * @param incidentsSnapshotFactory Incident snapshot factory.
     * @param checkResultsDataService Data quality check results data service, used to load results (matching issues).
     * @param userHomeContextFactory User home context factory, used to load a list of connections.
     * @param dqoIncidentsConfigurationProperties DQOps incidents configuration parameters.
     */
    @Autowired
    public IncidentsDataServiceImpl(IncidentsSnapshotFactory incidentsSnapshotFactory,
                                    CheckResultsDataService checkResultsDataService,
                                    UserHomeContextFactory userHomeContextFactory,
                                    DqoIncidentsConfigurationProperties dqoIncidentsConfigurationProperties) {
        this.incidentsSnapshotFactory = incidentsSnapshotFactory;
        this.checkResultsDataService = checkResultsDataService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoIncidentsConfigurationProperties = dqoIncidentsConfigurationProperties;
    }

    /**
     * Loads recent incidents on a connection.
     * @param connectionName Connection name.
     * @param filterParameters Incident filter parameters.
     * @param userDomainIdentity Calling user identity with the data domain.
     * @return Collection of recent incidents.
     */
    @Override
    public Collection<IncidentModel> loadRecentIncidentsOnConnection(
            String connectionName, IncidentListFilterParameters filterParameters, UserDomainIdentity userDomainIdentity) {
        IncidentsSnapshot incidentsSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName, userDomainIdentity);
        LocalDate endDate = Instant.now().atOffset(ZoneOffset.UTC).toLocalDate();
        LocalDate startDate = filterParameters.getRecentMonths() > 1 ?
                endDate.minusMonths(filterParameters.getRecentMonths() - 1) : endDate;
        if (!incidentsSnapshot.ensureMonthsAreLoaded(startDate, endDate)) {
            return new ArrayList<>(); // no results
        }

        String filter = filterParameters.getFilter();
        if (!Strings.isNullOrEmpty(filter) && filter.indexOf('*') < 0) {
            filter = "*" + filter + "*";
        }

        ArrayList<IncidentModel> incidentModels = new ArrayList<>();
        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = incidentsSnapshot.getLoadedMonthlyPartitions();
        for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> partitionEntry : loadedMonthlyPartitions.entrySet()) {
            Table partitionTable = partitionEntry.getValue().getData();
            if (partitionTable == null) {
                // empty partition
                continue;
            }

            TextColumn incidentIdColumn = partitionTable.textColumn(IncidentsColumnNames.ID_COLUMN_NAME);
            TextColumn schemaColumn = partitionTable.textColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME);
            TextColumn tableColumn = partitionTable.textColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME);
            IntColumn tablePriorityColumn = partitionTable.intColumn(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME);
            LongColumn incidentHashColumn = partitionTable.longColumn(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME);
            InstantColumn firstSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);
            InstantColumn lastSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME);
            InstantColumn incidentUntilColumn = partitionTable.instantColumn(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME);
            TextColumn dataStreamNameColumn = partitionTable.textColumn(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
            TextColumn qualityDimensionColumn = partitionTable.textColumn(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
            TextColumn checkCategoryColumn = partitionTable.textColumn(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            TextColumn checkTypeColumn = partitionTable.textColumn(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME);
            TextColumn checkNameColumn = partitionTable.textColumn(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME);
            IntColumn highestSeverityColumn = partitionTable.intColumn(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME);
            IntColumn minSeverityColumn = partitionTable.containsColumn(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME) ?
                    partitionTable.intColumn(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME) : null;
            IntColumn failedChecksCountColumn = partitionTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME);
            TextColumn issueUrlColumn = partitionTable.textColumn(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME);
            TextColumn statusColumn = partitionTable.textColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);

            int partitionYear = partitionEntry.getKey().getMonth().getYear();
            int partitionMonth = partitionEntry.getKey().getMonth().getMonthValue();

            int rowCount = partitionTable.rowCount();
            for (int rowIndex = rowCount - 1; rowIndex >= 0; rowIndex--) {
                String status = statusColumn.get(rowIndex);
                IncidentStatus incidentStatus = IncidentStatus.valueOf(status);

                if (!filterParameters.isIncidentStatusEnabled(incidentStatus)) {
                    continue; // skipping
                }

                IncidentModel incidentModel = new IncidentModel();
                incidentModel.setIncidentId(incidentIdColumn.get(rowIndex));
                incidentModel.setYear(partitionYear);
                incidentModel.setMonth(partitionMonth);
                incidentModel.setConnection(connectionName);
                incidentModel.setSchema(schemaColumn.get(rowIndex));
                incidentModel.setTable(tableColumn.get(rowIndex));
                if (!tablePriorityColumn.isMissing(rowIndex)) {
                    incidentModel.setTablePriority(tablePriorityColumn.get(rowIndex));
                }
                incidentModel.setIncidentHash(incidentHashColumn.get(rowIndex));
                Instant firstSeen = firstSeenColumn.get(rowIndex);
                incidentModel.setFirstSeen(firstSeen);
                incidentModel.setLastSeen(lastSeenColumn.get(rowIndex));
                incidentModel.setIncidentUntil(incidentUntilColumn.get(rowIndex));
                if (!dataStreamNameColumn.isMissing(rowIndex)) {
                    incidentModel.setDataGroup(dataStreamNameColumn.get(rowIndex));
                }
                if (!qualityDimensionColumn.isMissing(rowIndex)) {
                    incidentModel.setQualityDimension(qualityDimensionColumn.get(rowIndex));
                }
                if (!checkCategoryColumn.isMissing(rowIndex)) {
                    incidentModel.setCheckCategory(checkCategoryColumn.get(rowIndex));
                }
                if (!checkTypeColumn.isMissing(rowIndex)) {
                    incidentModel.setCheckType(checkTypeColumn.get(rowIndex));
                }
                if (!checkNameColumn.isMissing(rowIndex)) {
                    incidentModel.setCheckName(checkNameColumn.get(rowIndex));
                }
                if (!issueUrlColumn.isMissing(rowIndex)) {
                    incidentModel.setIssueUrl(issueUrlColumn.get(rowIndex));
                }
                incidentModel.setHighestSeverity(highestSeverityColumn.get(rowIndex));
                if (minSeverityColumn != null && !minSeverityColumn.isMissing(rowIndex)) {
                    incidentModel.setMinimumSeverity(minSeverityColumn.get(rowIndex));
                }
                incidentModel.setFailedChecksCount(failedChecksCountColumn.get(rowIndex));
                incidentModel.setStatus(incidentStatus);

                if (!Strings.isNullOrEmpty(filter) &&
                     !incidentModel.matchesFilter(filter)) {
                    continue;
                }

                incidentModels.add(incidentModel);
            }
        }

        Comparator<IncidentModel> sortComparator = IncidentModel.makeSortComparator(filterParameters.getOrder());
        if (filterParameters.getSortDirection() == SortDirection.asc) {
            incidentModels.sort(sortComparator);
        }
        else {
            incidentModels.sort(sortComparator.reversed());
        }

        int startRowIndexInPage = (filterParameters.getPage() - 1) * filterParameters.getLimit();
        int untilRowIndexInPage = filterParameters.getPage() * filterParameters.getLimit();

        if (startRowIndexInPage >= incidentModels.size()) {
            return new ArrayList<>(); // no results
        }

        List<IncidentModel> pageResults = incidentModels.subList(startRowIndexInPage, Math.min(untilRowIndexInPage, incidentModels.size()));
        return pageResults;
    }

    /**
     * Loads one incident.
     *
     * @param connectionName Connection name on which the incident was raised.
     * @param year           Year when the incident was first seen.
     * @param month          Month of year when the incident was first seen.
     * @param incidentId     Incident id.
     * @param userDomainIdentity Calling user identity with the data domain.
     * @return Incident model when the incident was found or null when the incident is not found.
     */
    @Override
    public IncidentModel loadIncident(String connectionName, int year, int month, String incidentId, UserDomainIdentity userDomainIdentity) {
        IncidentsSnapshot incidentsSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName, userDomainIdentity);
        LocalDate incidentMonth = LocalDate.of(year, month, 1);
        LoadedMonthlyPartition monthPartition = incidentsSnapshot.getMonthPartition(incidentMonth, true);
        if (monthPartition == null || monthPartition.getData() == null || monthPartition.getData().rowCount() == 0) {
            return null; // no data
        }

        Table incidentMonthData = monthPartition.getData();
        TextColumn incidentIdColumn = incidentMonthData.textColumn(IncidentsColumnNames.ID_COLUMN_NAME);
        Selection incidentIdSelection = incidentIdColumn.isEqualTo(incidentId);
        if (incidentIdSelection.isEmpty()) {
            return null;
        }

        int rowIndex = incidentIdSelection.get(0);
        IncidentModel incidentModel = IncidentModel.fromIncidentRow(incidentMonthData.row(rowIndex), connectionName);
        return incidentModel;
    }

    /**
     * Loads all failed check results covered by a given incident.
     * @param connectionName   Connection name where the incident happened.
     * @param year             Year when the incident was first seen.
     * @param month            Month of year when the incident was first seen.
     * @param incidentId       The incident id.
     * @param filterParameters List filter parameters.
     * @param userDomainIdentity Calling user identity with the data domain.
     * @return Array of check results for the incident.
     */
    @Override
    public CheckResultEntryModel[] loadCheckResultsForIncident(String connectionName,
                                                               int year,
                                                               int month,
                                                               String incidentId,
                                                               CheckResultListFilterParameters filterParameters,
                                                               UserDomainIdentity userDomainIdentity) {
        IncidentModel incidentModel = this.loadIncident(connectionName, year, month, incidentId, userDomainIdentity);
        if (incidentModel == null) {
            return null;
        }

        CheckResultEntryModel[] failedChecks = this.checkResultsDataService.loadCheckResultsRelatedToIncident(
                connectionName,
                new PhysicalTableName(incidentModel.getSchema(), incidentModel.getTable()),
                incidentModel.getIncidentHash(),
                incidentModel.getFirstSeen(),
                incidentModel.getIncidentUntil(),
                incidentModel.getMinimumSeverity(),
                filterParameters,
                userDomainIdentity);

        return failedChecks;
    }

    /**
     * Builds a histogram of data quality issue occurrences per day.
     *
     * @param connectionName   Connection name where the incident happened.
     * @param year             Year when the incident was first seen.
     * @param month            Month of year when the incident was first seen.
     * @param incidentId       The incident id.
     * @param filterParameters Filter to limit the issues included in the histogram.
     * @param userDomainIdentity Calling user identity with the data domain.
     * @return Daily histogram of days when a data quality issue failed.
     */
    @Override
    public IncidentIssueHistogramModel buildDailyIssuesHistogramForIncident(String connectionName,
                                                                            int year,
                                                                            int month,
                                                                            String incidentId,
                                                                            IncidentHistogramFilterParameters filterParameters,
                                                                            UserDomainIdentity userDomainIdentity) {
        IncidentModel incidentModel = this.loadIncident(connectionName, year, month, incidentId, userDomainIdentity);
        if (incidentModel == null) {
            return null;
        }

        IncidentIssueHistogramModel histogramModel = this.checkResultsDataService.buildDailyIssuesHistogramForIncident(connectionName,
                new PhysicalTableName(incidentModel.getSchema(), incidentModel.getTable()),
                incidentModel.getIncidentHash(),
                incidentModel.getFirstSeen(),
                incidentModel.getIncidentUntil(),
                incidentModel.getMinimumSeverity(),
                filterParameters,
                userDomainIdentity);

        return histogramModel;
    }

    /**
     * Returns a list of all connections, also counting the number of recent open incidents.
     *
     * @param userDomainIdentity Calling user identity with the data domain.
     *
     * @return Collection of connection names, with a count of open incidents.
     */
    @Override
    public Collection<IncidentsPerConnectionModel> findConnectionIncidentStats(UserDomainIdentity userDomainIdentity) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity);
        ConnectionList connectionList = userHomeContext.getUserHome().getConnections();
        List<ConnectionWrapper> connectionWrappers = connectionList.toList();

        List<IncidentsPerConnectionModel> resultList = connectionWrappers.stream()
                .map(connectionWrapper -> connectionWrapper.getName())
                .sorted()
                .map(connectionName -> loadConnectionStats(connectionName, userDomainIdentity))
                .collect(Collectors.toList());

        return resultList;
    }

    /**
     * Creates a connection incident statistics model, counting the number of recent open incidents.
     * @param connectionName Connection name.
     * @param userDomainIdentity Calling user identity with the data domain.
     * @return Connection incident stats model.
     */
    public IncidentsPerConnectionModel loadConnectionStats(String connectionName, UserDomainIdentity userDomainIdentity) {
        IncidentsPerConnectionModel model = new IncidentsPerConnectionModel();
        model.setConnection(connectionName);

        IncidentsSnapshot incidentsSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName, userDomainIdentity);
        Instant now = Instant.now();
        Instant since = now.minus(this.dqoIncidentsConfigurationProperties.getCountOpenIncidentsDays(), ChronoUnit.DAYS);
        LocalDate dateUntil = now.atOffset(ZoneOffset.UTC).toLocalDate();
        LocalDate dateSince = since.atOffset(ZoneOffset.UTC).toLocalDate();
        if (!incidentsSnapshot.ensureMonthsAreLoaded(dateSince, dateUntil)) {
            return model; // no incident parquet files
        }

        int openIncidentsCount = 0;

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = incidentsSnapshot.getLoadedMonthlyPartitions();
        for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> partitionEntry : loadedMonthlyPartitions.entrySet()) {
            Table partitionTable = partitionEntry.getValue().getData();
            if (partitionTable == null) {
                // empty partition
                continue;
            }

            TextColumn statusColumn = partitionTable.textColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);
            InstantColumn firstSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);

            int openIncidentsInPartition = statusColumn.isEqualTo(IncidentStatus.open.name())
                    .and(firstSeenColumn.isAfter(since))
                    .size();
            openIncidentsCount += openIncidentsInPartition;
        }

        model.setOpenIncidents(openIncidentsCount);
        return model;
    }
}
