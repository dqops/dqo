/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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

package ai.dqo.data.checkresults.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;

/**
 * Parameters for filtering the data (the data quality issues) for the histogram of a data quality incident.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentHistogramFilterParameters {
    /**
     * Optional filter that is a pattern expression. Filter is used on schema names, table names, dimensions, categories, etc.
     */
    @JsonPropertyDescription("Optional filter that is a pattern expression. Filters are used on schema names, table names, dimensions, categories, etc.")
    private String filter;

    /**
     * Optional filter for the number of recent days (1, 7, 30, etc.) that are scanned for issues related to an incident.
     */
    @JsonPropertyDescription("Optional filter for the number of recent days (1, 7, 30, etc.) that are scanned for issues related to an incident.")
    private Integer days;

    /**
     * Optional filter for a single date to return data quality issues that were detected on that date. The date uses a local time zone of the DQO server.
     */
    @JsonPropertyDescription("Optional filter for a single date to return data quality issues that were detected on that date. The date uses a local time zone of the DQO server.")
    private LocalDate date;

    /**
     * Optional filter for an exact column name.
     */
    @JsonPropertyDescription("Optional filter for an exact column name.")
    private String column;

    /**
     * Optional filter for an exact data quality check name.
     */
    @JsonPropertyDescription("Optional filter for an exact data quality check name.")
    private String check;
}
