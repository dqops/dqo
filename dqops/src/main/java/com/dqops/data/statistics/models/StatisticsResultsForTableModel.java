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

import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class StatisticsResultsForTableModel {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name")
    private String connectionName;

    /**
     * Physical table name including the schema and table names.
     */
    @JsonPropertyDescription("Physical table name including the schema and table names")
    private PhysicalTableName table;

    /**
     * List of statistics metrics.
     */
    @JsonPropertyDescription("List of statistics metrics")
    private List<StatisticsMetricModel> metrics = new ArrayList<>();

    /**
     * Statistics metrics of columns.
     */
    @JsonPropertyDescription("Statistics metrics of columns")
    private Map<String, StatisticsResultsForColumnModel> columns = new LinkedHashMap<>();

    /**
     * Checks if the statistics are missing. Returns true when no current statistics were found.
     * @return True when no statistics are present, false when the results are present.
     */
    @JsonIgnore
    public boolean isEmpty() {
        return this.metrics.isEmpty() && this.columns.isEmpty();
    }
}
