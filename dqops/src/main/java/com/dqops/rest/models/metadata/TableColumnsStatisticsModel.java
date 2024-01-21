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

import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.docs.generators.SampleListUtility;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableColumnsStatisticsModel", description = "Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.")
public class TableColumnsStatisticsModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Physical table name including the schema and table names.")
    private PhysicalTableName table;

    @JsonPropertyDescription("List of collected column level statistics for all columns.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<ColumnStatisticsModel> columnStatistics;

    /**
     * Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors for all columns on this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors for all columns on this table.")
    private StatisticsCollectorSearchFilters collectColumnStatisticsJobTemplate;

    /**
     * Boolean flag that decides if the current user can collect statistics.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can collect statistics.")
    private boolean canCollectStatistics;

    public static class TableColumnsStatisticsModelSampleFactory implements SampleValueFactory<TableColumnsStatisticsModel> {
        @Override
        public TableColumnsStatisticsModel createSample() {
            List<ColumnStatisticsModel> columnStatisticsModelList = SampleListUtility.generateList(
                    ColumnStatisticsModel.class, 2,

                    ColumnStatisticsModel::getColumnName,
                    (Function<String, String>) SampleListUtility.HelperMutators.ITERATE_STRING.getMutator(),
                    ColumnStatisticsModel::setColumnName,

                    ColumnStatisticsModel::getStatistics,
                    s -> {
                        // TODO: Modify sample statistics.
                        return s;
                    },
                    ColumnStatisticsModel::setStatistics
            );
            return new TableColumnsStatisticsModel() {{
                setConnectionName(SampleStringsRegistry.getConnectionName());
                setTable(PhysicalTableName.fromSchemaTableFilter(SampleStringsRegistry.getSchemaTableName()));
                setColumnStatistics(columnStatisticsModelList);
                setCollectColumnStatisticsJobTemplate(new StatisticsCollectorSearchFilters.StatisticsCollectorSearchFiltersSampleFactory().createSample());
                setCanCollectStatistics(true);
            }};
        }
    }
}
