package ai.dqo.core.scheduler.synchronization;

import ai.dqo.core.configuration.DqoSchedulerConfigurationProperties;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKey;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import ai.dqo.core.dqocloud.synchronization.DqoCloudSynchronizationService;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
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
    private SchedulerAllFileSystemSynchronizationListener synchronizationListenerMetadata;
    private SchedulerDataFileSystemSynchronizationListener synchronizationListenerData;

    /**
     * Creates a file synchronization service using dependencies.
     * @param schedulerConfigurationProperties Scheduler configuration properties.
     * @param apiKeyProvider Api key provider - just to verify that a DQO Cloud API Key is present, so a full cloud synchronization is possible.
     * @param dqoCloudSynchronizationService DQO Cloud synchronization service used to synchronize the metadata and data.
     * @param synchronizationListenerMetadata Synchronization listener that is updated with the synchronization progress when the metadata is synchronized.
     * @param synchronizationListenerData Synchronization listener used before and after checks are executed.
     */
    @Autowired
    public SchedulerFileSynchronizationServiceImpl(DqoSchedulerConfigurationProperties schedulerConfigurationProperties,
                                                   DqoCloudApiKeyProvider apiKeyProvider,
                                                   DqoCloudSynchronizationService dqoCloudSynchronizationService,
                                                   SchedulerAllFileSystemSynchronizationListener synchronizationListenerMetadata,
                                                   SchedulerDataFileSystemSynchronizationListener synchronizationListenerData) {
        this.schedulerConfigurationProperties = schedulerConfigurationProperties;
        this.apiKeyProvider = apiKeyProvider;
        this.dqoCloudSynchronizationService = dqoCloudSynchronizationService;
        this.synchronizationListenerMetadata = synchronizationListenerMetadata;
        this.synchronizationListenerData = synchronizationListenerData;
    }

    /**
     * Synchronizes the whole user home, both the metadata (checks, rules, sensors) and the parquet data files. Should be called in the job that updates the metadata.
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    @Override
    public boolean synchronizeAll() {
        if (!this.schedulerConfigurationProperties.isEnableCloudSync()) {
            return false;
        }

        DqoCloudApiKey apiKey = this.apiKeyProvider.getApiKey();
        if (apiKey == null) {
            LOG.warn("Cannot synchronize the metadata. The DQO Cloud API Key is missing. The job scheduler will only execute local metadata check definitions, without synchronizing the data with the cloud.");
            return false;
        }

        try {
            this.dqoCloudSynchronizationService.synchronizeFolder(DqoRoot.SOURCES, this.synchronizationListenerMetadata);
            this.dqoCloudSynchronizationService.synchronizeFolder(DqoRoot.SENSORS, this.synchronizationListenerMetadata);
            this.dqoCloudSynchronizationService.synchronizeFolder(DqoRoot.RULES, this.synchronizationListenerMetadata);
            this.dqoCloudSynchronizationService.synchronizeFolder(DqoRoot.DATA_READINGS, this.synchronizationListenerMetadata);
            this.dqoCloudSynchronizationService.synchronizeFolder(DqoRoot.DATA_ALERTS, this.synchronizationListenerMetadata);

        }
        catch (Exception ex) {
            LOG.error("Cannot synchronize the metadata when refreshing the user home during a metadata refresh in the job scheduler.", ex);
            return false;
        }

        return true;
    }

    /**
     * Synchronizes only the data files (parquet files). Should be called in the job that executes the data quality checks.
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    @Override
    public boolean synchronizeData() {
        if (!this.schedulerConfigurationProperties.isEnableCloudSync()) {
            return false;
        }

        DqoCloudApiKey apiKey = this.apiKeyProvider.getApiKey();
        if (apiKey == null) {
            LOG.warn("Cannot synchronize the parquet data files. The DQO Cloud API Key is missing. The job scheduler will use the local copy of parquet files, without synchronizing the data with the cloud.");
            return false;
        }

        try {
            this.dqoCloudSynchronizationService.synchronizeFolder(DqoRoot.RULES, this.synchronizationListenerData);
            this.dqoCloudSynchronizationService.synchronizeFolder(DqoRoot.DATA_READINGS, this.synchronizationListenerData);
            this.dqoCloudSynchronizationService.synchronizeFolder(DqoRoot.DATA_ALERTS, this.synchronizationListenerData);

        }
        catch (Exception ex) {
            LOG.error("Cannot synchronize the data files when running schedule data quality checks.", ex);
            return false;
        }

        return true;
    }
}
