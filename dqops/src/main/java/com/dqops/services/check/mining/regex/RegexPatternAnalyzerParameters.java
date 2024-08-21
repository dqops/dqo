/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
