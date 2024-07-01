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
package com.dqops.postgresql.sensors.column.uniqueness;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
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
import com.dqops.postgresql.BasePostgresqlIntegrationTest;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.uniqueness.ColumnUniquenessDuplicatePercentSensorParametersSpec;
import com.dqops.testutils.ValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class PostgresqlColumnUniquenessDuplicatePercentSensorParametersSpecIntegrationTest extends BasePostgresqlIntegrationTest {
    private ColumnUniquenessDuplicatePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnDuplicatePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.postgresql);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
		this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
		this.sut = new ColumnUniquenessDuplicatePercentSensorParametersSpec();
		this.checkSpec = new ColumnDuplicatePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullData_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, ProviderType.postgresql);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "string_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedAndAllValuesAreUniqueProfiling_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "id", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "length_string", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(80.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "id", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "id", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "id", this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(25, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "id", this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingAndNoIdColumns_thenReturnsErrorSamples() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "date_iso", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());

        Assertions.assertTrue(sampleValues.contains("2022-02-01"));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingButWithIdColumns_thenReturnsErrorSamples() {
        sampleTableMetadata.getTableSpec().getColumns().getAt(0).setId(true);
        sampleTableMetadata.getTableSpec().getColumns().getAt(1).setId(true);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "date_iso", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("row_id_1", resultTable.column(1).name());
        Assertions.assertEquals("row_id_2", resultTable.column(2).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains("2022-02-01"));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(26));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithDataGroupingAndWithIdColumns_thenReturnsErrorSamples() {
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
                sampleTableMetadata, "date_iso", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(2, resultTable.rowCount());
        Assertions.assertEquals(4, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("grouping_level_1", resultTable.column(1).name());
        Assertions.assertEquals("row_id_1", resultTable.column(2).name());
        Assertions.assertEquals("row_id_2", resultTable.column(3).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains("2022-02-01"));

        List<Integer> groupingLevel1Values = new ArrayList<>(
                List.of(resultTable.column("grouping_level_1").asObjectArray())
                        .stream().map(val -> ValueConverter.toInteger(val))
                        .collect(Collectors.toSet()));
        Assertions.assertEquals(2, groupingLevel1Values.size());
        Assertions.assertTrue(groupingLevel1Values.contains(0));
        Assertions.assertTrue(groupingLevel1Values.contains(1));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(27));
    }
}
