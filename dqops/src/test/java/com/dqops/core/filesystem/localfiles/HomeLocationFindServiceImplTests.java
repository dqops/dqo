/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.core.filesystem.localfiles;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
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
    void getUserHomePath_whenUserHomeConfigured_thenReturnsUserHome() {
        String userHomePath = this.sut.getUserHomePath();
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
        String userHomePath = this.sut.getHomePath(HomeType.USER_HOME);
        Assertions.assertEquals(Path.of(this.userConfigurationProperties.getHome()).toAbsolutePath().normalize().toString(), userHomePath);
    }

    @Test
    void getHomePath_whenUDqoHome_thenReturnsDqoHome() {
        String dqoHomePath = this.sut.getHomePath(HomeType.DQO_HOME);
        Assertions.assertEquals(Path.of(this.dqoConfigurationProperties.getHome()).toAbsolutePath().normalize().toString(), dqoHomePath);
    }
}
