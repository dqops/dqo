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
package ai.dqo.core.synchronization.filesystems.local;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.contract.SynchronizationRoot;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * DQO file system that accesses local files (selected folders) in the DQO_USER_HOME.
 */
@Component
public class LocalSynchronizationFileSystemFactoryImpl implements LocalSynchronizationFileSystemFactory {
    private LocalFileSystemSynchronizationOperations localFileSystemService;
    private LocalDqoUserHomePathProvider localDqoUserHomePathProvider;

    /**
     * Default injection constructor.
     * @param localFileSystemService Local file system service.
     * @param localDqoUserHomePathProvider Local DQO User Home path provider.
     */
    @Autowired
    public LocalSynchronizationFileSystemFactoryImpl(LocalFileSystemSynchronizationOperations localFileSystemService,
                                                     LocalDqoUserHomePathProvider localDqoUserHomePathProvider) {
        this.localFileSystemService = localFileSystemService;
        this.localDqoUserHomePathProvider = localDqoUserHomePathProvider;
    }

    /**
     * Creates a DQO file system that accesses physical files on the local file system.
     * @param rootType Root type (folder type).
     * @return DQO file system that can manage local files in a selected folder.
     */
    public SynchronizationRoot createUserHomeFolderFileSystem(DqoRoot rootType) {
        Path absoluteLocalPathToFolder = getAbsoluteLocalPathToFolder(rootType);
        UserHomeFileSystemSynchronizationRoot userHomeFileSystemRoot = new UserHomeFileSystemSynchronizationRoot(absoluteLocalPathToFolder);
        return new SynchronizationRoot(userHomeFileSystemRoot, this.localFileSystemService);
    }

    /**
     * Returns an absolute path to a selected folder inside the DQO_USER_HOME.
     * @param rootType Root type (folder type).
     * @return Absolute file system path to a requested folder.
     */
    public Path getAbsoluteLocalPathToFolder(DqoRoot rootType) {
        Path localUserHomePath = this.localDqoUserHomePathProvider.getLocalUserHomePath();

        switch (rootType) {
            case data_sensor_readouts:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.SENSOR_READOUTS);

            case data_check_results:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.CHECK_RESULTS);

            case data_errors:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.ERRORS);

            case data_statistics:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.STATISTICS);

            case data_incidents:
                return localUserHomePath.resolve(BuiltInFolderNames.DATA).resolve(BuiltInFolderNames.INCIDENTS);

            case sources:
                return localUserHomePath.resolve(BuiltInFolderNames.SOURCES);

            case sensors:
                return localUserHomePath.resolve(BuiltInFolderNames.SENSORS);

            case rules:
                return localUserHomePath.resolve(BuiltInFolderNames.RULES);

            case checks:
                return localUserHomePath.resolve(BuiltInFolderNames.CHECKS);

            default:
                throw new IllegalArgumentException("Unsupported root: " +  rootType.toString());
        }
    }
}
