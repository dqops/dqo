/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.rules;

import com.dqops.BaseTest;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionListImpl;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapperImpl;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
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
