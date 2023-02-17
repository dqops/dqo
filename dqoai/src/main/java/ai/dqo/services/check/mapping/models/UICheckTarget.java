/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.services.check.mapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Enumeration of possible targets for UI check model request result.
 */
public enum UICheckTarget {
    @JsonProperty("table")
    @JsonPropertyDescription("The check is assigned to a table.")
    table,

    @JsonProperty("column")
    @JsonPropertyDescription("The check is assigned to a column.")
    column,

    @JsonProperty("target")
    @JsonPropertyDescription("Requesting agent knows the target, whatever that could be.")
    target,
}
