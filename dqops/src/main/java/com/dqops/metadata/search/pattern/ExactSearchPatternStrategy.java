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

/**
 * Exact char-to-char match. Case-sensitive or insensitive.
 */
public class ExactSearchPatternStrategy extends AbstractSearchPatternStrategy {

    protected final String baseString;

    /**
     * Create new {@link ExactSearchPatternStrategy}.
     * @param ignoreCase Case-insensitive match flag.
     * @param baseString Base string to match.
     */
    public ExactSearchPatternStrategy(boolean ignoreCase, String baseString) {
        super(ignoreCase);
        this.baseString = baseString;
    }

    /**
     * Verify if the provided string fits the pattern.
     *
     * @param matchedString String to match.
     * @return True if the string matches the pattern, false otherwise.
     */
    @Override
    public boolean match(String matchedString) {
        if (this.baseString == null) {
            return false;
        }

        if (this.ignoreCase) {
            return baseString.equalsIgnoreCase(matchedString);
        } else {
            return baseString.equals(matchedString);
        }
    }

    /**
     * Checks if a given strategy involves matching against a wildcard '*'.
     *
     * @return True when it is a valid wildcard search pattern strategy, false otherwise.
     */
    @Override
    public boolean isWildcardSearchPattern() {
        return false;
    }
}
