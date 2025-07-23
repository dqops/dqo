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
