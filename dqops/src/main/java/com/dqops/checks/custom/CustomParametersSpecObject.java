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
