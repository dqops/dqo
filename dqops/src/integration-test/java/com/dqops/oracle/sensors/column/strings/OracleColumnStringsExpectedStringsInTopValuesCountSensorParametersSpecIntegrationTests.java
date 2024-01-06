/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.oracle.sensors.column.strings;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.acceptedvalues.ColumnExpectedStringsInTopValuesCountCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.groupings.DataGroupingDimensionSource;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.oracle.BaseOracleIntegrationTest;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.acceptedvalues.ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OracleColumnStringsExpectedStringsInTopValuesCountSensorParametersSpecIntegrationTests extends BaseOracleIntegrationTest {
    private ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnExpectedStringsInTopValuesCountCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.oracle);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec();
        this.checkSpec = new ColumnExpectedStringsInTopValuesCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("\"id\" < 5");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2, (double) resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingAndExpectedValueNotFound_thenReturnsCorrectCount() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("not_found");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(1, (double) resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingOneDataStream_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("b22b");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);

        DataGroupingConfigurationSpec dataGroupingConfiguration = this.sampleTableMetadata.getTableSpec().getDefaultDataGroupingConfiguration();
        dataGroupingConfiguration.setLevel1(new DataGroupingDimensionSpec() {{
            setSource(DataGroupingDimensionSource.column_value);
            setColumn("dim1");
        }});

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(3, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2, (double) resultTable.column(0).get(0));
        Assertions.assertEquals(1, (double) resultTable.column(0).get(1));
        Assertions.assertEquals(1, (double) resultTable.column(0).get(2));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("\"id\"< 5");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2, (double) resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(5L);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2, (double) resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(5L);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.daily, "date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(25, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2, (double) resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(5L);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.monthly, "date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2, (double) resultTable.column(0).get(0));
    }
}