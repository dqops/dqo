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
import com.dqops.connectors.mysql.MysqlConnectionSpecObjectMother;
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
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sampledata.files.CsvSampleFilesObjectMother;
import com.dqops.sampledata.files.SampleTableFromCsv;
import org.junit.jupiter.api.Assertions;
import tech.tablesaw.columns.Column;

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
        }

        Assertions.fail("Add a case statement for a target provider and define a connection spec object mother for " + providerType.name());
        return null;
    }

    /**
     * Returns the default schema (physical schema name) where tables are created in a testable database.
     * @param providerType Provider type.
     * @return Schema name.
     */
    public static String getSchemaForProvider(ProviderType providerType) {
        switch (providerType) {
            case bigquery:
                return BigQueryConnectionSpecObjectMother.getDatasetName();

            case snowflake:
                return SnowflakeConnectionSpecObjectMother.getSchemaName();

            case postgresql:
                return PostgresqlConnectionSpecObjectMother.getSchemaName();

            case redshift:
                return RedshiftConnectionSpecObjectMother.getSchemaName();

            case sqlserver:
                return SqlServerConnectionSpecObjectMother.getSchemaName();

            case presto:
                return PrestoConnectionSpecObjectMother.getSchemaName();

            case trino:
                return TrinoConnectionSpecObjectMother.getSchemaName();

            case mysql:
                return MysqlConnectionSpecObjectMother.getSchemaName();

            case oracle:
                return OracleConnectionSpecObjectMother.getSchemaName();

            case spark:
                return SparkConnectionSpecObjectMother.getSchemaName();
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
        String connectionName = getConnectionNameForProvider(providerType);
        ConnectionSpec connectionSpecRaw = makeConnectionSpecForProvider(providerType); // in order to support different database versions, we can accept a ConnectionSpec as a parameter
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        String targetSchema = getSchemaForProvider(providerType);
        SampleTableFromCsv sampleTable = CsvSampleFilesObjectMother.getSampleTable(csvFileName);
        PhysicalTableName physicalTableName = new PhysicalTableName(targetSchema, sampleTable.getHashedTableName());
        TableSpec tableSpec = new TableSpec(physicalTableName);
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
        tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(providerType);

        for (Column<?> dataColumn : sampleTable.getTable().columns()) {
            ColumnSpec columnSpec = new ColumnSpec();
            String userProposedDataType = sampleTable.getPhysicalColumnTypes().get(dataColumn.name());
            if (userProposedDataType != null) {
                ColumnTypeSnapshotSpec userProposedType = ColumnTypeSnapshotSpec.fromType(userProposedDataType);
                columnSpec.setTypeSnapshot(userProposedType);
            }
            else {
                ColumnTypeSnapshotSpec providerProposedTypeSnapshot = connectionProvider.proposePhysicalColumnType(dataColumn);
                columnSpec.setTypeSnapshot(providerProposedTypeSnapshot);
            }

            tableSpec.getColumns().put(dataColumn.name(), columnSpec);
        }

        return new SampleTableMetadata(connectionName, connectionSpec, tableSpec, sampleTable);
    }

    /**
     * Creates a sample table metadata with a non-existing table that cannot used for sql execution.
     * Schema and table name should not point to the existing table.
     * @param schemaName A schema name.
     * @param tableName Imagined table name that should not exist in real database.
     * @param providerType Target provider type.
     * @return Sample table metadata.
     */
    public static SampleTableMetadata createSampleTableMetadataWithNonExistingTable(String schemaName, String tableName, ProviderType providerType) {
        String connectionName = getConnectionNameForProvider(providerType);
        ConnectionSpec connectionSpecRaw = makeConnectionSpecForProvider(providerType); // in order to support different database versions, we can accept a ConnectionSpec as a parameter
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        TableSpec tableSpec = new TableSpec(new PhysicalTableName(schemaName, tableName));
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
        tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);

        return new SampleTableMetadata(connectionName, connectionSpec, tableSpec, null);
    }

}
