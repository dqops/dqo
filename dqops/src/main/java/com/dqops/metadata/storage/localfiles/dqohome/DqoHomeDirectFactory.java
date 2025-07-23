/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import com.dqops.core.similarity.DummyTableSimilarityRefreshService;
import com.dqops.core.similarity.TableSimilarityRefreshServiceProviderImpl;
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
                new TableSimilarityRefreshServiceProviderImpl(StaticBeanFactory.getBeanFactory(), new DummyTableSimilarityRefreshService()),
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
