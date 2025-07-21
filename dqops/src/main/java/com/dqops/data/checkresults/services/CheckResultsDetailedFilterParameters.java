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
 * The parameters for retrieving the check results model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultsDetailedFilterParameters {
    /**
     * The default limit of results per check to load.
     */
    public static final int DEFAULT_MAX_RESULTS_PER_CHECK = 100;

    /**
     * The maximum number of most recent results to load for each check.
     */
    @JsonPropertyDescription("The maximum number of most recent results to load for each check.")
    private int maxResultsPerCheck = DEFAULT_MAX_RESULTS_PER_CHECK;

    /**
     * Check name to filter.
     */
    @JsonPropertyDescription("Check name to filter.")
    private String checkName;

    /**
     * Check category to filter.
     */
    @JsonPropertyDescription("Check category to filter.")
    private String checkCategory;

    /**
     * Table comparison name to filter.
     */
    @JsonPropertyDescription("Table comparison name to filter.")
    private String tableComparison;

    /**
     * Name of the data group name for which to get the results.
     * If null, the default one is picked.
     */
    @JsonPropertyDescription("Data group name for which to load the results.")
    private String dataGroupName = null;

    /**
     * Start month to load. Loads the data for the whole month identified by the date.
     * If null, two most recent months for which the results are available are selected.
     */
    @JsonPropertyDescription("Start month to load. Loads the data for the whole month identified by the date.")
    private LocalDate startMonth = null;

    /**
     * End month to load. Loads the data for the whole month identified by the date.
     * If null, two most recent months for which the results are available are selected.
     */
    @JsonPropertyDescription("End month to load. Loads the data for the whole month identified by the date.")
    private LocalDate endMonth = null;

    /**
     * The mode of loading data. The default mode is to find the most recent result from each check,
     * identify its data group and load remaining results only for that group. An alternative method loads the most recent value from all data groups.
     */
    @JsonPropertyDescription("The mode of loading data. The default mode is to find the most recent result from each check, identify its data group and load remaining results only for that group. An alternative method loads the most recent value from all data groups.")
    private CheckResultsDetailedLoadMode loadMode = CheckResultsDetailedLoadMode.first_data_group;
}
