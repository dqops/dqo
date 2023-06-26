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
package ai.dqo.rest.models.comparison;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Model with the custom compare threshold levels for raising data quality issues at different severity levels
 * when the difference between the compared (tested) table and the reference table (the source of truth) exceed given
 * thresholds as a percentage of difference between the actual value and the expected value from the reference table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CompareThresholdsModel", description = "Model with the compare threshold levels for raising data quality issues at different severity levels " +
        "when the difference between the compared (tested) table and the reference table (the source of truth) exceed given " +
        "thresholds as a percentage of difference between the actual value and the expected value from the reference table.")
@Data
public class CompareThresholdsModel {
    @JsonPropertyDescription("Enables raising a warning severity data quality issue when the difference between the measure on the compared table and the reference table is above the warning level threshold (the percentage difference).")
    private boolean enableWarning = true;

    @JsonPropertyDescription("The percentage difference between the measure value on the compared table and the reference table that raises a warning severity data quality issue when the difference is bigger.")
    private double warningDifferencePercent = 0.0;

    @JsonPropertyDescription("Enables raising an error severity data quality issue when the difference between the measure on the compared table and the reference table is above the error level threshold (the percentage difference).")
    private boolean enableError = true;

    @JsonPropertyDescription("The percentage difference between the measure value on the compared table and the reference table that raises an error severity data quality issue when the difference is bigger.")
    private double errorDifferencePercent = 1.0;

    @JsonPropertyDescription("Enables raising a fatal severity data quality issue when the difference between the measure on the compared table and the reference table is above the fatal level threshold (the percentage difference).")
    private boolean enableFatal = true;

    @JsonPropertyDescription("The percentage difference between the measure value on the compared table and the reference table that raises a fatal severity data quality issue when the difference is bigger.")
    private double fatalDifferencePercent = 5.0;
}
