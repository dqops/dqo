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
package com.dqops.data.readouts.services;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.services.models.SensorReadoutEntryModel;
import com.dqops.data.readouts.services.models.SensorReadoutsListModel;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactory;
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
 * Service that returns data from sensor readouts.
 */
@Service
public class SensorReadoutsDataServiceImpl implements SensorReadoutsDataService {
    public static final int DEFAULT_MAX_RECENT_LOADED_MONTHS = 3;

    private SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory;

    @Autowired
    public SensorReadoutsDataServiceImpl(SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory) {
        this.sensorReadoutsSnapshotFactory = sensorReadoutsSnapshotFactory;
    }

    /**
     * Retrieves the complete model of the readouts of sensor activations for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Complete model of the sensor readouts.
     */
    @Override
    public SensorReadoutsListModel[] readSensorReadoutsDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                SensorReadoutsDetailedFilterParameters loadParameters) {
        Map<Long, SensorReadoutsListModel> readoutMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table sensorReadoutsTable = loadRecentSensorReadouts(loadParameters, connectionName, physicalTableName);
        if (sensorReadoutsTable == null || sensorReadoutsTable.isEmpty()) {
            return new SensorReadoutsListModel[0]; // empty array
        }

        Table filteredTableWithAllDataGroups = filterTableToRootChecksContainerAndFilterParameters(rootChecksContainerSpec, sensorReadoutsTable, loadParameters);

        Table filteredTableByDataGroup = filteredTableWithAllDataGroups;
        if (!Strings.isNullOrEmpty(loadParameters.getDataGroupName())) {
            TextColumn dataGroupNameFilteredColumn = filteredTableWithAllDataGroups.textColumn(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
            filteredTableByDataGroup = filteredTableWithAllDataGroups.where(dataGroupNameFilteredColumn.isEqualTo(loadParameters.getDataGroupName()));
        }

        if (filteredTableByDataGroup.isEmpty()) {
            return new SensorReadoutsListModel[0]; // empty array
        }

        Table sortedTable = filteredTableByDataGroup.sortDescendingOn(
                SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME); // then the most recent reading (for partitioned checks) when many partitions were captured

        LongColumn checkHashColumn = sortedTable.longColumn(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
        LongColumn checkHashColumnUnsorted = filteredTableWithAllDataGroups.longColumn(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
        TextColumn allDataGroupColumnUnsorted = filteredTableWithAllDataGroups.textColumn(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
        TextColumn allDataGroupColumn = sortedTable.textColumn(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

        int rowCount = sortedTable.rowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {

            Long checkHash = checkHashColumn.get(rowIndex);
            String dataGroupNameForCheck = allDataGroupColumn.get(rowIndex);
            SensorReadoutsListModel sensorReadoutDetailedDataModel = readoutMap.get(checkHash);

            SensorReadoutEntryModel singleModel = null;

            if (sensorReadoutDetailedDataModel != null) {
                if (sensorReadoutDetailedDataModel.getSensorReadoutEntries().size() >= loadParameters.getMaxResultsPerCheck()) {
                    continue; // enough results loaded
                }

                if (!Objects.equals(dataGroupNameForCheck, sensorReadoutDetailedDataModel.getDataGroup())) {
                    continue; // we are not mixing groups, results for a different group were already loaded
                }
            } else {
                Row row = sortedTable.row(rowIndex);
                singleModel = createSensorReadoutSingleRow(row);
                String checkCategory = row.getString(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
                String checkDisplayName = row.getString(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME);
                String checkName = row.getString(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
                String checkTypeString = row.getString(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME);

                sensorReadoutDetailedDataModel = new SensorReadoutsListModel();
                sensorReadoutDetailedDataModel.setCheckCategory(checkCategory);
                sensorReadoutDetailedDataModel.setCheckName(checkName);
                sensorReadoutDetailedDataModel.setCheckHash(checkHash);
                sensorReadoutDetailedDataModel.setCheckType(CheckType.fromString(checkTypeString));
                sensorReadoutDetailedDataModel.setCheckDisplayName(checkDisplayName);
                sensorReadoutDetailedDataModel.setDataGroup(dataGroupNameForCheck);


                Selection resultsForCheckHash = checkHashColumnUnsorted.isEqualTo(checkHash);
                List<String> dataGroupsForCheck = allDataGroupColumnUnsorted.where(resultsForCheckHash).asList().stream().distinct().sorted().collect(Collectors.toList());

                if (dataGroupsForCheck.size() > 1 && dataGroupsForCheck.contains(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME)) {
                    dataGroupsForCheck.remove(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
                    dataGroupsForCheck.add(0, CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
                }

                sensorReadoutDetailedDataModel.setDataGroupNames(dataGroupsForCheck);
                readoutMap.put(checkHash, sensorReadoutDetailedDataModel);
            }

            if (singleModel == null) {
                singleModel = createSensorReadoutSingleRow(sortedTable.row(rowIndex));
            }
            if (sensorReadoutDetailedDataModel.getSensorReadoutEntries() == null) {
                sensorReadoutDetailedDataModel.setSensorReadoutEntries(new ArrayList<>());
            }

            sensorReadoutDetailedDataModel.getSensorReadoutEntries().add(singleModel);
        }

        return readoutMap.values().toArray(SensorReadoutsListModel[]::new);
    }

    /**
     * Creates a model of a single sensor result.
     * @param row A row with a single sensor result.
     * @return Single sensor result model.
     */
    @NotNull
    private static SensorReadoutEntryModel createSensorReadoutSingleRow(Row row) {
        String id = row.getString(SensorReadoutsColumnNames.ID_COLUMN_NAME);

        String checkDisplayName = row.getString(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME);
        String checkName = row.getString(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
        String checkTypeString = row.getString(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME);

        Double actualValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        Double expectedValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);

        String columnName = TableRowUtility.getSanitizedStringValue(row, SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
        String dataStream = row.getString(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

        Integer durationMs = row.getInt(SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME);
        Instant executedAt = row.getInstant(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
        String timeGradientString = row.getString(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
        LocalDateTime timePeriod = row.getDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);

        String provider = row.getString(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME);
        String qualityDimension = row.getString(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
        String tableComparison = row.getString(SensorReadoutsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);


        SensorReadoutEntryModel singleModel = new SensorReadoutEntryModel() {{
            setId(id);
            setActualValue(actualValue);
            setExpectedValue(expectedValue);

            setColumnName(columnName);
            setDataGroup(dataStream);

            setDurationMs(durationMs);
            setExecutedAt(executedAt);
            setTimeGradient(TimePeriodGradient.fromString(timeGradientString));
            setTimePeriod(timePeriod);

            setProvider(provider);
            setQualityDimension(qualityDimension);

            setCheckName(checkName);
            setCheckType(CheckType.fromString(checkTypeString));
            setCheckDisplayName(checkDisplayName);
            setTableComparison(tableComparison);

        }};
        return singleModel;
    }

    /**
     * Filters the readouts to only the readouts for the target root check container.
     * @param rootChecksContainerSpec Root checks container to identify the parent column, check type and time scale.
     * @param sourceTable Source table to be filtered.
     * @return Filtered table.
     */
    protected Table filterTableToRootChecksContainer(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                     Table sourceTable) {
        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();

        Selection rowSelection = sourceTable.textColumn(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        TextColumn columnNameColumn = sourceTable.textColumn(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
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
                                                                        SensorReadoutsDetailedFilterParameters filterParameters) {
        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();

        Selection rowSelection = sourceTable.textColumn(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (timeScale != null) {
            TextColumn timeGradientColumn = sourceTable.textColumn(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimePeriodGradient timePeriodGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timePeriodGradient.name()));
        }

        TextColumn columnNameColumn = sourceTable.textColumn(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
        rowSelection = rowSelection.and((columnName != null) ? columnNameColumn.isEqualTo(columnName) : columnNameColumn.isMissing());

        if (!Strings.isNullOrEmpty(filterParameters.getCheckName())) {
            TextColumn checkNameColumn = sourceTable.textColumn(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
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

            TextColumn checkCategoryColumn = sourceTable.textColumn(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            rowSelection = rowSelection.and(checkCategoryColumn.isEqualTo(checkCategory));
        }

        if (!Strings.isNullOrEmpty(tableComparison)) {
            TextColumn tableComparisonNameColumn = sourceTable.textColumn(SensorReadoutsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME);
            rowSelection = rowSelection.and(tableComparisonNameColumn.isEqualTo(tableComparison));
        }

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Loads sensor readouts for a maximum of three months available in the data, within the date range specified in {@code loadParameters}.
     * If the date range is open-ended, only one or none of the range's boundaries are checked.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with sensor readouts for the most recent three months inside the specified range or null when no data found.
     */
    protected Table loadRecentSensorReadouts(SensorReadoutsDetailedFilterParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        SensorReadoutsSnapshot sensorReadoutsSnapshot = this.sensorReadoutsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, SensorReadoutsColumnNames.COLUMN_NAMES_FOR_READOUTS_DETAILED);
        int maxMonthsToLoad = DEFAULT_MAX_RECENT_LOADED_MONTHS;

        if (loadParameters.getStartMonth() != null && loadParameters.getEndMonth() != null) {
            LocalDate startMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getStartMonth());
            LocalDate endMonthTruncated = LocalDateTimeTruncateUtility.truncateMonth(loadParameters.getEndMonth());

            maxMonthsToLoad =
                    (int) LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                            startMonthTruncated.atStartOfDay(), endMonthTruncated.atStartOfDay(), TimePeriodGradient.month) + 1;
        }

        sensorReadoutsSnapshot.ensureNRecentMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth(), maxMonthsToLoad);
        Table sensorReadoutsData = sensorReadoutsSnapshot.getAllData();
        return sensorReadoutsData;
    }
}
