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

import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.filesystem.metadata.FileDifference;
import com.dqops.core.principal.UserDomainIdentity;
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
     * @param userDomainIdentity Identity of the user who started synchronization and the data domain that is synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference   Change in the source file system that was applied (uploaded, deleted, etc.)
     */
    @Override
    public void onSourceChangeAppliedToTarget(DqoRoot dqoRoot,
                                              UserDomainIdentity userDomainIdentity,
                                              SynchronizationRoot sourceFileSystem,
                                              SynchronizationRoot targetFileSystem,
                                              FileDifference fileDifference) {
        super.onSourceChangeAppliedToTarget(dqoRoot, userDomainIdentity, sourceFileSystem, targetFileSystem, fileDifference);

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
     * @param userDomainIdentity Identity of the user who started synchronization and the data domain that is synchronized.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference   Change in the target (remote) file system that was applied (uploaded, deleted, etc.) on the source system (downloaded).
     */
    @Override
    public void onTargetChangeAppliedToSource(DqoRoot dqoRoot,
                                              UserDomainIdentity userDomainIdentity,
                                              SynchronizationRoot sourceFileSystem,
                                              SynchronizationRoot targetFileSystem,
                                              FileDifference fileDifference) {
        super.onTargetChangeAppliedToSource(dqoRoot, userDomainIdentity, sourceFileSystem, targetFileSystem, fileDifference);

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
