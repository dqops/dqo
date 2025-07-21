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
