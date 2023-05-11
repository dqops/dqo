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

package ai.dqo.core.synchronization.jobs;

import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.fileexchange.FileSynchronizationDirection;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationReportingMode;

/**
 * Service that creates and starts folder synchronization jobs that will synchronize the folder with a remote file system.
 */
public interface SynchronizeRootFolderJobStarter {
    /**
     * Creates a job that will synchronize a folder.
     *
     * @param rootType                 Folder type that will be synchronized.
     * @param reportingMode            Reporting type (how to report progress to the console).
     * @param synchronizationDirection Synchronization direction.
     * @param forceRefreshNativeTable  Force a full refresh of a native table.
     * @return DQO Job that will synchronize the folder, it is not yet queued for execution.
     */
    SynchronizeRootFolderDqoQueueJob createSynchronizeFolderJob(DqoRoot rootType,
                                                                FileSystemSynchronizationReportingMode reportingMode,
                                                                FileSynchronizationDirection synchronizationDirection,
                                                                boolean forceRefreshNativeTable);

    /**
     * Creates and starts a job that will synchronize a folder.
     *
     * @param rootType                 Folder type that will be synchronized.
     * @param reportingMode            Reporting type (how to report progress to the console).
     * @param synchronizationDirection Synchronization direction.
     * @param forceRefreshNativeTable  Force a full refresh of a native table.
     * @return DQO Job that will synchronize the folder which as started.
     */
    SynchronizeRootFolderDqoQueueJob startSynchronizeFolderJob(DqoRoot rootType,
                                                               FileSystemSynchronizationReportingMode reportingMode,
                                                               FileSynchronizationDirection synchronizationDirection,
                                                               boolean forceRefreshNativeTable);
}
