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

import java.util.*;

/**
 * A branching node that contains different nested regular expressions for each type of the next tokens.
 */
public class RegexPatternBranch {
    private Map<RegexPatternTokenType, RegexPatternToken> nextTokens = new LinkedHashMap<>();

    /**
     * Returns a dictionary of next tokens by the next token type.
     * @return Map of next tokens.
     */
    public Map<RegexPatternTokenType, RegexPatternToken> getNextTokens() {
        return nextTokens;
    }

    /**
     * Imports a regular expression into the branch, merging it with the current trie.
     * @param regexPatternToken Pattern to import.
     */
    public void addTokenTrie(RegexPatternToken regexPatternToken) {
        RegexPatternToken existingToken = this.nextTokens.get(regexPatternToken.getTokenType());
        if (existingToken == null) {
            this.nextTokens.put(regexPatternToken.getTokenType(), regexPatternToken);
        } else {
            existingToken.importTokenTrie(regexPatternToken);
        }
    }

    /**
     * Imports all occurrences from another branch.
     * @param otherBranch Another branch.
     */
    public void importPatternBranch(RegexPatternBranch otherBranch) {
        for (RegexPatternToken otherToken : otherBranch.nextTokens.values()) {
            addTokenTrie(otherToken);
        }
    }

    /**
     * Tries to merge lowercase, upper case, mixed case and digit nodes into a single node.
     */
    public void mergeLetterAndDigitBranches() {
        int countOfLetterChildren =
                (this.nextTokens.containsKey(RegexPatternTokenType.LOWER_CASE_LETTERS) ? 1 : 0) +
                (this.nextTokens.containsKey(RegexPatternTokenType.UPPER_CASE_LETTERS) ? 1 : 0) +
                (this.nextTokens.containsKey(RegexPatternTokenType.MIXED_CASE_LETTERS) ? 1 : 0);

        boolean containsMixedDigitsLettersAndMustBeMerged =
                this.nextTokens.containsKey(RegexPatternTokenType.LETTERS_OR_DIGITS) && // we already detected a token with a mix of letters and digits, for example: "a1"
                (countOfLetterChildren > 0 || this.nextTokens.containsKey(RegexPatternTokenType.DIGITS)); // and there are other tokens to be merged into it (letters only, digits only) to be merged

        if (countOfLetterChildren > 1 || containsMixedDigitsLettersAndMustBeMerged) {
            RegexPatternToken mixedCaseToken = this.nextTokens.get(RegexPatternTokenType.MIXED_CASE_LETTERS);
            if (mixedCaseToken == null) {
                mixedCaseToken = new RegexPatternToken(RegexPatternTokenType.MIXED_CASE_LETTERS);
                this.nextTokens.put(RegexPatternTokenType.MIXED_CASE_LETTERS, mixedCaseToken);
            }

            RegexPatternToken lowerCaseLetters = this.nextTokens.get(RegexPatternTokenType.LOWER_CASE_LETTERS);
            if (lowerCaseLetters != null) {
                mixedCaseToken.importTokenTrie(lowerCaseLetters);
                this.nextTokens.remove(RegexPatternTokenType.LOWER_CASE_LETTERS);
            }

            RegexPatternToken upperCaseLetters = this.nextTokens.get(RegexPatternTokenType.UPPER_CASE_LETTERS);
            if (upperCaseLetters != null) {
                mixedCaseToken.importTokenTrie(upperCaseLetters);
                this.nextTokens.remove(RegexPatternTokenType.UPPER_CASE_LETTERS);
            }
        }

        if (containsMixedDigitsLettersAndMustBeMerged) {
            RegexPatternToken letterOrDigitToken = this.nextTokens.get(RegexPatternTokenType.LETTERS_OR_DIGITS); // not null here

            RegexPatternToken mixedCaseToken = this.nextTokens.get(RegexPatternTokenType.MIXED_CASE_LETTERS);
            if (mixedCaseToken != null) {
                letterOrDigitToken.importTokenTrie(mixedCaseToken);
                this.nextTokens.remove(RegexPatternTokenType.MIXED_CASE_LETTERS);
            }

            RegexPatternToken digitToken = this.nextTokens.get(RegexPatternTokenType.DIGITS);
            if (digitToken != null) {
                letterOrDigitToken.importTokenTrie(digitToken);
                this.nextTokens.remove(RegexPatternTokenType.DIGITS);
            }
        }

        for (RegexPatternToken childToken : this.nextTokens.values()) {
            childToken.getBranch().mergeLetterAndDigitBranches();
        }
    }

    /**
     * Counts all samples that were included to detect child tokens.
     * @return Total number of samples that were included in the child tokens.
     */
    public int countTotalChildSampleOccurrences() {
        if (this.nextTokens.isEmpty()) {
            return 0;
        }

        int totalOccurrences = 0;
        for (RegexPatternToken childToken : this.nextTokens.values()) {
            totalOccurrences += childToken.countTotalSampleOccurrences();
        }

        return totalOccurrences;
    }

    /**
     * Counts all result expressions that will be generated, combined by | character (OR).
     * @return The count of terminal expressions that will be generated as separate regular expressions, separated by a | character (OR).
     */
    public int countTerminalExpressions() {
        int totalTerminalExpressions = 0;
        for (RegexPatternToken childToken : this.nextTokens.values()) {
            totalTerminalExpressions += childToken.countTerminalExpressions();
        }

        return totalTerminalExpressions;
    }

    /**
     * Finds the depth of the longest expression, based on the number of tokens.
     * @return The depth of the longest expression.
     */
    public int measureExpressionDepth() {
        int maxDepth = 0;
        for (RegexPatternToken childToken : this.nextTokens.values()) {
            maxDepth = Math.max(maxDepth, childToken.measureExpressionDepth());
        }

        return maxDepth;
    }

    /**
     * Generate a list of alternative regular expressions.
     * @param parameters Parameters object.
     * @return A list of alternative regular expressions.
     */
    public List<String> collectAlternativeRegularExpressions(RegexPatternAnalyzerParameters parameters) {
        List<String> alternativeExpressions = new ArrayList<>();

        for (RegexPatternToken childToken : this.nextTokens.values()) {
            alternativeExpressions.addAll(childToken.collectAlternativeRegularExpressions(parameters));
        }

        return alternativeExpressions;
    }

    /**
     * Checks if there are any child nodes.
     * @return True when the tree branch is empty and has no child nodes.
     */
    public boolean isEmpty() {
        return this.nextTokens.isEmpty();
    }
}

