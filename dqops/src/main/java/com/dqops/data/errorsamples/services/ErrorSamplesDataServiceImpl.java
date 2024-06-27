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
package com.dqops.data.errorsamples.services;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errorsamples.factory.ErrorSampleResultDataType;
import com.dqops.data.errorsamples.factory.ErrorSamplesColumnNames;
import com.dqops.data.errorsamples.models.ErrorSampleEntryModel;
import com.dqops.data.errorsamples.models.ErrorSamplesListModel;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshot;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshotFactory;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.data.storage.ParquetPartitionId;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.datetime.LocalDateTimePeriodUtility;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import com.dqops.utils.tables.TableRowUtility;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
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
 * Service that returns data from error samples.
 */
@Service
public class ErrorSamplesDataServiceImpl implements ErrorSamplesDataService {
    public static final int DEFAULT_MAX_RECENT_LOADED_MONTHS = 3;

    private ErrorSamplesSnapshotFactory errorSamplesSnapshotFactory;

    @Autowired
    public ErrorSamplesDataServiceImpl(ErrorSamplesSnapshotFactory errorSamplesSnapshotFactory) {
        this.errorSamplesSnapshotFactory = errorSamplesSnapshotFactory;
    }

    /**
     * Retrieves the complete model of the error samples related to a check.
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @param userDomainIdentity      User identity within the data domain.
     * @return Complete model of the error samples.
     */
    @Override
    public ErrorSamplesListModel[] readErrorSamplesDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                            ErrorSamplesFilterParameters loadParameters,
                                                            UserDomainIdentity userDomainIdentity) {
        Map<Long, ErrorSamplesListModel> errorMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        ErrorSamplesSnapshot samplesSnapshot = loadRecentErrorSamples(loadParameters, connectionName, physicalTableName, userDomainIdentity);
        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = samplesSnapshot.getLoadedMonthlyPartitions();
        if (loadedMonthlyPartitions == null || loadedMonthlyPartitions.isEmpty()) {
            return new ErrorSamplesListModel[0]; // empty array
        }

        ArrayList<LoadedMonthlyPartition> loadedPartitions = new ArrayList<>(loadedMonthlyPartitions.values());
        Lists.reverse(loadedPartitions);

        for (LoadedMonthlyPartition loadedMonthlyPartition : loadedPartitions) {
            if (loadedMonthlyPartition == null || loadedMonthlyPartition.getData() == null) {
                continue;
            }

            Table errorSamplesTable = loadedMonthlyPartition.getData();
            Table filteredTable = filterTableToRootChecksContainerAndFilterParameters(rootChecksContainerSpec, errorSamplesTable, loadParameters);
            if (filteredTable.isEmpty()) {
                continue; // empty array
            }

            Table filteredTableByDataGroup = filteredTable;
            if (!Strings.isNullOrEmpty(loadParameters.getDataGroupName())) {
                TextColumn dataGroupNameFilteredColumn = filteredTable.textColumn(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
                filteredTableByDataGroup = filteredTable.where(dataGroupNameFilteredColumn.isEqualTo(loadParameters.getDataGroupName()));
            }

            if (filteredTableByDataGroup.isEmpty()) {
                continue; // empty array
            }

            Table sortedTable = filteredTableByDataGroup.sortDescendingOn(
                    ErrorSamplesColumnNames.EXECUTED_AT_COLUMN_NAME); // most recent execution first

            LongColumn checkHashColumn = sortedTable.longColumn(ErrorSamplesColumnNames.CHECK_HASH_COLUMN_NAME);
            LongColumn checkHashColumnUnsorted = filteredTable.longColumn(ErrorSamplesColumnNames.CHECK_HASH_COLUMN_NAME);
            TextColumn allDataGroupColumnUnsorted = filteredTable.textColumn(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
            TextColumn allDataGroupColumn = sortedTable.textColumn(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

            int rowCount = sortedTable.rowCount();
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Long checkHash = checkHashColumn.get(rowIndex);
                String dataGroupNameForCheck = allDataGroupColumn.get(rowIndex);
                ErrorSamplesListModel errorsListModel = errorMap.get(checkHash);

                ErrorSampleEntryModel singleModel = null;

                if (errorsListModel != null) {
                    if (errorsListModel.getErrorSamplesEntries().size() >= loadParameters.getMaxResultsPerCheck()) {
                        continue; // enough results loaded
                    }

                    if (!Objects.equals(dataGroupNameForCheck, errorsListModel.getDataGroup())) {
                        continue; // we are not mixing groups, results for a different group were already loaded
                    }
                } else {
                    Row row = sortedTable.row(rowIndex);
                    singleModel = createErrorSingleModel(row);
                    String checkCategory = row.getString(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME);
                    String checkDisplayName = row.getString(ErrorSamplesColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME);
                    String checkName = row.getString(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME);
                    String checkTypeString = row.getString(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME);

                    errorsListModel = new ErrorSamplesListModel();
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

                errorsListModel.getErrorSamplesEntries().add(singleModel);
            }

            if (!errorMap.isEmpty()) {
                return errorMap.values().toArray(ErrorSamplesListModel[]::new);
            }
        }

        return new ErrorSamplesListModel[0]; // no results found
    }

    /**
     * Creates a model with a single error sample row.
     * @param row Row.
     * @return Model with a single error sample.
     */
    @NotNull
    private static ErrorSampleEntryModel createErrorSingleModel(Row row) {
        LocalDateTime collectedAt = row.getDateTime(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME);

        String id = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.ID_COLUMN_NAME);
        String columnName = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME);
        String dataGroupName = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
        String checkTypeString = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME);

        Instant executedAt = row.getInstant(ErrorSamplesColumnNames.EXECUTED_AT_COLUMN_NAME);
        String timeGradientString = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME);

        String provider = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.PROVIDER_COLUMN_NAME);
        String qualityDimension = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
        String sensorName = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME);
        String tableComparison = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);

        String rowId1 = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "1");
        String rowId2 = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "2");
        String rowId3 = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "3");
        String rowId4 = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "4");
        String rowId5 = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "5");

        ErrorSampleEntryModel singleModel = new ErrorSampleEntryModel() {{
            setId(id);
            setCollectedAt(collectedAt);
            setExecutedAt(executedAt);

            setColumnName(columnName);
            setDataGroup(dataGroupName);
            setCheckType(CheckType.fromString(checkTypeString));
            setTimeGradient(TimePeriodGradient.fromString(timeGradientString));

            setProvider(provider);
            setQualityDimension(qualityDimension);

            setSensorName(sensorName);
            setTableComparison(tableComparison);

            setRowId1(rowId1);
            setRowId2(rowId2);
            setRowId3(rowId3);
            setRowId4(rowId4);
            setRowId5(rowId5);
        }};

        String resultTypeString = TableRowUtility.getSanitizedStringValue(row, ErrorSamplesColumnNames.RESULT_TYPE_COLUMN_NAME);
        ErrorSampleResultDataType errorSampleResultDataType = ErrorSampleResultDataType.fromName(resultTypeString);
        singleModel.setResultDataType(errorSampleResultDataType);
        if (!row.isMissing(ErrorSamplesColumnNames.SAMPLE_INDEX_COLUMN_NAME)) {
            singleModel.setSampleIndex(row.getInt(ErrorSamplesColumnNames.SAMPLE_INDEX_COLUMN_NAME));
        }

        switch (errorSampleResultDataType) {
            case INTEGER:
                singleModel.setResult(row.getLong(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME)); // TODO: store also typed values in typed fields
                break;
            case BOOLEAN:
                singleModel.setResult(row.getBoolean(ErrorSamplesColumnNames.RESULT_BOOLEAN_COLUMN_NAME));
                break;
            case FLOAT:
                singleModel.setResult(row.getDouble(ErrorSamplesColumnNames.RESULT_FLOAT_COLUMN_NAME));
                break;
            case STRING:
                singleModel.setResult(row.getString(ErrorSamplesColumnNames.RESULT_STRING_COLUMN_NAME));
                break;
            case INSTANT:
                singleModel.setResult(row.getInstant(ErrorSamplesColumnNames.RESULT_INSTANT_COLUMN_NAME));
                break;
            case DATE:
                singleModel.setResult(row.getDate(ErrorSamplesColumnNames.RESULT_DATE_COLUMN_NAME));
                break;
            case DATETIME:
                singleModel.setResult(row.getDateTime(ErrorSamplesColumnNames.RESULT_DATE_TIME_COLUMN_NAME));
                break;
            case TIME:
                singleModel.setResult(row.getTime(ErrorSamplesColumnNames.RESULT_TIME_COLUMN_NAME));
                break;
            default:
                break;
        }

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

        Selection rowSelection = sourceTable.textColumn(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        TextColumn columnNameColumn = sourceTable.textColumn(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME);
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
                                                                        ErrorSamplesFilterParameters filterParameters) {
        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();

        Selection rowSelection = sourceTable.textColumn(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        TextColumn columnNameColumn = sourceTable.textColumn(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME);
        rowSelection = rowSelection.and((columnName != null) ? columnNameColumn.isEqualTo(columnName) : columnNameColumn.isMissing());

        if (!Strings.isNullOrEmpty(filterParameters.getCheckName())) {
            TextColumn checkNameColumn = sourceTable.textColumn(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME);
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

            TextColumn checkCategoryColumn = sourceTable.textColumn(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            rowSelection = rowSelection.and(checkCategoryColumn.isEqualTo(checkCategory));
        }

        if (!Strings.isNullOrEmpty(tableComparison)) {
            TextColumn tableComparisonNameColumn = sourceTable.textColumn(ErrorSamplesColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);
            rowSelection = rowSelection.and(tableComparisonNameColumn.isEqualTo(tableComparison));
        }

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Loads error samples for a maximum of two months available in the data, within the date range specified in {@code loadParameters}.
     * If the date range is open-ended, only one or none of the range's boundaries are checked.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity within the data domain.
     * @return Table with error samples for the most recent two months inside the specified range or null when no data found.
     */
    protected ErrorSamplesSnapshot loadRecentErrorSamples(ErrorSamplesFilterParameters loadParameters,
                                                          String connectionName,
                                                          PhysicalTableName physicalTableName,
                                                          UserDomainIdentity userDomainIdentity) {
        ErrorSamplesSnapshot errorSamplesSnapshot = this.errorSamplesSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorSamplesColumnNames.COLUMN_NAMES_FOR_READ_ONLY_ACCESS, userDomainIdentity);
        int maxMonthsToLoad = DEFAULT_MAX_RECENT_LOADED_MONTHS;

        if (loadParameters.getStartMonth() != null && loadParameters.getEndMonth() != null) {
            LocalDate startMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getStartMonth());
            LocalDate endMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getEndMonth());

            maxMonthsToLoad =
                    (int) LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                            startMonthTruncated.atStartOfDay(), endMonthTruncated.atStartOfDay(), TimePeriodGradient.month) + 1;
        }

        errorSamplesSnapshot.ensureNRecentMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth(), maxMonthsToLoad);
        return errorSamplesSnapshot;
    }
}
