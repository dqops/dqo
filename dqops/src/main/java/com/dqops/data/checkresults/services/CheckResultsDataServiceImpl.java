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
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.checkresults.models.*;
import com.dqops.data.checkresults.models.currentstatus.*;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshot;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.errors.snapshot.ErrorsSnapshot;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactory;
import com.dqops.data.normalization.CommonColumnNames;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.data.storage.ParquetPartitionId;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.rest.models.common.SortDirection;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.services.check.mining.DataAssetProfilingResults;
import com.dqops.services.check.mining.TableProfilingResults;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.utils.datetime.LocalDateTimePeriodUtility;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import com.dqops.utils.tables.TableColumnUtility;
import com.dqops.utils.tables.TableCopyUtility;
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
     * @param userDomainIdentity User identity with the data domain.
     * @return Overview of the check recent results.
     */
    @Override
    public CheckResultsOverviewDataModel[] readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                       CheckResultsOverviewParameters loadParameters,
                                                                       UserDomainIdentity userDomainIdentity) {
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();
        int resultsCountLimit = loadParameters.getResultsCount();

        List<Table> checkResultsList = loadCheckResultsPartitions(loadParameters, connectionName, physicalTableName, userDomainIdentity);
        List<Table> executionErrorsList = loadExecutionErrorsPartitions(loadParameters, connectionName, physicalTableName, userDomainIdentity);

        if (checkResultsList.isEmpty() && executionErrorsList.isEmpty()) {
            return new CheckResultsOverviewDataModel[0]; // empty array
        }

        List<Table> aggregatedPartitionResults = new ArrayList<>();
        aggregatedPartitionResults.addAll(checkResultsList);
        aggregatedPartitionResults.addAll(executionErrorsList);

        return makeCheckResultsOverviewDataModelsForContainer(rootChecksContainerSpec, aggregatedPartitionResults,
                loadParameters.getCheckName(), loadParameters.getCategory(), resultsCountLimit);
    }

    /**
     * Filters all table's data quality results by the target container, which is the type of checks (table/column), column name, and check type (i.e., daily monitoring).
     * Then, finds the most recent results for each data quality check that has any results.
     * @param rootChecksContainerSpec Root container for the data quality check, used to identify the type of checks (partitioned, monitoring, profiling), the time scale and possibly the column name.
     * @param aggregatedPartitionResults All results of the checks and execution errors for all partitions.
     * @param checkNameFilter Optional check name filter.
     * @param categoryNameFilter Optional category name filter.
     * @param resultsCountLimit Maximum number of results to return for each table.
     * @return Array of data quality results for each check.
     */
    public CheckResultsOverviewDataModel[] makeCheckResultsOverviewDataModelsForContainer(
            AbstractRootChecksContainerSpec rootChecksContainerSpec, 
            List<Table> aggregatedPartitionResults,
            String checkNameFilter,
            String categoryNameFilter,
            int resultsCountLimit) {
        Map<Long, CheckResultsOverviewDataModel> resultMap = new LinkedHashMap<>();

        for (Table partitionResults : aggregatedPartitionResults) {
            Table filteredTable = filterTableToRootChecksContainer(rootChecksContainerSpec, checkNameFilter, categoryNameFilter, partitionResults);
            if (filteredTable.isEmpty()) {
                continue;
            }

            int rowCount = filteredTable.rowCount();
            DateTimeColumn timePeriodColumn = filteredTable.dateTimeColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
            InstantColumn timePeriodUtcColumn = filteredTable.instantColumn(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
            InstantColumn executedAtColumn = filteredTable.instantColumn(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
            IntColumn severityColumn = TableColumnUtility.getOrAddIntColumn(filteredTable, CheckResultsColumnNames.SEVERITY_COLUMN_NAME, false);
            TextColumn dataGroupNameColumn = filteredTable.textColumn(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
            LongColumn checkHashColumn = filteredTable.longColumn(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
            TextColumn checkCategoryColumn = filteredTable.textColumn(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            TextColumn checkNameColumn = filteredTable.textColumn(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
            TextColumn tableComparisonColumn = filteredTable.textColumn(SensorReadoutsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);
            DoubleColumn actualValueColumn = filteredTable.doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
            for (int i = rowCount - 1; i >= 0 ; i--) {
                LocalDateTime timePeriod = timePeriodColumn.get(i);
                Instant timePeriodUtc = timePeriodUtcColumn.get(i);
                if (timePeriodUtc == null) {
                    timePeriodUtc = timePeriod.atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId().getRules().getOffset(timePeriod)).toInstant();
                }
                Instant executedAt = executedAtColumn.get(i);
                Integer severity = severityColumn != null ? severityColumn.get(i) : 4;  // when the severity column is missing, we are processing a partition from the execution error table, so the fake severity is 4 for execution errors
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
                        severity, actualValue, dataGroupName, resultsCountLimit);
            }
        }

        resultMap.values().forEach(m -> m.reverseLists());

        return resultMap.values().toArray(CheckResultsOverviewDataModel[]::new);
    }

    /**
     * Loads the most recent table profiling results for a table and its columns. Reads one most recent result of the profiling checks.
     * @param tableSpec Table specification for which we are loading the results.
     * @param userDomainIdentity User domain identity to identify the data domain.
     * @return Aggregated results for the most recent check result.
     */
    @Override
    public TableProfilingResults loadProfilingChecksResultsForTable(
            TableSpec tableSpec,
            UserDomainIdentity userDomainIdentity) {
        TableProfilingResults tableProfilingResults = new TableProfilingResults();
        HierarchyId tableHierarchyId = tableSpec.getHierarchyId();
        String connectionName = tableHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = tableHierarchyId.getPhysicalTableName();
        int resultsCountLimit = 1;

        CheckResultsOverviewParameters checkResultsOverviewParameters = new CheckResultsOverviewParameters();
        List<Table> checkResultsList = loadCheckResultsPartitions(checkResultsOverviewParameters, connectionName, physicalTableName, userDomainIdentity);
        if (checkResultsList.isEmpty()) {
            tableProfilingResults.setMissingProfilingChecksResults(true);
            return tableProfilingResults;
        }

        AbstractRootChecksContainerSpec tableProfilingChecksContainer = tableSpec.getTableCheckRootContainer(
                CheckType.profiling, null, false, true);

        CheckResultsOverviewDataModel[] checkResultsOverviewTableLevel = makeCheckResultsOverviewDataModelsForContainer(
                tableProfilingChecksContainer, checkResultsList, null, null, resultsCountLimit);

        DataAssetProfilingResults tableAssetProfilingResultsContainer = tableProfilingResults.getTableProfilingResults();
        tableAssetProfilingResultsContainer.importProfilingChecksResults(checkResultsOverviewTableLevel);

        for (ColumnSpec columnSpec : tableSpec.getColumns().values()) {
            AbstractRootChecksContainerSpec columnProfilingChecksContainer = columnSpec.getColumnCheckRootContainer(
                    CheckType.profiling, null, false, true);

            CheckResultsOverviewDataModel[] checkResultsOverviewColumnLevel = makeCheckResultsOverviewDataModelsForContainer(
                    columnProfilingChecksContainer, checkResultsList, null, null, resultsCountLimit);

            DataAssetProfilingResults columnAssetProfilingResultsContainer = tableProfilingResults.getColumnProfilingResults(columnSpec.getColumnName());
            columnAssetProfilingResultsContainer.importProfilingChecksResults(checkResultsOverviewColumnLevel);
        }

        return tableProfilingResults;
    }

    /**
     * Read the results of the most recent table comparison.
     * @param connectionName The connection name of the compared table.
     * @param physicalTableName Physical table name (schema and table) of the compared table.
     * @param checkType Check type.
     * @param timeScale Optional check scale (daily, monthly) for monitoring and partitioned checks.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Returns the summary information about the table comparison.
     */
    @Override
    public TableComparisonResultsModel readMostRecentTableComparisonResults(String connectionName,
                                                                            PhysicalTableName physicalTableName,
                                                                            CheckType checkType,
                                                                            CheckTimeScale timeScale,
                                                                            String tableComparisonConfigurationName,
                                                                            UserDomainIdentity userDomainIdentity) {
        TableComparisonResultsModel result = new TableComparisonResultsModel();
        CheckResultsOverviewParameters checkResultsLoadParameters = CheckResultsOverviewParameters.createForRecentMonths(2, 1);

        Table ruleResultsTable = loadAggregatedCheckResults(checkResultsLoadParameters, connectionName, physicalTableName, userDomainIdentity);
        Table errorsTable = loadAggregatedErrorsNormalizedToResults(checkResultsLoadParameters, connectionName, physicalTableName, userDomainIdentity);
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
     * @param userDomainIdentity      User identity with the data domain.
     * @return Detailed model of the check results.
     */
    @Override
    public CheckResultsListModel[] readCheckStatusesDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                             CheckResultsDetailedFilterParameters loadParameters,
                                                             UserDomainIdentity userDomainIdentity) {
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        List<Table> loadedPartitionTables = loadRecentRuleResults(loadParameters, connectionName, physicalTableName, userDomainIdentity);

        if (loadParameters.getLoadMode() == CheckResultsDetailedLoadMode.most_recent_per_group) {
            return extractMostRecentCheckResultsForEachDataGroup(rootChecksContainerSpec, loadParameters, loadedPartitionTables);
        }

        // default mode - results for one data group only
        return extractCheckResultsFromTheMostRecentDataGroup(rootChecksContainerSpec, loadParameters, loadedPartitionTables);
    }

    /**
     * Extract check results for each matching check, collecting results only from the first data group that had the most recent values.
     * Results from other groups for the same checks are ignored. This method returns data that is shown in the check results panels
     * on the check result's user interface.
     * @param rootChecksContainerSpec Root check container to identify the type of checks.
     * @param loadParameters Filters.
     * @param loadedPartitionTables A list of partitions loaded for that time period, from the newest to the oldest partition.
     * @return An array of check results to be returned to the user.
     */
    protected CheckResultsListModel[] extractCheckResultsFromTheMostRecentDataGroup(
            AbstractRootChecksContainerSpec rootChecksContainerSpec,
            CheckResultsDetailedFilterParameters loadParameters,
            List<Table> loadedPartitionTables) {
        Map<Long, CheckResultsListModel> resultMap = new LinkedHashMap<>();
        for (Table checkResultsTable : loadedPartitionTables) {
            if (checkResultsTable == null || checkResultsTable.isEmpty()) {
                continue;
            }

            Table filteredTable = filterTableToRootChecksContainerAndFilterParameters(rootChecksContainerSpec, checkResultsTable, loadParameters);
            if (filteredTable.isEmpty()) {
                continue;
            }
            Table filteredTableByDataGroup = filteredTable;
            if (!Strings.isNullOrEmpty(loadParameters.getDataGroupName())) {
                TextColumn dataGroupNameFilteredColumn = filteredTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
                filteredTableByDataGroup = filteredTable.where(dataGroupNameFilteredColumn.isEqualTo(loadParameters.getDataGroupName()));
            }

            if (filteredTableByDataGroup.isEmpty()) {
                continue;
            }

            Table sortedTable = filteredTableByDataGroup.sortDescendingOn(
                    SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                    SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, // then the most recent reading (for partitioned checks) when many partitions were captured
                    CheckResultsColumnNames.SEVERITY_COLUMN_NAME); // second on the highest severity first on that time period
            CheckResultsNormalizedResult checkResultsNormalizedResult = new CheckResultsNormalizedResult(sortedTable, false);

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
                    singleModel = createSingleCheckResultDetailedModel(checkResultsNormalizedResult, rowIndex);
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
                    singleModel = createSingleCheckResultDetailedModel(checkResultsNormalizedResult, rowIndex);
                }

                checkResultsListModel.getCheckResultEntries().add(singleModel);
            }
        }

        return resultMap.values().toArray(CheckResultsListModel[]::new);
    }

    /**
     * Extract check results, returning results from all data groups for matching check, but only the most recent result for
     * each data group is returned.
     * @param rootChecksContainerSpec Root check container to identify the type of checks.
     * @param loadParameters Filters.
     * @param loadedPartitionTables A list of partitions loaded for that time period, from the newest to the oldest partition.
     * @return An array of check results to be returned to the user.
     */
    protected CheckResultsListModel[] extractMostRecentCheckResultsForEachDataGroup(
            AbstractRootChecksContainerSpec rootChecksContainerSpec,
            CheckResultsDetailedFilterParameters loadParameters,
            List<Table> loadedPartitionTables) {
        Map<Long, Map<String, CheckResultsListModel>> resultMap = new LinkedHashMap<>();
        for (Table checkResultsTable : loadedPartitionTables) {
            if (checkResultsTable == null || checkResultsTable.isEmpty()) {
                continue;
            }

            Table filteredTable = filterTableToRootChecksContainerAndFilterParameters(rootChecksContainerSpec, checkResultsTable, loadParameters);
            if (filteredTable.isEmpty()) {
                continue;
            }
            Table filteredTableByDataGroup = filteredTable;
            if (!Strings.isNullOrEmpty(loadParameters.getDataGroupName())) {
                TextColumn dataGroupNameFilteredColumn = filteredTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
                filteredTableByDataGroup = filteredTable.where(dataGroupNameFilteredColumn.isEqualTo(loadParameters.getDataGroupName()));
            }

            if (filteredTableByDataGroup.isEmpty()) {
                continue;
            }

            Table sortedTable = filteredTableByDataGroup.sortDescendingOn(
                    SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                    SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, // then the most recent reading (for partitioned checks) when many partitions were captured
                    CheckResultsColumnNames.SEVERITY_COLUMN_NAME); // second on the highest severity first on that time period
            CheckResultsNormalizedResult checkResultsNormalizedResult = new CheckResultsNormalizedResult(sortedTable, false);

            LongColumn checkHashColumn = sortedTable.longColumn(CheckResultsColumnNames.CHECK_HASH_COLUMN_NAME);
            TextColumn allDataGroupColumn = sortedTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

            int rowCount = sortedTable.rowCount();
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Long checkHash = checkHashColumn.get(rowIndex);
                String dataGroupNameForCheck = allDataGroupColumn.get(rowIndex);
                Map<String, CheckResultsListModel> checkResultsPerDataGroup = resultMap.get(checkHash);
                if (checkResultsPerDataGroup == null) {
                    checkResultsPerDataGroup = new LinkedHashMap<>();
                    resultMap.put(checkHash, checkResultsPerDataGroup);
                } else {
                    if (checkResultsPerDataGroup.size() >= loadParameters.getMaxResultsPerCheck()) {
                        continue; // enough results loaded
                    }
                }

                CheckResultsListModel checkResultsListModel = checkResultsPerDataGroup.get(dataGroupNameForCheck);

                if (checkResultsListModel == null) {
                    CheckResultEntryModel singleModel = createSingleCheckResultDetailedModel(checkResultsNormalizedResult, rowIndex);
                    checkResultsListModel = new CheckResultsListModel();
                    checkResultsListModel.setCheckCategory(singleModel.getCheckCategory());
                    checkResultsListModel.setCheckName(singleModel.getCheckName());
                    checkResultsListModel.setCheckHash(singleModel.getCheckHash());
                    checkResultsListModel.setCheckType(singleModel.getCheckType());
                    checkResultsListModel.setCheckDisplayName(singleModel.getCheckDisplayName());
                    checkResultsListModel.setDataGroup(dataGroupNameForCheck);
                    checkResultsListModel.getCheckResultEntries().add(singleModel);
                    checkResultsPerDataGroup.put(dataGroupNameForCheck, checkResultsListModel);
                }
            }
        }

        return resultMap.values()
                        .stream()
                        .flatMap(groups -> groups.values().stream())
                        .toArray(CheckResultsListModel[]::new);
    }

    /**
     * Creates a data model that returns the result of a single check.
     * @param checkNormalizedResults A table wrapper for check results.
     * @param rowIndex Row index.
     * @return Model with all information for a single check result.
     */
    @NotNull
    protected CheckResultEntryModel createSingleCheckResultDetailedModel(CheckResultsNormalizedResult checkNormalizedResults, int rowIndex) {
        String id = checkNormalizedResults.getIdColumn().getString(rowIndex);
        Double actualValue = checkNormalizedResults.getActualValueColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getActualValueColumn().getDouble(rowIndex);
        Double expectedValue = checkNormalizedResults.getExpectedValueColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getExpectedValueColumn().getDouble(rowIndex);
        Double warningLowerBound = checkNormalizedResults.getWarningLowerBoundColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getWarningLowerBoundColumn().getDouble(rowIndex);
        Double warningUpperBound = checkNormalizedResults.getWarningUpperBoundColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getWarningUpperBoundColumn().getDouble(rowIndex);
        Double errorLowerBound = checkNormalizedResults.getErrorLowerBoundColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getErrorLowerBoundColumn().getDouble(rowIndex);
        Double errorUpperBound = checkNormalizedResults.getErrorUpperBoundColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getErrorUpperBoundColumn().getDouble(rowIndex);
        Double fatalLowerBound = checkNormalizedResults.getFatalLowerBoundColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getFatalLowerBoundColumn().getDouble(rowIndex);
        Double fatalUpperBound = checkNormalizedResults.getFatalUpperBoundColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getFatalUpperBoundColumn().getDouble(rowIndex);
        Integer severity = checkNormalizedResults.getSeverityColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getSeverityColumn().getInt(rowIndex);

        String checkCategory = checkNormalizedResults.getCheckCategoryColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getCheckCategoryColumn().getString(rowIndex);
        String checkDisplayName = checkNormalizedResults.getCheckDisplayNameColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getCheckDisplayNameColumn().getString(rowIndex);
        Long checkHash = checkNormalizedResults.getCheckHashColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getCheckHashColumn().getLong(rowIndex);
        String checkName = checkNormalizedResults.getCheckNameColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getCheckNameColumn().getString(rowIndex);
        String checkTypeString = checkNormalizedResults.getCheckTypeColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getCheckTypeColumn().getString(rowIndex);

        String columnName = checkNormalizedResults.getColumnNameColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getColumnNameColumn().getString(rowIndex);
        String dataGroupName = checkNormalizedResults.getDataGroupNameColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getDataGroupNameColumn().getString(rowIndex);

        Integer durationMs = checkNormalizedResults.getDurationMsColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getDurationMsColumn().getInt(rowIndex);
        Instant executedAt = checkNormalizedResults.getExecutedAtColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getExecutedAtColumn().get(rowIndex);
        String timeGradientString = checkNormalizedResults.getTimeGradientColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getTimeGradientColumn().getString(rowIndex);
        LocalDateTime timePeriod = checkNormalizedResults.getTimePeriodColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getTimePeriodColumn().get(rowIndex);

        Boolean includeInKpi = checkNormalizedResults.getIncludeInKpiColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getIncludeInKpiColumn().get(rowIndex);
        Boolean includeInSla = checkNormalizedResults.getIncludeInSlaColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getIncludeInSlaColumn().get(rowIndex);
        String provider = checkNormalizedResults.getProviderColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getProviderColumn().getString(rowIndex);
        String qualityDimension = checkNormalizedResults.getQualityDimensionColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getQualityDimensionColumn().getString(rowIndex);
        String sensorName = checkNormalizedResults.getSensorNameColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getSensorNameColumn().getString(rowIndex);

        String tableComparison = checkNormalizedResults.getTableComparisonNameColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getTableComparisonNameColumn().getString(rowIndex);
        Instant updatedAt = checkNormalizedResults.getUpdatedAtColumn().isMissing(rowIndex) ? null : checkNormalizedResults.getUpdatedAtColumn().get(rowIndex);

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
            setCheckType(CheckType.fromString(checkTypeString));
            setCheckDisplayName(checkDisplayName);

            setColumnName(columnName);
            setDataGroup(dataGroupName);

            setDurationMs(durationMs);
            setExecutedAt(executedAt);
            setTimeGradient(TimePeriodGradient.fromString(timeGradientString));
            setTimePeriod(timePeriod);

            setIncludeInKpi(includeInKpi);
            setIncludeInSla(includeInSla);
            setProvider(provider);
            setQualityDimension(qualityDimension);
            setSensorName(sensorName);
            setTableComparison(tableComparison);

            setUpdatedAt(updatedAt);
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
     * @param userDomainIdentity User identity with the data domain.
     * @return An array of matching check results.
     */
    @Override
    public CheckResultEntryModel[] loadCheckResultsRelatedToIncident(String connectionName,
                                                                     PhysicalTableName physicalTableName,
                                                                     long incidentHash,
                                                                     Instant firstSeen,
                                                                     Instant incidentUntil,
                                                                     int minSeverity,
                                                                     CheckResultListFilterParameters filterParameters,
                                                                     UserDomainIdentity userDomainIdentity) {
        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        LocalDate startDay = firstSeen.atZone(defaultTimeZoneId).toLocalDate()
                .minus(this.dqoIncidentsConfigurationProperties.getPartitionedChecksTimeWindowDays(), ChronoUnit.DAYS);

        if (filterParameters.getDays() != null) {
            LocalDate earliestRequestedDate = Instant.now().atZone(defaultTimeZoneId).truncatedTo(ChronoUnit.DAYS)
                    .minus(filterParameters.getDays(), ChronoUnit.DAYS).toLocalDate();
            if (earliestRequestedDate.isAfter(startDay)) {
                startDay = earliestRequestedDate;
            }
        }

        LocalDate endMonth = incidentUntil.plus(12L, ChronoUnit.HOURS).atZone(defaultTimeZoneId).toLocalDate();
        if (!checkResultsSnapshot.ensureMonthsAreLoaded(startDay, endMonth)) {
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

        if (startTimestamp.isBefore(firstSeen)) {
            startTimestamp = firstSeen;
        }

        List<CheckResultEntryModel> resultsList = new ArrayList<>();

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = checkResultsSnapshot.getLoadedMonthlyPartitions();
        for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitionEntry : loadedMonthlyPartitions.entrySet()) {
            Table partitionData = loadedPartitionEntry.getValue().getData();
            if (partitionData == null || partitionData.rowCount() == 0) {
                continue;
            }

            CheckResultsNormalizedResult checkResultsNormalizedResult = new CheckResultsNormalizedResult(partitionData, false);

            Selection minSeveritySelection = checkResultsNormalizedResult.getSeverityColumn().isGreaterThanOrEqualTo(minSeverity);
            Selection notProfilingSelection = checkResultsNormalizedResult.getCheckTypeColumn().isNotEqualTo(CheckType.profiling.getDisplayName());
            InstantColumn partitionExecutedAtColumn = checkResultsNormalizedResult.getExecutedAtColumn();
            Selection issuesInTimeRange = partitionExecutedAtColumn.isBetweenIncluding(
                    PackedInstant.pack(startTimestamp), PackedInstant.pack(endTimestamp));
            Selection incidentHashSelection = checkResultsNormalizedResult.getIncidentHashColumn().isIn(incidentHash);

            Selection selectionOfMatchingIssues = minSeveritySelection
                    .and(notProfilingSelection)
                    .and(issuesInTimeRange)
                    .and(incidentHashSelection);
            if (!Strings.isNullOrEmpty(filterParameters.getColumn())) {
                TextColumn partitionColumnNameColumn = checkResultsNormalizedResult.getColumnNameColumn();
                if (Objects.equals(CheckResultsDataService.COLUMN_NAME_TABLE_CHECKS_PLACEHOLDER, filterParameters.getColumn())) {
                    selectionOfMatchingIssues = selectionOfMatchingIssues.and(partitionColumnNameColumn.isMissing()); // table level sensors
                } else {
                    selectionOfMatchingIssues = selectionOfMatchingIssues.and(partitionColumnNameColumn.isEqualTo(filterParameters.getColumn()));
                }
            }

            if (!Strings.isNullOrEmpty(filterParameters.getCheck())) {
                TextColumn partitionCheckNameColumn =checkResultsNormalizedResult.getCheckNameColumn();
                selectionOfMatchingIssues = selectionOfMatchingIssues.and(partitionCheckNameColumn.isEqualTo(filterParameters.getCheck()));
            }

            if (selectionOfMatchingIssues.size() == 0) {
                continue;
            }

            for (Integer rowIndex : selectionOfMatchingIssues) {
                CheckResultEntryModel singleCheckResultDetailedModel = createSingleCheckResultDetailedModel(checkResultsNormalizedResult, rowIndex);

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
     * @param executedAtSince         The timestamp when the incident was first seen.
     * @param executedAtUntil     The timestamp when the incident was closed or expired, returns check results up to this timestamp.
     * @param minSeverity       Minimum check issue severity that is returned.
     * @param filterParameters  Optional filter to limit the issues included in the histogram.
     * @param userDomainIdentity User identity with the data domain.
     * @return Daily histogram of failed data quality checks.
     */
    @Override
    public IssueHistogramModel buildDailyIssuesHistogram(String connectionName,
                                                         PhysicalTableName physicalTableName,
                                                         Long incidentHash,
                                                         Instant executedAtSince,
                                                         Instant executedAtUntil,
                                                         int minSeverity,
                                                         HistogramFilterParameters filterParameters,
                                                         UserDomainIdentity userDomainIdentity) {
        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();

        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        LocalDate startDay = executedAtSince.atZone(defaultTimeZoneId).toLocalDate()
                .minus(this.dqoIncidentsConfigurationProperties.getPartitionedChecksTimeWindowDays(), ChronoUnit.DAYS);

        if (filterParameters.getDays() != null) {
            LocalDate earliestRequestedDate = Instant.now().atZone(defaultTimeZoneId).truncatedTo(ChronoUnit.DAYS)
                    .minus(filterParameters.getDays(), ChronoUnit.DAYS).toLocalDate();
            if (earliestRequestedDate.isAfter(startDay)) {
                startDay = earliestRequestedDate;
            }
        }

        if (executedAtUntil == null) {
            executedAtUntil = Instant.now().plus(1L, ChronoUnit.DAYS);
        }
        
        LocalDate endMonth = executedAtUntil.plus(12L, ChronoUnit.HOURS).atZone(defaultTimeZoneId).toLocalDate();
        if (!checkResultsSnapshot.ensureMonthsAreLoaded(startDay, endMonth)) {
            return new IssueHistogramModel();
        }

        Instant startTimestamp = executedAtSince;
        if (filterParameters.getDays() != null) {
            startTimestamp = Instant.now().atZone(defaultTimeZoneId).toLocalDate()
                    .minus(filterParameters.getDays(), ChronoUnit.DAYS).atTime(0, 0).atZone(defaultTimeZoneId)
                    .toInstant();

            if (startTimestamp.isBefore(executedAtSince)) {
                startTimestamp = executedAtSince;
            }
        }

        IssueHistogramModel histogramModel = new IssueHistogramModel();

        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = checkResultsSnapshot.getLoadedMonthlyPartitions();
        for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> loadedPartitionEntry : loadedMonthlyPartitions.entrySet()) {
            Table partitionData = loadedPartitionEntry.getValue().getData();
            if (partitionData == null || partitionData.rowCount() == 0) {
                continue;
            }

            CheckResultsNormalizedResult checkResultsNormalizedResult = new CheckResultsNormalizedResult(partitionData, false);

            IntColumn severityColumn = checkResultsNormalizedResult.getSeverityColumn();
            Selection minSeveritySelection = severityColumn.isGreaterThanOrEqualTo(minSeverity);

            TextColumn checkTypeColumn = checkResultsNormalizedResult.getCheckTypeColumn();
            Selection checkTypeSelection =
                    filterParameters.getCheckType() != null ? checkTypeColumn.isEqualTo(filterParameters.getCheckType().getDisplayName()) :
                    checkTypeColumn.isNotEqualTo(CheckType.profiling.getDisplayName()); // by default, we are snowing monitoring and partition checks, excluding profiling checks, but user can pick a different check type

            InstantColumn executedAtColumn = checkResultsNormalizedResult.getExecutedAtColumn();
            DateTimeColumn timePeriodColumn = checkResultsNormalizedResult.getTimePeriodColumn();

            Selection issuesInTimeRange = executedAtColumn.isBetweenIncluding(
                    PackedInstant.pack(startTimestamp), PackedInstant.pack(executedAtUntil));
            Selection incidentHashSelection = incidentHash != null ? checkResultsNormalizedResult.getIncidentHashColumn().isIn(incidentHash) : null;

            Selection selectionOfMatchingIssues = minSeveritySelection
                    .and(checkTypeSelection)
                    .and(issuesInTimeRange);
            if (incidentHashSelection != null) {
                selectionOfMatchingIssues = selectionOfMatchingIssues.and(incidentHashSelection);
            }

            if (selectionOfMatchingIssues.size() == 0) {
                continue;
            }

            TextColumn columnNameColumn = checkResultsNormalizedResult.getColumnNameColumn();
            TextColumn checkNameColumn = checkResultsNormalizedResult.getCheckNameColumn();
            TextColumn checkTimeGradientColumn = checkResultsNormalizedResult.getTimeGradientColumn();

            for (Integer rowIndex : selectionOfMatchingIssues) {
                if (!Strings.isNullOrEmpty(filterParameters.getFilter())) {
                    CheckResultEntryModel singleCheckResultDetailedModel = createSingleCheckResultDetailedModel(checkResultsNormalizedResult, rowIndex);
                    if (!singleCheckResultDetailedModel.matchesFilter(filterParameters.getFilter())) {
                        continue;
                    }
                }

                Integer severity = severityColumn.get(rowIndex);
                LocalDateTime timePeriod = timePeriodColumn.get(rowIndex);
                LocalDate timePeriodDate = timePeriod.toLocalDate();
                String columnName = columnNameColumn.get(rowIndex);
                String checkName = checkNameColumn.get(rowIndex);
                String checkTypeString = checkTypeColumn.get(rowIndex);
                CheckType checkType = Strings.isNullOrEmpty(checkTypeString) ? null : Enum.valueOf(CheckType.class, checkTypeString);
                String checkTimeGradientString = checkTimeGradientColumn.isMissing(rowIndex) ? null : checkTimeGradientColumn.get(rowIndex);
                TimePeriodGradient timePeriodGradient = Strings.isNullOrEmpty(checkTimeGradientString) ? null : Enum.valueOf(TimePeriodGradient.class, checkTimeGradientString);
                if (columnName == null) {
                    columnName = CheckResultsDataService.COLUMN_NAME_TABLE_CHECKS_PLACEHOLDER;
                }

                boolean dateMatch = filterParameters.getDate() == null || Objects.equals(filterParameters.getDate(), timePeriodDate);
                boolean columnMatch = Strings.isNullOrEmpty(filterParameters.getColumn()) || Objects.equals(filterParameters.getColumn(), columnName);
                boolean checkMatch = Strings.isNullOrEmpty(filterParameters.getCheck()) || Objects.equals(filterParameters.getCheck(), checkName);

                if (columnMatch && checkMatch) {
                    histogramModel.incrementSeverityForDay(timePeriodDate, severity);
                }

                if (dateMatch && checkMatch) {
                    histogramModel.incrementCountForColumn(columnName);
                }

                if (dateMatch && columnMatch) {
                    histogramModel.incrementCountForCheck(checkName);
                }

                histogramModel.markCheckType(checkType, timePeriodGradient);
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
     * @param userDomainIdentity User identity with the data domain.
     * @return The table status.
     */
    @Override
    public TableCurrentDataQualityStatusModel analyzeTableMostRecentQualityStatus(
            TableCurrentDataQualityStatusFilterParameters tableCurrentDataQualityStatusFilterParameters,
            UserDomainIdentity userDomainIdentity) {
        String connectionName = tableCurrentDataQualityStatusFilterParameters.getConnectionName();
        PhysicalTableName physicalTableName = tableCurrentDataQualityStatusFilterParameters.getPhysicalTableName();

        TableCurrentDataQualityStatusModel statusModel = new TableCurrentDataQualityStatusModel();
        statusModel.setDataDomain(userDomainIdentity.getDataDomainCloud());
        statusModel.setConnectionName(connectionName);
        statusModel.setSchemaName(physicalTableName.getSchemaName());
        statusModel.setTableName(physicalTableName.getTableName());

        int lastMonths = tableCurrentDataQualityStatusFilterParameters.getLastMonths() == null ?
                TableCurrentDataQualityStatusFilterParameters.DEFAULT_PREVIOUS_MONTHS :
                tableCurrentDataQualityStatusFilterParameters.getLastMonths();
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

        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        checkResultsSnapshot.ensureMonthsAreLoaded(checkResultsLoadParameters.getStartMonth(), checkResultsLoadParameters.getEndMonth());

        if (checkResultsSnapshot.getLoadedMonthlyPartitions() != null && !checkResultsSnapshot.getLoadedMonthlyPartitions().isEmpty()) {
            List<LoadedMonthlyPartition> partitionsFromNewest = checkResultsSnapshot.getLoadedMonthlyPartitions()
                    .values()
                    .stream()
                    .sorted(Comparator.comparing((LoadedMonthlyPartition partition) -> partition.getPartitionId()).reversed())
                    .collect(Collectors.toList());

            for (LoadedMonthlyPartition loadedMonthlyPartition : partitionsFromNewest) {
                if (loadedMonthlyPartition.getData() == null || loadedMonthlyPartition.getData().rowCount() == 0) {
                    continue;
                }

                Table filteredTable = filterTableOnFilterParameters(loadedMonthlyPartition.getData(), tableCurrentDataQualityStatusFilterParameters);
                Table filteredTableByDataGroup = filteredTable;
                if (!Strings.isNullOrEmpty(tableCurrentDataQualityStatusFilterParameters.getDataGroup())) {
                    TextColumn dataGroupNameFilteredColumn = filteredTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
                    filteredTableByDataGroup = filteredTable.where(dataGroupNameFilteredColumn.isEqualTo(tableCurrentDataQualityStatusFilterParameters.getDataGroup()));
                }

                calculateStatus(filteredTableByDataGroup, statusModel);

                if (statusModel.getTotalRowCount() == null) {
                    statusModel.setTotalRowCount(extractMostRecentRowCount(filteredTable));
                }

                if (statusModel.getDataFreshnessDelayDays() == null) {
                    statusModel.setDataFreshnessDelayDays(extractMostRecentDataFreshnessDelay(filteredTable));
                }
            }
        }

        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorsColumnNames.COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        errorsSnapshot.ensureMonthsAreLoaded(checkResultsLoadParameters.getStartMonth(), checkResultsLoadParameters.getEndMonth());

        if (errorsSnapshot.getLoadedMonthlyPartitions() != null && !errorsSnapshot.getLoadedMonthlyPartitions().isEmpty()) {
            List<LoadedMonthlyPartition> partitionsFromNewest = errorsSnapshot.getLoadedMonthlyPartitions()
                    .values()
                    .stream()
                    .sorted(Comparator.comparing((LoadedMonthlyPartition partition) -> partition.getPartitionId()).reversed())
                    .collect(Collectors.toList());

            for (LoadedMonthlyPartition loadedMonthlyPartition : partitionsFromNewest) {
                if (loadedMonthlyPartition.getData() == null || loadedMonthlyPartition.getData().rowCount() == 0) {
                    continue;
                }

                Table filteredTable = filterTableOnFilterParameters(loadedMonthlyPartition.getData(), tableCurrentDataQualityStatusFilterParameters);
                Table filteredTableByDataGroup = filteredTable;
                if (!Strings.isNullOrEmpty(tableCurrentDataQualityStatusFilterParameters.getDataGroup())) {
                    TextColumn dataGroupNameFilteredColumn = filteredTable.textColumn(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
                    filteredTableByDataGroup = filteredTable.where(dataGroupNameFilteredColumn.isEqualTo(tableCurrentDataQualityStatusFilterParameters.getDataGroup()));
                }

                calculateStatus(filteredTableByDataGroup, statusModel);
            }
        }

        return statusModel;
    }

    /**
     * Finds the most recent row count in the table.
     * @param checkResultsTable Check results table.
     * @return Most recent row count returned from any check containing the total row count.
     */
    private Long extractMostRecentRowCount(Table checkResultsTable) {
        InstantColumn executedAtColumn = checkResultsTable.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
        TextColumn checkNameColumn = checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
        DoubleColumn actualValueColumn = checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        String[] rowCountCheckNames = new String[] { "daily_row_count", "daily_row_count_anomaly", "profile_row_count", "profile_row_count_anomaly" };
        Instant mostRecentExecutedAt = null;
        Long totalRowCount = null;

        for (String checkName : rowCountCheckNames) {
            Selection checkResultColumn = checkNameColumn.isEqualTo(checkName);
            int[] checkResultsRowIndexes = checkResultColumn.toArray();

            for (int i = checkResultsRowIndexes.length - 1; i >= 0 ; i--) {
                int rowIndex = checkResultsRowIndexes[i];
                if (actualValueColumn.isMissing(rowIndex)) {
                    continue;
                }

                Instant executedAt = executedAtColumn.get(rowIndex);
                long actualValue = (long)actualValueColumn.getDouble(rowIndex);

                if (mostRecentExecutedAt != null && executedAt.isAfter(mostRecentExecutedAt)) {
                    mostRecentExecutedAt = executedAt;
                    totalRowCount = null; // the results are in mixed order, we found more recent results
                }

                if (mostRecentExecutedAt == null || mostRecentExecutedAt.equals(executedAt)) {
                    if (totalRowCount == null) {
                        totalRowCount = actualValue;
                    } else {
                        totalRowCount = totalRowCount + actualValue; // add up all values in case that the table is analyzed by data groups and we have to sum values up
                    }

                    mostRecentExecutedAt = executedAt;
                }
                else {
                    break; // older results
                }
            }

            if (totalRowCount != null) {
                break;
            }
        }

        return totalRowCount;
    }

    /**
     * Finds the most recent data freshness delay in the table.
     * @param checkResultsTable Check results table.
     * @return Most recent data freshness returned from any check containing the total row count.
     */
    private Double extractMostRecentDataFreshnessDelay(Table checkResultsTable) {
        InstantColumn executedAtColumn = checkResultsTable.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
        TextColumn checkNameColumn = checkResultsTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
        DoubleColumn actualValueColumn = checkResultsTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        String[] rowCountCheckNames = new String[] { "daily_data_freshness", "daily_data_freshness_anomaly", "profile_data_freshness", "profile_data_freshness_anomaly" };
        Instant mostRecentExecutedAt = null;
        Double mostRecentFreshnessDelay = null;

        for (String checkName : rowCountCheckNames) {
            Selection checkResultColumn = checkNameColumn.isEqualTo(checkName);
            int[] checkResultsRowIndexes = checkResultColumn.toArray();

            for (int i = checkResultsRowIndexes.length - 1; i >= 0 ; i--) {
                int rowIndex = checkResultsRowIndexes[i];
                if (actualValueColumn.isMissing(rowIndex)) {
                    continue;
                }

                Instant executedAt = executedAtColumn.get(rowIndex);
                double actualValue = actualValueColumn.getDouble(rowIndex);

                if (mostRecentExecutedAt != null && executedAt.isAfter(mostRecentExecutedAt)) {
                    mostRecentExecutedAt = executedAt;
                }

                if (mostRecentExecutedAt == null || mostRecentExecutedAt.equals(executedAt)) {
                    if (mostRecentFreshnessDelay == null) {
                        mostRecentFreshnessDelay = actualValue;
                    } else {
                        if (actualValue < mostRecentFreshnessDelay) {
                            mostRecentFreshnessDelay = actualValue; // a different data group has most recent data
                        }
                    }

                    mostRecentExecutedAt = executedAt;
                }
                else {
                    break; // older results
                }
            }
        }

        return mostRecentFreshnessDelay;
    }

    /**
     * Calculates status for the table. Completes the TableDataQualityStatusModel with total severity data.
     * @param sourceTable Source table to be filtered.
     * @param tableStatusModel Target current table status model to update and fill with the status.
     * @return Complete TableDataQualityStatusModel
     */
    protected TableCurrentDataQualityStatusModel calculateStatus(Table sourceTable, TableCurrentDataQualityStatusModel tableStatusModel) {
        InstantColumn executedAtColumn = sourceTable.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
        IntColumn severityColumn = (IntColumn)TableColumnUtility.findColumn(sourceTable, CheckResultsColumnNames.SEVERITY_COLUMN_NAME); // when there is no severity column, it is the "errors" table and the severity is 4 as an execution error
        TextColumn checkNameColumn = sourceTable.textColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
        TextColumn checkCategoryColumn = sourceTable.textColumn(CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
        TextColumn qualityDimensionColumn = sourceTable.textColumn(CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
        TextColumn columnNameColumn = sourceTable.textColumn(CheckResultsColumnNames.COLUMN_NAME_COLUMN_NAME);
        TextColumn checkTypeColumn = sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME);
        TextColumn timeGradientColumn = sourceTable.textColumn(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME);

        int rowCount = sourceTable.rowCount();

        for (int i = 0; i < rowCount; i++) {
            Integer severity = severityColumn == null ? 4 : (severityColumn.isMissing(i) ? null : severityColumn.get(i));
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
            if (columnName != null && columnName.isEmpty()) {
                columnName = null;
            }
            CurrentDataQualityStatusHolder currentStatusHolder;
            ColumnCurrentDataQualityStatusModel columnCurrentDataQualityStatusModel = null;

            if (columnName == null) {
                // table level check
                currentStatusHolder = tableStatusModel;
            } else {
                // column level check
                columnCurrentDataQualityStatusModel = tableStatusModel.getColumns().get(columnName);
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
                String checkCategory = checkCategoryColumn.get(i);
                String qualityDimension = qualityDimensionColumn.get(i);
                String checkTypeString = checkTypeColumn.get(i);
                String checkTimeGradientString = timeGradientColumn.get(i);

                TimePeriodGradient timePeriodGradient = Strings.isNullOrEmpty(checkTimeGradientString) ? null :
                        Enum.valueOf(TimePeriodGradient.class, checkTimeGradientString);
                CheckTimeScale checkTimeScale = timePeriodGradient != null ? timePeriodGradient.toTimeScale() : null;

                checkCurrentStatusModel = new CheckCurrentDataQualityStatusModel();
                checkCurrentStatusModel.setCategory(Strings.isNullOrEmpty(checkCategory) ? null :checkCategory);
                checkCurrentStatusModel.setQualityDimension(Strings.isNullOrEmpty(qualityDimension) ? null : qualityDimension);
                checkCurrentStatusModel.setCheckType(Strings.isNullOrEmpty(checkTypeString) ? null : Enum.valueOf(CheckType.class, checkTypeString));
                checkCurrentStatusModel.setTimeScale(checkTimeScale);
                checkCurrentStatusModel.setLastExecutedAt(executedAt);
                checkCurrentStatusModel.setHighestHistoricalSeverity(RuleSeverityLevel.fromSeverityLevel(severity));
                checkCurrentStatusModel.setCurrentSeverity(CheckResultStatus.fromSeverity(severity));
                checkCurrentStatusModel.setColumnName(columnName);
                currentStatusHolder.getChecks().put(checkName, checkCurrentStatusModel);
            } else {
                if (severity != 4 && (checkCurrentStatusModel.getHighestHistoricalSeverity() == null ||
                        severity > checkCurrentStatusModel.getHighestHistoricalSeverity().getSeverity())) {
                    checkCurrentStatusModel.setHighestHistoricalSeverity(RuleSeverityLevel.fromSeverityLevel(severity));
                }

                if (checkCurrentStatusModel.getLastExecutedAt() != null && executedAt != null) {
                    if (checkCurrentStatusModel.getLastExecutedAt().equals(executedAt)) {
                        // groupings or partitioned checks, the whole batch of check results will have the same executed at timestamp

                        if (severity > checkCurrentStatusModel.getCurrentSeverity().getSeverity()) {
                            checkCurrentStatusModel.setCurrentSeverity(CheckResultStatus.fromSeverity(severity));
                        }
                    } else if (executedAt.isAfter(checkCurrentStatusModel.getLastExecutedAt())) {
                        checkCurrentStatusModel.setLastExecutedAt(executedAt);
                        checkCurrentStatusModel.setCurrentSeverity(CheckResultStatus.fromSeverity(severity));
                    }
                }
            }

            checkCurrentStatusModel.incrementTotalIssueCount(severity);
        }

        tableStatusModel.calculateHighestCurrentAndHistoricSeverity();
        tableStatusModel.calculateDataQualityKpiScore();
        tableStatusModel.calculateStatusesForDataQualityDimensions();

        return tableStatusModel;
    }

    /**
     * Increments the count of issues with the given severity level in a current DQ status holder, status holders are table and column levels.
     * @param dataQualityStatusHolder Target data quality status holder to increment.
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
     * @param checkName Optional filter for the check name.
     * @param categoryName Optional filter for the category name.
     * @param sourceTable Source table to be filtered.
     * @return Filtered table.
     */
    protected Table filterTableToRootChecksContainer(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                     String checkName,
                                                     String categoryName,
                                                     Table sourceTable) {
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        Selection rowSelection = sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (!Strings.isNullOrEmpty(checkName)) {
            TextColumn checkNameColumn = sourceTable.textColumn(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
            rowSelection = rowSelection.and(checkNameColumn.isEqualTo(checkName));
        }

        if (!Strings.isNullOrEmpty(categoryName)) {
            TextColumn categoryColumn = sourceTable.textColumn(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            rowSelection = rowSelection.and(categoryColumn.isEqualTo(categoryName));
        }

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

        if (!filterParameters.isProfiling()) {
            rowSelection = rowSelection.and(sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME)
                    .isNotEqualTo(CheckType.profiling.getDisplayName()));
        }

        if (!filterParameters.isMonitoring()) {
            rowSelection = rowSelection.and(sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME)
                    .isNotEqualTo(CheckType.monitoring.getDisplayName()));
        }

        if (!filterParameters.isPartitioned()) {
            rowSelection = rowSelection.and(sourceTable.textColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME)
                    .isNotEqualTo(CheckType.partitioned.getDisplayName()));
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
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity with the  data domain.
     * @return List of partition results with execution errors for a table.
     */
    protected List<Table> loadExecutionErrorsPartitions(CheckResultsOverviewParameters loadParameters,
                                                        String connectionName,
                                                        PhysicalTableName physicalTableName,
                                                        UserDomainIdentity userDomainIdentity) {
        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorsColumnNames.COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        errorsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        List<Table> partitionTables = new ArrayList<>();
        Collection<LoadedMonthlyPartition> loadedMonthlyPartitions = errorsSnapshot.getLoadedMonthlyPartitions().values();
        for (LoadedMonthlyPartition loadedMonthlyPartition : loadedMonthlyPartitions) {
            if (loadedMonthlyPartition.getData() != null) {
                partitionTables.add(loadedMonthlyPartition.getData());
            }
        }

        return partitionTables;
    }

    /**
     * Loads check results.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity with the data domain.
     * @return List of partition results as tables with all check results.
     */
    protected List<Table> loadCheckResultsPartitions(CheckResultsOverviewParameters loadParameters,
                                                     String connectionName,
                                                     PhysicalTableName physicalTableName,
                                                     UserDomainIdentity userDomainIdentity) {
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        checkResultsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        List<Table> partitionTables = new ArrayList<>();
        Collection<LoadedMonthlyPartition> loadedMonthlyPartitions = checkResultsSnapshot.getLoadedMonthlyPartitions().values();
        for (LoadedMonthlyPartition loadedMonthlyPartition : loadedMonthlyPartitions) {
            if (loadedMonthlyPartition.getData() != null) {
                partitionTables.add(loadedMonthlyPartition.getData());
            }
        }

        return partitionTables;
    }

    /**
     * Loads rule results for a maximum of two months available in the data, within the date range specified in {@code loadParameters}.
     * If the date range is open-ended, only one or none of the range's boundaries are checked.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Table with rule results for the most recent two months inside the specified range or null when no data found.
     */
    protected List<Table> loadRecentRuleResults(CheckResultsDetailedFilterParameters loadParameters,
                                          String connectionName,
                                          PhysicalTableName physicalTableName,
                                          UserDomainIdentity userDomainIdentity) {
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        int maxMonthsToLoad = DEFAULT_MAX_RECENT_LOADED_MONTHS;

        if (loadParameters.getStartMonth() != null && loadParameters.getEndMonth() != null) {
            LocalDate startMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getStartMonth());
            LocalDate endMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getEndMonth());

            maxMonthsToLoad =
                    (int)LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                            startMonthTruncated.atStartOfDay(), endMonthTruncated.atStartOfDay(), TimePeriodGradient.month) + 1;
        }

        checkResultsSnapshot.ensureNRecentMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth(), maxMonthsToLoad);
        List<Table> loadedTables = checkResultsSnapshot.getLoadedMonthlyPartitions().values()
                .stream()
                .filter(p -> p != null && p.getData() != null)
                .map(p -> p.getData())
                .collect(Collectors.toList());

        return loadedTables;
    }

    /**
     * Loads all errors and normalizes them to match the column names loaded from the rule results.
     * Adds a fake "severity" column with a value 4, so it is higher even then a fatal severity data quality error.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity with the  data domain.
     * @return Errors table or null when no data found.
     */
    protected Table loadAggregatedErrorsNormalizedToResults(CheckResultsOverviewParameters loadParameters,
                                                            String connectionName,
                                                            PhysicalTableName physicalTableName,
                                                            UserDomainIdentity userDomainIdentity) {
        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorsColumnNames.COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        errorsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        Table allErrorsWithAllColumns = errorsSnapshot.getAllData();
        if (allErrorsWithAllColumns == null) {
            return null;
        }

        Table allErrors = TableCopyUtility.extractColumns(allErrorsWithAllColumns, ErrorsColumnNames.COLUMN_NAMES_FOR_ERRORS_OVERVIEW);
        IntColumn severityColumn = IntColumn.create(CheckResultsColumnNames.SEVERITY_COLUMN_NAME, allErrors.rowCount());
        severityColumn.setMissingTo(CheckResultStatus.execution_error.getSeverity()); // severity 0,1,2,3 are success,warning,error,fatal, so a processing error with severity 4 will sort ahead of other severities (processing errors are more severe for the overview)
        allErrors.addColumns(severityColumn);

        return allErrors;
    }

    /**
     * Loads check results.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Table with all check results or null when no data found.
     */
    protected Table loadAggregatedCheckResults(CheckResultsOverviewParameters loadParameters,
                                               String connectionName,
                                               PhysicalTableName physicalTableName,
                                               UserDomainIdentity userDomainIdentity) {
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        checkResultsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        Table ruleResultsDataAll = checkResultsSnapshot.getAllData();
        if (ruleResultsDataAll == null) {
            return null;
        }
        Table ruleResultsData = TableCopyUtility.extractColumns(ruleResultsDataAll, CheckResultsColumnNames.COLUMN_NAMES_FOR_RESULTS_OVERVIEW);
        return ruleResultsData;
    }

    /**
     * Checks if there are any recent partition files with the results of check results for the given table.
     * This operation is used to propose the user to run checks.
     *
     * @param connectionName     Connection name.
     * @param physicalTableName  Physical table name.
     * @param userDomainIdentity User identity with the data domain.
     * @return True when there are any results, false when there are no results.
     */
    @Override
    public boolean hasAnyRecentCheckResults(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity) {
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);

        LocalDate todayDate = LocalDate.now();
        int monthsToLoad = DEFAULT_MAX_RECENT_LOADED_MONTHS;
        LocalDate startDate = todayDate.minus(monthsToLoad, ChronoUnit.MONTHS);
        checkResultsSnapshot.ensureNRecentMonthsAreLoaded(startDate, todayDate, 1);

        for (LoadedMonthlyPartition loadedMonthlyPartition : checkResultsSnapshot.getLoadedMonthlyPartitions().values()) {
            if (loadedMonthlyPartition != null && loadedMonthlyPartition.getData() != null && loadedMonthlyPartition.getData().rowCount() > 0) {
                return true;
            }
        }

        return false;
    }
}
