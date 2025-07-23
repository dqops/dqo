/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.localfiles;

import com.dqops.core.filesystem.virtual.FileSystemContext;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.storage.localfiles.dqohome.LocalDqoHomeFileStorageService;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory class for the local file system. Creates the virtual file system that uses the local file system to access the home folder.
 */
@Component
public class LocalFileSystemFactoryImpl implements LocalFileSystemFactory {
    private final BeanFactory beanFactory;

    /**
     * Creates a file system factory that will use a bean factory to create new instances of file storage services.
     * @param beanFactory Spring bean factory.
     */
    @Autowired
    public LocalFileSystemFactoryImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Creates a local file system that is based on the real user home folder.
     * @param userDomainIdentity User identity and the data domain for which the user home is opened.
     * @return Local file system (root node) for the user's home folder.
     */
    @Override
    public LocalFolderTreeNode openLocalUserHome(UserDomainIdentity userDomainIdentity) {
        LocalUserHomeFileStorageService localUserHomeFileStorageService = this.beanFactory.getBean(LocalUserHomeFileStorageService.class);
        FileSystemContext fileSystemContext = new FileSystemContext(localUserHomeFileStorageService);
        LocalFolderTreeNode userHomeFolder = new LocalFolderTreeNode(fileSystemContext, new HomeFolderPath(userDomainIdentity.getDataDomainFolder()));
        return userHomeFolder;
    }

    /**
     * Creates a local file system that is based on the real DQO_HOME home folder.
     *
     * @return Local file system (root node) for the DQO_HOME home folder.
     */
    @Override
    public LocalFolderTreeNode openLocalDqoHome() {
        LocalDqoHomeFileStorageService localDqoHomeFileStorageService = this.beanFactory.getBean(LocalDqoHomeFileStorageService.class);
        FileSystemContext fileSystemContext = new FileSystemContext(localDqoHomeFileStorageService);
        LocalFolderTreeNode userHomeFolder = new LocalFolderTreeNode(fileSystemContext, new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN));
        return userHomeFolder;
    }
}
