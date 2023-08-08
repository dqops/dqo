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
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.bigquery.BigQueryParametersSpec;
import com.dqops.connectors.mysql.MysqlParametersSpec;
import com.dqops.connectors.oracle.OracleParametersSpec;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.connectors.redshift.RedshiftParametersSpec;
import com.dqops.connectors.snowflake.SnowflakeParametersSpec;
import com.dqops.connectors.sqlserver.SqlServerParametersSpec;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ConnectionBasicModel", description = "Basic connection model with a subset of parameters, excluding all nested objects.")
public class ConnectionBasicModel {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * Connection hash that identifies the connection using a unique hash code.
     */
    @JsonPropertyDescription("Connection hash that identifies the connection using a unique hash code.")
    private Long connectionHash;

    /**
     * The concurrency limit for the maximum number of parallel SQL queries executed on this connection.
     */
    @JsonPropertyDescription("The concurrency limit for the maximum number of parallel SQL queries executed on this connection.")
    private Integer parallelRunsLimit;

    /**
     * Database provider type (required). Accepts: bigquery, snowflake.
     */
    @JsonPropertyDescription("Database provider type (required). Accepts: bigquery, snowflake.")
    private ProviderType providerType;

    /**
     * BigQuery connection parameters. Specify parameters in the bigquery section.
     */
    @JsonPropertyDescription("BigQuery connection parameters. Specify parameters in the bigquery section.")
    private BigQueryParametersSpec bigquery;

    /**
     * Snowflake connection parameters.
     */
    @JsonPropertyDescription("Snowflake connection parameters.")
    private SnowflakeParametersSpec snowflake;

    /**
     * PostgreSQL connection parameters.
     */
    @JsonPropertyDescription("PostgreSQL connection parameters.")
    private PostgresqlParametersSpec postgresql;

    /**
     * Redshift connection parameters.
     */
    @JsonPropertyDescription("Redshift connection parameters.")
    private RedshiftParametersSpec redshift;

    /**
     * SqlServer connection parameters.
     */
    @JsonPropertyDescription("SqlServer connection parameters.")
    private SqlServerParametersSpec sqlserver;

    /**
     * MySQL connection parameters.
     */
    @JsonPropertyDescription("MySQL connection parameters.")
    private MysqlParametersSpec mysql;

    /**
     * Oracle connection parameters.
     */
    @JsonPropertyDescription("Oracle connection parameters.")
    private OracleParametersSpec oracle;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this connection.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this connection.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this connection.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run profiling checks within this connection.")
    private CheckSearchFilters runProfilingChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run recurring checks within this connection.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run recurring checks within this connection.")
    private CheckSearchFilters runRecurringChecksJobTemplate;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this connection.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run partition partitioned checks within this connection.")
    private CheckSearchFilters runPartitionChecksJobTemplate;

    /**
     * Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this connection.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors within this connection.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this connection.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this connection.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Creates a basic connection model from a connection specification by cherry-picking relevant fields.
     * @param connectionName Connection name to store in the model.
     * @param connectionSpec Source connection specification.
     * @return Basic connection model.
     */
    public static ConnectionBasicModel fromConnectionSpecification(String connectionName, ConnectionSpec connectionSpec) {
        return new ConnectionBasicModel() {{
            setConnectionName(connectionName);
            setParallelRunsLimit(connectionSpec.getParallelRunsLimit());
            setConnectionHash(connectionSpec.getHierarchyId() != null ? connectionSpec.getHierarchyId().hashCode64() : null);
            setProviderType(connectionSpec.getProviderType());
            setBigquery(connectionSpec.getBigquery());
            setSnowflake(connectionSpec.getSnowflake());
            setPostgresql(connectionSpec.getPostgresql());
            setRedshift(connectionSpec.getRedshift());
            setSqlserver(connectionSpec.getSqlserver());
            setMysql(connectionSpec.getMysql());
            setOracle(connectionSpec.getOracle());
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setCheckType(CheckType.profiling);
                setEnabled(true);
            }});
            setRunRecurringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setCheckType(CheckType.recurring);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setCheckType(CheckType.partitioned);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnectionName(connectionName);
                setEnabled(true);
            }});
            setDataCleanJobTemplate(new DeleteStoredDataQueueJobParameters()
            {{
                setConnectionName(connectionName);

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
     * Updates a connection specification by copying all fields.
     * @param targetConnectionSpec Target connection specification to update.
     */
    public void copyToConnectionSpecification(ConnectionSpec targetConnectionSpec) {
        targetConnectionSpec.setProviderType(this.getProviderType());
        targetConnectionSpec.setParallelRunsLimit(this.parallelRunsLimit);
        targetConnectionSpec.setBigquery(this.getBigquery());
        targetConnectionSpec.setSnowflake(this.getSnowflake());
        targetConnectionSpec.setPostgresql(this.getPostgresql());
        targetConnectionSpec.setRedshift(this.getRedshift());
        targetConnectionSpec.setSqlserver(this.getSqlserver());
        targetConnectionSpec.setMysql(this.getMysql());
        targetConnectionSpec.setOracle(this.getOracle());
    }
}
