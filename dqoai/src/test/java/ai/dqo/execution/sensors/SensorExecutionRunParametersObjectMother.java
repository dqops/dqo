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
package ai.dqo.execution.sensors;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.connectors.ProviderDialectSettingsObjectMother;
import ai.dqo.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for SensorExecutionRunParameters.
 */
public class SensorExecutionRunParametersObjectMother {
    /**
     * Returns the singleton instance of the sensor run parameter factory.
     * @return Sensor execution run parameters factory.
     */
    public static SensorExecutionRunParametersFactory getFactory() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(SensorExecutionRunParametersFactory.class);
    }

    /**
     * Creates an empty run parameters with a connection to bigquery.
     * @return Empty sensor run parameters.
     */
    public static SensorExecutionRunParameters createEmptyBigQuery() {
        return new SensorExecutionRunParameters(BigQueryConnectionSpecObjectMother.create(),
                null, null, null, null, null, null, null);
    }

    /**
     * Creates a sensor run parameters object by extracting the given connection name, schema name, table name and using given sensor parameters.
     * @param userHome User home with the requested connection and table.
     * @param connectionName Connection name.
     * @param schemaName Physical schema name.
     * @param tableName Physical table name.
     * @param checkSpec Check specification.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableAndCheck(
			UserHome userHome, String connectionName, String schemaName, String tableName,
			AbstractCheckSpec checkSpec) {
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionWrapper.getSpec().getProviderType());
        SensorExecutionRunParametersFactory factory = getFactory();

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(
                connectionWrapper.getSpec(), tableWrapper.getSpec(), null, checkSpec, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run on a given sample table.
     * @param sampleTableMetadata Sample table metadata.
     * @param checkSpec Check specification.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableAndCheck(
			SampleTableMetadata sampleTableMetadata,
			AbstractCheckSpec checkSpec) {
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        SensorExecutionRunParametersFactory factory = getFactory();

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(connectionSpec, tableSpec, null,
                checkSpec, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object by extracting the given connection name, schema name, table name, column and using given sensor parameters.
     * @param userHome User home with the requested connection and table.
     * @param connectionName Connection name.
     * @param schemaName Physical schema name.
     * @param tableName Physical table name.
     * @param columnName Column name.
     * @param checkSpec Check specification.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableColumnAndCheck(
			UserHome userHome, String connectionName, String schemaName, String tableName, String columnName,
			AbstractCheckSpec checkSpec) {
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        SensorExecutionRunParametersFactory factory = getFactory();

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(connectionSpec, tableSpec, columnSpec,
                checkSpec, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run on a given sample table, when a column is selected.
     * @param sampleTableMetadata Sample table metadata.
     * @param columnName Target column name.
     * @param checkSpec Check specification.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableColumnAndCheck(
			SampleTableMetadata sampleTableMetadata,
			String columnName,
			AbstractCheckSpec checkSpec) {
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        SensorExecutionRunParametersFactory factory = getFactory();

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(connectionSpec, tableSpec, columnSpec,
                checkSpec, dialectSettings);
        return sensorExecutionRunParameters;
    }
}
