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
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.SynchronizationRoot;
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
     * @param dqoRoot          DQOps User home folder that will be synchronized.
     * @param userDomainIdentity Identity of the user who started synchronization and the data domain that is synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    @Override
    public void onSynchronizationBegin(DqoRoot dqoRoot,
                                       UserDomainIdentity userDomainIdentity,
                                       SynchronizationRoot sourceFileSystem,
                                       SynchronizationRoot targetFileSystem) {
        super.onSynchronizationBegin(dqoRoot, userDomainIdentity, sourceFileSystem, targetFileSystem);

        StringBuilder sb = new StringBuilder();
        sb.append(dqoRoot.toString());
        sb.append(" local <-> cloud synchronization started");
        terminalWriter.writeLine(sb.toString());
    }

    /**
     * Called when the synchronization has finished. The synchronization is from the source to the target.
     *
     * @param dqoRoot          DQOps User home folder that will be synchronized.
     * @param userDomainIdentity Identity of the user who started synchronization and the data domain that is synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    @Override
    public void onSynchronizationFinished(DqoRoot dqoRoot,
                                          UserDomainIdentity userDomainIdentity,
                                          SynchronizationRoot sourceFileSystem,
                                          SynchronizationRoot targetFileSystem) {
        super.onSynchronizationFinished(dqoRoot, userDomainIdentity, sourceFileSystem, targetFileSystem);

        StringBuilder sb = new StringBuilder();
        sb.append(dqoRoot.toString());
        sb.append(" local <-> cloud synchronization finished");
        terminalWriter.writeLine(sb.toString());
    }
}
