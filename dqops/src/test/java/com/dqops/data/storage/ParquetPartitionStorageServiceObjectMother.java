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
package com.dqops.data.storage;

import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceObjectMother;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.locks.UserHomeLockManagerObjectMother;
import com.dqops.core.synchronization.status.SynchronizationStatusTrackerStub;
import com.dqops.data.local.LocalDqoUserHomePathProviderStub;
import com.dqops.data.storage.parquet.HadoopConfigurationProvider;
import com.dqops.data.storage.parquet.HadoopConfigurationProviderObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageServiceImpl;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;

import java.nio.file.Path;

/**
 * Object mother for {@link ParquetPartitionStorageServiceImpl}
 */
public class ParquetPartitionStorageServiceObjectMother {
    /**
     * Creates a parquet partition storage service that will read/write files directly in the folder where the given user home is located.
     * @param userHomeContext User home context (file based).
     * @return Parquet partition storage service.
     */
    public static ParquetPartitionStorageServiceImpl create(UserHomeContext userHomeContext) {
        Path absolutePathToUserHome = userHomeContext.getHomeRoot().getPhysicalAbsolutePath();
        LocalDqoUserHomePathProviderStub localDqoUserHomePathProviderStub = new LocalDqoUserHomePathProviderStub(absolutePathToUserHome);
        UserHomeLockManager userHomeLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
        HomeLocationFindServiceImpl homeLocationFindService = HomeLocationFindServiceObjectMother.getCustomUserHome(absolutePathToUserHome);
        SynchronizationStatusTrackerStub synchronizationStatusTracker = new SynchronizationStatusTrackerStub();
        LocalUserHomeFileStorageServiceImpl localUserHomeFileStorageService = new LocalUserHomeFileStorageServiceImpl(
                homeLocationFindService, userHomeLockManager, synchronizationStatusTracker, LocalFileSystemCacheObjectMother.createNewCache());
        ParquetPartitionMetadataServiceImpl parquetPartitionMetadataService = new ParquetPartitionMetadataServiceImpl(
                userHomeLockManager, localUserHomeFileStorageService);
        HadoopConfigurationProvider hadoopConfigurationProvider = HadoopConfigurationProviderObjectMother.getDefault();
        LocalFileSystemCache fileSystemCache = LocalFileSystemCacheObjectMother.createNewWithCachingDisabled();

        ParquetPartitionStorageServiceImpl parquetPartitionStorageService = new ParquetPartitionStorageServiceImpl(
                parquetPartitionMetadataService, localDqoUserHomePathProviderStub, userHomeLockManager,
                hadoopConfigurationProvider, localUserHomeFileStorageService, synchronizationStatusTracker, fileSystemCache);
        return parquetPartitionStorageService;
    }
}
