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
package com.dqops.sqlserver.sensors.column.acceptedvalues;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.acceptedvalues.ColumnExpectedTextsInTopValuesCountCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingDimensionSource;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.acceptedvalues.ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec;
import com.dqops.sqlserver.BaseSqlServerIntegrationTest;
import com.dqops.testutils.ValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class SqlServerColumnStringsExpectedTextsInTopValuesCountSensorParametersSpecIntegrationTest extends BaseSqlServerIntegrationTest {
    private ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnExpectedTextsInTopValuesCountCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.sqlserver);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec();
        this.checkSpec = new ColumnExpectedTextsInTopValuesCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullDataWithInvalidParameters_thenReturnsNull() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, ProviderType.sqlserver);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "string_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(null, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_onNullData_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, ProviderType.sqlserver);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "string_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0, ValueConverter.toLong(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingAndDataStreamIsTag_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        DataGroupingConfigurationSpec dataGroupingConfiguration = this.sampleTableMetadata.getTableSpec().getDefaultDataGroupingConfiguration();
        dataGroupingConfiguration.setLevel1(new DataGroupingDimensionSpec() {{
            setSource(DataGroupingDimensionSource.tag);
            setTag("mytag");
        }});
        this.sampleTableMetadata.getTableSpec().setDefaultDataGroupingConfiguration(dataGroupingConfiguration);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingOneDataStream_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        DataGroupingConfigurationSpec dataGroupingConfiguration = this.sampleTableMetadata.getTableSpec().getDefaultDataGroupingConfiguration();
        dataGroupingConfiguration.setLevel1(new DataGroupingDimensionSpec() {{
            setSource(DataGroupingDimensionSource.column_value);
            setColumn("mix_string_int");
        }});
        this.sampleTableMetadata.getTableSpec().setDefaultDataGroupingConfiguration(dataGroupingConfiguration);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(4, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0L, resultTable.column("actual_value").get(0));
        Assertions.assertEquals(1L, resultTable.column("actual_value").get(1));
        Assertions.assertEquals(1L, resultTable.column("actual_value").get(2));
        Assertions.assertEquals(0L, resultTable.column("actual_value").get(3));

        Assertions.assertEquals("11", resultTable.column("grouping_level_1").get(0).toString());
        Assertions.assertEquals("22", resultTable.column("grouping_level_1").get(1).toString());
        Assertions.assertEquals("aa", resultTable.column("grouping_level_1").get(2).toString());
        Assertions.assertEquals("bb", resultTable.column("grouping_level_1").get(3).toString());

    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(2L, resultTable.column(0).get(0));
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
        Assertions.assertEquals(2L, resultTable.column(0).get(0));
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
        Assertions.assertEquals(2L, resultTable.column(0).get(0));
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
        Assertions.assertEquals(2L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingAndNoIdColumns_thenReturnsErrorSamples() {
        List<String> values = new ArrayList<>();
        values.add("e55e");
        values.add("d44d");
        values.add("c33c");
        values.add("b22b");
        this.sut.setExpectedValues(values);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals(1, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());

        Assertions.assertTrue(sampleValues.contains("a111a"));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingButWithIdColumns_thenReturnsErrorSamples() {
        List<String> values = new ArrayList<>();
        values.add("e55e");
        values.add("d44d");
        values.add("c33c");
        values.add("b22b");
        this.sut.setExpectedValues(values);

        sampleTableMetadata.getTableSpec().getColumns().getAt(0).setId(true);
        sampleTableMetadata.getTableSpec().getColumns().getAt(1).setId(true);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals(3, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("row_id_1", resultTable.column(1).name());
        Assertions.assertEquals("row_id_2", resultTable.column(2).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains("a111a"));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(1));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithDataGroupingAndWithIdColumns_thenReturnsErrorSamples() {
        List<String> values = new ArrayList<>();
        values.add("e55e");
        values.add("d44d");
        values.add("c33c");
        values.add("b22b");
        this.sut.setExpectedValues(values);

        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec() {{
            setLevel1(new DataGroupingDimensionSpec() {{
                setSource(DataGroupingDimensionSource.column_value);
                setColumn("correct");
            }});
        }};
        sampleTableMetadata.getTableSpec().setDefaultDataGroupingConfiguration(dataGroupingConfigurationSpec);
        sampleTableMetadata.getTableSpec().getColumns().getAt(0).setId(true);
        sampleTableMetadata.getTableSpec().getColumns().getAt(1).setId(true);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "strings_with_numbers", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals(5, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("sample_index", resultTable.column(1).name());
        Assertions.assertEquals("grouping_level_1", resultTable.column(2).name());
        Assertions.assertEquals("row_id_1", resultTable.column(3).name());
        Assertions.assertEquals("row_id_2", resultTable.column(4).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains("a111a"));

        List<Integer> groupingLevel1Values = new ArrayList<>(
                Stream.of(resultTable.column("grouping_level_1").asObjectArray())
                        .map(val -> ValueConverter.toInteger(val))
                        .collect(Collectors.toSet()));
        Assertions.assertEquals(2, groupingLevel1Values.size());
        Assertions.assertTrue(groupingLevel1Values.contains(0));
        Assertions.assertTrue(groupingLevel1Values.contains(1));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(1));
    }
}