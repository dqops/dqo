/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
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

package ai.dqo.data.checkresults.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Detailed results for a single check. Represent one row in the check results table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultDetailedSingleModel {
    @JsonPropertyDescription("Check result ID.")
    String id;

    @JsonPropertyDescription("Actual value.")
    Double actualValue;
    @JsonPropertyDescription("Expected value.")
    Double expectedValue;
    @JsonPropertyDescription("Warning lower bound.")
    Double warningLowerBound;
    @JsonPropertyDescription("Warning upper bound.")
    Double warningUpperBound;
    @JsonPropertyDescription("Error lower bound.")
    Double errorLowerBound;
    @JsonPropertyDescription("Error upper bound.")
    Double errorUpperBound;
    @JsonPropertyDescription("Fatal lower bound.")
    Double fatalLowerBound;
    @JsonPropertyDescription("Fatal upper bound.")
    Double fatalUpperBound;
    @JsonPropertyDescription("Severity.")
    Integer severity;

    @JsonPropertyDescription("Column name.")
    String columnName;
    @JsonPropertyDescription("Data stream.")
    String dataStream;

    @JsonPropertyDescription("Duration (ms).")
    Integer durationMs;
    @JsonPropertyDescription("Executed at.")
    Instant executedAt;
    @JsonPropertyDescription("Time gradient.")
    String timeGradient;
    @JsonPropertyDescription("Time period.")
    LocalDateTime timePeriod;

    @JsonPropertyDescription("Include in KPI.")
    Boolean includeInKpi;
    @JsonPropertyDescription("Include in SLA.")
    Boolean includeInSla;
    @JsonPropertyDescription("Provider.")
    String provider;
    @JsonPropertyDescription("Quality dimension.")
    String qualityDimension;
    @JsonPropertyDescription("Sensor name.")
    String sensorName;
}
