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

package com.dqops.execution.rules;

/**
 * The mode of updating outdated ML models used by a rule.
 */
public enum RuleModelUpdateMode {
    /**
     * Never update the model, even if it is outdated. Just return the results using the old model.
     * This is the default option.
     */
    never,

    /**
     * Update the model if it is outdated.
     */
    when_outdated,

    /**
     * Update the model, no matter if it is fresh, or outdated.
     */
    always
}
