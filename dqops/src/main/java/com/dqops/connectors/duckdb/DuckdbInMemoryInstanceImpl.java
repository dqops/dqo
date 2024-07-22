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

package com.dqops.connectors.duckdb;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.duckdb.config.DuckdbReadMode;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.duckdb.DuckDBConnection;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A singleton that holds a shared DuckDB instance.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DuckdbInMemoryInstanceImpl implements DisposableBean, DuckdbInMemoryInstance {
    private final Object lock = new Object();
    private final DuckdbConnectionProvider duckdbConnectionProvider;
    private DuckdbSourceConnection sharedDqopsConnection;
    private DuckDBConnection sharedJdbcConnection;

    @Autowired
    public DuckdbInMemoryInstanceImpl(DuckdbConnectionProvider duckdbConnectionProvider) {
        this.duckdbConnectionProvider = duckdbConnectionProvider;
    }

    /**
     * Creates a new JDBC connection to a shared in-memory DuckDB instance.
     * @return JDBC connection to DuckDB.
     */
    @Override
    public Connection duplicateInMemoryJdbcConnection() {
        synchronized (this.lock) {
            if (this.sharedDqopsConnection == null) {
                ConnectionSpec connectionSpec = new ConnectionSpec();
                connectionSpec.setProviderType(ProviderType.duckdb);
                DuckdbParametersSpec duckdbParameters = new DuckdbParametersSpec();
                duckdbParameters.setReadMode(DuckdbReadMode.in_memory);
                connectionSpec.setDuckdb(duckdbParameters);

                this.sharedDqopsConnection = this.duckdbConnectionProvider.createConnection(connectionSpec, false, null);
                try {
                    Class.forName("org.duckdb.DuckDBDriver");
                } catch (ClassNotFoundException e) {
                    throw new DqoRuntimeException(e);
                }

                try {
                    this.sharedJdbcConnection = (DuckDBConnection)DriverManager.getConnection("jdbc:duckdb::memory:");
                } catch (SQLException e) {
                    throw new DqoRuntimeException("Failed to create an in-memory DuckDB instance, error: " + e.getMessage(), e);
                }
                this.sharedDqopsConnection.setJdbcConnection(this.sharedJdbcConnection);
                this.sharedDqopsConnection.initializeInMemoryConfiguration();
            }
        }

        try {
            return this.sharedJdbcConnection.duplicate();
        }
        catch (SQLException ex) {
            throw new DqoRuntimeException("Failed to create a shared connection to an in-memory DuckDB instance: " + ex.getMessage(), ex);
        }
    }

    /**
     * Destroys the instance and closes the connection to an in-memory instance of DuckDB.
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        if (this.sharedDqopsConnection != null) {
            this.sharedJdbcConnection.close();
        }
    }
}
