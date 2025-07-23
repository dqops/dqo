/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.filesystem.cache;

import com.dqops.core.configuration.DqoCacheSpecConfigurationPropertiesObjectMother;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceObjectMother;
import com.dqops.core.similarity.DummyTableSimilarityRefreshService;
import com.dqops.core.similarity.TableSimilarityRefreshServiceProviderImpl;
import com.dqops.data.checkresults.statuscache.TableStatusCacheProviderImpl;
import com.dqops.metadata.labels.labelloader.DummyLabelsIndexer;
import com.dqops.metadata.labels.labelloader.LabelsIndexerProviderImpl;
import com.dqops.metadata.lineage.lineagecache.DummyTableLineageCache;
import com.dqops.metadata.lineage.lineagecache.TableLineageCacheProviderImpl;
import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link LocalFileSystemCache}
 */
public class LocalFileSystemCacheObjectMother {
    /**
     * Returns a real file system cache - the singleton instance.
     * @return Real file system cache.
     */
    public static LocalFileSystemCache getRealCache() {
        LocalFileSystemCache fileSystemCache = BeanFactoryObjectMother.getBeanFactory().getBean(LocalFileSystemCache.class);
        return fileSystemCache;
    }

    /**
     * Invalidates the cache.
     */
    public static void invalidateAll() {
        getRealCache().invalidateAll();
    }

    /**
     * Creates a new file system cache.
     * @return New file system cache.
     */
    public static LocalFileSystemCache createNewCache() {
        LocalFileSystemCacheImpl localFileSystemCache = new LocalFileSystemCacheImpl(
                DqoCacheSpecConfigurationPropertiesObjectMother.getWithCacheEnabledButNoFileSystemWatching(),
                new TableStatusCacheProviderImpl(BeanFactoryObjectMother.getBeanFactory()),
                new LabelsIndexerProviderImpl(BeanFactoryObjectMother.getBeanFactory(), new DummyLabelsIndexer()),
                new TableLineageCacheProviderImpl(BeanFactoryObjectMother.getBeanFactory(), new DummyTableLineageCache()),
                new TableSimilarityRefreshServiceProviderImpl(BeanFactoryObjectMother.getBeanFactory(), new DummyTableSimilarityRefreshService()),
                HomeLocationFindServiceObjectMother.getDefaultHomeFinder());
        localFileSystemCache.start();

        return localFileSystemCache;
    }

    /**
     * Creates a new caching service with a disabled caching.
     * @return Caching service that works only as a read-through caching.
     */
    public static LocalFileSystemCache createNewWithCachingDisabled() {
        LocalFileSystemCacheImpl localFileSystemCache = new LocalFileSystemCacheImpl(DqoCacheSpecConfigurationPropertiesObjectMother.createWithDisabledCache(),
                new TableStatusCacheProviderImpl(BeanFactoryObjectMother.getBeanFactory()),
                new LabelsIndexerProviderImpl(BeanFactoryObjectMother.getBeanFactory(), new DummyLabelsIndexer()),
                new TableLineageCacheProviderImpl(BeanFactoryObjectMother.getBeanFactory(), new DummyTableLineageCache()),
                new TableSimilarityRefreshServiceProviderImpl(BeanFactoryObjectMother.getBeanFactory(), new DummyTableSimilarityRefreshService()),
                HomeLocationFindServiceObjectMother.getDefaultHomeFinder());
        localFileSystemCache.start();
        return localFileSystemCache;
    }
}
