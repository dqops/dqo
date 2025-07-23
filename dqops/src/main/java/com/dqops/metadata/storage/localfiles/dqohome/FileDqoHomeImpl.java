/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.dqohome;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.dqohome.DqoHomeImpl;
import com.dqops.metadata.storage.localfiles.checkdefinitions.FileCheckDefinitionListImpl;
import com.dqops.metadata.storage.localfiles.dashboards.FileDashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.storage.localfiles.ruledefinitions.FileRuleDefinitionListImpl;
import com.dqops.metadata.storage.localfiles.sensordefinitions.FileSensorDefinitionListImpl;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File based dqo home model that uses a local file system to access the files.
 */
public class FileDqoHomeImpl extends DqoHomeImpl {
    @JsonIgnore
    private final FolderTreeNode homeFolder;
    @JsonIgnore
    private final DqoHomeContext dqoHomeContext;

    public FileDqoHomeImpl(FileSensorDefinitionListImpl sensors,
                           FileRuleDefinitionListImpl rules,
                           FileCheckDefinitionListImpl checks,
                           FileDashboardFolderListSpecWrapperImpl dashboards,
                           DqoHomeContext dqoHomeContext,
                           boolean readOnly) {
        super(sensors, rules, checks, dashboards, readOnly);
        this.dqoHomeContext = dqoHomeContext;
		this.homeFolder = dqoHomeContext.getHomeRoot(); // just a convenience
    }

    /**
     * Creates a file based user home.
     * @param dqoHomeContext Dqo home context.
     * @param yamlSerializer YAML serializer.
     * @param readOnly Open the home in read-only mode.
     * @return File based user home.
     */
    public static FileDqoHomeImpl create(DqoHomeContext dqoHomeContext, YamlSerializer yamlSerializer, boolean readOnly) {
        FolderTreeNode sensorsFolder = dqoHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SENSORS);
        FolderTreeNode rulesFolder = dqoHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.RULES);
        FolderTreeNode checksFolder = dqoHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.CHECKS);
        FolderTreeNode settingsFolder = dqoHomeContext.getHomeRoot().getOrAddDirectFolder(BuiltInFolderNames.SETTINGS);
        FileSensorDefinitionListImpl sensors = new FileSensorDefinitionListImpl(sensorsFolder, yamlSerializer, readOnly);
        FileRuleDefinitionListImpl rules = new FileRuleDefinitionListImpl(rulesFolder, yamlSerializer, readOnly);
        FileCheckDefinitionListImpl checks = new FileCheckDefinitionListImpl(checksFolder, yamlSerializer, readOnly);
        FileDashboardFolderListSpecWrapperImpl dashboards = new FileDashboardFolderListSpecWrapperImpl(settingsFolder, yamlSerializer, readOnly);

        return new FileDqoHomeImpl(sensors, rules, checks, dashboards, dqoHomeContext, readOnly);
    }

    /**
     * Returns the file tree used to access the file system.
     * @return Folder node for the root home folder.
     */
    public FolderTreeNode getHomeFolder() {
        return homeFolder;
    }
}
