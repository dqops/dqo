/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.scheduler.synchronize;

/**
 * Enumeration of synchronization modes (selection of folders that are synchronized to the DQOps Cloud) during a scheduled synchronization.
 */
public enum ScheduledSynchronizationFolderSelectionMode {
    /**
     * Always synchronizes all folders, possibly downloading remote changes from the DQOps Cloud.
     */
    all,

    /**
     * Only synchronize folders with local changes.
     */
    locally_changed
}
