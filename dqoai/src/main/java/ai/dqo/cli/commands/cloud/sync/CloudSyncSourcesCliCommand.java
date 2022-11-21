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
package ai.dqo.cli.commands.cloud.sync;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.cloud.sync.impl.CloudSynchronizationService;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * 3st level CLI command "cloud sync sources" to synchronize the "sources" folder in the DQO user home.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "sources", description = "Synchronize local \"sources\" connection and table level quality definitions with DQO Cloud")
public class CloudSyncSourcesCliCommand extends BaseCommand implements ICommand {
    private CloudSynchronizationService cloudSynchronizationService;

    @Autowired
    public CloudSyncSourcesCliCommand(CloudSynchronizationService cloudSynchronizationService) {
        this.cloudSynchronizationService = cloudSynchronizationService;
    }

    @CommandLine.Option(names = {"-m", "--mode"}, description = "Reporting mode (silent, summary, debug)", defaultValue = "summary")
    private FileSystemSynchronizationReportingMode mode = FileSystemSynchronizationReportingMode.summary;

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
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        return this.cloudSynchronizationService.synchronizeRoot(DqoRoot.SOURCES, this.mode, this.isHeadless(), true);
    }
}
