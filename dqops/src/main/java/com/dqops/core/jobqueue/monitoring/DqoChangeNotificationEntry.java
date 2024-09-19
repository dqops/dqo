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
