/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import com.dqops.BaseTest;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DqoCoreConfigurationTests extends BaseTest {
	private DqoCoreConfiguration sut;

	@BeforeEach
	void setUp() {
		this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(DqoCoreConfiguration.class);
	}

	@Test
	void contextLoads_whenCalled_thenDoesNotThrowSpringExceptions() {
	}
}
