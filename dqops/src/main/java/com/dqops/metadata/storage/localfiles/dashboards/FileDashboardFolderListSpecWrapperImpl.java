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
package com.dqops.metadata.storage.localfiles.dashboards;

import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.dashboards.DashboardsFolderListSpec;
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
                    fileContent.setCachedObjectInstance(cachedObjectInstance);
                } else {
                    deserializedSpec = this.isReadOnly() ? deserializedSpec : deserializedSpec.deepClone();
                }
                this.setSpec(deserializedSpec);
                deserializedSpec.clearDirty(true);
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