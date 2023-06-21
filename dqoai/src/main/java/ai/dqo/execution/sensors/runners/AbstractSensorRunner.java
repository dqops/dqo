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
package ai.dqo.execution.sensors.runners;

import ai.dqo.core.jobqueue.JobCancellationToken;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorPrepareResult;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.grouping.GroupedSensorExecutionResult;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import tech.tablesaw.api.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Base abstract class for sensor runners. Executes a sensor given a target data quality check.
 */
public abstract class AbstractSensorRunner {
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Dependency injection constructor.
     * @param defaultTimeZoneProvider The default time zone provider.
     */
    protected AbstractSensorRunner(DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Executes a sensor and returns the sensor result.
     * @param executionContext      Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorPrepareResult   Sensor preparation result, contains the sensor definition, rendered SQL template, sensor run parameters (connection, table, etc.).
     * @param progressListener      Progress listener that receives events when the sensor is executed.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken  Job cancellation token, may cancel a running query.
     * @return Sensor result.
     */
    @Deprecated
    public abstract SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                                        SensorPrepareResult sensorPrepareResult,
                                                        SensorExecutionProgressListener progressListener,
                                                        boolean dummySensorExecution,
                                                        JobCancellationToken jobCancellationToken);

    /**
     * Prepares a sensor for execution. SQL templated sensors will render the SQL template, filled with the table and column names.
     * @param executionContext    Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorDefinition    Sensor definition that was found in the dqo home or the user home.
     * @param progressListener    Progress listener that receives events when the sensor is executed.
     */
    public abstract SensorPrepareResult prepareSensor(ExecutionContext executionContext,
                                                      SensorExecutionRunParameters sensorRunParameters,
                                                      SensorDefinitionFindResult sensorDefinition,
                                                      SensorExecutionProgressListener progressListener);

    /**
     * Transforms the sensor result that was captured by the sensor executor. This method performs de-grouping of grouped sensors that were executed as multiple SQL queries merged into one big query.
     * @param executionContext      Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param groupedSensorExecutionResult Sensor execution result with the data retrieved from the data source. It will be adapted to a sensor result for one sensor.
     * @param sensorPrepareResult   Original sensor prepare results for this sensor. Contains also the sensor run parameters.
     * @param progressListener      Progress listener that receives events when the sensor is executed.
     * @param jobCancellationToken  Job cancellation token, may cancel a running query.
     * @return Sensor result for one sensor.
     */
    public abstract SensorExecutionResult extractSensorResults(ExecutionContext executionContext,
                                                               GroupedSensorExecutionResult groupedSensorExecutionResult,
                                                               SensorPrepareResult sensorPrepareResult,
                                                               SensorExecutionProgressListener progressListener,
                                                               JobCancellationToken jobCancellationToken);

    /**
     * Creates a local date time of NOW, using the default configured time zone.
     * @return The local date time of NOW, based on the configured default time zone.
     */
    protected LocalDateTime getNowAtDefaultTimeZone() {
        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
        return Instant.now().atZone(defaultTimeZoneId).toLocalDateTime();
    }

    /**
     * Creates a result table given a single result to be stored.
     * @param actualValue Sensor execution actual value.
     * @return Dummy result table.
     */
    public Table createResultTableWithResult(Integer actualValue) {
        Table dummyResultTable = Table.create("dummy_results",
                IntColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setInt(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, getNowAtDefaultTimeZone());
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, Instant.now());

        return dummyResultTable;
    }

    /**
     * Creates a result table given a single result to be stored.
     * @param actualValue Sensor execution actual value.
     * @return Dummy result table.
     */
    public Table createResultTableWithResult(Long actualValue) {
        Table dummyResultTable = Table.create("dummy_results",
                LongColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setLong(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, getNowAtDefaultTimeZone());
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, Instant.now());

        return dummyResultTable;
    }

    /**
     * Creates a result table given a single result to be stored.
     * @param actualValue Sensor execution actual value.
     * @return Dummy result table.
     */
    public Table createResultTableWithResult(Double actualValue) {
        Table dummyResultTable = Table.create("dummy_results",
                DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setDouble(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, getNowAtDefaultTimeZone());
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, Instant.now());

        return dummyResultTable;
    }

    /**
     * Creates a one row dummy result table, used when a dummy sensor run is requested (without running the query on the data source).
     * @param sensorRunParameters Sensor execution run parameters.
     * @return Dummy result table.
     */
    public Table createDummyResultTable(SensorExecutionRunParameters sensorRunParameters) {
        Table dummyResultTable = Table.create("dummy_results",
                DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setDouble(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, 10.0);

        TimeSeriesConfigurationSpec effectiveTimeSeries = sensorRunParameters.getTimeSeries();
        if (effectiveTimeSeries != null && effectiveTimeSeries.getMode() != null) {
            row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, getNowAtDefaultTimeZone());
            row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, Instant.now());
        }

        return dummyResultTable;
    }
}
