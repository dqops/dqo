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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.*;

/**
 * Rule basic folder model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "RuleBasicFolderModel", description = "Rule basic folder model")
public class RuleBasicFolderModel {
    @JsonPropertyDescription("A map of folder-level children rules.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, RuleBasicFolderModel> folders = new LinkedHashMap<>();

    @JsonPropertyDescription("Rule basic model list")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RuleBasicModel> rules = new ArrayList<>();

    /**
     * Creates a new instance of RuleBasicFolderModel with empty lists.
     */
    public RuleBasicFolderModel() {
    }

    /**
     * Adds a rule to this folder based on the given path.
     * @param fullRuleName the path of the rule
     * @param isCustom The rule has a custom definition.
     * @param isBuiltIn The rule is provided (built-in) with DQO.
     * @param canEdit The current user can edit the rule.
     */
    public void addRule(String fullRuleName, boolean isCustom, boolean isBuiltIn, boolean canEdit) {
        String[] ruleFolders = fullRuleName.split("/");
        String ruleName = ruleFolders[ruleFolders.length - 1];
        RuleBasicFolderModel folderModel = this;

        for (int i = 0; i < ruleFolders.length - 1; i++) {
            String name = ruleFolders[i];
            RuleBasicFolderModel nextRuleFolder = folderModel.folders.get(name);
            if (nextRuleFolder == null) {
                nextRuleFolder = new RuleBasicFolderModel();
                folderModel.folders.put(name, nextRuleFolder);
            }
            folderModel = nextRuleFolder;
        }

        Optional<RuleBasicModel> existingRule = folderModel.rules.stream().filter(m -> Objects.equals(m.getRuleName(), ruleName)).findFirst();

        if (!existingRule.isPresent()) {
            RuleBasicModel ruleBasicModel = new RuleBasicModel();
            ruleBasicModel.setRuleName(ruleName);
            ruleBasicModel.setFullRuleName(fullRuleName);
            ruleBasicModel.setCustom(isCustom);
            ruleBasicModel.setBuiltIn(isBuiltIn);
            ruleBasicModel.setCanEdit(canEdit);
            folderModel.rules.add(ruleBasicModel);
        } else {
            if (isCustom){
                existingRule.get().setCustom(true);
            }
            if (isBuiltIn){
                existingRule.get().setBuiltIn(true);
            }
        }
    }

    /**
     * Collects all rules from all tree levels.
     * @return A list of all rules.
     */
    public List<RuleBasicModel> getAllRules()  {
        List<RuleBasicModel> allRules = new ArrayList<>(this.getRules());
        for (RuleBasicFolderModel folder : this.folders.values()) {
            allRules.addAll(folder.getAllRules());
        }

        return allRules;
    }
}
