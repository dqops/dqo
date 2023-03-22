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
package ai.dqo.data.checkresults.snapshot;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.storage.FileStorageSettings;
import ai.dqo.data.storage.ParquetPartitionStorageService;
import ai.dqo.data.storage.TableDataSnapshot;
import ai.dqo.metadata.sources.PhysicalTableName;
import net.tlabs.tablesaw.parquet.TablesawParquetWriteOptions;
import tech.tablesaw.api.Table;


/**
 * Rule evaluation results snapshot that contains an in-memory check results (rule evaluation results - issues) snapshot
 * for a single table and selected time ranges.
 */
public class CheckResultsSnapshot extends TableDataSnapshot {
    public static final String PARQUET_FILE_NAME = "check_results.0.parquet";

    /**
     * Default constructor that creates a snapshot.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new rule results (captured during the current sensor execution).
     */
    public CheckResultsSnapshot(String connectionName,
                                PhysicalTableName tableName,
                                ParquetPartitionStorageService storageService,
                                Table newResults) {
        super(connectionName, tableName, storageService, createRuleResultsStorageSettings(), newResults);
    }

    /**
     * Default constructor that creates a snapshot.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param columnNames Column names that will be loaded.
     * @param tableResultsSample Empty table with the expected schema (columns).
     */
    public CheckResultsSnapshot(String connectionName,
                                PhysicalTableName tableName,
                                ParquetPartitionStorageService storageService,
                                String[] columnNames,
                                Table tableResultsSample) {
        super(connectionName, tableName, storageService, createRuleResultsStorageSettings(), columnNames, tableResultsSample);
    }

    /**
     * Creates the storage settings for storing the rule results.
     * @return Storage settings.
     */
    public static FileStorageSettings createRuleResultsStorageSettings() {
        return new FileStorageSettings(DqoRoot.data_check_results,
                BuiltInFolderNames.CHECK_RESULTS,
                PARQUET_FILE_NAME,
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME,
                SensorReadoutsColumnNames.ID_COLUMN_NAME,
                TablesawParquetWriteOptions.CompressionCodec.UNCOMPRESSED);
    }
}
