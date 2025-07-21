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
