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
