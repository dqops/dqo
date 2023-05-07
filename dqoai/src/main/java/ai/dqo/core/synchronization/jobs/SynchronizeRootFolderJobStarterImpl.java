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

import ai.dqo.core.dqocloud.apikey.DqoCloudApiKey;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.fileexchange.FileSynchronizationDirection;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationListener;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationListenerProvider;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.synchronization.service.DqoCloudSynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service that creates and starts folder synchronization jobs that will synchronize the folder with a remote file system.
 */
@Component
public class SynchronizeRootFolderJobStarterImpl implements SynchronizeRootFolderJobStarter {
    private DqoCloudSynchronizationService dqoCloudSynchronizationService;
    private FileSystemSynchronizationListenerProvider systemSynchronizationListenerProvider;
    private DqoCloudApiKeyProvider apiKeyProvider;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;

    @Autowired
    public SynchronizeRootFolderJobStarterImpl(DqoCloudSynchronizationService dqoCloudSynchronizationService,
                                               FileSystemSynchronizationListenerProvider systemSynchronizationListenerProvider,
                                               DqoCloudApiKeyProvider apiKeyProvider,
                                               DqoQueueJobFactory dqoQueueJobFactory,
                                               DqoJobQueue dqoJobQueue) {
        this.dqoCloudSynchronizationService = dqoCloudSynchronizationService;
        this.systemSynchronizationListenerProvider = systemSynchronizationListenerProvider;
        this.apiKeyProvider = apiKeyProvider;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Creates a job that will synchronize a folder.
     * @param rootType Folder type that will be synchronized.
     * @param reportingMode Reporting type (how to report progress to the console).
     * @param synchronizationDirection Synchronization direction.
     * @param forceRefreshNativeTable Force a full refresh of a native table.
     * @return DQO Job that will synchronize the folder, it is not yet queued for execution.
     */
    @Override
    public SynchronizeRootFolderDqoQueueJob createSynchronizeFolderJob(DqoRoot rootType,
                                                                       FileSystemSynchronizationReportingMode reportingMode,
                                                                       FileSynchronizationDirection synchronizationDirection,
                                                                       boolean forceRefreshNativeTable) {
        DqoCloudApiKey apiKey = this.apiKeyProvider.getApiKey();
        if (apiKey == null) {
            return null;
        }

        FileSystemSynchronizationListener synchronizationListener = this.systemSynchronizationListenerProvider.getSynchronizationListener(reportingMode);
        SynchronizeRootFolderDqoQueueJob synchronizeRootFolderJob = this.dqoQueueJobFactory.createSynchronizeRootFolderJob();
        SynchronizeRootFolderParameters synchronizationParameter = new SynchronizeRootFolderParameters(
                rootType, synchronizationDirection, forceRefreshNativeTable);
        SynchronizeRootFolderDqoQueueJobParameters jobParameters = new SynchronizeRootFolderDqoQueueJobParameters(
                synchronizationParameter, synchronizationListener);
        synchronizeRootFolderJob.setParameters(jobParameters);

        return synchronizeRootFolderJob;
    }

    /**
     * Creates and starts a job that will synchronize a folder.
     * @param rootType Folder type that will be synchronized.
     * @param reportingMode Reporting type (how to report progress to the console).
     * @param synchronizationDirection Synchronization direction.
     * @param forceRefreshNativeTable Force a full refresh of a native table.
     * @return DQO Job that will synchronize the folder which as started.
     */
    @Override
    public SynchronizeRootFolderDqoQueueJob startSynchronizeFolderJob(DqoRoot rootType,
                                                                      FileSystemSynchronizationReportingMode reportingMode,
                                                                      FileSynchronizationDirection synchronizationDirection,
                                                                      boolean forceRefreshNativeTable){
        SynchronizeRootFolderDqoQueueJob synchronizeFolderJob = this.createSynchronizeFolderJob(rootType, reportingMode, synchronizationDirection, forceRefreshNativeTable);
        this.dqoJobQueue.pushJob(synchronizeFolderJob);
        return synchronizeFolderJob;
    }
}
