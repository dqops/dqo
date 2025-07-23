/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.dashboards;

import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.dashboards.DashboardsFolderListSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File based dashboard spec wrapper. Loads and writes the dashboards information to a yaml file in the user's home folder.
 */
public class FileDashboardFolderListSpecWrapperImpl extends DashboardFolderListSpecWrapperImpl {

    @JsonIgnore
    private final FolderTreeNode dashboardsFolderNode;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a settings wrapper for a dashboards specification that uses yaml files for storage.
     * @param dashboardsFolderNode Folder with yaml files for dashboards specifications.
     * @param yamlSerializer Yaml serializer.
     * @param readOnly Make the list read-only.
     */
    public FileDashboardFolderListSpecWrapperImpl(FolderTreeNode dashboardsFolderNode, YamlSerializer yamlSerializer, boolean readOnly) {
        super(readOnly);
        this.dashboardsFolderNode = dashboardsFolderNode;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Loads the table spec with the table details.
     *
     * @return Loaded table specification.
     */
    @Override
    public DashboardsFolderListSpec getSpec() {
        DashboardsFolderListSpec spec = super.getSpec();
        if (spec == null && this.getStatus() == InstanceStatus.LOAD_IN_PROGRESS) {
            FileTreeNode fileNode = this.dashboardsFolderNode.getChildFileByFileName(SpecFileNames.DASHBOARDS_SPEC_FILE_NAME_YAML);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                this.setLastModified(fileContent.getLastModified());
                String textContent = fileContent.getTextContent();
                DashboardsFolderListSpec deserializedSpec = (DashboardsFolderListSpec) fileContent.getCachedObjectInstance();

                if (deserializedSpec == null) {
                    DashboardYaml deserialized = this.yamlSerializer.deserialize(textContent, DashboardYaml.class, fileNode.getPhysicalAbsolutePath());
                    deserializedSpec = deserialized.getSpec();
                    if (deserializedSpec == null) {
                        deserializedSpec = new DashboardsFolderListSpec();
                    }

                    deserializedSpec.setFileLastModified(fileContent.getLastModified());
                    if (deserialized.getKind() != SpecificationKind.dashboards) {
                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }

                    DashboardsFolderListSpec cachedObjectInstance = deserializedSpec.deepClone();
                    cachedObjectInstance.makeReadOnly(true);
                    if (this.getHierarchyId() != null) {
                        cachedObjectInstance.setHierarchyId(new HierarchyId(this.getHierarchyId(), "spec"));
                    }
                    fileContent.setCachedObjectInstance(cachedObjectInstance);
                } else {
                    deserializedSpec = this.isReadOnly() ? deserializedSpec : deserializedSpec.deepClone();
                }
                this.setSpec(deserializedSpec);
                this.clearDirty(false);
                return deserializedSpec;
            } else {
                this.setSpec(null);
            }
        }
        return spec;
    }

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    @Override
    public void flush() {
        if (this.getStatus() == InstanceStatus.DELETED || this.getStatus() == InstanceStatus.NOT_TOUCHED) {
            return; // do nothing
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() == null) {
            return; // nothing to do, the instance is empty (no file)
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty() ) {
            super.getSpec().clearDirty(true);
            this.setStatus(InstanceStatus.MODIFIED);
        }

        DashboardYaml dashboardYaml = new DashboardYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(dashboardYaml);
        FileContent newFileContent = new FileContent(specAsYaml);
        String fileNameWithExt = SpecFileNames.DASHBOARDS_SPEC_FILE_NAME_YAML;

        switch (this.getStatus()) {
            case ADDED:
                this.dashboardsFolderNode.addChildFile(fileNameWithExt, newFileContent);
                this.getSpec().clearDirty(true);
                break;

            case MODIFIED:
                FileTreeNode modifiedFileNode = this.dashboardsFolderNode.getChildFileByFileName(fileNameWithExt);
                if (modifiedFileNode != null) {
                    modifiedFileNode.changeContent(newFileContent);
                }
                else {
                    this.dashboardsFolderNode.addChildFile(fileNameWithExt, newFileContent);
                }
                this.getSpec().clearDirty(true);
                break;

            case TO_BE_DELETED:
                this.dashboardsFolderNode.deleteChildFile(fileNameWithExt);
                break;
        }

        super.flush(); // change the statuses
    }
}