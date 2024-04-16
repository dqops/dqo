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
package com.dqops.data.errors.models;

import com.dqops.checks.CheckType;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Detailed error statuses for a single check. Represent one row in the errors table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class ErrorEntryModel {
    @JsonPropertyDescription("Actual value")
    Double actualValue;
    @JsonPropertyDescription("Expected value")
    Double expectedValue;

    @JsonPropertyDescription("Column name")
    String columnName;
    @JsonPropertyDescription("Data group")
    String dataGroup;
    @JsonPropertyDescription("Check type")
    private CheckType checkType;

    @JsonPropertyDescription("Duration (ms)")
    Integer durationMs;
    @JsonPropertyDescription("Executed at")
    Instant executedAt;
    @JsonPropertyDescription("Time gradient")
    TimePeriodGradient timeGradient;
    @JsonPropertyDescription("Time period")
    LocalDateTime timePeriod;

    @JsonPropertyDescription("Provider name")
    String provider;
    @JsonPropertyDescription("Data quality dimension")
    String qualityDimension;

    @JsonPropertyDescription("Sensor name")
    String sensorName;
    @JsonPropertyDescription("Sensor readout ID")
    String readoutId;

    @JsonPropertyDescription("Error message")
    String errorMessage;
    @JsonPropertyDescription("Error source")
    String errorSource;
    @JsonPropertyDescription("Error timestamp")
    LocalDateTime errorTimestamp;

    @JsonPropertyDescription("Table comparison name")
    String tableComparison;
}
