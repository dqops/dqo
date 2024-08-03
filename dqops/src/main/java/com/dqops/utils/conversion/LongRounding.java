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
