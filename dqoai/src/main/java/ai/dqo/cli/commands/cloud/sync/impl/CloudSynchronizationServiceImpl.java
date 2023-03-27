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
package ai.dqo.cli.commands.cloud.sync.impl;

import ai.dqo.cli.commands.cloud.impl.CloudLoginService;
import ai.dqo.cli.exceptions.CliRequiredParameterMissingException;
import ai.dqo.cli.terminal.TerminalFactory;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKey;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import ai.dqo.core.synchronization.service.DqoCloudSynchronizationService;
import ai.dqo.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJob;
import ai.dqo.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJobParameters;
import ai.dqo.core.synchronization.jobs.SynchronizeRootFolderParameters;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.fileexchange.FileSynchronizationDirection;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationListener;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationListenerProvider;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service called by "cloud sync" CLI commands to synchronize the data with DQO Cloud.
 */
@Service
public class CloudSynchronizationServiceImpl implements CloudSynchronizationService {
    private DqoCloudSynchronizationService dqoCloudSynchronizationService;
    private FileSystemSynchronizationListenerProvider systemSynchronizationListenerProvider;
    private DqoCloudApiKeyProvider apiKeyProvider;
    private CloudLoginService cloudLoginService;
    private TerminalFactory terminalFactory;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;

    /**
     * Default injection constructor.
     * @param dqoCloudSynchronizationService Cloud synchronization service.
     * @param systemSynchronizationListenerProvider Synchronization listener provider.
     * @param apiKeyProvider Api key provider - used to check if the user logged in to DQO Cloud.
     * @param cloudLoginService Cloud login service - used to log in.
     * @param terminalFactory Terminal factory.
     * @param dqoQueueJobFactory DQO job factory used to create a new instance of a folder synchronization job.
     * @param dqoJobQueue DQO job queue to execute a background synchronization.
     */
    @Autowired
    public CloudSynchronizationServiceImpl(
            DqoCloudSynchronizationService dqoCloudSynchronizationService,
            FileSystemSynchronizationListenerProvider systemSynchronizationListenerProvider,
            DqoCloudApiKeyProvider apiKeyProvider,
            CloudLoginService cloudLoginService,
            TerminalFactory terminalFactory,
            DqoQueueJobFactory dqoQueueJobFactory,
            DqoJobQueue dqoJobQueue) {
        this.dqoCloudSynchronizationService = dqoCloudSynchronizationService;
        this.systemSynchronizationListenerProvider = systemSynchronizationListenerProvider;
        this.apiKeyProvider = apiKeyProvider;
        this.cloudLoginService = cloudLoginService;
        this.terminalFactory = terminalFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Synchronize a folder type to/from DQO Cloud.
     * @param rootType Root type.
     * @param reportingMode File synchronization progress reporting mode.
     * @param headlessMode The application was started in a headless mode and should not bother the user with questions (prompts).
     * @param synchronizationDirection File synchronization direction.
     * @param forceRefreshNativeTable Forces to refresh a whole native table for data folders.
     * @param runOnBackgroundQueue True when the actual synchronization operation should be executed in the background on the DQO job queue.
     *                             False when the operation should be executed on the caller's thread.
     * @return 0 when success, -1 when an error.
     */
    @Override
    public int synchronizeRoot(DqoRoot rootType,
                               FileSystemSynchronizationReportingMode reportingMode,
                               FileSynchronizationDirection synchronizationDirection,
                               boolean forceRefreshNativeTable,
                               boolean headlessMode,
                               boolean runOnBackgroundQueue) {
        DqoCloudApiKey apiKey = this.apiKeyProvider.getApiKey();
        if (apiKey == null) {
            // the api key is missing

            if (headlessMode) {
                throw new CliRequiredParameterMissingException("API Key is missing, please run \"cloud login\" to configure your DQO Cloud API KEY");
            }

            Boolean loginMe = this.terminalFactory.getReader().promptBoolean("DQO Cloud API Key is missing, do you want to log in or register to DOQ Cloud?",
                    true);
            if (loginMe) {
                if (!this.cloudLoginService.logInToDqoCloud()) {
                    return -2;
                }
            } else {
                return -1;
            }
        }

        FileSystemSynchronizationListener synchronizationListener = this.systemSynchronizationListenerProvider.getSynchronizationListener(reportingMode);

        if (runOnBackgroundQueue) {
            SynchronizeRootFolderDqoQueueJob synchronizeRootFolderJob = this.dqoQueueJobFactory.createSynchronizeRootFolderJob();
            SynchronizeRootFolderParameters synchronizationParameter = new SynchronizeRootFolderParameters(
                    rootType, FileSynchronizationDirection.full, forceRefreshNativeTable);
            SynchronizeRootFolderDqoQueueJobParameters jobParameters = new SynchronizeRootFolderDqoQueueJobParameters(
                    synchronizationParameter, synchronizationListener);
            synchronizeRootFolderJob.setParameters(jobParameters);

            this.dqoJobQueue.pushJob(synchronizeRootFolderJob);
            synchronizeRootFolderJob.waitForFinish();
        }
        else {
            this.dqoCloudSynchronizationService.synchronizeFolder(rootType, synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        }

        this.terminalFactory.getWriter().writeLine(rootType.toString() + " synchronization between local DQO User Home and DQO Cloud finished.\n");

        return 0;
    }
}
