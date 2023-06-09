/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
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

package ai.dqo.metadata.search.pattern;

import ai.dqo.utils.string.StringMatchUtility;

/**
 * Suffix match on strings (*text). Case-sensitive or insensitive.
 */
public class SuffixSearchPatternStrategy extends AbstractSearchPatternStrategy {

    protected final String baseSuffix;

    /**
     * Create new {@link SuffixSearchPatternStrategy}.
     * @param ignoreCase Case-insensitive match flag.
     * @param baseSuffix Base string to match with the end of other string.
     */
    public SuffixSearchPatternStrategy(boolean ignoreCase, String baseSuffix) {
        super(ignoreCase);
        this.baseSuffix = baseSuffix;
    }

    /**
     * Verify if the provided string fits the pattern.
     *
     * @param matchedString String to match.
     * @return True if the string matches the pattern, false otherwise.
     */
    @Override
    public boolean match(String matchedString) {
        if (matchedString == null) {
            return false;
        }

        if (ignoreCase) {
            return StringMatchUtility.endsWithIgnoreCase(matchedString, baseSuffix);
        } else {
            return matchedString.endsWith(baseSuffix);
        }
    }

    /**
     * Checks if a given strategy involves matching against a wildcard '*'.
     *
     * @return True when it is a valid wildcard search pattern strategy, false otherwise.
     */
    @Override
    public boolean isWildcardSearchPattern() {
        return true;
    }
}
