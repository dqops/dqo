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
 * Configuration POJO with the configuration for the dqo.cache.spec - for configuring how long the yaml files and file lists are cached.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.cache.spec")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoCacheSpecConfigurationProperties implements Cloneable {
    /**
     * The time in seconds to expire the cache entries since they were added to the cache.
     */
    private long expireAfterSeconds = 3600;

    /**
     * The maximum number of objects to cache.
     */
    private long maximumSize = 1000000;

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
    private long processFileChangesDelayMs = 100;

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoCacheSpecConfigurationProperties clone() {
        try {
            DqoCacheSpecConfigurationProperties cloned = (DqoCacheSpecConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
