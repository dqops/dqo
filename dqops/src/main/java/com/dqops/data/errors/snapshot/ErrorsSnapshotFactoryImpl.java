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
