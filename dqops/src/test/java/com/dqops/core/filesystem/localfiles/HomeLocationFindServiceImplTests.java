/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.localfiles;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.storage.localfiles.HomeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
public class HomeLocationFindServiceImplTests extends BaseTest {
    private HomeLocationFindServiceImpl sut;
    private DqoUserConfigurationProperties userConfigurationProperties;
    private DqoConfigurationProperties dqoConfigurationProperties;

    @BeforeEach
    void setUp() {
		userConfigurationProperties = DqoUserConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(true);
		dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
		this.sut = new HomeLocationFindServiceImpl(userConfigurationProperties, dqoConfigurationProperties);
    }

    @Test
    void getRootUserHomePath_whenUserHomeConfigured_thenReturnsUserHome() {
        String userHomePath = this.sut.getRootUserHomePath();
        Assertions.assertNotNull(userHomePath);
        Assertions.assertTrue(Path.of(userHomePath).isAbsolute());
        Assertions.assertTrue(Files.exists(Path.of(userHomePath)));
        Assertions.assertEquals(Path.of(this.userConfigurationProperties.getHome()).toAbsolutePath().normalize().toString(), userHomePath);
    }

    @Test
    void getDqoHomePath_whenDqoPathConfigured_thenReturnsPath() {
        String dqoHomePath = this.sut.getDqoHomePath();
        Assertions.assertNotNull(dqoHomePath);
        Assertions.assertEquals(Path.of(this.dqoConfigurationProperties.getHome()).toAbsolutePath().normalize().toString(), dqoHomePath);
    }

    @Test
    void getHomePath_whenUserHome_thenReturnsUserHome() {
        String userHomePath = this.sut.getHomePath(HomeType.USER_HOME, UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY);
        Assertions.assertEquals(Path.of(this.userConfigurationProperties.getHome()).toAbsolutePath().normalize().toString(), userHomePath);
    }

    @Test
    void getHomePath_whenUDqoHome_thenReturnsDqoHome() {
        String dqoHomePath = this.sut.getHomePath(HomeType.DQO_HOME, UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY);
        Assertions.assertEquals(Path.of(this.dqoConfigurationProperties.getHome()).toAbsolutePath().normalize().toString(), dqoHomePath);
    }
}
