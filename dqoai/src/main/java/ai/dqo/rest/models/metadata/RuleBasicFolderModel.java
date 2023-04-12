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

import java.util.HashMap;
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
    private Map<String, RuleBasicFolderModel> folders;

    @JsonPropertyDescription("Whether the rule is a User Home rule.")
    private Boolean custom = null;

    public RuleBasicFolderModel() {}

    public RuleBasicFolderModel(Map<String, RuleBasicFolderModel> folders, Boolean custom) {
        this.folders = folders;
    }

    /**
     * Adds a child rule to the folder-level map.
     * @param path     The path of the child rule.
     * @param custom Whether the child rule is User Home or not.
     */
    public void addChild(String path, Boolean custom) {
        if (this.folders == null) {
            this.folders = new HashMap<>();
        }
        String[] parts = path.split("/", 2);
        String name = parts[0];
        String childFolder = parts.length > 1 ? parts[1] : null;
        RuleBasicFolderModel child = this.folders.get(name);
        if (child == null) {
            child = new RuleBasicFolderModel();
            this.folders.put(name, child);
        }
        if (childFolder != null) {
            child.addChild(childFolder, custom);
        }
        if (child.getFolders() == null && childFolder == null) {
            child.setCustom(custom);
        }
    }

    public Map<String, RuleBasicFolderModel> getFolders() {
        return folders;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setFolders(Map<String, RuleBasicFolderModel> folders) {
        this.folders = folders;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }
}
