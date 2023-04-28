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
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableOwnerSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TimestampColumnsSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.parquet.Strings;

/**
 * Table basic model returned by the rest api that is limited only to the basic fields, excluding nested nodes.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableBasicModel", description = "Basic table model with a subset of parameters, excluding all nested objects.")
public class TableBasicModel {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * Table hash that identifies the table using a unique hash code.
     */
    @JsonPropertyDescription("Table hash that identifies the table using a unique hash code.")
    private Long tableHash;

    /**
     * Physical table details (a physical schema name and a physical table name).
     */
    @JsonPropertyDescription("Physical table details (a physical schema name and a physical table name).")
    private PhysicalTableName target;

    /**
     * Disables all data quality checks on the table. Data quality checks will not be executed.
     */
    @JsonPropertyDescription("Disables all data quality checks on the table. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    /**
     * Stage name.
     */
    @JsonPropertyDescription("Stage name.")
    private String stage;

    /**
     * SQL WHERE clause added to the sensor queries.
     */
    @JsonPropertyDescription("SQL WHERE clause added to the sensor queries.")
    private String filter;

    /**
     * Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.
     */
    @JsonPropertyDescription("Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer priority;

    /**
     * Table owner information like the data steward name or the business application name."
     */
    @JsonPropertyDescription("Table owner information like the data steward name or the business application name.")
    private TableOwnerSpec owner;

    /**
     * True when the table has any checks configured.
     */
    @JsonPropertyDescription("True when the table has any checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredChecks;

    /**
     * True when the table has any profiling checks configured.
     */
    @JsonPropertyDescription("True when the table has any profiling checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredProfilingChecks;

    /**
     * True when the table has any recurring checks configured.
     */
    @JsonPropertyDescription("True when the table has any recurring checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredRecurringChecks;

    /**
     * True when the table has any partition checks configured.
     */
    @JsonPropertyDescription("True when the table has any partition checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredPartitionChecks;

    /**
     * True when the table has any partition checks configured, but the partitionByColumn is not set, so the partition checks will fail when started.
     */
    @JsonPropertyDescription("True when the table has missing configuration of the \"partition_by_column\" column, making any partition checks fail when executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean partitioningConfigurationMissing;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this table.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run profiling checks within this table.")
    private CheckSearchFilters runProfilingChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run recurring checks within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run recurring checks within this table.")
    private CheckSearchFilters runRecurringChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run partition partitioned checks within this table.")
    private CheckSearchFilters runPartitionChecksJobTemplate;

    /**
     * Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors within this table.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Creates a basic table model from a table specification by cherry-picking relevant fields.
     * This model is used for the table list screen and it has even less fields.
     * @param connectionName Connection name to store in the model.
     * @param tableSpec      Source table specification.
     * @return Basic table model.
     */
    public static TableBasicModel fromTableSpecificationForListEntry(String connectionName, TableSpec tableSpec) {
        return new TableBasicModel() {{
            setConnectionName(connectionName);
            setTableHash(tableSpec.getHierarchyId() != null ? tableSpec.getHierarchyId().hashCode64() : null);
            setTarget(tableSpec.getPhysicalTableName());
            setDisabled(tableSpec.isDisabled());
            setPartitioningConfigurationMissing(tableSpec.getTimestampColumns() == null ||
                    Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getPartitionByColumn()));
            setHasAnyConfiguredChecks(tableSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(tableSpec.hasAnyChecksConfigured(CheckType.PROFILING));
            setHasAnyConfiguredRecurringChecks(tableSpec.hasAnyChecksConfigured(CheckType.RECURRING));
            setHasAnyConfiguredPartitionChecks(tableSpec.hasAnyChecksConfigured(CheckType.PARTITIONED));
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.PROFILING);
                setEnabled(true);
            }});
            setRunRecurringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.RECURRING);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.PARTITIONED);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setEnabled(true);
            }});
        }};
    }

    /**
     * Creates a basic table model from a table specification by cherry-picking relevant fields.
     * @param connectionName Connection name to store in the model.
     * @param tableSpec      Source table specification.
     * @return Basic table model.
     */
    public static TableBasicModel fromTableSpecification(String connectionName, TableSpec tableSpec) {
        return new TableBasicModel() {{
            setConnectionName(connectionName);
            setTableHash(tableSpec.getHierarchyId() != null ? tableSpec.getHierarchyId().hashCode64() : null);
            setTarget(tableSpec.getPhysicalTableName());
            setDisabled(tableSpec.isDisabled());
            setStage(tableSpec.getStage());
            setFilter(tableSpec.getFilter());
            setPriority(tableSpec.getPriority());
            setOwner(tableSpec.getOwner());
            setPartitioningConfigurationMissing(tableSpec.getTimestampColumns() == null ||
                    Strings.isNullOrEmpty(tableSpec.getTimestampColumns().getPartitionByColumn()));
            setHasAnyConfiguredChecks(tableSpec.hasAnyChecksConfigured());
            setHasAnyConfiguredProfilingChecks(tableSpec.hasAnyChecksConfigured(CheckType.PROFILING));
            setHasAnyConfiguredRecurringChecks(tableSpec.hasAnyChecksConfigured(CheckType.RECURRING));
            setHasAnyConfiguredPartitionChecks(tableSpec.hasAnyChecksConfigured(CheckType.PARTITIONED));
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.PROFILING);
                setEnabled(true);
            }});
            setRunRecurringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.RECURRING);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());
                setCheckType(CheckType.PARTITIONED);
                setEnabled(true);
            }});
            setDataCleanJobTemplate(new DeleteStoredDataQueueJobParameters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getPhysicalTableName().toTableSearchFilter());

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
     * Updates a table specification by copying all fields. The {@see TableSpec#target field is not updated}.
     * @param targetTableSpec Target table specification to update.
     */
    public void copyToTableSpecification(TableSpec targetTableSpec) {
        targetTableSpec.setDisabled(this.isDisabled());
        targetTableSpec.setStage(this.getStage());
        targetTableSpec.setFilter(this.getFilter());
        targetTableSpec.setPriority(this.getPriority());
        targetTableSpec.setOwner(this.getOwner());
    }
}
