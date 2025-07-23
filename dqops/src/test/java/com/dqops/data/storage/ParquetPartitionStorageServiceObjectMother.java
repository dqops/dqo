/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
