/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.sensors;

import com.dqops.BaseTest;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.sensors.column.patterns.ColumnPatternsTextsNotMatchingRegexPercentSensorParametersSpec;
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
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath, true);
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        this.sut = new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);
    }

    @Test
    void createSensorDocumentation_whenCalledForClassWithSensorParameterFields_thenCreatesDocumentationModel() {
        ColumnPatternsTextsNotMatchingRegexPercentSensorParametersSpec parametersSpec = new ColumnPatternsTextsNotMatchingRegexPercentSensorParametersSpec();
        SensorDocumentationModel sensorDocumentation = this.sut.createSensorDocumentation(parametersSpec);

        Assertions.assertNotNull(sensorDocumentation);
        Assertions.assertEquals("Column level sensor that calculates the percent of values that fit to a regex in a column.", sensorDocumentation.getSensorParametersJavaDoc());
        Assertions.assertEquals("column", sensorDocumentation.getTarget());
        Assertions.assertEquals("patterns", sensorDocumentation.getCategory());
        Assertions.assertEquals("texts_not_matching_regex_percent", sensorDocumentation.getSensorName());
        Assertions.assertEquals("column/patterns/texts_not_matching_regex_percent", sensorDocumentation.getFullSensorName());

        Assertions.assertNotNull(sensorDocumentation.getSqlTemplates());
        Assertions.assertEquals(18,sensorDocumentation.getSqlTemplates().keySet().size());
        Assertions.assertTrue(sensorDocumentation.getSqlTemplates().keySet().stream()
                .map(ProviderTypeModel::getProviderTypeName)
                .anyMatch(provider -> provider.equals("bigquery")));
        Assertions.assertEquals(18,sensorDocumentation.getSqlTemplates().values().size());

        Assertions.assertNotNull(sensorDocumentation.getDefinition());
        Assertions.assertEquals(1, sensorDocumentation.getDefinition().getSpec().getFields().size());
        Assertions.assertTrue(sensorDocumentation.getDefinition().getSpec().getFields().stream().anyMatch(f -> Objects.equals("regex", f.getFieldName())));
    }
}
