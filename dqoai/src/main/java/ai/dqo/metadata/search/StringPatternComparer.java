/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.search;

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
