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
package ai.dqo.metadata.storage.localfiles.dqohome;

import ai.dqo.core.filesystem.localfiles.LocalFileSystemFactory;
import ai.dqo.core.filesystem.localfiles.LocalFolderTreeNode;
import ai.dqo.utils.serialization.YamlSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates a DQO_HOME come context and loads the home model from the file system.
 */
@Component
public class DqoHomeContextFactoryImpl implements DqoHomeContextFactory {
    private final YamlSerializer yamlSerializer;
    private final LocalFileSystemFactory localFileSystemFactory;

    @Autowired
    public DqoHomeContextFactoryImpl(YamlSerializer yamlSerializer, LocalFileSystemFactory localFileSystemFactory) {
        this.yamlSerializer = yamlSerializer;
        this.localFileSystemFactory = localFileSystemFactory;
    }

    /**
     * Opens a local home context, loads the files from the local file system.
     * @return Dqo home context with an active DQO_HOME home model that is backed by the local home file system.
     */
    @Override
    public DqoHomeContext openLocalDqoHome() {
        // TODO: consider caching a home context instance and returning a shared instance, we will need to preload it to avoid race conditions if multiple threads are loading definitions
        // it is probably enough to preload a list of rules and a list of sensor definitions + their list of provider sensors, we can load specs later (on demand)

        LocalFolderTreeNode homeRoot = this.localFileSystemFactory.openLocalDqoHome();
        DqoHomeContext dqoHomeContext = new DqoHomeContext(homeRoot);
        FileDqoHomeImpl fileDqoHomeModel = FileDqoHomeImpl.create(dqoHomeContext, this.yamlSerializer);
        dqoHomeContext.setDqoHome(fileDqoHomeModel);
        return dqoHomeContext;
    }
}
