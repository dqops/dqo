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

import com.dqops.checks.CheckType;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Basic column model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnBasicModel", description = "Basic column model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.")
public class ColumnBasicModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Physical table name including the schema and table names.")
    private PhysicalTableName table;

    @JsonPropertyDescription("Column name.")
    private String columnName;

    @JsonPropertyDescription("SQL expression.")
    private String sqlExpression;

    @JsonPropertyDescription("Column hash that identifies the column using a unique hash code.")
    private Long columnHash;

    @JsonPropertyDescription("Disables all data quality checks on the column. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("True when the column has any checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredChecks;

    @JsonPropertyDescription("True when the column has any profiling checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredProfilingChecks;

    @JsonPropertyDescription("True when the column has any monitoring checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredMonitoringChecks;

    @JsonPropertyDescription("True when the column has any partition checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredPartitionChecks;

    @JsonPropertyDescription("Column data type that was retrieved when the table metadata was imported.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ColumnTypeSnapshotSpec typeSnapshot;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this column.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run profiling checks within this column.")
    private CheckSearchFilters runProfilingChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run monitoring checks within this column.")
    private CheckSearchFilters runMonitoringChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run partition partitioned checks within this column.")
    private CheckSearchFilters runPartitionChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collector within this column.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Boolean flag that decides if the current user can update or delete the column.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete the column.")
    private boolean canEdit;

    /**
     * Boolean flag that decides if the current user can collect statistics.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can collect statistics.")
    private boolean canCollectStatistics;

    /**
     * Boolean flag that decides if the current user can run checks.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can run checks.")
    private boolean canRunChecks;

    /**
     * Boolean flag that decides if the current user can delete data (results).
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can delete data (results).")
    private boolean canDeleteData;

    /**
     * Creates a basic column model from a column specification by cherry-picking relevant fields.
     * This model is used for the column list screen and it has even less fields.
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param columnName        Column name.
     * @param columnSpec        Source column specification.
     * @param isEditor          The current user has the editor permission.
     * @param isOperator        The current user has the operator permission.
     * @return Basic column model.
     */
    public static ColumnBasicModel fromColumnSpecificationForListEntry(String connectionName,
                                                                       PhysicalTableName physicalTableName,
                                                                       String columnName,
                                                                       ColumnSpec columnSpec,
                                                                       boolean isEditor,
                                                                       boolean isOperator) {
        return new ColumnBasicModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setSqlExpression(columnSpec.getSqlExpression());
            setDisabled(columnSpec.isDisabled());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(columnSpec.hasAnyChecksConfigured(CheckType.profiling));
            setHasAnyConfiguredMonitoringChecks(columnSpec.hasAnyChecksConfigured(CheckType.monitoring));
            setHasAnyConfiguredPartitionChecks(columnSpec.hasAnyChecksConfigured(CheckType.partitioned));
            setCanEdit(isEditor);
            setCanRunChecks(isOperator);
            setCanCollectStatistics(isOperator);
            setCanDeleteData(isOperator);

            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setCheckType(CheckType.profiling);
                setEnabled(true);
            }});
            setRunMonitoringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setCheckType(CheckType.monitoring);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setCheckType(CheckType.partitioned);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setEnabled(true);
            }});
            setDataCleanJobTemplate(new DeleteStoredDataQueueJobParameters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);

                setDateStart(null);
                setDateEnd(null);

                setDeleteStatistics(true);
                setDeleteErrors(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
            }});
        }};
    }

    /**
     * Creates a basic column model from a column specification by cherry-picking relevant fields.
     * @param connectionName    Connection name to store in the model.
     * @param physicalTableName Physical table name.
     * @param columnName        Column name.
     * @param columnSpec        Source column specification.
     * @param isEditor          The current user has the editor permission.
     * @param isOperator        The current user has the operator permission.
     * @return Basic column model.
     */
    public static ColumnBasicModel fromColumnSpecification(String connectionName,
                                                           PhysicalTableName physicalTableName,
                                                           String columnName,
                                                           ColumnSpec columnSpec,
                                                           boolean isEditor,
                                                           boolean isOperator) {
        return new ColumnBasicModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setSqlExpression(columnSpec.getSqlExpression());
            setDisabled(columnSpec.isDisabled());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(columnSpec.hasAnyChecksConfigured(CheckType.profiling));
            setHasAnyConfiguredMonitoringChecks(columnSpec.hasAnyChecksConfigured(CheckType.monitoring));
            setHasAnyConfiguredPartitionChecks(columnSpec.hasAnyChecksConfigured(CheckType.partitioned));
            setCanEdit(isEditor);
            setCanRunChecks(isOperator);
            setCanCollectStatistics(isOperator);
            setCanDeleteData(isOperator);
        }};
    }

    /**
     * Updates a column specification by copying all fields.
     * @param targetColumnSpec Target column specification to update.
     */
    public void copyToColumnSpecification(ColumnSpec targetColumnSpec) {
        targetColumnSpec.setSqlExpression(this.getSqlExpression());
        targetColumnSpec.setDisabled(this.isDisabled());
        targetColumnSpec.setTypeSnapshot(this.getTypeSnapshot());
    }
}
