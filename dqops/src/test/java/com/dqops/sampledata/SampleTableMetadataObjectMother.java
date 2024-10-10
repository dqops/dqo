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
package com.dqops.sampledata;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistryObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.connectors.databricks.DatabricksConnectionSpecObjectMother;
import com.dqops.connectors.db2.Db2ConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckDbTypesMappings;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.hana.HanaConnectionSpecObjectMother;
import com.dqops.connectors.mariadb.MariaDbConnectionSpecObjectMother;
import com.dqops.connectors.mysql.MysqlConnectionSpecObjectMother;
import com.dqops.connectors.mysql.MysqlEngineType;
import com.dqops.connectors.mysql.SingleStoreDbConnectionSpecObjectMother;
import com.dqops.connectors.oracle.OracleConnectionSpecObjectMother;
import com.dqops.connectors.postgresql.PostgresqlConnectionSpecObjectMother;
import com.dqops.connectors.presto.PrestoConnectionSpecObjectMother;
import com.dqops.connectors.redshift.RedshiftConnectionSpecObjectMother;
import com.dqops.connectors.snowflake.SnowflakeConnectionSpecObjectMother;
import com.dqops.connectors.spark.SparkConnectionSpecObjectMother;
import com.dqops.connectors.sqlserver.SqlServerConnectionSpecObjectMother;
import com.dqops.connectors.trino.TrinoConnectionSpecObjectMother;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpecObjectMother;
import com.dqops.sampledata.files.SampleDataFilesProvider;
import com.dqops.sampledata.files.SampleTableFromTestDataFile;
import com.dqops.sampledata.files.TestDataSampleFilesObjectMother;
import org.junit.jupiter.api.Assertions;
import tech.tablesaw.columns.Column;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Object mother that creates metadata ({@link ConnectionSpec} and {@link TableSpec}) for sample tables
 * that are defined as CSV files in the dqo/sampledata folder.
 */
public class SampleTableMetadataObjectMother {
    /**
     * Makes a connection specification for a testable database on a target provider type.
     * @param providerType Provider type.
     * @return Connection specification.
     */
    protected static ConnectionSpec makeConnectionSpecForProvider(ProviderType providerType) {
        switch (providerType) {
            case bigquery:
                return BigQueryConnectionSpecObjectMother.create();

            case snowflake:
                return SnowflakeConnectionSpecObjectMother.create();

            case postgresql:
                return PostgresqlConnectionSpecObjectMother.create();

            case redshift:
                return RedshiftConnectionSpecObjectMother.create();

            case sqlserver:
                return SqlServerConnectionSpecObjectMother.create();

            case presto:
                return PrestoConnectionSpecObjectMother.create();

            case trino:
                return TrinoConnectionSpecObjectMother.create();

            case mysql:
                return MysqlConnectionSpecObjectMother.create();

            case oracle:
                return OracleConnectionSpecObjectMother.create();

            case spark:
                return SparkConnectionSpecObjectMother.create();

            case databricks:
                return DatabricksConnectionSpecObjectMother.create();

            case hana:
                return HanaConnectionSpecObjectMother.create();

            case db2:
                return Db2ConnectionSpecObjectMother.create();

            case mariadb:
                return MariaDbConnectionSpecObjectMother.create();

        }

        Assertions.fail("Add a case statement for a target provider and define a connection spec object mother for " + providerType.name());
        return null;
    }

    /**
     * Returns the default schema (physical schema name) where tables are created in a testable database.
     * @param connectionSpec Connection spec with set provider type.
     * @return Schema name.
     */
    public static String getSchemaForProvider(ConnectionSpec connectionSpec) {
        ProviderType providerType = connectionSpec.getProviderType();
        switch (providerType) {
            case bigquery:
                return BigQueryConnectionSpecObjectMother.getDatasetName();

            case snowflake:
                return SnowflakeConnectionSpecObjectMother.getSchemaName();

            case postgresql:
                return PostgresqlConnectionSpecObjectMother.getSchemaName();

            case duckdb:
                return DuckdbConnectionSpecObjectMother.getSchemaName();

            case redshift:
                return RedshiftConnectionSpecObjectMother.getSchemaName();

            case sqlserver:
                return SqlServerConnectionSpecObjectMother.getSchemaName();

            case presto:
                return PrestoConnectionSpecObjectMother.getSchemaName();

            case trino:
                return TrinoConnectionSpecObjectMother.getSchemaName();

            case mysql:
                if (connectionSpec.getMysql().getMysqlEngineType() == MysqlEngineType.singlestoredb) {
                    return SingleStoreDbConnectionSpecObjectMother.getSchemaName();
                } else {
                    return MysqlConnectionSpecObjectMother.getSchemaName();
                }
            case oracle:
                return OracleConnectionSpecObjectMother.getSchemaName();

            case spark:
                return SparkConnectionSpecObjectMother.getSchemaName();

            case databricks:
                return DatabricksConnectionSpecObjectMother.getSchemaName();

            case hana:
                return HanaConnectionSpecObjectMother.getSchemaName();

            case db2:
                return Db2ConnectionSpecObjectMother.getSchemaName();

            case mariadb:
                return MariaDbConnectionSpecObjectMother.getSchemaName();
        }

        Assertions.fail("Add a case statement for a target provider " + providerType.name());
        return null;
    }

    /**
     * Returns the default connection name for the provider.
     * @param providerType Provider type.
     * @return Desired connection name for the provider.
     */
    public static String getConnectionNameForProvider(ProviderType providerType) {
        return providerType.name() + "_connection";
    }

    /**
     * Creates a sample table metadata adapted for the tested provider. The physical table name will match the desired
     * name for a table in a tested database.
     * @param csvFileName Sample data CSV file name (a file name in the dqo/sampledata folder).
     * @param providerType Target provider type.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataForCsvFile(String csvFileName, ProviderType providerType) {
        ConnectionSpec connectionSpecRaw = makeConnectionSpecForProvider(providerType);
        SampleTableMetadata sampleTableMetadata = createSampleTableMetadataForCsvFile(csvFileName, connectionSpecRaw);
        return sampleTableMetadata;
    }

    /**
     * Creates a sample table metadata adapted for the tested connection spec.
     * The physical table name will match the desired name for a table in a tested database.
     * The method allows to test e.g. different database versions.
     * @param csvFileName Sample data CSV file name (a file name in the dqo/sampledata folder).
     * @param connectionSpecRaw Target connection spec.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataForCsvFile(String csvFileName, ConnectionSpec connectionSpecRaw) {
        ProviderType providerType = connectionSpecRaw.getProviderType();
        String connectionName = getConnectionNameForProvider(providerType);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        String targetSchema = getSchemaForProvider(connectionSpec);
        SampleTableFromTestDataFile sampleTable = TestDataSampleFilesObjectMother.getSampleTableFromCsv(csvFileName);
        PhysicalTableName physicalTableName = new PhysicalTableName(targetSchema, sampleTable.getHashedTableName());
        TableSpec tableSpec = new TableSpec(physicalTableName);
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
        tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);

        fillColumnSpecsInTableSpec(tableSpec, sampleTable, connectionSpec);

        return new SampleTableMetadata(connectionName, connectionSpec, tableSpec, sampleTable);
    }

    /**
     * Creates a sample table metadata with a non-existing table that cannot be used for sql execution.
     * Schema and table name should not point to the existing table.
     * @param schemaName A schema name.
     * @param tableName Imagined table name that should not exist in real database.
     * @param providerType Target provider type.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataWithNonExistingTable(String schemaName, String tableName, ProviderType providerType) {
        ConnectionSpec connectionSpecRaw = makeConnectionSpecForProvider(providerType); // in order to support different database versions, we can accept a ConnectionSpec as a parameter
        return createSampleTableMetadataWithNonExistingTable(schemaName, tableName, connectionSpecRaw);
    }

    /**
     * Creates a sample table metadata with a non-existing table that cannot be used for sql execution.
     * Schema and table name should not point to the existing table.
     * @param schemaName A schema name.
     * @param tableName Imagined table name that should not exist in real database.
     * @param connectionSpecRaw Target connection spec.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataWithNonExistingTable(String schemaName, String tableName, ConnectionSpec connectionSpecRaw) {
        String connectionName = getConnectionNameForProvider(connectionSpecRaw.getProviderType());
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        TableSpec tableSpec = new TableSpec(new PhysicalTableName(schemaName, tableName));
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
        tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);

        return new SampleTableMetadata(connectionName, connectionSpec, tableSpec, null);
    }

    /**
     * Creates a sample table metadata adapted for the tested connection spec.
     * The physical table name will match the desired name for a table in a tested database.
     * The method allows to test e.g. different database versions.
     * @param csvFileName Sample data CSV file name (a file name in the dqo/sampledata folder).
     * @param connectionSpecRaw Target connection spec.
     * @param dateFormat Date format of the date column from csv file.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataForExplicitCsvFile(String csvFileName,
                                                                                  ConnectionSpec connectionSpecRaw,
                                                                                  String dateFormat) {
        ProviderType providerType = connectionSpecRaw.getProviderType();
        String connectionName = getConnectionNameForProvider(providerType);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        SampleTableFromTestDataFile sampleTable = TestDataSampleFilesObjectMother.getSampleTableFromCsv(csvFileName);

        String targetSchema = getSchemaForProvider(connectionSpec);
        PhysicalTableName physicalTableName = new PhysicalTableName(targetSchema, sampleTable.getHashedTableName());
        TableSpec tableSpec = new TableSpec();
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForCsvFile(csvFileName);
        tableSpec.setFileFormat(fileFormatSpec);
        tableSpec.setPhysicalTableName(physicalTableName);
        fileFormatSpec.getCsv().setDateformat(dateFormat);

        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
        tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);

        fillColumnSpecsInTableSpec(tableSpec, sampleTable, connectionSpec);

        return new SampleTableMetadata(connectionName, connectionSpec, tableSpec, sampleTable);
    }

    /**
     * Creates a sample table metadata adapted for the tested connection spec.
     * The physical table name will match the desired name for a table in a tested database.
     * The method allows to test e.g. different database versions.
     * @param csvFileName Sample data CSV file name (a file name in the dqo/sampledata folder).
     * @param connectionSpecRaw Target connection spec.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataForExplicitCsvFile(String csvFileName,
                                                                                  ConnectionSpec connectionSpecRaw) {
        return createSampleTableMetadataForExplicitCsvFile(csvFileName, connectionSpecRaw, "%Y-%m-%d");
    }

    /**
     * Creates a sample table metadata adapted for the tested connection spec.
     * The physical table name will match the desired name for a table in a tested database.
     * The method allows to test e.g. different database versions.
     * @param csvFilesFolder Sample data CSV file name (a file name in the dqo/sampledata folder).
     * @param connectionSpecRaw Target connection spec.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataForExplicitMultipleCsvFiles(String csvFilesFolder,
                                                                                           ConnectionSpec connectionSpecRaw) {
        ProviderType providerType = connectionSpecRaw.getProviderType();
        String connectionName = getConnectionNameForProvider(providerType);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        SampleTableFromTestDataFile sampleTable = TestDataSampleFilesObjectMother.getSampleTableForFiles(csvFilesFolder);

        HashMap<String, String> header = new LinkedHashMap<>();
        List<Column<?>> columnList = Arrays.asList(sampleTable.getTable().columnArray());
        columnList.stream().forEachOrdered(column -> {
            if(DuckDbTypesMappings.CSV_TYPES_TO_DUCK_DB.containsKey(column.type())){
                header.put(column.name(), column.type().toString());
            } else {
                header.put(
                        column.name(),
                        DuckDbTypesMappings.CSV_TYPES_TO_DUCK_DB.get(column.type().toString())
                );
            }
        });

        TableSpec tableSpec = new TableSpec();
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForMultipleCsvFiles(
                SampleDataFilesProvider.getCsvFiles(csvFilesFolder).stream()
                        .filter(file -> file.getName().endsWith(".csv"))
                        .map(file -> file.toString()).collect(Collectors.toList())
        );
        tableSpec.setFileFormat(fileFormatSpec);
        tableSpec.setPhysicalTableName(new PhysicalTableName("a_random_schema_name", "a_random_table_name"));

        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
        tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);

        fillColumnSpecsInTableSpec(tableSpec, sampleTable, connectionSpec);

        return new SampleTableMetadata(connectionName, connectionSpec, tableSpec, sampleTable);
    }

    /**
     * Creates a sample table metadata adapted for the tested connection spec.
     * The physical table name will match the desired name for a table in a tested database.
     * The method allows to test e.g. different database versions.
     * @param jsonFileName Sample data JSON file name (a file name in the dqo/sampledata folder).
     * @param connectionSpecRaw Target connection spec.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataForExplicitJsonFile(String jsonFileName,
                                                                                   ConnectionSpec connectionSpecRaw) {
        return createSampleTableMetadataForExplicitJsonFile(jsonFileName, connectionSpecRaw, "%Y-%m-%d");
    }

    /**
     * Creates a sample table metadata adapted for the tested connection spec.
     * The physical table name will match the desired name for a table in a tested database.
     * The method allows to test e.g. different database versions.
     * @param jsonFileName Sample data JSON file name (a file name in the dqo/sampledata folder).
     * @param connectionSpecRaw Target connection spec.
     * @param dateFormat Date format of the date column from csv file.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataForExplicitJsonFile(String jsonFileName,
                                                                                   ConnectionSpec connectionSpecRaw,
                                                                                   String dateFormat) {
        ProviderType providerType = connectionSpecRaw.getProviderType();
        String connectionName = getConnectionNameForProvider(providerType);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        SampleTableFromTestDataFile sampleTable = TestDataSampleFilesObjectMother.getSampleTableFromJson(jsonFileName);

        String targetSchema = getSchemaForProvider(connectionSpec);
        PhysicalTableName physicalTableName = new PhysicalTableName(targetSchema, sampleTable.getHashedTableName());
        TableSpec tableSpec = new TableSpec();
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForJsonFile(jsonFileName);
        tableSpec.setFileFormat(fileFormatSpec);
        tableSpec.setPhysicalTableName(physicalTableName);
        fileFormatSpec.getJson().setDateformat(dateFormat);

        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
        tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);

        fillColumnSpecsInTableSpec(tableSpec, sampleTable, connectionSpec);

        return new SampleTableMetadata(connectionName, connectionSpec, tableSpec, sampleTable);
    }

    /**
     * Creates a sample table metadata adapted for the tested connection spec.
     * The physical table name will match the desired name for a table in a tested database.
     * The method allows to test e.g. different database versions.
     * @param parquetFileName Sample data JSON file name (a file name in the dqo/sampledata folder).
     * @param connectionSpecRaw Target connection spec.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataForExplicitParquetFile(String parquetFileName,
                                                                                      ConnectionSpec connectionSpecRaw) {
        ProviderType providerType = connectionSpecRaw.getProviderType();
        String connectionName = getConnectionNameForProvider(providerType);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        SampleTableFromTestDataFile sampleTable = TestDataSampleFilesObjectMother.getSampleTableFromParquet(parquetFileName);

        String targetSchema = getSchemaForProvider(connectionSpec);
        PhysicalTableName physicalTableName = new PhysicalTableName(targetSchema, sampleTable.getHashedTableName());
        TableSpec tableSpec = new TableSpec();
        FileFormatSpec fileFormatSpec = FileFormatSpecObjectMother.createForParquetFile(parquetFileName);
        tableSpec.setFileFormat(fileFormatSpec);
        tableSpec.setPhysicalTableName(physicalTableName);

        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
        tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);

        fillColumnSpecsInTableSpec(tableSpec, sampleTable, connectionSpec);

        return new SampleTableMetadata(connectionName, connectionSpec, tableSpec, sampleTable);
    }

    /**
     * Fills column specifications basing on the sample data from csv file.
     * @param tableSpec The table spec that will be filled.
     * @param sampleTable The sample table from csv with the column details.
     * @param connectionSpec The connection spec for the
     */
    private static void fillColumnSpecsInTableSpec(TableSpec tableSpec, SampleTableFromTestDataFile sampleTable, ConnectionSpec connectionSpec){
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(connectionSpec.getProviderType());

        for (Column<?> dataColumn : sampleTable.getTable().columns()) {
            ColumnSpec columnSpec = new ColumnSpec();
            String userProposedDataType = sampleTable.getPhysicalColumnTypes().get(dataColumn.name());
            if (userProposedDataType != null) {
                ColumnTypeSnapshotSpec userProposedType = ColumnTypeSnapshotSpec.fromType(userProposedDataType);
                columnSpec.setTypeSnapshot(userProposedType);
            }
            else {
                ColumnTypeSnapshotSpec providerProposedTypeSnapshot = connectionProvider
                        .proposePhysicalColumnType(connectionSpec, dataColumn);
                columnSpec.setTypeSnapshot(providerProposedTypeSnapshot);
            }

            tableSpec.getColumns().put(dataColumn.name(), columnSpec);
        }
    }

}
