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
package com.dqops.data.errors.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;


/**
 * The parameters for retrieving the execution errors model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class ErrorsDetailedFilterParameters {
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
     * Name of the data group for which to get the errors.
     * If null, the default one is picked.
     */
    @JsonPropertyDescription("Data group for which to load the errors.")
    private String dataGroupName = null;

    /**
     * Start month to load. Loads the data for the whole month identified by the date.
     * If null, two most recent months for which the errors are available are selected.
     */
    @JsonPropertyDescription("Start month to load. Loads the data for the whole month identified by the date.")
    private LocalDate startMonth = null;

    /**
     * End month to load. Loads the data for the whole month identified by the date.
     * If null, two most recent months for which the errors are available are selected.
     */
    @JsonPropertyDescription("End month to load. Loads the data for the whole month identified by the date.")
    private LocalDate endMonth = null;
}
