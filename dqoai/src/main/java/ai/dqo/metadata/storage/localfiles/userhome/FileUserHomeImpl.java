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
package ai.dqo.metadata.storage.localfiles.userhome;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.virtual.FolderTreeNode;
import ai.dqo.metadata.storage.localfiles.fileindices.FileFileIndexListImpl;
import ai.dqo.metadata.storage.localfiles.ruledefinitions.FileRuleDefinitionListImpl;
import ai.dqo.metadata.storage.localfiles.sensordefinitions.FileSensorDefinitionListImpl;
import ai.dqo.metadata.storage.localfiles.settings.FileSettingsWrapperImpl;
import ai.dqo.metadata.storage.localfiles.sources.FileConnectionListImpl;
import ai.dqo.metadata.userhome.UserHomeImpl;
import ai.dqo.utils.serialization.JsonSerializer;
import ai.dqo.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File based home model that uses a local file system to access the files.
 */
public class FileUserHomeImpl extends UserHomeImpl {
    @JsonIgnore
    private final FolderTreeNode homeFolder;
    @JsonIgnore
    private final UserHomeContext userHomeContext;

    /**
     * Creates a file based user home implementation.
     * @param sources Sources list.
     * @param sensors Custom sensor list.
     * @param customRules Custom rules list.
     * @param settings Settings.
     * @param fileIndices File indices list.
     * @param userHomeContext User home context.
     */
    public FileUserHomeImpl(FileConnectionListImpl sources,
                            FileSensorDefinitionListImpl sensors,
                            FileRuleDefinitionListImpl customRules,
                            FileSettingsWrapperImpl settings,
                            FileFileIndexListImpl fileIndices,
                            UserHomeContext userHomeContext) {
        super(sources, sensors, customRules, settings, fileIndices);
        this.userHomeContext = userHomeContext;
		this.homeFolder = userHomeContext.getHomeRoot(); // just a convenience
    }

    /**
     * Creates a file based user home.
     * @param userHomeContext User home context.
     * @param yamlSerializer Configured YAML serializer.
     * @param jsonSerializer Configured JSON serializer.
     * @return File based user home.
     */
    public static FileUserHomeImpl create(UserHomeContext userHomeContext, YamlSerializer yamlSerializer, JsonSerializer jsonSerializer) {
        FolderTreeNode sourcesFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SOURCES);
        FolderTreeNode sensorsFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SENSORS);
        FolderTreeNode rulesFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode indexFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.INDEX);
        FolderTreeNode settingsFolder = userHomeContext.getHomeRoot();
        FileConnectionListImpl dataSources = new FileConnectionListImpl(sourcesFolder, yamlSerializer);
        FileSensorDefinitionListImpl sensors = new FileSensorDefinitionListImpl(sensorsFolder, yamlSerializer);
        FileRuleDefinitionListImpl rules = new FileRuleDefinitionListImpl(rulesFolder, yamlSerializer);
        FileSettingsWrapperImpl settings = new FileSettingsWrapperImpl(settingsFolder, yamlSerializer);
        FileFileIndexListImpl fileIndices = new FileFileIndexListImpl(indexFolder, jsonSerializer);
        return new FileUserHomeImpl(dataSources, sensors, rules, settings, fileIndices, userHomeContext);
    }

    /**
     * Returns the file tree used to access the file system.
     * @return Folder node for the root home folder.
     */
    public FolderTreeNode getHomeFolder() {
        return homeFolder;
    }
}
