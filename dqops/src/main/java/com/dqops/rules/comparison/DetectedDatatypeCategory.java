/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rules.comparison;

/**
 * Detected data type category.
 */
public enum DetectedDatatypeCategory {
    /**
     * Integer values
     */
    integers(1),

    /**
     * Float and numeric types that are not integers
     */
    floats(2),

    /**
     * Dates
     */
    dates(3),

    /**
     * Datetime (date with time)
     */
    datetimes(4),

    /**
     * Timestamp types (with a time zone).
     */
    timestamps(5),

    /**
     * Booleans
     */
    booleans(6),

    /**
     * Text values
     */
    texts(7),

    /**
     * Mixed data types
     */
    mixed(8);

    private int code;

    DetectedDatatypeCategory(int code) {
        this.code = code;
    }

    /**
     * Returns a numeric code used to identify the data type.
     * @return Numeric type.
     */
    public int getCode() {
        return code;
    }

    /**
     * Creates an enum value from a value code.
     * @param code Code.
     * @return Category enum value.
     */
    public static DetectedDatatypeCategory fromCode(int code) {
        switch (code) {
            case 1: return integers;
            case 2: return floats;
            case 3: return dates;
            case 4: return datetimes;
            case 6: return timestamps;
            case 7: return texts;
            case 8: return mixed;
            default:
                return null;
        }
    }
}
