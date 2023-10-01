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
package com.dqops.rest.models.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Check list folder model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckDefinitionFolderModel", description = "Check folder list model with a list of data quality checks in this folder or a list of nested folders.")
public class CheckDefinitionFolderModel {
    /**
     * A dictionary of nested folders with data quality checks. The keys are the folder names.
     */
    @JsonPropertyDescription("A dictionary of nested folders with data quality checks. The keys are the folder names.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, CheckDefinitionFolderModel> folders = new LinkedHashMap<>();

    /**
     * List of data quality checks defined in this folder.
     */
    @JsonPropertyDescription("List of data quality checks defined in this folder.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CheckDefinitionListModel> checks;

    /**
     * Creates a new instance of RuleBasicFolderModel with empty lists.
     */
    public CheckDefinitionFolderModel() {
    }

    /**
     * Adds a check to this folder based on the given path.
     * @param fullCheckName the path of the check
     * @param isCustom The check has a custom definition.
     * @param isBuiltIn The check is provided (built-in) with DQO.
     * @param canEdit The current user can edit the check.
     */
    public void addCheck(String fullCheckName, boolean isCustom, boolean isBuiltIn, boolean canEdit) {

        String[] checkFolders = fullCheckName.split("/");
        String checkName = checkFolders[checkFolders.length - 1];
        CheckDefinitionFolderModel folderModel = this;

        for (int i = 0; i < checkFolders.length - 1; i++) {
            String name = checkFolders[i];
            CheckDefinitionFolderModel nextCheckFolder = folderModel.folders.get(name);
            if (nextCheckFolder == null) {
                nextCheckFolder = new CheckDefinitionFolderModel();
                folderModel.folders.put(name, nextCheckFolder);
            }
            folderModel = nextCheckFolder;
        }

        Optional<CheckDefinitionListModel> existingCheck =
                folderModel.checks != null
                ? folderModel.checks.stream().filter(m -> Objects.equals(m.getCheckName(), checkName)).findFirst()
                : Optional.empty();

        if (!existingCheck.isPresent()) {
            CheckDefinitionListModel checkDefinitionListModel = new CheckDefinitionListModel();
            checkDefinitionListModel.setCheckName(checkName);
            checkDefinitionListModel.setFullCheckName(fullCheckName);
            checkDefinitionListModel.setCustom(isCustom);
            checkDefinitionListModel.setBuiltIn(isBuiltIn);
            checkDefinitionListModel.setCanEdit(canEdit);
            if (folderModel.checks == null) {
                folderModel.checks = new ArrayList<>();
            }
            folderModel.checks.add(checkDefinitionListModel);
        } else {
            if (isCustom){
                existingCheck.get().setCustom(true);
            }
            if (isBuiltIn){
                existingCheck.get().setBuiltIn(true);
            }
        }
    }

    /**
     * Collects all checks from all tree levels.
     * @return A list of all checks.
     */
    @JsonIgnore
    public List<CheckDefinitionListModel> getAllChecks() {
        List<CheckDefinitionListModel> allChecks = this.checks != null ? new ArrayList<>(this.checks) : new ArrayList<>();
        for (CheckDefinitionFolderModel folder : this.folders.values()) {
            allChecks.addAll(folder.getAllChecks());
        }

        return allChecks;
    }

    /**
     * Adds nested folders, used to add nested folders for custom checks.
     * @param fullFolderName Full folder name, for example: table/profiling/custom
     */
    public void addFolderIfMissing(String fullFolderName) {
        String[] folderElements = StringUtils.split(fullFolderName, '/');
        CheckDefinitionFolderModel childFolder = this.folders.get(folderElements[0]);
        if (childFolder == null) {
            childFolder = new CheckDefinitionFolderModel();
            if (folderElements.length == 1) {
                // top most folder with checks
                childFolder.checks = new ArrayList<>();
            }
            this.folders.put(folderElements[0], childFolder);
        }

        if (folderElements.length > 1) {
            String nestedFolderFullName = String.join("/", Arrays.copyOfRange(folderElements, 1, folderElements.length));
            childFolder.addFolderIfMissing(nestedFolderFullName);
        }
    }
}
