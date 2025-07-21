/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining.regex;

import lombok.Data;

/**
 * Parameter object that configures how the {@link  CommonRegexPatternAnalyzer} finds patterns.
 */
@Data
public class RegexPatternAnalyzerParameters {
    /**
     * Treats digits as words, allowing to mix digits and letters.
     */
    private boolean mixDigitsAndLetters = true;

    /**
     * The maximum number of alternative regular expressions separated by a '|' (OR) operator that will be generated.
     * If the samples are too different from each other and this limit is exceeded, does not generate a regular expression.
     */
    private int maximumAlternativeExpressions = 5;

    /**
     * The minimum number of alternative tokens in a regular expression.
     */
    private int minimumTokensInRegex = 3;

    /**
     * Try to find an exact maximum count of characters. If this is false, and a character was detected as multiplied, it will have an open-ended '*' repeat count.
     */
    private boolean findMaxCharactersCount = false;
}
