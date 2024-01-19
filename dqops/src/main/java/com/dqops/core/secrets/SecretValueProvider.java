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
package com.dqops.core.secrets;

import java.util.List;
import java.util.Map;

/**
 * Provider that returns secret values. It can retrieve values from environment variables or in the future, from Secret Managers, Vaults, etc.
 */
public interface SecretValueProvider {
    /**
     * Expands a value that references possible secret values. For example ${ENVIRONMENT_VARIABLE_NAME}
     * @param value Value to expand.
     * @param lookupContext Context used to look up credentials in the user home.
     * @return Value (when no expansions possible) or an expanded value.
     */
    String expandValue(String value, SecretValueLookupContext lookupContext);

    /**
     * Expands properties in a given hash map. Returns a cloned instance with all property values expanded.
     * @param properties Properties to expand.
     * @param lookupContext Context to look up credentials.
     * @return Expanded properties.
     */
    Map<String, String> expandProperties(Map<String, String> properties, SecretValueLookupContext lookupContext);

    /**
     * Expands entries in a given list. Returns a cloned instance with all entry values expanded.
     *
     * @param list Entries list to expand.
     * @param lookupContext Lookup context with the user home used to look up credentials.
     * @return Expanded entries.
     */
    List<String> expandList(List<String> list, SecretValueLookupContext lookupContext);
}
