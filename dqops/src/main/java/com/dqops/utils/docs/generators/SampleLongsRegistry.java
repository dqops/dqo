/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.generators;

import com.dqops.metadata.sources.PhysicalTableName;

import java.time.LocalDate;

public class SampleLongsRegistry {
    public static long getMaxResultsPerCheck() {
        return 100;
    }

    public static long getMonths() {
        return 1;
    }

    public static long getYear() {
        return 2007;
    }

    public static long getMonth() {
        return 10;
    }

    public static long getJobId() {
        return 123456789L;
    }

    public static long getSequenceNumber() {
        return 3854372;
    }

    /**
     * Gets sample long fitting the parameter.
     * @param parameterName Parameter name.
     * @return Sample long for the parameter.
     */
    public static long getMatchingLongForParameter(String parameterName) {
        String parameterNameLower = parameterName.toLowerCase();

        if (parameterNameLower.contains("max_results_per_check")) {
            return getMaxResultsPerCheck();
        } else if (parameterNameLower.contains("months")) {
            return getMonths();
        } else if (parameterNameLower.contains("year")) {
            return getYear();
        } else if (parameterNameLower.contains("month")) {
            return getMonth();
        } else if (parameterNameLower.contains("job")) {
            return getJobId();
        } else if (parameterNameLower.contains("sequence")) {
            return getSequenceNumber();
        }

        throw new IllegalArgumentException("No value found fitting the parameter \"" + parameterName + "\".");
    }
}
