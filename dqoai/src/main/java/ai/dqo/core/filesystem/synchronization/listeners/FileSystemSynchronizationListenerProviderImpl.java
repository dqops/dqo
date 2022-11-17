/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.filesystem.synchronization.listeners;

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
