/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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

        UserHomeContext userHomeContext = sut.openLocalUserHome(userDomainIdentity, false);
        Assertions.assertNotNull(userHomeContext);
        Assertions.assertSame(localHomeFolder, userHomeContext.getHomeRoot());
        Assertions.assertNotNull(userHomeContext.getUserHome());
    }
}
