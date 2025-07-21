/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.monitoring;

import com.dqops.core.synchronization.status.CloudSynchronizationFoldersStatusModel;

/**
 * Object published on the notification sink that should be published to the notification listeners. It is added to the change queue.
 */
public final class DqoChangeNotificationEntry {
    private final DqoJobChange jobChange;
    private final String dataDomainName;
    private final CloudSynchronizationFoldersStatusModel synchronizationStatus;

    /**
     * Creates a change notification entry.
     * @param jobChange Optional job change, if a job was changed (started, finished, etc).
     * @param synchronizationStatus Local file synchronization status has changed.
     */
    public DqoChangeNotificationEntry(DqoJobChange jobChange, CloudSynchronizationFoldersStatusModel synchronizationStatus) {
        this.jobChange = jobChange;
        this.synchronizationStatus = synchronizationStatus;
        this.dataDomainName = jobChange.getDomainName();
    }

    /**
     * Creates a change notification entry.
     * @param jobChange Optional job change, if a job was changed (started, finished, etc).
     */
    public DqoChangeNotificationEntry(DqoJobChange jobChange) {
        this.jobChange = jobChange;
        this.dataDomainName = jobChange.getDomainName();
        this.synchronizationStatus = null;
    }

    /**
     * Creates a change notification entry.
     * @param dataDomainName Data domain name.
     * @param synchronizationStatus Local file synchronization status has changed.
     */
    public DqoChangeNotificationEntry(String dataDomainName, CloudSynchronizationFoldersStatusModel synchronizationStatus) {
        this.jobChange = null;
        this.dataDomainName = dataDomainName;
        this.synchronizationStatus = synchronizationStatus;
    }

    /**
     * Returns a job change if the reason of the file notification is a job status change.
     * @return Job status change.
     */
    public DqoJobChange getJobChange() {
        return jobChange;
    }

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    public String getDataDomainName() {
        return dataDomainName;
    }

    /**
     * Returns a new DQOps Cloud file synchronization folder status change if the reason of the notification is a change to a folder status.
     * @return Folder status change.
     */
    public CloudSynchronizationFoldersStatusModel getSynchronizationStatus() {
        return synchronizationStatus;
    }
}
