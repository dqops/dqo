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
package ai.dqo.data.errors.normalization;

import ai.dqo.data.errors.factory.ErrorSource;
import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.data.normalization.CommonTableNormalizationService;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizationService;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

import java.time.LocalDateTime;

/**
 * Error normalization service that creates normalized error tables for errors, filling all possible fields.
 */
@Component
public class ErrorsNormalizationServiceImpl implements ErrorsNormalizationService {
    private SensorReadoutsNormalizationService sensorReadoutsNormalizationService;
    private CommonTableNormalizationService commonNormalizationService;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Dependency injection constructor for the error normalization service.
     * @param sensorReadoutsNormalizationService Sensor readout normalization service that is used to fill all fields from the table/check/etc. specifications.
     * @param commonNormalizationService         Common normalization service with reusable methods for normalizing some column values.
     */
    @Autowired
    public ErrorsNormalizationServiceImpl(SensorReadoutsNormalizationService sensorReadoutsNormalizationService,
                                          CommonTableNormalizationService commonNormalizationService,
                                          DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.sensorReadoutsNormalizationService = sensorReadoutsNormalizationService;
        this.commonNormalizationService = commonNormalizationService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Creates an error result with one row for a sensor execution error.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data. Must be an error result with an exception.
     * @param sensorRunParameters Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    @Override
    public ErrorsNormalizedResult createNormalizedSensorErrorResults(SensorExecutionResult sensorExecutionResult,
                                                                     SensorExecutionRunParameters sensorRunParameters) {
        assert sensorExecutionResult.getException() != null && sensorExecutionResult.getResultTable() != null &&
                sensorExecutionResult.getResultTable().rowCount() == 1 &&
                sensorExecutionResult.getResultTable().columnCount() == 0;  // we can call this method with an error result which also has one fake row, so the normalization service can fill all the remaining values

        ErrorsNormalizedResult normalizedErrorResults = createNormalizedErrorResults(sensorExecutionResult,
                sensorRunParameters, ErrorSource.sensor, sensorExecutionResult.getException());
        return normalizedErrorResults;
    }

    /**
     * Creates an error result with one row for a rule execution error.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data.
     * @param sensorRunParameters Sensor run parameters.
     * @param ruleEvaluationException Exception thrown at the rule evaluation.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    @Override
    public ErrorsNormalizedResult createNormalizedRuleErrorResults(SensorExecutionResult sensorExecutionResult,
                                                                   SensorExecutionRunParameters sensorRunParameters,
                                                                   Throwable ruleEvaluationException) {
        assert sensorExecutionResult.getException() == null && sensorExecutionResult.getResultTable() != null;

        ErrorsNormalizedResult normalizedErrorResults = createNormalizedErrorResults(sensorExecutionResult,
                sensorRunParameters, ErrorSource.rule, ruleEvaluationException);
        return normalizedErrorResults;
    }

    /**
     * Common method that creates an error evaluation normalized table given the exception and the source (rule or sensor).
     * @param sensorExecutionResult Sensor execution result.
     * @param sensorRunParameters Sensor run parameters.
     * @param errorSource Error source (sensor or rule).
     * @param exception Exception that was thrown at the sensor or rule evaluation.
     * @return Normalized error result.
     */
    public ErrorsNormalizedResult createNormalizedErrorResults(SensorExecutionResult sensorExecutionResult,
                                                               SensorExecutionRunParameters sensorRunParameters,
                                                               ErrorSource errorSource,
                                                               Throwable exception) {
        SensorReadoutsNormalizedResult normalizedSensorReadout = this.sensorReadoutsNormalizationService.normalizeResults(
                sensorExecutionResult, sensorRunParameters);
        normalizedSensorReadout.getIdColumn().setName(ErrorsColumnNames.READOUT_ID_COLUMN_NAME); // renaming the ID column
        Table table = normalizedSensorReadout.getTable();

        // adding remaining columns
        TextColumn errorMessageColumn = TextColumn.create(ErrorsColumnNames.ERROR_MESSAGE_COLUMN_NAME, table.rowCount());
        errorMessageColumn.setMissingTo(makeErrorMessage(exception));
        table.addColumns(errorMessageColumn);

        TextColumn errorSourceColumn = TextColumn.create(ErrorsColumnNames.ERROR_SOURCE_COLUMN_NAME, table.rowCount());
        errorSourceColumn.setMissingTo(errorSource.name());
        table.addColumns(errorSourceColumn);

        DateTimeColumn errorTimestampColumn = DateTimeColumn.create(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME, table.rowCount());
        LocalDateTime executedAtLocalTimestamp = LocalDateTime.ofInstant(sensorExecutionResult.getFinishedAt(), this.defaultTimeZoneProvider.getDefaultTimeZoneId());
        errorTimestampColumn.setMissingTo(executedAtLocalTimestamp);
        table.addColumns(errorTimestampColumn);

        // make the real ID (error unique ID, using the executed_at timestamp, not a truncated time period)
        long checkHash = sensorRunParameters.getCheck().getHierarchyId().hashCode64();
        long tableHash = sensorRunParameters.getTable().getHierarchyId().hashCode64();
        long columnHash = sensorRunParameters.getColumn() != null ? sensorRunParameters.getColumn().getHierarchyId().hashCode64() : 0L;

        LongColumn dataStreamHashColumn = normalizedSensorReadout.getDataGroupHashColumn();
        TextColumn rowIdColumn = this.commonNormalizationService.createRowIdColumnAndUpdateIndexes(dataStreamHashColumn, errorTimestampColumn,
                checkHash, tableHash, columnHash, table.rowCount());
        table.addColumns(rowIdColumn);

        return new ErrorsNormalizedResult(table);
    }

    /**
     * Extracts the error message from the exception that will be saved to the error table.
     * @param exception Exception.
     * @return Error message.
     */
    public String makeErrorMessage(Throwable exception) {
        String errorMessage = exception.getMessage();
        return errorMessage;
    }
}
