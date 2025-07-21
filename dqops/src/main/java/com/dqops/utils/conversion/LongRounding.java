/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.conversion;

/**
 * Helper class that performs rounding of Long (64-bit integer) values to preserve a given number of important digits.
 */
public final class LongRounding {
    public static final int DEFAULT_MAX_EFFECTIVE_DIGITS = 3;

    /**
     * Rounds the number to preserve N effective digits, not counting any preceding zeroes.
     * @param value Value to round.
     * @return Rounded value.
     */
    public static long roundToKeepEffectiveDigits(long value) {
        return roundToKeepMaxEffectiveDigits(value, DEFAULT_MAX_EFFECTIVE_DIGITS);
    }

    /**
     * Rounds the number to preserve N effective digits, not counting any preceding zeroes.
     * @param value Value to round.
     * @return Rounded value.
     */
    public static long roundToKeepMaxEffectiveDigits(long value, int maxDigits) {
        if (value == 0L) {
            return 0L;
        }

        String text = Long.toString(value);
        char[] digits = text.toCharArray();
        for (int i = (text.charAt(0) == '-' ? 1 : 0) + maxDigits; i < digits.length; i++) {
            digits[i] = '0';
        }

        return Long.parseLong(new String(digits));
    }
}
