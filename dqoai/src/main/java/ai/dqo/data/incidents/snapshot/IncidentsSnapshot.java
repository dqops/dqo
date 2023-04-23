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
package ai.dqo.data.incidents.snapshot;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.data.incidents.factory.IncidentsColumnNames;
import ai.dqo.data.storage.FileStorageSettings;
import ai.dqo.data.storage.ParquetPartitionStorageService;
import ai.dqo.data.storage.TableDataSnapshot;
import ai.dqo.data.storage.TablePartitioningPattern;
import ai.dqo.metadata.sources.PhysicalTableName;
import tech.tablesaw.api.Table;

/**
 * Stores incidents for one month (based on the first_seen) timestamp.
 */
public class IncidentsSnapshot extends TableDataSnapshot {
    public static final String PARQUET_FILE_NAME = "incidents.0.parquet";

    /**
     * Default constructor that creates a snapshot.
     * @param connectionName Connection name.
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new incidents.
     */
    public IncidentsSnapshot(String connectionName,
                             ParquetPartitionStorageService storageService,
                             Table newResults) {
        super(connectionName, null, storageService, createIncidentsStorageSettings(), newResults);
    }

    /**
     * Creates a read-only incident results snapshot limited to a set of columns.
     * @param connectionName Connection name.
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param columnNames Column names that will be loaded.
     * @param tableResultsSample Empty table with the expected schema (columns).
     */
    public IncidentsSnapshot(String connectionName,
                             ParquetPartitionStorageService storageService,
                             String[] columnNames,
                             Table tableResultsSample) {
        super(connectionName, null, storageService, createIncidentsStorageSettings(), columnNames, tableResultsSample);
    }

    /**
     * Creates the storage settings for storing the incidents.
     * @return Storage settings.
     */
    public static FileStorageSettings createIncidentsStorageSettings() {
        return new FileStorageSettings(DqoRoot.data_incidents,
                BuiltInFolderNames.INCIDENTS,
                PARQUET_FILE_NAME,
                IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME,
                IncidentsColumnNames.ID_COLUMN_NAME,
                TablePartitioningPattern.CM);
    }
}
