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
package ai.dqo.sensors.table.availability;

import ai.dqo.core.jobqueue.JobCancellationToken;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorPrepareResult;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;
import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import ai.dqo.execution.sqltemplates.JinjaSqlTemplateSensorRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Sensor runner that transforms an SQL template and executes a generated SQL on a connection.
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TableAvailabilitySensorRunner extends AbstractSensorRunner {
    /**
     * Sensor runner class name.
     */
    public static final String CLASS_NAME = TableAvailabilitySensorRunner.class.getName();
    private JinjaSqlTemplateSensorRunner jinjaSqlTemplateSensorRunner;

    @Autowired
    public TableAvailabilitySensorRunner(JinjaSqlTemplateSensorRunner jinjaSqlTemplateSensorRunner){
        this.jinjaSqlTemplateSensorRunner = jinjaSqlTemplateSensorRunner;
    }

    /**
     * Prepares a sensor for execution. SQL templated sensors will render the SQL template, filled with the table and column names.
     *
     * @param executionContext    Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorPrepareResult Sensor prepare result with additional sensor run parameters. The prepareSensor method should fill additional values in this object that will be used when the sensor is executed.
     * @param progressListener    Progress listener that receives events when the sensor is executed.
     */
    @Override
    public void prepareSensor(ExecutionContext executionContext,
                              SensorPrepareResult sensorPrepareResult,
                              SensorExecutionProgressListener progressListener) {
        this.jinjaSqlTemplateSensorRunner.prepareSensor(executionContext, sensorPrepareResult, progressListener);
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
        SensorExecutionResult sensorExecutionResult = this.jinjaSqlTemplateSensorRunner.executeSensor(executionContext,
                sensorPrepareResult, progressListener, dummySensorExecution, jobCancellationToken);

        if (sensorExecutionResult.isSuccess()) {
            try {
                if (sensorExecutionResult.getResultTable().column(0).get(0) == null) {
                    Table dummyResultTable = createDummyResultTable(1L);
                    return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
                }
            } catch (Exception exception) {
                Table dummyResultTable = createDummyResultTable(0L);
                return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
            }
            return sensorExecutionResult;
        }

        Table dummyResultTable = createDummyResultTable(0L);
        return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
    }

    /**
     * Creates a one row dummy result table.
     * @param actualValue Sensor execution actual value.
     * @return Dummy result table.
     */
    public Table createDummyResultTable(Long actualValue) {
        Table dummyResultTable = Table.create("dummy_results",
                LongColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setLong(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, LocalDateTime.now());
        row.setInstant(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME, Instant.now());

        return dummyResultTable;
    }
}
