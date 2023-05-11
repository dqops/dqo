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
package ai.dqo.data.readouts.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Detailed results for a single sensor. Represent one row in the sensor readouts table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class SensorReadoutDetailedSingleModel {
    @JsonPropertyDescription("Sensor readout ID.")
    String id;

    @JsonPropertyDescription("Check name.")
    String checkName;
    @JsonPropertyDescription("Check display name.")
    String checkDisplayName;
    @JsonPropertyDescription("Check type.")
    String checkType;

    @JsonPropertyDescription("Actual value.")
    Double actualValue;
    @JsonPropertyDescription("Expected value.")
    Double expectedValue;

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

    @JsonPropertyDescription("Provider.")
    String provider;
    @JsonPropertyDescription("Quality dimension.")
    String qualityDimension;
}
