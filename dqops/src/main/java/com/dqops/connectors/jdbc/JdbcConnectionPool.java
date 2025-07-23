/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.jdbc;

import com.dqops.metadata.sources.ConnectionSpec;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.concurrent.Callable;


/**
 * JDDB connection pool that supports multiple connections.
 */
public interface JdbcConnectionPool {
    /**
     * Returns or creates a data source for the given connection specification.
     * @param connectionSpec Connection specification (should be not mutable).
     * @param makeConfig Lambda to create a hikari connection configuration.
     * @return Data source.
     */
    HikariDataSource getDataSource(ConnectionSpec connectionSpec, Callable<HikariConfig> makeConfig);
}
