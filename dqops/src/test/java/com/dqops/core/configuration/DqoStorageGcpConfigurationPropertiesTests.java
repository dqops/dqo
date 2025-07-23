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
public class DqoStorageGcpConfigurationPropertiesTests extends BaseTest {
    private DqoStorageGcpConfigurationProperties sut;

    @BeforeEach
    void setUp() {
        this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(DqoStorageGcpConfigurationProperties.class);
    }

    @Test
    void http2_whenDefaultConfiguration_thenReturnsTrue() {
        Assertions.assertTrue(this.sut.isHttp2());
    }

    @Test
    void getHttp2MaxConcurrentStreams_whenDefaultConfiguration_thenReturns5000() {
        Assertions.assertEquals(2000, this.sut.getHttp2MaxConcurrentStreams().intValue());
    }
}
