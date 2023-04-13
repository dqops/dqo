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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, RuleBasicFolderModel> folders;

    @JsonPropertyDescription("Whether the rule is a User Home rule.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RuleBasicModel> rules;

    /**
     * Creates a new instance of RuleBasicFolderModel with empty lists.
     */
    public RuleBasicFolderModel() {
        rules = new ArrayList<>();
        folders = new HashMap<>();
    }

    /**
     * Adds a rule to this folder based on the given path.
     * @param fullRuleName the path of the rule
     */
    public void addRule(String fullRuleName, boolean isCustom) {

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

        boolean ruleExists = false;
        if (folderModel.rules != null) {
            for (RuleBasicModel rule : folderModel.rules) {
                if (rule.getRuleName().equals(ruleName)) {
                    rule.setCustom(isCustom);
                    ruleExists = true;
                    break;
                }
            }
        } else {
            folderModel.rules = new ArrayList<>();
        }
        if (!ruleExists) {
            RuleBasicModel ruleBasicModel = new RuleBasicModel();
            ruleBasicModel.setRuleName(ruleName);
            ruleBasicModel.setFullRuleName(fullRuleName);
            ruleBasicModel.setCustom(isCustom);
            folderModel.rules.add(ruleBasicModel);
        }
    }
}
