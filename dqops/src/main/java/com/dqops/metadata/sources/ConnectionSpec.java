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
package com.dqops.metadata.sources;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.bigquery.BigQueryParametersSpec;
import com.dqops.connectors.databricks.DatabricksParametersSpec;
import com.dqops.connectors.mysql.MysqlParametersSpec;
import com.dqops.connectors.oracle.OracleParametersSpec;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.connectors.presto.PrestoParametersSpec;
import com.dqops.connectors.redshift.RedshiftParametersSpec;
import com.dqops.connectors.snowflake.SnowflakeParametersSpec;
import com.dqops.connectors.spark.SparkParametersSpec;
import com.dqops.connectors.sqlserver.SqlServerParametersSpec;
import com.dqops.connectors.trino.TrinoParametersSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.id.*;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
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
            put("redshift", o -> o.redshift);
            put("sqlserver", o -> o.sqlserver);
            put("presto", o -> o.presto);
            put("trino", o -> o.trino);
            put("mysql", o -> o.mysql);
            put("oracle", o -> o.oracle);
            put("spark", o -> o.spark);
            put("databricks", o -> o.databricks);
            put("labels", o -> o.labels);
            put("schedules", o -> o.schedules);
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
    private DefaultSchedulesSpec schedules;

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
     * Returns the configuration of schedules for each type of check.
     * @return Configuration of schedules for each type of checks.
     */
    public DefaultSchedulesSpec getSchedules() {
        return schedules;
    }

    /**
     * Sets the configuration of schedules for running each type of checks.
     * @param schedules Configuration of schedules.
     */
    public void setSchedules(DefaultSchedulesSpec schedules) {
        setDirtyIf(!Objects.equals(this.schedules, schedules));
        this.schedules = schedules;
        propagateHierarchyIdToField(schedules, "schedules");
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
            if (cloned.incidentGrouping != null) {
                cloned.incidentGrouping = cloned.incidentGrouping.expandAndTrim(secretValueProvider, secretValueLookupContext);
            }
            cloned.comments = null;
            cloned.schedules = null;
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
