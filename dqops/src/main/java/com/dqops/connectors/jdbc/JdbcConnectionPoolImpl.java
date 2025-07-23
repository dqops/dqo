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
