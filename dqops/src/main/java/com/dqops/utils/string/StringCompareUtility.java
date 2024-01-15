/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
