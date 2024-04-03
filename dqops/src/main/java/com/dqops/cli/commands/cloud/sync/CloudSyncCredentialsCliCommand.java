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
package com.dqops.cli.commands.cloud.sync;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.cloud.sync.impl.CloudSynchronizationCliService;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.dqocloud.accesskey.DqoCloudCredentialsException;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobExecutionException;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * 3st level CLI command "cloud sync credentials" to synchronize the ".credentials" folder in the DQOps user home.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "credentials", header = "Synchronize local \".credentials\" folder that stores shared credentials with DQOps Cloud",
        description = "Uploads any local changes to the cloud and downloads any changes made to the cloud version of the \".credentials\" folder.")
public class CloudSyncCredentialsCliCommand extends BaseCommand implements ICommand {
    private CloudSynchronizationCliService cloudSynchronizationCliService;
    private TerminalFactory terminalFactory;
    private DqoUserPrincipalProvider principalProvider;

    public CloudSyncCredentialsCliCommand() {
    }

    @Autowired
    public CloudSyncCredentialsCliCommand(CloudSynchronizationCliService cloudSynchronizationCliService,
                                          TerminalFactory terminalFactory,
                                          DqoUserPrincipalProvider principalProvider) {
        this.cloudSynchronizationCliService = cloudSynchronizationCliService;
        this.terminalFactory = terminalFactory;
        this.principalProvider = principalProvider;
    }

    @CommandLine.Option(names = {"-m", "--mode"}, description = "Reporting mode (silent, summary, debug)", defaultValue = "summary")
    private FileSystemSynchronizationReportingMode mode = FileSystemSynchronizationReportingMode.summary;

    @CommandLine.Option(names = {"-d", "--direction"}, description = "File synchronization direction", defaultValue = "full")
    private FileSynchronizationDirection direction = FileSynchronizationDirection.full;

    /**
     * Returns the synchronization logging mode.
     * @return Logging mode.
     */
    public FileSystemSynchronizationReportingMode getMode() {
        return mode;
    }

    /**
     * Sets the reporting (logging) mode.
     * @param mode Reporting mode.
     */
    public void setMode(FileSystemSynchronizationReportingMode mode) {
        this.mode = mode;
    }

    /**
     * Returns the file synchronization direction.
     * @return File synchronization direction.
     */
    public FileSynchronizationDirection getDirection() {
        return direction;
    }

    /**
     * Sets the file synchronization direction.
     * @param direction File synchronization direction.
     */
    public void setDirection(FileSynchronizationDirection direction) {
        this.direction = direction;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        try {
            DqoUserPrincipal principal = this.principalProvider.getLocalUserPrincipal();
            return this.cloudSynchronizationCliService.synchronizeRoot(
                    DqoRoot.credentials, this.mode, this.direction, false, this.isHeadless(), true, principal);
        }
        catch (DqoQueueJobExecutionException cex) {
            if (cex.getRealCause() instanceof DqoCloudCredentialsException) {
                TerminalWriter terminalWriter = this.terminalFactory.getWriter();
                terminalWriter.writeLine(CloudSyncCliCommand.API_KEY_INVALID_MESSAGE);
                return -1;
            }

            throw cex;
        }
    }
}
