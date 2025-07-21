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
import com.dqops.metadata.basespecs.InstanceStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RuleDefinitionListImplTests extends BaseTest {
    private RuleDefinitionListImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new RuleDefinitionListImpl(false);
    }

    @Test
    void createNewElement_whenCalled_createsElementWithEmptyModel() {
        RuleDefinitionWrapper wrapper = this.sut.createNewElement("src");
        Assertions.assertEquals("src", wrapper.getRuleName());
        Assertions.assertEquals(InstanceStatus.ADDED, wrapper.getStatus());
        Assertions.assertNotNull(wrapper.getSpec());
        Assertions.assertEquals(0, this.sut.size());
    }

    @Test
    void toList_whenCalled_returnList() {
        RuleDefinitionWrapper wrapper = this.sut.createAndAddNew("src");
        Assertions.assertEquals(1, this.sut.size());
        List<RuleDefinitionWrapper> list = this.sut.toList();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertSame(wrapper, list.get(0));
    }
}
