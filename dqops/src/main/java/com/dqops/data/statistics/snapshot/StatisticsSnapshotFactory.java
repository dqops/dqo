/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.statistics.snapshot;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.sources.PhysicalTableName;

/**
 * Statistics results snapshot service. Creates snapshots connected to a persistent storage.
 */
public interface StatisticsSnapshotFactory {
    /**
     * Creates an empty snapshot that is connected to the statistics result storage service that will load requested months on demand.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param userIdentity      User identity that specifies the data domain.
     * @return Statistics results snapshot connected to a storage service.
     */
    StatisticsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userIdentity);

    /**
     * Creates an empty, read-only snapshot that is connected to the statistics results storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param columnNames Array of column names to load from parquet files. Other columns will not be loaded.
     * @param userIdentity User identity that specifies the data domain.
     * @return Statistics results snapshot connected to a storage service.
     */
    StatisticsSnapshot createReadOnlySnapshot(String connectionName, PhysicalTableName physicalTableName, String[] columnNames, UserDomainIdentity userIdentity);
}
