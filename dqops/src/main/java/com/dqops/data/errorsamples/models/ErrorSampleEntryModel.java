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
