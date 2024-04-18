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

package com.dqops.core.filesystem.cache;

import com.dqops.core.configuration.DqoCacheSpecConfigurationPropertiesObjectMother;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceObjectMother;
import com.dqops.data.checkresults.statuscache.TableStatusCacheProviderImpl;
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
        return new LocalFileSystemCacheImpl(DqoCacheSpecConfigurationPropertiesObjectMother.getWithCacheEnabledButNoFileSystemWatching(),
                new TableStatusCacheProviderImpl(BeanFactoryObjectMother.getBeanFactory()),
                HomeLocationFindServiceObjectMother.getDefaultHomeFinder());
    }

    /**
     * Creates a new caching service with a disabled caching.
     * @return Caching service that works only as a read-through caching.
     */
    public static LocalFileSystemCache createNewWithCachingDisabled() {
        return new LocalFileSystemCacheImpl(DqoCacheSpecConfigurationPropertiesObjectMother.createWithDisabledCache(),
                new TableStatusCacheProviderImpl(BeanFactoryObjectMother.getBeanFactory()),
                HomeLocationFindServiceObjectMother.getDefaultHomeFinder());
    }
}
