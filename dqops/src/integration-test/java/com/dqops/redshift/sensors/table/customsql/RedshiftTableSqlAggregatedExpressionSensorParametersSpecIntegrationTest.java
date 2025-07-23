/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.redshift.sensors.table.customsql;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.table.checkspecs.customsql.TableSqlAggregateExpressionCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.redshift.BaseRedshiftIntegrationTest;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.table.customsql.TableSqlAggregatedExpressionSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class RedshiftTableSqlAggregatedExpressionSensorParametersSpecIntegrationTest extends BaseRedshiftIntegrationTest {
    private TableSqlAggregatedExpressionSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableSqlAggregateExpressionCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.redshift);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new TableSqlAggregatedExpressionSensorParametersSpec();
        this.checkSpec = new TableSqlAggregateExpressionCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sut.setSqlExpression("COUNT(*)");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForProfilingCheck(
                sampleTableMetadata, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sut.setSqlExpression("COUNT(*)");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sut.setSqlExpression("COUNT(*)");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues2() {
        this.sut.setSqlExpression("COUNT(*)");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily, "date2");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(10, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(3L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues2() {
        this.sut.setSqlExpression("COUNT(*)");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec,CheckTimeScale.monthly, "date2");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13L, resultTable.column(0).get(0));
    }
}
