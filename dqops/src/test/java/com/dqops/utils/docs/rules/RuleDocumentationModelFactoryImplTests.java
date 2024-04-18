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
package com.dqops.utils.docs.rules;

import com.dqops.BaseTest;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.rules.averages.BetweenPercentMovingAverage7DaysRule5ParametersSpec;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.Objects;

@SpringBootTest
public class RuleDocumentationModelFactoryImplTests extends BaseTest {
    private RuleDocumentationModelFactoryImpl sut;

    @BeforeEach
    void setUp() {
        Path projectRoot = Path.of(".");
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath, true);
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        this.sut = new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToUiCheckMappingService);
    }

    @Test
    void createRuleDocumentation_whenCalledForClassWithRuleParameterFields_thenCreatesDocumentationModel() {
        BetweenPercentMovingAverage7DaysRule5ParametersSpec parametersSpec = new BetweenPercentMovingAverage7DaysRule5ParametersSpec();
        RuleDocumentationModel ruleDocumentation = this.sut.createRuleDocumentation(parametersSpec);

        Assertions.assertNotNull(ruleDocumentation);
        Assertions.assertEquals("Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.", ruleDocumentation.getRuleParametersJavaDoc());
        Assertions.assertEquals("averages", ruleDocumentation.getCategory());
        Assertions.assertEquals("between_percent_moving_average_7_days", ruleDocumentation.getRuleName());
        Assertions.assertEquals("averages/between_percent_moving_average_7_days", ruleDocumentation.getFullRuleName());

        Assertions.assertNotNull(ruleDocumentation.getRuleExample());

        Assertions.assertNotNull(ruleDocumentation.getDefinition());
        Assertions.assertEquals(2, ruleDocumentation.getDefinition().getSpec().getFields().size());
        Assertions.assertTrue(ruleDocumentation.getDefinition().getSpec().getFields().stream().anyMatch(f -> Objects.equals("max_percent_above", f.getFieldName())));
    }
}
