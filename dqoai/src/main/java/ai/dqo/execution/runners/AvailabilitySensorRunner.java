package ai.dqo.execution.runners;

import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;
import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import ai.dqo.execution.sqltemplates.JinjaSqlTemplateSensorRunner;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

import java.time.LocalDateTime;

@Component
@Slf4j
public class AvailabilitySensorRunner extends AbstractSensorRunner {
    /**
     * Sensor runner class name.
     */
    public static final String CLASS_NAME = AvailabilitySensorRunner.class.getName();
    private JinjaSqlTemplateSensorRunner jinjaSqlTemplateSensorRunner;


    public AvailabilitySensorRunner(JinjaSqlTemplateSensorRunner jinjaSqlTemplateSensorRunner){
        this.jinjaSqlTemplateSensorRunner = jinjaSqlTemplateSensorRunner;
    }

    @Override
    public SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                               SensorExecutionRunParameters sensorRunParameters,
                                               SensorDefinitionFindResult sensorDefinitions,
                                               SensorExecutionProgressListener progressListener,
                                               boolean dummySensorExecution) {

        SensorExecutionResult sensorExecutionResult = jinjaSqlTemplateSensorRunner.executeSensor(executionContext,
                sensorRunParameters, sensorDefinitions, progressListener, dummySensorExecution);

        if(sensorExecutionResult.isSuccess()){
            return sensorExecutionResult;
        }

        Table dummyResultTable = createDummyResultTable(sensorRunParameters);
        return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
    }

    /**
     * Creates a one row dummy result table.
     * @param sensorRunParameters Sensor execution run parameters.
     * @return Dummy result table.
     */
    public Table createDummyResultTable(SensorExecutionRunParameters sensorRunParameters) {
        Table dummyResultTable = Table.create("dummy_results", LongColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME), DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setLong(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, 0L);

        TimeSeriesConfigurationSpec effectiveTimeSeries = sensorRunParameters.getTimeSeries();
        if (effectiveTimeSeries != null && effectiveTimeSeries.getMode() != null) {
            row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, LocalDateTime.now());
        }

        return dummyResultTable;
    }
}
