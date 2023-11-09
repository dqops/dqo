/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.utils.docs;

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
        return 10832;
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
