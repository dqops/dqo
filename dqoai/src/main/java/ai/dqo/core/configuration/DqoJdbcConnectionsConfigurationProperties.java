/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.jdbc.connections that configures JDBC connection pooling.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.jdbc.connections")
@EqualsAndHashCode(callSuper = false)
public class DqoJdbcConnectionsConfigurationProperties implements Cloneable {
    private int maxConnectionInPool = 1000;
    private int expireAfterAccessSeconds = 1800;

    /**
     * Returns the maximum number of connections in the jdbc connection pool.
     * @return Max numer of pooled connections.
     */
    public int getMaxConnectionInPool() {
        return maxConnectionInPool;
    }

    /**
     * Sets the maximum number of connections in the JDBC connection pool, shared across all data sources using JDBC drivers.
     * @param maxConnectionInPool Maximum number of connections in the pool.
     */
    public void setMaxConnectionInPool(int maxConnectionInPool) {
        this.maxConnectionInPool = maxConnectionInPool;
    }

    /**
     * Returns the number of seconds when a connection in a pool is expired after the last access.
     * @return The number of seconds when a connection in a pool is expired after the last access.
     */
    public int getExpireAfterAccessSeconds() {
        return expireAfterAccessSeconds;
    }

    /**
     * Sets the number of seconds when a connection in a JDBC pool is expired after the last access.
     * @param expireAfterAccessSeconds Number of seconds.
     */
    public void setExpireAfterAccessSeconds(int expireAfterAccessSeconds) {
        this.expireAfterAccessSeconds = expireAfterAccessSeconds;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoJdbcConnectionsConfigurationProperties clone() {
        try {
            DqoJdbcConnectionsConfigurationProperties cloned = (DqoJdbcConnectionsConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
