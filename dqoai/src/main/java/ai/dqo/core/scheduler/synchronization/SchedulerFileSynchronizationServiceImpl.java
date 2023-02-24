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
package ai.dqo.core.scheduler.synchronization;

import ai.dqo.core.configuration.DqoSchedulerConfigurationProperties;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKey;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import ai.dqo.core.dqocloud.synchronization.DqoCloudSynchronizationService;
import ai.dqo.core.filesystem.synchronization.FileSynchronizationDirection;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListener;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListenerProvider;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * File synchronization service used by the job scheduler to synchronize the data (after checks were executed) and metadata.
 */
@Component
public class SchedulerFileSynchronizationServiceImpl implements SchedulerFileSynchronizationService {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerFileSynchronizationServiceImpl.class);

    private DqoSchedulerConfigurationProperties schedulerConfigurationProperties;
    private DqoCloudApiKeyProvider apiKeyProvider;
    private DqoCloudSynchronizationService dqoCloudSynchronizationService;
    private FileSystemSynchronizationListenerProvider fileSystemSynchronizationListenerProvider;

    /**
     * Creates a file synchronization service using dependencies.
     * @param schedulerConfigurationProperties Scheduler configuration properties.
     * @param apiKeyProvider Api key provider - just to verify that a DQO Cloud API Key is present, so a full cloud synchronization is possible.
     * @param dqoCloudSynchronizationService DQO Cloud synchronization service used to synchronize the metadata and data.
     * @param fileSystemSynchronizationListenerProvider File system synchronization listener provider for creating (getting) the correct reporting mode.
     */
    @Autowired
    public SchedulerFileSynchronizationServiceImpl(DqoSchedulerConfigurationProperties schedulerConfigurationProperties,
                                                   DqoCloudApiKeyProvider apiKeyProvider,
                                                   DqoCloudSynchronizationService dqoCloudSynchronizationService,
                                                   FileSystemSynchronizationListenerProvider fileSystemSynchronizationListenerProvider) {
        this.schedulerConfigurationProperties = schedulerConfigurationProperties;
        this.apiKeyProvider = apiKeyProvider;
        this.dqoCloudSynchronizationService = dqoCloudSynchronizationService;
        this.fileSystemSynchronizationListenerProvider = fileSystemSynchronizationListenerProvider;
    }

    /**
     * Synchronizes the whole user home, both the metadata (checks, rules, sensors) and the parquet data files. Should be called in the job that updates the metadata.
     * @param synchronizationReportingMode File system synchronization mode.
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    @Override
    public boolean synchronizeAll(FileSystemSynchronizationReportingMode synchronizationReportingMode) {
        if (!this.schedulerConfigurationProperties.isEnableCloudSync()) {
            return false;
        }

        DqoCloudApiKey apiKey = this.apiKeyProvider.getApiKey();
        if (apiKey == null) {
            LOG.warn("Cannot synchronize the metadata. The DQO Cloud API Key is missing. The job scheduler will only execute local metadata check definitions, without synchronizing the data with the cloud.");
            return false;
        }

        try {
            FileSystemSynchronizationListener synchronizationListener = this.fileSystemSynchronizationListenerProvider.getSynchronizationListener(synchronizationReportingMode);
            this.dqoCloudSynchronizationService.synchronizeAll(FileSynchronizationDirection.full, synchronizationListener);
        }
        catch (Exception ex) {
            LOG.error("Cannot synchronize the metadata when refreshing the user home during a metadata refresh in the job scheduler.", ex);
            return false;
        }

        return true;
    }

    /**
     * Synchronizes only the data files (parquet files). Should be called in the job that executes the data quality checks.
     * @param synchronizationReportingMode File system synchronization mode.
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    @Override
    public boolean synchronizeData(FileSystemSynchronizationReportingMode synchronizationReportingMode) {
        if (!this.schedulerConfigurationProperties.isEnableCloudSync()) {
            return false;
        }

        DqoCloudApiKey apiKey = this.apiKeyProvider.getApiKey();
        if (apiKey == null) {
            LOG.warn("Cannot synchronize the parquet data files. The DQO Cloud API Key is missing. The job scheduler will use the local copy of parquet files, without synchronizing the data with the cloud.");
            return false;
        }

        try {
            FileSystemSynchronizationListener synchronizationListener = this.fileSystemSynchronizationListenerProvider.getSynchronizationListener(synchronizationReportingMode);
            this.dqoCloudSynchronizationService.synchronizeData(FileSynchronizationDirection.full, synchronizationListener);
        }
        catch (Exception ex) {
            LOG.error("Cannot synchronize the data files when running schedule data quality checks.", ex);
            return false;
        }

        return true;
    }
}
