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
package com.dqops.data.checkresults.services;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.core.configuration.DqoIncidentsConfigurationProperties;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.checkresults.services.models.*;
import com.dqops.data.checkresults.services.models.currentstatus.*;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshot;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.errors.snapshot.ErrorsSnapshot;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactory;
import com.dqops.data.checkresults.services.models.IncidentIssueHistogramModel;
import com.dqops.data.normalization.CommonColumnNames;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.data.storage.ParquetPartitionId;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.rest.models.common.SortDirection;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.utils.datetime.LocalDateTimePeriodUtility;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import com.dqops.utils.tables.TableColumnUtility;
import com.dqops.utils.tables.TableRowUtility;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.instant.PackedInstant;
import tech.tablesaw.selection.Selection;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that returns data from the check results.
 */
@Service
public class CheckResultsDataServiceImpl implements CheckResultsDataService {
    public static final int DEFAULT_MAX_RECENT_LOADED_MONTHS = 3;

    private CheckResultsSnapshotFactory checkResultsSnapshotFactory;
    private ErrorsSnapshotFactory errorsSnapshotFactory;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;
    private DqoIncidentsConfigurationProperties dqoIncidentsConfigurationProperties;

    @Autowired
    public CheckResultsDataServiceImpl(CheckResultsSnapshotFactory checkResultsSnapshotFactory,
                                       ErrorsSnapshotFactory errorsSnapshotFactory,
                                       DefaultTimeZoneProvider defaultTimeZoneProvider,
                                       DqoIncidentsConfigurationProperties dqoIncidentsConfigurationProperties) {
        this.checkResultsSnapshotFactory = checkResultsSnapshotFactory;
        this.errorsSnapshotFactory = errorsSnapshotFactory;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
        this.dqoIncidentsConfigurationProperties = dqoIncidentsConfigurationProperties;
    }

    /**
     * Retrieves the overall status of the recent check executions for the given root checks container (group of checks).
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters Load parameters.
     * @return Overview of the check recent results.
     */
    @Override
    public CheckResultsOverviewDataModel[] readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                       CheckResultsOverviewParameters loadParameters) {
        Map<Long, CheckResultsOverviewDataModel> resultMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table ruleResultsTable = loadRuleResults(loadParameters, connectionName, physicalTableName);
        Table errorsTable = loadErrorsNormalizedToResults(loadParameters, connectionName, physicalTableName);
        Table combinedTable = errorsTable != null ?
                (ruleResultsTable != null ? errorsTable.append(ruleResultsTable) : errorsTable) :
                ruleResultsTable;

        if (combinedTable == null) {
            return new CheckResultsOverviewDataModel[0]; // empty array
        }

        Table filteredTable = filterTableToRootChecksContainer(rootChecksContainerSpec, combinedTable);
        Table sortedTable = filteredTable.sortDescendingOn(
                SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, // then the most recent reading (for partitioned checks) when many partitions were captured
                CheckResultsColumnNames.SEVERITY_COLUMN_NAME); // second on the highest severity first on that time period

        int rowCount = sortedTable.rowCount();
        DateTimeColumn timePeriodColumn = sortedTable.dateTimeColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
        InstantColumn timePeriodUtcColumn = sortedTable.instantColumn(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
        InstantColumn executedAtColumn = sortedTable.instantColumn(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
        IntColumn severityColumn = sortedTable.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
        TextColumn dataGroupNameColumn = sortedTable.textColumn(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
        LongColumn checkHashColumn = sortedTable.longColumn(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
        TextColumn checkCategoryColumn = sortedTable.textColumn(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
        TextColumn checkNameColumn = sortedTable.textColumn(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
        TextColumn tableComparisonColumn = sortedTable.textColumn(SensorReadoutsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);
        DoubleColumn actualValueColumn = sortedTable.doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        for (int i = 0; i < rowCount ; i++) {
            LocalDateTime timePeriod = timePeriodColumn.get(i);
            Instant timePeriodUtc = timePeriodUtcColumn.get(i);
            if (timePeriodUtc == null) {
                timePeriodUtc = timePeriod.atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId().getRules().getOffset(timePeriod)).toInstant();
            }
            Instant executedAt = executedAtColumn.get(i);
            Integer severity = severityColumn.get(i);
            String dataGroupName = dataGroupNameColumn.get(i);
            Long checkHash = checkHashColumn.get(i);
            Double actualValue = actualValueColumn.get(i);

            CheckResultsOverviewDataModel checkResultsOverviewDataModel = resultMap.get(checkHash);
            if (checkResultsOverviewDataModel == null) {
                String checkCategory = checkCategoryColumn.get(i);
                String checkName = checkNameColumn.get(i);
                String comparisonName = tableComparisonColumn.isMissing(i) ? null : tableComparisonColumn.get(i);
                checkResultsOverviewDataModel = new CheckResultsOverviewDataModel() {{
                    setCheckCategory(checkCategory);
                    setCheckName(checkName);
                    setCheckHash(checkHash);
                    setComparisonName(comparisonName);
                }};
                resultMap.put(checkHash, checkResultsOverviewDataModel);
            }

            checkResultsOverviewDataModel.appendResult(timePeriod, timePeriodUtc, executedAt, rootChecksContainerSpec.getCheckTimeScale(),
                    severity, actualValue, dataGroupName, loadParameters.getResultsCount());
        }

        resultMap.values().forEach(m -> m.reverseLists());

        return resultMap.values().toArray(CheckResultsOverviewDataModel[]::new);
    }

    /**
     * Read the results of the most recent table comparison.
     * @param connectionName The connection name of the compared table.
     * @param physicalTableName Physical table name (schema and table) of the compared table.
     * @param checkType Check type.
     * @param timeScale Optional check scale (daily, monthly) for monitoring and partitioned checks.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return Returns the summary information about the table comparison.
     */
    @Override
    public TableComparisonResultsModel readMostRecentTableComparisonResults(String connectionName,
                                                                            PhysicalTableName physicalTableName,
                                                                            CheckType checkType,
                                                                            CheckTimeScale timeScale,
                                                                            String tableComparisonConfigurationName) {
        TableComparisonResultsModel result = new TableComparisonResultsModel();
        CheckResultsOverviewParameters checkResultsLoadParameters = CheckResultsOverviewParameters.createForRecentMonths(2, 1);

        Table ruleResultsTable = loadRuleResults(checkResultsLoadParameters, connectionName, physicalTableName);
        Table errorsTable = loadErrorsNormalizedToResults(checkResultsLoadParameters, connectionName, physicalTableName);
        Table combinedTable = errorsTable != null ?
                (ruleResultsTable != null ? errorsTable.append(ruleResultsTable) : errorsTable) :
                ruleResultsTable;

        if (combinedTable == null) {
            return result;
        }

        Table filteredTable = filterCheckResultsForTableComparison(combinedTable, checkType, timeScale, tableComparisonConfigurationName);
        Table sortedTable = filteredTable.sortDescendingOn(
                SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME); // most recent executions first

        int rowCount = sortedTable.rowCount();
        InstantColumn executedAtColumn = sortedTable.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
        IntColumn severityColumn = sortedTable.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
        TextColumn checkNameColumn = sortedTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
        TextColumn columnNameColumn = sortedTable.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME);
        TextColumn dataGroupNameColumn = sortedTable.textColumn(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

        for (int i = 0; i < rowCount ; i++) {
            Instant executedAt = executedAtColumn.get(i);
            Integer severity = severityColumn.get(i);
            String checkName = checkNameColumn.get(i);
            String dataGroup = dataGroupNameColumn.get(i);
            String columnName = columnNameColumn.get(i);

            result.appendResult(executedAt, checkName, columnName, severity, dataGroup);
        }

        return result;
    }

    /**
     * Retrieves a detailed model of the results of check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Detailed model of the check results.
     */
    @Override
    public CheckResultsListModel[] readCheckStatusesDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                             CheckResultsDetailedFilterParameters loadParameters) {
        Map<Long, CheckResultsListModel> resultMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table ruleResultsTable = loadRecentRuleResults(loadParameters, connectionName, physicalTableName);
        if (ruleResultsTable == null || ruleResultsTable.isEmpty()) {
            return new CheckResultsListModel[0]; // empty array
        }

        Table filteredTable = filterTableToRootChecksContainerAndFilterParameters(rootChecksContainerSpec, ruleResultsTable, loadParameters);
        if (filteredTable.isEmpty()) {
            return new CheckResultsListModel[0]; // empty array
        }
        Table filteredTableByDataGroup = filteredTable;
        if (!Strings.isNullOrEmpty(loadParameters.getDataGroupName())) {
            TextColumn dataGroupNameFilteredColumn = filteredTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
            filteredTableByDataGroup = filteredTable.where(dataGroupNameFilteredColumn.isEqualTo(loadParameters.getDataGroupName()));
        }

        if (filteredTableByDataGroup.isEmpty()) {
            return new CheckResultsListModel[0]; // empty array
        }

        Table sortedTable = filteredTableByDataGroup.sortDescendingOn(
                SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, // then the most recent reading (for partitioned checks) when many partitions were captured
                CheckResultsColumnNames.SEVERITY_COLUMN_NAME); // second on the highest severity first on that time period

        LongColumn checkHashColumn = sortedTable.longColumn(CheckResultsColumnNames.CHECK_HASH_COLUMN_NAME);
        LongColumn checkHashColumnUnsorted = filteredTable.longColumn(CheckResultsColumnNames.CHECK_HASH_COLUMN_NAME);
        TextColumn allDataGroupColumnUnsorted = filteredTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
        TextColumn allDataGroupColumn = sortedTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

        int rowCount = sortedTable.rowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Long checkHash = checkHashColumn.get(rowIndex);
            String dataGroupNameForCheck = allDataGroupColumn.get(rowIndex);
            CheckResultsListModel checkResultsListModel = resultMap.get(checkHash);

            CheckResultEntryModel singleModel = null;

            if (checkResultsListModel != null) {
                if (checkResultsListModel.getCheckResultEntries().size() >= loadParameters.getMaxResultsPerCheck()) {
                    continue; // enough results loaded
                }

                if (!Objects.equals(dataGroupNameForCheck, checkResultsListModel.getDataGroup())) {
                    continue; // we are not mixing groups, results for a different group were already loaded
                }
            } else {
                singleModel = createSingleCheckResultDetailedModel(sortedTable.row(rowIndex));
                checkResultsListModel = new CheckResultsListModel();
                checkResultsListModel.setCheckCategory(singleModel.getCheckCategory());
                checkResultsListModel.setCheckName(singleModel.getCheckName());
                checkResultsListModel.setCheckHash(singleModel.getCheckHash());
                checkResultsListModel.setCheckType(singleModel.getCheckType());
                checkResultsListModel.setCheckDisplayName(singleModel.getCheckDisplayName());
                checkResultsListModel.setDataGroup(dataGroupNameForCheck);

                Selection resultsForCheckHash = checkHashColumnUnsorted.isEqualTo(checkHash);
                List<String> dataGroupsForCheck = allDataGroupColumnUnsorted.where(resultsForCheckHash).asList().stream().distinct().sorted().collect(Collectors.toList());

                if (dataGroupsForCheck.size() > 1 && dataGroupsForCheck.contains(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME)) {
                    dataGroupsForCheck.remove(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
                    dataGroupsForCheck.add(0, CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
                }

                checkResultsListModel.setDataGroups(dataGroupsForCheck);
                resultMap.put(singleModel.getCheckHash(), checkResultsListModel);
            }

            if (singleModel == null) {
                singleModel = createSingleCheckResultDetailedModel(sortedTable.row(rowIndex));
            }

            checkResultsListModel.getCheckResultEntries().add(singleModel);
        }

        return resultMap.values().toArray(CheckResultsListModel[]::new);
    }

    /**
     * Creates a data model that returns the result of a single check.
     * @param row Row from the data quality result table.
     * @return Model with all information for a single check result.
     */
    @NotNull
    protected CheckResultEntryModel createSingleCheckResultDetailedModel(Row row) {
        String id = row.getString(SensorReadoutsColumnNames.ID_COLUMN_NAME);
        Double actualValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        Double expectedValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);
        Double warningLowerBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.WARNING_LOWER_BOUND_COLUMN_NAME);
        Double warningUpperBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.WARNING_UPPER_BOUND_COLUMN_NAME);
        Double errorLowerBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.ERROR_LOWER_BOUND_COLUMN_NAME);
        Double errorUpperBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.ERROR_UPPER_BOUND_COLUMN_NAME);
        Double fatalLowerBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.FATAL_LOWER_BOUND_COLUMN_NAME);
        Double fatalUpperBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.FATAL_UPPER_BOUND_COLUMN_NAME);
        Integer severity = row.getInt(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);

        String checkCategory = row.getString(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
        String checkDisplayName = row.getString(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME);
        Long checkHash = row.getLong(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
        String checkName = row.getString(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
        String checkType = row.getString(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME);

        String columnName = TableRowUtility.getSanitizedStringValue(row, SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
        String dataGroupName = row.getString(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

        Integer durationMs = row.getInt(SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME);
        Instant executedAt = row.getInstant(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
        String timeGradient = row.getString(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
        LocalDateTime timePeriod = row.getDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);

        Boolean includeInKpi = row.getBoolean(CheckResultsColumnNames.INCLUDE_IN_KPI_COLUMN_NAME);
        Boolean includeInSla = row.getBoolean(CheckResultsColumnNames.INCLUDE_IN_SLA_COLUMN_NAME);
        String provider = row.getString(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME);
        String qualityDimension = row.getString(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
        String sensorName = row.getString(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME);

        String tableComparison = row.getString(CheckResultsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);

        CheckResultEntryModel singleModel = new CheckResultEntryModel() {{
            setId(id);
            setActualValue(actualValue);
            setExpectedValue(expectedValue);
            setWarningLowerBound(warningLowerBound);
            setWarningUpperBound(warningUpperBound);
            setErrorLowerBound(errorLowerBound);
            setErrorUpperBound(errorUpperBound);
            setFatalLowerBound(fatalLowerBound);
            setFatalUpperBound(fatalUpperBound);
            setSeverity(severity);

            setCheckCategory(checkCategory);
            setCheckName(checkName);
            setCheckHash(checkHash);
            setCheckType(checkType);
            setCheckDisplayName(checkDisplayName);

            setColumnName(columnName);
            setDataGroup(dataGroupName);

            setDurationMs(durationMs);
            setExecutedAt(executedAt);
            setTimeGradient(timeGradient);
            setTimePeriod(timePeriod);

            setIncludeInKpi(includeInKpi);
            setIncludeInSla(includeInSla);
            setProvider(provider);
            setQualityDimension(qualityDimension);
            setSensorName(sensorName);
            setTableComparison(tableComparison);
        }};
        return singleModel;
    }

    /**
     * Loads the results of failed data quality checks that are attached to the given incident, identified by the incident hash, first seen and incident until timestamps.
     * Returns only check results with a minimum severity.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param incidentHash      Incident hash.
     * @param firstSeen         The timestamp when the incident was first seen.
     * @param incidentUntil     The timestamp when the incident was closed or expired, returns check results up to this timestamp.
     * @param minSeverity       Minimum check issue severity that is returned.
     * @param filterParameters  Filter parameters.
     * @return An array of matching check results.
     */
    @Override
    public CheckResultEntryModel[] loadCheckResultsRelatedToIncident(String connectionName,
                                                                     PhysicalTableName physicalTableName,
                                                                     long incidentHash,
                                                                     Instant firstSeen,
                                                                     Instant incidentUntil,
                                                                     int minSeverity,
                                                                     CheckResultListFilterParameters filterParameters) {
        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.COLUMN_NAMES_FOR_INCIDENT_RELATED_RESULTS);
        LocalDate startMonth;
        if (filterParameters.getDays() != null) {
            startMonth = firstSeen.minus(12L, ChronoUnit.HOURS).atZone(ZoneOffset.UTC).toLocalDate();
        }
        else {
            startMonth = Instant.now().atZone(defaultTimeZoneId).toLocalDate().minus(filterParameters.getDays(), ChronoUnit.DAYS);
        }

        LocalDate endMonth = incidentUntil.plus(12L, ChronoUnit.HOURS).atZone(ZoneOffset.UTC).toLocalDate();
        if (!checkResultsSnapshot.ensureMonthsAreLoaded(startMonth, endMonth)) {
            return new CheckResultEntryModel[0];
        }

        Instant startTimestamp = firstSeen;
        Instant endTimestamp = incidentUntil;
        if (filterParameters.getDate() != null) {
            startTimestamp = filterParameters.getDate().atTime(0, 0).atZone(defaultTimeZoneId).toInstant();
            endTimestamp = startTimestamp.plus(1L, ChronoUnit.DAYS).minus(1L, ChronoUnit.MILLIS);
        } else {
            if (filterParameters.getDays() != null) {
                startTimestamp = Instant.now().atZone(defaultTimeZoneId).toLocalDate()
                        .minus(filterParameters.getDays(), ChronoUnit.DAYS).atTime(0, 0).atZone(defaultTimeZoneId)
                        .toInstant();
            }
        }

        List<CheckResultEntryModel> resultsList = new ArrayList<>();

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = checkResultsSnapshot.getLoadedMonthlyPartitions();
        for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitionEntry : loadedMonthlyPartitions.entrySet()) {
            Table partitionData = loadedPartitionEntry.getValue().getData();
            if (partitionData == null || partitionData.rowCount() == 0) {
                continue;
            }

            Selection minSeveritySelection = partitionData.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME).isGreaterThanOrEqualTo(minSeverity);
            InstantColumn partitionExecutedAtColumn = partitionData.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
            Selection issuesInTimeRange = partitionExecutedAtColumn.isBetweenIncluding(
                    PackedInstant.pack(startTimestamp), PackedInstant.pack(endTimestamp));
            Selection incidentHashSelection = partitionData.longColumn(CheckResultsColumnNames.INCIDENT_HASH_COLUMN_NAME).isIn(incidentHash);

            Selection selectionOfMatchingIssues = minSeveritySelection.and(issuesInTimeRange).and(incidentHashSelection);
            if (!Strings.isNullOrEmpty(filterParameters.getColumn())) {
                TextColumn partitionColumnNameColumn = partitionData.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME);
                if (Objects.equals(CheckResultsDataService.COLUMN_NAME_TABLE_CHECKS_PLACEHOLDER, filterParameters.getColumn())) {
                    selectionOfMatchingIssues = selectionOfMatchingIssues.and(partitionColumnNameColumn.isMissing()); // table level sensors
                } else {
                    selectionOfMatchingIssues = selectionOfMatchingIssues.and(partitionColumnNameColumn.isEqualTo(filterParameters.getColumn()));
                }
            }

            if (!Strings.isNullOrEmpty(filterParameters.getCheck())) {
                TextColumn partitionCheckNameColumn = partitionData.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
                selectionOfMatchingIssues = selectionOfMatchingIssues.and(partitionCheckNameColumn.isEqualTo(filterParameters.getCheck()));
            }

            if (selectionOfMatchingIssues.size() == 0) {
                continue;
            }

            for (Integer rowIndex : selectionOfMatchingIssues) {
                Row row = partitionData.row(rowIndex);
                CheckResultEntryModel singleCheckResultDetailedModel = createSingleCheckResultDetailedModel(row);

                if (!Strings.isNullOrEmpty(filterParameters.getFilter()) &&
                        !singleCheckResultDetailedModel.matchesFilter(filterParameters.getFilter())) {
                    continue;
                }

                resultsList.add(singleCheckResultDetailedModel);
            }
        }

        Comparator<CheckResultEntryModel> sortComparator = CheckResultEntryModel.makeSortComparator(filterParameters.getOrder());
        if (filterParameters.getSortDirection() == SortDirection.asc) {
            resultsList.sort(sortComparator);
        }
        else {
            resultsList.sort(sortComparator.reversed());
        }

        int startRowIndexInPage = (filterParameters.getPage() - 1) * filterParameters.getLimit();
        int untilRowIndexInPage = filterParameters.getPage() * filterParameters.getLimit();

        if (startRowIndexInPage >= resultsList.size()) {
            return new CheckResultEntryModel[0]; // no results
        }

        List<CheckResultEntryModel> pageResults = resultsList.subList(startRowIndexInPage, Math.min(untilRowIndexInPage, resultsList.size()));
        CheckResultEntryModel[] resultsArray = pageResults.toArray(CheckResultEntryModel[]::new);
        return resultsArray;
    }

    /**
     * Builds a histogram of data quality issues for an incident. The histogram returns daily counts of data quality issues,
     * also counting occurrences of data quality issues at various severity levels.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param incidentHash      Incident hash.
     * @param firstSeen         The timestamp when the incident was first seen.
     * @param incidentUntil     The timestamp when the incident was closed or expired, returns check results up to this timestamp.
     * @param minSeverity       Minimum check issue severity that is returned.
     * @param filterParameters  Optional filter to limit the issues included in the histogram.
     * @return Daily histogram of failed data quality checks.
     */
    @Override
    public IncidentIssueHistogramModel buildDailyIssuesHistogramForIncident(String connectionName,
                                                                            PhysicalTableName physicalTableName,
                                                                            long incidentHash,
                                                                            Instant firstSeen,
                                                                            Instant incidentUntil,
                                                                            int minSeverity,
                                                                            IncidentHistogramFilterParameters filterParameters) {
        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();

        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.COLUMN_NAMES_FOR_INCIDENT_RELATED_RESULTS);
        LocalDate startMonth;
        if (filterParameters.getDays() != null) {
            startMonth = firstSeen.minus(12L, ChronoUnit.HOURS).atZone(ZoneOffset.UTC).toLocalDate();
        }
        else {
            startMonth = Instant.now().atZone(defaultTimeZoneId).toLocalDate().minus(filterParameters.getDays(), ChronoUnit.DAYS);
        }
        LocalDate endMonth = incidentUntil.plus(12L, ChronoUnit.HOURS).atZone(ZoneOffset.UTC).toLocalDate();
        if (!checkResultsSnapshot.ensureMonthsAreLoaded(startMonth, endMonth)) {
            return new IncidentIssueHistogramModel();
        }

        IncidentIssueHistogramModel histogramModel = new IncidentIssueHistogramModel();

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = checkResultsSnapshot.getLoadedMonthlyPartitions();
        for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitionEntry : loadedMonthlyPartitions.entrySet()) {
            Table partitionData = loadedPartitionEntry.getValue().getData();
            if (partitionData == null || partitionData.rowCount() == 0) {
                continue;
            }

            IntColumn severityColumn = partitionData.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
            Selection minSeveritySelection = severityColumn.isGreaterThanOrEqualTo(minSeverity);

            InstantColumn executedAtColumn = partitionData.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
            Instant startTimestamp = firstSeen;
            if (filterParameters.getDays() != null) {
                startTimestamp = Instant.now().atZone(defaultTimeZoneId).toLocalDate()
                        .minus(filterParameters.getDays(), ChronoUnit.DAYS).atTime(0, 0).atZone(defaultTimeZoneId)
                        .toInstant();
            }

            Selection issuesInTimeRange = executedAtColumn.isBetweenIncluding(
                    PackedInstant.pack(startTimestamp), PackedInstant.pack(incidentUntil));
            Selection incidentHashSelection = partitionData.longColumn(CheckResultsColumnNames.INCIDENT_HASH_COLUMN_NAME).isIn(incidentHash);

            Selection selectionOfMatchingIssues = minSeveritySelection.and(issuesInTimeRange).and(incidentHashSelection);
            if (selectionOfMatchingIssues.size() == 0) {
                continue;
            }

            TextColumn columnNameColumn = partitionData.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME);
            TextColumn checkNameColumn = partitionData.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);

            for (Integer rowIndex : selectionOfMatchingIssues) {
                Row row = partitionData.row(rowIndex);

                if (!Strings.isNullOrEmpty(filterParameters.getFilter())) {
                    CheckResultEntryModel singleCheckResultDetailedModel = createSingleCheckResultDetailedModel(row);
                    if (!singleCheckResultDetailedModel.matchesFilter(filterParameters.getFilter())) {
                        continue;
                    }
                }

                Integer severity = severityColumn.get(rowIndex);
                Instant executedAt = executedAtColumn.get(rowIndex);
                LocalDate executedAtDate = executedAt.atZone(defaultTimeZoneId).toLocalDate();
                String columnName = columnNameColumn.get(rowIndex);
                String checkName = checkNameColumn.get(rowIndex);
                if (columnName == null) {
                    columnName = CheckResultsDataService.COLUMN_NAME_TABLE_CHECKS_PLACEHOLDER;
                }

                boolean dateMatch = filterParameters.getDate() == null || Objects.equals(filterParameters.getDate(), executedAtDate);
                boolean columnMatch = Strings.isNullOrEmpty(filterParameters.getColumn()) || Objects.equals(filterParameters.getColumn(), columnName);
                boolean checkMatch = Strings.isNullOrEmpty(filterParameters.getCheck()) || Objects.equals(filterParameters.getCheck(), checkName);

                if (columnMatch && checkMatch) {
                    histogramModel.incrementSeverityForDay(executedAtDate, severity);
                }

                if (dateMatch && checkMatch) {
                    histogramModel.incrementCountForColumn(columnName);
                }

                if (dateMatch && columnMatch) {
                    histogramModel.incrementCountForCheck(checkName);
                }
            }
        }

        histogramModel.addMissingDaysInRange();
        histogramModel.sortAndTruncateColumnHistogram(this.dqoIncidentsConfigurationProperties.getColumnHistogramSize());
        histogramModel.sortAndTruncateCheckHistogram(this.dqoIncidentsConfigurationProperties.getCheckHistogramSize());

        return histogramModel;
    }

    /**
     * Analyzes the table to find the status of the most recent data quality check for each time series
     * and asses the most current status.
     * @param tableCurrentDataQualityStatusFilterParameters Filter parameters container.
     * @return The table status.
     */
    @Override
    public TableCurrentDataQualityStatusModel analyzeTableMostRecentQualityStatus(
            TableCurrentDataQualityStatusFilterParameters tableCurrentDataQualityStatusFilterParameters) {
        String connectionName = tableCurrentDataQualityStatusFilterParameters.getConnectionName();
        PhysicalTableName physicalTableName = tableCurrentDataQualityStatusFilterParameters.getPhysicalTableName();

        TableCurrentDataQualityStatusModel statusModel = new TableCurrentDataQualityStatusModel();
        statusModel.setConnectionName(connectionName);
        statusModel.setSchemaName(physicalTableName.getSchemaName());
        statusModel.setTableName(physicalTableName.getTableName());

        int lastMonths = tableCurrentDataQualityStatusFilterParameters.getLastMonths();
        if (tableCurrentDataQualityStatusFilterParameters.getSince() != null) {
            ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
            ZonedDateTime nowZonedTime = Instant.now().atZone(defaultTimeZoneId);
            ZonedDateTime sinceZonedTime = tableCurrentDataQualityStatusFilterParameters.getSince().atZone(defaultTimeZoneId);

            int monthsDifference = (nowZonedTime.getYear() * 12 + nowZonedTime.getMonth().getValue() - 1) -
                    (sinceZonedTime.getYear() * 12 + sinceZonedTime.getMonth().getValue() - 1)
                    + 1; // load the current month

            if (lastMonths < monthsDifference) {
                lastMonths = monthsDifference;
            }
        }

        CheckResultsOverviewParameters checkResultsLoadParameters = CheckResultsOverviewParameters
                .createForRecentMonths(lastMonths, lastMonths + 1);

        Table ruleResultsTable = loadRuleResults(checkResultsLoadParameters, connectionName, physicalTableName);
        Table errorsTable = loadErrorsNormalizedToResults(checkResultsLoadParameters, connectionName, physicalTableName);
        Table combinedTable = errorsTable != null ?
                (ruleResultsTable != null ? errorsTable.append(ruleResultsTable) : errorsTable) :
                ruleResultsTable;

        if (combinedTable == null) {
            return statusModel;
        }

        Table filteredTable = filterTableOnFilterParameters(combinedTable, tableCurrentDataQualityStatusFilterParameters);

        Table filteredTableByDataGroup = filteredTable;
        if (!Strings.isNullOrEmpty(tableCurrentDataQualityStatusFilterParameters.getDataGroup())) {
            TextColumn dataGroupNameFilteredColumn = filteredTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
            filteredTableByDataGroup = filteredTable.where(dataGroupNameFilteredColumn.isEqualTo(tableCurrentDataQualityStatusFilterParameters.getDataGroup()));
        }

        Table sortedTable = filteredTableByDataGroup.sortDescendingOn(
                CheckResultsColumnNames.CHECK_HASH_COLUMN_NAME,
                CheckResultsColumnNames.TIME_SERIES_ID_COLUMN_NAME,
                CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);

        TableCurrentDataQualityStatusModel statusModelWithStatistics = calculateStatus(sortedTable, statusModel);

        return statusModelWithStatistics;
    }

    /**
     * Calculates status for the table. Completes the TableDataQualityStatusModel with total severity data.
     * @param sourceTable Source table to be filtered.
     * @param tableStatusModel Object with connection, schema and table name.
     * @return Complete TableDataQualityStatusModel
     */
    protected TableCurrentDataQualityStatusModel calculateStatus(Table sourceTable, TableCurrentDataQualityStatusModel tableStatusModel){

        LongColumn checkHashColumn = sourceTable.longColumn(CheckResultsColumnNames.CHECK_HASH_COLUMN_NAME);
        TextColumn timeSeriesIdColumn = sourceTable.textColumn(CheckResultsColumnNames.TIME_SERIES_ID_COLUMN_NAME);
        InstantColumn executedAtColumn = sourceTable.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
        IntColumn severityColumn = sourceTable.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
        TextColumn checkNameColumn = sourceTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
        TextColumn columnNameColumn = sourceTable.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME);

        long lastCheckHash = Long.MIN_VALUE;
        String lastTimeSeriesId = "";
        int rowCount = sourceTable.rowCount();

        for (int i = 0; i < rowCount; i++) {
            long currentCheckHash = checkHashColumn.getLong(i);
            String currentTimeSeriesId = timeSeriesIdColumn.getString(i);

            if (lastCheckHash == currentCheckHash && Objects.equals(lastTimeSeriesId, currentTimeSeriesId)) {
                continue;
            }

            Integer severity = severityColumn.get(i);
            if (severity == null) {
                continue;
            }

            Instant executedAt = executedAtColumn.get(i);
            if (tableStatusModel.getLastCheckExecutedAt() != null) {
                if (executedAt != null && executedAt.isAfter(tableStatusModel.getLastCheckExecutedAt())) {
                    tableStatusModel.setLastCheckExecutedAt(executedAt);
                }
            } else {
                tableStatusModel.setLastCheckExecutedAt(executedAt);
            }

            incrementTotalIssueCount(tableStatusModel, severity);

            String checkName = checkNameColumn.get(i);
            String columnName = columnNameColumn.get(i);
            CurrentDataQualityStatusHolder currentStatusHolder;

            if (Strings.isNullOrEmpty(columnName)) {
                // table level check
                currentStatusHolder = tableStatusModel;
            } else {
                // column level check
                ColumnCurrentDataQualityStatusModel columnCurrentDataQualityStatusModel = tableStatusModel.getColumns().get(columnName);
                if (columnCurrentDataQualityStatusModel == null) {
                    columnCurrentDataQualityStatusModel = new ColumnCurrentDataQualityStatusModel();
                    tableStatusModel.getColumns().put(columnName, columnCurrentDataQualityStatusModel);
                }

                currentStatusHolder = columnCurrentDataQualityStatusModel;
                incrementTotalIssueCount(columnCurrentDataQualityStatusModel, severity);
            }

            if (currentStatusHolder.getLastCheckExecutedAt() != null) {
                if (executedAt != null && executedAt.isAfter(currentStatusHolder.getLastCheckExecutedAt())) {
                    currentStatusHolder.setLastCheckExecutedAt(executedAt);
                }
            } else {
                currentStatusHolder.setLastCheckExecutedAt(executedAt);
            }

            CheckCurrentDataQualityStatusModel checkCurrentStatusModel = currentStatusHolder.getChecks().get(checkName);
            if (checkCurrentStatusModel == null) {
                checkCurrentStatusModel = new CheckCurrentDataQualityStatusModel();
                currentStatusHolder.getChecks().put(checkName, checkCurrentStatusModel);
            } else if (checkCurrentStatusModel.getExecutedAt().isAfter(executedAt)) {
                continue;  // we have the current status, we are skipping...  TODO: but for partitioned checks, we should not....
            }

            if (severity > tableStatusModel.getHighestSeverityLevel() && severity != 4) {
                tableStatusModel.setHighestSeverityLevel(severity);
            }

            if ((checkCurrentStatusModel.getSeverity() == null || severity > checkCurrentStatusModel.getSeverity().getSeverity()) && severity != 4) {
                checkCurrentStatusModel.setSeverity(CheckResultStatus.fromSeverity(severity));
            }
        }

        return tableStatusModel;
    }

    /**
     * Increments the count of issues with the given severity level in a current DQ status holder, status holders are table and column levels.
     * @param dataQualityStatusHolder Target aata quality status holder to increment.
     * @param severity The severity level.
     */
    protected void incrementTotalIssueCount(CurrentDataQualityStatusHolder dataQualityStatusHolder, int severity) {
        dataQualityStatusHolder.setExecutedChecks(dataQualityStatusHolder.getExecutedChecks() + 1);
        switch (severity) {
            case 0:
                dataQualityStatusHolder.setValidResults(dataQualityStatusHolder.getValidResults() + 1);
                break;
            case 1:
                dataQualityStatusHolder.setWarnings(dataQualityStatusHolder.getWarnings() + 1);
                break;
            case 2:
                dataQualityStatusHolder.setErrors(dataQualityStatusHolder.getErrors() + 1);
                break;
            case 3:
                dataQualityStatusHolder.setFatals(dataQualityStatusHolder.getFatals() + 1);
                break;
            case 4:
                dataQualityStatusHolder.setExecutionErrors(dataQualityStatusHolder.getExecutionErrors() + 1);
                break;
        }
    }

    /**
     * Filters the results to only the results for the target object.
     * @param rootChecksContainerSpec Root checks container to identify the parent column, check type and time scale.
     * @param sourceTable Source table to be filtered.
     * @return Filtered table.
     */
    protected Table filterTableToRootChecksContainer(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                     Table sourceTable) {
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        Selection rowSelection = sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();
        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        TextColumn columnNameColumn = sourceTable.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME);
        rowSelection = rowSelection.and((columnName != null) ? columnNameColumn.isEqualTo(columnName) : columnNameColumn.isMissing());

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Filters the results based on filterParameters object to only the results for the target object.
     * @param sourceTable Source table to be filtered.
     * @param filterParameters Filter parameters.
     * @return Filtered table.
     */
    protected Table filterTableOnFilterParameters(Table sourceTable,
                                                  TableCurrentDataQualityStatusFilterParameters filterParameters) {

        Selection rowSelection = Selection.withRange(0, sourceTable.rowCount());

        CheckType checkType = filterParameters.getCheckType();
        if (checkType != null) {
            rowSelection = sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType.toString());
        }

        Instant since = filterParameters.getSince();
        if (since != null) {
            InstantColumn executedAtColumn = sourceTable.instantColumn(CommonColumnNames.EXECUTED_AT_COLUMN_NAME);
            rowSelection = rowSelection.andNot(executedAtColumn.isBefore(since));
        }

        CheckTimeScale checkTimeScale = filterParameters.getCheckTimeScale();
        if (checkTimeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = checkTimeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        if (!Strings.isNullOrEmpty(filterParameters.getCheckName())) {
            TextColumn checkNameColumn = sourceTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
            rowSelection = rowSelection.and(checkNameColumn.isEqualTo(filterParameters.getCheckName()));
        }

        String checkCategory = filterParameters.getCategory();
        String tableComparison = filterParameters.getTableComparison();
        rowSelection = filterCategoryAndTableComparison(sourceTable, rowSelection, checkCategory, tableComparison);


        String qualityDimension = filterParameters.getQualityDimension();
        if (qualityDimension != null) {
            TextColumn dimensionColumn = sourceTable.textColumn(CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
            rowSelection = rowSelection.and(dimensionColumn.isEqualTo(qualityDimension));
        }

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Reduces the selection with a filter composed of combined the check category and table comparison.
     * @param sourceTable Source table to be filtered.
     * @param currentSelection A selection that will be reduced.
     * @param checkCategory Check category.
     * @param tableComparison Table comparison.
     * @return Reduced selection to combined check category and table comparison used for filtering a table.
     */
    protected Selection filterCategoryAndTableComparison(Table sourceTable,
                                                         Selection currentSelection,
                                                         String checkCategory,
                                                         String tableComparison){

        if (!Strings.isNullOrEmpty(checkCategory)) {
            if (checkCategory.startsWith(AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME + "/")) {
                // this code will support receiving combined category names for table comparisons
                String[] columnCategorySplits = StringUtils.split(checkCategory, '/');
                checkCategory = columnCategorySplits[0];
                tableComparison = columnCategorySplits[1];
            }

            TextColumn checkCategoryColumn = sourceTable.textColumn(CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            currentSelection = currentSelection.and(checkCategoryColumn.isEqualTo(checkCategory));
        }

        if (!Strings.isNullOrEmpty(tableComparison)) {
            TextColumn tableComparisonNameColumn = sourceTable.textColumn(CheckResultsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);
            currentSelection = currentSelection.and(tableComparisonNameColumn.isEqualTo(tableComparison));
        }

        return currentSelection;
    }


    /**
     * Filters the results to only the results for the target object.
     * @param rootChecksContainerSpec Root checks container to identify the parent column, check type and time scale.
     * @param sourceTable Source table to be filtered.
     * @param filterParameters Filter parameters.
     * @return Filtered table.
     */
    protected Table filterTableToRootChecksContainerAndFilterParameters(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                        Table sourceTable,
                                                                        CheckResultsDetailedFilterParameters filterParameters) {
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        Selection rowSelection = sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();
        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        TextColumn columnNameColumn = sourceTable.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME);
        rowSelection = rowSelection.and((columnName != null) ? columnNameColumn.isEqualTo(columnName) : columnNameColumn.isMissing());

        if (!Strings.isNullOrEmpty(filterParameters.getCheckName())) {
            TextColumn checkNameColumn = sourceTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
            rowSelection = rowSelection.and(checkNameColumn.isEqualTo(filterParameters.getCheckName()));
        }

        String checkCategory = filterParameters.getCheckCategory();
        String tableComparison = filterParameters.getTableComparison();

        rowSelection = filterCategoryAndTableComparison(sourceTable, rowSelection, checkCategory, tableComparison);

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Filters results to find only table comparison checks.
     * @param sourceTable Source table to be filtered.
     * @param checkType Check type.
     * @param timeScale Optional check time scale.
     * @param tableComparisonName Table comparison name.
     * @return Filtered table.
     */
    protected Table filterCheckResultsForTableComparison(Table sourceTable,
                                                         CheckType checkType,
                                                         CheckTimeScale timeScale,
                                                         String tableComparisonName) {
        String checkTypeString = checkType.getDisplayName();
        Selection rowSelection = sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME)
                .isEqualTo(checkTypeString);

        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            String timeSeriesGradientName = timePeriodGradient.name();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timeSeriesGradientName));
        }

        TextColumn checkCategoryColumn = sourceTable.textColumn(CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
        rowSelection = rowSelection.and(checkCategoryColumn.isEqualTo(AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME));

        if (!Strings.isNullOrEmpty(tableComparisonName)) {
            TextColumn tableComparisonNameColumn = (TextColumn) TableColumnUtility.findColumn(sourceTable, CheckResultsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);
            if (tableComparisonNameColumn != null) {
                rowSelection = rowSelection.and(tableComparisonNameColumn.isEqualTo(tableComparisonName));
            } else {
                rowSelection = Selection.with(); // empty selection, because there is no column
            }
        }

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Loads all errors and normalizes them to match the column names loaded from the rule results.
     * Adds a fake "severity" column with a value 4, so it is higher even then a fatal severity data quality error.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Errors table or null when no data found.
     */
    protected Table loadErrorsNormalizedToResults(CheckResultsOverviewParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorsColumnNames.COLUMN_NAMES_FOR_ERRORS_OVERVIEW);
        errorsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        Table allErrors = errorsSnapshot.getAllData();
        if (allErrors == null) {
            return null;
        }

        IntColumn severityColumn = IntColumn.create(CheckResultsColumnNames.SEVERITY_COLUMN_NAME, allErrors.rowCount());
        severityColumn.setMissingTo(CheckResultStatus.execution_error.getSeverity()); // severity 0,1,2,3 are success,warning,error,fatal, so a processing error with severity 4 will sort ahead of other severities (processing errors are more severe for the overview)
        allErrors.addColumns(severityColumn);

        return allErrors;
    }

    /**
     * Loads rule results.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with all rule results or null when no data found.
     */
    protected Table loadRuleResults(CheckResultsOverviewParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.COLUMN_NAMES_FOR_RESULTS_OVERVIEW);
        checkResultsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        Table ruleResultsData = checkResultsSnapshot.getAllData();
        return ruleResultsData;
    }

    /**
     * Loads rule results for a maximum of two months available in the data, within the date range specified in {@code loadParameters}.
     * If the date range is open-ended, only one or none of the range's boundaries are checked.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with rule results for the most recent two months inside the specified range or null when no data found.
     */
    protected Table loadRecentRuleResults(CheckResultsDetailedFilterParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.COLUMN_NAMES_FOR_RESULTS_DETAILED);
        int maxMonthsToLoad = DEFAULT_MAX_RECENT_LOADED_MONTHS;

        if (loadParameters.getStartMonth() != null && loadParameters.getEndMonth() != null) {
            LocalDate startMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getStartMonth());
            LocalDate endMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getEndMonth());

            maxMonthsToLoad =
                    (int)LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                            startMonthTruncated.atStartOfDay(), endMonthTruncated.atStartOfDay(), TimePeriodGradient.month) + 1;
        }

        checkResultsSnapshot.ensureNRecentMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth(), maxMonthsToLoad);
        Table ruleResultsData = checkResultsSnapshot.getAllData();
        return ruleResultsData;
    }
}
