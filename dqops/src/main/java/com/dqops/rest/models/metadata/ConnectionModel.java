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
import com.dqops.connectors.clickhouse.ClickHouseParametersSpec;
import com.dqops.connectors.databricks.DatabricksParametersSpec;
import com.dqops.connectors.db2.Db2ParametersSpec;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.hana.HanaParametersSpec;
import com.dqops.connectors.mariadb.MariaDbParametersSpec;
import com.dqops.connectors.mysql.MysqlParametersSpec;
import com.dqops.connectors.oracle.OracleParametersSpec;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.connectors.presto.PrestoParametersSpec;
import com.dqops.connectors.questdb.QuestDbParametersSpec;
import com.dqops.connectors.redshift.RedshiftParametersSpec;
import com.dqops.connectors.snowflake.SnowflakeParametersSpec;
import com.dqops.connectors.spark.SparkParametersSpec;
import com.dqops.connectors.sqlserver.SqlServerParametersSpec;
import com.dqops.connectors.teradata.TeradataParametersSpec;
import com.dqops.connectors.trino.TrinoParametersSpec;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;

/**
 * Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ConnectionModel", description = "Connection model with a subset of parameters, excluding all nested objects.")
public class ConnectionModel {
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
    private Integer parallelJobsLimit;

    @JsonPropertyDescription("Limits running scheduled checks (started by a CRON job scheduler) to run only on a named DQOps instance. When this field is empty, data quality checks are run on all DQOps instances. Set a DQOps instance name to run checks on a named instance only. The default name of the DQOps Cloud SaaS instance is \"cloud\".")
    private String scheduleOnInstance;

    /**
     * Database provider type (required). Accepts: bigquery, snowflake, etc.
     */
    @JsonPropertyDescription("Database provider type (required). Accepts: bigquery, snowflake, etc.")
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
     * DuckDB connection parameters.
     */
    @JsonPropertyDescription("DuckDB connection parameters.")
    private DuckdbParametersSpec duckdb;

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
     * Presto connection parameters.
     */
    @JsonPropertyDescription("Presto connection parameters.")
    private PrestoParametersSpec presto;

    /**
     * Trino connection parameters.
     */
    @JsonPropertyDescription("Trino connection parameters.")
    private TrinoParametersSpec trino;

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
     * Spark connection parameters.
     */
    @JsonPropertyDescription("Spark connection parameters.")
    private SparkParametersSpec spark;

    /**
     * Databricks connection parameters.
     */
    @JsonPropertyDescription("Databricks connection parameters.")
    private DatabricksParametersSpec databricks;

    /**
     * HANA connection parameters.
     */
    @JsonPropertyDescription("HANA connection parameters.")
    private HanaParametersSpec hana;

    /**
     * DB2 connection parameters.
     */
    @JsonPropertyDescription("DB2 connection parameters.")
    private Db2ParametersSpec db2;

    /**
     * MariaDB connection parameters.
     */
    @JsonPropertyDescription("MariaDB connection parameters.")
    private MariaDbParametersSpec mariadb;

    /**
     * ClickHouse connection parameters.
     */
    @JsonPropertyDescription("ClickHouse connection parameters.")
    private ClickHouseParametersSpec clickhouse;

    /**
     * QuestDB connection parameters.
     */
    @JsonPropertyDescription("QuestDB connection parameters.")
    private QuestDbParametersSpec questdb;

    /**
     * Teradata connection parameters.
     */
    @JsonPropertyDescription("Teradata connection parameters.")
    private TeradataParametersSpec teradata;

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
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this connection.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run monitoring checks within this connection.")
    private CheckSearchFilters runMonitoringChecksJobTemplate;

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
     * A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.
     */
    @JsonPropertyDescription("A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.")
    private Map<String, String> advancedProperties;

    /**
     * Boolean flag that decides if the current user can update or delete the connection to the data source.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete the connection to the data source.")
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
     * Optional parsing error that was captured when parsing the YAML file.
     * This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.
     */
    @JsonPropertyDescription("Optional parsing error that was captured when parsing the YAML file. " +
            "This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.")
    private String yamlParsingError;

    /**
     * Creates a basic connection model from a connection specification by cherry-picking relevant fields.
     * @param connectionName Connection name to store in the model.
     * @param connectionSpec Source connection specification.
     * @param isEditor       The current user has the editor permission.
     * @param isOperator     The current user has the operator permission.
     * @return Basic connection model.
     */
    public static ConnectionModel fromConnectionSpecification(
            String connectionName,
            ConnectionSpec connectionSpec,
            boolean isEditor,
            boolean isOperator) {
        return new ConnectionModel() {{
            setConnectionName(connectionName);
            setParallelJobsLimit(connectionSpec.getParallelJobsLimit());
            setScheduleOnInstance(connectionSpec.getScheduleOnInstance());
            setConnectionHash(connectionSpec.getHierarchyId() != null ? connectionSpec.getHierarchyId().hashCode64() : null);
            setProviderType(connectionSpec.getProviderType());
            setBigquery(connectionSpec.getBigquery());
            setSnowflake(connectionSpec.getSnowflake());
            setPostgresql(connectionSpec.getPostgresql());
            setDuckdb(connectionSpec.getDuckdb());
            setRedshift(connectionSpec.getRedshift());
            setSqlserver(connectionSpec.getSqlserver());
            setMysql(connectionSpec.getMysql());
            setOracle(connectionSpec.getOracle());
            setSpark(connectionSpec.getSpark());
            setPresto(connectionSpec.getPresto());
            setTrino(connectionSpec.getTrino());
            setDatabricks(connectionSpec.getDatabricks());
            setHana(connectionSpec.getHana());
            setDb2(connectionSpec.getDb2());
            setMariadb(connectionSpec.getMariadb());
            setClickhouse(connectionSpec.getClickhouse());
            setQuestdb(connectionSpec.getQuestdb());
            setTeradata(connectionSpec.getTeradata());
            setCanEdit(isEditor);
            setCanRunChecks(isOperator);
            setCanCollectStatistics(isOperator);
            setCanDeleteData(isOperator);
            setYamlParsingError(connectionSpec.getYamlParsingError());
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setEnabled(true);
            }});
            setRunProfilingChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setCheckType(CheckType.profiling);
                setEnabled(true);
            }});
            setRunMonitoringChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setCheckType(CheckType.monitoring);
                setEnabled(true);
            }});
            setRunPartitionChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnection(connectionName);
                setCheckType(CheckType.partitioned);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnection(connectionName);
                setEnabled(true);
            }});
            setDataCleanJobTemplate(new DeleteStoredDataQueueJobParameters()
            {{
                setConnection(connectionName);

                setDateStart(null);
                setDateEnd(null);

                setDeleteStatistics(true);
                setDeleteErrors(true);
                setDeleteCheckResults(true);
                setDeleteSensorReadouts(true);
                setDeleteErrorSamples(true);
                setDeleteIncidents(true);
            }});
            setAdvancedProperties(connectionSpec.getAdvancedProperties());
        }};
    }

    /**
     * Updates a connection specification by copying all fields.
     * @param targetConnectionSpec Target connection specification to update.
     */
    public void copyToConnectionSpecification(ConnectionSpec targetConnectionSpec) {
        targetConnectionSpec.setProviderType(this.getProviderType());
        targetConnectionSpec.setParallelJobsLimit(this.parallelJobsLimit);
        targetConnectionSpec.setScheduleOnInstance(this.scheduleOnInstance);
        targetConnectionSpec.setBigquery(this.getBigquery());
        targetConnectionSpec.setSnowflake(this.getSnowflake());
        targetConnectionSpec.setPostgresql(this.getPostgresql());
        targetConnectionSpec.setDuckdb(this.getDuckdb());
        targetConnectionSpec.setRedshift(this.getRedshift());
        targetConnectionSpec.setSqlserver(this.getSqlserver());
        targetConnectionSpec.setMysql(this.getMysql());
        targetConnectionSpec.setOracle(this.getOracle());
        targetConnectionSpec.setSpark(this.getSpark());
        targetConnectionSpec.setPresto(this.getPresto());
        targetConnectionSpec.setTrino(this.getTrino());
        targetConnectionSpec.setDatabricks(this.getDatabricks());
        targetConnectionSpec.setHana(this.getHana());
        targetConnectionSpec.setDb2(this.getDb2());
        targetConnectionSpec.setMariadb(this.getMariadb());
        targetConnectionSpec.setClickhouse(this.getClickhouse());
        targetConnectionSpec.setQuestdb(this.getQuestdb());
        targetConnectionSpec.setTeradata(this.getTeradata());
        targetConnectionSpec.setAdvancedProperties(this.getAdvancedProperties());
    }

    public static class ConnectionModelSampleFactory implements SampleValueFactory<ConnectionModel> {
        @Override
        public ConnectionModel createSample() {
            return fromConnectionSpecification(
                    SampleStringsRegistry.getConnectionName(),
                    new ConnectionSpec.ConnectionSpecSampleFactory().createSample(),
                    false,
                    true);
        }
    }
}
