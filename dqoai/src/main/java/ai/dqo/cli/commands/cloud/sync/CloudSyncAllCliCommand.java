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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * 3st level CLI command "cloud sync all" to synchronize all the files the DQO user home.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "all", description = "Synchronize local files with DQO Cloud (sources, table rules, custom rules, custom sensors and data - sensor readings and alerts)")
public class CloudSyncAllCliCommand extends BaseCommand implements ICommand {
    private CloudSynchronizationService cloudSynchronizationService;

    @Autowired
    public CloudSyncAllCliCommand(CloudSynchronizationService cloudSynchronizationService) {
        this.cloudSynchronizationService = cloudSynchronizationService;
    }

    @CommandLine.Option(names = {"-f", "--show-files"}, description = "Show progress for all files", defaultValue = "true", required = false)
    private boolean showFiles = true;

    /**
     * True when each file should be shown.
     * @return Show files.
     */
    public boolean isShowFiles() {
        return showFiles;
    }

    /**
     * Sets the show files flag.
     * @param showFiles Show files flag.
     */
    public void setShowFiles(boolean showFiles) {
        this.showFiles = showFiles;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        int synchronizeSourcesResult = this.cloudSynchronizationService.synchronizeRoot(DqoRoot.SOURCES, this.showFiles, this.isHeadless());
        if (synchronizeSourcesResult < 0) {
            return synchronizeSourcesResult;
        }

        int synchronizeRulesResult = this.cloudSynchronizationService.synchronizeRoot(DqoRoot.RULES, this.showFiles, this.isHeadless());
        if (synchronizeRulesResult < 0) {
            return synchronizeRulesResult;
        }

        int synchronizeSensorsResult = this.cloudSynchronizationService.synchronizeRoot(DqoRoot.SENSORS, this.showFiles, this.isHeadless());
        if (synchronizeSensorsResult < 0) {
            return synchronizeSensorsResult;
        }

        int synchronizeReadingsResult = this.cloudSynchronizationService.synchronizeRoot(DqoRoot.DATA_READINGS, this.showFiles, this.isHeadless());
        if (synchronizeReadingsResult < 0) {
            return synchronizeReadingsResult;
        }

        int synchronizeAlertsResult = this.cloudSynchronizationService.synchronizeRoot(DqoRoot.DATA_ALERTS, this.showFiles, this.isHeadless());
        return synchronizeAlertsResult;
    }
}
