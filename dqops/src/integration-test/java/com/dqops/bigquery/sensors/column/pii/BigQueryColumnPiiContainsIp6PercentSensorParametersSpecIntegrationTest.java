/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.bigquery.sensors.column.pii;

import com.dqops.bigquery.BaseBigQueryIntegrationTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.pii.ColumnPiiContainsIp6PercentCheckSpec;
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
import com.dqops.sensors.column.pii.ColumnPiiContainsIp6PercentSensorParametersSpec;
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
class BigQueryColumnPiiContainsIp6PercentSensorParametersSpecIntegrationTest extends BaseBigQueryIntegrationTest {
    private ColumnPiiContainsIp6PercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnPiiContainsIp6PercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private final String testedColumnName = "ip6";
    private final String validExamplesFilterText = "result = 1";
    private final String invalidExamplesFilterText = "result = 0";

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother
                .createSampleTableMetadataForCsvFile(SampleCsvFileNames.contains_ip6_test, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother
                .createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnPiiContainsIp6PercentSensorParametersSpec();
        this.checkSpec = new ColumnPiiContainsIp6PercentCheckSpec();
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
                sampleTableMetadata, "string_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingValidRows_thenReturnsOneRowWithTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForProfilingCheck(sampleTableMetadata, testedColumnName, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(88.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingInvalidRows_thenReturnsOneRowWithTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForProfilingCheck(sampleTableMetadata, testedColumnName, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDailyValidRows_thenReturnsOneRowWithTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForMonitoringCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(88.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDailyInvalidRows_thenReturnsOneRowWithTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForMonitoringCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthlyValidRows_thenReturnsOneRowWithTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForMonitoringCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(88.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthlyInvalidRows_thenReturnsOneRowWithTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForMonitoringCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDailyValidRows_thenReturnTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForPartitionedCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(2, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(87.5, resultTable.column(0).get(0));
        Assertions.assertEquals(100.0, resultTable.column(0).get(1));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDailyInvalidRows_thenReturnsTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForPartitionedCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(3, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
        Assertions.assertEquals(0.0, resultTable.column(0).get(1));
        Assertions.assertEquals(0.0, resultTable.column(0).get(2));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthlyValidRows_thenReturnsTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForPartitionedCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(88.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthlyInvalidRows_thenReturnsTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForPartitionedCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(2, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
        Assertions.assertEquals(0.0, resultTable.column(0).get(1));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingAndNoIdColumns_thenReturnsErrorSamples() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "ip6", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(23, resultTable.rowCount());
        Assertions.assertEquals(1, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());

        Assertions.assertTrue(sampleValues.contains("c219:0b3f:96f6:da15:bcac:856a:dd1a:9e71"));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingButWithIdColumns_thenReturnsErrorSamples() {
        sampleTableMetadata.getTableSpec().getColumns().getAt(0).setId(true);
        sampleTableMetadata.getTableSpec().getColumns().getAt(2).setId(true);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "ip6", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(23, resultTable.rowCount());
        Assertions.assertEquals(3, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("row_id_1", resultTable.column(1).name());
        Assertions.assertEquals("row_id_2", resultTable.column(2).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains("c219:0b3f:96f6:da15:bcac:856a:dd1a:9e71"));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(24));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithDataGroupingAndWithIdColumns_thenReturnsErrorSamples() {
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec() {{
            setLevel1(new DataGroupingDimensionSpec() {{
                setSource(DataGroupingDimensionSource.column_value);
                setColumn("result");
            }});
        }};
        sampleTableMetadata.getTableSpec().setDefaultDataGroupingConfiguration(dataGroupingConfigurationSpec);
        sampleTableMetadata.getTableSpec().getColumns().getAt(0).setId(true);
        sampleTableMetadata.getTableSpec().getColumns().getAt(2).setId(true);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "ip6", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(23, resultTable.rowCount());
        Assertions.assertEquals(5, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("sample_index", resultTable.column(1).name());
        Assertions.assertEquals("grouping_level_1", resultTable.column(2).name());
        Assertions.assertEquals("row_id_1", resultTable.column(3).name());
        Assertions.assertEquals("row_id_2", resultTable.column(4).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(sampleValues.contains("c219:0b3f:96f6:da15:bcac:856a:dd1a:9e71"));

        List<Integer> groupingLevel1Values = new ArrayList<>(
                Stream.of(resultTable.column("grouping_level_1").asObjectArray())
                        .map(val -> ValueConverter.toInteger(val))
                        .collect(Collectors.toSet()));
        Assertions.assertEquals(2, groupingLevel1Values.size());
        Assertions.assertTrue(groupingLevel1Values.contains(1));

        List<Integer> rowId1Values = List.of(resultTable.column("row_id_1").asObjectArray())
                .stream().map(val -> ValueConverter.toInteger(val))
                .collect(Collectors.toList());
        Assertions.assertTrue(rowId1Values.contains(24));
    }


}