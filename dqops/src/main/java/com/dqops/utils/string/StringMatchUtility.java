/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.string;

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
