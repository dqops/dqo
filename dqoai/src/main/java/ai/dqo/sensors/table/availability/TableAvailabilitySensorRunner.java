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
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorPrepareResult;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;
import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import ai.dqo.execution.sqltemplates.rendering.JinjaSqlTemplateSensorRunner;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

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

    /**
     * The default injection constructor.
     * @param jinjaSqlTemplateSensorRunner Nested Jinja2 template based sensor runner that is called (delegated to), before this table availability sensor runner performs its calculations, adjusting the returned sensor result.
     * @param defaultTimeZoneProvider The default time zone provider.
     */
    @Autowired
    public TableAvailabilitySensorRunner(JinjaSqlTemplateSensorRunner jinjaSqlTemplateSensorRunner,
                                         DefaultTimeZoneProvider defaultTimeZoneProvider) {
        super(defaultTimeZoneProvider);
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
                    Table resultTableWithResult = createResultTableWithResult(1L);
                    return new SensorExecutionResult(sensorRunParameters, resultTableWithResult);
                }
            } catch (Exception exception) {
                Table resultTable = createResultTableWithResult(0L);
                return new SensorExecutionResult(sensorRunParameters, resultTable);
            }
            return sensorExecutionResult;
        }

        Table resultTable = createResultTableWithResult(0L);
        return new SensorExecutionResult(sensorRunParameters, resultTable);
    }
}
