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
package com.dqops.sensors.bigquery.column.accuracy;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.accuracy.ColumnAccuracyTotalAverageMatchPercentCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderServiceObjectMother;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
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

@SpringBootTest
public class ColumnAccuracyTotalAverageMatchPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnAccuracyTotalAverageMatchPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    private SampleTableMetadata sampleTableMetadataReferenced;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip4_test, ProviderType.bigquery);
        this.sampleTableMetadataReferenced = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip6_test, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnAccuracyTotalAverageMatchPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "result", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersMonitoring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(this.sampleTableMetadata, "result", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunReferencedParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadataReferenced, "result", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunReferencedParametersMonitoring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(this.sampleTableMetadataReferenced, "result", this.checkSpec, timeScale);
    }

    private String getTableColumnName(SensorExecutionRunParameters runParameters) {
        return String.format("analyzed_table.`%s`", runParameters.getColumn().getColumnName());
    }

    private String getReferencedTableColumnName(SensorExecutionRunParameters runReferencedParameters) {
        return String.format("analyzed_table.`%s`", runReferencedParameters.getColumn().getColumnName());
    }

    private String getSubstitutedFilter(String tableName) {
        return this.checkSpec.getParameters().getFilter() != null ?
                this.checkSpec.getParameters().getFilter().replace("{alias}", "analyzed_table") : null;
    }

    private String getReferencedSubstitutedFilter(String tableName) {
        return this.checkSpec.getParameters().getFilter() != null ?
                this.checkSpec.getParameters().getFilter().replace("{alias}", "referenced_table") : null;
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionRetrieved_thenDefinitionFoundInDqoHome() {
        SensorDefinitionWrapper sensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findDqoHomeSensorDefinition(this.sut.getSensorDefinitionName());
        Assertions.assertNotNull(sensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(sensorDefinitionWrapper);
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionRetrieved_thenEqualsExpectedName() {
        Assertions.assertEquals("column/accuracy/total_average_match_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {

        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        SensorExecutionRunParameters runReferencedParameters = this.getRunReferencedParametersProfiling();
        runReferencedParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    (SELECT
                        AVG(referenced_table.`result`)
                    FROM `dqo-ai-testing`.`dqo_ai_test_data`.`ip6_test_1065018549043604182` AS referenced_table
                    ) AS expected_value,
                    AVG(analyzed_table.`result`) AS actual_value
                FROM `dqo-ai-testing`.`dqo_ai_test_data`.`ip4_test_9089433916044345932` AS analyzed_table
                WHERE (analyzed_table.`correct` = 1)""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.getReferencedTableColumnName(runReferencedParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                runReferencedParameters.getConnection().getBigquery().getSourceProjectId(),
                runReferencedParameters.getTable().getPhysicalTableName().getSchemaName(),
                runReferencedParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table"),
                this.getReferencedSubstitutedFilter("referenced_table")
        ), renderedTemplate);
    }
    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {

        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        SensorExecutionRunParameters runReferencedParameters = this.getRunReferencedParametersMonitoring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    (SELECT
                        AVG(referenced_table.`result`)
                    FROM `dqo-ai-testing`.`dqo_ai_test_data`.`ip6_test_1065018549043604182` AS referenced_table
                    ) AS expected_value,
                    AVG(analyzed_table.`result`) AS actual_value
                FROM `dqo-ai-testing`.`dqo_ai_test_data`.`ip4_test_9089433916044345932` AS analyzed_table
                WHERE (analyzed_table.`correct` = 1)""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.getReferencedTableColumnName(runReferencedParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                runReferencedParameters.getConnection().getBigquery().getSourceProjectId(),
                runReferencedParameters.getTable().getPhysicalTableName().getSchemaName(),
                runReferencedParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table"),
                this.getReferencedSubstitutedFilter("referenced_table")
        ), renderedTemplate);
    }
}