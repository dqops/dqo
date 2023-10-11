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
package com.dqops.metadata.storage.localfiles.ruledefinitions;

import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.filesystem.virtual.FileTreeNode;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.definitions.rules.RuleDefinitionListImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.utils.serialization.YamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Custom rule definition collection that uses a local file system (the user's home folder) to read .py and .dqorule.yaml files with rule definitions.
 */
public class FileRuleDefinitionListImpl extends RuleDefinitionListImpl {
    @JsonIgnore
    private final FolderTreeNode rulesFolder;
    @JsonIgnore
    private final YamlSerializer yamlSerializer;

    /**
     * Creates a rule definition collection using a given rules folder.
     * @param rulesFolder Rules folder node.
     * @param yamlSerializer  Yaml serializer.
     */
    public FileRuleDefinitionListImpl(FolderTreeNode rulesFolder, YamlSerializer yamlSerializer) {
        this.rulesFolder = rulesFolder;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Rules folder.
     * @return Rules folder.
     */
    public FolderTreeNode getRulesFolder() {
        return rulesFolder;
    }

    /**
     * Loads all the elements from the backend source.
     */
    @Override
    protected void load() {
        // first: find the folders with .dqorule.yaml rule spec files (when a rule has a configuration file)
        for (FolderTreeNode ruleSpecFolderNode : this.rulesFolder.findNestedSubFoldersWithFiles(SpecFileNames.CUSTOM_RULE_SPEC_FILE_EXT_YAML, true)) {
            String ruleFolderName = ruleSpecFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName();  // getting the name after skipping the "rules" folder

            for (FileTreeNode specFileNode : ruleSpecFolderNode.getFiles()) {
                String specFileName = specFileNode.getFilePath().getFileName();
                if (!specFileName.endsWith(SpecFileNames.CUSTOM_RULE_SPEC_FILE_EXT_YAML)) {
                    // not a spec file, skipping
                    continue;
                }

                String decodedSpecFileName = FileNameSanitizer.decodeFileSystemName(specFileName);
                String ruleModuleName = decodedSpecFileName.substring(0, decodedSpecFileName.length() - SpecFileNames.CUSTOM_RULE_SPEC_FILE_EXT_YAML.length());
                String ruleName = (!ruleFolderName.equals("") ? (ruleFolderName + "/") : "") + ruleModuleName;

                if (this.getByObjectName(ruleName, false) != null) {
                    continue; // was already added
                }
				this.addWithoutFullLoad(new FileRuleDefinitionWrapperImpl(ruleSpecFolderNode, ruleName, ruleModuleName, this.yamlSerializer));
            }
        }

//        // second: find rules that are defined only as .py files, without the .dqorule.yaml specification file
//        for(FolderTreeNode rulePyFolderNode : this.rulesFolder.findNestedSubFoldersWithFiles(SpecFileNames.CUSTOM_RULE_PYTHON_MODULE_FILE_EXT_PY, true)) {
//            String ruleFolderName = rulePyFolderNode.getFolderPath().extractSubFolderAt(1).getFullObjectName();  // getting the name after skipping the "rules" folder
//
//            for(FileTreeNode pyFileNode : rulePyFolderNode.getFiles()) {
//                String pyFileName = pyFileNode.getFilePath().getFileName();
//                if (!pyFileName.endsWith(SpecFileNames.CUSTOM_RULE_PYTHON_MODULE_FILE_EXT_PY)) {
//                    // not a spec file, skipping
//                    continue;
//                }
//
//                String ruleModuleName = pyFileName.substring(0, pyFileName.length() - SpecFileNames.CUSTOM_RULE_PYTHON_MODULE_FILE_EXT_PY.length());
//                String ruleName = (!ruleFolderName.equals("") ? (ruleFolderName + "/") : "") + ruleModuleName;
//
//                if (this.getByObjectName(ruleName, false) != null) {
//                    continue; // was already added
//                }
//				this.addWithoutFullLoad(new FileRuleDefinitionWrapperImpl(rulePyFolderNode, ruleName, ruleModuleName, this.yamlSerializer));
//            }
//        }
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param ruleName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected RuleDefinitionWrapperImpl createNewElement(String ruleName) {
        String ruleModuleName = ruleName;
        String ruleFolderPath = "";
        int indexOfFileLocation = ruleName.lastIndexOf('/');
        if (indexOfFileLocation >= 0) {
            ruleModuleName = ruleName.substring(indexOfFileLocation + 1);
            ruleFolderPath = ruleName.substring(0, indexOfFileLocation);
        }
        FolderTreeNode ruleParentFolderNode = this.rulesFolder.getOrAddFolderPath(ruleFolderPath);
        FileRuleDefinitionWrapperImpl ruleModelWrapper =
                new FileRuleDefinitionWrapperImpl(ruleParentFolderNode, ruleName, ruleModuleName, this.yamlSerializer);
        ruleModelWrapper.setSpec(new RuleDefinitionSpec());
        return ruleModelWrapper;
    }
}
