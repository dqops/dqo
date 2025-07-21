/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.dqohome;

import com.dqops.BaseTest;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DqoHomeContextFactoryImplTests extends BaseTest {
    private DqoHomeContextFactoryImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = (DqoHomeContextFactoryImpl) BeanFactoryObjectMother.getBeanFactory().getBean(DqoHomeContextFactory.class);
    }

    @Test
    void openLocalDqoHome_whenCalled_thenOpensDqoHomeInstance() {
        DqoHomeContext dqoHomeContext = this.sut.openLocalDqoHome();
        Assertions.assertNotNull(dqoHomeContext);
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        Assertions.assertNotNull(dqoHome);
        Assertions.assertNotNull(dqoHome.getSensors());
        Assertions.assertNotNull(dqoHome.getRules());
        Assertions.assertNotNull(dqoHome.getDashboards());
    }
}
