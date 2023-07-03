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
package com.dqops.sensors.table.availability;

import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorPrepareResult;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;
import com.dqops.execution.sensors.runners.AbstractSensorRunner;
import com.dqops.execution.sqltemplates.rendering.JinjaSqlTemplateSensorRunner;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
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
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorDefinition    Sensor definition that was found in the dqo home or the user home.
     * @param progressListener    Progress listener that receives events when the sensor is executed.
     */
    @Override
    public SensorPrepareResult prepareSensor(ExecutionContext executionContext,
                                             SensorExecutionRunParameters sensorRunParameters,
                                             SensorDefinitionFindResult sensorDefinition,
                                             SensorExecutionProgressListener progressListener) {
        return this.jinjaSqlTemplateSensorRunner.prepareSensor(executionContext, sensorRunParameters, sensorDefinition, progressListener);
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
        SensorExecutionRunParameters sensorRunParameters = sensorPrepareResult.getSensorRunParameters();
        SensorExecutionResult sensorExecutionResult = this.jinjaSqlTemplateSensorRunner.extractSensorResults(executionContext,
                groupedSensorExecutionResult, sensorPrepareResult, progressListener, jobCancellationToken);

        if (sensorExecutionResult.isSuccess()) {
            try {
                if (sensorExecutionResult.getResultTable().column(0).get(0) == null) {
                    Table resultTableWithResult = createResultTableWithResult(1.0);
                    return new SensorExecutionResult(sensorRunParameters, resultTableWithResult);
                }
            } catch (Exception exception) {
                Table resultTable = createResultTableWithResult(0.0);
                return new SensorExecutionResult(sensorRunParameters, resultTable);
            }
            return sensorExecutionResult;
        }

        Table resultTable = createResultTableWithResult(0.0);
        return new SensorExecutionResult(sensorRunParameters, resultTable);
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
                    Table resultTableWithResult = createResultTableWithResult(1.0);
                    return new SensorExecutionResult(sensorRunParameters, resultTableWithResult);
                }
            } catch (Exception exception) {
                Table resultTable = createResultTableWithResult(0.0);
                return new SensorExecutionResult(sensorRunParameters, resultTable);
            }
            return sensorExecutionResult;
        }

        Table resultTable = createResultTableWithResult(0.0);
        return new SensorExecutionResult(sensorRunParameters, resultTable);
    }
}
