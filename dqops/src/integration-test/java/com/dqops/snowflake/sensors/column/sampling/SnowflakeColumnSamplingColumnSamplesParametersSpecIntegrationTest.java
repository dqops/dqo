/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.snowflake.sensors.column.sampling;

import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.redshift.BaseRedshiftIntegrationTest;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.sampling.ColumnSamplingColumnSamplesSensorParametersSpec;
import com.dqops.snowflake.BaseSnowflakeIntegrationTest;
import com.dqops.statistics.column.sampling.ColumnSamplingColumnSamplesStatisticsCollectorSpec;
import com.dqops.testutils.ValueConverter;
import com.dqops.utils.conversion.NumericTypeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.util.Set;


@SpringBootTest
public class SnowflakeColumnSamplingColumnSamplesParametersSpecIntegrationTest extends BaseSnowflakeIntegrationTest {
    private ColumnSamplingColumnSamplesSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnSamplingColumnSamplesStatisticsCollectorSpec collectorSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.nulls_and_uniqueness, ProviderType.snowflake);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
		this.sut = new ColumnSamplingColumnSamplesSensorParametersSpec();
		this.collectorSpec = new ColumnSamplingColumnSamplesStatisticsCollectorSpec();
        this.collectorSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullData_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, ProviderType.snowflake);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnStatisticsCollectorAtTableScope(
                sampleTableMetadata, "int_nulls", this.collectorSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("sample_count", resultTable.column(1).name());
        Assertions.assertEquals(null, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnStatisticsCollectorAtTableScope(
                sampleTableMetadata, "negative", this.collectorSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(21, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals("sample_count", resultTable.column(1).name());
        Set<?> sampleValues = resultTable.column(0).asSet();
        Assertions.assertTrue(sampleValues.stream().anyMatch(sampleValue -> sampleValue != null && NumericTypeConverter.toInt(sampleValue) == 91));
    }
}
