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
 * Prefix match on strings (text*). Case-sensitive or insensitive.
 */
public class PrefixSearchPatternStrategy extends AbstractSearchPatternStrategy {

    protected final String basePrefix;

    /**
     * Create new {@link PrefixSearchPatternStrategy}.
     * @param ignoreCase Case-insensitive match flag.
     * @param basePrefix Base string to match with the beginning of other string.
     */
    public PrefixSearchPatternStrategy(boolean ignoreCase, String basePrefix) {
        super(ignoreCase);
        this.basePrefix = basePrefix;
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
            return StringMatchUtility.startsWithIgnoreCase(matchedString, basePrefix);
        } else {
            return matchedString.startsWith(basePrefix);
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
