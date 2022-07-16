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
import ai.dqo.metadata.storage.localfiles.userhome.FileUserHomeImpl;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.utils.serialization.YamlSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryImpl.CACHE_DURATION_SECONDS;

/**
 * Creates a DQO_HOME come context and loads the home model from the file system.
 */
@Component
public class DqoHomeContextFactoryImpl implements DqoHomeContextFactory, DqoHomeContextCache {
    private final YamlSerializer yamlSerializer;
    private final LocalFileSystemFactory localFileSystemFactory;
    private DqoHomeContext cachedDqoHomeContext;
    private Instant cachedAt;

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
        LocalFolderTreeNode homeRoot = this.localFileSystemFactory.openLocalDqoHome();
        DqoHomeContext dqoHomeContext = new DqoHomeContext(homeRoot);
        FileDqoHomeImpl fileDqoHomeModel = FileDqoHomeImpl.create(dqoHomeContext, this.yamlSerializer);
        dqoHomeContext.setDqoHome(fileDqoHomeModel);
        dqoHomeContext.setDqoModelCache(this);
        return dqoHomeContext;
    }

    @Override
    public void invalidateCache() {
        this.cachedDqoHomeContext = null;
        this.cachedAt = null;
    }

    @Override
    public DqoHomeContext getCachedLocalDqoHome() {
        if (this.cachedAt != null && this.cachedDqoHomeContext != null &&
                this.cachedAt.plus(CACHE_DURATION_SECONDS, ChronoUnit.SECONDS).isAfter(Instant.now())) {
            return this.cachedDqoHomeContext;
        }

        DqoHomeContext cachedDqoHomeContext = openLocalDqoHome();
        this.cachedDqoHomeContext = cachedDqoHomeContext;
        this.cachedAt = Instant.now();

        return cachedDqoHomeContext;
    }
}
