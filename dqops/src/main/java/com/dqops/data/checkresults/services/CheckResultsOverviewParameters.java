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
package com.dqops.data.checkresults.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;


/**
 * The parameters for the check overview. The object created using the default constructor will contain the default configuration to load the current and previous month.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultsOverviewParameters {
    /**
     * The number of recent results to return.
     */
    @JsonPropertyDescription("Number of recent results to load.")
    private int resultsCount = 5;

    /**
     * Start month to load. Loads the data for the whole month identified by the date.
     */
    @JsonPropertyDescription("Start month to load. Loads the data for the whole month identified by the date.")
    private LocalDate startMonth = LocalDate.now().minusMonths(1L);

    /**
     * End month to load. Loads the data for the whole month identified by the date.
     */
    @JsonPropertyDescription("End month to load. Loads the data for the whole month identified by the date.")
    private LocalDate endMonth = LocalDate.now();

    /**
     * Returns the check overview parameters for a given number of months.
     * @param months Number of months. 0 for the current month only. 1 for current and previous month (to capture one full month).
     * @param resultsCount The maximum number of months to load, because loading starts at the most recent month and goes back. If there are no data for the current month and we requested 1 month, DQOps will load earlier months.
     * @return Check result overview parameters.
     */
    public static CheckResultsOverviewParameters createForRecentMonths(long months, int resultsCount) {
        CheckResultsOverviewParameters checkResultsOverviewParameters = new CheckResultsOverviewParameters();
        checkResultsOverviewParameters.setResultsCount(resultsCount);
        checkResultsOverviewParameters.setStartMonth(checkResultsOverviewParameters.getEndMonth().minusMonths(months - 1));
        return checkResultsOverviewParameters;
    }
}
