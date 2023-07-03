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
