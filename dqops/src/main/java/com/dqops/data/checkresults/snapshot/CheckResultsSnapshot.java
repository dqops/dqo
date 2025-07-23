/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.checkresults.snapshot;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.normalization.CommonColumnNames;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.storage.FileStorageSettings;
import com.dqops.data.storage.ParquetPartitionStorageService;
import com.dqops.data.storage.TableDataSnapshot;
import com.dqops.data.storage.TablePartitioningPattern;
import com.dqops.metadata.sources.PhysicalTableName;
import tech.tablesaw.api.Table;


/**
 * Rule evaluation results snapshot that contains an in-memory check results (rule evaluation results - issues) snapshot
 * for a single table and selected time ranges.
 */
public class CheckResultsSnapshot extends TableDataSnapshot {
    public static final String PARQUET_FILE_NAME = "check_results.0.parquet";

    public static final String[] COPIED_COLUMN_NAMES_FOR_UPDATED_ROWS = {
            CommonColumnNames.CREATED_AT_COLUMN_NAME,
            CommonColumnNames.CREATED_BY_COLUMN_NAME,
            CheckResultsColumnNames.INCIDENT_HASH_COLUMN_NAME
        };

    /**
     * Default constructor that creates a snapshot.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new rule results (captured during the current sensor execution).
     */
    public CheckResultsSnapshot(UserDomainIdentity userIdentity,
                                String connectionName,
                                PhysicalTableName tableName,
                                ParquetPartitionStorageService storageService,
                                Table newResults) {
        super(userIdentity, connectionName, tableName, storageService, createCheckResultsStorageSettings(), newResults);
    }

    /**
     * Default constructor that creates a snapshot.
     * @param userIdentity User identity that specifies the data domain.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param columnNames Column names that will be loaded.
     * @param tableResultsSample Empty table with the expected schema (columns).
     */
    public CheckResultsSnapshot(UserDomainIdentity userIdentity,
                                String connectionName,
                                PhysicalTableName tableName,
                                ParquetPartitionStorageService storageService,
                                String[] columnNames,
                                Table tableResultsSample) {
        super(userIdentity, connectionName, tableName, storageService, createCheckResultsStorageSettings(), columnNames, tableResultsSample);
    }

    /**
     * Creates the storage settings for storing the check results.
     * @return Storage settings.
     */
    public static FileStorageSettings createCheckResultsStorageSettings() {
        return new FileStorageSettings(DqoRoot.data_check_results,
                BuiltInFolderNames.CHECK_RESULTS,
                PARQUET_FILE_NAME,
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME,
                SensorReadoutsColumnNames.ID_COLUMN_NAME,
                COPIED_COLUMN_NAMES_FOR_UPDATED_ROWS,
                TablePartitioningPattern.CTM);
    }
}
