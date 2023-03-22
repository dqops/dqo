/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.data.storage;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.core.filesystem.virtual.utility.HomeFolderPathUtility;
import ai.dqo.core.locks.AcquiredSharedReadLock;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service providing information regarding metadata contained in the partitions of stored data,
 * such as connections, tables and months present in the stored data segment.
 */
@Service
public class ParquetPartitionMetadataServiceImpl implements ParquetPartitionMetadataService {
    private final UserHomeLockManager userHomeLockManager;
    private LocalUserHomeFileStorageService localUserHomeFileStorageService;
    private HivePartitionPathUtility hivePartitionPathUtility;

    /**
     * Dependency injection constructor.
     * @param userHomeLockManager User home lock manager.
     * @param localUserHomeFileStorageService Local DQO_USER_HOME file storage service.
     * @param hivePartitionPathUtility Utility for manipulating hive partition paths.
     */
    @Autowired
    public ParquetPartitionMetadataServiceImpl(
            UserHomeLockManager userHomeLockManager,
            LocalUserHomeFileStorageService localUserHomeFileStorageService,
            HivePartitionPathUtility hivePartitionPathUtility) {
        this.userHomeLockManager = userHomeLockManager;
        this.localUserHomeFileStorageService = localUserHomeFileStorageService;
        this.hivePartitionPathUtility = hivePartitionPathUtility;
    }

    /**
     * Lists all tables present in the directory dedicated to the connection under specific storage settings.
     *
     * @param connectionName  Connection name.
     * @param storageSettings Storage settings that identify the target partition type.
     * @return Returns a list of physical table names that are currently stored for the connection. Null if connection not found.
     */
    @Override
    public List<PhysicalTableName> listTablesForConnection(String connectionName, FileStorageSettings storageSettings) {
        Path homeRelativeStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());
        ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, null, null);
        String hivePartitionFolderName = this.hivePartitionPathUtility.makeHivePartitionPath(partitionId);
        Path homeRelativePartitionPath = homeRelativeStoragePath.resolve(hivePartitionFolderName);

        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType())) {
            List<HomeFolderPath> connectionStoredFolders = this.localUserHomeFileStorageService.listFolders(
                    HomeFolderPathUtility.createFromFilesystemPath(homeRelativePartitionPath));
            if (connectionStoredFolders == null) {
                return null;
            }

            return connectionStoredFolders.stream()
                    .map(homeFolderPath -> homeFolderPath.getTopFolder().getFileSystemName())
                    .filter(this.hivePartitionPathUtility::validHivePartitionTableFolderName)
                    .map(this.hivePartitionPathUtility::tableFromHivePartitionFolderName)
                    .collect(Collectors.toList());
        }
    }
}
