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
import com.dqops.metadata.basespecs.InstanceStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProviderSensorDefinitionListImplTests extends BaseTest {
    private ProviderSensorDefinitionListImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new ProviderSensorDefinitionListImpl(false);
    }

    @Test
    void createNewElement_whenCalled_createsElementWithEmptyModel() {
        ProviderSensorDefinitionWrapper wrapper = this.sut.createNewElement(ProviderType.bigquery);
        Assertions.assertEquals(ProviderType.bigquery, wrapper.getProvider());
        Assertions.assertEquals(InstanceStatus.ADDED, wrapper.getStatus());
        Assertions.assertNotNull(wrapper.getSpec());
        Assertions.assertEquals(0, this.sut.size());
    }

    @Test
    void toList_whenCalled_returnList() {
        ProviderSensorDefinitionWrapper wrapper = this.sut.createAndAddNew(ProviderType.bigquery);
        Assertions.assertEquals(1, this.sut.size());
        List<ProviderSensorDefinitionWrapper> list = this.sut.toList();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertSame(wrapper, list.get(0));
    }
}
