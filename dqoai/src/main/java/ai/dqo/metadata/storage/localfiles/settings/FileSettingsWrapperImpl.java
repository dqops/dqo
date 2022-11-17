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
package ai.dqo.metadata.storage.localfiles.settings;

import ai.dqo.core.filesystem.localfiles.LocalFileSystemException;
import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.core.filesystem.virtual.FileTreeNode;
import ai.dqo.core.filesystem.virtual.FolderTreeNode;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.sources.SettingsSpec;
import ai.dqo.metadata.sources.SettingsWrapperImpl;
import ai.dqo.metadata.storage.localfiles.SpecFileNames;
import ai.dqo.metadata.storage.localfiles.SpecificationKind;
import ai.dqo.utils.serialization.YamlSerializer;
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
	 */
	public FileSettingsWrapperImpl(FolderTreeNode settingsFolderNode, YamlSerializer yamlSerializer) {
		this.settingsFolderNode = settingsFolderNode;
		this.yamlSerializer = yamlSerializer;
	}

	/**
	 * Loads the table spec with the table details.
	 * @return Loaded table specification.
	 */
	@Override
	public SettingsSpec getSpec() {
		SettingsSpec spec = super.getSpec();
		if (spec == null) {
			FileTreeNode fileNode = this.settingsFolderNode.getChildFileByFileName(SpecFileNames.SETTINGS_SPEC_FILE_NAME_YAML);
			if (fileNode != null) {
				FileContent fileContent = fileNode.getContent();
				String textContent = fileContent.getTextContent();
				SettingsYaml deserialized = this.yamlSerializer.deserialize(textContent, SettingsYaml.class, fileNode.getPhysicalAbsolutePath());
				SettingsSpec deserializedSpec = deserialized.getSpec();
				if (deserialized.getKind() != SpecificationKind.SETTINGS) {
					throw new LocalFileSystemException("Invalid kind in file " + fileNode.getFilePath().toString());
				}
				this.setSpec(deserializedSpec);
				deserializedSpec.clearDirty(true);
				this.clearDirty(false);
				return deserializedSpec;
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
		if (this.getStatus() == InstanceStatus.DELETED) {
			return; // do nothing
		}

		if (this.getStatus() == InstanceStatus.UNCHANGED && super.getSpec() != null && super.getSpec().isDirty() ) {
			super.getSpec().clearDirty(true);
			this.setStatus(InstanceStatus.MODIFIED);
		}

		SettingsYaml settingsYaml = new SettingsYaml(this.getSpec());
		String specAsYaml = this.yamlSerializer.serialize(settingsYaml);
		FileContent newFileContent = new FileContent(specAsYaml);
		String fileNameWithExt = SpecFileNames.SETTINGS_SPEC_FILE_NAME_YAML;

		switch (this.getStatus()) {
			case ADDED:
				this.settingsFolderNode.addChildFile(fileNameWithExt, newFileContent);
				this.getSpec().clearDirty(true);
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
