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
package com.dqops.metadata.definitions.rules;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;

/**
 * Custom rule definition spec wrapper.
 */
public interface RuleDefinitionWrapper extends ElementWrapper<RuleDefinitionSpec>, ObjectName<String> {
    /**
     * Gets the custom rule definition name.
     * @return Custom rule definition name.
     */
    String getRuleName();

    /**
     * Sets a custom rule definition name.
     * @param ruleName Custom rule definition name.
     */
    void setRuleName(String ruleName);

    /**
     * Get the content of a rule python module file.
     * @return Content of the python module file (.py file with the rule code).
     */
    FileContent getRulePythonModuleContent();

    /**
     * Stores the content of the python module file (.py file).
     * @param rulePythonModuleContent Python rule implementation file content.
     */
    void setRulePythonModuleContent(FileContent rulePythonModuleContent);

    /**
     * Creates a deep clone of the object.
     * @return Deeply cloned object.
     */
    RuleDefinitionWrapper clone();
}
