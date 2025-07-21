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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactory;
import com.dqops.data.storage.ParquetPartitionStorageService;
import com.dqops.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

/**
 * Sensor readouts snapshot service. Creates snapshots connected to a persistent storage.
 */
@Component
public class SensorReadoutsSnapshotFactoryImpl implements SensorReadoutsSnapshotFactory {
    private final ParquetPartitionStorageService storageService;
    private final SensorReadoutsTableFactory sensorReadoutsTableFactory;

    /**
     * Dependency injection constructor.
     * @param storageService Storage service implementation.
     */
    @Autowired
    public SensorReadoutsSnapshotFactoryImpl(
            ParquetPartitionStorageService storageService,
			SensorReadoutsTableFactory sensorReadoutsTableFactory) {
        this.storageService = storageService;
        this.sensorReadoutsTableFactory = sensorReadoutsTableFactory;
    }

    /**
     * Creates an empty snapshot that is connected to the sensor readouts storage service that will load requested months on demand.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userIdentity User identity that specifies the data domain.
     * @return Sensor readouts snapshot connected to a storage service.
     */
    @Override
    public SensorReadoutsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userIdentity) {
        Table newSensorReadouts = this.sensorReadoutsTableFactory.createEmptySensorReadoutsTable("new_sensor_readouts");
        return new SensorReadoutsSnapshot(userIdentity, connectionName, physicalTableName, this.storageService, newSensorReadouts);
    }

    /**
     * Creates an empty, read-only snapshot that is connected to the sensor readout storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param columnNames       Array of column names to load from parquet files. Other columns will not be loaded.
     * @param userIdentity      User identity that specifies the data domain.
     * @return Rule result snapshot connected to a storage service.
     */
    @Override
    public SensorReadoutsSnapshot createReadOnlySnapshot(String connectionName, PhysicalTableName physicalTableName, String[] columnNames, UserDomainIdentity userIdentity) {
        Table templateSensorReadouts = this.sensorReadoutsTableFactory.createEmptySensorReadoutsTable("template_sensor_readouts");
        return new SensorReadoutsSnapshot(userIdentity, connectionName, physicalTableName, this.storageService, columnNames, templateSensorReadouts);
    }
}
