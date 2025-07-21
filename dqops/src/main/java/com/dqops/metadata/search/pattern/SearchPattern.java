/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.search.pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

public class SearchPattern {
    public static final String WILDCARD_TOKEN = "*";
    private final String rawPattern;
    private final AbstractSearchPatternStrategy searchPatternStrategy;

    protected SearchPattern(String rawPattern, AbstractSearchPatternStrategy searchPatternStrategy) {
        this.rawPattern = rawPattern;
        this.searchPatternStrategy = searchPatternStrategy;
    }

    protected static Map.Entry<Integer, Integer> sanitizeWildcardSearchIndices(int firstWildcardIndex, int lastWildcardIndex, int patternStringLength) {
        if (firstWildcardIndex == -1) {
            return null;
        }

        if (firstWildcardIndex == lastWildcardIndex || (firstWildcardIndex == 0 && lastWildcardIndex == patternStringLength - 1)) {
            return new AbstractMap.SimpleEntry<>(firstWildcardIndex, lastWildcardIndex);
        }

        if (lastWildcardIndex == patternStringLength - 1) {
            return new AbstractMap.SimpleEntry<>(lastWildcardIndex, lastWildcardIndex);
        }

        // If pattern is "aaa*bb*cccc", confix search "aaa)*(bb*cccc" with the second asterisk treated as a regular char.
        return new AbstractMap.SimpleEntry<>(firstWildcardIndex, firstWildcardIndex);
    }

    protected static AbstractSearchPatternStrategy determineSearchPatternStrategy(boolean ignoreCase, String patternString) {
        int firstWildcardIndex = patternString.indexOf(WILDCARD_TOKEN);
        int lastWildcardIndex = patternString.lastIndexOf(WILDCARD_TOKEN);

        Map.Entry<Integer, Integer> wildcardIndices = sanitizeWildcardSearchIndices(firstWildcardIndex, lastWildcardIndex, patternString.length());
        if (wildcardIndices == null) {
            // "text"
            return new ExactSearchPatternStrategy(ignoreCase, patternString);
        }

        firstWildcardIndex = wildcardIndices.getKey();
        lastWildcardIndex = wildcardIndices.getValue();

        if (firstWildcardIndex == lastWildcardIndex) {
            if (firstWildcardIndex == 0) {
                // "*text"
                return new SuffixSearchPatternStrategy(ignoreCase, patternString.substring(1));
            }
            if (firstWildcardIndex == patternString.length()) {
                // "text*"
                return new PrefixSearchPatternStrategy(ignoreCase, patternString.substring(0, patternString.length() - 1));
            }
            // "text1*text2"
            return new ConfixSearchPatternStrategy(
                    ignoreCase,
                    patternString.substring(0, firstWildcardIndex),
                    patternString.substring(firstWildcardIndex + 1)
            );
        } else {
            // *text*
            return new InfixSearchPatternStrategy(ignoreCase, patternString.substring(1, patternString.length() - 1));
        }
    }

    /**
     * Creates a search pattern for a given configuration.
     * @param ignoreCase Ignore case.
     * @param patternString Pattern text.
     * @return Search pattern.
     */
    public static SearchPattern create(boolean ignoreCase, String patternString) {
        if (patternString == null) {
            throw new IllegalArgumentException("Search pattern is null.");
        }

        AbstractSearchPatternStrategy searchPatternStrategy = determineSearchPatternStrategy(ignoreCase, patternString);
        return new SearchPattern(patternString, searchPatternStrategy);
    }

    /**
     * Creates an array of search patterns for values that are comma separated.
     * @param ignoreCase Ignore case.
     * @param patternString Pattern text with comma separated values.
     * @return Search pattern.
     */
    public static SearchPattern[] createForCommaSeparatedPatterns(boolean ignoreCase, String patternString) {
        if (patternString == null) {
            throw new IllegalArgumentException("Search pattern is null.");
        }

        String[] textPatterns = StringUtils.split(patternString, ',');
        if (textPatterns.length == 0) {
            return null;
        }

        SearchPattern[] searchPatterns = new SearchPattern[textPatterns.length];
        for (int i = 0; i < searchPatterns.length; i++) {
            String pattern = textPatterns[i];
            AbstractSearchPatternStrategy searchPatternStrategy = determineSearchPatternStrategy(ignoreCase, pattern);
            searchPatterns[i] = new SearchPattern(pattern, searchPatternStrategy);
        }

        return searchPatterns;
    }

    /**
     * Tests a text if it matches the pattern.
     * @param otherString Text to verify.
     * @return True when the value matches a pattern.
     */
    public boolean match(String otherString) {
        if (this.searchPatternStrategy == null) {
            return false;
        }
        return this.searchPatternStrategy.match(otherString);
    }

    /**
     * Tests the text if it matches any pattern from the array of check patterns.
     * @param patterns Array of check patterns.
     * @param text Text to verify.
     * @return True when it matches any pattern.
     */
    public static boolean matchAny(SearchPattern[] patterns, String text) {
        for (SearchPattern pattern : patterns) {
            if (pattern.match(text)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the pattern contains a wildcard character '*'.
     */
    @JsonIgnore
    public boolean isWildcardSearchPattern() {
        if (this.searchPatternStrategy == null) {
            return false;
        }
        return this.searchPatternStrategy.isWildcardSearchPattern();
    }
}
