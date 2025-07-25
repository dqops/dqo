/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.incidents.services;

import com.dqops.core.configuration.DqoIncidentsConfigurationProperties;
import com.dqops.core.incidents.IncidentNotificationConfigurations;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.models.CheckResultEntryModel;
import com.dqops.data.checkresults.models.CheckResultListFilterParameters;
import com.dqops.data.checkresults.models.HistogramFilterParameters;
import com.dqops.data.checkresults.models.IssueHistogramModel;
import com.dqops.data.checkresults.services.CheckResultsDataService;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.incidents.models.*;
import com.dqops.data.incidents.snapshot.IncidentsSnapshot;
import com.dqops.data.incidents.snapshot.IncidentsSnapshotFactory;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.data.storage.ParquetPartitionId;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.FilteredNotificationSpec;
import com.dqops.metadata.incidents.IncidentNotificationSpec;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentNotificationsWrapper;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.rest.models.common.SortDirection;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Creates a new incident data service, given all required dependencies.
     * @param incidentsSnapshotFactory Incident snapshot factory.
     * @param checkResultsDataService Data quality check results data service, used to load results (matching issues).
     * @param userHomeContextFactory User home context factory, used to load a list of connections.
     * @param dqoIncidentsConfigurationProperties DQOps incidents configuration parameters.
     * @param defaultTimeZoneProvider Default time zone provider.
     */
    @Autowired
    public IncidentsDataServiceImpl(IncidentsSnapshotFactory incidentsSnapshotFactory,
                                    CheckResultsDataService checkResultsDataService,
                                    UserHomeContextFactory userHomeContextFactory,
                                    DqoIncidentsConfigurationProperties dqoIncidentsConfigurationProperties,
                                    DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.incidentsSnapshotFactory = incidentsSnapshotFactory;
        this.checkResultsDataService = checkResultsDataService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoIncidentsConfigurationProperties = dqoIncidentsConfigurationProperties;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Loads recent incidents on a connection.
     * @param connectionNameFilter Connection name filter.
     * @param filterParameters Incident filter parameters.
     * @param userDomainIdentity Calling user identity with the data domain.
     * @return Collection of recent incidents.
     */
    @Override
    public Collection<IncidentModel> loadRecentIncidentsOnConnection(
            String connectionNameFilter, IncidentListFilterParameters filterParameters, UserDomainIdentity userDomainIdentity) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity, true);
        ConnectionList connectionList = userHomeContext.getUserHome().getConnections();
        List<ConnectionWrapper> connectionWrappers = connectionList.toList();

        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            connectionNameFilter = "*";
        }

        SearchPattern connectionNameSearchPattern = SearchPattern.create(false, connectionNameFilter);
        ArrayList<IncidentModel> incidentModels = new ArrayList<>();

        String filter = filterParameters.getFilter();
        if (!Strings.isNullOrEmpty(filter) && filter.indexOf('*') < 0) {
            filter = "*" + filter + "*";
        }

        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        LocalDate endDate = Instant.now().atZone(defaultTimeZoneId).toLocalDate();
        LocalDate startDate = filterParameters.getRecentMonths() > 1 ?
                endDate.minusMonths(filterParameters.getRecentMonths() - 1) : endDate;

        for (ConnectionWrapper connectionWrapper : connectionWrappers) {
            String connectionName = connectionWrapper.getName();
            if (!connectionNameSearchPattern.match(connectionName)) {
                continue;
            }

            IncidentsSnapshot incidentsSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName, userDomainIdentity);
            if (!incidentsSnapshot.ensureMonthsAreLoaded(startDate, endDate)) {
                return new ArrayList<>(); // no results
            }

            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = incidentsSnapshot.getLoadedMonthlyPartitions();
            for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> partitionEntry : loadedMonthlyPartitions.entrySet()) {
                Table partitionTable = partitionEntry.getValue().getData();
                if (partitionTable == null) {
                    // empty partition
                    continue;
                }

                StringColumn incidentIdColumn = partitionTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME);
                StringColumn schemaColumn = partitionTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME);
                StringColumn tableColumn = partitionTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME);
                IntColumn tablePriorityColumn = partitionTable.intColumn(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME);
                LongColumn incidentHashColumn = partitionTable.longColumn(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME);
                InstantColumn firstSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);
                InstantColumn lastSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME);
                InstantColumn incidentUntilColumn = partitionTable.instantColumn(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME);
                StringColumn dataStreamNameColumn = partitionTable.stringColumn(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
                StringColumn qualityDimensionColumn = partitionTable.stringColumn(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
                StringColumn checkCategoryColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
                StringColumn checkTypeColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME);
                StringColumn checkNameColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME);
                IntColumn highestSeverityColumn = partitionTable.intColumn(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME);
                IntColumn minSeverityColumn = partitionTable.containsColumn(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME) ?
                        partitionTable.intColumn(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME) : null;
                IntColumn failedChecksCountColumn = partitionTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME);
                StringColumn issueUrlColumn = partitionTable.stringColumn(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME);
                StringColumn statusColumn = partitionTable.stringColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);

                int partitionYear = partitionEntry.getKey().getMonth().getYear();
                int partitionMonth = partitionEntry.getKey().getMonth().getMonthValue();

                int rowCount = partitionTable.rowCount();
                for (int rowIndex = rowCount - 1; rowIndex >= 0; rowIndex--) {
                    String status = statusColumn.get(rowIndex);
                    IncidentStatus incidentStatus = IncidentStatus.valueOf(status);

                    int highestSeverity = highestSeverityColumn.get(rowIndex);

                    if (!filterParameters.isIncidentStatusEnabled(incidentStatus)) {
                        continue; // skipping
                    }

                    if (filterParameters.getSeverity() != null && filterParameters.getSeverity() != highestSeverity) {
                        continue; // skipping
                    }

                    if (!Strings.isNullOrEmpty(filterParameters.getCategory())) {
                        if (!Objects.equals(checkCategoryColumn.get(rowIndex), filterParameters.getCategory())) {
                            continue;
                        }
                    }

                    if (!Strings.isNullOrEmpty(filterParameters.getDimension())) {
                        if (!Objects.equals(qualityDimensionColumn.get(rowIndex), filterParameters.getDimension())) {
                            continue;
                        }
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
                    incidentModel.setHighestSeverity(highestSeverity);
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
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity, true);
        ConnectionList connectionList = userHomeContext.getUserHome().getConnections();
        ConnectionWrapper connectionWrapper = connectionList.getByObjectName(connectionName, true);

        if (connectionWrapper == null) {
            return null;
        }

        IncidentsSnapshot incidentsSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName, userDomainIdentity);
        LocalDate incidentMonth = LocalDate.of(year, month, 1);
        LoadedMonthlyPartition monthPartition = incidentsSnapshot.getMonthPartition(incidentMonth, true);
        if (monthPartition == null || monthPartition.getData() == null || monthPartition.getData().rowCount() == 0) {
            return null; // no data
        }

        Table incidentMonthData = monthPartition.getData();
        StringColumn incidentIdColumn = incidentMonthData.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME);
        Selection incidentIdSelection = incidentIdColumn.isEqualTo(incidentId);
        if (incidentIdSelection.isEmpty()) {
            return null;
        }

        int rowIndex = incidentIdSelection.get(0);
        IncidentModel incidentModel = IncidentModel.fromIncidentRow(incidentMonthData.row(rowIndex), connectionName);

        ConnectionIncidentGroupingSpec connectionIncidentGroupingSpec = connectionWrapper.getSpec().getIncidentGrouping();
        IncidentNotificationSpec connectionNotifications = connectionIncidentGroupingSpec != null ?
                connectionIncidentGroupingSpec.getIncidentNotification() : new IncidentNotificationSpec();

        DefaultIncidentNotificationsWrapper defaultIncidentNotifications = userHomeContext.getUserHome().getDefaultIncidentNotifications();
        IncidentNotificationSpec defaultNotifications = defaultIncidentNotifications.getSpec() != null ? defaultIncidentNotifications.getSpec() : new IncidentNotificationSpec();

        IncidentNotificationConfigurations incidentNotificationConfigurations = new IncidentNotificationConfigurations(connectionNotifications, defaultNotifications);
        FilteredNotificationSpec firstMatchingNotification = incidentNotificationConfigurations.findFirstMatchingNotification(incidentModel);
        if (firstMatchingNotification != null) {
            incidentModel.setNotificationName(firstMatchingNotification.getNotificationName());
            incidentModel.setNotificationLocation(firstMatchingNotification.getNotificationLocation());
        }

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
                0, // load all incidents, they will be filtered by the incident hash anyway, so that updated check results for fixed issues are also returned
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
    public IssueHistogramModel buildDailyIssuesHistogramForIncident(String connectionName,
                                                                    int year,
                                                                    int month,
                                                                    String incidentId,
                                                                    HistogramFilterParameters filterParameters,
                                                                    UserDomainIdentity userDomainIdentity) {
        IncidentModel incidentModel = this.loadIncident(connectionName, year, month, incidentId, userDomainIdentity);
        if (incidentModel == null) {
            return null;
        }

        IssueHistogramModel histogramModel = this.checkResultsDataService.buildDailyIssuesHistogram(connectionName,
                new PhysicalTableName(incidentModel.getSchema(), incidentModel.getTable()),
                incidentModel.getIncidentHash(),
                incidentModel.getFirstSeen(),
                incidentModel.getIncidentUntil(),
                0, // load all incidents, they will be filtered by the incident hash anyway, so that updated check results for fixed issues are also returned
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
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity, true);
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
        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        LocalDate dateUntil = now.atZone(defaultTimeZoneId).toLocalDate();
        LocalDate dateSince = since.atZone(defaultTimeZoneId).toLocalDate();
        if (!incidentsSnapshot.ensureMonthsAreLoaded(dateSince, dateUntil)) {
            return model; // no incident parquet files
        }

        int openIncidentsCount = 0;
        Instant mostRecentFirstSeen = null;

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = incidentsSnapshot.getLoadedMonthlyPartitions();
        for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> partitionEntry : loadedMonthlyPartitions.entrySet()) {
            Table partitionTable = partitionEntry.getValue().getData();
            if (partitionTable == null) {
                // empty partition
                continue;
            }

            StringColumn statusColumn = partitionTable.stringColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);
            InstantColumn firstSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);
            Instant partitionMaxFirstSeen = firstSeenColumn.max();
            if (partitionMaxFirstSeen != null) {
                if (mostRecentFirstSeen == null || partitionMaxFirstSeen.isAfter(mostRecentFirstSeen)) {
                    mostRecentFirstSeen = partitionMaxFirstSeen;
                }
            }

            int openIncidentsInPartition = statusColumn.isEqualTo(IncidentStatus.open.name())
                    .and(firstSeenColumn.isAfter(since))
                    .size();
            openIncidentsCount += openIncidentsInPartition;
        }

        model.setOpenIncidents(openIncidentsCount);
        model.setMostRecentFirstSeen(mostRecentFirstSeen);
        return model;
    }

    /**
     * Finds the top <code>limitPerGroup</code> incidents grouped by <code>incidentGrouping</code> that are at the given incident status.
     *
     * @param incidentGrouping   Incident grouping.
     * @param incidentStatus     Incident status to filter by.
     * @param limitPerGroup      The maximum number of incidents per group to return.
     * @param monthsToScan       The number of months back to scan.
     * @param userDomainIdentity Calling user identity with the data domain.
     * @return Summary of the most recent incidents.
     */
    @Override
    public TopIncidentsModel findTopIncidents(TopIncidentGrouping incidentGrouping,
                                              IncidentStatus incidentStatus,
                                              int limitPerGroup,
                                              int monthsToScan,
                                              UserDomainIdentity userDomainIdentity) {
        TopIncidentsModel result = new TopIncidentsModel();
        result.setGrouping(incidentGrouping);
        result.setStatus(incidentStatus);

        if (monthsToScan > 6) {
            monthsToScan = 6; // sanity limit on ca. 180 days
        }

        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        Instant now = Instant.now();
        LocalDate nowLocalDate = now.atZone(defaultTimeZoneId).toLocalDate().withDayOfMonth(1).minusMonths(monthsToScan - 1);
        Instant since = nowLocalDate.atStartOfDay(defaultTimeZoneId).toInstant();
        LocalDate dateUntil = now.atZone(defaultTimeZoneId).toLocalDate();
        LocalDate dateSince = since.atZone(defaultTimeZoneId).toLocalDate();

        result.setOpenIncidentSeverityLevelCounts(IncidentSeverityLevelCountsModel.createInstance(defaultTimeZoneId));
        result.setAcknowledgedIncidentSeverityLevelCounts(IncidentSeverityLevelCountsModel.createInstance(defaultTimeZoneId));

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity, true);
        ConnectionList connectionList = userHomeContext.getUserHome().getConnections();
        List<ConnectionWrapper> connectionWrappers = connectionList.toList();

        for (ConnectionWrapper connectionWrapper : connectionWrappers) {
            String connectionName = connectionWrapper.getName();
            IncidentsSnapshot incidentsSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName, userDomainIdentity);
            if (!incidentsSnapshot.ensureMonthsAreLoaded(dateSince, dateUntil)) {
                continue;
            }

            Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = incidentsSnapshot.getLoadedMonthlyPartitions();
            for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> partitionEntry : loadedMonthlyPartitions.entrySet()) {
                Table partitionTable = partitionEntry.getValue().getData();
                if (partitionTable == null) {
                    // empty partition
                    continue;
                }

                StringColumn incidentIdColumn = partitionTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME);
                StringColumn schemaColumn = partitionTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME);
                StringColumn tableColumn = partitionTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME);
                IntColumn tablePriorityColumn = partitionTable.intColumn(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME);
                LongColumn incidentHashColumn = partitionTable.longColumn(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME);
                InstantColumn firstSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);
                InstantColumn lastSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME);
                InstantColumn incidentUntilColumn = partitionTable.instantColumn(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME);
                StringColumn dataStreamNameColumn = partitionTable.stringColumn(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
                StringColumn qualityDimensionColumn = partitionTable.stringColumn(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
                StringColumn checkCategoryColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
                StringColumn checkTypeColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME);
                StringColumn checkNameColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME);
                IntColumn highestSeverityColumn = partitionTable.intColumn(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME);
                IntColumn minSeverityColumn = partitionTable.containsColumn(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME) ?
                        partitionTable.intColumn(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME) : null;
                IntColumn failedChecksCountColumn = partitionTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME);
                StringColumn issueUrlColumn = partitionTable.stringColumn(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME);
                StringColumn statusColumn = partitionTable.stringColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);

                int partitionYear = partitionEntry.getKey().getMonth().getYear();
                int partitionMonth = partitionEntry.getKey().getMonth().getMonthValue();

                int rowCount = partitionTable.rowCount();
                for (int rowIndex = rowCount - 1; rowIndex >= 0; rowIndex--) {
                    String statusText = statusColumn.get(rowIndex);
                    IncidentStatus status = IncidentStatus.valueOf(statusText);

                    Instant firstSeen = firstSeenColumn.get(rowIndex);
                    int highestSeverity = highestSeverityColumn.get(rowIndex);
                    if (since.isAfter(firstSeen)) {
                        continue; // too old
                    }

                    if(status == IncidentStatus.open){
                        result.getOpenIncidentSeverityLevelCounts().processAddCount(highestSeverity, firstSeen);
                    }
                    if(status == IncidentStatus.acknowledged){
                        result.getAcknowledgedIncidentSeverityLevelCounts().processAddCount(highestSeverity, firstSeen);
                    }

                    if (status != incidentStatus) {
                        continue;
                    }

                    String groupingKey = null;
                    switch (incidentGrouping) {
                        case dimension:
                            groupingKey = qualityDimensionColumn.isMissing(rowIndex) ? null : qualityDimensionColumn.getString(rowIndex);
                            break;

                        case category:
                            groupingKey = checkCategoryColumn.isMissing(rowIndex) ? null : checkCategoryColumn.getString(rowIndex);
                            break;

                        case connection:
                            groupingKey = connectionName;
                            break;
                    }

                    if (groupingKey == null) {
                        groupingKey = ""; // alternative name, when an incident was not grouped by a dimension, we will get null, but we want to return that incident in an unnamed group
                    }

                    List<IncidentModel> incidentModelsByGroup = result.getTopIncidents().get(groupingKey);
                    if (incidentModelsByGroup == null) {
                        incidentModelsByGroup = new ArrayList<>();
                        result.getTopIncidents().put(groupingKey, incidentModelsByGroup);
                    }

                    if (incidentModelsByGroup.size() >= limitPerGroup) {
                        IncidentModel lastIncidentInGroup = incidentModelsByGroup.get(incidentModelsByGroup.size() - 1);
                        if (!firstSeen.isAfter(lastIncidentInGroup.getFirstSeen())) {
                            continue; // skipping
                        }
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
                    incidentModel.setHighestSeverity(highestSeverity);
                    if (minSeverityColumn != null && !minSeverityColumn.isMissing(rowIndex)) {
                        incidentModel.setMinimumSeverity(minSeverityColumn.get(rowIndex));
                    }
                    incidentModel.setFailedChecksCount(failedChecksCountColumn.get(rowIndex));
                    incidentModel.setStatus(incidentStatus);

                    int indexOfInsertionPoint = Collections.binarySearch(incidentModelsByGroup, incidentModel,
                            Comparator.comparing((IncidentModel model) -> model.getFirstSeen()).reversed());

                    if (indexOfInsertionPoint >= 0) {
                        incidentModelsByGroup.add(indexOfInsertionPoint, incidentModel);
                    } else {
                        incidentModelsByGroup.add(-indexOfInsertionPoint - 1, incidentModel);
                    }

                    if (incidentModelsByGroup.size() > limitPerGroup) {
                        incidentModelsByGroup.remove(incidentModelsByGroup.size() - 1);
                    }
                }
            }
        }

        return result;
    }
}
