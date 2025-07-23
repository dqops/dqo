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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of statuses identifying the synchronization status for each folder that can be synchronized with the DQOps cloud.
 */
public enum FolderSynchronizationStatus {
    /**
     * The folder has no changes that should be synchronized to the cloud.
     */
    @JsonProperty("unchanged")
    unchanged,

    /**
     * The folder has local changes that should be synchronized to the cloud.
     */
    @JsonProperty("changed")
    changed,

    /**
     * A synchronization job is running on the folder right now.
     */
    @JsonProperty("synchronizing")
    synchronizing
}
