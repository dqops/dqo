/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.string;

import java.util.HashMap;

/**
 * Helper class used to "intern" (deduplicate) string object instances that are the same.
 * Maintains a dictionary of strings that were already seen. Returns an earlier instance of the same string.
 */
public class UniqueStringSet {
    private final HashMap<String, String> uniqueStrings = new HashMap<>();  // we can use a regular has map here (not a linked hash map)

    /**
     * Deduplicates a string. Returns the same string if it is the first instance of a string object.
     * Returns an older string instance if an equal string was already seen.
     * @param value String value to deduplicate.
     * @return Deduplicated string value.
     */
    public String deduplicate(String value) {
        if (value == null) {
            return null;
        }

        String oldUniqueValue = uniqueStrings.putIfAbsent(value, value);
        if (oldUniqueValue == null) {
            return value;
        }
        return oldUniqueValue;
    }
}
