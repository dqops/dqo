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
package com.dqops.rest.models.metadata;

import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.data.statistics.models.StatisticsResultsForColumnModel;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Collection;

/**
 * Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnStatisticsModel", description = "Column model that returns the basic fields from a column specification with the additional data statistics.")
public class ColumnStatisticsModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Physical table name including the schema and table names.")
    private PhysicalTableName table;

    @JsonPropertyDescription("Column name.")
    private String columnName;

    @JsonPropertyDescription("Column hash that identifies the column using a unique hash code.")
    private Long columnHash;

    @JsonPropertyDescription("Disables all data quality checks on the column. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("True when the column has any checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredChecks;

    @JsonPropertyDescription("Column data type that was retrieved when the table metadata was imported.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ColumnTypeSnapshotSpec typeSnapshot;

    @JsonPropertyDescription("List of collected column statistics.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<StatisticsMetricModel> statistics;

    /**
     * Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors for this column.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors for this column")
    private StatisticsCollectorSearchFilters collectColumnStatisticsJobTemplate;

    /**
     * Boolean flag that decides if the current user can collect statistics.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can collect statistics.")
    private boolean canCollectStatistics;

    /**
     * Creates a column profile model from a column specification for a requested profile.
     * This model is used on a profiler summary screen.
     * @param physicalTableName Physical table name.
     * @param columnName        Column name.
     * @param columnSpec        Source column specification.
     * @param statisticsResultsForColumn List of column metrics.
     * @param canRunStatisticsJob The current user can collect statistics.
     * @return Column statistics model.
     */
    public static ColumnStatisticsModel fromColumnSpecificationAndStatistic(String connectionName,
                                                                            PhysicalTableName physicalTableName,
                                                                            String columnName,
                                                                            ColumnSpec columnSpec,
                                                                            StatisticsResultsForColumnModel statisticsResultsForColumn,
                                                                            boolean canRunStatisticsJob) {
        return new ColumnStatisticsModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setDisabled(columnSpec.isDisabled());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
            setCanCollectStatistics(canRunStatisticsJob);
            setStatistics(statisticsResultsForColumn != null ? statisticsResultsForColumn.getMetrics() : null);
            setCollectColumnStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(physicalTableName.toTableSearchFilter());
                getColumnNames().add(columnName);
                setEnabled(true);
            }});
        }};
    }

    public static class ColumnStatisticsModelSampleFactory implements SampleValueFactory<ColumnStatisticsModel> {
        @Override
        public ColumnStatisticsModel createSample() {
            return ColumnStatisticsModel.fromColumnSpecificationAndStatistic(
                    SampleStringsRegistry.getConnectionName(),
                    PhysicalTableName.fromSchemaTableFilter(SampleStringsRegistry.getSchemaTableName()),
                    SampleStringsRegistry.getColumnName(),
                     new ColumnSpec.ColumnSpecSampleFactory().createSample(),
                    new StatisticsResultsForColumnModel.StatisticsResultsForColumnModelSampleFactory().createSample(),
                    true
            );
        }
    }
}
