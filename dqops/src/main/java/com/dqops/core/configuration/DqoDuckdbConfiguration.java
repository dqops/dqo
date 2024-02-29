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
     * Memory limit string. // todo: description
     * @return Memory limit.
     */
    public String getMemoryLimit() {
        return memoryLimit;
    }

    /**
     * Sets memory limit string. // todo: description
     * @param memoryLimit Memory limit.
     */
    public void setMemoryLimit(String memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    /**
     * Threads count. // todo: description
     * @return Threads count.
     */
    public int getThreads() {
        return threads;
    }

    /**
     * Sets the threads count.// todo: description
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
