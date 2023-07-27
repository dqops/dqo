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
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.errors.services.models.ErrorDetailedSingleModel;
import com.dqops.data.errors.services.models.ErrorsDetailedDataModel;
import com.dqops.data.errors.snapshot.ErrorsSnapshot;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactory;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
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
public class ErrorsDataServiceImpl implements ErrorsDataService {
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
     * @return Complete model of the errors.
     */
    @Override
    public ErrorsDetailedDataModel[] readErrorsDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                        ErrorsDetailedParameters loadParameters) {
        Map<Long, ErrorsDetailedDataModel> errorMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table errorsTable = loadRecentErrors(loadParameters, connectionName, physicalTableName);
        if (errorsTable == null || errorsTable.isEmpty()) {
            return new ErrorsDetailedDataModel[0]; // empty array
        }

        Table filteredTable = filterTableToRootChecksContainer(rootChecksContainerSpec, errorsTable);
        if (filteredTable.isEmpty()) {
            return new ErrorsDetailedDataModel[0]; // empty array
        }

        TextColumn dataGroupNameColumn = filteredTable.textColumn(ErrorsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
        List<String> dataGroups = dataGroupNameColumn.unique().asList().stream().sorted().collect(Collectors.toList());

        if (dataGroups.size() > 1 && dataGroups.contains(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME)) {
            dataGroups.remove(CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
            dataGroups.add(0, CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);
        }

        String selectedDataStream = Objects.requireNonNullElse(loadParameters.getDataGroup(), dataGroups.get(0));
        Table filteredByDataStream = filteredTable.where(dataGroupNameColumn.isEqualTo(selectedDataStream));

        if (filteredByDataStream.isEmpty()) {
            return new ErrorsDetailedDataModel[0]; // empty array
        }

        Table sortedTable = filteredByDataStream.sortDescendingOn(
                ErrorsColumnNames.EXECUTED_AT_COLUMN_NAME,  // most recent execution first
                ErrorsColumnNames.TIME_PERIOD_COLUMN_NAME); // then the most recent reading (for partitioned checks) when many partitions were captured

        Table workingTable = sortedTable;
        if (loadParameters.getErrorsCount() < sortedTable.rowCount()) {
            workingTable = sortedTable.dropRange(loadParameters.getErrorsCount(), sortedTable.rowCount());
        }

        for (Row row : workingTable) {
            Double actualValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
            Double expectedValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);

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

            String provider = row.getString(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME);
            String qualityDimension = row.getString(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
            String sensorName = row.getString(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME);

            String sensorReadoutId = row.getString(ErrorsColumnNames.READOUT_ID_COLUMN_NAME);
            String errorMessage = row.getString(ErrorsColumnNames.ERROR_MESSAGE_COLUMN_NAME);
            LocalDateTime errorTimestamp = row.getDateTime(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME);
            String errorSource = row.getString(ErrorsColumnNames.ERROR_SOURCE_COLUMN_NAME);

            ErrorDetailedSingleModel singleModel = new ErrorDetailedSingleModel() {{
                setActualValue(actualValue);
                setExpectedValue(expectedValue);

                setColumnName(columnName);
                setDataGroup(dataGroupName);

                setDurationMs(durationMs);
                setExecutedAt(executedAt);
                setTimeGradient(timeGradient);
                setTimePeriod(timePeriod);

                setProvider(provider);
                setQualityDimension(qualityDimension);

                setSensorName(sensorName);
                setReadoutId(sensorReadoutId);
                setErrorMessage(errorMessage);
                setErrorSource(errorSource);
                setErrorTimestamp(errorTimestamp);
            }};

            ErrorsDetailedDataModel errorsDetailedDataModel = errorMap.get(checkHash);
            if (errorsDetailedDataModel == null) {
                errorsDetailedDataModel = new ErrorsDetailedDataModel() {{
                    setCheckCategory(checkCategory);
                    setCheckHash(checkHash);
                    setCheckName(checkName);
                    setCheckType(checkType);
                    setCheckDisplayName(checkDisplayName);

                    setDataGroupsNames(dataGroups);
                    setDataGroup(selectedDataStream);
                    setSingleErrors(new ArrayList<>());
                }};
                errorMap.put(checkHash, errorsDetailedDataModel);
            }

            errorsDetailedDataModel.getSingleErrors().add(singleModel);
        }

        return errorMap.values().toArray(ErrorsDetailedDataModel[]::new);
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
     * Loads errors for a maximum of two months available in the data, within the date range specified in {@code loadParameters}.
     * If the date range is open-ended, only one or none of the range's boundaries are checked.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with errors for the most recent two months inside the specified range or null when no data found.
     */
    protected Table loadRecentErrors(ErrorsDetailedParameters loadParameters,
                                     String connectionName,
                                     PhysicalTableName physicalTableName) {
        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorsColumnNames.COLUMN_NAMES_FOR_ERRORS_DETAILED);
        int monthsToLoad = 2;
        errorsSnapshot.ensureNRecentMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth(), monthsToLoad);
        Table errorsData = errorsSnapshot.getAllData();
        return errorsData;
    }
}
