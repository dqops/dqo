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
package ai.dqo.postgresql.sensors.column.datetime;

//    @SpringBootTest
//    public class ColumnDatetimeValueInRangeDatePercentSensorParametersSpecIntegrationTest extends BaseBigQueryIntegrationTest {
//        private ColumnDatetimeValueInRangeDatePercentSensorParametersSpec sut;
//        private UserHomeContext userHomeContext;
//        private ColumnStringValidDatesPercentCheckSpec checkSpec;
//        private SampleTableMetadata sampleTableMetadata;
//
//        /**
//     * Called before each test.
//     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
//     *
//     * @throws Throwable
//     */
//    @Override
//    @BeforeEach
//    protected void setUp() throws Throwable {
//        super.setUp();
//		this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_different_time_data_types, ProviderType.postgresql);
//        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
//		this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
//		this.sut = new ColumnDatetimeValueInRangeDatePercentSensorParametersSpec();
//		this.checkSpec = new ColumnStringValidDatesPercentCheckSpec();
//		this.checkSpec.setParameters(this.sut);
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithDatesAndAllValuesInRangeAndDefaultBounds_thenReturnsValues() {
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "date_type", this.checkSpec);
//        this.sut = (ColumnDatetimeValueInRangeDatePercentSensorParametersSpec) runParameters.getSensorParameters();
//
//        LocalDate lower = LocalDate.of(2022,1,1);
//        LocalDate upper = LocalDate.of(2022,1,10);
//
//        this.sut.setMinValue(lower);
//        this.sut.setMaxValue(upper);
//        runParameters.setTimeSeries(null);
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithDatesAnd4ValuesInRangeAndIncludeMaxValueFalse_thenReturnsValues() {
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "date_type", this.checkSpec);
//        this.sut = (ColumnDatetimeValueInRangeDatePercentSensorParametersSpec) runParameters.getSensorParameters();
//
//        LocalDate lower = LocalDate.of(2022,1,1);
//        LocalDate upper = LocalDate.of(2022,1,5);
//
//        this.sut.setMinValue(lower);
//        this.sut.setMaxValue(upper);
//        this.sut.setIncludeMaxValue(false);
//        runParameters.setTimeSeries(null);
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(40.0, resultTable.column(0).get(0));
//    }
//}
