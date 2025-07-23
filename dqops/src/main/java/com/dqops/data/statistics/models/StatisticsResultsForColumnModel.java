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

import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.docs.generators.SampleListUtility;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.*;

/**
 * Model object with the statistics results for a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class StatisticsResultsForColumnModel {
    public StatisticsResultsForColumnModel() {
    }

    public StatisticsResultsForColumnModel(String connectionName, PhysicalTableName table, String columnName) {
        this.connectionName = connectionName;
        this.table = table;
        this.columnName = columnName;
    }

    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name")
    private String connectionName;

    /**
     * Full table name (schema.table).
     */
    @JsonPropertyDescription("Physical table name including the schema and table names")
    private PhysicalTableName table;

    /**
     * Column name.
     */
    @JsonPropertyDescription("Column name")
    private String columnName;

    /**
     * Collection (list) of statistics that were collected for the column.
     */
    @JsonPropertyDescription("List of statistics metrics")
    private List<StatisticsMetricModel> metrics = new ArrayList<>();

    /**
     * Collect statistics job template that can be used to collect the statistics again for the column.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collector within this column.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    /**
     * Finds a collector by a collector name.
     * @param collectorName Collector name.
     * @return Metric model for a given collector or null when not found.
     */
    public StatisticsMetricModel getMetricByCollectorName(String collectorName) {
        for (StatisticsMetricModel metricModel : this.metrics) {
            if (Objects.equals(collectorName, metricModel.getCollector())) {
                return metricModel;
            }
        }

        return null;
    }

    public static class StatisticsResultsForColumnModelSampleFactory implements SampleValueFactory<StatisticsResultsForColumnModel> {
        @Override
        public StatisticsResultsForColumnModel createSample() {
            List<StatisticsMetricModel> statisticsMetricModels = SampleListUtility.generateList(StatisticsMetricModel.class, 5,
                    StatisticsMetricModel::getResult,
                    r -> Math.abs(new Random(Integer.toUnsignedLong((int)r)).nextInt() % 10_000),
                    StatisticsMetricModel::setResult,

                    StatisticsMetricModel::getCollectedAt,
                    r -> r.plusDays(1),
                    StatisticsMetricModel::setCollectedAt);
            return new StatisticsResultsForColumnModel() {{
                setConnectionName(SampleStringsRegistry.getConnectionName());
                setTable(PhysicalTableName.fromSchemaTableFilter(SampleStringsRegistry.getSchemaTableName()));
                setColumnName(SampleStringsRegistry.getColumnName());
                setMetrics(statisticsMetricModels);
            }};
        }
    }
}
