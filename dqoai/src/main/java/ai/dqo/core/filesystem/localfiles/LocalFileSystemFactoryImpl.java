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
package ai.dqo.core.filesystem.localfiles;

import ai.dqo.core.filesystem.virtual.FileSystemContext;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.metadata.storage.localfiles.dqohome.LocalDqoHomeFileStorageService;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
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
     * @return Local file system (root node) for the user's home folder.
     */
    @Override
    public LocalFolderTreeNode openLocalUserHome() {
        LocalUserHomeFileStorageService localUserHomeFileStorageService = this.beanFactory.getBean(LocalUserHomeFileStorageService.class);
        FileSystemContext fileSystemContext = new FileSystemContext(localUserHomeFileStorageService);
        LocalFolderTreeNode userHomeFolder = new LocalFolderTreeNode(fileSystemContext, new HomeFolderPath());
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
        LocalFolderTreeNode userHomeFolder = new LocalFolderTreeNode(fileSystemContext, new HomeFolderPath());
        return userHomeFolder;
    }
}
