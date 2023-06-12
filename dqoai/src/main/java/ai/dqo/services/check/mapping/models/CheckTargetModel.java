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
package ai.dqo.services.check.mapping.models;

import ai.dqo.checks.CheckTarget;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Enumeration of possible targets for UI check model request result.
 */
public enum CheckTargetModel {
    @JsonProperty("table")
    @JsonPropertyDescription("The check is assigned to a table.")
    table,

    @JsonProperty("column")
    @JsonPropertyDescription("The check is assigned to a column.")
    column;

    /**
     * Creates a UI check target from a backend check target.
     * @param checkTarget Check target.
     * @return UI Check target.
     */
    public static CheckTargetModel fromCheckTarget(CheckTarget checkTarget) {
        switch (checkTarget) {
            case table:
                return table;
            case column:
                return column;
            default:
                throw new IllegalArgumentException("Unknown check target: " + checkTarget);
        }
    }
}
