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
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.SourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.statistics.factory.StatisticsColumnNames;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorPrepareResult;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.progress.ExecutingSqlOnConnectionEvent;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;
import com.dqops.execution.sensors.runners.AbstractSensorRunner;
import com.dqops.execution.sensors.runners.GenericSensorResultsFactory;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.utils.logging.UserErrorLogger;
import com.dqops.utils.tables.TableColumnUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.util.List;
import java.util.Objects;

/**
 * Sensor runner that transforms an SQL template and executes a generated SQL on a connection.
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JinjaSqlTemplateSensorRunner extends AbstractSensorRunner {
    /**
     * Sensor runner class name.
     */
    public static final String CLASS_NAME = JinjaSqlTemplateSensorRunner.class.getName();
    private final JinjaTemplateRenderService jinjaTemplateRenderService;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final JinjaSqlTemplateSensorExecutor jinjaSqlTemplateSensorExecutor;
    private final UserErrorLogger userErrorLogger;

    /**
     * Creates a sql template runner.
     * @param jinjaTemplateRenderService Jinja template rendering service.
     * @param connectionProviderRegistry Connection provider registry.
     * @param defaultTimeZoneProvider The default time zone provider.
     * @param userErrorLogger Check execution logger.
     */
    @Autowired
    public JinjaSqlTemplateSensorRunner(JinjaTemplateRenderService jinjaTemplateRenderService,
                                        ConnectionProviderRegistry connectionProviderRegistry,
                                        DefaultTimeZoneProvider defaultTimeZoneProvider,
                                        JinjaSqlTemplateSensorExecutor jinjaSqlTemplateSensorExecutor,
                                        UserErrorLogger userErrorLogger) {
        super(defaultTimeZoneProvider);
        this.jinjaTemplateRenderService = jinjaTemplateRenderService;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.jinjaSqlTemplateSensorExecutor = jinjaSqlTemplateSensorExecutor;
        this.userErrorLogger = userErrorLogger;
    }

    /**
     * Prepares a sensor for execution. SQL templated sensors will render the SQL template, filled with the table and column names.
     *
     * @param executionContext    Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorDefinition    Sensor definition that was found in the dqo home or the user home.
     * @param progressListener    Progress listener that receives events when the sensor is executed.
     */
    @Override
    public SensorPrepareResult prepareSensor(ExecutionContext executionContext,
                                             SensorExecutionRunParameters sensorRunParameters,
                                             SensorDefinitionFindResult sensorDefinition,
                                             SensorExecutionProgressListener progressListener) {
        String renderedSql = null;
        try {
            JinjaTemplateRenderParameters templateRenderParameters = JinjaTemplateRenderParameters.createFromTrimmedObjects(
                    sensorRunParameters, sensorDefinition);
            renderedSql = this.jinjaTemplateRenderService.renderTemplate(executionContext, sensorDefinition,
                    templateRenderParameters, progressListener);

            ProviderSensorDefinitionSpec providerSensorDefinitionSpec = sensorDefinition.getProviderSensorDefinitionSpec();

            return new SensorPrepareResult(sensorRunParameters, sensorDefinition,
                    this.jinjaSqlTemplateSensorExecutor, this, renderedSql, providerSensorDefinitionSpec.isDisableMergingQueries());
        }
        catch (Throwable exception) {
            this.userErrorLogger.logSensor("Sensor failed to render an sql template :" + renderedSql, exception);
            return SensorPrepareResult.createForPrepareException(sensorRunParameters, sensorDefinition, exception);
        }
    }

    /**
     * Executes a sensor and returns the sensor result.
     *
     * @param executionContext     Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorPrepareResult  Sensor preparation result, contains the sensor definition, rendered SQL template, sensor run parameters (connection, table, etc.).
     * @param progressListener     Progress listener that receives events when the sensor is executed.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, may cancel a running query.
     * @return Sensor result.
     */
    @Override
    public SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                               SensorPrepareResult sensorPrepareResult,
                                               SensorExecutionProgressListener progressListener,
                                               boolean dummySensorExecution,
                                               JobCancellationToken jobCancellationToken) {
        SensorExecutionRunParameters sensorRunParameters = sensorPrepareResult.getSensorRunParameters();
        String renderedSensorSql = sensorPrepareResult.getRenderedSensorSql();

        try {
            if (!dummySensorExecution) {
                ConnectionSpec connectionSpec = sensorRunParameters.getConnection();
                progressListener.onExecutingSqlOnConnection(new ExecutingSqlOnConnectionEvent(sensorRunParameters,
                        sensorPrepareResult.getSensorDefinition(), connectionSpec, renderedSensorSql));

                jobCancellationToken.throwIfCancelled();
                ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
                SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(executionContext.getUserHomeContext().getUserHome());
                try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true, secretValueLookupContext)) {
                    jobCancellationToken.throwIfCancelled();
                    Table sensorResultRows = sourceConnection.executeQuery(renderedSensorSql, jobCancellationToken,
                            sensorRunParameters.getRowCountLimit(),
                            sensorRunParameters.isFailOnSensorReadoutLimitExceeded());
                    Double defaultValue = sensorPrepareResult.getSensorDefinition().getSensorDefinitionSpec().getDefaultValue();
                    if (sensorResultRows.rowCount() == 0 && defaultValue != null) {
                        Table defaultValueResultTable = GenericSensorResultsFactory.createResultTableWithResult(defaultValue,
                                this.defaultTimeZoneProvider.getDefaultTimeZoneId(), sensorRunParameters.getTimePeriodGradient());
                        return new SensorExecutionResult(sensorRunParameters, defaultValueResultTable);
                    }

                    return new SensorExecutionResult(sensorRunParameters, sensorResultRows);
                }
            }

            Table dummyResultTable = GenericSensorResultsFactory.createDummyResultTable(sensorRunParameters,
                    this.defaultTimeZoneProvider.getDefaultTimeZoneId());
            return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
        }
        catch (Throwable exception) {
            this.userErrorLogger.logSensor(sensorRunParameters.toString() + " failed to execute a query: " + renderedSensorSql +
                        ", error: " + exception.getMessage(), exception);
            return new SensorExecutionResult(sensorRunParameters, exception);
        }
    }

    /**
     * Transforms the sensor result that was captured by the sensor executor. This method performs de-grouping of grouped sensors that were executed as multiple SQL queries merged into one big query.
     *
     * @param executionContext             Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param groupedSensorExecutionResult Sensor execution result with the data retrieved from the data source. It will be adapted to a sensor result for one sensor.
     * @param sensorPrepareResult          Original sensor prepare results for this sensor. Contains also the sensor run parameters.
     * @param progressListener             Progress listener that receives events when the sensor is executed.
     * @param jobCancellationToken         Job cancellation token, may cancel a running query.
     * @return Sensor result for one sensor.
     */
    @Override
    public SensorExecutionResult extractSensorResults(ExecutionContext executionContext,
                                                      GroupedSensorExecutionResult groupedSensorExecutionResult,
                                                      SensorPrepareResult sensorPrepareResult,
                                                      SensorExecutionProgressListener progressListener,
                                                      JobCancellationToken jobCancellationToken) {
        if (!groupedSensorExecutionResult.isSuccess()) {
            return new SensorExecutionResult(sensorPrepareResult.getSensorRunParameters(), groupedSensorExecutionResult.getException());
        }

        Table multiSensorTableResult = groupedSensorExecutionResult.getTableResult();
        Table sensorResultRows = Table.create(multiSensorTableResult.name());

        Double sensorDefaultValuePlaceholder = sensorPrepareResult.getSensorDefinition().getSensorDefinitionSpec().getDefaultValue();
        if (multiSensorTableResult.rowCount() == 0 && sensorDefaultValuePlaceholder != null) {
            DoubleColumn defaultValueColumn = DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
            defaultValueColumn.append(sensorDefaultValuePlaceholder);
            sensorResultRows.addColumns(defaultValueColumn);
        } else {
            Column<?> actualValueColumn = TableColumnUtility.findColumn(multiSensorTableResult, sensorPrepareResult.getActualValueAlias());
            if (actualValueColumn != null) {
                if (!Objects.equals(actualValueColumn.name(), SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME)) {
                    actualValueColumn = actualValueColumn.copy();
                    actualValueColumn.setName(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
                }
                sensorResultRows.addColumns(actualValueColumn);
            }

            Column<?> expectedValueColumn = TableColumnUtility.findColumn(multiSensorTableResult, sensorPrepareResult.getExpectedValueAlias());
            if (expectedValueColumn != null) {
                if (!Objects.equals(expectedValueColumn.name(), SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME)) {
                    expectedValueColumn = expectedValueColumn.copy();
                    expectedValueColumn.setName(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);
                }
                sensorResultRows.addColumns(expectedValueColumn);
            }

            Column<?> timePeriodColumn = TableColumnUtility.findColumn(multiSensorTableResult, SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
            if (timePeriodColumn != null) {
                sensorResultRows.addColumns(timePeriodColumn);
            }
            Column<?> timePeriodUtcColumn = TableColumnUtility.findColumn(multiSensorTableResult, SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
            if (timePeriodUtcColumn != null) {
                sensorResultRows.addColumns(timePeriodUtcColumn);
            }

            // statistics (column sampling) specific columns
            Column<?> sampleCountColumn = TableColumnUtility.findColumn(multiSensorTableResult, StatisticsColumnNames.SAMPLE_COUNT_COLUMN_NAME);
            if (sampleCountColumn != null) {
                sensorResultRows.addColumns(sampleCountColumn);
            }
            Column<?> sampleIndexColumn = TableColumnUtility.findColumn(multiSensorTableResult, StatisticsColumnNames.SAMPLE_INDEX_COLUMN_NAME);
            if (sampleIndexColumn != null) {
                sensorResultRows.addColumns(sampleIndexColumn);
            }

            List<Column<?>> allSourceColumns = multiSensorTableResult.columns();
            Column<?>[] dataStreamLevelColumns = allSourceColumns.stream()
                    .filter(c -> c.name() != null && c.name().startsWith(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX))
                    .toArray(Column<?>[]::new);
            if (dataStreamLevelColumns.length > 0) {
                sensorResultRows.addColumns(dataStreamLevelColumns);
            }
        }

        return new SensorExecutionResult(sensorPrepareResult.getSensorRunParameters(), sensorResultRows);
    }
}
