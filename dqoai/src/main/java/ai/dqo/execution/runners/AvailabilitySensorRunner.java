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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

import java.time.LocalDateTime;

/**
 * Sensor runner that transforms an SQL template and executes a generated SQL on a connection.
 */
@Component
@Slf4j
public class AvailabilitySensorRunner extends AbstractSensorRunner {
    /**
     * Sensor runner class name.
     */
    public static final String CLASS_NAME = AvailabilitySensorRunner.class.getName();
    private JinjaSqlTemplateSensorRunner jinjaSqlTemplateSensorRunner;

    @Autowired
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
            try {
                if(sensorExecutionResult.getResultTable().column(0).get(0) == null){
                    Table dummyResultTable = createDummyResultTable(1L);
                    return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
                }
            }catch (Exception exception) {
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
        Table dummyResultTable = Table.create("dummy_results", LongColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME), DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setLong(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, actualValue);
        row.setDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, LocalDateTime.now());

        return dummyResultTable;
    }
}
