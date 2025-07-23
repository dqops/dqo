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
import com.dqops.connectors.ProviderType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProviderSensorDefinitionWrapperImplTests extends BaseTest {
    private ProviderSensorDefinitionWrapper sut;

    @BeforeEach
    void setUp() {
		this.sut = new ProviderSensorDefinitionWrapperImpl();
    }

    @Test
    void setProvider_whenSet_returnProvider() {
		this.sut.setProvider(ProviderType.bigquery);
        Assertions.assertEquals(ProviderType.bigquery, this.sut.getProvider());
    }

    @Test
    void setSwlTemplate_whenSet_returnSqlTemplate() {
		this.sut.setSqlTemplate("test");
        Assertions.assertEquals("test", this.sut.getSqlTemplate());
    }
}
