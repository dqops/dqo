/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
