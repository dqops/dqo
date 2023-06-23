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
package ai.dqo.sampledata;

import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.ConnectionProviderRegistryObjectMother;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import ai.dqo.connectors.mysql.MysqlConnectionSpecObjectMother;
import ai.dqo.connectors.oracle.OracleConnectionSpecObjectMother;
import ai.dqo.connectors.postgresql.PostgresqlConnectionSpecObjectMother;
import ai.dqo.connectors.redshift.RedshiftConnectionSpecObjectMother;
import ai.dqo.connectors.snowflake.SnowflakeConnectionSpecObjectMother;
import ai.dqo.connectors.sqlserver.SqlServerConnectionSpecObjectMother;
import ai.dqo.core.secrets.SecretValueProviderObjectMother;
import ai.dqo.metadata.groupings.DataGroupingConfigurationSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.sampledata.files.CsvSampleFilesObjectMother;
import ai.dqo.sampledata.files.SampleTableFromCsv;
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

            case mysql:
                return MysqlConnectionSpecObjectMother.create();

            case oracle:
                return OracleConnectionSpecObjectMother.create();
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

            case mysql:
                return MysqlConnectionSpecObjectMother.getSchemaName();

            case oracle:
                return OracleConnectionSpecObjectMother.getSchemaName();
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
        ConnectionSpec connectionSpec = connectionSpecRaw.expandAndTrim(SecretValueProviderObjectMother.getInstance());
        String targetSchema = getSchemaForProvider(providerType);
        SampleTableFromCsv sampleTable = CsvSampleFilesObjectMother.getSampleTable(csvFileName);
        PhysicalTableName physicalTableName = new PhysicalTableName(targetSchema, sampleTable.getHashedTableName());
        TableSpec tableSpec = new TableSpec(physicalTableName);
        tableSpec.getGroupings().setFirstDataGroupingConfiguration(new DataGroupingConfigurationSpec());
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
}
