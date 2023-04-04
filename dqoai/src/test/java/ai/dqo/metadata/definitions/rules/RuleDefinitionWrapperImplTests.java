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
package ai.dqo.metadata.definitions.rules;

import ai.dqo.BaseTest;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionListImpl;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperImpl;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.metadata.fields.ParameterDefinitionsListSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RuleDefinitionWrapperImplTests extends BaseTest {
    private RuleDefinitionWrapperImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new RuleDefinitionWrapperImpl();
    }

    @Test
    void setName_whenSet_returnName() {
		this.sut.setRuleName("test");
        Assertions.assertEquals("test", this.sut.getRuleName());
    }

    @Test
    void clone_whenCalled_thenListOfFieldsIsCloned() {
        RuleDefinitionSpec ruleDefinitionSpec = new RuleDefinitionSpec();
        ParameterDefinitionsListSpec fields = new ParameterDefinitionsListSpec();
        ruleDefinitionSpec.setFields(fields);
        fields.add(new ParameterDefinitionSpec() {{
            setFieldName("field");
        }});
        sut.setSpec(ruleDefinitionSpec);

        RuleDefinitionWrapper cloned = sut.clone();
        Assertions.assertNotNull(cloned);
        Assertions.assertNotSame(cloned, this.sut);
        Assertions.assertNotSame(cloned.getSpec(), ruleDefinitionSpec);
        Assertions.assertNotSame(cloned.getSpec().getFields(), fields);
        Assertions.assertNotSame(cloned.getSpec().getFields().get(0), fields.get(0));
    }
}
