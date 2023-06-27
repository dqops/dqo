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

package ai.dqo.execution.sensors.grouping;

import ai.dqo.core.jobqueue.JobCancellationToken;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.SensorPrepareResult;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import tech.tablesaw.api.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Implementation of the sensor executor. It is used for grouping similar sensors that can
 * be executed only once on a data source. The sensor executor executes a sensor and returns raw result
 * that must be adapted by the sensor runner. The sensor runner can extract correct columns (when multiple aliased "actual_value" columns are returned).
 */
public abstract class AbstractGroupedSensorExecutor {
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Default constructor.
     * @param defaultTimeZoneProvider Default time zone provider, used to generate time period for fake results.
     */
    protected AbstractGroupedSensorExecutor(DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Executes a sensor and returns the sensor result.
     * @param executionContext      Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param preparedSensorsGroup  Prepared sensor group. Contains one or multiple similar prepared sensor results that will use the same query to capture all metrics at once.
     * @param progressListener      Progress listener that receives events when the sensor is executed.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken  Job cancellation token, may cancel a running query.
     * @return Sensor result.
     */
    public abstract GroupedSensorExecutionResult executeGroupedSensor(ExecutionContext executionContext,
                                                                      PreparedSensorsGroup preparedSensorsGroup,
                                                                      SensorExecutionProgressListener progressListener,
                                                                      boolean dummySensorExecution,
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
     * Creates a result table given a value to be stored. Stores this value for all output columns.
     * @param actualValue Sensor execution actual value.
     * @param preparedSensorsGroup Prepared sensor group with all prepared sensors, with the column aliases to return.
     * @return Dummy result table with multiple actual value columns.
     */
    public Table createResultTableWithResult(Integer actualValue, PreparedSensorsGroup preparedSensorsGroup) {
        Table dummyResultTable = Table.create("dummy_results");

        for (SensorPrepareResult sensorPrepareResult : preparedSensorsGroup.getPreparedSensors()) {
            IntColumn actualValueColumn = IntColumn.create(sensorPrepareResult.getActualValueAlias());
            dummyResultTable.addColumns(actualValueColumn);
        }

        dummyResultTable.addColumns(
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));

        Row row = dummyResultTable.appendRow();

        for (SensorPrepareResult sensorPrepareResult : preparedSensorsGroup.getPreparedSensors()) {
            row.setInt(sensorPrepareResult.getActualValueAlias(), actualValue);
        }

        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, getNowAtDefaultTimeZone());
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, Instant.now());

        return dummyResultTable;
    }

    /**
     * Creates a result table given a single result to be stored.
     * @param actualValue Sensor execution actual value.
     * @param preparedSensorsGroup Prepared sensor group with all prepared sensors, with the column aliases to return.
     * @return Dummy result table.
     */
    public Table createResultTableWithResult(Long actualValue, PreparedSensorsGroup preparedSensorsGroup) {
        Table dummyResultTable = Table.create("dummy_results");

        for (SensorPrepareResult sensorPrepareResult : preparedSensorsGroup.getPreparedSensors()) {
            LongColumn actualValueColumn = LongColumn.create(sensorPrepareResult.getActualValueAlias());
            dummyResultTable.addColumns(actualValueColumn);
        }

        dummyResultTable.addColumns(
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));

        Row row = dummyResultTable.appendRow();

        for (SensorPrepareResult sensorPrepareResult : preparedSensorsGroup.getPreparedSensors()) {
            row.setLong(sensorPrepareResult.getActualValueAlias(), actualValue);
        }

        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, getNowAtDefaultTimeZone());
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, Instant.now());

        return dummyResultTable;
    }

    /**
     * Creates a result table given a single result to be stored.
     * @param actualValue Sensor execution actual value.
     * @param preparedSensorsGroup Prepared sensor group with all prepared sensors, with the column aliases to return.
     * @return Dummy result table.
     */
    public Table createResultTableWithResult(Double actualValue, PreparedSensorsGroup preparedSensorsGroup) {
        Table dummyResultTable = Table.create("dummy_results");

        for (SensorPrepareResult sensorPrepareResult : preparedSensorsGroup.getPreparedSensors()) {
            DoubleColumn actualValueColumn = DoubleColumn.create(sensorPrepareResult.getActualValueAlias());
            dummyResultTable.addColumns(actualValueColumn);
        }

        dummyResultTable.addColumns(
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));

        Row row = dummyResultTable.appendRow();

        for (SensorPrepareResult sensorPrepareResult : preparedSensorsGroup.getPreparedSensors()) {
            row.setDouble(sensorPrepareResult.getActualValueAlias(), actualValue);
        }

        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, getNowAtDefaultTimeZone());
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, Instant.now());

        return dummyResultTable;
    }

    /**
     * Creates a result table with a sample value.
     * @param preparedSensorsGroup Prepared sensor group with all prepared sensors, with the column aliases to return.
     * @return Dummy result table.
     */
    public Table createResultTableWithResult(PreparedSensorsGroup preparedSensorsGroup) {
        return createResultTableWithResult(10.0, preparedSensorsGroup);
    }
}
