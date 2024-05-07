---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## ConnectionYaml
Connection definition for a data source connection that is covered by data quality checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>|DQOps YAML schema version|*string*| |dqo/v1| |
|<span class="no-wrap-code ">`kind`</span>|File type|*enum*|*source*<br/>*table*<br/>*sensor*<br/>*provider_sensor*<br/>*rule*<br/>*check*<br/>*settings*<br/>*file_index*<br/>*dashboards*<br/>*default_schedules*<br/>*default_checks*<br/>*default_table_checks*<br/>*default_column_checks*<br/>*default_notifications*<br/>|source| |
|<span class="no-wrap-code ">[`spec`](./ConnectionYaml.md#connectionspec)</span>|Connection specification object with the connection parameters to the data source|*[ConnectionSpec](./ConnectionYaml.md#connectionspec)*| | | |









___


## ConnectionSpec
Data source (connection) specification.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`provider_type`</span>|Database provider type (required).|*enum*|*bigquery*<br/>*databricks*<br/>*mysql*<br/>*oracle*<br/>*postgresql*<br/>*duckdb*<br/>*presto*<br/>*redshift*<br/>*snowflake*<br/>*spark*<br/>*sqlserver*<br/>*trino*<br/>| | |
|<span class="no-wrap-code ">[`bigquery`](./ConnectionYaml.md#bigqueryparametersspec)</span>|BigQuery connection parameters. Specify parameters in the bigquery section.|*[BigQueryParametersSpec](./ConnectionYaml.md#bigqueryparametersspec)*| | | |
|<span class="no-wrap-code ">[`snowflake`](./ConnectionYaml.md#snowflakeparametersspec)</span>|Snowflake connection parameters. Specify parameters in the snowflake section or set the url (which is the Snowflake JDBC url).|*[SnowflakeParametersSpec](./ConnectionYaml.md#snowflakeparametersspec)*| | | |
|<span class="no-wrap-code ">[`postgresql`](./ConnectionYaml.md#postgresqlparametersspec)</span>|PostgreSQL connection parameters. Specify parameters in the postgresql section or set the url (which is the PostgreSQL JDBC url).|*[PostgresqlParametersSpec](./ConnectionYaml.md#postgresqlparametersspec)*| | | |
|<span class="no-wrap-code ">[`duckdb`](./ConnectionYaml.md#duckdbparametersspec)</span>|DuckDB connection parameters. Specify parameters in the duckdb section or set the url (which is the DuckDB JDBC url).|*[DuckdbParametersSpec](./ConnectionYaml.md#duckdbparametersspec)*| | | |
|<span class="no-wrap-code ">[`redshift`](./ConnectionYaml.md#redshiftparametersspec)</span>|Redshift connection parameters. Specify parameters in the redshift section or set the url (which is the Redshift JDBC url).|*[RedshiftParametersSpec](./ConnectionYaml.md#redshiftparametersspec)*| | | |
|<span class="no-wrap-code ">[`sqlserver`](./ConnectionYaml.md#sqlserverparametersspec)</span>|SQL Server connection parameters. Specify parameters in the sqlserver section or set the url (which is the SQL Server JDBC url).|*[SqlServerParametersSpec](./ConnectionYaml.md#sqlserverparametersspec)*| | | |
|<span class="no-wrap-code ">[`presto`](./ConnectionYaml.md#prestoparametersspec)</span>|Presto connection parameters. Specify parameters in the presto section or set the url (which is the Presto JDBC url).|*[PrestoParametersSpec](./ConnectionYaml.md#prestoparametersspec)*| | | |
|<span class="no-wrap-code ">[`trino`](./ConnectionYaml.md#trinoparametersspec)</span>|Trino connection parameters. Specify parameters in the trino section or set the url (which is the Trino JDBC url).|*[TrinoParametersSpec](./ConnectionYaml.md#trinoparametersspec)*| | | |
|<span class="no-wrap-code ">[`mysql`](./ConnectionYaml.md#mysqlparametersspec)</span>|MySQL connection parameters. Specify parameters in the mysql section or set the url (which is the MySQL JDBC url).|*[MysqlParametersSpec](./ConnectionYaml.md#mysqlparametersspec)*| | | |
|<span class="no-wrap-code ">[`oracle`](./ConnectionYaml.md#oracleparametersspec)</span>|Oracle connection parameters. Specify parameters in the oracle section or set the url (which is the Oracle JDBC url).|*[OracleParametersSpec](./ConnectionYaml.md#oracleparametersspec)*| | | |
|<span class="no-wrap-code ">[`spark`](./ConnectionYaml.md#sparkparametersspec)</span>|Spark connection parameters. Specify parameters in the spark section or set the url (which is the Spark JDBC url).|*[SparkParametersSpec](./ConnectionYaml.md#sparkparametersspec)*| | | |
|<span class="no-wrap-code ">[`databricks`](./ConnectionYaml.md#databricksparametersspec)</span>|Databricks connection parameters. Specify parameters in the databricks section or set the url (which is the Databricks JDBC url).|*[DatabricksParametersSpec](./ConnectionYaml.md#databricksparametersspec)*| | | |
|<span class="no-wrap-code ">`parallel_jobs_limit`</span>|The concurrency limit for the maximum number of parallel SQL queries executed on this connection.|*integer*| | | |
|<span class="no-wrap-code ">[`default_grouping_configuration`](./ConnectionYaml.md#datagroupingconfigurationspec)</span>|Default data grouping configuration for all tables. The configuration may be overridden on table, column and check level. Data groupings are configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). |*[DataGroupingConfigurationSpec](./ConnectionYaml.md#datagroupingconfigurationspec)*| | | |
|<span class="no-wrap-code ">[`schedules`](./ConnectionYaml.md#defaultschedulesspec)</span>|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|*[DefaultSchedulesSpec](./ConnectionYaml.md#defaultschedulesspec)*| | | |
|<span class="no-wrap-code ">[`incident_grouping`](./ConnectionYaml.md#connectionincidentgroupingspec)</span>|Configuration of data quality incident grouping. Configures how failed data quality checks are grouped into data quality incidents.|*[ConnectionIncidentGroupingSpec](./ConnectionYaml.md#connectionincidentgroupingspec)*| | | |
|<span class="no-wrap-code ">[`comments`](./profiling/table-profiling-checks.md#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](./profiling/table-profiling-checks.md#commentslistspec)*| | | |
|<span class="no-wrap-code ">[`labels`](./ConnectionYaml.md#labelsetspec)</span>|Custom labels that were assigned to the connection. Labels are used for searching for tables when filtered data quality checks are executed.|*[LabelSetSpec](./ConnectionYaml.md#labelsetspec)*| | | |









___


## BigQueryParametersSpec
BigQuery connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`source_project_id`</span>|Source GCP project ID. This is the project that has datasets that will be imported.|*string*| | | |
|<span class="no-wrap-code ">`jobs_create_project`</span>|Configures the way how to select the project that will be used to start BigQuery jobs and will be used for billing. The user/service identified by the credentials must have bigquery.jobs.create permission in that project.|*enum*|*create_jobs_in_source_project*<br/>*create_jobs_in_default_project_from_credentials*<br/>*create_jobs_in_selected_billing_project_id*<br/>| | |
|<span class="no-wrap-code ">`billing_project_id`</span>|Billing GCP project ID. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.|*string*| | | |
|<span class="no-wrap-code ">`authentication_mode`</span>|Authentication mode to the Google Cloud.|*enum*|*google_application_credentials*<br/>*json_key_content*<br/>*json_key_path*<br/>| | |
|<span class="no-wrap-code ">`json_key_content`</span>|JSON key content. Use an environment variable that contains the content of the key as ${KEY_ENV} or a name of a secret in the GCP Secret Manager: ${sm://key-secret-name}. Requires the authentication-mode: json_key_content.|*string*| | | |
|<span class="no-wrap-code ">`json_key_path`</span>|A path to the JSON key file. Requires the authentication-mode: json_key_path.|*string*| | | |
|<span class="no-wrap-code ">`quota_project_id`</span>|Quota GCP project ID.|*string*| | | |









___


## SnowflakeParametersSpec
Snowflake connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`account`</span>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.. Supports also a ${SNOWFLAKE_ACCOUNT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`warehouse`</span>|Snowflake warehouse name. Supports also a ${SNOWFLAKE_WAREHOUSE} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`database`</span>|Snowflake database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|Snowflake user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|Snowflake database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`role`</span>|Snowflake role name. Supports also ${SNOWFLAKE_ROLE} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## PostgresqlParametersSpec
Postgresql connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|PostgreSQL host name. Supports also a ${POSTGRESQL_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|PostgreSQL port number. The default port is 5432. Supports also a ${POSTGRESQL_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`database`</span>|PostgreSQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|PostgreSQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|PostgreSQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`options`</span>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${POSTGRESQL_OPTIONS} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`sslmode`</span>|Sslmode PostgreSQL connection parameter. The default value is disabled.|*enum*|*disable*<br/>*allow*<br/>*prefer*<br/>*require*<br/>*verify-ca*<br/>*verify-full*<br/>| | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## DuckdbParametersSpec
DuckDB connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`read_mode`</span>|DuckDB read mode.|*enum*|*in_memory*<br/>*files*<br/>| | |
|<span class="no-wrap-code ">`files_format_type`</span>|Type of source files format for DuckDB.|*enum*|*csv*<br/>*json*<br/>*parquet*<br/>| | |
|<span class="no-wrap-code ">`database`</span>|DuckDB database name for in-memory read mode. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |
|<span class="no-wrap-code ">[`csv`](./ConnectionYaml.md#csvfileformatspec)</span>|Csv file format specification.|*[CsvFileFormatSpec](./ConnectionYaml.md#csvfileformatspec)*| | | |
|<span class="no-wrap-code ">[`json`](./ConnectionYaml.md#jsonfileformatspec)</span>|Json file format specification.|*[JsonFileFormatSpec](./ConnectionYaml.md#jsonfileformatspec)*| | | |
|<span class="no-wrap-code ">[`parquet`](./ConnectionYaml.md#parquetfileformatspec)</span>|Parquet file format specification.|*[ParquetFileFormatSpec](./ConnectionYaml.md#parquetfileformatspec)*| | | |
|<span class="no-wrap-code ">`directories`</span>|Virtual schema name to directory mappings. The path must be an absolute path.|*Dict[string, string]*| | | |
|<span class="no-wrap-code ">`storage_type`</span>|The storage type.|*enum*|*local*<br/>*s3*<br/>*azure*<br/>| | |
|<span class="no-wrap-code ">`aws_authentication_mode`</span>|The authentication mode for AWS. Supports also a ${DUCKDB_AWS_AUTHENTICATION_MODE} configuration with a custom environment variable.|*enum*|*iam*<br/>*default_credentials*<br/>| | |
|<span class="no-wrap-code ">`user`</span>|DuckDB user name for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|DuckDB password for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`region`</span>|The region for the storage credentials. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |









___


## CsvFileFormatSpec
Csv file format specification for querying data in the csv format files.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`all_varchar`</span>|Option to skip type detection for CSV parsing and assume all columns to be of type VARCHAR.|*boolean*| | | |
|<span class="no-wrap-code ">`allow_quoted_nulls`</span>|Option to allow the conversion of quoted values to NULL values.|*boolean*| | | |
|<span class="no-wrap-code ">`auto_detect`</span>|Enables auto detection of CSV parameters.|*boolean*| | | |
|<span class="no-wrap-code ">`compression`</span>|The compression type for the file. By default this will be detected automatically from the file extension (e.g., t.csv.gz will use gzip, t.csv will use none). Options are none, gzip, zstd.|*enum*|*auto*<br/>*none*<br/>*gzip*<br/>*zstd*<br/>| | |
|<span class="no-wrap-code ">`dateformat`</span>|Specifies the date format to use when parsing dates.|*string*| | | |
|<span class="no-wrap-code ">`decimal_separator`</span>|The decimal separator of numbers.|*string*| | | |
|<span class="no-wrap-code ">`delim`</span>|Specifies the string that separates columns within each row (line) of the file.|*string*| | | |
|<span class="no-wrap-code ">`escape`</span>|Specifies the string that should appear before a data character sequence that matches the quote value.|*string*| | | |
|<span class="no-wrap-code ">`filename`</span>|Whether or not an extra filename column should be included in the result.|*boolean*| | | |
|<span class="no-wrap-code ">`header`</span>|Specifies that the file contains a header line with the names of each column in the file.|*boolean*| | | |
|<span class="no-wrap-code ">`hive_partitioning`</span>|Whether or not to interpret the path as a hive partitioned path.|*boolean*| | | |
|<span class="no-wrap-code ">`ignore_errors`</span>|Option to ignore any parsing errors encountered - and instead ignore rows with errors.|*boolean*| | | |
|<span class="no-wrap-code ">`new_line`</span>|Set the new line character(s) in the file. Options are &#x27;\r&#x27;,&#x27;\n&#x27;, or &#x27;\r\n&#x27;.|*enum*|*cr*<br/>*lf*<br/>*crlf*<br/>| | |
|<span class="no-wrap-code ">`quote`</span>|Specifies the quoting string to be used when a data value is quoted.|*string*| | | |
|<span class="no-wrap-code ">`sample_size`</span>|The number of sample rows for auto detection of parameters.|*long*| | | |
|<span class="no-wrap-code ">`skip`</span>|The number of lines at the top of the file to skip.|*long*| | | |
|<span class="no-wrap-code ">`timestampformat`</span>|Specifies the date format to use when parsing timestamps.|*string*| | | |









___


## JsonFileFormatSpec
Json file format specification for querying data in the json format files.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`auto_detect`</span>|Whether to auto-detect detect the names of the keys and data types of the values automatically.|*boolean*| | | |
|<span class="no-wrap-code ">`compression`</span>|The compression type for the file. By default this will be detected automatically from the file extension (e.g., t.json.gz will use gzip, t.json will use none). Options are &#x27;none&#x27;, &#x27;gzip&#x27;, &#x27;zstd&#x27;, and &#x27;auto&#x27;.|*enum*|*auto*<br/>*none*<br/>*gzip*<br/>*zstd*<br/>| | |
|<span class="no-wrap-code ">`convert_strings_to_integers`</span>|Whether strings representing integer values should be converted to a numerical type.|*boolean*| | | |
|<span class="no-wrap-code ">`dateformat`</span>|Specifies the date format to use when parsing dates.|*string*| | | |
|<span class="no-wrap-code ">`filename`</span>|Whether or not an extra filename column should be included in the result.|*boolean*| | | |
|<span class="no-wrap-code ">`format`</span>|Json format. Can be one of [&#x27;auto&#x27;, &#x27;unstructured&#x27;, &#x27;newline_delimited&#x27;, &#x27;array&#x27;].|*enum*|*auto*<br/>*unstructured*<br/>*newline_delimited*<br/>*array*<br/>| | |
|<span class="no-wrap-code ">`hive_partitioning`</span>|Whether or not to interpret the path as a hive partitioned path.|*boolean*| | | |
|<span class="no-wrap-code ">`ignore_errors`</span>|Whether to ignore parse errors (only possible when format is &#x27;newline_delimited&#x27;).|*boolean*| | | |
|<span class="no-wrap-code ">`maximum_depth`</span>|Maximum nesting depth to which the automatic schema detection detects types. Set to -1 to fully detect nested JSON types.|*long*| | | |
|<span class="no-wrap-code ">`maximum_object_size`</span>|The maximum size of a JSON object (in bytes).|*long*| | | |
|<span class="no-wrap-code ">`records`</span>|Can be one of [&#x27;auto&#x27;, &#x27;true&#x27;, &#x27;false&#x27;].|*enum*|*auto*<br/>*true*<br/>*false*<br/>| | |
|<span class="no-wrap-code ">`sample_size`</span>|The number of sample rows for auto detection of parameters.|*long*| | | |
|<span class="no-wrap-code ">`timestampformat`</span>|Specifies the date format to use when parsing timestamps.|*string*| | | |









___


## ParquetFileFormatSpec
Parquet file format specification for querying data in the parquet format files.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`binary_as_string`</span>|Parquet files generated by legacy writers do not correctly set the UTF8 flag for strings, causing string columns to be loaded as BLOB instead. Set this to true to load binary columns as strings.|*boolean*| | | |
|<span class="no-wrap-code ">`filename`</span>|Whether or not an extra filename column should be included in the result.|*boolean*| | | |
|<span class="no-wrap-code ">`file_row_number`</span>|Whether or not to include the file_row_number column.|*boolean*| | | |
|<span class="no-wrap-code ">`hive_partitioning`</span>|Whether or not to interpret the path as a hive partitioned path.|*boolean*| | | |
|<span class="no-wrap-code ">`union_by_name`</span>|Whether the columns of multiple schemas should be unified by name, rather than by position.|*boolean*| | | |









___


## RedshiftParametersSpec
Redshift connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|Redshift host name. Supports also a ${REDSHIFT_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|Redshift port number. The default port is 5432. Supports also a ${REDSHIFT_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`database`</span>|Redshift database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`redshift_authentication_mode`</span>|The authentication mode for AWS. Supports also a ${REDSHIFT_AUTHENTICATION_MODE} configuration with a custom environment variable.|*enum*|*iam*<br/>*default_credentials*<br/>*user_password*<br/>| | |
|<span class="no-wrap-code ">`user`</span>|Redshift user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|Redshift database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## SqlServerParametersSpec
Microsoft SQL Server connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|SQL Server host name. Supports also a ${SQLSERVER_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|SQL Server port number. The default port is 1433. Supports also a ${SQLSERVER_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`database`</span>|SQL Server database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|SQL Server user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|SQL Server database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`disable_encryption`</span>|Disable SSL encryption parameter. The default value is false. You may need to disable encryption when SQL Server is started in Docker.|*boolean*| | | |
|<span class="no-wrap-code ">`authentication_mode`</span>|Authenticaiton mode for the SQL Server. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*enum*|*sql_password*<br/>*active_directory_password*<br/>*active_directory_service_principal*<br/>*default_credential*<br/>| | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## PrestoParametersSpec
Presto connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|Presto host name. Supports also a ${PRESTO_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|Presto port number. The default port is 8080. Supports also a ${PRESTO_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`database`</span>|Presto database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|Presto user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|Presto database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## TrinoParametersSpec
Trino connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`trino_engine_type`</span>|Trino engine type. Supports also a ${TRINO_ENGINE} configuration with a custom environment variable.|*enum*|*trino*<br/>*athena*<br/>| | |
|<span class="no-wrap-code ">`host`</span>|Trino host name. Supports also a ${TRINO_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|Trino port number. The default port is 8080. Supports also a ${TRINO_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|Trino user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|Trino database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`aws_authentication_mode`</span>|The authentication mode for AWS Athena. Supports also a ${ATHENA_AWS_AUTHENTICATION_MODE} configuration with a custom environment variable.|*enum*|*iam*<br/>*default_credentials*<br/>| | |
|<span class="no-wrap-code ">`athena_region`</span>|The AWS Region where queries will be run. Supports also a ${ATHENA_REGION} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`catalog`</span>|The catalog that contains the databases and the tables that will be accessed with the driver. Supports also a ${TRINO_CATALOG} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`athena_work_group`</span>|The workgroup in which queries will run. Supports also a ${ATHENA_WORK_GROUP} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`athena_output_location`</span>|The location in Amazon S3 where query results will be stored. Supports also a ${ATHENA_OUTPUT_LOCATION} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## MysqlParametersSpec
MySql connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|MySQL host name. Supports also a ${MYSQL_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|MySQL port number. The default port is 3306. Supports also a ${MYSQL_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`database`</span>|MySQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|MySQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|MySQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`sslmode`</span>|SslMode MySQL connection parameter.|*enum*|*DISABLED*<br/>*PREFERRED*<br/>*REQUIRED*<br/>*VERIFY_CA*<br/>*VERIFY_IDENTITY*<br/>| | |
|<span class="no-wrap-code ">[`single_store_db_parameters_spec`](./ConnectionYaml.md#singlestoredbparametersspec)</span>|Single Store DB parameters spec.|*[SingleStoreDbParametersSpec](./ConnectionYaml.md#singlestoredbparametersspec)*| | | |
|<span class="no-wrap-code ">`mysql_engine_type`</span>|MySQL engine type. Supports also a ${MYSQL_ENGINE} configuration with a custom environment variable.|*enum*|*mysql*<br/>*singlestoredb*<br/>| | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## SingleStoreDbParametersSpec
Single Store DB connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`load_balancing_mode`</span>|SingleStoreDB Failover and Load-Balancing Modes for Single Store DB. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*enum*|*none*<br/>*sequential*<br/>*loadbalance*<br/>| | |
|<span class="no-wrap-code ">`host_descriptions`</span>|SingleStoreDB Host descriptions. Supports also a ${SINGLE_STORE_HOST_DESCRIPTIONS} configuration with a custom environment variable.|*List[string]*| | | |
|<span class="no-wrap-code ">`schema`</span>|SingleStoreDB database/schema name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`use_ssl`</span>|Force enables SSL/TLS on the connection. Supports also a ${SINGLE_STORE_USE_SSL} configuration with a custom environment variable.|*boolean*| | | |









___


## OracleParametersSpec
Oracle connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|Oracle host name. Supports also a ${ORACLE_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|Oracle port number. The default port is 1521. Supports also a ${ORACLE_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`database`</span>|Oracle database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|Oracle user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|Oracle database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`initialization_sql`</span>|Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT&#x3D;&#x27;YYYY-DD-MM HH24:MI:SS&#x27;|*string*| | | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## SparkParametersSpec
Apache Spark connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|Spark host name. Supports also a ${SPARK_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|Spark port number. The default port is 10000. Supports also a ${SPARK_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|Spark user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|Spark database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|*string*| | | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## DatabricksParametersSpec
Databricks connection parameters.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|Databricks host name. Supports also a ${DATABRICKS_HOST} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|Databricks port number. The default port is 443. Supports also a ${DATABRICKS_PORT} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`catalog`</span>|Databricks catalog name. Supports also a ${DATABRICKS_CATALOG} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`user`</span>|Databricks user name. Supports also a ${DATABRICKS_USER} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|Databricks database password. Supports also a ${DATABRICKS_PASSWORD} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`http_path`</span>|Databricks http path to the warehouse. For example: /sql/1.0/warehouses/&lt;warehouse instance id&gt;. Supports also a ${DATABRICKS_HTTP_PATH} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`access_token`</span>|Databricks access token the warehouse. Supports also a ${DATABRICKS_ACCESS_TOKEN} configuration with a custom environment variable.|*string*| | | |
|<span class="no-wrap-code ">`properties`</span>|A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.|*Dict[string, string]*| | | |









___


## DataGroupingConfigurationSpec
Configuration of the data groupings that is used to calculate data quality checks with a GROUP BY clause.
 Data grouping levels may be hardcoded if we have different (but similar) tables for different business areas (countries, product groups).
 We can also pull data grouping levels directly from the database if a table has a column that identifies a business area.
 Data quality results for new groups are dynamically identified in the database by the GROUP BY clause. Sensor values are extracted for each data group separately,
 a time series is build for each data group separately.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`level_1`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 1 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |
|<span class="no-wrap-code ">[`level_2`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 2 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |
|<span class="no-wrap-code ">[`level_3`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 3 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |
|<span class="no-wrap-code ">[`level_4`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 4 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |
|<span class="no-wrap-code ">[`level_5`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 5 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |
|<span class="no-wrap-code ">[`level_6`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 6 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |
|<span class="no-wrap-code ">[`level_7`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 7 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |
|<span class="no-wrap-code ">[`level_8`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 8 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |
|<span class="no-wrap-code ">[`level_9`](./ConnectionYaml.md#datagroupingdimensionspec)</span>|Data grouping dimension level 9 configuration.|*[DataGroupingDimensionSpec](./ConnectionYaml.md#datagroupingdimensionspec)*| | | |









___


## DataGroupingDimensionSpec
Single data grouping dimension configuration. A data grouping dimension may be configured as a hardcoded value or a mapping to a column.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`source`</span>|The source of the data grouping dimension value. The default source of the grouping dimension is a tag. The tag should be assigned when there are many similar tables that store the same data for different areas (countries, etc.). It can be the name of the country if the table or partition stores information for that country.|*enum*|*tag*<br/>*column_value*<br/>| | |
|<span class="no-wrap-code ">`tag`</span>|The value assigned to the data quality grouping dimension when the source is &#x27;tag&#x27;. Assign a hard-coded (static) value to the data grouping dimension (tag) when there are multiple similar tables storing the same data for different areas (countries, etc.). This can be the name of the country if the table or partition stores information for that country.|*string*| | | |
|<span class="no-wrap-code ">`column`</span>|Column name that contains a dynamic data grouping dimension value (for dynamic data-driven data groupings). Sensor queries will be extended with a GROUP BY {data group level colum name}, sensors (and alerts) will be calculated for each unique value of the specified column. Also a separate time series will be tracked for each value.|*string*| | | |
|<span class="no-wrap-code ">`name`</span>|Data grouping dimension name.|*string*| | | |









___


## DefaultSchedulesSpec
Container of all monitoring schedules (cron expressions) for each type of checks.
 Data quality checks are grouped by type (profiling, whole table checks, time period partitioned checks).
 Each group of checks can be further divided by time scale (daily, monthly, etc).
 Each time scale has a different monitoring schedule used by the job scheduler to run the checks.
 These schedules are defined in this object.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profiling`](./profiling/table-profiling-checks.md#monitoringschedulespec)</span>|Schedule for running profiling data quality checks.|*[MonitoringScheduleSpec](./profiling/table-profiling-checks.md#monitoringschedulespec)*| | | |
|<span class="no-wrap-code ">[`monitoring_daily`](./profiling/table-profiling-checks.md#monitoringschedulespec)</span>|Schedule for running daily monitoring checks.|*[MonitoringScheduleSpec](./profiling/table-profiling-checks.md#monitoringschedulespec)*| | | |
|<span class="no-wrap-code ">[`monitoring_monthly`](./profiling/table-profiling-checks.md#monitoringschedulespec)</span>|Schedule for running monthly monitoring checks.|*[MonitoringScheduleSpec](./profiling/table-profiling-checks.md#monitoringschedulespec)*| | | |
|<span class="no-wrap-code ">[`partitioned_daily`](./profiling/table-profiling-checks.md#monitoringschedulespec)</span>|Schedule for running daily partitioned checks.|*[MonitoringScheduleSpec](./profiling/table-profiling-checks.md#monitoringschedulespec)*| | | |
|<span class="no-wrap-code ">[`partitioned_monthly`](./profiling/table-profiling-checks.md#monitoringschedulespec)</span>|Schedule for running monthly partitioned checks.|*[MonitoringScheduleSpec](./profiling/table-profiling-checks.md#monitoringschedulespec)*| | | |









___


## ConnectionIncidentGroupingSpec
Configuration of data quality incident grouping on a connection level. Defines how similar data quality issues are grouped into incidents.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`grouping_level`</span>|Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a datatype data quality incident detected on a table X in the numeric checks category).|*enum*|*table*<br/>*table_dimension*<br/>*table_dimension_category*<br/>*table_dimension_category_type*<br/>*table_dimension_category_name*<br/>| | |
|<span class="no-wrap-code ">`minimum_severity`</span>|Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is &#x27;warning&#x27;. Other supported severity levels are &#x27;error&#x27; and &#x27;fatal&#x27;.|*enum*|*warning*<br/>*error*<br/>*fatal*<br/>| | |
|<span class="no-wrap-code ">`divide_by_data_groups`</span>|Create separate data quality incidents for each data group, creating different incidents for different groups of rows. By default, data groups are ignored for grouping data quality issues into data quality incidents.|*boolean*| | | |
|<span class="no-wrap-code ">`max_incident_length_days`</span>|The maximum length of a data quality incident in days. When a new data quality issue is detected after max_incident_length_days days since a similar data quality was first seen, a new data quality incident is created that will capture all following data quality issues for the next max_incident_length_days days. The default value is 60 days.|*integer*| | | |
|<span class="no-wrap-code ">`mute_for_days`</span>|The number of days that all similar data quality issues are muted when a a data quality incident is closed in the &#x27;mute&#x27; status.|*integer*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables data quality incident creation for failed data quality checks on the data source.|*boolean*| | | |
|<span class="no-wrap-code ">[`webhooks`](./ConnectionYaml.md#incidentwebhooknotificationsspec)</span>|Configuration of Webhook URLs for new or updated incident notifications.|*[IncidentWebhookNotificationsSpec](./ConnectionYaml.md#incidentwebhooknotificationsspec)*| | | |









___


## IncidentWebhookNotificationsSpec
Configuration of Webhook URLs used for new or updated incident&#x27;s notifications.
 Specifies the URLs of webhooks where the notification messages are sent.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`incident_opened_webhook_url`</span>|Webhook URL where the notification messages describing new incidents are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.|*string*| | | |
|<span class="no-wrap-code ">`incident_acknowledged_webhook_url`</span>|Webhook URL where the notification messages describing acknowledged messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.|*string*| | | |
|<span class="no-wrap-code ">`incident_resolved_webhook_url`</span>|Webhook URL where the notification messages describing resolved messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.|*string*| | | |
|<span class="no-wrap-code ">`incident_muted_webhook_url`</span>|Webhook URL where the notification messages describing muted messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.|*string*| | | |









___


## LabelSetSpec
A collection of unique labels assigned to items (tables, columns, checks) that can be targeted for a data quality check execution.





___


