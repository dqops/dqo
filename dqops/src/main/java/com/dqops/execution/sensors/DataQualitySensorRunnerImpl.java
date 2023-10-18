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

import com.dqops.connectors.ProviderType;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.finder.SensorDefinitionFindService;
import com.dqops.execution.sensors.grouping.AbstractGroupedSensorExecutor;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.grouping.PreparedSensorsGroup;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;
import com.dqops.execution.sensors.runners.AbstractSensorRunner;
import com.dqops.execution.sensors.runners.SensorRunnerFactory;
import com.dqops.execution.sqltemplates.grouping.FragmentedSqlQuery;
import com.dqops.execution.sqltemplates.grouping.SqlQueryFragmentsParser;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Data quality sensor preparation and running service. Prepares a sensor for execution and calls the runner.
 */
@Component
@Slf4j
public class DataQualitySensorRunnerImpl implements DataQualitySensorRunner {
    private final SensorDefinitionFindService sensorDefinitionFindService;
    private final SensorRunnerFactory sensorRunnerFactory;
    private final SqlQueryFragmentsParser sqlQueryFragmentsParser;

    /**
     * Creates a sensor runner.
     * @param sensorDefinitionFindService Sensor definition finder that finds the correct sensor definition.
     * @param sensorRunnerFactory Sensor runner factory. Creates instances of requested sensor runners.
     * @param sqlQueryFragmentsParser SQL query fragment parser, used to find similar sql query fragments for matching sensor queries that could be combined.
     */
    @Autowired
    public DataQualitySensorRunnerImpl(SensorDefinitionFindService sensorDefinitionFindService,
                                       SensorRunnerFactory sensorRunnerFactory,
                                       SqlQueryFragmentsParser sqlQueryFragmentsParser) {
        this.sensorDefinitionFindService = sensorDefinitionFindService;
        this.sensorRunnerFactory = sensorRunnerFactory;
        this.sqlQueryFragmentsParser = sqlQueryFragmentsParser;
    }

    /**
     * Prepare the sensor before it is executed on the data source.
     * @param executionContext DQOps execution context that provides access to the DQOps system and user home.
     * @param sensorRunParameters Sensor run parameters (connection, table, column, sensor parameters).
     * @param progressListener Progress lister that receives information about the progress of a sensor execution.
     * @return Sensor preparation result with a rendered sensor.
     */
    @Override
    public SensorPrepareResult prepareSensor(ExecutionContext executionContext,
                                             SensorExecutionRunParameters sensorRunParameters,
                                             SensorExecutionProgressListener progressListener) {
        String sensorName = sensorRunParameters.getEffectiveSensorRuleNames().getSensorName();
        ProviderType providerType = sensorRunParameters.getConnection().getProviderType();

        SensorDefinitionFindResult sensorDefinition = this.sensorDefinitionFindService.findProviderSensorDefinition(
                executionContext, sensorName, providerType);
        ProviderSensorDefinitionSpec providerSensorSpec = sensorDefinition.getProviderSensorDefinitionSpec();
        AbstractSensorRunner sensorRunner = this.sensorRunnerFactory.getSensorRunner(providerSensorSpec.getType(),
                providerSensorSpec.getJavaClassName());

        try {
            SensorPrepareResult sensorPrepareResult = sensorRunner.prepareSensor(executionContext, sensorRunParameters, sensorDefinition, progressListener);
            String renderedSensorSql = sensorPrepareResult.getRenderedSensorSql();
            if (renderedSensorSql != null) {
                if (renderedSensorSql.contains(sensorRunParameters.getActualValueAlias())) {
                    sensorPrepareResult.setActualValueAlias(sensorRunParameters.getActualValueAlias());
                }
                else if (!SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME.equals(sensorRunParameters.getActualValueAlias()) &&
                    renderedSensorSql.contains(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME)) {
                    sensorPrepareResult.setActualValueAlias(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
                }

                if (renderedSensorSql.contains(sensorRunParameters.getExpectedValueAlias())) {
                    sensorPrepareResult.setExpectedValueAlias(sensorRunParameters.getExpectedValueAlias());
                }
                else if (!SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME.equals(sensorRunParameters.getExpectedValueAlias()) &&
                        renderedSensorSql.contains(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME)) {
                    sensorPrepareResult.setExpectedValueAlias(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);
                }

                FragmentedSqlQuery fragmentedSqlQuery = this.sqlQueryFragmentsParser.parseQueryToComponents(renderedSensorSql,
                        sensorPrepareResult.getActualValueAlias(), sensorPrepareResult.getExpectedValueAlias());
                sensorPrepareResult.setFragmentedSqlQuery(fragmentedSqlQuery);
            }

            return sensorPrepareResult;
        }
        catch (Throwable ex) {
            log.error("Sensor failed to render, sensor name: " + sensorName, ex);
            return SensorPrepareResult.createForPrepareException(sensorRunParameters, sensorDefinition, ex);
        }
    }

    /**
     * Executes a sensor and returns the sensor result as a table returned from the query.
     * @param executionContext Check execution context that provides access to the user home and dqo home.
     * @param sensorPrepareResult Sensor preparation object with the information prepared by the sensor runner before it can execute the sensor.
     * @param progressListener Progress lister that receives information about the progress of a sensor execution.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, used to cancel a running sensor query.
     * @return Sensor execution result with the query result from the sensor.
     */
    @Override
    public SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                               SensorPrepareResult sensorPrepareResult,
                                               SensorExecutionProgressListener progressListener,
                                               boolean dummySensorExecution,
                                               JobCancellationToken jobCancellationToken) {
        AbstractSensorRunner sensorRunner = sensorPrepareResult.getSensorRunner();

        SensorExecutionResult result = sensorRunner.executeSensor(executionContext, sensorPrepareResult,
                progressListener, dummySensorExecution, jobCancellationToken);
        return result;
    }

    /**
     * Executes a sensor and returns the sensor result as a table returned from the query.
     *
     * @param executionContext     Check execution context that provides access to the user home and dqo home.
     * @param preparedSensorsGroup Object with a list of merged prepared sensors that will be executed by a single call to the data source.
     * @param progressListener     Progress lister that receives information about the progress of a sensor execution.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, used to cancel a running sensor query.
     * @return Collection of sensor group execution result with the query result from an executor. May contain results of multiple sensors that were merged.
     * The result is a collection because when a sensor fails, then the query could be split and executed again.
     */
    @Override
    public List<GroupedSensorExecutionResult> executeGroupedSensors(ExecutionContext executionContext,
                                                                    PreparedSensorsGroup preparedSensorsGroup,
                                                                    SensorExecutionProgressListener progressListener,
                                                                    boolean dummySensorExecution,
                                                                    JobCancellationToken jobCancellationToken) {
        AbstractGroupedSensorExecutor sensorExecutor = preparedSensorsGroup.getFirstSensorPrepareResult().getSensorExecutor();
        GroupedSensorExecutionResult result = sensorExecutor.executeGroupedSensor(executionContext, preparedSensorsGroup,
                progressListener, dummySensorExecution, jobCancellationToken);

        if (result.isSuccess() || !preparedSensorsGroup.isSplittable()) {
            return List.of(result);
        }

        ArrayList<GroupedSensorExecutionResult> resultsFromSplits = new ArrayList<>();
        PreparedSensorsGroup[] sensorGroups = preparedSensorsGroup.split();
        for (PreparedSensorsGroup smallerGroup : sensorGroups) {
            jobCancellationToken.throwIfCancelled();

            List<GroupedSensorExecutionResult> smallerSensorGroupResults = this.executeGroupedSensors(executionContext,
                    smallerGroup, progressListener, dummySensorExecution, jobCancellationToken);
            resultsFromSplits.addAll(smallerSensorGroupResults);
        }

        return resultsFromSplits;
    }
}
