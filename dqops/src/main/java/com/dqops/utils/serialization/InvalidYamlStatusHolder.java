/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.serialization;

/**
 * Interface applied on specification objects that are deserialized from YAML and can hold a status and an error message
 * if the YAML file was invalid.
 */
public interface InvalidYamlStatusHolder {
    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     * @param yamlParsingError YAML parsing error.
     */
    void setYamlParsingError(String yamlParsingError);

    /**
     * Returns the YAML parsing error that was captured.
     * @return YAML parsing error.
     */
    String getYamlParsingError();

    /**
     * Checks if the object has YAML parsing issues. A file that has parsing issues is returned as an empty default object and this method returns true.
     * @return True when the YAML file from which this object was deserialized has issues.
     */
    default boolean hasYamlParsingIssues() {
        return this.getYamlParsingError() != null;
    }
}
