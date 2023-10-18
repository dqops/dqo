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
