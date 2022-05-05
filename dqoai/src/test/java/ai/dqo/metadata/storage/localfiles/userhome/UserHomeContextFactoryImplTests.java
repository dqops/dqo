/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.storage.localfiles.userhome;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.filesystem.localfiles.LocalFileSystemFactory;
import ai.dqo.core.filesystem.localfiles.LocalFolderTreeNode;
import ai.dqo.core.filesystem.localfiles.LocalFolderTreeNodeObjectMother;
import ai.dqo.utils.serialization.JsonSerializer;
import ai.dqo.utils.serialization.JsonSerializerObjectMother;
import ai.dqo.utils.serialization.YamlSerializer;
import ai.dqo.utils.serialization.YamlSerializerImpl;
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
        when(factoryMock.openLocalUserHome()).thenReturn(localHomeFolder);
        YamlSerializer yamlSerializer = new YamlSerializerImpl(DqoConfigurationPropertiesObjectMother.getDefaultCloned());
        JsonSerializer jsonSerializer = JsonSerializerObjectMother.createNew();
        UserHomeContextFactoryImpl sut = new UserHomeContextFactoryImpl(yamlSerializer, jsonSerializer, factoryMock);

        UserHomeContext userHomeContext = sut.openLocalUserHome();
        Assertions.assertNotNull(userHomeContext);
        Assertions.assertSame(localHomeFolder, userHomeContext.getHomeRoot());
        Assertions.assertNotNull(userHomeContext.getUserHome());
    }
}
