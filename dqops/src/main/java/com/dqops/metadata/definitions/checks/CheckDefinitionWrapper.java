/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.checks;

import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;

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
