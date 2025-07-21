/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.testutils;

import org.apache.parquet.Strings;

/**
 * Helper method for converting any values to an expected data type.
 */
public class ValueConverter {
    /**
     * Converts any type of object to an integer. Throws exception when the conversion fails.
     * @param obj Object to convert.
     * @return Value converted to an integer.
     */
    public static Integer toInteger(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Integer) {
            return (Integer)obj;
        }

        if (obj instanceof Long) {
            return ((Long)obj).intValue();
        }

        if (obj instanceof Short) {
            return ((Short)obj).intValue();
        }

        if (obj instanceof Float) {
            return ((Float)obj).intValue();
        }

        if (obj instanceof Double) {
            return ((Double)obj).intValue();
        }

        if (obj instanceof String) {
            if (Strings.isNullOrEmpty((String)obj)) {
                return null;
            }
            return Integer.valueOf((String)obj);
        }

        return Integer.valueOf(obj.toString());
    }

    /**
     * Converts any type of object to a long. Throws exception when the conversion fails.
     * @param obj Object to convert.
     * @return Value converted to a long.
     */
    public static Long toLong(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Long) {
            return (Long)obj;
        }

        if (obj instanceof Integer) {
            return ((Integer)obj).longValue();
        }

        if (obj instanceof Short) {
            return ((Short)obj).longValue();
        }

        if (obj instanceof Float) {
            return ((Float)obj).longValue();
        }

        if (obj instanceof Double) {
            return ((Double)obj).longValue();
        }

        if (obj instanceof String) {
            if (Strings.isNullOrEmpty((String)obj)) {
                return null;
            }

            return Long.valueOf((String)obj);
        }

        return Long.valueOf(obj.toString());
    }

    /**
     * Converts any type of object to a double. Throws exception when the conversion fails.
     * @param obj Object to convert.
     * @return Value converted to an double.
     */
    public static Double toDouble(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Double) {
            return (Double)obj;
        }

        if (obj instanceof Long) {
            return ((Long)obj).doubleValue();
        }

        if (obj instanceof Short) {
            return ((Short)obj).doubleValue();
        }

        if (obj instanceof Float) {
            return ((Float)obj).doubleValue();
        }

        if (obj instanceof Integer) {
            return ((Integer)obj).doubleValue();
        }

        if (obj instanceof String) {
            if (Strings.isNullOrEmpty((String)obj)) {
                return null;
            }
            return Double.valueOf((String)obj);
        }

        return Double.valueOf(obj.toString());
    }
}
