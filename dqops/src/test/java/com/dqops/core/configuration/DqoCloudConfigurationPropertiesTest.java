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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DqoCloudConfigurationPropertiesTest extends BaseTest {
    private DqoCloudConfigurationProperties sut;

    @BeforeEach
    void setUp() {
		this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(DqoCloudConfigurationProperties.class);
    }

    @Test
    void getApiKeyRequestUrl_whenRetrieved_thenReturnsUrl() {
        String loginUrl = this.sut.getApiKeyRequestUrl();
        Assertions.assertNotNull(loginUrl);
        Assertions.assertEquals("https://cloud.dqops.com/requestapikey/", loginUrl);
    }
}
