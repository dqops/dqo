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
package ai.dqo.metadata.definitions.rules;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Rule historic data mode. A rule may evaluate only the current sensor readout (current_value) or use historic values.
 */
public enum RuleTimeWindowMode {
    /**
     * Analyze only the current sensor readout. This is the default settings.
     */
    @JsonProperty("current_value")
    current_value,

    /**
     * Use the previous sensor readouts (readouts from previous time periods) in the rule evaluation, for example by calculating an average.
     */
    @JsonProperty("previous_readouts")
    previous_readouts
}
