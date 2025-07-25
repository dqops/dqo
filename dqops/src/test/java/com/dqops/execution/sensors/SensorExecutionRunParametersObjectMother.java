/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.ProviderDialectSettingsObjectMother;
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.sqltemplates.rendering.ErrorSamplingRenderParameters;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.dqohome.DqoHomeObjectMother;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.groupings.TimeSeriesConfigurationSpecObjectMother;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

import java.util.List;
import java.util.stream.Collectors;

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
                null, null, null, null, null, 1000, false, null, null);
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
                null, timeSeriesConfigurationSpec, null, dialectSettings, null);
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
                 timeSeriesConfigurationSpec, null, dialectSettings, null);
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
                timeSeriesConfigurationSpec, null, dialectSettings, null);
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
            userTimeWindowFilters.setDailyPartitioningIncludeToday(false);
        }
        else if (timeScale == CheckTimeScale.monthly) {
            userTimeWindowFilters.setMonthlyPartitioningRecentMonths(12 * 10);   // TODO: analyze the last 10 years of data
            userTimeWindowFilters.setMonthlyPartitioningIncludeCurrentMonth(false);
        }

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(null, connectionSpec, tableSpec, null,
                checkSpec, null, CheckType.partitioned, null, null,
                timeSeriesConfigurationSpec, userTimeWindowFilters, dialectSettings, null);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run an error sampling query to capture error samples.
     * @param sampleTableMetadata Sample table metadata.
     * @param checkSpec Check specification.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableForErrorSampling(
            SampleTableMetadata sampleTableMetadata,
            AbstractCheckSpec<?,?,?,?> checkSpec) {
        SensorExecutionRunParameters sensorExecutionRunParameters = createForTableForProfilingCheck(sampleTableMetadata, checkSpec);

        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        ErrorSamplingRenderParameters errorSamplingRenderParameters = new ErrorSamplingRenderParameters();
        List<String> idColumns = tableSpec.getColumns().values().stream()
                .filter(c -> c.isId())
                .map(c -> c.getColumnName())
                .collect(Collectors.toList());
        errorSamplingRenderParameters.setIdColumns(idColumns);

        sensorExecutionRunParameters.setErrorSamplingRenderParameters(errorSamplingRenderParameters);

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
                timeSeriesConfigurationSpec, null, dialectSettings, null);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run an profiling check on a given sample table, when a column is selected.
     * @param sampleTableMetadata Sample table metadata.
     * @param columnName Target column name.
     * @param statisticsCollectorSpec Check specification.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableColumnStatisticsCollectorAtTableScope(
            SampleTableMetadata sampleTableMetadata,
            String columnName,
            AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec) {
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ProviderDialectSettings dialectSettings = ProviderDialectSettingsObjectMother.getDialectForProvider(connectionSpec.getProviderType());
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        SensorExecutionRunParametersFactory factory = getFactory();
        DqoHome dqoHome = DqoHomeObjectMother.getDqoHome();

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createStatisticsSensorParameters(
                dqoHome, null, connectionSpec, tableSpec, columnSpec,
                statisticsCollectorSpec, null, StatisticsDataScope.table, dialectSettings);
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
                timeSeriesConfigurationSpec, null, dialectSettings, null);
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
                timeSeriesConfigurationSpec, null, dialectSettings, null);
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
            userTimeWindowFilters.setDailyPartitioningIncludeToday(false);
        }
        else if (checkTimeScale == CheckTimeScale.monthly) {
            userTimeWindowFilters.setMonthlyPartitioningRecentMonths(12 * 10);   // TODO: analyze the last 10 years of data
            userTimeWindowFilters.setMonthlyPartitioningIncludeCurrentMonth(false);
        }

        SensorExecutionRunParameters sensorExecutionRunParameters = factory.createSensorParameters(null, connectionSpec, tableSpec, columnSpec,
                checkSpec, null, CheckType.partitioned, null, null,
                timeSeriesConfigurationSpec, userTimeWindowFilters, dialectSettings, null);
        return sensorExecutionRunParameters;
    }

    /**
     * Creates a sensor run parameters object to run an error sampling query to capture error samples.
     * @param sampleTableMetadata Sample table metadata.
     * @param columnName Target column name.
     * @param checkSpec Check specification.
     * @return Sensor execution run parameters.
     */
    public static SensorExecutionRunParameters createForTableColumnForErrorSampling(
            SampleTableMetadata sampleTableMetadata,
            String columnName,
            AbstractCheckSpec<?,?,?,?> checkSpec) {
        SensorExecutionRunParameters sensorExecutionRunParameters = createForTableColumnForProfilingCheck(sampleTableMetadata, columnName, checkSpec);

        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        ErrorSamplingRenderParameters errorSamplingRenderParameters = new ErrorSamplingRenderParameters();
        List<String> idColumns = tableSpec.getColumns().values().stream()
                .filter(c -> c.isId())
                .map(c -> c.getColumnName())
                .collect(Collectors.toList());
        errorSamplingRenderParameters.setIdColumns(idColumns);

        sensorExecutionRunParameters.setErrorSamplingRenderParameters(errorSamplingRenderParameters);

        return sensorExecutionRunParameters;
    }
}
