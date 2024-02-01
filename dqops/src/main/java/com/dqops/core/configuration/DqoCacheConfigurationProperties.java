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

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.cache - for configuring how long the yaml files, parquet files and file lists are cached.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.cache")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoCacheConfigurationProperties implements Cloneable {
    /**
     * The time in seconds to expire the cache entries since they were added to the cache.
     */
    private long expireAfterSeconds = 86400;  // 24h

    /**
     * The maximum number of specification files to cache.
     */
    private long yamlFilesLimit = 1000000;

    /**
     * The maximum number of folders for which the list of files are cached to avoid listing the files.
     */
    private long fileListsLimit = 1000000;

    /**
     * The maximum fraction of the JVM heap memory (configured using the -Xmx java parameter) that is used to cache parquet files in memory. The default value of 0.6 means that up to 50% of the JVM heap memory can be used for caching files.
     * The value of the reserved-heap-memory-bytes is subtracted from the total memory size (-Xmx parameter value) before the memory fraction is calculated.
     */
    private double parquetCacheMemoryFraction = 0.6;

    /**
     * The memory size (in bytes) that is not subtracted from the total JVM heap memory before the memory fraction dedicated for the parquet cache is calculated.
     */
    private long reservedHeapMemoryBytes = 200L * 1000 * 1000;

    /**
     * Enables or disables the specification cache.
     */
    private boolean enable = true;

    /**
     * Use a file watcher to detect file system changes.
     */
    private boolean watchFileSystemChanges = true;

    /**
     * The delay in milliseconds between processing file changes.
     */
    private long processFileChangesDelayMillis = 100;

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoCacheConfigurationProperties clone() {
        try {
            DqoCacheConfigurationProperties cloned = (DqoCacheConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
