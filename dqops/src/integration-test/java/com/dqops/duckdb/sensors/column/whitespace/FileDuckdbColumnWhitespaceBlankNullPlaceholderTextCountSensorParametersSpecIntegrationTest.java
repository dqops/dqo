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
package com.dqops.duckdb.sensors.column.whitespace;

import com.dqops.checks.column.checkspecs.whitespace.ColumnWhitespaceNullPlaceholderTextFoundCheckSpec;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
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
import com.dqops.sensors.column.whitespace.ColumnWhitespaceBlankNullPlaceholderTextCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class FileDuckdbColumnWhitespaceBlankNullPlaceholderTextCountSensorParametersSpecIntegrationTest extends BaseDuckdbIntegrationTest {
    private ColumnWhitespaceBlankNullPlaceholderTextCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnWhitespaceNullPlaceholderTextFoundCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv);
        String csvFileName = SampleCsvFileNames.string_test_data;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForExplicitCsvFile(csvFileName, connectionSpec);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnWhitespaceBlankNullPlaceholderTextCountSensorParametersSpec();
        this.checkSpec = new ColumnWhitespaceNullPlaceholderTextFoundCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_withUseOfLocalCsvFile_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "null_placeholder", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(10L, resultTable.column(0).get(0));
    }

}
