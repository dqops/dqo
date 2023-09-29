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
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.storage.localfiles.checkdefinitions.FileCheckDefinitionListImpl;
import com.dqops.metadata.storage.localfiles.credentials.FileSharedCredentialListImpl;
import com.dqops.metadata.storage.localfiles.dashboards.FileDashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.storage.localfiles.fileindices.FileFileIndexListImpl;
import com.dqops.metadata.storage.localfiles.monitoringschedules.FileMonitoringSchedulesWrapperImpl;
import com.dqops.metadata.storage.localfiles.observabilitychecksettings.FileObservabilityCheckWrapperImpl;
import com.dqops.metadata.storage.localfiles.ruledefinitions.FileRuleDefinitionListImpl;
import com.dqops.metadata.storage.localfiles.sensordefinitions.FileSensorDefinitionListImpl;
import com.dqops.metadata.storage.localfiles.settings.FileSettingsWrapperImpl;
import com.dqops.metadata.storage.localfiles.sources.FileConnectionListImpl;
import com.dqops.metadata.storage.localfiles.webhooks.FileDefaultIncidentWebhookNotificationsWrapperImpl;
import com.dqops.metadata.userhome.UserHomeImpl;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.YamlSerializer;
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
     * @param rules Custom rules list.
     * @param checks Custom checks list.
     * @param settings Settings.
     * @param credentials Credentials.
     * @param fileIndices File indices list.
     * @param dashboards Custom dashboards.
     * @param userHomeContext User home context.
     */
    public FileUserHomeImpl(FileConnectionListImpl sources,
                            FileSensorDefinitionListImpl sensors,
                            FileRuleDefinitionListImpl rules,
                            FileCheckDefinitionListImpl checks,
                            FileSettingsWrapperImpl settings,
                            FileSharedCredentialListImpl credentials,
                            FileFileIndexListImpl fileIndices,
                            FileDashboardFolderListSpecWrapperImpl dashboards,
                            FileMonitoringSchedulesWrapperImpl monitoringSchedules,
                            FileObservabilityCheckWrapperImpl observabilityCheck,
                            FileDefaultIncidentWebhookNotificationsWrapperImpl notificationWebhooks,
                            UserHomeContext userHomeContext) {
        super(sources, sensors, rules, checks, settings, credentials, fileIndices, dashboards,
                monitoringSchedules, observabilityCheck, notificationWebhooks);
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
        FolderTreeNode checksFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.CHECKS);
        FolderTreeNode indexFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.INDEX);
        FolderTreeNode settingsFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SETTINGS);
        FolderTreeNode credentialsFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.CREDENTIALS);
        FolderTreeNode localSettingsFolder = userHomeContext.getHomeRoot();

        FileConnectionListImpl dataSources = new FileConnectionListImpl(sourcesFolder, yamlSerializer);
        FileSensorDefinitionListImpl sensors = new FileSensorDefinitionListImpl(sensorsFolder, yamlSerializer);
        FileRuleDefinitionListImpl rules = new FileRuleDefinitionListImpl(rulesFolder, yamlSerializer);
        FileCheckDefinitionListImpl checks = new FileCheckDefinitionListImpl(checksFolder, yamlSerializer);
        FileSettingsWrapperImpl settings = new FileSettingsWrapperImpl(localSettingsFolder, yamlSerializer);
        FileSharedCredentialListImpl credentials = new FileSharedCredentialListImpl(credentialsFolder);
        FileFileIndexListImpl fileIndices = new FileFileIndexListImpl(indexFolder, jsonSerializer);
        FileDashboardFolderListSpecWrapperImpl dashboards = new FileDashboardFolderListSpecWrapperImpl(settingsFolder, yamlSerializer);
        FileMonitoringSchedulesWrapperImpl monitoringSchedules = new FileMonitoringSchedulesWrapperImpl(settingsFolder, yamlSerializer);
        FileObservabilityCheckWrapperImpl observabilityCheckSettings = new FileObservabilityCheckWrapperImpl(settingsFolder, yamlSerializer);
        FileDefaultIncidentWebhookNotificationsWrapperImpl notificationWebhooks = new FileDefaultIncidentWebhookNotificationsWrapperImpl(settingsFolder, yamlSerializer);

        return new FileUserHomeImpl(dataSources, sensors, rules, checks, settings, credentials, fileIndices, dashboards,
                monitoringSchedules, observabilityCheckSettings, notificationWebhooks, userHomeContext);
    }

    /**
     * Returns the file tree used to access the file system.
     * @return Folder node for the root home folder.
     */
    public FolderTreeNode getHomeFolder() {
        return homeFolder;
    }
}
