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

        int indexOfDot = text.indexOf('.');
        if (indexOfDot < 0) {
            return value;
        }

        if (indexOfDot > maxDigits + (text.charAt(0) == '-' ? 1 : 0)) {
            text = text.substring(0, indexOfDot);
            return Double.valueOf(text); // truncate to the nearest integer value
        }

        int indexOfFirstNotZero = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isDigit(c) && c != '0') {
                indexOfFirstNotZero = i;
                break;
            }
        }

        if (text.length() < indexOfFirstNotZero + maxDigits) {
            return value;
        }

        if (indexOfDot > indexOfFirstNotZero && indexOfDot <= indexOfFirstNotZero + maxDigits) {
            text = text.substring(0, indexOfFirstNotZero + maxDigits + 1);
        } else {
            text = text.substring(0, indexOfFirstNotZero + maxDigits);
        }

        return Double.valueOf(text);
    }
}
