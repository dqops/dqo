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
