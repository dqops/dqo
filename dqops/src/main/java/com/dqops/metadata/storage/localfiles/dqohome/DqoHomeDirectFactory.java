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
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.filesystem.cache.LocalFileSystemCacheImpl;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.core.filesystem.virtual.FileSystemContext;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.statuscache.TableStatusCacheProviderImpl;
import com.dqops.metadata.labels.labelloader.DummyLabelsIndexer;
import com.dqops.metadata.labels.labelloader.LabelsIndexerProviderImpl;
import com.dqops.metadata.lineage.lineagecache.DummyTableLineageCache;
import com.dqops.metadata.lineage.lineagecache.TableLineageCacheProviderImpl;
import com.dqops.utils.StaticBeanFactory;
import com.dqops.utils.logging.UserErrorLoggerImpl;
import com.dqops.utils.serialization.YamlSerializerImpl;

import java.nio.file.Path;

/**
 * Creates a DQOps home instance directly without using Spring IoC.
 * WARNING: this class should be only used internally by DQOps build tools (classes called by Maven during build).
 */
public class DqoHomeDirectFactory {
    /**
     * Creates an instance of DQOps home given a path.
     * WARNING: this method should be only used internally by DQOps build tools (classes called by Maven during build).
     * @param dqoHomePath Path to DQOps home.
     * @param readOnly Open the home context in read-only mode.
     * @return DQOps Home context.
     */
    public static DqoHomeContext openDqoHome(Path dqoHomePath, boolean readOnly) {
        DqoCacheConfigurationProperties dqoCacheConfigurationProperties = new DqoCacheConfigurationProperties();
        dqoCacheConfigurationProperties.setEnable(false);
        dqoCacheConfigurationProperties.setWatchFileSystemChanges(false);
        LocalFileSystemCacheImpl localFileSystemCache = new LocalFileSystemCacheImpl(dqoCacheConfigurationProperties,
                new TableStatusCacheProviderImpl(StaticBeanFactory.getBeanFactory()),
                new LabelsIndexerProviderImpl(StaticBeanFactory.getBeanFactory(), new DummyLabelsIndexer()),
                new TableLineageCacheProviderImpl(StaticBeanFactory.getBeanFactory(), new DummyTableLineageCache()),
                new HomeLocationFindServiceImpl(new DqoUserConfigurationProperties(), new DqoConfigurationProperties()));
        LocalDqoHomeFileStorageServiceImpl localDqoHomeFileStorageService = new LocalDqoHomeFileStorageServiceImpl(dqoHomePath.toString(), localFileSystemCache);
        FileSystemContext fileSystemContext = new FileSystemContext(localDqoHomeFileStorageService);
        LocalFolderTreeNode dqoHomeFolder = new LocalFolderTreeNode(fileSystemContext, new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN));
        DqoHomeContext dqoHomeContext = new DqoHomeContext(dqoHomeFolder);
        YamlSerializerImpl yamlSerializer = new YamlSerializerImpl(new DqoConfigurationProperties(), new UserErrorLoggerImpl(new DqoLoggingUserErrorsConfigurationProperties()));
        FileDqoHomeImpl fileDqoHome = FileDqoHomeImpl.create(dqoHomeContext, yamlSerializer, readOnly);
        dqoHomeContext.setDqoHome(fileDqoHome);

        return dqoHomeContext;
    }
}
