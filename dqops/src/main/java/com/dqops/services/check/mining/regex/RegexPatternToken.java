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
    private int maxRepeats = 1;
    private Map<String, RegexTokenOccurrence> tokenOccurrences = new LinkedHashMap<>();


}
