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
package ai.dqo.metadata.storage.localfiles.dqohome;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.virtual.FolderTreeNode;
import ai.dqo.metadata.dqohome.DqoHomeImpl;
import ai.dqo.metadata.storage.localfiles.dashboards.FileDashboardFolderListSpecWrapperImpl;
import ai.dqo.metadata.storage.localfiles.ruledefinitions.FileRuleDefinitionListImpl;
import ai.dqo.metadata.storage.localfiles.sensordefinitions.FileSensorDefinitionListImpl;
import ai.dqo.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File based dqo home model that uses a local file system to access the files.
 */
public class FileDqoHomeImpl extends DqoHomeImpl {
    @JsonIgnore
    private final FolderTreeNode homeFolder;
    @JsonIgnore
    private final DqoHomeContext dqoHomeContext;

    public FileDqoHomeImpl(FileSensorDefinitionListImpl sensors, FileRuleDefinitionListImpl customRules, FileDashboardFolderListSpecWrapperImpl dashboards, DqoHomeContext dqoHomeContext) {
        super(sensors, customRules, dashboards);
        this.dqoHomeContext = dqoHomeContext;
		this.homeFolder = dqoHomeContext.getHomeRoot(); // just a convenience
    }

    /**
     * Creates a file based user home.
     * @param dqoHomeContext Dqo home context.
     * @return File based user home.
     */
    public static FileDqoHomeImpl create(DqoHomeContext dqoHomeContext, YamlSerializer yamlSerializer) {
        FolderTreeNode sensorsFolder = dqoHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SENSORS);
        FolderTreeNode rulesFolder = dqoHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode dashboardsFolder = dqoHomeContext.getHomeRoot();
        FileSensorDefinitionListImpl sensors = new FileSensorDefinitionListImpl(sensorsFolder, yamlSerializer);
        FileRuleDefinitionListImpl rules = new FileRuleDefinitionListImpl(rulesFolder, yamlSerializer);
        FileDashboardFolderListSpecWrapperImpl dashboards = new FileDashboardFolderListSpecWrapperImpl(dashboardsFolder, yamlSerializer);

        return new FileDqoHomeImpl(sensors, rules, dashboards, dqoHomeContext);
    }

    /**
     * Returns the file tree used to access the file system.
     * @return Folder node for the root home folder.
     */
    public FolderTreeNode getHomeFolder() {
        return homeFolder;
    }
}
