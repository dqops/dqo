/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.teradata.sensors.column.schema;

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
import com.dqops.teradata.BaseTeradataIntegrationTest;
import com.dqops.testutils.ValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class TeradataColumnColumnExistsSensorParametersSpecIntegrationTest extends BaseTeradataIntegrationTest {
    private ColumnColumnExistsSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnSchemaColumnExistsCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp(){
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.ip4_test,
                ProviderType.teradata);
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
        Assertions.assertEquals(1.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
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
        Assertions.assertEquals(0.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

}
