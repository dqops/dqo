/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.secrets;

import java.util.List;
import java.util.Map;

/**
 * Provider that returns secret values. It can retrieve values from environment variables or in the future, from Secret Managers, Vaults, etc.
 */
public interface SecretValueProvider {
    /**
     * The prefix of an entry that references a data dictionary.
     */
    String DICTIONARY_EXTRACT_TOKEN_PREFIX = "${dictionary://";

    /**
     * The suffix of an entry that references a data dictionary.
     */
    String DICTIONARY_EXTRACT_TOKEN_SUFFIX = "}";

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
