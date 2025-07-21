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
 * Confix match on strings (text1*text2). Case-sensitive or insensitive.
 */
public class ConfixSearchPatternStrategy extends AbstractSearchPatternStrategy {

    protected final String basePrefix;
    protected final String baseSuffix;

    /**
     * Create new {@link ConfixSearchPatternStrategy}.
     * @param ignoreCase Case-insensitive match flag.
     * @param basePrefix Base string to match with the beginning of other string.
     * @param baseSuffix Base string to match with the end of other string.
     */
    public ConfixSearchPatternStrategy(boolean ignoreCase, String basePrefix, String baseSuffix) {
        super(ignoreCase);
        this.basePrefix = basePrefix;
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
            return StringMatchUtility.startsWithEndsWithIgnoreCase(matchedString, basePrefix, baseSuffix);
        } else {
            return matchedString.startsWith(basePrefix) && matchedString.endsWith(baseSuffix);
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
