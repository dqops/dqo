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

package com.dqops.data.checkresults.services.models.currentstatus;

import com.dqops.data.checkresults.services.models.CheckResultStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;

/**
 * The most recent data quality status for a single data quality check.
 * If data grouping is enabled, this model will return the highest data quality issue status from all
 * data quality results for all data groups.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckCurrentDataQualityStatusModel", description = "The most recent data quality status for a single data quality check. " +
          "If data grouping is enabled, this model will return the highest data quality issue status from all data quality results for all data groups.")
@Data
public class CheckCurrentDataQualityStatusModel {
    /**
     * The data quality issue severity for this data quality check.
     */
    @JsonPropertyDescription("The data quality issue severity for this data quality check.")
    private CheckResultStatus severity;

    /**
     * The UTC timestamp when the check was recently executed.
     */
    @JsonPropertyDescription("The UTC timestamp when the check was recently executed.")
    private Instant executedAt;

    /**
     * Check category name.
     */
    @JsonPropertyDescription("Check category name, such as nulls, schema, strings, volume.")
    private String category;

    /**
     * Data quality dimension, such as Completeness, Uniqueness, Validity.
     */
    @JsonPropertyDescription("Data quality dimension, such as Completeness, Uniqueness, Validity.")
    private String qualityDimension;
}
