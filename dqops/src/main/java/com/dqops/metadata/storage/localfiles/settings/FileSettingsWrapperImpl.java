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
package com.dqops.metadata.storage.localfiles.settings;

import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.SettingsWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File based settings spec wrapper. Loads and writes the settings information to a yaml file in the user's home folder.
 */
public class FileSettingsWrapperImpl extends SettingsWrapperImpl {
	@JsonIgnore
	private final FolderTreeNode settingsFolderNode;
	@JsonIgnore
	private final YamlSerializer yamlSerializer;

	/**
	 * Creates a settings wrapper for a settings specification that uses yaml files for storage.
	 * @param settingsFolderNode Folder with yaml files for settings specifications.
	 * @param yamlSerializer Yaml serializer.
	 * @param readOnly Make the wrapper read-only.
	 */
	public FileSettingsWrapperImpl(FolderTreeNode settingsFolderNode, YamlSerializer yamlSerializer, boolean readOnly) {
		super(readOnly);
		this.settingsFolderNode = settingsFolderNode;
		this.yamlSerializer = yamlSerializer;
	}

	/**
	 * Loads the settings specification.
	 * @return Loaded settings specification.
	 */
	@Override
	public LocalSettingsSpec getSpec() {
		LocalSettingsSpec spec = super.getSpec();
		if (spec == null && this.getStatus() == InstanceStatus.LOAD_IN_PROGRESS) {
			FileTreeNode fileNode = this.settingsFolderNode.getChildFileByFileName(SpecFileNames.LOCAL_SETTINGS_SPEC_FILE_NAME_YAML);
			if (fileNode != null) {
				FileContent fileContent = fileNode.getContent();
				String textContent = fileContent.getTextContent();
				LocalSettingsSpec deserializedSpec = (LocalSettingsSpec) fileContent.getCachedObjectInstance();

				if (deserializedSpec == null) {
					LocalSettingsYaml deserialized = this.yamlSerializer.deserialize(textContent, LocalSettingsYaml.class, fileNode.getPhysicalAbsolutePath());
					deserializedSpec = deserialized.getSpec();
					if (deserialized.getKind() != SpecificationKind.settings) {
						throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
					}
					if (deserializedSpec != null) {
						LocalSettingsSpec cachedObjectInstance = deserializedSpec.deepClone();
						cachedObjectInstance.makeReadOnly(true);
						if (this.getHierarchyId() != null) {
							cachedObjectInstance.setHierarchyId(new HierarchyId(this.getHierarchyId(), "spec"));
						}
						fileContent.setCachedObjectInstance(cachedObjectInstance);
					}
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

		LocalSettingsYaml localSettingsYaml = new LocalSettingsYaml(this.getSpec());
		String specAsYaml = this.yamlSerializer.serialize(localSettingsYaml);
		FileContent newFileContent = new FileContent(specAsYaml);
		String fileNameWithExt = SpecFileNames.LOCAL_SETTINGS_SPEC_FILE_NAME_YAML;

		switch (this.getStatus()) {
			case ADDED:
				this.settingsFolderNode.addChildFile(fileNameWithExt, newFileContent);
				this.getSpec().clearDirty(true);
				break;

			case MODIFIED:
				FileTreeNode modifiedFileNode = this.settingsFolderNode.getChildFileByFileName(fileNameWithExt);
				if (modifiedFileNode != null) {
					modifiedFileNode.changeContent(newFileContent);
				}
				else {
					this.settingsFolderNode.addChildFile(fileNameWithExt, newFileContent);
				}
				this.getSpec().clearDirty(true);
				break;

			case TO_BE_DELETED:
				this.settingsFolderNode.deleteChildFile(fileNameWithExt);
				break;
		}

		super.flush(); // change the statuses
	}
}
