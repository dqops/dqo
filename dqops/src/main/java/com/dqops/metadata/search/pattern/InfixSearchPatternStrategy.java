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
