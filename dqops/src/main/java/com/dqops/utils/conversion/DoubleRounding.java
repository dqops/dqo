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

import java.util.Locale;

/**
 * Helper class that performs rounding of double (floating point) values to preserve a given number of important digits.
 */
public final class DoubleRounding {
    public static final int DEFAULT_MAX_EFFECTIVE_DIGITS = 3;

    /**
     * Rounds the number to preserve N effective digits, not counting any preceding zeroes.
     * @param value Value to round.
     * @return Rounded value.
     */
    public static double roundToKeepEffectiveDigits(double value) {
        return roundToKeepMaxEffectiveDigits(value, DEFAULT_MAX_EFFECTIVE_DIGITS);
    }

    /**
     * Rounds the number to preserve N effective digits, not counting any preceding zeroes.
     * @param value Value to round.
     * @return Rounded value.
     */
    public static double roundToKeepMaxEffectiveDigits(double value, int maxDigits) {
        if (!Double.isFinite(value)) {
            return value;
        }

        String text = String.format(Locale.ENGLISH, "%.13f", value);

        char[] digits = text.toCharArray();
        int charIndex = 0;

        // skipping preceding zeroes
        for (; charIndex < digits.length; charIndex++) {
            char currentChar = digits[charIndex];
            if (currentChar != '0' && currentChar != '-' && currentChar != '.') {
                break;
            }
        }

        for (int digitsCount = 0; charIndex < digits.length; charIndex++) {
            char currentChar = digits[charIndex];
            if (Character.isDigit(currentChar)) {
                digitsCount++;

                if (digitsCount == maxDigits) {
                    charIndex++;
                    break;
                }
            }
        }

        for (; charIndex < digits.length; charIndex++) {
            char currentChar = digits[charIndex];
            if (Character.isDigit(currentChar)) {
                digits[charIndex] = '0'; // clearing
            }
        }

        String fixedDigits = new String(digits);
        return Double.valueOf(fixedDigits);
    }
}
