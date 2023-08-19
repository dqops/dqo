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

import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link DqoCacheConfigurationProperties}
 */
public class DqoCacheSpecConfigurationPropertiesObjectMother {
    /**
     * Returns a real cache specification configuration.
     * @return Real cache configuration.
     */
    public static DqoCacheConfigurationProperties getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(DqoCacheConfigurationProperties.class).clone();
    }

    /**
     * Returns a configuration for a local file cache that caches files, but does not use a file system watcher to detect changes.
     * @return Cache configuration with enabled caching but no file watcher.
     */
    public static DqoCacheConfigurationProperties getWithCacheEnabledButNoFileSystemWatching() {
        DqoCacheConfigurationProperties cacheSpecConfigurationProperties = new DqoCacheConfigurationProperties();
        cacheSpecConfigurationProperties.setEnable(true);
        cacheSpecConfigurationProperties.setWatchFileSystemChanges(false);
        return cacheSpecConfigurationProperties;
    }

    /**
     * Creates a cache configuration that disables caching.
     * @return Cache configuration with disabled caching.
     */
    public static DqoCacheConfigurationProperties createWithDisabledCache() {
        DqoCacheConfigurationProperties cacheSpecConfigurationProperties = new DqoCacheConfigurationProperties();
        cacheSpecConfigurationProperties.setEnable(false);
        cacheSpecConfigurationProperties.setWatchFileSystemChanges(false);
        return cacheSpecConfigurationProperties;
    }
}
