/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.search;

/**
 * String comparison functions that accept patterns like: *text, text*text2, text*, *text*.
 */
public class StringPatternComparer {
    /**
     * Compares a given text with a pattern that may contain one '*' wildcard at the beginning, middle or the end of the pattern.
     * Optionally may contain two '*'s, one at the beginning and one at the end of the pattern.
     * The text comparison is case-insensitive.
     * @param text Text to compare.
     * @param pattern Expected pattern.
     * @return True when the text matches the pattern, false when it did not match.
     */
    public static boolean matchSearchPattern(String text, String pattern) {
        if (text == null) {
            return pattern == null;
        }

        if (pattern == null) {
            return true;
        }

        int starOccurrences = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '*') {
                starOccurrences++;
            }
        }

        int indexOfStar = pattern.indexOf('*');
        if (indexOfStar < 0) {
            // wildcard '*' not used, names must match exactly
            return text.equalsIgnoreCase(pattern);
        }

        if (starOccurrences == 2) {
            if (text.length() < pattern.length() - 2) {
                return false;
            }
        }
        else {
            if (text.length() < pattern.length() - 1) {
                return false;
            }
        }

        if (starOccurrences == 2 && indexOfStar == 0 && pattern.lastIndexOf('*') == pattern.length() - 1) {
            if (pattern.length() == 2) {
                return true; // only "**"
            }
            return org.apache.commons.lang3.StringUtils.containsIgnoreCase(text, pattern.substring(1, pattern.length() - 1));
        }

        if (indexOfStar == 0) {
            // pattern like "*text"
            if (pattern.length() == 1) {
                return true; // only "*" that match everything
            }

            return text.regionMatches( true,text.length() - pattern.length() + 1, pattern, 1, pattern.length() - 1);
        }

        if (indexOfStar == pattern.length() - 1) {
            // ends with *, for example: "table*"
            return text.regionMatches(true, 0, pattern, 0, pattern.length() - 1);
        }

        int lengthOfEndPattern = pattern.length() - indexOfStar - 1;
        return text.regionMatches(true, 0, pattern, 0, indexOfStar) &&
                text.regionMatches(true, text.length() - lengthOfEndPattern, pattern, indexOfStar + 1, lengthOfEndPattern);
    }

    /**
     * Checks if a given pattern contains a wildcard '*' which makes it a search pattern.
     * @param pattern Possible search pattern to verify.
     * @return True when it is a valid wildcard search pattern, false otherwise.
     */
    public static boolean isSearchPattern(String pattern) {
        if (pattern == null) {
            return false;
        }

        return pattern.indexOf('*') >= 0;
    }
}
