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
 * Strategy for executing variants of pattern matching.
 */
public abstract class AbstractSearchPatternStrategy {
    protected final boolean ignoreCase;

    public AbstractSearchPatternStrategy(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * Verify if the provided string fits the pattern.
     * @param matchedString String to match.
     * @return True if the string matches the pattern, false otherwise.
     */
    public abstract boolean match(String matchedString);

    /**
     * Checks if a given strategy involves matching against a wildcard '*'.
     * @return True when it is a valid wildcard search pattern strategy, false otherwise.
     */
    public abstract boolean isWildcardSearchPattern();
}
