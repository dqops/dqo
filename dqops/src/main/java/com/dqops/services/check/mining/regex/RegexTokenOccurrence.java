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
