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
package ai.dqo.execution.sqltemplates.rendering;

import ai.dqo.BaseTest;
import ai.dqo.connectors.ProviderDialectSettingsObjectMother;
import ai.dqo.connectors.ProviderType;
import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.configuration.DqoPythonConfigurationProperties;
import ai.dqo.core.configuration.DqoPythonConfigurationPropertiesObjectMother;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.sensors.TimeWindowFilterParameters;
import ai.dqo.execution.sqltemplates.rendering.JinjaTemplateRenderParameters;
import ai.dqo.execution.sqltemplates.rendering.JinjaTemplateRenderServiceImpl;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.sensors.table.volume.TableVolumeRowCountSensorParametersSpec;
import ai.dqo.utils.python.PythonCallerServiceImpl;
import ai.dqo.utils.python.PythonVirtualEnvService;
import ai.dqo.utils.python.PythonVirtualEnvServiceObjectMother;
import ai.dqo.utils.serialization.JsonSerializerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JinjaTemplateRenderServiceImplTests extends BaseTest {
    private JinjaTemplateRenderServiceImpl sut;
    private JinjaTemplateRenderParameters renderParameters;
    private TableSpec table;

    @BeforeEach
    void setUp() {
        DqoConfigurationProperties dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        DqoPythonConfigurationProperties pythonConfigurationProperties = DqoPythonConfigurationPropertiesObjectMother.getDefaultCloned();
        PythonVirtualEnvService pythonVirtualEnvService = PythonVirtualEnvServiceObjectMother.getDefault();
        PythonCallerServiceImpl pythonCallerService = new PythonCallerServiceImpl(
                dqoConfigurationProperties, pythonConfigurationProperties, new JsonSerializerImpl(), pythonVirtualEnvService);
		this.sut = new JinjaTemplateRenderServiceImpl(pythonCallerService, pythonConfigurationProperties);
        table = new TableSpec();
        table.setPhysicalTableName(new PhysicalTableName("schema1", "table1"));
        this.renderParameters = new JinjaTemplateRenderParameters(
                new ConnectionSpec(),
                table,
                new ColumnSpec(),
                null,
                new TableVolumeRowCountSensorParametersSpec(),
                TimeSeriesConfigurationSpec.createCurrentTimeMilliseconds(),
                new TimeWindowFilterParameters(),
                new DataStreamMappingSpec(),
                new SensorDefinitionSpec(),
                new ProviderSensorDefinitionSpec(),
                ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.bigquery),
                SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME,
                SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME
        );
    }

    @Test
    void renderTemplate_whenFakeTemplateWithoutParameters_thenRendersTemplate() {
        String result = this.sut.renderTemplate("select 1", this.renderParameters);
        Assertions.assertEquals("select 1", result);
    }

    @Test
    void renderTemplate_whenSensorReferencingTable_thenRendersTemplateWithFilledField() {
        String result = this.sut.renderTemplate("select 1 from {{ target_table.table_name }}", this.renderParameters);
        Assertions.assertEquals("select 1 from table1", result);
    }
}
