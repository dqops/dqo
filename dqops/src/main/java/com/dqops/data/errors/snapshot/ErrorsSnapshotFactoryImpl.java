/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errors.snapshot;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errors.factory.ErrorsTableFactory;
import com.dqops.data.storage.ParquetPartitionStorageService;
import com.dqops.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

/**
 * Errors snapshot service. Creates snapshots connected to a persistent storage.
 */
@Component
public class ErrorsSnapshotFactoryImpl implements ErrorsSnapshotFactory {
    private final ParquetPartitionStorageService storageService;
    private final ErrorsTableFactory errorsTableFactory;

    /**
     * Dependency injection constructor.
     * @param storageService Storage service implementation.
     * @param errorsTableFactory Errors template table factory.
     */
    @Autowired
    public ErrorsSnapshotFactoryImpl(
            ParquetPartitionStorageService storageService,
            ErrorsTableFactory errorsTableFactory) {
        this.storageService = storageService;
        this.errorsTableFactory = errorsTableFactory;
    }

    /**
     * Creates an empty snapshot that is connected to the errors storage service that will load requested months on demand.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userIdentity User identity that specifies the data domain.
     * @return Errors snapshot connected to a storage service.
     */
    @Override
    public ErrorsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userIdentity) {
        Table newErrorsTable = this.errorsTableFactory.createEmptyErrorsTable("new_errors_results");
        return new ErrorsSnapshot(userIdentity, connectionName, physicalTableName, this.storageService, newErrorsTable);
    }

    /**
     * Creates an empty, read-only snapshot that is connected to the errors storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param columnNames       Array of column names to load from parquet files. Other columns will not be loaded.
     * @param userIdentity      User identity that specifies the data domain.
     * @return Errors snapshot connected to a storage service.
     */
    @Override
    public ErrorsSnapshot createReadOnlySnapshot(String connectionName, PhysicalTableName physicalTableName, String[] columnNames, UserDomainIdentity userIdentity) {
        Table templateTable = this.errorsTableFactory.createEmptyErrorsTable("template_errors_results");
        return new ErrorsSnapshot(userIdentity, connectionName, physicalTableName, this.storageService, columnNames, templateTable);
    }
}
