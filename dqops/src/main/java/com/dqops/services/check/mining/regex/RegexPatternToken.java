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
 * A single token that is at the beginning of this part of a regular expression. It is followed by a branch node for the tokens that were present later.
 */
public class RegexPatternToken {
    private RegexPatternTokenType tokenType;
    private RegexPatternBranch branch = new RegexPatternBranch();
    private boolean terminalToken;
    private int minRepeats = 1;
    private int maxRepeats = 1;
    private Map<String, RegexTokenOccurrence> tokenOccurrences = new LinkedHashMap<>();

    public RegexPatternToken() {
    }

    /**
     * Create a new token given a token type.
     * @param tokenType Token type.
     */
    public RegexPatternToken(RegexPatternTokenType tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Create a new token given a token type.
     * @param tokenType Token type.
     * @param maxRepeats Max character repeats.
     */
    public RegexPatternToken(RegexPatternTokenType tokenType, int maxRepeats) {
        this.tokenType = tokenType;
        this.maxRepeats = maxRepeats;
    }

    /**
     * Returns the token type.
     * @return Token type.
     */
    public RegexPatternTokenType getTokenType() {
        return tokenType;
    }

    /**
     * Returns true if this is the last token and any following nodes in the branches are alternative expressions, that must be wrapped in parenthesis as optional.
     * @return True when this is a terminal token, false when it has required following tokens.
     */
    public boolean isTerminalToken() {
        return terminalToken;
    }

    /**
     * Sets the flag if this is a terminal token.
     * @param terminalToken True when this is a terminal token, false when it has follow up tokens.
     */
    public void setTerminalToken(boolean terminalToken) {
        this.terminalToken = terminalToken;
    }

    /**
     * Returns the minimum number of repeats of characters, for example, if it is a digit, it is the number of digits in one token.
     * @return The minimum number of characters.
     */
    public int getMinRepeats() {
        return minRepeats;
    }

    /**
     * Returns the maximum number of repeats of characters, for example, if it is a digit, it is the number of digits in one token.
     * @return The maximum number of characters.
     */
    public int getMaxRepeats() {
        return maxRepeats;
    }

    /**
     * Changes the maximum repeats of the character.
     * @param maxRepeats Maximum repeats.
     */
    public void setMaxRepeats(int maxRepeats) {
        this.maxRepeats = maxRepeats;
    }

    /**
     * Returns a branching node that contains next tokens (if present).
     * @return Branching node.
     */
    public RegexPatternBranch getBranch() {
        return branch;
    }

    /**
     * Returns a dictionary of all token instances that were included in this token.
     * @return Token occurrences map, by the token's text.
     */
    public Map<String, RegexTokenOccurrence> getTokenOccurrences() {
        return tokenOccurrences;
    }

    /**
     * Imports another occurrence(s) of this token, with their following tokens.
     * @param regexPatternToken Regex pattern token.
     */
    public void importTokenTrie(RegexPatternToken regexPatternToken) {
        this.terminalToken |= regexPatternToken.terminalToken;
        if (regexPatternToken.minRepeats < this.minRepeats) {
            this.minRepeats = regexPatternToken.minRepeats;
        }

        if (regexPatternToken.maxRepeats > this.maxRepeats) {
            this.maxRepeats = regexPatternToken.maxRepeats;
        }

        for (Map.Entry<String, RegexTokenOccurrence> otherPatternOccurrences : regexPatternToken.getTokenOccurrences().entrySet()) {
            RegexTokenOccurrence existingOccurrence = this.tokenOccurrences.get(otherPatternOccurrences.getKey());
            if (existingOccurrence == null) {
                this.tokenOccurrences.put(otherPatternOccurrences.getKey(), otherPatternOccurrences.getValue());
            } else {
                existingOccurrence.increaseCount(otherPatternOccurrences.getValue().getCount());
            }
        }

        this.branch.importPatternBranch(regexPatternToken.branch);
    }

    /**
     * Counts all samples that were included to detect this token.
     * @return Total number of samples.
     */
    public int countTotalSampleOccurrences() {
        int totalOccurrences = 0;
        for (RegexTokenOccurrence tokenOccurrence : this.tokenOccurrences.values()) {
            totalOccurrences += tokenOccurrence.getCount();
        }

        return totalOccurrences;
    }

    /**
     * Counts all result expressions that will be generated, combined by | character (OR).
     * @return The count of terminal expressions that will be generated as separate regular expressions, separated by a | character (OR).
     */
    public int countTerminalExpressions() {
        if (this.terminalToken && this.branch.getNextTokens().isEmpty()) {
            return 1;
        }

        return this.branch.countTerminalExpressions();
    }

    /**
     * Measures the depth of the regular expression. The depth is the number of alternative tokens in the regular expression.
     * @return Total depth in including this token and its deepest child token.
     */
    public int measureExpressionDepth() {
        return this.branch.measureExpressionDepth() + 1;
    }

    /**
     * Generate a list of alternative regular expressions.
     * @param parameters Parameters object.
     * @return A list of alternative regular expressions.
     */
    public List<String> collectAlternativeRegularExpressions(RegexPatternAnalyzerParameters parameters) {
        List<String> alternativeExpressions = new ArrayList<>();
        String myTokenRegex = null;

        if (this.tokenType.isRequireEscape()) {
            // single node pattern, detect if it does not require escaping
            if (this.tokenType == RegexPatternTokenType.EOL) {
                myTokenRegex = "\\n";
            } else if (this.tokenType == RegexPatternTokenType.TAB) {
                myTokenRegex = "\\t";
            } else {
                myTokenRegex = "\\" + this.tokenType.getPattern();
            }
        } else {
            myTokenRegex = this.tokenType.getPattern();
        }

        if (this.minRepeats == 0 && this.maxRepeats == 1) {
            myTokenRegex += "?";
        } else if (this.minRepeats != 1 || this.maxRepeats != 1) {
            if (parameters.isFindMaxCharactersCount()) {
                if (this.minRepeats == this.maxRepeats) {
                    myTokenRegex += "{" + this.minRepeats + "}";
                } else {
                    myTokenRegex += "{" + this.minRepeats + "," + this.maxRepeats + "}";
                }
            } else {
                if (this.minRepeats > 0) {
                    myTokenRegex += "+";
                } else {
                    myTokenRegex += "*";
                }
            }
        }

        if (this.branch.isEmpty()) {
            alternativeExpressions.add(myTokenRegex);
        } else {
            for (RegexPatternToken childToken : this.branch.getNextTokens().values()) {
                List<String> childNodeRegexExpressions = childToken.collectAlternativeRegularExpressions(parameters);

                for (String childRegex : childNodeRegexExpressions) {
                    if (this.isTerminalToken() && !this.branch.isEmpty()) {
                        alternativeExpressions.add(myTokenRegex + "(" + childRegex + ")?");
                    } else {
                        alternativeExpressions.add(myTokenRegex + childRegex);
                    }
                }
            }
        }

        return alternativeExpressions;
    }
}
