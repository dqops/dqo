/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.dqocloud.synchronization;

import ai.dqo.core.dqocloud.filesystem.DqoCloudRemoteFileSystemServiceFactory;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.filesystemservice.localfiles.DqoUserHomeFileSystemFactory;
import ai.dqo.core.filesystem.synchronization.FileSystemChangeSet;
import ai.dqo.core.filesystem.synchronization.FileSystemSynchronizationService;
import ai.dqo.core.filesystem.synchronization.SynchronizationResult;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListener;
import ai.dqo.metadata.fileindices.FileIndexName;
import ai.dqo.metadata.fileindices.FileIndexWrapper;
import ai.dqo.metadata.fileindices.FileLocation;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * File synchronization service. Performs a full synchronization of a given category of files to the DQO Cloud.
 */
@Component
public class DqoCloudSynchronizationServiceImpl implements DqoCloudSynchronizationService {
    private UserHomeContextFactory userHomeContextFactory;
    private FileSystemSynchronizationService fileSystemSynchronizationService;
    private DqoUserHomeFileSystemFactory dqoUserHomeFileSystemFactory;
    private DqoCloudRemoteFileSystemServiceFactory dqoCloudRemoteFileSystemServiceFactory;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory. Provides access to the local user home context.
     * @param fileSystemSynchronizationService File system synchronization utility.
     */
    @Autowired
    public DqoCloudSynchronizationServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                              FileSystemSynchronizationService fileSystemSynchronizationService,
                                              DqoUserHomeFileSystemFactory dqoUserHomeFileSystemFactory,
                                              DqoCloudRemoteFileSystemServiceFactory dqoCloudRemoteFileSystemServiceFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.fileSystemSynchronizationService = fileSystemSynchronizationService;
        this.dqoUserHomeFileSystemFactory = dqoUserHomeFileSystemFactory;
        this.dqoCloudRemoteFileSystemServiceFactory = dqoCloudRemoteFileSystemServiceFactory;
    }

    /**
     * Performs synchronization of a given user home folder to the DQO Cloud.
     * @param dqoRoot User Home folder type to synchronize.
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    public void synchronizeFolder(DqoRoot dqoRoot, FileSystemSynchronizationListener synchronizationListener) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        FileIndexName localIndexName = new FileIndexName(dqoRoot, FileLocation.LOCAL);
        FileIndexWrapper localFileIndexWrapper = userHome.getFileIndices().getByObjectName(localIndexName, true);
        if (localFileIndexWrapper == null) {
            localFileIndexWrapper = userHome.getFileIndices().createAndAddNew(localIndexName);
        }

        FileIndexName remoteIndexName = new FileIndexName(dqoRoot, FileLocation.REMOTE);
        FileIndexWrapper remoteFileIndexWrapper = userHome.getFileIndices().getByObjectName(
                remoteIndexName, true);
        if (remoteFileIndexWrapper == null) {
            remoteFileIndexWrapper = userHome.getFileIndices().createAndAddNew(remoteIndexName);
        }

        DqoFileSystem userHomeFolderFileSystem = this.dqoUserHomeFileSystemFactory.createUserHomeFolderFileSystem(dqoRoot);
        DqoFileSystem remoteDqoCloudFileSystem = this.dqoCloudRemoteFileSystemServiceFactory.createRemoteDqoCloudFSRW(dqoRoot);

        FileSystemChangeSet sourceChangeSet = new FileSystemChangeSet(
                userHomeFolderFileSystem,
                localFileIndexWrapper.getSpec().getFolder(),
                Optional.empty()); // empty means that the file system should be scanned to find new files

        FileSystemChangeSet remoteChangeSet = new FileSystemChangeSet(
                remoteDqoCloudFileSystem,
                remoteFileIndexWrapper.getSpec().getFolder(),
                Optional.empty()); // empty means that the file system should be scanned to find new files

        SynchronizationResult synchronizationResult = this.fileSystemSynchronizationService.synchronize(
                sourceChangeSet, remoteChangeSet, dqoRoot, synchronizationListener);

        localFileIndexWrapper.getSpec().setFolder(synchronizationResult.getSourceFileIndex());
        remoteFileIndexWrapper.getSpec().setFolder(synchronizationResult.getTargetFileIndex());
        userHomeContext.flush(); // commit the indexes
    }

    /**
     * Synchronizes all roots (sources, check definitions, data).
     *
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    @Override
    public void synchronizeAll(FileSystemSynchronizationListener synchronizationListener) {
        synchronizeFolder(DqoRoot.SOURCES, synchronizationListener);
        synchronizeFolder(DqoRoot.SENSORS, synchronizationListener);
        synchronizeFolder(DqoRoot.RULES, synchronizationListener);
        synchronizeFolder(DqoRoot.DATA_SENSOR_READOUTS, synchronizationListener);
        synchronizeFolder(DqoRoot.DATA_RULE_RESULTS, synchronizationListener);
    }

    /**
     * Synchronizes only the data roots (readings, alerts).
     *
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    @Override
    public void synchronizeData(FileSystemSynchronizationListener synchronizationListener) {
        synchronizeFolder(DqoRoot.DATA_SENSOR_READOUTS, synchronizationListener);
        synchronizeFolder(DqoRoot.DATA_RULE_RESULTS, synchronizationListener);
    }
}
