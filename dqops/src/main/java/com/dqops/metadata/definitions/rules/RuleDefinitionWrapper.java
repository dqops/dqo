/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
