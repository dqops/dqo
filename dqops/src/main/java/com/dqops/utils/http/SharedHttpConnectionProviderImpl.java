/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.http;

import com.dqops.core.configuration.DqoHttpClientPoolConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

/**
 * Returns a shared, preconfigured HTTP Connection provider that maintains a pool of HTTP connections
 * used by a WebFlux web client in DQOps.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SharedHttpConnectionProviderImpl implements SharedHttpConnectionProvider {
    private ConnectionProvider connectionProvider;
    private final DqoHttpClientPoolConfigurationProperties httpClientPoolConfigurationProperties;

    /**
     * Creates a shared http connection provider.
     * @param httpClientPoolConfigurationProperties Configuration parameters for the http connection pool.
     */
    @Autowired
    public SharedHttpConnectionProviderImpl(DqoHttpClientPoolConfigurationProperties httpClientPoolConfigurationProperties) {
        this.httpClientPoolConfigurationProperties = httpClientPoolConfigurationProperties;
    }

    /**
     * Returns a shared HTTP connection provider (connection pool).
     * @return HTTP connection provider (connection pool).
     */
    @Override
    public ConnectionProvider getConnectionProvider() {
        synchronized (this) {
            if (this.connectionProvider == null) {
                ConnectionProvider.Builder builder = ConnectionProvider.builder("dqo-shared");

                if (this.httpClientPoolConfigurationProperties.getMaxConnections() != null) {
                    builder = builder.maxConnections(this.httpClientPoolConfigurationProperties.getMaxConnections());
                }

                if (this.httpClientPoolConfigurationProperties.getMaxIdleTimeSeconds() != null) {
                    builder = builder.maxIdleTime(Duration.ofSeconds(this.httpClientPoolConfigurationProperties.getMaxIdleTimeSeconds()));
                }

                if (this.httpClientPoolConfigurationProperties.getEvictInBackgroundSeconds() != null) {
                    builder = builder.evictInBackground(Duration.ofSeconds(this.httpClientPoolConfigurationProperties.getEvictInBackgroundSeconds()));
                }

                if (this.httpClientPoolConfigurationProperties.getMaxLifeTimeSeconds() != null) {
                    builder = builder.maxLifeTime(Duration.ofSeconds(this.httpClientPoolConfigurationProperties.getMaxLifeTimeSeconds()));
                }

                if (this.httpClientPoolConfigurationProperties.getPendingAcquireTimeoutSeconds() != null) {
                    builder = builder.pendingAcquireTimeout(Duration.ofSeconds(this.httpClientPoolConfigurationProperties.getPendingAcquireTimeoutSeconds()));
                }

                this.connectionProvider = builder.build();
            }

            return this.connectionProvider;
        }
    }
}
