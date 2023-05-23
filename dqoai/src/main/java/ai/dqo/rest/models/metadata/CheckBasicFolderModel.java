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
package ai.dqo.rest.models.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.*;

/**
 * Check basic folder model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckBasicFolderModel", description = "Check basic folder model")
public class CheckBasicFolderModel {
    @JsonPropertyDescription("A map of folder-level children checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, CheckBasicFolderModel> folders = new LinkedHashMap<>();

    @JsonPropertyDescription("Check basic model list of checks defined at this level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CheckBasicModel> checks = new ArrayList<>();

    /**
     * Creates a new instance of RuleBasicFolderModel with empty lists.
     */
    public CheckBasicFolderModel() {
    }

    /**
     * Adds a check to this folder based on the given path.
     * @param fullCheckName the path of the check
     * @param isCustom The check has a custom definition.
     * @param isBuiltIn The check is provided (built-in) with DQO.
     */
    public void addCheck(String fullCheckName, boolean isCustom, boolean isBuiltIn) {

        String[] checkFolders = fullCheckName.split("/");
        String checkName = checkFolders[checkFolders.length - 1];
        CheckBasicFolderModel folderModel = this;

        for (int i = 0; i < checkFolders.length - 1; i++) {
            String name = checkFolders[i];
            CheckBasicFolderModel nextCheckFolder = folderModel.folders.get(name);
            if (nextCheckFolder == null) {
                nextCheckFolder = new CheckBasicFolderModel();
                folderModel.folders.put(name, nextCheckFolder);
            }
            folderModel = nextCheckFolder;
        }

        Optional<CheckBasicModel> existingCheck = folderModel.checks.stream().filter(m -> Objects.equals(m.getCheckName(), checkName)).findFirst();

        if (!existingCheck.isPresent()) {
            CheckBasicModel checkBasicModel = new CheckBasicModel();
            checkBasicModel.setCheckName(checkName);
            checkBasicModel.setFullCheckName(fullCheckName);
            checkBasicModel.setCustom(isCustom);
            checkBasicModel.setBuiltIn(isBuiltIn);
            folderModel.checks.add(checkBasicModel);
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
    public List<CheckBasicModel> getAllChecks() {
        List<CheckBasicModel> allChecks = new ArrayList<>(this.getChecks());
        for (CheckBasicFolderModel folder : this.folders.values()) {
            allChecks.addAll(folder.getAllChecks());
        }

        return allChecks;
    }
}
