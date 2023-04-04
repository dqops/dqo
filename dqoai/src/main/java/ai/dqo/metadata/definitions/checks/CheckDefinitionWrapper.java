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
package ai.dqo.metadata.definitions.checks;

import ai.dqo.metadata.basespecs.ElementWrapper;
import ai.dqo.metadata.basespecs.ObjectName;

/**
 * Custom check definition spec wrapper.
 */
public interface CheckDefinitionWrapper extends ElementWrapper<CheckDefinitionSpec>, ObjectName<String> {
    /**
     * Gets the custom check name.
     * @return Custom check name.
     */
    String getCheckName();

    /**
     * Sets a custom check definition name.
     * @param checkName Custom check definition name.
     */
    void setCheckName(String checkName);

    /**
     * Creates a deep clone of the object.
     * @return Deeply cloned object.
     */
    CheckDefinitionWrapper clone();
}
