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

/**
 * Incidents snapshot service. Creates snapshots connected to a persistent storage.
 */
public interface IncidentsSnapshotFactory {
    /**
     * Creates an empty snapshot that is connected to the incidents storage service that will load requested months on demand.
     *
     * @param connectionName Connection name.
     * @param userIdentity   User identity that specifies the data domain.
     * @return Incidents snapshot connected to a storage service.
     */
    IncidentsSnapshot createSnapshot(String connectionName, UserDomainIdentity userIdentity);

    /**
     * Creates an empty, read-only snapshot that is connected to the incidets storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     *
     * @param connectionName Connection name.
     * @param columnNames    Array of column names to load from parquet files. Other columns will not be loaded.
     * @param userIdentity   User identity that specifies the data domain.
     * @return Incidents snapshot connected to a storage service.
     */
    IncidentsSnapshot createReadOnlySnapshot(String connectionName, String[] columnNames, UserDomainIdentity userIdentity);
}
