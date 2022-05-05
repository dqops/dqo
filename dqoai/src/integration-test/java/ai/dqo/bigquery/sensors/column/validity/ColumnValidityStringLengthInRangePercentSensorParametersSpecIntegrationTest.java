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
package ai.dqo.bigquery.sensors.column.validity;

import ai.dqo.bigquery.BaseBigQueryIntegrationTest;
import ai.dqo.checks.column.validity.ColumnValidityStringLengthInRangePercentCheckSpec;
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
import ai.dqo.sensors.column.validity.ColumnValidityStringLengthInRangePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class ColumnValidityStringLengthInRangePercentSensorParametersSpecIntegrationTest extends BaseBigQueryIntegrationTest {
    private ColumnValidityStringLengthInRangePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnValidityStringLengthInRangePercentCheckSpec checkSpec;
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
		this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_regex_sensor, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
		this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
		this.sut = new ColumnValidityStringLengthInRangePercentSensorParametersSpec();
		this.checkSpec = new ColumnValidityStringLengthInRangePercentCheckSpec();
		this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithStringsAndLowerLengthBoundIs3AndUpperLengthBoundIs5_thenReturnsPercentLengthOfStringInSpecifiedRange() {

        this.sut.setMinLength(3);
        this.sut.setMaxLength(5);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "length_string", this.checkSpec);
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(60.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithStringsAndLowerLengthBoundIs6AndUpperLengthBoundIs10_thenReturnsPercentLengthOfStringInSpecifiedRange() {

        this.sut.setMinLength(6);
        this.sut.setMaxLength(10);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "length_string", this.checkSpec);
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(40.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedOnColumnWithStringsAndLowerLengthBoundIs2AndUpperLengthBoundIs5_thenReturnsPercentLengthOfStringInSpecifiedRange() {

        this.sut.setMinLength(2);
        this.sut.setMaxLength(5);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "length_string_int", this.checkSpec);
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(60.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedOnEmptyColumnLowerLengthBoundIs2AndUpperLengthBoundIs5_thenReturnsNull() {

        this.sut.setMinLength(2);
        this.sut.setMaxLength(5);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "count_0", this.checkSpec);
        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);


        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(null, resultTable.column(0).get(0));
    }



}
