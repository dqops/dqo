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
package com.dqops.mysql.sensors.column.accuracy;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.accuracy.ColumnAccuracyTotalMaxMatchPercentCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.mysql.BaseMysqlIntegrationTest;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.accuracy.ColumnAccuracyTotalMaxMatchPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class MysqlColumnAccuracyTotalMaxMatchPercentSensorParametersSpecIntegrationTest extends BaseMysqlIntegrationTest {
    private ColumnAccuracyTotalMaxMatchPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnAccuracyTotalMaxMatchPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private SampleTableMetadata sampleTableMetadataReferenced;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip4_test, ProviderType.mysql);
        this.sampleTableMetadataReferenced = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip6_test, ProviderType.mysql);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.sampleTableMetadata);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.sampleTableMetadataReferenced);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnAccuracyTotalMaxMatchPercentSensorParametersSpec();
        this.checkSpec = new ColumnAccuracyTotalMaxMatchPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "result", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(1, resultTable.column(0).get(0));
        Assertions.assertEquals(1, resultTable.column(1).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "result", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(1, resultTable.column(0).get(0));
        Assertions.assertEquals(1, resultTable.column(1).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "result", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(1, resultTable.column(0).get(0));
        Assertions.assertEquals(1, resultTable.column(1).get(0));
    }
}