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
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Schema model that is returned by the REST API. Describes a single unique schema name.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SchemaModel", description = "Schema model")
public class SchemaModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Schema name.")
    private String schemaName;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this schema.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run profiling checks within this schema.")
    private CheckSearchFilters runProfilingChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run monitoring checks within this schema.")
    private CheckSearchFilters runMonitoringChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run partition partitioned checks within this schema.")
    private CheckSearchFilters runPartitionChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors within this schema.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    @JsonPropertyDescription("Job parameters for the import tables job that will import all tables from this schema.")
    private ImportTablesQueueJobParameters importTableJobParameters;

    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this schema.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Boolean flag that decides if the current user can update or delete the schema.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete the schema.")
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
     * Creates a schema model from connection and schema names.
     * @param connectionName Connection name to store in the model.
     * @param schemaName     Schema name.
     * @param isEditor       The current user has the editor permission.
     * @param isOperator     The current user has the operator permission.
     * @return Schema model.
     */
    public static SchemaModel fromSchemaNameStrings(
            String connectionName,
            String schemaName,
            boolean isEditor,
            boolean isOperator) {
        return new SchemaModel()
        {{
            setConnectionName(connectionName);
            setSchemaName(schemaName);
            setCanEdit(isEditor);
            setCanRunChecks(isOperator);
            setCanCollectStatistics(isOperator);
            setCanDeleteData(isOperator);
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(schemaName + ".*");
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(schemaName + ".*");
                setCheckType(CheckType.profiling);
                setEnabled(true);
            }});
            setRunMonitoringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(schemaName + ".*");
                setCheckType(CheckType.monitoring);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(schemaName + ".*");
                setCheckType(CheckType.partitioned);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnection(connectionName);
                setFullTableName(schemaName + ".*");
                setEnabled(true);
            }});
            setImportTableJobParameters(new ImportTablesQueueJobParameters()
            {{
                setConnectionName(connectionName);
                setSchemaName(schemaName);
            }});
            setDataCleanJobTemplate(new DeleteStoredDataQueueJobParameters()
            {{
                setConnection(connectionName);
                setFullTableName(schemaName + ".*");

                setDateStart(null);
                setDateEnd(null);

                setDeleteStatistics(true);
                setDeleteErrors(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
            }});
        }};
    }
}
