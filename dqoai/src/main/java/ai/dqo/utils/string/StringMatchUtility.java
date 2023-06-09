/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.utils.string;

/**
 * Utility class for string matching functions that aren't included in Java 11.
 */
public class StringMatchUtility {
    public static boolean startsWithIgnoreCase(String base, String prefix) {
        if (base == null || prefix == null) {
            return false;
        }

        String lowerPrefix = prefix.toLowerCase();
        String upperPrefix = prefix.toUpperCase();
        return base.toLowerCase().startsWith(lowerPrefix) && base.toUpperCase().startsWith(upperPrefix);
    }

    public static boolean containsIgnoreCase(String base, String infix) {
        if (base == null || infix == null) {
            return false;
        }

        String lowerInfix = infix.toLowerCase();
        String upperInfix = infix.toUpperCase();
        return base.toLowerCase().contains(lowerInfix) && base.toUpperCase().contains(upperInfix);
    }

    public static boolean endsWithIgnoreCase(String base, String suffix) {
        if (base == null || suffix == null) {
            return false;
        }

        String lowerSuffix = suffix.toLowerCase();
        String upperSuffix = suffix.toUpperCase();
        return base.toLowerCase().endsWith(lowerSuffix) && base.toUpperCase().endsWith(upperSuffix);
    }

    public static boolean startsWithEndsWith(String base, String prefix, String suffix) {
        if (base == null || prefix == null || !base.startsWith(prefix)) {
            return false;
        }

        String baseNoPrefix = base.substring(prefix.length());
        return baseNoPrefix.endsWith(suffix);
    }

    public static boolean startsWithEndsWithIgnoreCase(String base, String prefix, String suffix) {
        if (!startsWithIgnoreCase(base, prefix)) {
            return false;
        }

        String baseNoPrefix = base.substring(prefix.length());
        return endsWithIgnoreCase(baseNoPrefix, suffix);
    }
}
