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
 * Configuration POJO with the configuration for DuckDB.
 * Properties are mapped to the "dqo.duckdb." prefix that are responsible the configuration of a DuckDB.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.duckdb")
@EqualsAndHashCode(callSuper = false)
public class DqoDuckdbConfiguration implements Cloneable {
    private String memoryLimit;
    private int threads = 1000;

    /**
     * The maximum memory of the system (e.g., 1GB). When not set, DuckDB use the 80% of RAM
     * @return Memory limit.
     */
    public String getMemoryLimit() {
        return memoryLimit;
    }

    /**
     * Sets the maximum memory of the system (e.g., 1GB). When not set, DuckDB use the 80% of RAM
     * @param memoryLimit Memory limit.
     */
    public void setMemoryLimit(String memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    /**
     * The number of total threads used by the system.
     * @return Threads count.
     */
    public int getThreads() {
        return threads;
    }

    /**
     * Sets the number of total threads used by the system.
     * @param threads Threads count.
     */
    public void setThreads(int threads) {
        this.threads = threads;
    }

    /**
     * Clones the current object.
     * @return Cloned instance.
     */
    @Override
    public DqoDuckdbConfiguration clone() {
        try {
            return (DqoDuckdbConfiguration)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
