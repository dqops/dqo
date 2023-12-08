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
package com.dqops.core.synchronization.service;

import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.SynchronizationRoot;
import com.dqops.core.synchronization.fileexchange.*;
import com.dqops.core.synchronization.filesystems.dqocloud.DqoCloudRemoteFileSystemServiceFactory;
import com.dqops.core.synchronization.filesystems.local.LocalSynchronizationFileSystemFactory;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationListener;
import com.dqops.metadata.fileindices.FileIndexName;
import com.dqops.metadata.fileindices.FileIndexSpec;
import com.dqops.metadata.fileindices.FileIndexWrapper;
import com.dqops.metadata.fileindices.FileLocation;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * File synchronization service. Performs a full synchronization of a given category of files to the DQOps Cloud.
 */
@Component
@Slf4j
public class DqoCloudSynchronizationServiceImpl implements DqoCloudSynchronizationService {
    private UserHomeContextFactory userHomeContextFactory;
    private FileSystemSynchronizationService fileSystemSynchronizationService;
    private LocalSynchronizationFileSystemFactory localSynchronizationFileSystemFactory;
    private DqoCloudRemoteFileSystemServiceFactory dqoCloudRemoteFileSystemServiceFactory;
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private DqoCloudWarehouseService dqoCloudWarehouseService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory. Provides access to the local user home context.
     * @param fileSystemSynchronizationService File system synchronization utility.
     * @param localSynchronizationFileSystemFactory User home file system factory.
     * @param dqoCloudRemoteFileSystemServiceFactory DQOps Cloud remote file system factory.
     * @param dqoCloudApiKeyProvider API key provider.
     * @param dqoCloudWarehouseService DQOps Cloud warehouse refresh service, used to refresh the native tables.
     */
    @Autowired
    public DqoCloudSynchronizationServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                              FileSystemSynchronizationService fileSystemSynchronizationService,
                                              LocalSynchronizationFileSystemFactory localSynchronizationFileSystemFactory,
                                              DqoCloudRemoteFileSystemServiceFactory dqoCloudRemoteFileSystemServiceFactory,
                                              DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                              DqoCloudWarehouseService dqoCloudWarehouseService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.fileSystemSynchronizationService = fileSystemSynchronizationService;
        this.localSynchronizationFileSystemFactory = localSynchronizationFileSystemFactory;
        this.dqoCloudRemoteFileSystemServiceFactory = dqoCloudRemoteFileSystemServiceFactory;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.dqoCloudWarehouseService = dqoCloudWarehouseService;
    }

    /**
     * Performs synchronization of a given user home folder to the DQOps Cloud.
     * @param dqoRoot User Home folder type to synchronize.
     * @param userIdentity User identity that identifies the data domain.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param forceRefreshNativeTable True when the native table should be forcibly refreshed even if there are no changes.
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    @Override
    public void synchronizeFolder(DqoRoot dqoRoot,
                                  UserDomainIdentity userIdentity,
                                  FileSynchronizationDirection synchronizationDirection,
                                  boolean forceRefreshNativeTable,
                                  FileSystemSynchronizationListener synchronizationListener) {
        DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey(userIdentity);
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
        UserHome userHome = userHomeContext.getUserHome();

        FileIndexName localIndexName = new FileIndexName(dqoRoot, FileLocation.LOCAL);
        FileIndexWrapper localFileIndexWrapper = userHome.getFileIndices().getByObjectName(localIndexName, true);
        if (localFileIndexWrapper == null) {
            localFileIndexWrapper = userHome.getFileIndices().createAndAddNew(localIndexName);
            localFileIndexWrapper.getSpec().setTenantId(apiKey.getApiKeyPayload().getTenantId());
            localFileIndexWrapper.getSpec().setDomain(apiKey.getApiKeyPayload().getDomain());
        } else {
            if (localFileIndexWrapper.getSpec().getTenantId() == null) {
                localFileIndexWrapper.getSpec().setTenantId(apiKey.getApiKeyPayload().getTenantId());
                localFileIndexWrapper.getSpec().setDomain(apiKey.getApiKeyPayload().getDomain());
            } else if (!Objects.equals(localFileIndexWrapper.getSpec().getTenantId(), apiKey.getApiKeyPayload().getTenantId()) ||
                       !Objects.equals(localFileIndexWrapper.getSpec().getDomain(), apiKey.getApiKeyPayload().getDomain())) {
                localFileIndexWrapper.setSpec(new FileIndexSpec());
                localFileIndexWrapper.getSpec().setTenantId(apiKey.getApiKeyPayload().getTenantId());
                localFileIndexWrapper.getSpec().setDomain(apiKey.getApiKeyPayload().getDomain());
            }
        }

        FileIndexName remoteIndexName = new FileIndexName(dqoRoot, FileLocation.REMOTE);
        FileIndexWrapper remoteFileIndexWrapper = userHome.getFileIndices().getByObjectName(
                remoteIndexName, true);
        if (remoteFileIndexWrapper == null) {
            remoteFileIndexWrapper = userHome.getFileIndices().createAndAddNew(remoteIndexName);
            remoteFileIndexWrapper.getSpec().setTenantId(apiKey.getApiKeyPayload().getTenantId());
            remoteFileIndexWrapper.getSpec().setDomain(apiKey.getApiKeyPayload().getDomain());
        } else {
            if (remoteFileIndexWrapper.getSpec().getTenantId() == null) {
                remoteFileIndexWrapper.getSpec().setTenantId(apiKey.getApiKeyPayload().getTenantId());
                remoteFileIndexWrapper.getSpec().setDomain(apiKey.getApiKeyPayload().getDomain());
            } else if (!Objects.equals(remoteFileIndexWrapper.getSpec().getTenantId(), apiKey.getApiKeyPayload().getTenantId()) ||
                    !Objects.equals(remoteFileIndexWrapper.getSpec().getDomain(), apiKey.getApiKeyPayload().getDomain())) {
                remoteFileIndexWrapper.setSpec(new FileIndexSpec());
                remoteFileIndexWrapper.getSpec().setTenantId(apiKey.getApiKeyPayload().getTenantId());
                remoteFileIndexWrapper.getSpec().setDomain(apiKey.getApiKeyPayload().getDomain());
            }
        }

        SynchronizationRoot userHomeFolderFileSystem = this.localSynchronizationFileSystemFactory.createUserHomeFolderFileSystem(dqoRoot, userIdentity);
        SynchronizationRoot remoteDqoCloudFileSystem = this.dqoCloudRemoteFileSystemServiceFactory.createRemoteDqoCloudFSRW(dqoRoot, userIdentity);

        if (remoteDqoCloudFileSystem == null) {
            // no access to the remote file system
            log.warn("Cannot access the remote file system for the folder root: " + dqoRoot);
            return;
        }

        FileSystemChangeSet sourceChangeSet = new FileSystemChangeSet(
                userHomeFolderFileSystem,
                localFileIndexWrapper.getSpec().getFolder(),
                Optional.empty()); // empty means that the file system should be scanned to find new files

        FileSystemChangeSet remoteChangeSet = new FileSystemChangeSet(
                remoteDqoCloudFileSystem,
                remoteFileIndexWrapper.getSpec().getFolder(),
                Optional.empty()); // empty means that the file system should be scanned to find new files

        SynchronizationResult synchronizationResult = this.fileSystemSynchronizationService.synchronize(
                sourceChangeSet, remoteChangeSet, dqoRoot, userIdentity, synchronizationDirection, apiKey, synchronizationListener);

        TargetTableModifiedPartitions targetTableModifiedPartitions = synchronizationResult.getTargetTableModifiedPartitions();
        if (forceRefreshNativeTable) {
            this.dqoCloudWarehouseService.refreshNativeTable(new TargetTableModifiedPartitions(dqoRoot), userIdentity);
        } else {
            if (targetTableModifiedPartitions.hasAnyChanges()) {
                this.dqoCloudWarehouseService.refreshNativeTable(targetTableModifiedPartitions, userIdentity);
            }
        }

        if (localFileIndexWrapper.getSpec().getFolder() == null ||
                !Objects.equals(localFileIndexWrapper.getSpec().getFolder().getHash(), synchronizationResult.getSourceFileIndex().getHash())) {
            localFileIndexWrapper.getSpec().setFolder(synchronizationResult.getSourceFileIndex());
        }

        if (remoteFileIndexWrapper.getSpec().getFolder() == null ||
                !Objects.equals(remoteFileIndexWrapper.getSpec().getFolder().getHash(), synchronizationResult.getTargetFileIndex().getHash())) {
            remoteFileIndexWrapper.getSpec().setFolder(synchronizationResult.getTargetFileIndex());
        }

        userHomeContext.flush(); // commit the indexes
    }

    /**
     * Synchronizes all roots (sources, check definitions, data).
     *
     * @param userIdentity User identity that identifies the target data domain.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param forceRefreshNativeTable True when the native table should be forcibly refreshed even if there are no changes.
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    @Override
    public void synchronizeAll(UserDomainIdentity userIdentity,
                               FileSynchronizationDirection synchronizationDirection,
                               boolean forceRefreshNativeTable,
                               FileSystemSynchronizationListener synchronizationListener) {
        synchronizeFolder(DqoRoot.sources, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.sensors, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.rules, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.checks, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.settings, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.credentials, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_sensor_readouts, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_check_results, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_errors, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_statistics, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_incidents, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
    }

    /**
     * Synchronizes only the data roots (sensor readouts, rule results).
     *
     * @param userIdentity User identity that identifies the target data domain.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param forceRefreshNativeTable True when the native table should be forcibly refreshed even if there are no changes.
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    @Override
    public void synchronizeData(UserDomainIdentity userIdentity,
                                FileSynchronizationDirection synchronizationDirection,
                                boolean forceRefreshNativeTable,
                                FileSystemSynchronizationListener synchronizationListener) {
        synchronizeFolder(DqoRoot.data_sensor_readouts, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_check_results, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_errors, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_statistics, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        synchronizeFolder(DqoRoot.data_incidents, userIdentity, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
    }
}
