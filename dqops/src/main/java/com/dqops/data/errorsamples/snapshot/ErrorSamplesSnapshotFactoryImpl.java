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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errorsamples.factory.ErrorSamplesTableFactory;
import com.dqops.data.storage.ParquetPartitionStorageService;
import com.dqops.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

/**
 * Error samples results snapshot service. Creates snapshots connected to a persistent storage.
 */
@Component
public class ErrorSamplesSnapshotFactoryImpl implements ErrorSamplesSnapshotFactory {
    private final ParquetPartitionStorageService storageService;
    private final ErrorSamplesTableFactory errorSamplesTableFactory;

    /**
     * Dependency injection constructor.
     * @param storageService Storage service implementation.
     * @param errorSamplesTableFactory Error samples results template table factory.
     */
    @Autowired
    public ErrorSamplesSnapshotFactoryImpl(
            ParquetPartitionStorageService storageService,
            ErrorSamplesTableFactory errorSamplesTableFactory) {
        this.storageService = storageService;
        this.errorSamplesTableFactory = errorSamplesTableFactory;
    }

    /**
     * Creates an empty snapshot that is connected to the error samples result storage service that will load requested months on demand.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userIdentity User identity that specifies the data domain.
     * @return Error samples results snapshot connected to a storage service.
     */
    @Override
    public ErrorSamplesSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userIdentity) {
        Table newStatisticsTable = this.errorSamplesTableFactory.createEmptyErrorSamplesTable("new_error_samples");
        return new ErrorSamplesSnapshot(userIdentity, connectionName, physicalTableName, this.storageService, newStatisticsTable);
    }

    /**
     * Creates an empty, read-only snapshot that is connected to the error samples results storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param columnNames       Array of column names to load from parquet files. Other columns will not be loaded.
     * @param userIdentity      User identity that specifies the data domain.
     * @return Error samples results snapshot connected to a storage service.
     */
    @Override
    public ErrorSamplesSnapshot createReadOnlySnapshot(String connectionName, PhysicalTableName physicalTableName, String[] columnNames, UserDomainIdentity userIdentity) {
        Table templateStatisticsTable = this.errorSamplesTableFactory.createEmptyErrorSamplesTable("template_error_samples");
        return new ErrorSamplesSnapshot(userIdentity, connectionName, physicalTableName, this.storageService, columnNames, templateStatisticsTable);
    }
}
