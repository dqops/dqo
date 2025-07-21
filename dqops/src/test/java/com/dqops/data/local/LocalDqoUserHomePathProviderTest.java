/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.local;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;

@SpringBootTest
public class LocalDqoUserHomePathProviderTest extends BaseTest {
    private LocalDqoUserHomePathProvider sut;

    @BeforeEach
    void setUp() {
    	this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(LocalDqoUserHomePathProvider.class);
    }

    @Test
    void getLocalUserHomePath_whenRetrieved_thenReturnsUserHomePath() {
        Path userHomePath = this.sut.getLocalUserHomePath(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY);
        DqoUserConfigurationProperties dqoUserConfigurationProperties = DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration();
        String expected = dqoUserConfigurationProperties.getHome().replace('\\', '/');
        Assertions.assertEquals(expected, userHomePath.toString().replace('\\', '/'));
    }
}
