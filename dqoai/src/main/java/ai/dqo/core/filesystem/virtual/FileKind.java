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
package ai.dqo.core.filesystem.virtual;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * File kind type.
 */
public enum FileKind {
    @JsonProperty("unknown")
    UNKNOWN,

    @JsonProperty("foreign")
    FOREIGN,  // not our tracked file, a user has put his own files

    @JsonProperty("source")
    SOURCE,

//    @JsonProperty("schema")
//    SCHEMA,

    @JsonProperty("table")
    TABLE,

//    @JsonProperty("column")
//    COLUMN,

    @JsonProperty("template")
    TEMPLATE,

    @JsonProperty("rule")
    RULE
}
