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
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.storage.localfiles.checkdefinitions.FileCheckDefinitionListImpl;
import com.dqops.metadata.storage.localfiles.columndefaultpatterns.FileColumnDefaultChecksPatternListImpl;
import com.dqops.metadata.storage.localfiles.credentials.FileSharedCredentialListImpl;
import com.dqops.metadata.storage.localfiles.dashboards.FileDashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.storage.localfiles.dictionaries.FileDictionaryListImpl;
import com.dqops.metadata.storage.localfiles.fileindices.FileFileIndexListImpl;
import com.dqops.metadata.storage.localfiles.defaultschedules.FileMonitoringSchedulesWrapperImpl;
import com.dqops.metadata.storage.localfiles.ruledefinitions.FileRuleDefinitionListImpl;
import com.dqops.metadata.storage.localfiles.sensordefinitions.FileSensorDefinitionListImpl;
import com.dqops.metadata.storage.localfiles.settings.FileSettingsWrapperImpl;
import com.dqops.metadata.storage.localfiles.sources.FileConnectionListImpl;
import com.dqops.metadata.storage.localfiles.defaultnotifications.FileDefaultIncidentNotificationsWrapperImpl;
import com.dqops.metadata.storage.localfiles.tabledefaultpatterns.FileTableDefaultChecksPatternListImpl;
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
     * @param userIdentity Calling user identity that specifies the data domain.
     * @param sources Sources list.
     * @param sensors Custom sensor list.
     * @param rules Custom rules list.
     * @param checks Custom checks list.
     * @param settings Settings.
     * @param credentials Credentials.
     * @param dictionaries Data dictionaries.
     * @param fileIndices File indices list.
     * @param dashboards Custom dashboards.
     * @param tableDefaultChecksPattern Default table check patterns.
     * @param columnDefaultChecksPattern Default column check patterns.
     * @param userHomeContext User home context.
     * @param readOnly Make the user home read-only.
     */
    public FileUserHomeImpl(UserDomainIdentity userIdentity,
                            FileConnectionListImpl sources,
                            FileSensorDefinitionListImpl sensors,
                            FileRuleDefinitionListImpl rules,
                            FileCheckDefinitionListImpl checks,
                            FileSettingsWrapperImpl settings,
                            FileSharedCredentialListImpl credentials,
                            FileDictionaryListImpl dictionaries,
                            FileFileIndexListImpl fileIndices,
                            FileDashboardFolderListSpecWrapperImpl dashboards,
                            FileMonitoringSchedulesWrapperImpl monitoringSchedules,
                            FileTableDefaultChecksPatternListImpl tableDefaultChecksPattern,
                            FileColumnDefaultChecksPatternListImpl columnDefaultChecksPattern,
                            FileDefaultIncidentNotificationsWrapperImpl notificationWebhooks,
                            UserHomeContext userHomeContext,
                            boolean readOnly) {
        super(userIdentity, sources, sensors, rules, checks, settings, credentials, dictionaries, fileIndices, dashboards,
                monitoringSchedules, tableDefaultChecksPattern, columnDefaultChecksPattern, notificationWebhooks, readOnly);
        this.userHomeContext = userHomeContext;
		this.homeFolder = userHomeContext.getHomeRoot(); // just a convenience
    }

    /**
     * Creates a file based user home.
     * @param userHomeContext User home context.
     * @param yamlSerializer Configured YAML serializer.
     * @param jsonSerializer Configured JSON serializer.
     * @param readOnly Create the user home in a read-only mode.
     * @return File based user home.
     */
    public static FileUserHomeImpl create(UserHomeContext userHomeContext, YamlSerializer yamlSerializer, JsonSerializer jsonSerializer, boolean readOnly) {
        FolderTreeNode sourcesFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SOURCES);
        FolderTreeNode sensorsFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SENSORS);
        FolderTreeNode rulesFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode checksFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.CHECKS);
        FolderTreeNode indexFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.INDEX);
        FolderTreeNode settingsFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SETTINGS);
        FolderTreeNode credentialsFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.CREDENTIALS);
        FolderTreeNode dictionariesFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.DICTIONARIES);
        FolderTreeNode patternsFolder = userHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.PATTERNS);
        FolderTreeNode localSettingsFolder = userHomeContext.getHomeRoot();

        FileConnectionListImpl dataSources = new FileConnectionListImpl(sourcesFolder, yamlSerializer, readOnly);
        FileSensorDefinitionListImpl sensors = new FileSensorDefinitionListImpl(sensorsFolder, yamlSerializer, readOnly);
        FileRuleDefinitionListImpl rules = new FileRuleDefinitionListImpl(rulesFolder, yamlSerializer, readOnly);
        FileCheckDefinitionListImpl checks = new FileCheckDefinitionListImpl(checksFolder, yamlSerializer, readOnly);
        FileSettingsWrapperImpl settings = new FileSettingsWrapperImpl(localSettingsFolder, yamlSerializer, readOnly);
        FileSharedCredentialListImpl credentials = new FileSharedCredentialListImpl(credentialsFolder, readOnly);
        FileDictionaryListImpl dictionaries = new FileDictionaryListImpl(dictionariesFolder, readOnly);
        FileFileIndexListImpl fileIndices = new FileFileIndexListImpl(indexFolder, jsonSerializer, readOnly);
        FileDashboardFolderListSpecWrapperImpl dashboards = new FileDashboardFolderListSpecWrapperImpl(settingsFolder, yamlSerializer, readOnly);
        FileMonitoringSchedulesWrapperImpl monitoringSchedules = new FileMonitoringSchedulesWrapperImpl(settingsFolder, yamlSerializer, readOnly);
        FileTableDefaultChecksPatternListImpl tableDefaultChecksPatterns = new FileTableDefaultChecksPatternListImpl(patternsFolder, yamlSerializer, readOnly);
        FileColumnDefaultChecksPatternListImpl columnDefaultChecksPatterns = new FileColumnDefaultChecksPatternListImpl(patternsFolder, yamlSerializer, readOnly);
        FileDefaultIncidentNotificationsWrapperImpl notificationWebhooks = new FileDefaultIncidentNotificationsWrapperImpl(settingsFolder, yamlSerializer, readOnly);

        return new FileUserHomeImpl(userHomeContext.getUserIdentity(), dataSources,
                sensors, rules, checks, settings, credentials, dictionaries, fileIndices, dashboards,
                monitoringSchedules, tableDefaultChecksPatterns, columnDefaultChecksPatterns,
                notificationWebhooks, userHomeContext, readOnly);
    }

    /**
     * Returns the file tree used to access the file system.
     * @return Folder node for the root home folder.
     */
    public FolderTreeNode getHomeFolder() {
        return homeFolder;
    }
}
