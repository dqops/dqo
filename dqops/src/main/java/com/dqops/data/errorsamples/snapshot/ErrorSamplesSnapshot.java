/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errorsamples.snapshot;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.data.errorsamples.factory.ErrorSamplesColumnNames;
import com.dqops.data.storage.FileStorageSettings;
import com.dqops.data.storage.ParquetPartitionStorageService;
import com.dqops.data.storage.TableDataSnapshot;
import com.dqops.data.storage.TablePartitioningPattern;
import com.dqops.metadata.sources.PhysicalTableName;
import tech.tablesaw.api.Table;


/**
 * Error samples results snapshot that contains an in-memory error sampling results snapshot
 * for a single table and selected time ranges.
 */
public class ErrorSamplesSnapshot extends TableDataSnapshot {
    public static final String PARQUET_FILE_NAME = "errorsamples.0.parquet";

    /**
     * Default constructor that creates a snapshot.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new error samples results (captured during the current sensor execution).
     */
    public ErrorSamplesSnapshot(UserDomainIdentity userIdentity,
                                String connectionName,
                                PhysicalTableName tableName,
                                ParquetPartitionStorageService storageService,
                                Table newResults) {
        super(userIdentity, connectionName, tableName, storageService, createErrorSamplesStorageSettings(), newResults);
    }

    /**
     * Creates a read-only error samples results snapshot limited to a set of columns.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param columnNames Column names that will be loaded.
     * @param tableResultsSample Empty table with the expected schema (columns).
     */
    public ErrorSamplesSnapshot(UserDomainIdentity userIdentity,
                                String connectionName,
                                PhysicalTableName tableName,
                                ParquetPartitionStorageService storageService,
                                String[] columnNames,
                                Table tableResultsSample) {
        super(userIdentity, connectionName, tableName, storageService, createErrorSamplesStorageSettings(), columnNames, tableResultsSample);
    }

    /**
     * Creates the storage settings for storing the statistics results.
     * @return Storage settings.
     */
    public static FileStorageSettings createErrorSamplesStorageSettings() {
        return new FileStorageSettings(DqoRoot.data_error_samples,
                BuiltInFolderNames.ERROR_SAMPLES,
                PARQUET_FILE_NAME,
                ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME,
                ErrorSamplesColumnNames.ID_COLUMN_NAME,
                FileStorageSettings.DEFAULT_COPIED_COLUMN_NAMES,
                TablePartitioningPattern.CTM);
    }
}
