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
