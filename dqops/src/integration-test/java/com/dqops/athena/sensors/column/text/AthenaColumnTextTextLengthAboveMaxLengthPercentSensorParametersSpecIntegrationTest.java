/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.athena.sensors.column.text;

import com.dqops.athena.BaseAthenaIntegrationTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.text.ColumnTextLengthAboveMaxLengthPercentCheckSpec;
import com.dqops.connectors.trino.AthenaConnectionSpecObjectMother;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingDimensionSource;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.text.ColumnTextTextLengthAboveMaxLengthPercentSensorParametersSpec;
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
public class AthenaColumnTextTextLengthAboveMaxLengthPercentSensorParametersSpecIntegrationTest extends BaseAthenaIntegrationTest {
    private ColumnTextTextLengthAboveMaxLengthPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private ConnectionSpec connectionSpec;

    @BeforeEach
    void setUp() {
        this.connectionSpec = AthenaConnectionSpecObjectMother.create();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnTextTextLengthAboveMaxLengthPercentSensorParametersSpec();
        this.checkSpec = new ColumnTextLengthAboveMaxLengthPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullDataAndNoParams_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, connectionSpec);
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
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "length_string", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(43.333, ValueConverter.toDouble(resultTable.column(0).get(0)), 0.001);
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "length_string", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(43.333, ValueConverter.toDouble(resultTable.column(0).get(0)), 0.001);
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "length_string", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(43.333, ValueConverter.toDouble(resultTable.column(0).get(0)), 0.001);
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "length_string", this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(25, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, ValueConverter.toDouble(resultTable.column(0).get(0)), 0.001);
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "length_string", this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(43.333, ValueConverter.toDouble(resultTable.column(0).get(0)), 0.001);
    }

    @Test
    void runSensor_whenRunOnIntegerColumn_thenReturnsValues() {
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "length_int", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(70.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingAndNoIdColumns_thenReturnsErrorSamples() {
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "length_string", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(13, resultTable.rowCount());
        Assertions.assertEquals(1, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());

        Assertions.assertTrue(sampleValues.contains("abcde"));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingButWithIdColumns_thenReturnsErrorSamples() {
        this.sut.setMaxLength(4);
        sampleTableMetadata.getTableSpec().getColumns().getAt(0).setId(true);
        sampleTableMetadata.getTableSpec().getColumns().getAt(1).setId(true);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "length_string", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(13, resultTable.rowCount());
        Assertions.assertEquals(3, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("row_id_1", resultTable.column(1).name());
        Assertions.assertEquals("row_id_2", resultTable.column(2).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains("abcde"));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(2));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithDataGroupingAndWithIdColumns_thenReturnsErrorSamples() {
        this.sut.setMaxLength(4);
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
                sampleTableMetadata, "length_string", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(13, resultTable.rowCount());
        Assertions.assertEquals(5, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("sample_index", resultTable.column(1).name());
        Assertions.assertEquals("grouping_level_1", resultTable.column(2).name());
        Assertions.assertEquals("row_id_1", resultTable.column(3).name());
        Assertions.assertEquals("row_id_2", resultTable.column(4).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains("abcde"));

        List<Integer> groupingLevel1Values = new ArrayList<>(
                Stream.of(resultTable.column("grouping_level_1").asObjectArray())
                        .map(val -> ValueConverter.toInteger(val))
                        .collect(Collectors.toSet()));
        Assertions.assertEquals(2, groupingLevel1Values.size());
        Assertions.assertTrue(groupingLevel1Values.contains(1));
        Assertions.assertTrue(groupingLevel1Values.contains(0));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(2));
    }

}