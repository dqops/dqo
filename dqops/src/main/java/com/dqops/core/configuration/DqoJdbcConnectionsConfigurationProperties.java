/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

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
    private int expireAfterAccessSeconds = 10;

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
