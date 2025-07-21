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

/**
 * Helper class for comparing strings.
 */
public class StringCompareUtility {
    /**
     * Compares two strings like <code>s1.compareTo(s2)</code>, with permission of null parameters.
     * If one of the strings is null, it's considered as lesser than the non-null string (null is the minimum item in this ordering).
     * @param s1 First string.
     * @param s2 Second string.
     * @return A negative, zero, or positive value if <code>s1</code> is lesser than, equal to, or greater than <code>s2</code>.
     */
    public static int compareNullableString(String s1, String s2) {
        if (s1 == null && s2 != null) {
            return -1;
        }
        if (s1 != null && s2 == null) {
            return 1;
        }

        if (s1 == null) {
            return 0;
        }
        else {
            return s1.compareTo(s2);
        }
    }
}
