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
package com.dqops.utils.docs.sensors;

import com.dqops.BaseTest;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.sensors.column.strings.ColumnStringsStringMatchRegexPercentSensorParametersSpec;
import com.dqops.utils.docs.ProviderTypeModel;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.Objects;

@SpringBootTest
public class SensorDocumentationModelFactoryImplTests extends BaseTest {
    private SensorDocumentationModelFactoryImpl sut;

    @BeforeEach
    void setUp() {
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        this.sut = new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);
    }

    @Test
    void createSensorDocumentation_whenCalledForClassWithSensorParameterFields_thenCreatesDocumentationModel() {
        ColumnStringsStringMatchRegexPercentSensorParametersSpec parametersSpec = new ColumnStringsStringMatchRegexPercentSensorParametersSpec();
        SensorDocumentationModel sensorDocumentation = this.sut.createSensorDocumentation(parametersSpec);

        Assertions.assertNotNull(sensorDocumentation);
        Assertions.assertEquals("Column level sensor that calculates the percent of values that fit to a regex in a column.", sensorDocumentation.getSensorParametersJavaDoc());
        Assertions.assertEquals("column", sensorDocumentation.getTarget());
        Assertions.assertEquals("strings", sensorDocumentation.getCategory());
        Assertions.assertEquals("string_match_regex_percent", sensorDocumentation.getSensorName());
        Assertions.assertEquals("column/strings/string_match_regex_percent", sensorDocumentation.getFullSensorName());

        Assertions.assertNotNull(sensorDocumentation.getSqlTemplates());
        Assertions.assertEquals(8,sensorDocumentation.getSqlTemplates().keySet().size());
        Assertions.assertTrue(sensorDocumentation.getSqlTemplates().keySet().stream()
                .map(ProviderTypeModel::getProviderTypeName)
                .anyMatch(provider -> provider.equals("bigquery")));
        Assertions.assertEquals(8,sensorDocumentation.getSqlTemplates().values().size());

        Assertions.assertNotNull(sensorDocumentation.getDefinition());
        Assertions.assertEquals(1, sensorDocumentation.getDefinition().getSpec().getFields().size());
        Assertions.assertTrue(sensorDocumentation.getDefinition().getSpec().getFields().stream().anyMatch(f -> Objects.equals("regex", f.getFieldName())));
    }
}
