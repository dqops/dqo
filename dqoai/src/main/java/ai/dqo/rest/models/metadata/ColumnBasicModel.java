/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.rest.models.metadata;

import ai.dqo.checks.CheckType;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ColumnTypeSnapshotSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
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

    @JsonPropertyDescription("True when the column has any recurring checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredRecurringChecks;

    @JsonPropertyDescription("True when the column has any time period checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredTimePeriodChecks;

    @JsonPropertyDescription("Column data type that was retrieved when the table metadata was imported.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ColumnTypeSnapshotSpec typeSnapshot;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this column.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run profiling checks within this column.")
    private CheckSearchFilters runProfilingChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run recurring checks within this column.")
    private CheckSearchFilters runRecurringChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run time period partitioned checks within this column.")
    private CheckSearchFilters runTimePeriodChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collector within this column.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Creates a basic column model from a column specification by cherry-picking relevant fields.
     * This model is used for the column list screen and it has even less fields.
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param columnName        Column name.
     * @param columnSpec        Source column specification.
     * @return Basic column model.
     */
    public static ColumnBasicModel fromColumnSpecificationForListEntry(String connectionName,
                                                                       PhysicalTableName physicalTableName,
                                                                       String columnName,
                                                                       ColumnSpec columnSpec) {
        return new ColumnBasicModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setDisabled(columnSpec.isDisabled());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(columnSpec.hasAnyChecksConfigured(CheckType.ADHOC));
            setHasAnyConfiguredRecurringChecks(columnSpec.hasAnyChecksConfigured(CheckType.CHECKPOINT));
            setHasAnyConfiguredTimePeriodChecks(columnSpec.hasAnyChecksConfigured(CheckType.PARTITIONED));
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
                setCheckType(CheckType.ADHOC);
                setEnabled(true);
            }});
            setRunRecurringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setCheckType(CheckType.CHECKPOINT);
                setEnabled(true);
            }});
            setRunTimePeriodChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setCheckType(CheckType.PARTITIONED);
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

                setDeleteProfilingResults(true);
                setDeleteErrors(true);
                setDeleteRuleResults(true);
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
     * @return Basic column model.
     */
    public static ColumnBasicModel fromColumnSpecification(String connectionName,
                                                           PhysicalTableName physicalTableName,
                                                           String columnName,
                                                           ColumnSpec columnSpec) {
        return new ColumnBasicModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setDisabled(columnSpec.isDisabled());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(columnSpec.hasAnyChecksConfigured(CheckType.ADHOC));
            setHasAnyConfiguredRecurringChecks(columnSpec.hasAnyChecksConfigured(CheckType.CHECKPOINT));
            setHasAnyConfiguredTimePeriodChecks(columnSpec.hasAnyChecksConfigured(CheckType.PARTITIONED));
        }};
    }

    /**
     * Updates a column specification by copying all fields.
     * @param targetColumnSpec Target column specification to update.
     */
    public void copyToColumnSpecification(ColumnSpec targetColumnSpec) {
        targetColumnSpec.setDisabled(this.isDisabled());
        targetColumnSpec.setTypeSnapshot(this.getTypeSnapshot());
    }
}
