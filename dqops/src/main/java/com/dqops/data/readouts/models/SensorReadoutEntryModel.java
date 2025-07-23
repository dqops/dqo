/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.models;

import com.dqops.checks.CheckType;
import com.dqops.data.errors.models.ErrorEntryModel;
import com.dqops.metadata.timeseries.TimePeriodGradient;
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
 * Detailed results for a single sensor. Represent one row in the sensor readouts table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class SensorReadoutEntryModel {
    @JsonPropertyDescription("Sensor readout primary key")
    String id;

    @JsonPropertyDescription("Check name")
    String checkName;
    @JsonPropertyDescription("Check display name")
    String checkDisplayName;
    @JsonPropertyDescription("Check type")
    CheckType checkType;

    @JsonPropertyDescription("Actual value")
    Double actualValue;
    @JsonPropertyDescription("Expected value")
    Double expectedValue;

    @JsonPropertyDescription("Column name")
    String columnName;
    @JsonPropertyDescription("Data group")
    String dataGroup;

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

    @JsonPropertyDescription("Table comparison name")
    String tableComparison;

    public static class SensorReadoutEntryModelSampleFactory implements SampleValueFactory<SensorReadoutEntryModel> {
        @Override
        public SensorReadoutEntryModel createSample() {
            return new SensorReadoutEntryModel() {{
                setActualValue(12.1);
                setCheckType(CheckType.monitoring);
                setTimeGradient(TimePeriodGradient.day);
                setColumnName("col1");
                setCheckName("daily_sum_in_range");
                setProvider("spark");
                setDurationMs(123);
                setExecutedAt(LocalDateTime.of(2023, 10, 11, 18, 0, 0).toInstant(ZoneOffset.UTC));
                setQualityDimension(SampleStringsRegistry.getQualityDimension());
                setTimePeriod(LocalDateTime.of(2023, 10, 11, 0, 0, 0));
            }};
        }
    }
}
