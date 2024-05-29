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
