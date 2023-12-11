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
