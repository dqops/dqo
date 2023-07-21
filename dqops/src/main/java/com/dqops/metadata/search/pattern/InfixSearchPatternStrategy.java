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

import com.dqops.utils.string.StringMatchUtility;

/**
 * Infix match on strings (*text*). Case-sensitive or insensitive.
 */
public class InfixSearchPatternStrategy extends AbstractSearchPatternStrategy {

    protected final String baseInfix;

    /**
     * Create new {@link InfixSearchPatternStrategy}.
     * @param ignoreCase Case-insensitive match flag.
     * @param baseInfix  Base string to match with the contents of other string.
     */
    public InfixSearchPatternStrategy(boolean ignoreCase, String baseInfix) {
        super(ignoreCase);
        this.baseInfix = baseInfix;
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
            return StringMatchUtility.containsIgnoreCase(matchedString, baseInfix);
        } else {
            return matchedString.contains(baseInfix);
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
