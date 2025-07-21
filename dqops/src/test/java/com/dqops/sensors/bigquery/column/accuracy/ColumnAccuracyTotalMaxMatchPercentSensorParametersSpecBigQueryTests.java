/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.bigquery.column.accuracy;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.accuracy.ColumnAccuracyTotalMaxMatchPercentCheckSpec;
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
import com.dqops.sensors.column.accuracy.ColumnAccuracyTotalMaxMatchPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnAccuracyTotalMaxMatchPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnAccuracyTotalMaxMatchPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnAccuracyTotalMaxMatchPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    private SampleTableMetadata sampleTableMetadataReferenced;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnAccuracyTotalMaxMatchPercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip4_test, ProviderType.bigquery);
        this.sampleTableMetadataReferenced = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip6_test, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnAccuracyTotalMaxMatchPercentCheckSpec();
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
        Assertions.assertEquals("column/accuracy/total_max_match_percent", this.sut.getSensorDefinitionName());
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
                        MAX(referenced_table.`result`)
                    FROM `dqo-ai-testing`.`dqo_ai_test_data`.`ip6_test_6185415995993241952` AS referenced_table
                    ) AS expected_value,
                    MAX(analyzed_table.`result`) AS actual_value
                FROM `dqo-ai-testing`.`dqo_ai_test_data`.`ip4_test_2859346598431424118` AS analyzed_table
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
                        MAX(referenced_table.`result`)
                    FROM `dqo-ai-testing`.`dqo_ai_test_data`.`ip6_test_6185415995993241952` AS referenced_table
                    ) AS expected_value,
                    MAX(analyzed_table.`result`) AS actual_value
                FROM `dqo-ai-testing`.`dqo_ai_test_data`.`ip4_test_2859346598431424118` AS analyzed_table
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