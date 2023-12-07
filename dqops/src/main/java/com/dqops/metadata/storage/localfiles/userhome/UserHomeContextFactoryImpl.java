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

import com.dqops.core.filesystem.localfiles.LocalFileSystemFactory;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.core.principal.DqoUserIdentity;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.YamlSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates a user come context and loads the home model from the file system.
 */
@Component
public class UserHomeContextFactoryImpl implements UserHomeContextFactory {
    private final YamlSerializer yamlSerializer;
    private final JsonSerializer jsonSerializer;
    private final LocalFileSystemFactory localFileSystemFactory;
    private final UserHomeContextCache userHomeContextCache;

    /**
     * Default injection constructor.
     * @param yamlSerializer Configured yaml serializer.
     * @param jsonSerializer Configured json serializer.
     * @param localFileSystemFactory Local file system factory to manage local files.
     */
    @Autowired
    public UserHomeContextFactoryImpl(YamlSerializer yamlSerializer,
                                      JsonSerializer jsonSerializer,
                                      LocalFileSystemFactory localFileSystemFactory,
                                      UserHomeContextCache userHomeContextCache) {
        this.yamlSerializer = yamlSerializer;
        this.jsonSerializer = jsonSerializer;
        this.localFileSystemFactory = localFileSystemFactory;
        this.userHomeContextCache = userHomeContextCache;
        userHomeContextCache.setUserHomeContextFactory(this);
    }

    /**
     * Opens a local home context, loads the files from the local file system.
     * @param identity User identity that identifies the user for whom we are opening the user home.
     * @return User home context with an active user home model that is backed by the local home file system.
     */
    @Override
    public UserHomeContext openLocalUserHome(DqoUserIdentity identity) {
        LocalFolderTreeNode homeRoot = this.localFileSystemFactory.openLocalUserHome();
        UserHomeContext userHomeContext = new UserHomeContext(homeRoot, identity);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, this.yamlSerializer, this.jsonSerializer);
        userHomeContext.setUserHome(fileUserHomeModel);
        userHomeContext.setUserModelCache(this.userHomeContextCache);
        return userHomeContext;
    }
}
