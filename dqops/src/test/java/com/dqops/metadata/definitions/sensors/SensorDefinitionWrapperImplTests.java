/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.sensors;

import com.dqops.BaseTest;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensorDefinitionWrapperImplTests extends BaseTest {
    private SensorDefinitionWrapperImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new SensorDefinitionWrapperImpl();
    }

    @Test
    void setName_whenSet_returnName() {
		this.sut.setName("test");
        Assertions.assertEquals("test", this.sut.getName());
    }

    @Test
    void setProviderChecks_whenSet_returnProviderChecks() {
        ProviderSensorDefinitionListImpl providerCheckList = new ProviderSensorDefinitionListImpl(false);
		this.sut.setProviderSensors(providerCheckList);
        Assertions.assertEquals(providerCheckList, this.sut.getProviderSensors());
    }

    @Test
    void clone_whenCalled_thenListOfFieldsIsCloned() {
        SensorDefinitionSpec sensorDefinitionSpec = new SensorDefinitionSpec();
        ParameterDefinitionsListSpec fields = new ParameterDefinitionsListSpec();
        sensorDefinitionSpec.setFields(fields);
        fields.add(new ParameterDefinitionSpec() {{
            setFieldName("field");
        }});
        sut.setSpec(sensorDefinitionSpec);

        SensorDefinitionWrapper cloned = sut.clone();
        Assertions.assertNotNull(cloned);
        Assertions.assertNotSame(cloned, this.sut);
        Assertions.assertNotSame(cloned.getSpec(), sensorDefinitionSpec);
        Assertions.assertNotSame(cloned.getSpec().getFields(), fields);
        Assertions.assertNotSame(cloned.getSpec().getFields().get(0), fields.get(0));
    }
}
