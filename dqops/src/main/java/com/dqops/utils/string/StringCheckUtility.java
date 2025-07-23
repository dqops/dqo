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
 * Helper class that detects strings inside a given string.
 */
public class StringCheckUtility {
    /**
     * Checks if the <code>testedString</code> contains any of the <code>nestedStrings</code> strings.
     * @param testedString Tested string.
     * @param nestedStrings Array of possible values.
     * @return True - one of the alternative values was found. False - no string found.
     */
    public static boolean containsAny(String testedString, String... nestedStrings) {
        if (testedString == null || nestedStrings == null || nestedStrings.length == 0) {
            return false;
        }

        for (String nested : nestedStrings) {
            if (testedString.contains(nested)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the <code>testedString</code> is equal to any of the <code>nestedStrings</code> strings.
     * @param testedString Tested string.
     * @param nestedStrings Array of possible values.
     * @return True - one of the alternative values was found. False - no string found.
     */
    public static boolean equalsAny(String testedString, String... nestedStrings) {
        if (testedString == null || nestedStrings == null || nestedStrings.length == 0) {
            return false;
        }

        for (String nested : nestedStrings) {
            if (testedString.equals(nested)) {
                return true;
            }
        }

        return false;
    }
    
}
