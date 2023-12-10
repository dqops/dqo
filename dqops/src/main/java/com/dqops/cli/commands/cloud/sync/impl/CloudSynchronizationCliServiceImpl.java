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
package com.dqops.cli.commands.cloud.sync.impl;

import com.dqops.cli.commands.cloud.impl.CloudLoginService;
import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.dqops.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJob;
import com.dqops.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJobParameters;
import com.dqops.core.synchronization.jobs.SynchronizeRootFolderParameters;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationListener;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationListenerProvider;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import com.dqops.core.synchronization.service.DqoCloudSynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service called by "cloud sync" CLI commands to synchronize the data with DQOps Cloud.
 */
@Service
public class CloudSynchronizationCliServiceImpl implements CloudSynchronizationCliService {
    private DqoCloudSynchronizationService dqoCloudSynchronizationService;
    private FileSystemSynchronizationListenerProvider systemSynchronizationListenerProvider;
    private DqoCloudApiKeyProvider apiKeyProvider;
    private CloudLoginService cloudLoginService;
    private TerminalFactory terminalFactory;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;
    private DqoUserPrincipalProvider userPrincipalProvider;

    /**
     * Default injection constructor.
     * @param dqoCloudSynchronizationService Cloud synchronization service.
     * @param systemSynchronizationListenerProvider Synchronization listener provider.
     * @param apiKeyProvider Api key provider - used to check if the user logged in to DQOps Cloud.
     * @param cloudLoginService Cloud login service - used to log in.
     * @param terminalFactory Terminal factory.
     * @param dqoQueueJobFactory DQOps job factory used to create a new instance of a folder synchronization job.
     * @param dqoJobQueue DQOps job queue to execute a background synchronization.
     * @param userPrincipalProvider Local user principal provider.
     */
    @Autowired
    public CloudSynchronizationCliServiceImpl(
            DqoCloudSynchronizationService dqoCloudSynchronizationService,
            FileSystemSynchronizationListenerProvider systemSynchronizationListenerProvider,
            DqoCloudApiKeyProvider apiKeyProvider,
            CloudLoginService cloudLoginService,
            TerminalFactory terminalFactory,
            DqoQueueJobFactory dqoQueueJobFactory,
            DqoJobQueue dqoJobQueue,
            DqoUserPrincipalProvider userPrincipalProvider) {
        this.dqoCloudSynchronizationService = dqoCloudSynchronizationService;
        this.systemSynchronizationListenerProvider = systemSynchronizationListenerProvider;
        this.apiKeyProvider = apiKeyProvider;
        this.cloudLoginService = cloudLoginService;
        this.terminalFactory = terminalFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.userPrincipalProvider = userPrincipalProvider;
    }

    /**
     * Synchronize a folder type to/from DQOps Cloud.
     * @param rootType Root type.
     * @param reportingMode File synchronization progress reporting mode.
     * @param headlessMode The application was started in a headless mode and should not bother the user with questions (prompts).
     * @param synchronizationDirection File synchronization direction.
     * @param forceRefreshNativeTable Forces to refresh a whole native table for data folders.
     * @param runOnBackgroundQueue True when the actual synchronization operation should be executed in the background on the DQOps job queue.
     *                             False when the operation should be executed on the caller's thread.
     * @param principal Principal that will be used to run the job.
     * @return 0 when success, -1 when an error.
     */
    @Override
    public int synchronizeRoot(DqoRoot rootType,
                               FileSystemSynchronizationReportingMode reportingMode,
                               FileSynchronizationDirection synchronizationDirection,
                               boolean forceRefreshNativeTable,
                               boolean headlessMode,
                               boolean runOnBackgroundQueue,
                               DqoUserPrincipal principal) {
        DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
        DqoCloudApiKey apiKey = this.apiKeyProvider.getApiKey(userPrincipal.getDataDomainIdentity());
        if (apiKey == null) {
            // the api key is missing

            if (headlessMode) {
                throw new CliRequiredParameterMissingException("API Key is missing, please run \"cloud login\" to configure your DQOps Cloud API KEY");
            }

            Boolean loginMe = this.terminalFactory.getReader().promptBoolean("DQOps Cloud API Key is missing, do you want to log in or register to DOQps Cloud?",
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
                    rootType, synchronizationDirection, forceRefreshNativeTable);
            SynchronizeRootFolderDqoQueueJobParameters jobParameters = new SynchronizeRootFolderDqoQueueJobParameters(
                    synchronizationParameter, synchronizationListener);
            synchronizeRootFolderJob.setParameters(jobParameters);

            this.dqoJobQueue.pushJob(synchronizeRootFolderJob, principal);
            synchronizeRootFolderJob.waitForFinish();
        }
        else {
            this.dqoCloudSynchronizationService.synchronizeFolder(rootType, principal.getDataDomainIdentity(), synchronizationDirection, forceRefreshNativeTable, synchronizationListener);
        }

        this.terminalFactory.getWriter().writeLine(rootType.toString() + " synchronization between local DQOps User Home and DQOps Cloud finished.\n");

        return 0;
    }
}
