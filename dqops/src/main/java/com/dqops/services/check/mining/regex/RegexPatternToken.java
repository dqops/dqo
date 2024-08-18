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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A single token that is at the beginning of this part of a regular expression. It is followed by a branch node for the tokens that were present later.
 */
public class RegexPatternToken {
    private RegexPatternTokenType tokenType;
    private RegexPatternBranch branch = new RegexPatternBranch();
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
}
