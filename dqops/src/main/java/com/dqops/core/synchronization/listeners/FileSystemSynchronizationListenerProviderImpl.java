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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * File synchronization listener factory.
 */
@Component
public class FileSystemSynchronizationListenerProviderImpl implements FileSystemSynchronizationListenerProvider {
    private SilentFileSystemSynchronizationListener silentFileSystemSynchronizationListener;
    private SummaryFileSystemSynchronizationListener summaryFileSystemSynchronizationListener;
    private DebugFileSystemSynchronizationListener debugFileSystemSynchronizationListener;

    @Autowired
    public FileSystemSynchronizationListenerProviderImpl(SilentFileSystemSynchronizationListener silentFileSystemSynchronizationListener,
                                                         SummaryFileSystemSynchronizationListener summaryFileSystemSynchronizationListener,
                                                         DebugFileSystemSynchronizationListener debugFileSystemSynchronizationListener) {
        this.silentFileSystemSynchronizationListener = silentFileSystemSynchronizationListener;
        this.summaryFileSystemSynchronizationListener = summaryFileSystemSynchronizationListener;
        this.debugFileSystemSynchronizationListener = debugFileSystemSynchronizationListener;
    }

    /**
     * Returns the requested synchronization listener for the given reporting mode.
     * @param reportingMode Reporting mode (silent, summary, debug).
     * @return Synchronization listener.
     */
    @Override
    public FileSystemSynchronizationListener getSynchronizationListener(FileSystemSynchronizationReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return this.silentFileSystemSynchronizationListener;
            case summary:
                return this.summaryFileSystemSynchronizationListener;
            case debug:
                return debugFileSystemSynchronizationListener;
            default:
                return this.silentFileSystemSynchronizationListener;
        }
    }
}
