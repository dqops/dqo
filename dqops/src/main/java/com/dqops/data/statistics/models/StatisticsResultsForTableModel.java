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
