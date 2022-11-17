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
package ai.dqo.core.filesystem.filesystemservice.localfiles;

import ai.dqo.core.configuration.DqoStorageConfigurationProperties;
import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * DQO file system that accesses local files (selected folders) in the DQO_USER_HOME.
 */
@Component
public class DqoUserHomeFileSystemFactoryImpl implements DqoUserHomeFileSystemFactory {
    private LocalFileSystemService localFileSystemService;
    private DqoStorageConfigurationProperties storageConfigurationProperties;
    private LocalDqoUserHomePathProvider localDqoUserHomePathProvider;

    /**
     * Default injection constructor.
     * @param localFileSystemService Local file system service.
     * @param storageConfigurationProperties Local data storage configuration.
     * @param localDqoUserHomePathProvider Local DQO User Home path provider.
     */
    @Autowired
    public DqoUserHomeFileSystemFactoryImpl(LocalFileSystemService localFileSystemService,
                                            DqoStorageConfigurationProperties storageConfigurationProperties,
                                            LocalDqoUserHomePathProvider localDqoUserHomePathProvider) {
        this.localFileSystemService = localFileSystemService;
        this.storageConfigurationProperties = storageConfigurationProperties;
        this.localDqoUserHomePathProvider = localDqoUserHomePathProvider;
    }

    /**
     * Creates a DQO file system that accesses physical files on the local file system.
     * @param rootType Root type (folder type).
     * @return DQO file system that can manage local files in a selected folder.
     */
    public DqoFileSystem createUserHomeFolderFileSystem(DqoRoot rootType) {
        Path absoluteLocalPathToFolder = getAbsoluteLocalPathToFolder(rootType);
        UserHomeFileSystemRoot userHomeFileSystemRoot = new UserHomeFileSystemRoot(absoluteLocalPathToFolder);
        return new DqoFileSystem(userHomeFileSystemRoot, this.localFileSystemService);
    }

    /**
     * Returns an absolute path to a selected folder inside the DQO_USER_HOME.
     * @param rootType Root type (folder type).
     * @return Absolute file system path to a requested folder.
     */
    public Path getAbsoluteLocalPathToFolder(DqoRoot rootType) {
        Path localUserHomePath = this.localDqoUserHomePathProvider.getLocalUserHomePath();

        switch (rootType) {
            case DATA_SENSOR_READOUTS:
                return localUserHomePath.resolve(this.storageConfigurationProperties.getSensorReadoutsStoragePath());

            case DATA_RULE_RESULTS:
                return localUserHomePath.resolve(this.storageConfigurationProperties.getRuleResultsStoragePath());

            case SOURCES:
                return localUserHomePath.resolve(BuiltInFolderNames.SOURCES);

            case SENSORS:
                return localUserHomePath.resolve(BuiltInFolderNames.SENSORS);

            case RULES:
                return localUserHomePath.resolve(BuiltInFolderNames.RULES);

            default:
                throw new IllegalArgumentException("Unsupported root: " +  rootType.toString());
        }
    }
}
