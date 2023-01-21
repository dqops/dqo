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
package ai.dqo.snowflake.sensors.column.validity;

import ai.dqo.checks.column.checkspecs.validity.ColumnValidityValuesInSetPercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.DataQualitySensorRunnerObjectMother;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.IntegrationTestSampleDataObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.validity.BuiltInListFormats;
import ai.dqo.sensors.column.validity.ColumnValidityValuesInSetPercentSensorParametersSpec;
import ai.dqo.snowflake.BaseSnowflakeIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.util.ArrayList;


@SpringBootTest
public class ColumnValidityValuesInSetPercentSensorParametersSpecIntegrationTest extends BaseSnowflakeIntegrationTest {
    private ColumnValidityValuesInSetPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnValidityValuesInSetPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.snowflake);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnValidityValuesInSetPercentSensorParametersSpec();
        this.checkSpec = new ColumnValidityValuesInSetPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithEmptyList_thenReturnsValues() {

        ArrayList<String> valuesList = new ArrayList<>();
        this.sut.setValuesType(BuiltInListFormats.STRING);
        this.sut.setValuesList(valuesList);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "length_string", this.checkSpec);
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        System.out.println(runParameters);
        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0F, resultTable.column(0).get(0));
    }

    // TODO: move the test to another sensor that works on numeric values
//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithNumericalValues_thenReturnsValues() {
//
//        ArrayList<Integer> valuesList = new ArrayList<>();
//        valuesList.add(123);
//        this.sut.setValuesType(BuiltInListFormats.NUMERIC);
//        this.sut.setValuesList(valuesList);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "length_int", this.checkSpec);
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(20.0F, resultTable.column(0).get(0));
//    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithStringValues_thenReturnsValues() {

        ArrayList<String> valuesList = new ArrayList<>();
        valuesList.add("e55e");
        valuesList.add("a111a");
        valuesList.add("d44d");
        valuesList.add("c33c");
        valuesList.add("b22b");
        this.sut.setValuesType(BuiltInListFormats.STRING);
        this.sut.setValuesList(valuesList);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "strings_with_numbers", this.checkSpec);
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0F, resultTable.column(0).get(0));
    }

    // TODO: move the test to another sensor that works on date values

//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithDateValues_thenReturnsValues() {
//
//        ArrayList<LocalDate> valuesList = new ArrayList<>();
//        valuesList.add(LocalDate.of(2022,2,1));
//        this.sut.setValuesType(BuiltInListFormats.DATE);
//        this.sut.setValuesList(valuesList);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "date", this.checkSpec);
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(20.0F, resultTable.column(0).get(0));
//    }
}
