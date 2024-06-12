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
package com.dqops.data.incidents.snapshot;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.storage.FileStorageSettings;
import com.dqops.data.storage.ParquetPartitionStorageService;
import com.dqops.data.storage.TableDataSnapshot;
import com.dqops.data.storage.TablePartitioningPattern;
import tech.tablesaw.api.Table;

/**
 * Stores incidents for one month (based on the first_seen) timestamp.
 */
public class IncidentsSnapshot extends TableDataSnapshot {
    public static final String PARQUET_FILE_NAME = "incidents.0.parquet";

    /**
     * Default constructor that creates a snapshot.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new incidents.
     */
    public IncidentsSnapshot(UserDomainIdentity userIdentity,
                             String connectionName,
                             ParquetPartitionStorageService storageService,
                             Table newResults) {
        super(userIdentity, connectionName, null, storageService, createIncidentsStorageSettings(), newResults);
    }

    /**
     * Creates a read-only incident results snapshot limited to a set of columns.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param columnNames Column names that will be loaded.
     * @param tableResultsSample Empty table with the expected schema (columns).
     */
    public IncidentsSnapshot(UserDomainIdentity userIdentity,
                             String connectionName,
                             ParquetPartitionStorageService storageService,
                             String[] columnNames,
                             Table tableResultsSample) {
        super(userIdentity, connectionName, null, storageService, createIncidentsStorageSettings(), columnNames, tableResultsSample);
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
                FileStorageSettings.DEFAULT_COPIED_COLUMN_NAMES,
                TablePartitioningPattern.CM);
    }
}
