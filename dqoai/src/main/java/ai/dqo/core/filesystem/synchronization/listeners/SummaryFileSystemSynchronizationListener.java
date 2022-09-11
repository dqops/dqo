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

import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * File synchronization listener that shows only the summary information (folder started, stopped) to the console.
 */
@Component
public class SummaryFileSystemSynchronizationListener extends FileSystemSynchronizationListener {
    private TerminalWriter terminalWriter;

    /**
     * Creates a cli file synchronization listener.
     * @param terminalWriter Terminal writer to write the output.
     */
    @Autowired
    public SummaryFileSystemSynchronizationListener(TerminalWriter terminalWriter) {
        this.terminalWriter = terminalWriter;
    }

    /**
     * Called when the synchronization is about to begin. The synchronization is from the source to the target.
     *
     * @param dqoRoot          DQO User home folder that will be synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    @Override
    public void onSynchronizationBegin(DqoRoot dqoRoot, DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem) {
        super.onSynchronizationBegin(dqoRoot, sourceFileSystem, targetFileSystem);

        StringBuilder sb = new StringBuilder();
        sb.append(dqoRoot.toString());
        sb.append(" local <-> cloud synchronization started");
        terminalWriter.writeLine(sb.toString());
    }

    /**
     * Called when the synchronization has finished. The synchronization is from the source to the target.
     *
     * @param dqoRoot          DQO User home folder that will be synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    @Override
    public void onSynchronizationFinished(DqoRoot dqoRoot, DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem) {
        super.onSynchronizationFinished(dqoRoot, sourceFileSystem, targetFileSystem);

        StringBuilder sb = new StringBuilder();
        sb.append(dqoRoot.toString());
        sb.append(" local <-> cloud synchronization finished");
        terminalWriter.writeLine(sb.toString());
    }
}
