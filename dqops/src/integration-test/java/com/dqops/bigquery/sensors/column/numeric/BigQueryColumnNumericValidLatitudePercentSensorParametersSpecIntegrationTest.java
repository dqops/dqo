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
package com.dqops.bigquery.sensors.column.numeric;

import com.dqops.bigquery.BaseBigQueryIntegrationTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.numeric.ColumnValidLatitudePercentCheckSpec;
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
import com.dqops.sensors.column.numeric.ColumnNumericValidLatitudePercentSensorParametersSpec;
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
public class BigQueryColumnNumericValidLatitudePercentSensorParametersSpecIntegrationTest extends BaseBigQueryIntegrationTest {
    private ColumnNumericValidLatitudePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnValidLatitudePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.geographic_coordinate_system_test, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnNumericValidLatitudePercentSensorParametersSpec();
        this.checkSpec = new ColumnValidLatitudePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullData_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "int_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "gcs", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(35.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "gcs", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(35.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "gcs", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(35.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "gcs", this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(4, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "gcs", this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(4, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingAndNoIdColumns_thenReturnsErrorSamples() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "gcs", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(13, resultTable.rowCount());
        Assertions.assertEquals(1, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        List<Double> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> ValueConverter.toDouble(val))
                .collect(Collectors.toList());

        Assertions.assertTrue(sampleValues.contains(91.18464));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingButWithIdColumns_thenReturnsErrorSamples() {
        sampleTableMetadata.getTableSpec().getColumns().getAt(0).setId(true);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "gcs", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(13, resultTable.rowCount());
        Assertions.assertEquals(2, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("row_id_1", resultTable.column(1).name());
        List<Double> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> ValueConverter.toDouble(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains(91.18464));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(20));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithDataGroupingAndWithIdColumns_thenReturnsErrorSamples() {
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec() {{
            setLevel1(new DataGroupingDimensionSpec() {{
                setSource(DataGroupingDimensionSource.column_value);
                setColumn("gcs_ok");
            }});
        }};
        sampleTableMetadata.getTableSpec().setDefaultDataGroupingConfiguration(dataGroupingConfigurationSpec);
        sampleTableMetadata.getTableSpec().getColumns().getAt(0).setId(true);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "gcs", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(13, resultTable.rowCount());
        Assertions.assertEquals(4, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("sample_index", resultTable.column(1).name());
        Assertions.assertEquals("grouping_level_1", resultTable.column(2).name());
        Assertions.assertEquals("row_id_1", resultTable.column(3).name());
        List<Double> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> ValueConverter.toDouble(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains(91.18464));

        List<Integer> groupingLevel1Values = new ArrayList<>(
                List.of(resultTable.column("grouping_level_1").asObjectArray())
                        .stream().map(val -> ValueConverter.toInteger(val))
                        .collect(Collectors.toSet()));
        Assertions.assertEquals(2, groupingLevel1Values.size());
        Assertions.assertTrue(groupingLevel1Values.contains(0));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(20));
    }
}