package ai.dqo.data.errors.normalization;

import ai.dqo.data.errors.factory.ErrorSource;
import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.data.normalization.CommonTableNormalizationService;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizationService;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
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

    /**
     * Dependency injection constructor for the error normalization service.
     * @param sensorReadoutsNormalizationService Sensor readout normalization service that is used to fill all fields from the table/check/etc. specifications.
     * @param commonNormalizationService         Common normalization service with reusable methods for normalizing some column values.
     */
    @Autowired
    public ErrorsNormalizationServiceImpl(SensorReadoutsNormalizationService sensorReadoutsNormalizationService,
                                          CommonTableNormalizationService commonNormalizationService) {
        this.sensorReadoutsNormalizationService = sensorReadoutsNormalizationService;
        this.commonNormalizationService = commonNormalizationService;
    }

    /**
     * Creates an error result with one row for a sensor execution error.
     * @param sensorExecutionResult Sensor execution result with the table that contains returned data. Must be an error result with an exception.
     * @param timeSeriesGradient Time series gradient.
     * @param sensorRunParameters Sensor run parameters.
     * @return Metadata object that describes the sensor result table. Contains also a normalized results table.
     */
    @Override
    public ErrorsNormalizedResult createNormalizedSensorErrorResults(SensorExecutionResult sensorExecutionResult,
                                                                     TimeSeriesGradient timeSeriesGradient,
                                                                     SensorExecutionRunParameters sensorRunParameters) {
        assert sensorExecutionResult.getException() != null && sensorExecutionResult.getResultTable() != null &&
                sensorExecutionResult.getResultTable().rowCount() == 1 &&
                sensorExecutionResult.getResultTable().columnCount() == 0;  // we can call this method with an error result which also has one fake row, so the normalization service can fill all the remaining values

        SensorReadoutsNormalizedResult normalizedSensorReadout = this.sensorReadoutsNormalizationService.normalizeResults(
                sensorExecutionResult, timeSeriesGradient, sensorRunParameters);
        normalizedSensorReadout.getIdColumn().setName(ErrorsColumnNames.READOUT_ID_COLUMN_NAME); // renaming the ID column
        Table table = normalizedSensorReadout.getTable();

        // adding remaining columns
        StringColumn errorMessageColumn = StringColumn.create(ErrorsColumnNames.ERROR_MESSAGE_COLUMN_NAME, table.rowCount());
        errorMessageColumn.setMissingTo(makeErrorMessage(sensorExecutionResult.getException()));
        table.addColumns(errorMessageColumn);

        StringColumn errorSourceColumn = StringColumn.create(ErrorsColumnNames.ERROR_SOURCE_COLUMN_NAME, table.rowCount());
        errorSourceColumn.setMissingTo(ErrorSource.sensor.name());
        table.addColumns(errorSourceColumn);

        DateTimeColumn errorTimestampColumn = DateTimeColumn.create(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME, table.rowCount());
        LocalDateTime executedAtLocalTimestamp = LocalDateTime.from(sensorExecutionResult.getFinishedAt());
        errorTimestampColumn.setMissingTo(executedAtLocalTimestamp);
        table.addColumns(errorTimestampColumn);

        // make the real ID (error unique ID, using the executed_at timestamp, not a truncated time period)
        long checkHash = sensorRunParameters.getCheck().getHierarchyId().hashCode64();
        long tableHash = sensorRunParameters.getTable().getHierarchyId().hashCode64();
        long columnHash = sensorRunParameters.getColumn() != null ? sensorRunParameters.getColumn().getHierarchyId().hashCode64() : 0L;

        LongColumn dataStreamHashColumn = normalizedSensorReadout.getDataStreamHashColumn();
        StringColumn rowIdColumn = this.commonNormalizationService.createRowIdColumn(dataStreamHashColumn, errorTimestampColumn,
                checkHash, tableHash, columnHash, table.rowCount());
        table.addColumns(rowIdColumn);

        return new ErrorsNormalizedResult(table);
    }

    /**
     * Extracts the error message from the exception that will be saved to the error table.
     * @param exception Exception.
     * @return Error message.
     */
    public String makeErrorMessage(Exception exception) {
        String errorMessage = exception.getMessage();
        return errorMessage;
    }
}
