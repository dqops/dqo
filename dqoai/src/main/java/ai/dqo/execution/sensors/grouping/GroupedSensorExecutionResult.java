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

import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.metadata.sources.TableSpec;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Sensor execution result for a group of sensors that were executed as a single query. Contains the result of executing one query with multiple sensor results.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupedSensorExecutionResult {
    private PreparedSensorsGroup sensorGroup;
    private Table tableResult;
    private TableSpec capturedMetadataResult;
    private Object otherResult;
    private Instant finishedAt;
    private int sensorDurationMs;
    private boolean success;
    private Throwable exception;

    /**
     * Creates an empty sensor execution result object.
     */
    public GroupedSensorExecutionResult() {
		this.finishedAt = Instant.now();
    }

    /**
     * Creates a sensor execution result object with the sensor parameters and a tabular result returned as a query from the sensor.
     * @param sensorGroup Sensor group.
     * @param executionStartedAt Timestamp when the sensor was executed on the data source.
     * @param tableResult Sensor SELECT result table.
     */
    public GroupedSensorExecutionResult(PreparedSensorsGroup sensorGroup, Instant executionStartedAt, Table tableResult) {
        this.sensorGroup = sensorGroup;
        this.tableResult = tableResult;
		this.finishedAt = Instant.now();
		this.sensorDurationMs = (int)ChronoUnit.MILLIS.between(executionStartedAt, this.finishedAt);
        this.success = true;
    }

    /**
     * Creates a sensor execution result object with the sensor parameters and captured metadata of a table, for schema validation sensors (column count, column order).
     * @param sensorGroup             Sensor execution parameters.
     * @param executionStartedAt Timestamp when the sensor was executed on the data source.
     * @param capturedMetadataResult The metadata of a table that was just introspected.
     */
    public GroupedSensorExecutionResult(PreparedSensorsGroup sensorGroup, Instant executionStartedAt, TableSpec capturedMetadataResult) {
        this.sensorGroup = sensorGroup;
        this.capturedMetadataResult = capturedMetadataResult;
        this.finishedAt = Instant.now();
        this.sensorDurationMs = (int)ChronoUnit.MILLIS.between(executionStartedAt, this.finishedAt);
        this.success = true;
    }

    /**
     * Creates a sensor execution result object with the sensor parameters and an unspecified result object of any time (the sensor runner will know how to parse it).
     * @param sensorGroup  Sensor group.
     * @param executionStartedAt Timestamp when the sensor was executed on the data source.
     * @param otherResult The sensor execution result of any type, let the sensor runner parse it further.
     */
    public GroupedSensorExecutionResult(PreparedSensorsGroup sensorGroup, Instant executionStartedAt, Object otherResult) {
        this.sensorGroup = sensorGroup;
        this.otherResult = otherResult;
        this.finishedAt = Instant.now();
        this.sensorDurationMs = (int)ChronoUnit.MILLIS.between(executionStartedAt, this.finishedAt);
        this.success = true;
    }

    /**
     * Creates a sensor execution result object with the sensor parameters when the sensor failed. The isSensorFailed result will be false
     * and the exception will be preserved.
     * @param sensorGroup Sensor group.
     * @param executionStartedAt Timestamp when the sensor was executed on the data source.
     * @param exception The exception throw by the sensor.
     */
    public GroupedSensorExecutionResult(PreparedSensorsGroup sensorGroup, Instant executionStartedAt, Throwable exception) {
        this.sensorGroup = sensorGroup;
        this.exception = exception;
        this.finishedAt = Instant.now();
        this.sensorDurationMs = (int)ChronoUnit.MILLIS.between(executionStartedAt, this.finishedAt);
        this.success = false;
        this.tableResult = Table.create("error");
        this.tableResult.addColumns(DoubleColumn.create(ErrorsColumnNames.ACTUAL_VALUE_COLUMN_NAME));
        this.tableResult.appendRow(); // one placeholder row added, so we can use error result in the sensor readout normalization service to make the error row
    }

    /**
     * Sensor group that was executed.
     * @return Sensor group.
     */
    public PreparedSensorsGroup getSensorGroup() {
        return sensorGroup;
    }

    /**
     * Stores the sensor group that was executed.
     * @param sensorGroup Sensor group.
     */
    public void setSensorGroup(PreparedSensorsGroup sensorGroup) {
        this.sensorGroup = sensorGroup;
    }

    /**
     * Returns a tabular result that is the output of a SELECT statement executed by the sensor's query.
     * @return Result table.
     */
    public Table getTableResult() {
        return tableResult;
    }

    /**
     * Stores the tabular result with the sensor query.
     * @param tableResult Result table with the output of the sensor's query.
     */
    public void setTableResult(Table tableResult) {
        this.tableResult = tableResult;
    }

    /**
     * Returns the captured metadata of the table for schema sensors.
     * @return The just introspected table schema.
     */
    public TableSpec getCapturedMetadataResult() {
        return capturedMetadataResult;
    }

    /**
     * Sets the metadata of the table that was just introspected for schema sensors.
     * @param capturedMetadataResult The just introspected table schema.
     */
    public void setCapturedMetadataResult(TableSpec capturedMetadataResult) {
        this.capturedMetadataResult = capturedMetadataResult;
    }

    /**
     * Returns any other type of the result (not tabular and not metadata) that was returned by the sensor executor. The sensor runner will have to parse it.
     * @return The sensor result of any type.
     */
    public Object getOtherResult() {
        return otherResult;
    }

    /**
     * Sets a sensor result of any other type.
     * @param otherResult Other sensor result.
     */
    public void setOtherResult(Object otherResult) {
        if (otherResult instanceof Table || otherResult instanceof TableSpec) {
            throw new IllegalArgumentException("Set the tablesaw's table or table spec using different methods");
        }
        this.otherResult = otherResult;
    }

    /**
     * Returns the timestamp when the sensor execution finished. The time includes also the time required to receive the
     * sensor results from a JDBC query.
     * @return Sensor finished timestamp.
     */
    public Instant getFinishedAt() {
        return finishedAt;
    }

    /**
     * Sets the timestamp when the sensor has finished. There is no need to call this method because the execution time is calculated automatically.
     * @param finishedAt Sensor finished at timestamp.
     */
    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    /**
     * Sensor execution duration in milliseconds.
     * @return Sensor execution duration - the time between the sensor execution started at and finished at timestamps.
     */
    public int getSensorDurationMs() {
        return sensorDurationMs;
    }

    /**
     * Sets the sensor execution duration (if a different value should be used).
     * @param sensorDurationMs Sensor execution duration in milliseconds.
     */
    public void setSensorDurationMs(int sensorDurationMs) {
        this.sensorDurationMs = sensorDurationMs;
    }

    /**
     * Returns true if the sensor has finished successfully. False means that the sensor has failed.
     * @return True - sensor executed successfully and the resultTable contains the sensor output, false - the sensor failed, the exception should be provided to find more information about the failure.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the execution status. True - success, false - error.
     * @param success Sensor status.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the exception thrown by the sensor if the execution failed.
     * @return Exception.
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * Sets the exception that was thrown by the sensor.
     * @param exception Exception.
     */
    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
