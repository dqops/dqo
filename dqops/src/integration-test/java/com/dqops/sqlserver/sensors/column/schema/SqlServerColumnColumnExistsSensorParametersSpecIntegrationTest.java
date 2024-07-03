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
package com.dqops.sqlserver.sensors.column.schema;

import com.dqops.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.schema.ColumnColumnExistsSensorParametersSpec;
import com.dqops.sqlserver.BaseSqlServerIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class SqlServerColumnColumnExistsSensorParametersSpecIntegrationTest extends BaseSqlServerIntegrationTest {
    private ColumnColumnExistsSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnSchemaColumnExistsCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp(){
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.sqlserver);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnColumnExistsSensorParametersSpec();
        this.checkSpec = new ColumnSchemaColumnExistsCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfilingOnExistingColumn_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableForProfilingCheck(sampleTableMetadata, this.checkSpec);

        HierarchyId hierarchyId = new HierarchyId("spec", "columns", "date");   // only the last position in HierarchyId is a column name that is used, the rest does not matter
        ColumnSpec columnSpec = new ColumnSpec();
        columnSpec.setHierarchyId(hierarchyId);
        runParameters.setColumn(columnSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(1.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingOnNonExistingColumn_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableForProfilingCheck(sampleTableMetadata, this.checkSpec);

        HierarchyId hierarchyId = new HierarchyId("spec", "columns", "not_existing_column_name");
        ColumnSpec columnSpec = new ColumnSpec();
        columnSpec.setHierarchyId(hierarchyId);
        runParameters.setColumn(columnSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother
                .executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, resultTable.column(0).get(0));
    }

}
