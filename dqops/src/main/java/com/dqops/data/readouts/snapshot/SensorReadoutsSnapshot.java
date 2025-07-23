/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.snapshot;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.storage.*;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.reflection.ObjectMemorySizeUtility;
import com.dqops.utils.tables.TableColumnUtility;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.table.TableSlice;
import tech.tablesaw.table.TableSliceGroup;

import java.util.Objects;

/**
 * Sensor readouts snapshot that contains an in-memory sensor readout snapshot
 * for a single table and selected time ranges.
 */
public class SensorReadoutsSnapshot extends TableDataSnapshot {
    public static String PARQUET_FILE_NAME = "sensor_readout.0.parquet";
    private SensorReadoutsTimeSeriesMap timeSeriesMap;

    /**
     * Creates a sensor readout snapshot.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new sensor readouts (captured during the current sensor execution).
     */
    public SensorReadoutsSnapshot(UserDomainIdentity userIdentity,
                                  String connectionName,
                                  PhysicalTableName tableName,
                                  ParquetPartitionStorageService storageService,
                                  Table newResults) {
        super(userIdentity, connectionName, tableName, storageService, createSensorReadoutsStorageSettings(), newResults);
    }

    /**
     * Creates a read-only sensor readout snapshot limited to a set of columns.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param columnNames Column names that will be loaded.
     * @param tableResultsSample Empty table with the expected schema (columns).
     */
    public SensorReadoutsSnapshot(UserDomainIdentity userIdentity,
                                  String connectionName,
                                  PhysicalTableName tableName,
                                  ParquetPartitionStorageService storageService,
                                  String[] columnNames,
                                  Table tableResultsSample) {
        super(userIdentity, connectionName, tableName, storageService, createSensorReadoutsStorageSettings(), columnNames, tableResultsSample);
    }

    /**
     * Creates the storage settings for storing the sensor readouts.
     * @return Storage settings.
     */
    public static FileStorageSettings createSensorReadoutsStorageSettings() {
        return new FileStorageSettings(DqoRoot.data_sensor_readouts,
                BuiltInFolderNames.SENSOR_READOUTS,
                PARQUET_FILE_NAME,
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME,
                SensorReadoutsColumnNames.ID_COLUMN_NAME,
                FileStorageSettings.DEFAULT_COPIED_COLUMN_NAMES,
                TablePartitioningPattern.CTM);
    }

    /**
     * Creates or returns a cached split of historic sensor readouts, divided by time series.
     * A single time series is a subset of sensor readouts for a single check (which maps 1-to-1 to a sensor) and a data stream id.
     */
    public SensorReadoutsTimeSeriesMap getHistoricReadoutsTimeSeries() {
        if (this.timeSeriesMap != null &&
                Objects.equals(this.getFirstLoadedMonth(), this.timeSeriesMap.getFirstLoadedMonth()) &&
                Objects.equals(this.getLastLoadedMonth(), this.timeSeriesMap.getLastLoadedMonth())) {
            return this.timeSeriesMap;
        }

		this.timeSeriesMap = new SensorReadoutsTimeSeriesMap(this.getFirstLoadedMonth(), this.getLastLoadedMonth(),
                this.getLoadedMonthlyPartitions());
        return this.timeSeriesMap;
    }
}
