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

import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.grouping.AbstractGroupedSensorExecutor;
import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import ai.dqo.execution.sqltemplates.grouping.FragmentedSqlQuery;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Result object that stores all objects required to execute a data quality sensor before it can be executed on the data source connection.
 * For sensors based on SQL templates, it is a rendered SQL template.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SensorPrepareResult {
    private SensorExecutionRunParameters sensorRunParameters;
    private boolean success = true;
    private Throwable prepareException;
    private SensorDefinitionFindResult sensorDefinition;
    private AbstractGroupedSensorExecutor sensorExecutor;
    private AbstractSensorRunner sensorRunner;
    private String renderedSensorSql;
    private String actualValueAlias = SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME;
    private String expectedValueAlias = SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME;
    private FragmentedSqlQuery fragmentedSqlQuery;


    public SensorPrepareResult() {
    }

    /**
     * Creates a sensor prepare result.
     * @param sensorRunParameters Sensor run parameters used to run the sensor.
     * @param sensorDefinition Sensor definition with the sensor configuration and the SQL template (for templated sensors).
     * @param sensorExecutor Sensor executor that will execute the sensor, also supporting grouping of sensors whose SQLs could be merged together.
     * @param sensorRunner  Sensor runner instance that will parse the sensor result from the sensor executor to adapt it to a tabular format.
     */
    public SensorPrepareResult(SensorExecutionRunParameters sensorRunParameters,
                               SensorDefinitionFindResult sensorDefinition,
                               AbstractGroupedSensorExecutor sensorExecutor,
                               AbstractSensorRunner sensorRunner) {
        this.sensorRunParameters = sensorRunParameters;
        this.sensorDefinition = sensorDefinition;
        this.sensorExecutor = sensorExecutor;
        this.sensorRunner = sensorRunner;
    }

    /**
     * Creates a sensor prepare result, including the rendered SQL.
     * @param sensorRunParameters Sensor run parameters used to run the sensor.
     * @param sensorDefinition Sensor definition with the sensor configuration and the SQL template (for templated sensors).
     * @param sensorRunner  Sensor runner instance that will run the sensor.
     * @param sensorExecutor Sensor executor that will execute the sensor, also supporting grouping of sensors whose SQLs could be merged together.
     * @param renderedSensorSql Rendered sensor SQL.
     */
    public SensorPrepareResult(SensorExecutionRunParameters sensorRunParameters,
                               SensorDefinitionFindResult sensorDefinition,
                               AbstractGroupedSensorExecutor sensorExecutor,
                               AbstractSensorRunner sensorRunner,
                               String renderedSensorSql) {
        this(sensorRunParameters, sensorDefinition, sensorExecutor, sensorRunner);
        this.renderedSensorSql = renderedSensorSql;
    }

    /**
     * Creates a sensor prepare result when the sensor failed to render.
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorDefinition Sensor definition - for reference.
     * @param prepareException Exception that was thrown during the preparation.
     * @return Sensor prepare result with an error.
     */
    public static SensorPrepareResult createForPrepareException(SensorExecutionRunParameters sensorRunParameters,
                                                                SensorDefinitionFindResult sensorDefinition,
                                                                Throwable prepareException) {
        return new SensorPrepareResult(sensorRunParameters, sensorDefinition, null, null) {{
            setSuccess(false);
            setPrepareException(prepareException);
        }};
    }

    /**
     * Returns the sensor execution parameters that will be used to execute the sensor.
     * @return Sensor execution run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Sets the sensor execution run parameters with all details required to execute the sensor.
     * @param sensorRunParameters Sensor run parameters.
     */
    public void setSensorRunParameters(SensorExecutionRunParameters sensorRunParameters) {
        this.sensorRunParameters = sensorRunParameters;
    }

    /**
     * Returns true if the sensor managed to prepare (render the SQL template for templated sensors). When the success is false, the exception should provide more detailed information about the issue.
     * @return True when the sensor managed to prepare.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status if the check managed to prepare for execution. When the success is false, the exception should provide more detailed information about the issue.
     * @param success True when the sensor successfully prepared, false when it failed - a problem with the SQL sensor.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns an exception if the sensor failed to prepare.
     * @return An exception or null when no exception was raised.
     */
    public Throwable getPrepareException() {
        return prepareException;
    }

    /**
     * Stores an exception that was caught.
     * @param prepareException Exception that was caught.
     */
    public void setPrepareException(Throwable prepareException) {
        if (prepareException != null) {
            this.success = false;
        }
        this.prepareException = prepareException;
    }

    /**
     * Returns the sensor definition that was found in DQO.
     * @return Sensor definition that was found.
     */
    public SensorDefinitionFindResult getSensorDefinition() {
        return sensorDefinition;
    }

    /**
     * Sets the sensor definition that will be used.
     * @param sensorDefinition The sensor definition to be used.
     */
    public void setSensorDefinition(SensorDefinitionFindResult sensorDefinition) {
        this.sensorDefinition = sensorDefinition;
    }

    /**
     * Returns an instance of a sensor executor that will execute the query or capture metrics that would be transformed
     * further into a tabular format by the sensor runner.
     * @return Sensor executor that supports grouping and only knows how to call the data source to get raw metrics.
     */
    public AbstractGroupedSensorExecutor getSensorExecutor() {
        return sensorExecutor;
    }

    /**
     * Sets the grouped sensor executor instance that will run the sensor and collect the metrics directly on the data source.
     * @param sensorExecutor Sensor executor instance.
     */
    public void setSensorExecutor(AbstractGroupedSensorExecutor sensorExecutor) {
        this.sensorExecutor = sensorExecutor;
    }

    /**
     * Returns a sensor runner that will be used to parse the sensor results from the executor.
     */
    public AbstractSensorRunner getSensorRunner() {
        return sensorRunner;
    }

    /**
     * Sets a reference to a sensor runner that will be used to parse the sensor results from the executor.
     * @param sensorRunner Sensor runner to be used.
     */
    public void setSensorRunner(AbstractSensorRunner sensorRunner) {
        this.sensorRunner = sensorRunner;
    }

    /**
     * Returns a rendered SQL template that should be executed on the monitored data source.
     * @return Rendered SQL.
     */
    public String getRenderedSensorSql() {
        return renderedSensorSql;
    }

    /**
     * Sets the rendered SQL that will be executed on the monitored data source.
     * @param renderedSensorSql Rendered sensor SQL.
     */
    public void setRenderedSensorSql(String renderedSensorSql) {
        this.renderedSensorSql = renderedSensorSql;
    }

    /**
     * Returns the real alias of an actual_value column that contains the output column with the sensor readout.
     * It could be different than the default "actual_value" when query merging is used to run a single SQL for multiple sensors.
     * @return The column name (alias) from the results that contains the actual_value.
     */
    public String getActualValueAlias() {
        return actualValueAlias;
    }

    /**
     * Sets the alias of the column that contains the actual_value (the sensor readout value).
     * @param actualValueAlias The column name (alias) from the results that contains the actual_value.
     */
    public void setActualValueAlias(String actualValueAlias) {
        this.actualValueAlias = actualValueAlias;
    }

    /**
     * Returns the real alias of an expected_value column that contains the output column with the sensor readout's expected value.
     * It could be different than the default "expected_value" when query merging is used to run a single SQL for multiple sensors.
     * @return The column name (alias) from the results that contains the expected_value.
     */
    public String getExpectedValueAlias() {
        return expectedValueAlias;
    }

    /**
     * Sets the alias of the column that contains the expected_value (the expected value that is a secondary sensor readout value).
     * @param expectedValueAlias The column name (alias) from the results that contains the expected_value.
     */
    public void setExpectedValueAlias(String expectedValueAlias) {
        this.expectedValueAlias = expectedValueAlias;
    }

    /**
     * The SQL query that is parsed into SQL fragments usable for matching to similar queries that could be merged into a bigger query.
     * @return Fragmented sql query.
     */
    public FragmentedSqlQuery getFragmentedSqlQuery() {
        return fragmentedSqlQuery;
    }

    /**
     * Sets a parsed query that is divided into SQL fragments.
     * @param fragmentedSqlQuery SQL query divided into fragments.
     */
    public void setFragmentedSqlQuery(FragmentedSqlQuery fragmentedSqlQuery) {
        this.fragmentedSqlQuery = fragmentedSqlQuery;
    }
}
