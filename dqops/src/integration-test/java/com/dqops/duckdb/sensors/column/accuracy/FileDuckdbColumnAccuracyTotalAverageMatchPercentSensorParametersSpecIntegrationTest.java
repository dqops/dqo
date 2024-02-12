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
package com.dqops.duckdb.sensors.column.accuracy;

import com.dqops.checks.column.checkspecs.accuracy.ColumnAccuracyTotalAverageMatchPercentCheckSpec;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.duckdb.BaseDuckdbIntegrationTest;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.accuracy.ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class FileDuckdbColumnAccuracyTotalAverageMatchPercentSensorParametersSpecIntegrationTest extends BaseDuckdbIntegrationTest {
    private ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnAccuracyTotalAverageMatchPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private SampleTableMetadata sampleTableMetadataReferenced;

    @BeforeEach
    void setUp() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForCsv();
        String csvFileName = SampleCsvFileNames.ip4_test;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForExplicitCsvFile(csvFileName, connectionSpec);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec();
        this.checkSpec = new ColumnAccuracyTotalAverageMatchPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_withUseOfLocalCsvFile_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "result", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(0.75, resultTable.column(0).get(0));
        Assertions.assertEquals(0.75, resultTable.column(1).get(0));
    }

}
