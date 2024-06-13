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
package com.dqops.data.errors.snapshot;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.storage.FileStorageSettings;
import com.dqops.data.storage.ParquetPartitionStorageService;
import com.dqops.data.storage.TableDataSnapshot;
import com.dqops.data.storage.TablePartitioningPattern;
import com.dqops.metadata.sources.PhysicalTableName;
import tech.tablesaw.api.Table;

/**
 * Check execution errors snapshot that contains an in-memory error result snapshot
 * for a single table and selected time ranges.
 */
public class ErrorsSnapshot extends TableDataSnapshot {
    public static final String PARQUET_FILE_NAME = "errors.0.parquet";

    /**
     * Default constructor that creates an error's snapshot.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new errors (captured during the current sensor execution).
     */
    public ErrorsSnapshot(UserDomainIdentity userIdentity,
                          String connectionName,
                          PhysicalTableName tableName,
                          ParquetPartitionStorageService storageService,
                          Table newResults) {
        super(userIdentity, connectionName, tableName, storageService, createErrorsStorageSettings(), newResults);
    }

    /**
     * Creates a read-only errors snapshot limited to a set of columns.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param columnNames Column names that will be loaded.
     * @param tableResultsSample Empty table with the expected schema (columns).
     */
    public ErrorsSnapshot(UserDomainIdentity userIdentity,
                          String connectionName,
                          PhysicalTableName tableName,
                          ParquetPartitionStorageService storageService,
                          String[] columnNames,
                          Table tableResultsSample) {
        super(userIdentity, connectionName, tableName, storageService, createErrorsStorageSettings(), columnNames, tableResultsSample);
    }

    /**
     * Creates the storage settings for storing the errors.
     * @return Storage settings.
     */
    public static FileStorageSettings createErrorsStorageSettings() {
        return new FileStorageSettings(DqoRoot.data_errors,
                BuiltInFolderNames.ERRORS,
                PARQUET_FILE_NAME,
                ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME,
                ErrorsColumnNames.ID_COLUMN_NAME,
                FileStorageSettings.DEFAULT_COPIED_COLUMN_NAMES,
                TablePartitioningPattern.CTM);
    }

    /**
     * Saves all results to a persistent storage (like files). New rows are added, rows with matching IDs are updated.
     * Rows identified by an ID column are deleted.
     * [NOT ON READ-ONLY]
     */
    @Override
    public void save() {
        this.dropDuplicateNewRows();

        super.save();
    }
}
