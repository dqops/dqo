/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.duckdb;

import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.config.DuckdbReadMode;
import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.*;
import com.dqops.connectors.duckdb.schema.DuckDBDataTypeParser;
import com.dqops.connectors.duckdb.schema.DuckDBField;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.connectors.storage.aws.AwsAuthenticationMode;
import com.dqops.connectors.storage.azure.AzureAuthenticationMode;
import com.dqops.core.configuration.DqoDuckdbConfiguration;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpecProvider;
import com.dqops.metadata.sources.fileformat.FilePathListSpec;
import com.dqops.metadata.storage.localfiles.credentials.azure.AzureCredentialsProvider;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DuckDB source connection.
 */
@Slf4j
@Component("duckdb-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DuckdbSourceConnection extends AbstractJdbcSourceConnection {

    private final HomeLocationFindService homeLocationFindService;
    private final DqoDuckdbConfiguration dqoDuckdbConfiguration;
    private final DuckDBDataTypeParser dataTypeParser;
    private static final String temporaryDirectoryPrefix = "dqops_duckdb_temp_";
    private final TablesListerProvider tablesListerProvider;
    private final DuckdbInMemoryInstance duckdbInMemoryInstance;
    private final AzureCredentialsProvider azureCredentialsProvider;
    private boolean extensionsAreNotAvailable; // flag to tell that we cannot register extensions

    /**
     * Injection constructor for the duckdb connection.
     *
     * @param jdbcConnectionPool       Jdbc connection pool.
     * @param secretValueProvider      Secret value provider for the environment variable expansion.
     * @param homeLocationFindService  Home location find service.
     * @param dqoDuckdbConfiguration   Configuration settings for duckdb.
     * @param dataTypeParser           Data type parser that parses the schema of structures.
     * @param duckdbInMemoryInstance   Holder of a shared in-memory DuckDB connection that is duplicated.
     * @param azureCredentialsProvider Azure credentials provider
     */
    @Autowired
    public DuckdbSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                  SecretValueProvider secretValueProvider,
                                  DuckdbConnectionProvider duckdbConnectionProvider,
                                  HomeLocationFindService homeLocationFindService,
                                  DqoDuckdbConfiguration dqoDuckdbConfiguration,
                                  DuckDBDataTypeParser dataTypeParser,
                                  TablesListerProvider tablesListerProvider,
                                  DuckdbInMemoryInstance duckdbInMemoryInstance,
                                  AzureCredentialsProvider azureCredentialsProvider) {
        super(jdbcConnectionPool, secretValueProvider, duckdbConnectionProvider);
        this.homeLocationFindService = homeLocationFindService;
        this.dqoDuckdbConfiguration = dqoDuckdbConfiguration;
        this.dataTypeParser = dataTypeParser;
        this.tablesListerProvider = tablesListerProvider;
        this.duckdbInMemoryInstance = duckdbInMemoryInstance;
        this.azureCredentialsProvider = azureCredentialsProvider;
    }

    @Override
    public void createTable(TableSpec tableSpec) {
        // nothing to do
    }

    @Override
    public void loadData(TableSpec tableSpec, Table data) {
        // nothing to do
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        DuckdbParametersSpec duckdbSpec = connectionSpec.getDuckdb();

        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:duckdb:");

        String database = this.getSecretValueProvider().expandValue(duckdbSpec.getDatabase(), secretValueLookupContext);
        if(database != null){
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();

        if (duckdbSpec.getProperties() != null) {
            dataSourceProperties.putAll(
                    duckdbSpec.getProperties()
                            .entrySet().stream()
                            .filter(x -> !x.getKey().isEmpty())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    /**
     * Creates the tablesaw's Table from the ResultSet for the query execution
     * @param results               ResultSet object that contains the data produced by a query
     * @param sqlQueryStatement     SQL statement that returns a row set.
     * @return Tabular result captured from the query.
     * @throws SQLException
     */
    @Override
    protected Table rawTableResultFromResultSet(ResultSet results, String sqlQueryStatement) throws SQLException {
        try (DuckdbResultSet mysqlResultSet = new DuckdbResultSet(results)) {
            Table resultTable = Table.read().db(mysqlResultSet, sqlQueryStatement);
            return resultTable;
        }
    }

    /**
     * Stores a reference to an opened JDBC connection. This method should be called only when we create the connection in a different way, for example - by duplicating a DuckDB in-memory connection.
     *
     * @param jdbcConnection JDBC connection to store.
     */
    @Override
    protected void setJdbcConnection(Connection jdbcConnection) {
        super.setJdbcConnection(jdbcConnection);
    }

    /**
     * Executes a provider specific SQL that runs a command DML/DDL command.
     *
     * @param sqlStatement SQL DDL or DML statement.
     * @param jobCancellationToken Job cancellation token, enables cancelling a running query.
     */
    @Override
    public long executeCommand(String sqlStatement, JobCancellationToken jobCancellationToken) {
        try {
            try (Statement statement = this.getJdbcConnection().createStatement()) {
                try (JobCancellationListenerHandle cancellationListenerHandle =
                             jobCancellationToken.registerCancellationListener(
                                     cancellationToken -> RunSilently.run(statement::cancel))) {
                    statement.execute(sqlStatement);
                    return 0;
                }
                finally {
                    jobCancellationToken.throwIfCancelled();
                }
            }
        }
        catch (Exception ex) {
            String connectionName = this.getConnectionSpec().getConnectionName();
            throw new JdbcQueryFailedException(
                    String.format("SQL statement failed: %s, connection: %s, SQL: %s", ex.getMessage(), connectionName, sqlStatement),
                    ex, sqlStatement, connectionName);
        }
    }

    /**
     * Opens a connection before it can be used for executing any statements.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     */
    @Override
    public void open(SecretValueLookupContext secretValueLookupContext) {
        if (this.getConnectionSpec().getDuckdb() == null || this.getConnectionSpec().getDuckdb().getReadMode() == DuckdbReadMode.in_memory) {
            // use an in-memory shared instance
            Connection duckDbDuplicatedInstance = this.duckdbInMemoryInstance.duplicateInMemoryJdbcConnection();
            this.setJdbcConnection(duckDbDuplicatedInstance);
        } else {
            try {
                Class.forName("org.duckdb.DuckDBDriver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            super.open(secretValueLookupContext);
        }

        loadSecrets(secretValueLookupContext);

        this.configureOptimizer(this.getConnectionSpec().getDuckdb() != null &&
                this.getConnectionSpec().getDuckdb().getEnableOptimizer() != null &&
                this.getConnectionSpec().getDuckdb().getEnableOptimizer().booleanValue());
    }

    /**
     * Configures an in-memory instance. This operation is called by {@link DuckdbInMemoryInstanceImpl} singleton to configure the shared instance.
     */
    public void initializeInMemoryConfiguration() {
        configureSettings();
        registerExtensions();
    }

    /**
     * Configures the query optimizer (enables or disables). This option is used when Parquet files are corrupted, so the optimizer should be disabled.
     * @param enableOptimizer Enable optimizer.
     */
    public void configureOptimizer(boolean enableOptimizer) {
        String optimizerPragma = "PRAGMA " + (enableOptimizer ? "enable_optimizer" : "disable_optimizer") + ";";
        this.executeCommand(optimizerPragma, JobCancellationToken.createDummyJobCancellationToken());
    }

    /**
     * Configures settings for a connection, such as the memory limit.
     */
    private void configureSettings() {
        try {
            String memoryLimit = dqoDuckdbConfiguration.getMemoryLimit();
            if (memoryLimit != null){
                String memoryLimitQuery = "SET GLOBAL memory_limit = '" + memoryLimit + "'";
                this.executeCommand(memoryLimitQuery, JobCancellationToken.createDummyJobCancellationToken());
            }

            String threadsQuery = "SET GLOBAL threads = " + dqoDuckdbConfiguration.getThreads();
            this.executeCommand(threadsQuery, JobCancellationToken.createDummyJobCancellationToken());

            String temporaryDirectory = Files.createTempDirectory(temporaryDirectoryPrefix).toFile().getAbsolutePath();
            Path.of(temporaryDirectory).toFile().deleteOnExit();
            String tempDirectoryQuery = "SET GLOBAL temp_directory = '" + temporaryDirectory + "'";
            this.executeCommand(tempDirectoryQuery, JobCancellationToken.createDummyJobCancellationToken());
        } catch (Exception e) {
            throw new DqoRuntimeException(e);
        }
    }

    /**
     * The list of available extensions for DuckDB. Extensions are gathered locally through the project installation process.
     * @return The list of extension names for DuckDB.
     */
    private List<String> getAvailableExtensions() {
        return Arrays.asList(
                "httpfs",
                "aws",
                "azure",
                "iceberg",
                "delta",
                "avro"
        );
    }

    /**
     * Registers extensions for duckdb from the local extension repository.
     */
    private void registerExtensions() {
        try {
            File userLocationFile = Path.of(this.homeLocationFindService.getRootUserHomePath()).resolve("bin/.duckdb/extensions").toFile();
            String setExtensionsQuery =
                    userLocationFile.exists() && userLocationFile.isDirectory() ?
                            DuckdbQueriesProvider.provideSetExtensionsQuery(this.homeLocationFindService.getRootUserHomePath()) :
                            DuckdbQueriesProvider.provideSetExtensionsQuery(this.homeLocationFindService.getDqoHomePath());
            this.executeCommand(setExtensionsQuery, JobCancellationToken.createDummyJobCancellationToken());

            List<String> availableExtensionList = getAvailableExtensions();
            availableExtensionList.stream().forEach(extensionName -> {
                try {
                    String installExtensionQuery = "INSTALL " + extensionName;
                    if (Objects.equals(extensionName, "avro")) {
                        installExtensionQuery += " FROM community"; // https://duckdb.org/2024/12/09/duckdb-avro-extension.html
                    }

                    this.executeCommand(installExtensionQuery, JobCancellationToken.createDummyJobCancellationToken());
                    String loadExtensionQuery = "LOAD " + extensionName;
                    this.executeCommand(loadExtensionQuery, JobCancellationToken.createDummyJobCancellationToken());
                } catch (Exception exception) {
                    log.warn("Extension " + extensionName + " cannot be loaded.");
                    log.warn(exception.getMessage());
                }
            });
        } catch (Exception e) {
            this.extensionsAreNotAvailable = true;
        }
    }

    /**
     * Loads secrets that are used during the connection to a cloud storage service.
     *
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     */
    private void loadSecrets(SecretValueLookupContext secretValueLookupContext){
        DuckdbParametersSpec duckdb = getConnectionSpec().getDuckdb();
        if (duckdb.getStorageType() == null || duckdb.getStorageType().equals(DuckdbStorageType.local)) {
            return;
        }

        DuckdbParametersSpec duckdbSpecCloned = getConnectionSpec()
                .expandAndTrim(getSecretValueProvider(), secretValueLookupContext).getDuckdb();

        DuckdbStorageType storageType = duckdb.getStorageType();
        if (storageType != null){
            switch (storageType){
                case s3:
                    AwsAuthenticationMode awsAuthenticationMode = duckdb.getAwsAuthenticationMode() == null ?
                            AwsAuthenticationMode.default_credentials : duckdb.getAwsAuthenticationMode();

                    if (awsAuthenticationMode.equals(AwsAuthenticationMode.default_credentials)) {
                        duckdbSpecCloned.fillSpecWithDefaultAwsCredentials(secretValueLookupContext);
                    } else if (Strings.isNullOrEmpty(duckdbSpecCloned.getRegion())){
                        duckdbSpecCloned.fillSpecWithDefaultAwsConfig(secretValueLookupContext);
                    }
                    break;
                case azure:
                    AzureAuthenticationMode azureAuthenticationMode = duckdb.getAzureAuthenticationMode() == null ?
                            AzureAuthenticationMode.default_credentials : duckdb.getAzureAuthenticationMode();

                    if(azureAuthenticationMode.equals(AzureAuthenticationMode.default_credentials)){
                        duckdbSpecCloned.fillSpecWithDefaultAzureCredentials(secretValueLookupContext, azureCredentialsProvider);
                    }
                    break;
            }
        }

        this.getConnectionSpec().setDuckdb(duckdbSpecCloned);

        try {
            DuckdbSecretManager.getInstance().createSecrets(this.getConnectionSpec(), this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of schemas from the source.
     *
     * @return List of schemas.
     */
    @Override
    public List<SourceSchemaModel> listSchemas() {
        DuckdbParametersSpec duckdb = getConnectionSpec().getDuckdb();
        Map<String, String> directories = duckdb.getDirectories();
        List<SourceSchemaModel> results = new ArrayList<>();
        directories.keySet().forEach(s -> {
            SourceSchemaModel schemaModel = new SourceSchemaModel(s);
            results.add(schemaModel);
        });

        return results;
    }

    /**
     * Lists tables inside a schema.
     *
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter with a text that must be present inside a table name.
     * @param limit The maximum number of tables to return.
     * @param secretValueLookupContext Secret lookup context.
     * @return List of tables in the given schema.
     */
    @Override
    public List<SourceTableModel> listTables(String schemaName, String tableNameContains, int limit, SecretValueLookupContext secretValueLookupContext) {
        DuckdbParametersSpec duckDbParameters = getConnectionSpec().getDuckdb();
        if (duckDbParameters == null) {
            duckDbParameters = new DuckdbParametersSpec();
        }

        if (duckDbParameters == null || duckDbParameters.getFilesFormatType() == null) {
            return new ArrayList<>();
        }

        DuckdbStorageType storageType = duckDbParameters.getStorageType();
        TablesLister remoteTablesLister = tablesListerProvider.createTablesLister(storageType);
        List<SourceTableModel> sourceTableModels = remoteTablesLister.listTables(duckDbParameters, schemaName, tableNameContains, limit);
        return sourceTableModels;
    }

    /**
     * Retrieves the metadata (column information) for a given list of tables from a given schema.
     *
     * @param schemaName Schema name.
     * @param tableNameContains An optional filter for a substring that must be present in a table name.
     * @param limit The maximum number of tables to return.
     * @param tableNames Table names.
     * @param connectionWrapper The connection wrapper on table spec which points the file path and the file format.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return List of table specifications with the column list.
     */
    @Override
    public List<TableSpec> retrieveTableMetadata(String schemaName,
                                                 String tableNameContains,
                                                 int limit,
                                                 List<String> tableNames,
                                                 ConnectionWrapper connectionWrapper,
                                                 SecretValueLookupContext secretValueLookupContext) {
        assert !Strings.isNullOrEmpty(schemaName);

        DuckdbParametersSpec duckdbParametersSpec = getConnectionSpec().getDuckdb();

        List<TableSpec> tableSpecs = new ArrayList<>();

        Map<String, TableSpec> physicalTableNameToTableSpec = new HashMap<>();
        if (connectionWrapper != null){
            List<TableWrapper> tableWrappers = connectionWrapper.getTables().toList();
            physicalTableNameToTableSpec = tableWrappers.stream()
                    .filter(tableWrapper -> tableWrapper.getPhysicalTableName().getSchemaName().equals(schemaName))
                    .collect(Collectors.toMap(
                            tableWrapper -> tableWrapper.getPhysicalTableName().toString(),
                            tableWrapper -> tableWrapper.getSpec()
                    ));
        }

        for (String tableName : tableNames) {
            if (!Strings.isNullOrEmpty(tableNameContains)) {
                if (!tableName.contains(tableNameContains)) {
                    continue;
                }
            }

            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            TableSpec tableSpecTemp = physicalTableNameToTableSpec.get(physicalTableName.toString());
            if (tableSpecTemp == null){
                tableSpecTemp = new TableSpec();
                tableSpecTemp.setPhysicalTableName(physicalTableName);
            }

            FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableSpecTemp);
            if (fileFormatSpec == null){
                return tableSpecs;
            }

            TableSpec tableSpec = prepareNewTableSpec(tableSpecTemp.deepClone(), fileFormatSpec.getFilePaths());
            tableSpecs.add(tableSpec);

            try {
                Table tableResult = queryForTableResult(fileFormatSpec, tableSpecTemp, secretValueLookupContext);

                Column<?>[] columns = tableResult.columnArray();
                for (Column<?> column : columns) {
                    column.setName(column.name().toLowerCase(Locale.ROOT));
                }

                for (Row colRow : tableResult) {
                    String columnName = colRow.getString("column_name");
                    String dataType = colRow.getString("column_type");
                    boolean isNullable = Objects.equals(colRow.getString("null"), "YES");
                    ColumnSpec columnSpec = prepareNewColumnSpec(dataType, isNullable);
                    if (dataType != null && (dataType.startsWith("STRUCT") || dataType.startsWith("UNION") || dataType.startsWith("MAP"))) {
                        columnSpec.setTypeSnapshot(new ColumnTypeSnapshotSpec(dataType, isNullable));
                    }
                    tableSpec.getColumns().put(columnName, columnSpec);

                    DuckDBField parsedField = this.dataTypeParser.parseFieldType(dataType, columnName);
                    if (parsedField.isStruct()) {
                        String parentColumnPrefix = parsedField.isArray() ? columnName + "[0]" : columnName;
                        addNestedFieldsFromStructs(parentColumnPrefix, parsedField, tableSpec.getColumns());
                    }
                }
            } catch (Exception e){
                if (!e.getMessage().contains("SQL query failed: java.sql.SQLException: IO Error: No files found that match the pattern")){
                    throw new DqoRuntimeException(e);
                }
            }

            if (tableSpecs.size() >= limit) {
                break;
            }
        }
        return tableSpecs;
    }

    /**
     * Traverses a structure of nested fields inside STRUCT data types and adds all nested fields.
     * @param parentColumnName Parent column name used as a prefix to access nested fields.
     * @param structField DuckDB field schema that as parsed - must be a STRUCT field.
     * @param targetColumnsMap Target column map to add generated columns.
     */
    private void addNestedFieldsFromStructs(String parentColumnName, DuckDBField structField, ColumnSpecMap targetColumnsMap) {
        if (structField.isStruct() && structField.getNestedFields() != null) {
            for (DuckDBField childField : structField.getNestedFields()) {
                ColumnSpec columnSpec = prepareNewColumnSpec(childField.getTypeName(), childField.isNullable());
                columnSpec.getTypeSnapshot().setNested(true);
                String nestedFieldName = parentColumnName + "." + childField.getFieldName();
                targetColumnsMap.put(nestedFieldName, columnSpec);

                if (childField.isStruct()) {
                    String childColumnPrefix = childField.isArray() ? nestedFieldName + "[0]" : nestedFieldName;
                    addNestedFieldsFromStructs(childColumnPrefix, childField, targetColumnsMap);
                }
            }
        }
    }

    /**
     * Creates a new column spec.
     * @param dataType A data type of the column.
     * @param isNullable Whether the column is nullable.
     * @return The new column spec.
     */
    private ColumnSpec prepareNewColumnSpec(String dataType, boolean isNullable){
        ColumnSpec columnSpec = new ColumnSpec();
        ColumnTypeSnapshotSpec columnType = ColumnTypeSnapshotSpec.fromType(dataType);
        columnType.setNullable(isNullable);
        columnSpec.setTypeSnapshot(columnType);
        return columnSpec;
    }

    /**
     * Creates a new table spec with file format filled.
     * @param clonedTableSpec A table spec.
     * @param filePaths File path list spec.
     * @return The new table spec.
     */
    private TableSpec prepareNewTableSpec(TableSpec clonedTableSpec, FilePathListSpec filePaths){
        TableSpec tableSpec = new TableSpec();
        tableSpec.setPhysicalTableName(clonedTableSpec.getPhysicalTableName());
        tableSpec.setFileFormat(
                clonedTableSpec.getFileFormat() == null
                        ? new FileFormatSpec()
                        : clonedTableSpec.getFileFormat()
        );

        FileFormatSpec newFileFormat = tableSpec.getFileFormat();
        if(newFileFormat.getFilePaths().isEmpty()){
            tableSpec.getFileFormat().getFilePaths().addAll(filePaths);
        }
        return tableSpec;
    }

    /**
     * Retrieves a table result depending on the source files type.
     * @param fileFormatSpec A file format specification with paths and format configuration.
     * @return A table result with metadata of the given data file.
     */
    private tech.tablesaw.api.Table queryForTableResult(FileFormatSpec fileFormatSpec,
                                                        TableSpec tableSpec,
                                                        SecretValueLookupContext secretValueLookupContext){
        ConnectionSpec connectionSpec = getConnectionSpec().expandAndTrim(getSecretValueProvider(), secretValueLookupContext);
        DuckdbParametersSpec duckdb = connectionSpec.getDuckdb();
        TableSpec tableSpecWithoutColumns = tableSpec.deepClone();
        tableSpecWithoutColumns.getColumns().clear();
        String tableString = fileFormatSpec.buildTableOptionsString(duckdb, tableSpecWithoutColumns);
        String query = String.format("DESCRIBE SELECT * FROM %s", tableString);
        return this.executeQuery(query, JobCancellationToken.createDummyJobCancellationToken(), null, false);
    }
}
