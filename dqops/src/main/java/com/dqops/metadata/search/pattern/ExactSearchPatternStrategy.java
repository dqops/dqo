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
