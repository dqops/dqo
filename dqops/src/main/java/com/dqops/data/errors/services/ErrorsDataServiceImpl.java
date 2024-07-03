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
package com.dqops.data.errors.services;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.errors.models.ErrorEntryModel;
import com.dqops.data.errors.models.ErrorsListModel;
import com.dqops.data.errors.snapshot.ErrorsSnapshot;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactory;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.datetime.LocalDateTimePeriodUtility;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import com.dqops.utils.tables.TableRowUtility;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that returns data from execution errors.
 */
@Service
public class ErrorsDataServiceImpl implements ErrorsDataService {
    public static final int DEFAULT_MAX_RECENT_LOADED_MONTHS = 3;

    private ErrorsSnapshotFactory errorsSnapshotFactory;

    @Autowired
    public ErrorsDataServiceImpl(ErrorsSnapshotFactory errorsSnapshotFactory) {
        this.errorsSnapshotFactory = errorsSnapshotFactory;
    }

    /**
     * Retrieves the complete model of the errors related to check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @param userDomainIdentity      User identity within the data domain.
     * @return Complete model of the errors.
     */
    @Override
    public ErrorsListModel[] readErrorsDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                ErrorsDetailedFilterParameters loadParameters,
                                                UserDomainIdentity userDomainIdentity) {
        Map<Long, ErrorsListModel> errorMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table errorsTable = loadRecentErrors(loadParameters, connectionName, physicalTableName, userDomainIdentity);
        if (errorsTable == null || errorsTable.isEmpty()) {
            return new ErrorsListModel[0]; // empty array
        }

        Table filteredTable = filterTableToRootChecksContainerAndFilterParameters(rootChecksContainerSpec, errorsTable, loadParameters);
        if (filteredTable.isEmpty()) {
            return new ErrorsListModel[0]; // empty array
        }

        Table filteredTableByDataGroup = filteredTable;
        if (!Strings.isNullOrEmpty(loadParameters.getDataGroupName())) {
            TextColumn dataGroupNameFilteredColumn = filteredTable.textColumn(ErrorsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
            filteredTableByDataGroup = filteredTable.where(dataGroupNameFilteredColumn.isEqualTo(loadParameters.getDataGroupName()));
        }

        if (filteredTableByDataGroup.isEmpty()) {
            return new ErrorsListModel[0]; // empty array
        }

        Table sortedTable = filteredTableByDataGroup.sortDescendingOn(
                ErrorsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME); // then the most recent reading (for partitioned checks) when many partitions were captured

        LongColumn checkHashColumn = sortedTable.longColumn(ErrorsColumnNames.CHECK_HASH_COLUMN_NAME);
        LongColumn checkHashColumnUnsorted = filteredTable.longColumn(ErrorsColumnNames.CHECK_HASH_COLUMN_NAME);
        TextColumn allDataGroupColumnUnsorted = filteredTable.textColumn(ErrorsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
        TextColumn allDataGroupColumn = sortedTable.textColumn(ErrorsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

        int rowCount = sortedTable.rowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Long checkHash = checkHashColumn.get(rowIndex);
            String dataGroupNameForCheck = allDataGroupColumn.get(rowIndex);
            ErrorsListModel errorsListModel = errorMap.get(checkHash);

            ErrorEntryModel singleModel = null;

            if (errorsListModel != null) {
                if (errorsListModel.getErrorEntries().size() >= loadParameters.getMaxResultsPerCheck()) {
                    continue; // enough results loaded
                }

                if (!Objects.equals(dataGroupNameForCheck, errorsListModel.getDataGroup())) {
                    continue; // we are not mixing groups, results for a different group were already loaded
                }
            } else {
                Row row = sortedTable.row(rowIndex);
                singleModel = createErrorSingleModel(row);
                String checkCategory = row.getString(ErrorsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
                String checkDisplayName = row.getString(ErrorsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME);
                String checkName = row.getString(ErrorsColumnNames.CHECK_NAME_COLUMN_NAME);
                String checkTypeString = row.getString(ErrorsColumnNames.CHECK_TYPE_COLUMN_NAME);

                errorsListModel = new ErrorsListModel();
                errorsListModel.setCheckCategory(checkCategory);
                errorsListModel.setCheckName(checkName);
                errorsListModel.setCheckHash(checkHash);
                errorsListModel.setCheckType(CheckType.fromString(checkTypeString));
                errorsListModel.setCheckDisplayName(checkDisplayName);
                errorsListModel.setDataGroup(dataGroupNameForCheck);

                Selection resultsForCheckHash = checkHashColumnUnsorted.isIn(checkHash);
                List<String> dataGroupsForCheck = allDataGroupColumnUnsorted.where(resultsForCheckHash)
                        .unique().asList().stream().sorted().collect(Collectors.toList());

                if (dataGroupsForCheck.size() > 1 && dataGroupsForCheck.contains(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME)) {
                    dataGroupsForCheck.remove(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
                    dataGroupsForCheck.add(0, CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
                }

                errorsListModel.setDataGroupsNames(dataGroupsForCheck);
                errorMap.put(checkHash, errorsListModel);
            }

            if (singleModel == null) {
                singleModel = createErrorSingleModel(sortedTable.row(rowIndex));
            }

            errorsListModel.getErrorEntries().add(singleModel);
        }

        return errorMap.values().toArray(ErrorsListModel[]::new);
    }

    /**
     * Creates a model with a single error row.
     * @param row Row.
     * @return Model with a single error.
     */
    @NotNull
    private static ErrorEntryModel createErrorSingleModel(Row row) {
        Double actualValue = TableRowUtility.getSanitizedDoubleValue(row, ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        Double expectedValue = TableRowUtility.getSanitizedDoubleValue(row, ErrorsColumnNames.EXPECTED_VALUE_COLUMN_NAME);

        String columnName = TableRowUtility.getSanitizedStringValue(row, ErrorsColumnNames.COLUMN_NAME_COLUMN_NAME);
        String dataGroupName = row.getString(ErrorsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
        String checkTypeString = row.getString(ErrorsColumnNames.CHECK_TYPE_COLUMN_NAME);

        Integer durationMs = row.getInt(ErrorsColumnNames.DURATION_MS_COLUMN_NAME);
        Instant executedAt = row.getInstant(ErrorsColumnNames.EXECUTED_AT_COLUMN_NAME);
        String timeGradientString = row.getString(ErrorsColumnNames.TIME_GRADIENT_COLUMN_NAME);
        LocalDateTime timePeriod = row.getDateTime(ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME);

        String provider = row.getString(ErrorsColumnNames.PROVIDER_COLUMN_NAME);
        String qualityDimension = row.getString(ErrorsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
        String sensorName = row.getString(ErrorsColumnNames.SENSOR_NAME_COLUMN_NAME);

        String sensorReadoutId = row.getString(ErrorsColumnNames.READOUT_ID_COLUMN_NAME);
        String errorMessage = row.getString(ErrorsColumnNames.ERROR_MESSAGE_COLUMN_NAME);
        LocalDateTime errorTimestamp = row.getDateTime(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME);
        String errorSource = row.getString(ErrorsColumnNames.ERROR_SOURCE_COLUMN_NAME);
        String tableComparison = row.getString(ErrorsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);

        ErrorEntryModel singleModel = new ErrorEntryModel() {{
            setActualValue(actualValue);
            setExpectedValue(expectedValue);

            setColumnName(columnName);
            setDataGroup(dataGroupName);
            setCheckType(CheckType.fromString(checkTypeString));

            setDurationMs(durationMs);
            setExecutedAt(executedAt);
            setTimeGradient(TimePeriodGradient.fromString(timeGradientString));
            setTimePeriod(timePeriod);

            setProvider(provider);
            setQualityDimension(qualityDimension);

            setSensorName(sensorName);
            setReadoutId(sensorReadoutId);
            setErrorMessage(errorMessage);
            setErrorSource(errorSource);
            setErrorTimestamp(errorTimestamp);

            setTableComparison(tableComparison);
        }};
        return singleModel;
    }

    /**
     * Filters the errors to only the errors that appeared for the target root check container.
     * @param rootChecksContainerSpec Root checks container to identify the parent column, check type and time scale.
     * @param sourceTable Source table to be filtered.
     * @return Filtered table.
     */
    protected Table filterTableToRootChecksContainer(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                     Table sourceTable) {
        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();

        Selection rowSelection = sourceTable.textColumn(ErrorsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(ErrorsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        TextColumn columnNameColumn = sourceTable.textColumn(ErrorsColumnNames.COLUMN_NAME_COLUMN_NAME);
        rowSelection = rowSelection.and((columnName != null) ? columnNameColumn.isEqualTo(columnName) : columnNameColumn.isMissing());

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
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
                                                                        ErrorsDetailedFilterParameters filterParameters) {
        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();

        Selection rowSelection = sourceTable.textColumn(ErrorsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(ErrorsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        TextColumn columnNameColumn = sourceTable.textColumn(ErrorsColumnNames.COLUMN_NAME_COLUMN_NAME);
        rowSelection = rowSelection.and((columnName != null) ? columnNameColumn.isEqualTo(columnName) : columnNameColumn.isMissing());

        if (!Strings.isNullOrEmpty(filterParameters.getCheckName())) {
            TextColumn checkNameColumn = sourceTable.textColumn(ErrorsColumnNames.CHECK_NAME_COLUMN_NAME);
            rowSelection = rowSelection.and(checkNameColumn.isEqualTo(filterParameters.getCheckName()));
        }

        String checkCategory = filterParameters.getCheckCategory();
        String tableComparison = filterParameters.getTableComparison();

        if (!Strings.isNullOrEmpty(checkCategory)) {
            if (checkCategory.startsWith(AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME + "/")) {
                // this code will support receiving combined category names for table comparisons
                String[] columnCategorySplits = StringUtils.split(checkCategory, '/');
                checkCategory = columnCategorySplits[0];
                tableComparison = columnCategorySplits[1];
            }

            TextColumn checkCategoryColumn = sourceTable.textColumn(ErrorsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            rowSelection = rowSelection.and(checkCategoryColumn.isEqualTo(checkCategory));
        }

        if (!Strings.isNullOrEmpty(tableComparison)) {
            TextColumn tableComparisonNameColumn = sourceTable.textColumn(ErrorsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);
            rowSelection = rowSelection.and(tableComparisonNameColumn.isEqualTo(tableComparison));
        }

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Loads errors for a maximum of two months available in the data, within the date range specified in {@code loadParameters}.
     * If the date range is open-ended, only one or none of the range's boundaries are checked.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity within the data domain.
     * @return Table with errors for the most recent two months inside the specified range or null when no data found.
     */
    protected Table loadRecentErrors(ErrorsDetailedFilterParameters loadParameters,
                                     String connectionName,
                                     PhysicalTableName physicalTableName,
                                     UserDomainIdentity userDomainIdentity) {
        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorsColumnNames.COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        int maxMonthsToLoad = DEFAULT_MAX_RECENT_LOADED_MONTHS;

        if (loadParameters.getStartMonth() != null && loadParameters.getEndMonth() != null) {
            LocalDate startMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getStartMonth());
            LocalDate endMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getEndMonth());

            maxMonthsToLoad =
                    (int) LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                            startMonthTruncated.atStartOfDay(), endMonthTruncated.atStartOfDay(), TimePeriodGradient.month) + 1;
        }

        errorsSnapshot.ensureNRecentMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth(), maxMonthsToLoad);
        Table ruleResultsData = errorsSnapshot.getAllData();
        return ruleResultsData;
    }
}
