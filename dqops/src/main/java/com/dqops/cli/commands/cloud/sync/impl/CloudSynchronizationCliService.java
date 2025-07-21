/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.cloud.sync.impl;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;

/**
 * Service called by "cloud sync" CLI commands to synchronize the data with DQOps Cloud.
 */
public interface CloudSynchronizationCliService {
    /**
     * Synchronize a folder type to/from DQOps Cloud.
     * @param rootType      Root type.
     * @param reportingMode File synchronization progress reporting mode.
     * @param headlessMode  The application was started in a headless mode and should not bother the user with questions (prompts).
     * @param synchronizationDirection File synchronization direction.
     * @param forceRefreshNativeTable Forces to refresh a whole native table for data folders.
     * @param runOnBackgroundQueue True when the actual synchronization operation should be executed in the background on the DQOps job queue.
     *                             False when the operation should be executed on the caller's thread.
     * @param principal Principal that will be used to run the job.
     * @return 0 when success, -1 when an error, -2 when login to cloud dqo failed.
     */
    int synchronizeRoot(DqoRoot rootType,
                        FileSystemSynchronizationReportingMode reportingMode,
                        FileSynchronizationDirection synchronizationDirection,
                        boolean forceRefreshNativeTable,
                        boolean headlessMode,
                        boolean runOnBackgroundQueue,
                        DqoUserPrincipal principal);
}
