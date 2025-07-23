/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.status;

import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import com.dqops.core.synchronization.contract.DqoRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Tracks the synchronization status for each type of folder. Knows that there are unsaved changes in a given folder.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SynchronizationStatusTrackerImpl implements SynchronizationStatusTracker {
    private final HashMap<String, CloudSynchronizationFoldersStatusModel> domainSynchronizationStatus = new LinkedHashMap<>();
    private final Object lock = new Object();
    private final DqoJobQueueMonitoringService dqoJobQueueMonitoringService;

    /**
     * Creates a default instance, receiving dependencies.
     * @param dqoJobQueueMonitoringService Job monitoring service, notified about the new synchronization status.
     */
    @Autowired
    public SynchronizationStatusTrackerImpl(DqoJobQueueMonitoringService dqoJobQueueMonitoringService) {
        this.dqoJobQueueMonitoringService = dqoJobQueueMonitoringService;
    }

    /**
     * Updates the folder synchronization status.
     * @param folderRoot Folder type.
     * @param dataDomain Data domain name.
     * @param newStatus New synchronization status.
     */
    @Override
    public void changeFolderSynchronizationStatus(DqoRoot folderRoot, String dataDomain, FolderSynchronizationStatus newStatus) {
        CloudSynchronizationFoldersStatusModel newSynchronizationStatus = null;

        synchronized (this.lock) {
            CloudSynchronizationFoldersStatusModel domainSynchronizationStatusModel = this.domainSynchronizationStatus.get(dataDomain);
            if (domainSynchronizationStatusModel == null) {
                domainSynchronizationStatusModel = new CloudSynchronizationFoldersStatusModel();
                this.domainSynchronizationStatus.put(dataDomain, domainSynchronizationStatusModel);
            }

            FolderSynchronizationStatus currentStatus = domainSynchronizationStatusModel.getFolderStatus(folderRoot);
            if (Objects.equals(currentStatus, newStatus)) {
                return; // no change
            }

            newSynchronizationStatus = domainSynchronizationStatusModel.clone();
            newSynchronizationStatus.setFolderStatus(folderRoot, newStatus);

            this.domainSynchronizationStatus.put(dataDomain, newSynchronizationStatus);
        }

        // send the notification
        this.dqoJobQueueMonitoringService.publishFolderSynchronizationStatus(dataDomain, newSynchronizationStatus);
    }

    /**
     * Returns the current folder synchronization status for each folder that is synchronized do DQOps Cloud.
     * @param dataDomain Data domain name.
     * @return Current folder synchronization status.
     */
    @Override
    public CloudSynchronizationFoldersStatusModel getCurrentSynchronizationStatus(String dataDomain) {
        synchronized (this.lock) {
            CloudSynchronizationFoldersStatusModel domainSynchronizationStatusModel = this.domainSynchronizationStatus.get(dataDomain);
            if (domainSynchronizationStatusModel == null) {
                domainSynchronizationStatusModel = new CloudSynchronizationFoldersStatusModel();
            }
            return domainSynchronizationStatusModel;
        }
    }
}
