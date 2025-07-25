/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.statistics.models;

import com.dqops.data.statistics.factory.StatisticsResultDataType;
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
 * Returns a single metric captured by the data statistics collectors (basic profilers).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class StatisticsMetricModel {
    public StatisticsMetricModel() {
    }

    public StatisticsMetricModel(Object result, Instant executedAt, Long sampleCount) {
        this.result = result;
        this.executedAt = executedAt;
        this.sampleCount = sampleCount;
    }

    /**
     * Statistic category.
     */
    @JsonPropertyDescription("Statistics category")
    private String category;

    /**
     * Statistic's collector name (the type of the statistics).
     */
    @JsonPropertyDescription("Statistics (metric) name")
    private String collector;

    /**
     * Sensor name.
     */
    @JsonPropertyDescription("Sensor name")
    private String sensorName;

    /**
     * The data type that was collected.
     */
    @JsonPropertyDescription("Statistics result data type")
    private StatisticsResultDataType resultDataType;

    /**
     * The metric value that was collected. It can be a string, datetime, date, double - depending on the data type.
     */
    @JsonPropertyDescription("Statistics result for the metric")
    private Object result;

    /**
     * The local date and time when the metric was collected.
     */
    @JsonPropertyDescription("The local timestamp when the metric was collected")
    private LocalDateTime collectedAt;

    /**
     * The UTC date and time when the metric was collected (executed).
     */
    @JsonPropertyDescription("The UTC timestamp when the metric was collected (executed)")
    private Instant executedAt;

    /**
     * The number of the value samples for this result value. Filled only by the column value sampling profilers.
     */
    @JsonPropertyDescription("The number of the value samples for this result value. Filled only by the column value sampling profilers.")
    private Long sampleCount;

    /**
     * The index of the result that was returned. Filled only by the column value sampling profilers to identify each column value sample.
     */
    @JsonPropertyDescription("The index of the result that was returned. Filled only by the column value sampling profilers to identify each column value sample.")
    private Integer sampleIndex;

    public static class StatisticsMetricModelSampleFactory implements SampleValueFactory<StatisticsMetricModel> {
        @Override
        public StatisticsMetricModel createSample() {
            return new StatisticsMetricModel() {{
                setCategory(SampleStringsRegistry.getCategoryName());
                setCollector(SampleStringsRegistry.getCollectorName());
                setSensorName(SampleStringsRegistry.getSensorName());
                setResultDataType(StatisticsResultDataType.INTEGER);
                setResult((int)SampleLongsRegistry.getSequenceNumber() % 10_000);
                setCollectedAt(LocalDateTime.of(2007, 10, 11, 18, 0, 0));
                setExecutedAt(LocalDateTime.of(2007, 10, 11, 18, 0, 0).atOffset(ZoneOffset.UTC).toInstant());
            }};
        }
    }
}
