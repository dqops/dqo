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
