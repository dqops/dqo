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
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoLoggingUserErrorsConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.dqocloud.datadomains.CliCurrentDataDomainServiceImpl;
import com.dqops.core.filesystem.localfiles.LocalFileSystemFactory;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNodeObjectMother;
import com.dqops.core.principal.*;
import com.dqops.utils.logging.UserErrorLoggerImpl;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserHomeContextFactoryImplTests extends BaseTest {
    @Test
    void openLocalHome_whenCalledForTestableHome_thenLoadsEmptyHome() {
        LocalFolderTreeNode localHomeFolder = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(true);
        LocalFileSystemFactory factoryMock = mock(LocalFileSystemFactory.class);
        DqoUserPrincipal dqoUserPrincipal = DqoUserPrincipalObjectMother.createStandaloneAdmin();

        UserDomainIdentity userDomainIdentity = dqoUserPrincipal.getDataDomainIdentity();
        when(factoryMock.openLocalUserHome(userDomainIdentity)).thenReturn(localHomeFolder);
        UserErrorLoggerImpl userErrorLogger = new UserErrorLoggerImpl(new DqoLoggingUserErrorsConfigurationProperties());
        YamlSerializer yamlSerializer = new YamlSerializerImpl(DqoConfigurationPropertiesObjectMother.getDefaultCloned(), userErrorLogger);
        JsonSerializer jsonSerializer = JsonSerializerObjectMother.createNew();

        DqoUserConfigurationProperties defaultUserConfiguration = DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration();
        UserHomeContextCacheImpl userHomeContextCache = new UserHomeContextCacheImpl(
                new CliCurrentDataDomainServiceImpl(defaultUserConfiguration),
                new UserDomainIdentityFactoryImpl(defaultUserConfiguration));
        UserHomeContextFactoryImpl sut = new UserHomeContextFactoryImpl(yamlSerializer, jsonSerializer, factoryMock, userHomeContextCache);

        UserHomeContext userHomeContext = sut.openLocalUserHome(userDomainIdentity);
        Assertions.assertNotNull(userHomeContext);
        Assertions.assertSame(localHomeFolder, userHomeContext.getHomeRoot());
        Assertions.assertNotNull(userHomeContext.getUserHome());
    }
}
