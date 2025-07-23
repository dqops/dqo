/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
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
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.id.*;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import picocli.CommandLine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Data source (connection) specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class ConnectionSpec extends AbstractSpec implements InvalidYamlStatusHolder {
    private static final ChildHierarchyNodeFieldMapImpl<ConnectionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("comments", o -> o.comments);
			put("default_grouping_configuration", o -> o.defaultGroupingConfiguration);

			put("bigquery", o -> o.bigquery);
			put("snowflake", o -> o.snowflake);
            put("postgresql", o -> o.postgresql);
            put("duckdb", o -> o.duckdb);
            put("redshift", o -> o.redshift);
            put("sqlserver", o -> o.sqlserver);
            put("presto", o -> o.presto);
            put("trino", o -> o.trino);
            put("mysql", o -> o.mysql);
            put("oracle", o -> o.oracle);
            put("spark", o -> o.spark);
            put("databricks", o -> o.databricks);
            put("hana", o -> o.hana);
            put("db2", o -> o.db2);
            put("mariadb", o -> o.mariadb);
            put("clickhouse", o -> o.clickhouse);
            put("questdb", o -> o.questdb);
            put("teradata", o -> o.teradata);

            put("labels", o -> o.labels);
            put("schedules", o -> o.schedules);
            put("auto_import_tables", o -> o.autoImportTables);
            put("incident_grouping", o -> o.incidentGrouping);
        }
    };

    @JsonPropertyDescription("Database provider type (required).")
    private ProviderType providerType;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("BigQuery connection parameters. Specify parameters in the bigquery section.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private BigQueryParametersSpec bigquery;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Snowflake connection parameters. Specify parameters in the snowflake section or set the url (which is the Snowflake JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private SnowflakeParametersSpec snowflake;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("PostgreSQL connection parameters. Specify parameters in the postgresql section or set the url (which is the PostgreSQL JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PostgresqlParametersSpec postgresql;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("DuckDB connection parameters. Specify parameters in the duckdb section or set the url (which is the DuckDB JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DuckdbParametersSpec duckdb;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Redshift connection parameters. Specify parameters in the redshift section or set the url (which is the Redshift JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RedshiftParametersSpec redshift;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("SQL Server connection parameters. Specify parameters in the sqlserver section or set the url (which is the SQL Server JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private SqlServerParametersSpec sqlserver;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Presto connection parameters. Specify parameters in the presto section or set the url (which is the Presto JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PrestoParametersSpec presto;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Trino connection parameters. Specify parameters in the trino section or set the url (which is the Trino JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TrinoParametersSpec trino;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("MySQL connection parameters. Specify parameters in the mysql section or set the url (which is the MySQL JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MysqlParametersSpec mysql;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Oracle connection parameters. Specify parameters in the oracle section or set the url (which is the Oracle JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private OracleParametersSpec oracle;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Spark connection parameters. Specify parameters in the spark section or set the url (which is the Spark JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private SparkParametersSpec spark;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Databricks connection parameters. Specify parameters in the databricks section or set the url (which is the Databricks JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DatabricksParametersSpec databricks;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("HANA connection parameters. Specify parameters in the hana section or set the url (which is the HANA JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private HanaParametersSpec hana;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("DB2 connection parameters. Specify parameters in the db2 section or set the url (which is the DB2 JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private Db2ParametersSpec db2;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("MariaDB connection parameters. Specify parameters in the mariadb section or set the url (which is the MariaDB JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MariaDbParametersSpec mariadb;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("ClickHouse connection parameters. Specify parameters in the clickhouse section or set the url (which is the ClickHouse JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ClickHouseParametersSpec clickhouse;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("QuestDB connection parameters. Specify parameters in the questdb section or set the url (which is the QuestDB JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private QuestDbParametersSpec questdb;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Teradata connection parameters. Specify parameters in the teradata section or set the url (which is the Teradata JDBC url).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TeradataParametersSpec teradata;

    @JsonPropertyDescription("The concurrency limit for the maximum number of parallel SQL queries executed on this connection.")
    private Integer parallelJobsLimit;

    @JsonPropertyDescription("Default data grouping configuration for all tables. The configuration may be overridden on table, column and check level. " +
            "Data groupings are configured in two cases: " +
            "(1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning. a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). " +
            "(2) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). ")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DataGroupingConfigurationSpec defaultGroupingConfiguration;

    @JsonPropertyDescription("Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CronSchedulesSpec schedules;

    @JsonPropertyDescription("Configuration of CRON schedule used to automatically import new tables in regular intervals.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AutoImportTablesSpec autoImportTables;

    @JsonPropertyDescription("Limits running scheduled checks (started by a CRON job scheduler) to run only on a named DQOps instance. When this field is empty, data quality checks are run on all DQOps instances. Set a DQOps instance name to run checks on a named instance only. The default name of the DQOps Cloud SaaS instance is \"cloud\".")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String scheduleOnInstance;

    @JsonPropertyDescription("Configuration of data quality incident grouping. Configures how failed data quality checks are grouped into data quality incidents.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ConnectionIncidentGroupingSpec incidentGrouping = new ConnectionIncidentGroupingSpec();

    @JsonPropertyDescription("Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CommentsListSpec comments;

    @JsonPropertyDescription("Custom labels that were assigned to the connection. Labels are used for searching for tables when filtered data quality checks are executed.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private LabelSetSpec labels;

    @JsonPropertyDescription("A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> advancedProperties = new HashMap<>();

    @JsonIgnore
    private String yamlParsingError;

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }

    /**
     * Default constructor.
     */
    public ConnectionSpec() {
    }

    /**
     * Constructor for creating a connection for a selected provider.
     * @param providerType Provider type.
     */
    public ConnectionSpec(ProviderType providerType) {
        this.providerType = providerType;
    }

    /**
     * Returns a database provider type to be used.
     * @return Connection provider type.
     */
    public ProviderType getProviderType() {
        return providerType;
    }

    /**
     * Sets a database provider type.
     * @param providerType Database provider type.
     */
    public void setProviderType(ProviderType providerType) {
		setDirtyIf(!Objects.equals(this.providerType, providerType));
        this.providerType = providerType;
    }

    /**
     * Big query connection settings.
     * @return Big query connection settings.
     */
    public BigQueryParametersSpec getBigquery() {
        return bigquery;
    }

    /**
     * Sets the bigquery connection parameters.
     * @param bigquery Bigquery connection parameters.
     */
    public void setBigquery(BigQueryParametersSpec bigquery) {
		setDirtyIf(!Objects.equals(this.bigquery, bigquery));
        this.bigquery = bigquery;
		propagateHierarchyIdToField(bigquery, "bigquery");
    }

    /**
     * Returns snowflake connection parameters.
     * @return Snowflake connection parameters.
     */
    public SnowflakeParametersSpec getSnowflake() {
        return snowflake;
    }

    /**
     * Sets the snowflake connection parameters.
     * @param snowflake Snowflake connection parameters.
     */
    public void setSnowflake(SnowflakeParametersSpec snowflake) {
		setDirtyIf(!Objects.equals(this.snowflake, snowflake));
        this.snowflake = snowflake;
		propagateHierarchyIdToField(snowflake, "snowflake");
    }

    /**
     * Returns the connection parameters for PostgreSQL.
     * @return PostgreSQL connection parameters.
     */
    public PostgresqlParametersSpec getPostgresql() {
        return postgresql;
    }

    /**
     * Sets the PostgreSQL connection parameters.
     * @param postgresql New PostgreSQL connection parameters.
     */
    public void setPostgresql(PostgresqlParametersSpec postgresql) {
        setDirtyIf(!Objects.equals(this.postgresql, postgresql));
        this.postgresql = postgresql;
        propagateHierarchyIdToField(postgresql, "postgresql");
    }

    /**
     * Returns the connection parameters for DuckDB.
     * @return DuckDB connection parameters.
     */
    public DuckdbParametersSpec getDuckdb() {
        return duckdb;
    }

    /**
     * Sets the DuckDB connection parameters.
     * @param duckdb New DuckDB connection parameters.
     */
    public void setDuckdb(DuckdbParametersSpec duckdb) {
        setDirtyIf(!Objects.equals(this.duckdb, duckdb));
        this.duckdb = duckdb;
        propagateHierarchyIdToField(duckdb, "duckdb");
    }

    /**
     * Returns the connection parameters for Redshift.
     * @return Redshift connection parameters.
     */
    public RedshiftParametersSpec getRedshift() {
        return redshift;
    }

    /**
     * Sets the Redshift connection parameters.
     * @param redshift New Redshift connection parameters.
     */
    public void setRedshift(RedshiftParametersSpec redshift) {
        setDirtyIf(!Objects.equals(this.redshift, redshift));
        this.redshift = redshift;
        propagateHierarchyIdToField(redshift, "redshift");
    }

    /**
     * Returns the connection parameters for SQL Server.
     * @return SQL Server connection parameters.
     */
    public SqlServerParametersSpec getSqlserver() {
        return sqlserver;
    }

    /**
     * Sets the SQL Server connection parameters.
     * @param sqlserver New SQL Server connection parameters.
     */
    public void setSqlserver(SqlServerParametersSpec sqlserver) {
        setDirtyIf(!Objects.equals(this.sqlserver, sqlserver));
        this.sqlserver = sqlserver;
        propagateHierarchyIdToField(sqlserver, "sqlserver");
    }

    /**
     * Returns the connection parameters for Presto.
     * @return Presto connection parameters.
     */
    public PrestoParametersSpec getPresto() {
        return presto;
    }

    /**
     * Sets the Presto connection parameters.
     * @param presto New Presto connection parameters.
     */
    public void setPresto(PrestoParametersSpec presto) {
        setDirtyIf(!Objects.equals(this.presto, presto));
        this.presto = presto;
        propagateHierarchyIdToField(presto, "presto");
    }

    /**
     * Returns the connection parameters for Trino.
     * @return Trino connection parameters.
     */
    public TrinoParametersSpec getTrino() {
        return trino;
    }

    /**
     * Sets the Trino connection parameters.
     * @param trino New Trino connection parameters.
     */
    public void setTrino(TrinoParametersSpec trino) {
        setDirtyIf(!Objects.equals(this.trino, trino));
        this.trino = trino;
        propagateHierarchyIdToField(trino, "trino");
    }

    /**
     * Returns the connection parameters for MySQL.
     * @return MySQL connection parameters.
     */
    public MysqlParametersSpec getMysql() {
        return mysql;
    }

    /**
     * Sets the MySQL connection parameters.
     * @param mysql New MySQL connection parameters.
     */
    public void setMysql(MysqlParametersSpec mysql) {
        setDirtyIf(!Objects.equals(this.mysql, mysql));
        this.mysql = mysql;
        propagateHierarchyIdToField(mysql, "mysql");
    }

    /**
     * Returns the connection parameters for Oracle.
     * @return Oracle connection parameters.
     */
    public OracleParametersSpec getOracle() {
        return oracle;
    }

    /**
     * Sets the Oracle connection parameters.
     * @param oracle New Oracle connection parameters.
     */
    public void setOracle(OracleParametersSpec oracle) {
        setDirtyIf(!Objects.equals(this.oracle, oracle));
        this.oracle = oracle;
        propagateHierarchyIdToField(oracle, "oracle");
    }

    /**
     * Returns the connection parameters for Spark.
     * @return Spark connection parameters.
     */
    public SparkParametersSpec getSpark() {
        return spark;
    }

    /**
     * Sets the Spark connection parameters.
     * @param spark New Spark connection parameters.
     */
    public void setSpark(SparkParametersSpec spark) {
        setDirtyIf(!Objects.equals(this.spark, spark));
        this.spark = spark;
        propagateHierarchyIdToField(spark, "spark");
    }

    /**
     * Returns the connection parameters for Databricks.
     * @return Databricks connection parameters.
     */
    public DatabricksParametersSpec getDatabricks() {
        return databricks;
    }

    /**
     * Sets the Databricks connection parameters.
     * @param databricks New Databricks connection parameters.
     */
    public void setDatabricks(DatabricksParametersSpec databricks) {
        setDirtyIf(!Objects.equals(this.databricks, databricks));
        this.databricks = databricks;
        propagateHierarchyIdToField(databricks, "databricks");
    }

    /**
     * Returns the connection parameters for SAP HANA.
     * @return SAP HANA connection parameters.
     */
    public HanaParametersSpec getHana() {
        return hana;
    }

    /**
     * Sets the SAP HANA connection parameters.
     * @param hana New SAP HANA connection parameters.
     */
    public void setHana(HanaParametersSpec hana) {
        setDirtyIf(!Objects.equals(this.hana, hana));
        this.hana = hana;
        propagateHierarchyIdToField(hana, "hana");
    }

    /**
     * Returns the connection parameters for IBM DB2
     * @return IBM DB2 connection parameters.
     */
    public Db2ParametersSpec getDb2() {
        return db2;
    }

    /**
     * Sets the IBM DB2 connection parameters.
     * @param db2 New IBM DB2 connection parameters.
     */
    public void setDb2(Db2ParametersSpec db2) {
        setDirtyIf(!Objects.equals(this.db2, db2));
        this.db2 = db2;
        propagateHierarchyIdToField(db2, "db2");
    }

    /**
     * Returns the connection parameters for MariaDB.
     * @return MariaDB connection parameters.
     */
    public MariaDbParametersSpec getMariadb() {
        return mariadb;
    }

    /**
     * Sets the MariaDB connection parameters.
     * @param mariadb New MariaDB connection parameters.
     */
    public void setMariadb(MariaDbParametersSpec mariadb) {
        setDirtyIf(!Objects.equals(this.mariadb, mariadb));
        this.mariadb = mariadb;
        propagateHierarchyIdToField(mariadb, "mariadb");
    }

    /**
     * Returns the connection parameters for ClickHouse.
     * @return ClickHouse connection parameters.
     */
    public ClickHouseParametersSpec getClickhouse() {
        return clickhouse;
    }

    /**
     * Sets the ClickHouse connection parameters.
     * @param clickhouse New ClickHouse connection parameters.
     */
    public void setClickhouse(ClickHouseParametersSpec clickhouse) {
        setDirtyIf(!Objects.equals(this.clickhouse, clickhouse));
        this.clickhouse = clickhouse;
        propagateHierarchyIdToField(clickhouse, "clickhouse");
    }

    /**
     * Returns the connection parameters for QuestDB.
     * @return QuestDB connection parameters.
     */
    public QuestDbParametersSpec getQuestdb() {
        return questdb;
    }

    /**
     * Sets the QuestDB connection parameters.
     * @param questdb New QuestDB connection parameters.
     */
    public void setQuestdb(QuestDbParametersSpec questdb) {
        setDirtyIf(!Objects.equals(this.questdb, questdb));
        this.questdb = questdb;
        propagateHierarchyIdToField(questdb, "questdb");
    }

    /**
     * Returns the connection parameters for Teradata.
     * @return Teradata connection parameters.
     */
    public TeradataParametersSpec getTeradata() {
        return teradata;
    }

    /**
     * Sets the Teradata connection parameters.
     * @param teradata New Teradata connection parameters.
     */
    public void setTeradata(TeradataParametersSpec teradata) {
        setDirtyIf(!Objects.equals(this.teradata, teradata));
        this.teradata = teradata;
        propagateHierarchyIdToField(teradata, "teradata");
    }

    /**
     * Returns the configuration of schedules for each type of check.
     * @return Configuration of schedules for each type of checks.
     */
    public CronSchedulesSpec getSchedules() {
        return schedules;
    }

    /**
     * Sets the configuration of schedules for running each type of checks.
     * @param schedules Configuration of schedules.
     */
    public void setSchedules(CronSchedulesSpec schedules) {
        setDirtyIf(!Objects.equals(this.schedules, schedules));
        this.schedules = schedules;
        propagateHierarchyIdToField(schedules, "schedules");
    }

    /**
     * Returns the configuration of automatic table import that is performed by a CRON scheduler.
     * @return Automatic table import settings.
     */
    public AutoImportTablesSpec getAutoImportTables() {
        return autoImportTables;
    }

    /**
     * Sets the configuration of an automatic table import.
     * @param autoImportTables Configuration of an automatic table import.
     */
    public void setAutoImportTables(AutoImportTablesSpec autoImportTables) {
        setDirtyIf(!Objects.equals(this.autoImportTables, autoImportTables));
        this.autoImportTables = autoImportTables;
        propagateHierarchyIdToField(autoImportTables, "auto_import_tables");
    }

    /**
     * Returns the name of a named DQOps instance where checks from this connection are triggered by a CRON scheduler.
     * @return Instance name which schedules checks in this connection.
     */
    public String getScheduleOnInstance() {
        return scheduleOnInstance;
    }

    /**
     * Sets the name of a DQOps instance that will schedule checks.
     * @param scheduleOnInstance Instance name to schedule or null to run on any instance.
     */
    public void setScheduleOnInstance(String scheduleOnInstance) {
        setDirtyIf(!Objects.equals(this.scheduleOnInstance, scheduleOnInstance));
        this.scheduleOnInstance = scheduleOnInstance;
    }

    /**
     * Returns the limit of parallel data quality checks that could be started at the same time on the connection.
     * @return Concurrency limit (number of parallel jobs) that are executing checks or null when no limits are enforced.
     */
    public Integer getParallelJobsLimit() {
        return parallelJobsLimit;
    }

    /**
     * Sets the concurrency limit of the number of checks that can run in parallel on this connection.
     * @param parallelJobsLimit New concurrency limit or null when no limit is applied.
     */
    public void setParallelJobsLimit(Integer parallelJobsLimit) {
        this.setDirtyIf(!Objects.equals(this.parallelJobsLimit, parallelJobsLimit));
        this.parallelJobsLimit = parallelJobsLimit;
    }

    /**
     * Returns the configuration of grouping failed data quality checks into data quality incidents.
     * @return Grouping of failed data quality checks into incidents.
     */
    public ConnectionIncidentGroupingSpec getIncidentGrouping() {
        return incidentGrouping;
    }

    /**
     * Sets the configuration of data quality issued into incidents.
     * @param incidentGrouping New configuration of data quality issue grouping into incidents.
     */
    public void setIncidentGrouping(ConnectionIncidentGroupingSpec incidentGrouping) {
        setDirtyIf(!Objects.equals(this.incidentGrouping, incidentGrouping));
        this.incidentGrouping = incidentGrouping;
        propagateHierarchyIdToField(incidentGrouping, "incident_grouping");
    }

    /**
     * Returns a collection of comments for this connection.
     * @return List of comments (or null).
     */
    public CommentsListSpec getComments() {
        return comments;
    }

    /**
     * Sets a list of comments for this connection.
     * @param comments Comments list.
     */
    public void setComments(CommentsListSpec comments) {
		setDirtyIf(!Objects.equals(this.comments, comments));
        this.comments = comments;
		propagateHierarchyIdToField(comments, "comments");
    }

    /**
     * Returns the default data grouping configuration for all tables on the connection.
     * @return Default data grouping configuration.
     */
    public DataGroupingConfigurationSpec getDefaultGroupingConfiguration() {
        return defaultGroupingConfiguration;
    }

    /**
     * Returns the default data grouping configuration for all tables on this connection.
     * @param defaultGroupingConfiguration Data grouping configuration.
     */
    public void setDefaultGroupingConfiguration(DataGroupingConfigurationSpec defaultGroupingConfiguration) {
		setDirtyIf(!Objects.equals(this.defaultGroupingConfiguration, defaultGroupingConfiguration));
        this.defaultGroupingConfiguration = defaultGroupingConfiguration;
		propagateHierarchyIdToField(defaultGroupingConfiguration, "default_grouping_configuration");
    }

    /**
     * List of labels assigned to a table. Labels are used for targeting the execution of tests.
     * @return Labels collection.
     */
    public LabelSetSpec getLabels() {
        return labels;
    }

    /**
     * Changes a list of labels.
     * @param labels Labels collection.
     */
    public void setLabels(LabelSetSpec labels) {
        setDirtyIf(!Objects.equals(this.labels, labels));
        this.labels = labels;
        propagateHierarchyIdToField(labels, "labels");
    }

    /**
     * Returns a key/value map of advanced properties.
     * @return Key/value dictionary of advanced properties.
     */
    public Map<String, String> getAdvancedProperties() {
        return advancedProperties;
    }

    /**
     * Sets a dictionary of advanced properties.
     * @param advancedProperties Key/value dictionary with extra parameters.
     */
    public void setAdvancedProperties(Map<String, String> advancedProperties) {
        setDirtyIf(!Objects.equals(this.advancedProperties, advancedProperties));
        this.advancedProperties = advancedProperties != null ? Collections.unmodifiableMap(advancedProperties) : null;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a deep copy of this object.
     */
    @Override
    public ConnectionSpec deepClone() {
        ConnectionSpec cloned = (ConnectionSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Trimmed and expanded version of this object.
     */
    public ConnectionSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext secretValueLookupContext) {
        try {
            ConnectionSpec cloned = (ConnectionSpec) super.clone();
            if (cloned.defaultGroupingConfiguration != null) {
                cloned.defaultGroupingConfiguration = cloned.defaultGroupingConfiguration.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.bigquery != null) {
                cloned.bigquery = cloned.bigquery.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.snowflake != null) {
                cloned.snowflake = cloned.snowflake.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.postgresql != null) {
                cloned.postgresql = cloned.postgresql.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.duckdb != null) {
                cloned.duckdb = cloned.duckdb.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.redshift != null) {
                cloned.redshift = cloned.redshift.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.mysql != null) {
                cloned.mysql = cloned.mysql.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.presto != null) {
                cloned.presto = cloned.presto.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.trino != null) {
                cloned.trino = cloned.trino.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.oracle != null) {
                cloned.oracle = cloned.oracle.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.sqlserver != null) {
                cloned.sqlserver = cloned.sqlserver.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.spark != null) {
                cloned.spark = cloned.spark.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.databricks != null) {
                cloned.databricks = cloned.databricks.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.hana != null) {
                cloned.hana = cloned.hana.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.db2 != null) {
                cloned.db2 = cloned.db2.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.mariadb != null) {
                cloned.mariadb = cloned.mariadb.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.clickhouse != null) {
                cloned.clickhouse = cloned.clickhouse.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.questdb != null) {
                cloned.questdb = cloned.questdb.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.teradata != null) {
                cloned.teradata = cloned.teradata.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            if (cloned.incidentGrouping != null) {
                cloned.incidentGrouping = cloned.incidentGrouping.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            cloned.comments = null;
            cloned.schedules = null;
            cloned.advancedProperties = null;
            cloned.autoImportTables = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Creates a trimmed version of the object without unwanted properties.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @return Trimmed version of this object.
     */
    public ConnectionSpec trim() {
        try {
            ConnectionSpec cloned = (ConnectionSpec) super.clone();
            cloned.defaultGroupingConfiguration = null;
            cloned.comments = null;
            cloned.schedules = null;
            cloned.incidentGrouping = null;
            cloned.advancedProperties = null;
            cloned.autoImportTables = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Retrieves the connection name from the hierarchy.
     * @return Connection name or null for a standalone connection spec object.
     */
    @JsonIgnore
    public String getConnectionName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.get(hierarchyId.size() - 2).toString();
    }

    /**
     * Returns the provider specific configuration object. Takes the name of the provider and returns the child object of that name (postgresql, snowflake, bigquery).
     * @return Provider specific configuration.
     */
    @JsonIgnore
    public ConnectionProviderSpecificParameters getProviderSpecificConfiguration() {
        if (this.providerType == null) {
            throw new DqoRuntimeException("Missing provider type in the connection");
        }

        HierarchyNode providerConfigChild = this.getChild(this.providerType.name());
        ConnectionProviderSpecificParameters providerConfiguration =
                (ConnectionProviderSpecificParameters) providerConfigChild;

        return providerConfiguration;
    }

    public static class ConnectionSpecSampleFactory implements SampleValueFactory<ConnectionSpec> {
        @Override
        public ConnectionSpec createSample() {
            return new ConnectionSpec() {{
                setProviderType(ProviderType.postgresql);
                setPostgresql(new PostgresqlParametersSpec.PostgresqlParametersSpecSampleFactory().createSample());
                setParallelJobsLimit(4);
            }};
        }
    }
}
