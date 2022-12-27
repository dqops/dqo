/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
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

package ai.dqo.data.ruleresults.services.models;

import ai.dqo.metadata.search.TableSearchFilters;
import lombok.Data;
import org.apache.parquet.Strings;

import java.time.LocalDate;
import java.util.*;

/**
 * Filter parameters for acquiring a fragment of rule results.
 */
@Data
public class RuleResultsFragmentFilter {
    /**
     * Filters for finding the desired table.
     */
    private TableSearchFilters tableSearchFilters;

    /**
     * Start of the date range.
     */
    private LocalDate dateStart;

    /**
     * End of the date range.
     */
    private LocalDate dateEnd;

    /**
     * Should the day part of date be ignored, only the month is taken into consideration. Default true.
     */
    private boolean ignoreDateDay = true;

    private String checkCategory;

    private String checkName;

    private String checkType;

    private String columnName;

    private String dataStreamName;

    private String qualityDimension;

    private String timeGradient;

    /**
     * Tell which additional columns should be read from the parquet, and what their value should be.
     * @return Mapping column name to expected value.
     */
    public Map<String, String> getColumnConditions() {
        Map<String, String> result = new HashMap<>();
        if (!Strings.isNullOrEmpty(checkCategory)) {
            result.put("check_category", checkCategory);
        }
        if (!Strings.isNullOrEmpty(checkName)) {
            result.put("check_name", checkName);
        }
        if (!Strings.isNullOrEmpty(checkType)) {
            result.put("check_type", checkType);
        }
        if (!Strings.isNullOrEmpty(columnName)) {
            result.put("column_name", columnName);
        }
        if (!Strings.isNullOrEmpty(dataStreamName)) {
            result.put("data_stream_name", dataStreamName);
        }
        if (!Strings.isNullOrEmpty(qualityDimension)) {
            result.put("quality_dimension", qualityDimension);
        }
        if (!Strings.isNullOrEmpty(timeGradient)) {
            result.put("time_gradient", timeGradient);
        }

        return result;
    }
}
