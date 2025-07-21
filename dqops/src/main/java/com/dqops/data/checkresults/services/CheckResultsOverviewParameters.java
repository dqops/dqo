/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
     * The default number of results to return.
     */
    public static final int DEFAULT_RESULTS_COUNT = 5;

    /**
     * The number of recent results to return.
     */
    @JsonPropertyDescription("Number of recent results to load.")
    private int resultsCount = DEFAULT_RESULTS_COUNT;

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
     * Optional filter on the check category name.
     */
    @JsonPropertyDescription("Optional filter on the check category name.")
    private String category;

    /**
     * Optional filter on the check name.
     */
    @JsonPropertyDescription("Optional filter on the check name.")
    private String checkName;

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
