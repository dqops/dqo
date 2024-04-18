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

import java.time.LocalDateTime;

/**
 * Returns a single metric captured by the data statistics collectors (basic profilers).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class StatisticsMetricModel {
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
                setResultDataType(StatisticsResultDataType.INTEGER);
                setResult((int)SampleLongsRegistry.getSequenceNumber() % 10_000);
                setCollectedAt(LocalDateTime.of(2007, 10, 11, 18, 0, 0));
            }};
        }
    }
}
