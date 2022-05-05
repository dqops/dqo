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
package ai.dqo.cli.commands.cloud.sync.impl;

import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.metadata.FileDifference;
import ai.dqo.core.filesystem.synchronization.BaseFileSystemSynchronizationListener;

/**
 * File synchronization service to be used during the file synchronization. Shows progress.
 */
public class CliFileSystemSynchronizationListener extends BaseFileSystemSynchronizationListener {
    private DqoRoot rootType;
    private boolean reportFiles;
    private TerminalWriter terminalWriter;

    /**
     * Creates a cli file synchronization listener.
     * @param reportFiles True when every synchronized file should be reported.
     * @param terminalWriter Terminal writer to write the output.
     */
    public CliFileSystemSynchronizationListener(DqoRoot rootType, boolean reportFiles, TerminalWriter terminalWriter) {
        this.rootType = rootType;
        this.reportFiles = reportFiles;
        this.terminalWriter = terminalWriter;
    }

    /**
     * Called when a local change (from the source) was applied on the target file system.
     *
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference   Change in the source file system that was applied (uploaded, deleted, etc.)
     */
    @Override
    public void onSourceChangeAppliedToTarget(DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem, FileDifference fileDifference) {
        if (!this.reportFiles) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(rootType.toString());
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

        terminalWriter.writeLine(sb.toString());
    }

    /**
     * Called when a remote change (from the target system) was applied on the source file system (downloaded).
     *
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference   Change in the target (remote) file system that was applied (uploaded, deleted, etc.) on the source system (downloaded).
     */
    @Override
    public void onTargetChangeAppliedToSource(DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem, FileDifference fileDifference) {
        if (!this.reportFiles) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(rootType.toString());
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

        terminalWriter.writeLine(sb.toString());
    }
}
