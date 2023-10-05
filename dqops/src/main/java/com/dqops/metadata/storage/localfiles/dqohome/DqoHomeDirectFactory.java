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

import com.dqops.core.configuration.DqoCacheConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoLoggingUserErrorsConfigurationProperties;
import com.dqops.core.filesystem.cache.LocalFileSystemCacheImpl;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.core.filesystem.virtual.FileSystemContext;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.utils.logging.UserErrorLoggerImpl;
import com.dqops.utils.serialization.YamlSerializerImpl;

import java.nio.file.Path;

/**
 * Creates a DQO home instance directly without using Spring IoC.
 * WARNING: this class should be only used internally by DQO build tools (classes called by Maven during build).
 */
public class DqoHomeDirectFactory {
    /**
     * Creates an instance of DQO home given a path.
     * WARNING: this method should be only used internally by DQO build tools (classes called by Maven during build).
     * @param dqoHomePath Path to DQO home.
     * @return DQO Home context.
     */
    public static DqoHomeContext openDqoHome(Path dqoHomePath) {
        DqoCacheConfigurationProperties dqoCacheConfigurationProperties = new DqoCacheConfigurationProperties();
        dqoCacheConfigurationProperties.setEnable(false);
        dqoCacheConfigurationProperties.setWatchFileSystemChanges(false);
        LocalFileSystemCacheImpl localFileSystemCache = new LocalFileSystemCacheImpl(dqoCacheConfigurationProperties);
        LocalDqoHomeFileStorageServiceImpl localDqoHomeFileStorageService = new LocalDqoHomeFileStorageServiceImpl(dqoHomePath.toString(), localFileSystemCache);
        FileSystemContext fileSystemContext = new FileSystemContext(localDqoHomeFileStorageService);
        LocalFolderTreeNode dqoHomeFolder = new LocalFolderTreeNode(fileSystemContext, new HomeFolderPath());
        DqoHomeContext dqoHomeContext = new DqoHomeContext(dqoHomeFolder);
        YamlSerializerImpl yamlSerializer = new YamlSerializerImpl(new DqoConfigurationProperties(), new UserErrorLoggerImpl(new DqoLoggingUserErrorsConfigurationProperties()));
        FileDqoHomeImpl fileDqoHome = FileDqoHomeImpl.create(dqoHomeContext, yamlSerializer);
        dqoHomeContext.setDqoHome(fileDqoHome);

        return dqoHomeContext;
    }
}
