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
