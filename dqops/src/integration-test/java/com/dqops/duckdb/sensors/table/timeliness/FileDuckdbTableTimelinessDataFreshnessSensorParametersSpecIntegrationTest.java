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
package com.dqops.duckdb.sensors.table.timeliness;

import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
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
import com.dqops.sensors.table.timeliness.TableTimelinessDataFreshnessSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
public class FileDuckdbTableTimelinessDataFreshnessSensorParametersSpecIntegrationTest extends BaseDuckdbIntegrationTest {
    private TableTimelinessDataFreshnessSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableDataFreshnessCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbSourceFilesType.csv);
        String csvFileName = SampleCsvFileNames.test_average_delay;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForExplicitCsvFile(csvFileName, connectionSpec);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new TableTimelinessDataFreshnessSensorParametersSpec();
        this.checkSpec = new TableDataFreshnessCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_withUseOfLocalCsvFile_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1:LOCAL_DATE_TIME");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForProfilingCheck(
                sampleTableMetadata, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1:LOCAL_DATE_TIME").max(),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue((double)resultTable.column(0).get(0)>=min && (double)resultTable.column(0).get(0)<=max);
    }

}
