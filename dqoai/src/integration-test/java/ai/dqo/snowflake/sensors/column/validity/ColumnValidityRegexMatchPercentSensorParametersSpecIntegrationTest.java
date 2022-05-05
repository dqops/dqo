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

import ai.dqo.sensors.column.validity.ColumnValidityRegexMatchPercentSensorParametersSpec;
import ai.dqo.checks.column.validity.ColumnValidityRegexMatchPercentCheckSpec;
import ai.dqo.snowflake.BaseSnowflakeIntegrationTest;
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
import ai.dqo.sensors.column.validity.BuiltInRegex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class ColumnValidityRegexMatchPercentSensorParametersSpecIntegrationTest extends BaseSnowflakeIntegrationTest {
    private ColumnValidityRegexMatchPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnValidityRegexMatchPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_regex_sensor, ProviderType.snowflake);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnValidityRegexMatchPercentSensorParametersSpec();
        this.checkSpec = new ColumnValidityRegexMatchPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithMixedValuesAndSetEmailRegex_thenReturnsValues() {
        this.sut.setNamedRegex(BuiltInRegex.email);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "email_mixed", this.checkSpec);
        this.sut = (ColumnValidityRegexMatchPercentSensorParametersSpec) runParameters.getSensorParameters();
        runParameters.getTable().setTimeSeries(null);
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(40.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithMixedValuesAndSetCustomRegex_thenReturnsValues() {
        this.sut.setCustomRegex("abcd");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "strings_with_numbers", this.checkSpec);
        this.sut = (ColumnValidityRegexMatchPercentSensorParametersSpec) runParameters.getSensorParameters();
        runParameters.getTable().setTimeSeries(null);
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(40.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithMixedValuesAndSetPhoneNumberRegex_Where_Correct1() {
        this.sut.setNamedRegex(BuiltInRegex.phoneNumber);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "phone_numbers_mixed", this.checkSpec);
        this.sut = (ColumnValidityRegexMatchPercentSensorParametersSpec) runParameters.getSensorParameters();
        runParameters.getTable().setTimeSeries(null);
        runParameters.getTable().setFilter("\"correct\"=1");
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithMixedValuesAndSetPhoneNumberRegex_Where_Correct0() {
        this.sut.setNamedRegex(BuiltInRegex.phoneNumber);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "phone_numbers_mixed", this.checkSpec);
        this.sut = (ColumnValidityRegexMatchPercentSensorParametersSpec) runParameters.getSensorParameters();
        runParameters.getTable().setTimeSeries(null);
        runParameters.getTable().setFilter("\"correct\"=0");
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithMixedValuesAndSetPhoneNumberRegex_WholeColumn() {
        this.sut.setNamedRegex(BuiltInRegex.phoneNumber);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "phone_numbers_mixed", this.checkSpec);
        this.sut = (ColumnValidityRegexMatchPercentSensorParametersSpec) runParameters.getSensorParameters();
        runParameters.getTable().setTimeSeries(null);
        //runParameters.getTable().setFilter("correct=0");
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(60.0F, resultTable.column(0).get(0));
    }

}