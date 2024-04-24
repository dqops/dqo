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
package com.dqops.metadata.storage.localfiles.tabledefaultpatterns;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternWrapperImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * File based configuration of table-level checks pattern wrapper. Loads and writes the default check patterns to a yaml file.
 */
public class FileTableDefaultChecksPatternWrapperImpl extends TableDefaultChecksPatternWrapperImpl {
    @JsonIgnore
    private final FolderTreeNode defaultsFolderNode;
    @JsonIgnore
    private final String patternFileNameBaseName;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a default checks pattern spec wrapper that is file based.
     * @param defaultsFolderNode Default check patterns folder with yaml files.
     * @param patternName Full pattern name as used to store in the database.
     * @param patternFileNameBaseName Pattern file module name. This is the default checks specification file name inside the defaults folder without the .dqotablepattern.yaml extension.
     * @param yamlSerializer Yaml serializer.
     * @param readOnly Make the wrapper read-only.
     */
    public FileTableDefaultChecksPatternWrapperImpl(FolderTreeNode defaultsFolderNode,
                                                    String patternName,
                                                    String patternFileNameBaseName,
                                                    YamlSerializer yamlSerializer,
                                                    boolean readOnly) {
        super(patternName, readOnly);
        this.defaultsFolderNode = defaultsFolderNode;
        this.patternFileNameBaseName = patternFileNameBaseName;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Returns the folder that contains the default check configurations files.
     * @return Defaults folder.
     */
    @JsonIgnore
    public FolderTreeNode getDefaultsFolderNode() {
        return defaultsFolderNode;
    }

    /**
     * Returns the default checks pattern name inside the folder. This is the bare file name without the .dqotablepattern.yaml extensions.
     * @return Bare pattern name inside the folder.
     */
    public String getPatternFileNameBaseName() {
        return patternFileNameBaseName;
    }

    /**
     * Loads the default checks pattern from a .dqotablepattern.yaml file.
     * @return Loaded default checks pattern specification.
     */
    @Override
    public TableDefaultChecksPatternSpec getSpec() {
        TableDefaultChecksPatternSpec spec = super.getSpec();
        if (spec == null) {
            String specFileName = FileNameSanitizer.encodeForFileSystem(this.patternFileNameBaseName) + SpecFileNames.TABLE_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML;
            FileTreeNode fileNode = this.defaultsFolderNode.getChildFileByFileName(specFileName);
            if (fileNode != null) {
                FileContent fileContent = fileNode.getContent();
                String textContent = fileContent.getTextContent();
                TableDefaultChecksPatternSpec deserializedSpec = (TableDefaultChecksPatternSpec) fileContent.getCachedObjectInstance();

                if (deserializedSpec == null) {
                    TableDefaultChecksPatternYaml deserialized = this.yamlSerializer.deserialize(textContent, TableDefaultChecksPatternYaml.class, fileNode.getPhysicalAbsolutePath());
                    deserializedSpec = deserialized.getSpec();
                    if (deserializedSpec == null) {
                        deserializedSpec = new TableDefaultChecksPatternSpec();
                    }

                    if (!Objects.equals(deserialized.getApiVersion(), ApiVersion.CURRENT_API_VERSION)) {
                        throw new LocalFileSystemException("apiVersion not supported in file " + fileNode.getFilePath().toString());
                    }
                    if (deserialized.getKind() != SpecificationKind.default_table_checks) {
                        throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
                    }

                    AbstractSpec cachedObjectInstance = deserializedSpec.deepClone();
                    cachedObjectInstance.makeReadOnly(true);
                    if (this.getHierarchyId() != null) {
                        cachedObjectInstance.setHierarchyId(new HierarchyId(this.getHierarchyId(), "spec"));
                    }
                    fileContent.setCachedObjectInstance(cachedObjectInstance);
                } else {
                    deserializedSpec = this.isReadOnly() ? deserializedSpec : (TableDefaultChecksPatternSpec) deserializedSpec.deepClone();
                }
				this.setSpec(deserializedSpec);
				this.clearDirty(false);
                return deserializedSpec;
            }
            else {
                TableDefaultChecksPatternSpec newEmptySpec = new TableDefaultChecksPatternSpec();
				this.setSpec(newEmptySpec);
				this.clearDirty(true);
                return newEmptySpec;
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
            return; // nothing to do, the instance is null (not present)
        }

        if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty()) {
			this.setStatus(InstanceStatus.MODIFIED);
        }

        TableDefaultChecksPatternYaml defaultChecksPatternYaml = new TableDefaultChecksPatternYaml(this.getSpec());
        String specAsYaml = this.yamlSerializer.serialize(defaultChecksPatternYaml);
        FileContent newSpecFileContent = new FileContent(specAsYaml);
        String specFileNameWithExt = FileNameSanitizer.encodeForFileSystem(this.patternFileNameBaseName) + SpecFileNames.TABLE_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML;

        switch (this.getStatus()) {
            case ADDED:
				this.defaultsFolderNode.addChildFile(specFileNameWithExt, newSpecFileContent);
				this.getSpec().clearDirty(true);
                break;

            case MODIFIED:
                FileTreeNode modifiedFileNode = this.defaultsFolderNode.getChildFileByFileName(specFileNameWithExt);
                modifiedFileNode.changeContent(newSpecFileContent);
				this.getSpec().clearDirty(true);
                break;

            case TO_BE_DELETED:
				this.defaultsFolderNode.deleteChildFile(specFileNameWithExt);
                break;
        }

        super.flush(); // change the statuses
    }
}
