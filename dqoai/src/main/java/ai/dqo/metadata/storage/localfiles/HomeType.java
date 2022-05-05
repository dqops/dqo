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
package ai.dqo.metadata.storage.localfiles;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identifies which home: user home (custom definitions and connections) or DQO_HOME (built-in definitions) is used
 */
public enum HomeType {
    /**
     * User home with custom rule and sensor definitions.
     */
    @JsonProperty("USER_HOME")
    USER_HOME,

    /**
     * DQO_HOME system home with built-in sensor and rule definitions.
     */
    @JsonProperty("DQO_HOME")
    DQO_HOME
}
