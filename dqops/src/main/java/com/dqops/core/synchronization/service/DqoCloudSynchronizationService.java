/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.service;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationListener;

/**
 * File synchronization service. Performs a full synchronization of a given category of files to the DQOps Cloud.
 */
public interface DqoCloudSynchronizationService {
    /**
     * Performs synchronization of a given user home folder to the DQOps Cloud.
     *
     * @param dqoRoot User Home folder type to synchronize.
     * @param userIdentity User identity that identifies the data domain.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param forceRefreshNativeTable True when the native table should be forcibly refreshed even if there are no changes.
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    void synchronizeFolder(DqoRoot dqoRoot,
                           UserDomainIdentity userIdentity,
                           FileSynchronizationDirection synchronizationDirection,
                           boolean forceRefreshNativeTable,
                           FileSystemSynchronizationListener synchronizationListener);

    /**
     * Synchronizes all roots (sources, check definitions, data).
     *
     * @param userIdentity User identity that identifies the data domain.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param forceRefreshNativeTable True when the native table should be forcibly refreshed even if there are no changes.
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    void synchronizeAll(UserDomainIdentity userIdentity,
                        FileSynchronizationDirection synchronizationDirection,
                        boolean forceRefreshNativeTable,
                        FileSystemSynchronizationListener synchronizationListener);

    /**
     * Synchronizes only the data roots (sensor readouts, rule results).
     *
     * @param userIdentity User identity that identifies the data domain.
     * @param fileSynchronizationDirection File synchronization direction (full, download, upload).
     * @param forceRefreshNativeTable True when the native table should be forcibly refreshed even if there are no changes.
     * @param synchronizationListener Synchronization listener to notify about the progress.
     */
    void synchronizeData(UserDomainIdentity userIdentity,
                         FileSynchronizationDirection fileSynchronizationDirection,
                         boolean forceRefreshNativeTable,
                         FileSystemSynchronizationListener synchronizationListener);
}
