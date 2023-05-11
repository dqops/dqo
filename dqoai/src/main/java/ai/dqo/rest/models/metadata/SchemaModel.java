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
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;
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

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run recurring checks within this schema.")
    private CheckSearchFilters runRecurringChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run partition partitioned checks within this schema.")
    private CheckSearchFilters runPartitionChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors within this schema.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    @JsonPropertyDescription("Job parameters for the import tables job that will import all tables from this schema.")
    private ImportTablesQueueJobParameters importTableJobParameters;

    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this schema.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Creates a schema model from connection and schema names.
     * @param connectionName Connection name to store in the model.
     * @param schemaName     Schema name.
     * @return Schema model.
     */
    public static SchemaModel fromSchemaNameStrings(String connectionName, String schemaName) {
        return new SchemaModel()
        {{
            setConnectionName(connectionName);
            setSchemaName(schemaName);
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(schemaName + ".*");
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(schemaName + ".*");
                setCheckType(CheckType.PROFILING);
                setEnabled(true);
            }});
            setRunRecurringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(schemaName + ".*");
                setCheckType(CheckType.RECURRING);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(schemaName + ".*");
                setCheckType(CheckType.PARTITIONED);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(schemaName + ".*");
                setEnabled(true);
            }});
            setImportTableJobParameters(new ImportTablesQueueJobParameters()
            {{
                setConnectionName(connectionName);
                setSchemaName(schemaName);
            }});
            setDataCleanJobTemplate(new DeleteStoredDataQueueJobParameters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(schemaName + ".*");

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
