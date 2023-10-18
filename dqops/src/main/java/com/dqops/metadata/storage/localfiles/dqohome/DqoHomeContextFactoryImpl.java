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
package com.dqops.metadata.storage.localfiles.dqohome;

import com.dqops.core.filesystem.localfiles.LocalFileSystemFactory;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.utils.serialization.YamlSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Creates a DQO_HOME come context and loads the home model from the file system.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DqoHomeContextFactoryImpl implements DqoHomeContextFactory {
    private final YamlSerializer yamlSerializer;
    private final LocalFileSystemFactory localFileSystemFactory;
    private DqoHomeContext sharedDqoHomeContext;

    @Autowired
    public DqoHomeContextFactoryImpl(YamlSerializer yamlSerializer, LocalFileSystemFactory localFileSystemFactory) {
        this.yamlSerializer = yamlSerializer;
        this.localFileSystemFactory = localFileSystemFactory;
    }

    /**
     * Opens and returns a shared DQOps user home.
     * @return Dqo home context with an active DQO_HOME home model that is backed by the local home file system.
     */
    @Override
    public DqoHomeContext openLocalDqoHome() {
        synchronized (this) {
            if (this.sharedDqoHomeContext == null) {
                this.sharedDqoHomeContext = loadNewLocalDqoHome();
            }

            return this.sharedDqoHomeContext;
        }
    }

    /**
     * Loads a new DQOps user home context, accessing the files again.
     * @return New instance of a DQOps home context with an active DQO_HOME home model that is backed by the local home file system.
     */
    public DqoHomeContext loadNewLocalDqoHome() {
        LocalFolderTreeNode homeRoot = this.localFileSystemFactory.openLocalDqoHome();
        DqoHomeContext dqoHomeContext = new DqoHomeContext(homeRoot);
        FileDqoHomeImpl fileDqoHomeModel = FileDqoHomeImpl.create(dqoHomeContext, this.yamlSerializer);
        dqoHomeContext.setDqoHome(fileDqoHomeModel);
        return dqoHomeContext;
    }
}
