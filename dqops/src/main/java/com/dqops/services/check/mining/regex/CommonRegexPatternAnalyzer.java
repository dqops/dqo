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

import joptsimple.internal.Strings;

import java.util.List;

/**
 * Support class that analyzes a list of tests and tries to find regular expressions.
 */
public class CommonRegexPatternAnalyzer {
    /**
     * Analyzes texts in a list of texts. Returns a regular expression that analyzes all values.
     * @param texts List of texts to analyze.
     * @param parameters Analyzer configuration parameter.
     * @return Regular expression when a common expression was found, or null when it was not possible to find a common expression.
     */
    public static String findCommonRegex(List<String> texts, RegexPatternAnalyzerParameters parameters) {
        RegexPatternBranch rootBranch = new RegexPatternBranch();

        for (String text : texts) {
            RegexPatternToken regexPatternToken = RegexPatternParser.parseText(text, parameters.isMixDigitsAndLetters());
            if (regexPatternToken == null) {
                return null; // not parsable, we will not propose a common regular expression
            }

            rootBranch.addTokenTrie(regexPatternToken);
        }

        rootBranch.mergeLetterAndDigitBranches();

        // TODO: we can try to detect some optional tokens, but that would be compute intensive for longer chains of similar chains

        int terminalExpressionsCount = rootBranch.countTerminalExpressions();
        if (terminalExpressionsCount > parameters.getMaximumAlternativeExpressions()) {
            return null;
        }

        int regularExpressionDepth = rootBranch.measureExpressionDepth();
        if (regularExpressionDepth < parameters.getMinimumTokensInRegex()) {
            return null;
        }

        List<String> regularExpressions = rootBranch.collectAlternativeRegularExpressions(parameters);
        String combinedRegex = Strings.join(regularExpressions, "|");

        return combinedRegex;
    }
}
