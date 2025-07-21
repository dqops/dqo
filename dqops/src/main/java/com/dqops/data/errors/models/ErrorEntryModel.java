/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errors.models;

import com.dqops.checks.CheckType;
import com.dqops.data.statistics.factory.StatisticsResultDataType;
import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.docs.generators.SampleLongsRegistry;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    public static class ErrorEntryModelSampleFactory implements SampleValueFactory<ErrorEntryModel> {
        @Override
        public ErrorEntryModel createSample() {
            return new ErrorEntryModel() {{
                setCheckType(CheckType.monitoring);
                setProvider("spark");
                setDurationMs(123);
                setExecutedAt(LocalDateTime.of(2023, 10, 11, 18, 0, 0).toInstant(ZoneOffset.UTC));
                setErrorSource("sensor");
                setQualityDimension(SampleStringsRegistry.getQualityDimension());
                setTimeGradient(TimePeriodGradient.day);
                setTimePeriod(LocalDateTime.of(2023, 10, 11, 0, 0, 0));
                setErrorMessage("SQL query failed at 3:10. Unknown or unexpected token 'WHERE'.");
            }};
        }
    }
}
