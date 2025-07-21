/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.custom;

import java.util.Map;

/**
 * Interface implemented by a custom sensor parameters and a custom rule parameters objects to access the parameter values.
 */
public interface CustomParametersSpecObject {
    /**
     * Returns a dictionary of invalid properties that were present in the YAML specification file, but were not declared in the class.
     * Returns null when all properties were valid.
     *
     * @return True when undefined properties were present in the YAML file that failed the deserialization. Null when all properties were valid (declared).
     */
    Map<String, Object> getAdditionalProperties();

    /**
     * Retrieves a parameter value.
     * @param parameterName Parameter name.
     * @return Parameter value or null when the parameter was unknown.
     */
    Object getParameter(String parameterName);

    /**
     * Sets a parameter value.
     * @param parameterName Parameter name.
     * @param value New parameter value.
     */
    void setParameter(String parameterName, Object value);
}
