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

import com.dqops.core.configuration.DqoJdbcConnectionsConfigurationProperties;
import com.dqops.metadata.sources.ConnectionSpec;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * JDBC connection pool that supports multiple connections.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class JdbcConnectionPoolImpl implements JdbcConnectionPool {
    /**
     * Data sources cache.
     */
    private Cache<ConnectionSpec, HikariDataSource> dataSourceCache;
    private DqoJdbcConnectionsConfigurationProperties jdbcConnectionsConfigurationProperties;


    @Autowired
    public JdbcConnectionPoolImpl(DqoJdbcConnectionsConfigurationProperties jdbcConnectionsConfigurationProperties) {
        this.jdbcConnectionsConfigurationProperties = jdbcConnectionsConfigurationProperties;

        this. dataSourceCache =
                CacheBuilder.newBuilder()
                        .maximumSize(jdbcConnectionsConfigurationProperties.getMaxConnectionInPool())
                        .expireAfterAccess(jdbcConnectionsConfigurationProperties.getExpireAfterAccessSeconds(), TimeUnit.SECONDS)
                        .removalListener(notification -> onRemoveDataSource(notification))
                        .build();
    }

    /**
     * Notification called when the cache decided to remove a data source.
     * @param notification Notification that a data source is removed.
     */
    private void onRemoveDataSource(RemovalNotification<Object, Object> notification) {
        try {
            HikariDataSource dataSource = (HikariDataSource)notification.getValue();
            dataSource.close();
        }
        catch (Exception ex) {
            log.error("Cannot close a data source for the connection: " + notification.getKey().toString() + ", error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Returns or creates a data source for the given connection specification.
     * @param connectionSpec Connection specification (should be not mutable).
     * @param makeConfig Lambda to create a hikari connection configuration.
     * @return Data source.
     */
    public HikariDataSource getDataSource(ConnectionSpec connectionSpec, Callable<HikariConfig> makeConfig) {
        assert connectionSpec != null;

        try {
            return this.dataSourceCache.get(connectionSpec, () -> {
                HikariConfig hikariConfig = makeConfig.call();
                assert hikariConfig != null;
                return new HikariDataSource(hikariConfig);
            });
        } catch (ExecutionException e) {
            throw new JdbConnectionPoolCreateException("Cannot create a JDBC connection for " + connectionSpec.getConnectionName(), e);
        }
    }
}
