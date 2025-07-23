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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;

/**
 * Service that is called on startup, detects if there are any unsynchronized changes in any DQOps User Home folder that should be synchronized to the DQOps Cloud.
 */
public interface FileSynchronizationChangeDetectionService {
    /**
     * Detects if there are any unsynchronized changes in a given DQOps User home folder.
     *
     * @param dqoRoot User home folder to be analyzed.
     * @param userDomainIdentity User identity that identifies the data domain.
     * @return True when there are local unsynchronized changes, false otherwise.
     */
    boolean detectNotSynchronizedChangesInFolder(DqoRoot dqoRoot, UserDomainIdentity userDomainIdentity);

    /**
     * Detects changes in a folder. Optionally publishes a folder change status to "changed" if a change is detected.
     *
     * @param userDomainIdentity User identity that identifies the data domain.
     * @param dqoRoot Folder to be analyzed.
     */
    void detectAndPublishLocalFolderStatus(DqoRoot dqoRoot, UserDomainIdentity userDomainIdentity);

    /**
     * Starts a background job that checks all folders and tries to detect local changes that were not yet synchronized to DQOps Cloud.
     */
    void detectNotSynchronizedChangesAllDomains();

    /**
     * Detect changes in the local user home for the given data domain.
     * @param domainName Data domain name.
     */
    void detectNotSynchronizedChangesInDomain(String domainName);
}
