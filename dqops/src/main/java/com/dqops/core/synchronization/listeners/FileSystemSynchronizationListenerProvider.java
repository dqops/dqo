/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.listeners;

/**
 * File synchronization listener factory.
 */
public interface FileSystemSynchronizationListenerProvider {
    /**
     * Returns the requested synchronization listener for the given reporting mode.
     *
     * @param reportingMode Reporting mode (silent, summary, debug).
     * @return Synchronization listener.
     */
    FileSystemSynchronizationListener getSynchronizationListener(FileSystemSynchronizationReportingMode reportingMode);
}
