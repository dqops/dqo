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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.incidents.factory.IncidentsTableFactory;
import com.dqops.data.storage.ParquetPartitionStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

/**
 * Incidents snapshot service. Creates snapshots connected to a persistent storage.
 */
@Component
public class IncidentsSnapshotFactoryImpl implements IncidentsSnapshotFactory {
    private final ParquetPartitionStorageService storageService;
    private final IncidentsTableFactory incidentsTableFactory;

    /**
     * Dependency injection constructor.
     * @param storageService Storage service implementation.
     * @param incidentsTableFactory Incidents template table factory.
     */
    @Autowired
    public IncidentsSnapshotFactoryImpl(
            ParquetPartitionStorageService storageService,
            IncidentsTableFactory incidentsTableFactory) {
        this.storageService = storageService;
        this.incidentsTableFactory = incidentsTableFactory;
    }

    /**
     * Creates an empty snapshot that is connected to the incidents storage service that will load requested months on demand.
     * @param connectionName Connection name.
     * @param userIdentity   User identity that specifies the data domain.
     * @return Incidents snapshot connected to a storage service.
     */
    @Override
    public IncidentsSnapshot createSnapshot(String connectionName, UserDomainIdentity userIdentity) {
        Table newIncidentsTable = this.incidentsTableFactory.createEmptyIncidentsTable("new_incidents");
        return new IncidentsSnapshot(userIdentity, connectionName, this.storageService, newIncidentsTable);
    }

    /**
     * Creates an empty, read-only snapshot that is connected to the incidets storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     *
     * @param connectionName    Connection name.
     * @param columnNames       Array of column names to load from parquet files. Other columns will not be loaded.
     * @param userIdentity      User identity that specifies the data domain.
     * @return Incidents snapshot connected to a storage service.
     */
    @Override
    public IncidentsSnapshot createReadOnlySnapshot(String connectionName, String[] columnNames, UserDomainIdentity userIdentity) {
        Table templateIncidentsTable = this.incidentsTableFactory.createEmptyIncidentsTable("template_incidents");
        return new IncidentsSnapshot(userIdentity, connectionName, this.storageService, columnNames, templateIncidentsTable);
    }
}
