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

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.data.checkresults.services.models.CheckResultStatus;
import com.dqops.rules.RuleSeverityLevel;
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
          "If data grouping is enabled, the *current_severity* will be the highest data quality issue status from all data quality results for all data groups. " +
          "For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.")
@Data
public class CheckCurrentDataQualityStatusModel {
    /**
     * The data quality issue severity for this data quality check. An additional value *execution_error* is used to tell that the check,
     * sensor or rule failed to execute due to insufficient  permissions to the table or an error in the sensor's template or a Python rule.
     * For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.
     */
    @JsonPropertyDescription("The data quality issue severity for this data quality check. An additional value *execution_error* is used to tell that the check, " +
            "sensor or rule failed to execute due to insufficient  permissions to the table or an error in the sensor's template or a Python rule. " +
            "For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.")
    private CheckResultStatus currentSeverity;

    /**
     * The highest severity of previous executions of this data quality issue in the analyzed time range.
     * It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore.
     * For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.
     */
    @JsonPropertyDescription("The highest severity of previous executions of this data quality issue in the analyzed time range. " +
                             "It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore. " +
                             "For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.")
    private RuleSeverityLevel highestHistoricalSeverity;

    /**
     * The UTC timestamp when the check was recently executed.
     */
    @JsonPropertyDescription("The UTC timestamp when the check was recently executed.")
    private Instant executedAt;

    /**
     * The check type: profiling, monitoring, partitioned.
     */
    @JsonPropertyDescription("The check type: profiling, monitoring, partitioned.")
    private CheckType checkType;

    /**
     * The check time scale for *monitoring* and *partitioned* check types. The time scales are *daily* and *monthly*. The profiling checks do not have a time scale.
     */
    @JsonPropertyDescription("The check time scale for *monitoring* and *partitioned* check types. The time scales are *daily* and *monthly*. The profiling checks do not have a time scale.")
    private CheckTimeScale timeScale;

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
