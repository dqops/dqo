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
 * Rule folder model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "RuleFolderModel", description = "Rule folder model with a list of rules defined in this folder and nested folders that contain additional rules.")
public class RuleFolderModel {
    /**
     * A dictionary of nested folders with rules, the keys are the folder names.
     */
    @JsonPropertyDescription("A dictionary of nested folders with rules, the keys are the folder names.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, RuleFolderModel> folders = new LinkedHashMap<>();

    /**
     * List of rules defined in this folder.
     */
    @JsonPropertyDescription("List of rules defined in this folder.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RuleListModel> rules = new ArrayList<>();

    /**
     * Creates a new instance of RuleFolderModel with empty lists.
     */
    public RuleFolderModel() {
    }

    /**
     * Adds a rule to this folder based on the given path.
     * @param fullRuleName the path of the rule
     * @param isCustom The rule has a custom definition.
     * @param isBuiltIn The rule is provided (built-in) with DQOps.
     * @param canEdit The current user can edit the rule.
     */
    public void addRule(String fullRuleName, boolean isCustom, boolean isBuiltIn, boolean canEdit, String yamlParsingError) {
        String[] ruleFolders = fullRuleName.split("/");
        String ruleName = ruleFolders[ruleFolders.length - 1];
        RuleFolderModel folderModel = this;

        for (int i = 0; i < ruleFolders.length - 1; i++) {
            String name = ruleFolders[i];
            RuleFolderModel nextRuleFolder = folderModel.folders.get(name);
            if (nextRuleFolder == null) {
                nextRuleFolder = new RuleFolderModel();
                folderModel.folders.put(name, nextRuleFolder);
            }
            folderModel = nextRuleFolder;
        }

        Optional<RuleListModel> existingRule = folderModel.rules.stream().filter(m -> Objects.equals(m.getRuleName(), ruleName)).findFirst();

        if (!existingRule.isPresent()) {
            RuleListModel ruleListModel = new RuleListModel();
            ruleListModel.setRuleName(ruleName);
            ruleListModel.setFullRuleName(fullRuleName);
            ruleListModel.setCustom(isCustom);
            ruleListModel.setBuiltIn(isBuiltIn);
            ruleListModel.setCanEdit(canEdit);
            ruleListModel.setYamlParsingError(yamlParsingError);
            folderModel.rules.add(ruleListModel);
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
    public List<RuleListModel> getAllRules()  {
        List<RuleListModel> allRules = new ArrayList<>(this.getRules());
        for (RuleFolderModel folder : this.folders.values()) {
            allRules.addAll(folder.getAllRules());
        }

        return allRules;
    }
}
