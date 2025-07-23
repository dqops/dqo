/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errorsamples.models;

import com.dqops.checks.CheckType;
import com.dqops.data.errorsamples.factory.ErrorSampleResultDataType;
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

/**
 * Detailed error samples captured for a single check. Represent one row in the error_samples table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class ErrorSampleEntryModel {
    /**
     * Unique ID of the error sample value.
     */
    @JsonPropertyDescription("Unique ID of the error sample value")
    private String id;

    /**
     * The error sample value that was collected. It can be a string, datetime, date, double - depending on the data type.
     */
    @JsonPropertyDescription("Error sample (value)")
    private Object result;

    /**
     * The data type that was collected.
     */
    @JsonPropertyDescription("Error sample result data type")
    private ErrorSampleResultDataType resultDataType;

    /**
     * The local date and time when the error sample was collected.
     */
    @JsonPropertyDescription("The local timestamp when the error sample was collected")
    private LocalDateTime collectedAt;

    /**
     * The index of the result that was returned. Identifies a single error sample within a list.
     */
    @JsonPropertyDescription("The index of the result that was returned. Identifies a single error sample within a list.")
    private Integer sampleIndex;

    /**
     * Check type
     */
    @JsonPropertyDescription("Check type")
    private CheckType checkType;

    /**
     * Time gradient
     */
    @JsonPropertyDescription("Time gradient")
    private TimePeriodGradient timeGradient;

    /**
     * Execution duration (ms)
     */
    @JsonPropertyDescription("Execution duration (ms)")
    private Integer durationMs;

    /**
     * Executed at
     */
    @JsonPropertyDescription("Executed at")
    private Instant executedAt;

    /**
     * The value of the first column of the unique identifier that identifies a row containing the error sample.
     */
    @JsonPropertyDescription("The value of the first column of the unique identifier that identifies a row containing the error sample.")
    private String rowId1;

    /**
     * The value of the second column of the unique identifier that identifies a row containing the error sample.
     */
    @JsonPropertyDescription("The value of the second column of the unique identifier that identifies a row containing the error sample.")
    private String rowId2;

    /**
     * The value of the third column of the unique identifier that identifies a row containing the error sample.
     */
    @JsonPropertyDescription("The value of the third column of the unique identifier that identifies a row containing the error sample.")
    private String rowId3;

    /**
     * The value of the fourth column of the unique identifier that identifies a row containing the error sample.
     */
    @JsonPropertyDescription("The value of the fourth column of the unique identifier that identifies a row containing the error sample.")
    private String rowId4;

    /**
     * The value of the fifth column of the unique identifier that identifies a row containing the error sample.
     */
    @JsonPropertyDescription("The value of the fifth column of the unique identifier that identifies a row containing the error sample.")
    private String rowId5;

    /**
     * Column name
     */
    @JsonPropertyDescription("Column name")
    String columnName;

    /**
     * Data group
     */
    @JsonPropertyDescription("Data group")
    String dataGroup;

    /**
     * Provider name.
     */
    @JsonPropertyDescription("Provider name")
    String provider;

    /**
     * Data quality dimension
     */
    @JsonPropertyDescription("Data quality dimension")
    String qualityDimension;

    /**
     * Sensor name
     */
    @JsonPropertyDescription("Sensor name")
    String sensorName;

    /**
     * Table comparison name
     */
    @JsonPropertyDescription("Table comparison name")
    String tableComparison;

    public static class ErrorSampleEntryModelSampleFactory implements SampleValueFactory<ErrorSampleEntryModel> {
        @Override
        public ErrorSampleEntryModel createSample() {
            return new ErrorSampleEntryModel() {{
                setCheckType(CheckType.monitoring);
                setProvider("spark");
                setQualityDimension("Validity");
                setResult("abc");
                setRowId1("4");
                setResultDataType(ErrorSampleResultDataType.STRING);
                setQualityDimension(SampleStringsRegistry.getQualityDimension());
                setColumnName("col1");
                setCollectedAt(LocalDateTime.of(2023, 10, 11, 0, 0, 0));
            }};
        }
    }
}
