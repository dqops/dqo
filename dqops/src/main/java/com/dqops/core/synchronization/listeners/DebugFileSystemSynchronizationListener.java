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
package com.dqops.core.synchronization.listeners;

import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.filesystem.metadata.FileDifference;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.SynchronizationRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * File synchronization listener that shows the output of all logging operations to the console.
 */
@Component
public class DebugFileSystemSynchronizationListener extends SummaryFileSystemSynchronizationListener {
    private TerminalWriter terminalWriter;

    /**
     * Creates a cli file synchronization listener.
     * @param terminalWriter Terminal writer to write the output.
     */
    @Autowired
    public DebugFileSystemSynchronizationListener(TerminalWriter terminalWriter) {
        super(terminalWriter);
        this.terminalWriter = terminalWriter;
    }

    /**
     * Called when a local change (from the source) was applied on the target file system.
     *
     * @param dqoRoot          DQOps User home folder that will be synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference   Change in the source file system that was applied (uploaded, deleted, etc.)
     */
    @Override
    public void onSourceChangeAppliedToTarget(DqoRoot dqoRoot, SynchronizationRoot sourceFileSystem, SynchronizationRoot targetFileSystem, FileDifference fileDifference) {
        super.onSourceChangeAppliedToTarget(dqoRoot, sourceFileSystem, targetFileSystem, fileDifference);

        StringBuilder sb = new StringBuilder();
        sb.append(dqoRoot.toString());
        sb.append(" local -> cloud: ");
        if (fileDifference.isCurrentNew()) {
            sb.append(" [NEW]    ");
        }
        else if (fileDifference.isCurrentChanged()) {
            sb.append(" [CHANGE] ");
        } else if (fileDifference.isCurrentDeleted()) {
            sb.append(" [DELETE] ");
        }

        sb.append(fileDifference.getRelativePath().toString().replace('\\', '/'));

        synchronized (this) {
            terminalWriter.writeLine(sb.toString());
        }
    }

    /**
     * Called when a remote change (from the target system) was applied on the source file system (downloaded).
     *
     * @param dqoRoot          DQOps User home folder that will be synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference   Change in the target (remote) file system that was applied (uploaded, deleted, etc.) on the source system (downloaded).
     */
    @Override
    public void onTargetChangeAppliedToSource(DqoRoot dqoRoot, SynchronizationRoot sourceFileSystem, SynchronizationRoot targetFileSystem, FileDifference fileDifference) {
        super.onTargetChangeAppliedToSource(dqoRoot, sourceFileSystem, targetFileSystem, fileDifference);

        StringBuilder sb = new StringBuilder();
        sb.append(dqoRoot.toString());
        sb.append(" cloud -> local: ");
        if (fileDifference.isCurrentNew()) {
            sb.append(" [NEW]    ");
        }
        else if (fileDifference.isCurrentChanged()) {
            sb.append(" [CHANGE] ");
        } else if (fileDifference.isCurrentDeleted()) {
            sb.append(" [DELETE] ");
        }

        sb.append(fileDifference.getRelativePath().toString().replace('\\', '/'));

        synchronized (this) {
            terminalWriter.writeLine(sb.toString());
        }
    }
}
