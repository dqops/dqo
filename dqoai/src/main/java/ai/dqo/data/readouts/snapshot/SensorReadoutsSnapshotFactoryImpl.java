/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.readouts.snapshot;

import ai.dqo.data.readouts.factory.SensorReadoutsTableFactory;
import ai.dqo.data.storage.ParquetPartitionStorageService;
import ai.dqo.metadata.sources.PhysicalTableName;
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
     * @return Sensor readouts snapshot connected to a storage service.
     */
    public SensorReadoutsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName) {
        Table newSensorReadouts = this.sensorReadoutsTableFactory.createEmptySensorReadoutsTable("new_sensor_readouts");
        return new SensorReadoutsSnapshot(connectionName, physicalTableName, this.storageService, newSensorReadouts);
    }
}
