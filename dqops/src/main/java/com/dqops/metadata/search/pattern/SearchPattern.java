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

package com.dqops.metadata.search.pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public static SearchPattern create(boolean ignoreCase, String patternString) {
        if (patternString == null) {
            throw new IllegalArgumentException("Search pattern is null.");
        }

        AbstractSearchPatternStrategy searchPatternStrategy = determineSearchPatternStrategy(ignoreCase, patternString);
        return new SearchPattern(patternString, searchPatternStrategy);
    }

    public boolean match(String otherString) {
        if (this.searchPatternStrategy == null) {
            return false;
        }
        return this.searchPatternStrategy.match(otherString);
    }

    @JsonIgnore
    public boolean isWildcardSearchPattern() {
        if (this.searchPatternStrategy == null) {
            return false;
        }
        return this.searchPatternStrategy.isWildcardSearchPattern();
    }
}
