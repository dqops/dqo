/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.sensors.bigquery.table.relevance;

import ai.dqo.BaseTest;
import ai.dqo.checks.table.relevance.TableRelevanceMovingWeekAverageCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import ai.dqo.connectors.bigquery.BigQueryTableSpecObjectMother;
import ai.dqo.connectors.bigquery.BigQueryUserHomeContextObjectMother;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.sensors.table.relevance.TableRelevanceMovingWeekAverageSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableRelevanceMovingWeekAverageSensorParametersSpecBigQueryTests extends BaseTest {
    private TableRelevanceMovingWeekAverageSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private TableRelevanceMovingWeekAverageCheckSpec checkSpec;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        this.userHomeContext = BigQueryUserHomeContextObjectMother.createWithSampleTablesInMemory();
        this.sut = new TableRelevanceMovingWeekAverageSensorParametersSpec();
        this.checkSpec = new TableRelevanceMovingWeekAverageCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableAndLegacyCheck(
                this.userHomeContext.getUserHome(),
                BigQueryConnectionSpecObjectMother.CONNECTION_NAME,
                BigQueryTableSpecObjectMother.DATASET_NAME,
                BigQueryTableSpecObjectMother.TableNames.bq_data_types_test.name(),
                this.checkSpec
        );
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDocumatiHome() {
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(providerSensorDefinitionWrapper);
    }

    @Test
    void renderSensor_whenNoTimeSeries_thenRendersCorrectSql() {
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                SELECT
                  AVG(analyzed_table.``) OVER(ORDER BY date DESC ROWS BETWEEN CURRENT ROW AND 6 FOLLOWING) AS actual_value
                FROM %s AS analyzed_table
                LIMIT 1""",
                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)
                ), renderedTemplate);
    }
}
