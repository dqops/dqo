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
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * HTTP client configuration parameters.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.http-client.pool")
@EqualsAndHashCode(callSuper = false)
public class DqoHttpClientPoolConfigurationProperties implements Cloneable {
    private Integer maxConnections;
    private Integer maxIdleTimeSeconds;
    private Integer evictInBackgroundSeconds;
    private Integer maxLifeTimeSeconds;
    private Integer pendingAcquireTimeoutSeconds;

    /**
     * Returns the maximum number of http connections in the pool.
     * @return Maximum connections.
     */
    public Integer getMaxConnections() {
        return maxConnections;
    }

    /**
     * Sets the maximum number of http connections in the pool.
     * @param maxConnections Maximum open http connections in the pool.
     */
    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    /**
     * Returns the number of seconds that an idle http connection is stored in the connection pool.
     * @return Max connection idle seconds.
     */
    public Integer getMaxIdleTimeSeconds() {
        return maxIdleTimeSeconds;
    }

    /**
     * Sets the time limit in seconds to keep an idle http connection in the pool.
     * @param maxIdleTimeSeconds Max idle seconds.
     */
    public void setMaxIdleTimeSeconds(Integer maxIdleTimeSeconds) {
        this.maxIdleTimeSeconds = maxIdleTimeSeconds;
    }

    /**
     * Returns the frequency of cleaning idle or outdated http connections from the pool.
     * @return Seconds between pool eviction operations.
     */
    public Integer getEvictInBackgroundSeconds() {
        return evictInBackgroundSeconds;
    }

    /**
     * Sets the frequency (delay in seconds) between the http connection pool cleanup operations.
     * @param evictInBackgroundSeconds Delay in seconds between the pool clean ups.
     */
    public void setEvictInBackgroundSeconds(Integer evictInBackgroundSeconds) {
        this.evictInBackgroundSeconds = evictInBackgroundSeconds;
    }

    /**
     * Returns the maximum number of seconds that a http connection could be used.
     * @return Max lifetime of a connection before it is terminated.
     */
    public Integer getMaxLifeTimeSeconds() {
        return maxLifeTimeSeconds;
    }

    /**
     * Sets the maximum lifetime of a http connection.
     * @param maxLifeTimeSeconds Max seconds to use a http connection.
     */
    public void setMaxLifeTimeSeconds(Integer maxLifeTimeSeconds) {
        this.maxLifeTimeSeconds = maxLifeTimeSeconds;
    }

    /**
     * Returns the number of seconds to wait until a http connection is available in the pool.
     * @return Number of seconds to wait for a connection.
     */
    public Integer getPendingAcquireTimeoutSeconds() {
        return pendingAcquireTimeoutSeconds;
    }

    /**
     * Sets the number of seconds to wait to get an open http connection.
     * @param pendingAcquireTimeoutSeconds Time limit to wait for a new connection.
     */
    public void setPendingAcquireTimeoutSeconds(Integer pendingAcquireTimeoutSeconds) {
        this.pendingAcquireTimeoutSeconds = pendingAcquireTimeoutSeconds;
    }

    /**
     * Clones the current object.
     * @return
     */
    @Override
    public DqoHttpClientPoolConfigurationProperties clone() {
        try {
            return (DqoHttpClientPoolConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
