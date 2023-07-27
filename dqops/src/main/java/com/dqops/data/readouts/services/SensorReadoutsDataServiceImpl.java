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
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.services.models.SensorReadoutDetailedSingleModel;
import com.dqops.data.readouts.services.models.SensorReadoutsDetailedDataModel;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactory;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.tables.TableRowUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that returns data from sensor readouts.
 */
@Service
public class SensorReadoutsDataServiceImpl implements SensorReadoutsDataService {
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
    public SensorReadoutsDetailedDataModel[] readSensorReadoutsDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                        SensorReadoutsDetailedParameters loadParameters) {
        Map<Long, SensorReadoutsDetailedDataModel> readoutMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table sensorReadoutsTable = loadRecentSensorReadouts(loadParameters, connectionName, physicalTableName);
        if (sensorReadoutsTable == null || sensorReadoutsTable.isEmpty()) {
            return new SensorReadoutsDetailedDataModel[0]; // empty array
        }

        Table filteredTable = filterTableToRootChecksContainer(rootChecksContainerSpec, sensorReadoutsTable);
        if (filteredTable.isEmpty()) {
            return new SensorReadoutsDetailedDataModel[0]; // empty array
        }

        TextColumn dataGroupNameColumn = filteredTable.textColumn(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
        List<String> dataGroups = dataGroupNameColumn.unique().asList().stream().sorted().collect(Collectors.toList());

        if (dataGroups.size() > 1 && dataGroups.contains(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME)) {
            dataGroups.remove(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
            dataGroups.add(0, CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
        }

        String selectedDataGroups = Objects.requireNonNullElse(loadParameters.getDataGroupName(), dataGroups.get(0));
        Table filteredByDataGroup = filteredTable.where(dataGroupNameColumn.isEqualTo(selectedDataGroups));

        if (filteredByDataGroup.isEmpty()) {
            return new SensorReadoutsDetailedDataModel[0]; // empty array
        }

        Table sortedTable = filteredByDataGroup.sortDescendingOn(
                SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME,  // most recent execution first
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME); // then the most recent reading (for partitioned checks) when many partitions were captured

        Table workingTable = sortedTable;
        if (loadParameters.getReadoutsCount() < sortedTable.rowCount()) {
            workingTable = sortedTable.dropRange(loadParameters.getReadoutsCount(), sortedTable.rowCount());
        }

        for (Row row : workingTable) {
            String id = row.getString(SensorReadoutsColumnNames.ID_COLUMN_NAME);
            Double actualValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
            Double expectedValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);

            String checkCategory = row.getString(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            String checkDisplayName = row.getString(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME);
            Long checkHash = row.getLong(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
            String checkName = row.getString(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
            String checkType = row.getString(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME);

            String columnName = TableRowUtility.getSanitizedStringValue(row, SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
            String dataStream = row.getString(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);

            Integer durationMs = row.getInt(SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME);
            Instant executedAt = row.getInstant(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
            String timeGradient = row.getString(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            LocalDateTime timePeriod = row.getDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);

            String provider = row.getString(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME);
            String qualityDimension = row.getString(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
            String sensorName = row.getString(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME);

            SensorReadoutDetailedSingleModel singleModel = new SensorReadoutDetailedSingleModel() {{
                setId(id);
                setActualValue(actualValue);
                setExpectedValue(expectedValue);

                setColumnName(columnName);
                setDataGroup(dataStream);

                setDurationMs(durationMs);
                setExecutedAt(executedAt);
                setTimeGradient(timeGradient);
                setTimePeriod(timePeriod);

                setProvider(provider);
                setQualityDimension(qualityDimension);

                setCheckName(checkName);
                setCheckType(checkType);
                setCheckDisplayName(checkDisplayName);
            }};

            SensorReadoutsDetailedDataModel sensorReadoutsDetailedDataModel = readoutMap.get(checkHash);
            if (sensorReadoutsDetailedDataModel == null) {
                sensorReadoutsDetailedDataModel = new SensorReadoutsDetailedDataModel() {{
                    setCheckCategory(checkCategory);
                    setCheckHash(checkHash);
                    setSensorName(sensorName);

                    setDataGroupNames(dataGroups);
                    setDataGroup(selectedDataGroups);
                    setSingleSensorReadouts(new ArrayList<>());
                }};
                readoutMap.put(checkHash, sensorReadoutsDetailedDataModel);
            }

            sensorReadoutsDetailedDataModel.getSingleSensorReadouts().add(singleModel);
        }

        return readoutMap.values().toArray(SensorReadoutsDetailedDataModel[]::new);
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
     * Loads sensor readouts for a maximum of two months available in the data, within the date range specified in {@code loadParameters}.
     * If the date range is open-ended, only one or none of the range's boundaries are checked.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with sensor readouts for the most recent two months inside the specified range or null when no data found.
     */
    protected Table loadRecentSensorReadouts(SensorReadoutsDetailedParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        SensorReadoutsSnapshot sensorReadoutsSnapshot = this.sensorReadoutsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, SensorReadoutsColumnNames.COLUMN_NAMES_FOR_READOUTS_DETAILED);
        int monthsToLoad = 2;
        sensorReadoutsSnapshot.ensureNRecentMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth(), monthsToLoad);
        Table sensorReadoutsData = sensorReadoutsSnapshot.getAllData();
        return sensorReadoutsData;
    }
}
