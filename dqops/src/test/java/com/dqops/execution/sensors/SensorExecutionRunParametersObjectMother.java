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
package com.dqops.execution.sensors;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.ProviderDialectSettingsObjectMother;
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.groupings.TimeSeriesConfigurationSpecObjectMother;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.utils.BeanFactoryObjectMother;
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
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, 1000, false);
    }

    /**
     * Creates a sensor run parameters object by extracting the given connection name, schema name, table name and using given sensor parameters.
     * @param userHome User home with the requested connection and table.
     * @param connectionName Connection name.
     * @param schemaName Physical schema name.
     * @param tableName Physical table name.
     * @param checkSpec Check specification.
     * @param checkType Check type.
     * @param timeSeriesConfigurationSpec Time series configuration.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableAndCheck(
            UserHome userHome, String connectionName, String schemaName, String tableName,
            AbstractCheckSpec<?,?,?,?> checkSpec, CheckType checkType,
            TimeSeriesConfigurationSpec timeSeriesConfigurationSpec) {
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionWrapper.getSpec().getProviderType());
        SensorExecutionRunParametersFactory factory = getFactory();

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(
                userHome, connectionWrapper.getSpec(), tableWrapper.getSpec(), null, checkSpec, null, checkType, null,
                null, timeSeriesConfigurationSpec, null, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run an ad-hoc check on a given sample table.
     * @param sampleTableMetadata Sample table metadata.
     * @param checkSpec Check specification.
     * @return Sensor execution run parameters.
     */
     public static SensorExecutionRunParameters createForTableForProfilingCheck(
             SampleTableMetadata sampleTableMetadata,
             AbstractCheckSpec<?,?,?,?> checkSpec) {
         ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
         ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
         TableSpec tableSpec = sampleTableMetadata.getTableSpec();
         SensorExecutionRunParametersFactory factory = getFactory();
         TimeSeriesConfigurationSpec timeSeriesConfigurationSpec = TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForProfiling();

         SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(null, connectionSpec, tableSpec, null,
                checkSpec, null, CheckType.profiling, null, null,
                 timeSeriesConfigurationSpec, null, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run a monitoring check on a given sample table.
     * @param sampleTableMetadata Sample table metadata.
     * @param checkSpec Check specification.
     * @param timeScale Time scale (daily, monthly).
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableForMonitoringCheck(
            SampleTableMetadata sampleTableMetadata,
            AbstractCheckSpec<?,?,?,?> checkSpec,
            CheckTimeScale timeScale) {
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        SensorExecutionRunParametersFactory factory = getFactory();
        TimeSeriesConfigurationSpec timeSeriesConfigurationSpec =
                TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForMonitoring(timeScale);

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(null, connectionSpec, tableSpec, null,
                checkSpec, null, CheckType.monitoring, null, null,
                timeSeriesConfigurationSpec, null, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run a date/time partitioned check on a given sample table.
     * @param sampleTableMetadata Sample table metadata.
     * @param checkSpec Check specification.
     * @param timeScale Time scale (daily, monthly).
     * @param datePartitioningColumn The name of the column used for the date partitioning.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableForPartitionedCheck(
            SampleTableMetadata sampleTableMetadata,
            AbstractCheckSpec<?,?,?,?> checkSpec,
            CheckTimeScale timeScale,
            String datePartitioningColumn) {
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        SensorExecutionRunParametersFactory factory = getFactory();
        TimeSeriesConfigurationSpec timeSeriesConfigurationSpec =
                TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(timeScale, datePartitioningColumn);
        TimeWindowFilterParameters userTimeWindowFilters = new TimeWindowFilterParameters();

        if (timeScale == CheckTimeScale.daily) {
            userTimeWindowFilters.setDailyPartitioningRecentDays(365 * 10 + 3);  // TODO: analyze the last 10 years of data
        }
        else if (timeScale == CheckTimeScale.monthly) {
            userTimeWindowFilters.setMonthlyPartitioningRecentMonths(12 * 10);   // TODO: analyze the last 10 years of data
        }

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(null, connectionSpec, tableSpec, null,
                checkSpec, null, CheckType.partitioned, null, null,
                timeSeriesConfigurationSpec, userTimeWindowFilters, dialectSettings);
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
     * @param checkType Check type.
     * @param timeSeriesConfigurationSpec Time series configuration.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableColumnAndCheck(
            UserHome userHome, String connectionName, String schemaName, String tableName, String columnName,
            AbstractCheckSpec<?,?,?,?> checkSpec,
            CheckType checkType,
            TimeSeriesConfigurationSpec timeSeriesConfigurationSpec) {
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        SensorExecutionRunParametersFactory factory = getFactory();

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(userHome, connectionSpec, tableSpec, columnSpec,
                checkSpec, null, checkType, null, null,
                timeSeriesConfigurationSpec, null, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run an profiling check on a given sample table, when a column is selected.
     * @param sampleTableMetadata Sample table metadata.
     * @param columnName Target column name.
     * @param checkSpec Check specification.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableColumnForProfilingCheck(
            SampleTableMetadata sampleTableMetadata,
            String columnName,
            AbstractCheckSpec<?,?,?,?> checkSpec) {
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        SensorExecutionRunParametersFactory factory = getFactory();
        TimeSeriesConfigurationSpec timeSeriesConfigurationSpec = TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForProfiling();

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(null, connectionSpec, tableSpec, columnSpec,
                checkSpec, null, CheckType.profiling, null, null,
                timeSeriesConfigurationSpec, null, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run a monitoring check on a given sample table, when a column is selected.
     * @param sampleTableMetadata Sample table metadata.
     * @param columnName Target column name.
     * @param checkSpec Check specification.
     * @param checkTimeScale Monitoring time scale (daily or monthly).
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableColumnForMonitoringCheck(
            SampleTableMetadata sampleTableMetadata,
            String columnName,
            AbstractCheckSpec<?,?,?,?> checkSpec,
            CheckTimeScale checkTimeScale) {
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        SensorExecutionRunParametersFactory factory = getFactory();
        TimeSeriesConfigurationSpec timeSeriesConfigurationSpec = TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForMonitoring(checkTimeScale);

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(null, connectionSpec, tableSpec, columnSpec,
                checkSpec, null, CheckType.monitoring, null, null,
                timeSeriesConfigurationSpec, null, dialectSettings);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run a date/time partitioned check on a given sample table, when a column is selected.
     * @param sampleTableMetadata Sample table metadata.
     * @param columnName Target column name.
     * @param checkSpec Check specification.
     * @param checkTimeScale Monitoring time scale (daily or monthly).
     * @param timePartitioningColumn The name of a date partitioning column.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableColumnForPartitionedCheck(
            SampleTableMetadata sampleTableMetadata,
            String columnName,
            AbstractCheckSpec<?,?,?,?> checkSpec,
            CheckTimeScale checkTimeScale,
            String timePartitioningColumn) {
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        SensorExecutionRunParametersFactory factory = getFactory();
        TimeSeriesConfigurationSpec timeSeriesConfigurationSpec = TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                checkTimeScale, timePartitioningColumn);
        TimeWindowFilterParameters userTimeWindowFilters = new TimeWindowFilterParameters();

        if (checkTimeScale == CheckTimeScale.daily) {
            userTimeWindowFilters.setDailyPartitioningRecentDays(365 * 10 + 3);  // TODO: analyze the last 10 years of data
        }
        else if (checkTimeScale == CheckTimeScale.monthly) {
            userTimeWindowFilters.setMonthlyPartitioningRecentMonths(12 * 10);   // TODO: analyze the last 10 years of data
        }

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(null, connectionSpec, tableSpec, columnSpec,
                checkSpec, null, CheckType.partitioned, null, null,
                timeSeriesConfigurationSpec, userTimeWindowFilters, dialectSettings);
        return sensorExecutionRunParameters;
    }
}
