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

/**
 * A single occurrence of a regular expression token. Stores the token and the number of occurrences.
 */
public class RegexTokenOccurrence {
    private String token;
    private int count;

    /**
     * Creates a token occurrence.
     * @param token Token (text).
     * @param count Count of occurrences.
     */
    public RegexTokenOccurrence(String token, int count) {
        this.token = token;
        this.count = count;
    }

    /**
     * Returns the token.
     * @return The token text.
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the count of occurrences of the token.
     * @return Count of occurrences.
     */
    public int getCount() {
        return count;
    }

    /**
     * Increases the count by a given increment, copied from another occurrence.
     * @param increment Increment to increase by.
     */
    public void increaseCount(int increment) {
        this.count += increment;
    }
}
