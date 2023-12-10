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
package com.dqops.data.storage;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.filesystem.virtual.utility.HomeFolderPathUtility;
import com.dqops.core.locks.AcquiredSharedReadLock;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service providing information regarding metadata contained in the partitions of stored data,
 * such as connections, tables and months present in the stored data segment.
 */
@Service
public class ParquetPartitionMetadataServiceImpl implements ParquetPartitionMetadataService {
    private final UserHomeLockManager userHomeLockManager;
    private LocalUserHomeFileStorageService localUserHomeFileStorageService;

    /**
     * Dependency injection constructor.
     * @param userHomeLockManager User home lock manager.
     * @param localUserHomeFileStorageService Local DQO_USER_HOME file storage service.
     */
    @Autowired
    public ParquetPartitionMetadataServiceImpl(
            UserHomeLockManager userHomeLockManager,
            LocalUserHomeFileStorageService localUserHomeFileStorageService) {
        this.userHomeLockManager = userHomeLockManager;
        this.localUserHomeFileStorageService = localUserHomeFileStorageService;
    }

    /**
     * Lists all connections present in the directory under specific storage settings.
     *
     * @param storageSettings Storage settings that identify the target partition type.
     * @param userIdentity User identity that specifies the data domain.
     * @return Returns a list of connection names that are currently stored for this storage type.
     */
    @Override
    public List<String> listConnections(FileStorageSettings storageSettings, UserDomainIdentity userIdentity) {
        Path homeRelativeStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());

        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType(), userIdentity.getDataDomainFolder())) {
            List<HomeFolderPath> storageStoredFolders = this.localUserHomeFileStorageService.listFolders(
                    HomeFolderPathUtility.createFromFilesystemPath(homeRelativeStoragePath));
            if (storageStoredFolders == null) {
                return new ArrayList<>();
            }

            return storageStoredFolders.stream()
                    .map(homeFolderPath -> homeFolderPath.getTopFolder().getFileSystemName())
                    .filter(HivePartitionPathUtility::validHivePartitionConnectionFolderName)
                    .map(HivePartitionPathUtility::connectionFromHivePartitionFolderName)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Lists all tables present in the directory dedicated to the connection under specific storage settings.
     *
     * @param connectionName  Connection name.
     * @param storageSettings Storage settings that identify the target partition type.
     * @param userIdentity User identity that specifies the data domain.
     * @return Returns a list of physical table names that are currently stored for the connection. Null if connection not found.
     */
    @Override
    public List<PhysicalTableName> listTablesForConnection(String connectionName, FileStorageSettings storageSettings, UserDomainIdentity userIdentity) {
        Path homeRelativeStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());
        ParquetPartitionId partitionId = new ParquetPartitionId(userIdentity.getDataDomainFolder(), storageSettings.getTableType(), connectionName, null, null);
        String hivePartitionFolderName = HivePartitionPathUtility.makeHivePartitionPath(partitionId);
        Path homeRelativePartitionPath = homeRelativeStoragePath.resolve(hivePartitionFolderName);

        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType(), userIdentity.getDataDomainFolder())) {
            List<HomeFolderPath> connectionStoredFolders = this.localUserHomeFileStorageService.listFolders(
                    HomeFolderPathUtility.createFromFilesystemPath(homeRelativePartitionPath));
            if (connectionStoredFolders == null) {
                return null;
            }

            return connectionStoredFolders.stream()
                    .map(homeFolderPath -> homeFolderPath.getTopFolder().getFileSystemName())
                    .filter(HivePartitionPathUtility::validHivePartitionTableFolderName)
                    .map(HivePartitionPathUtility::tableFromHivePartitionFolderName)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Get the month, furthest in the past, for which partition is stored, given the connection and table names, provided storage settings.
     * @param connectionName  Connection name.
     * @param tableName       Table name.
     * @param storageSettings File storage settings.
     * @param userIdentity User identity that specifies the data domain.
     * @return Optional with the oldest month as local date, if it exists. If not, <code>Optional.empty()</code>.
     */
    @Override
    public Optional<LocalDate> getOldestStoredPartitionMonth(String connectionName,
                                                             PhysicalTableName tableName,
                                                             FileStorageSettings storageSettings,
                                                             UserDomainIdentity userIdentity) {
        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType(), userIdentity.getDataDomainFolder())) {
            List<ParquetPartitionId> storedPartitions = getStoredPartitionsIds(connectionName, tableName, storageSettings, userIdentity);
            if (storedPartitions == null) {
                return Optional.empty();
            }

            return storedPartitions.stream()
                    .map(ParquetPartitionId::getMonth)
                    .min(LocalDate::compareTo);
        }
    }

    /**
     * Gets ids of partitions that are currently stored for a given connection name, provided storage settings to know where to look.
     * @param connectionName  Connection name.
     * @param storageSettings File storage settings.
     * @param userIdentity User identity that specifies the data domain.
     * @return List of partition ids. Null if parameters are invalid (e.g. target directory doesn't exist).
     */
    @Override
    public List<ParquetPartitionId> getStoredPartitionsIds(String connectionName,
                                                           FileStorageSettings storageSettings,
                                                           UserDomainIdentity userIdentity) {
        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType(), userIdentity.getDataDomainFolder())) {
            if (storageSettings.getPartitioningPattern() == TablePartitioningPattern.CTM) {
                List<PhysicalTableName> tablesForConnection = listTablesForConnection(connectionName, storageSettings, userIdentity);
                if (tablesForConnection == null) {
                    return null;
                }

                List<ParquetPartitionId> result = new ArrayList<>();
                for (PhysicalTableName tableName: tablesForConnection) {
                    List<ParquetPartitionId> tablePartitions = getStoredPartitionsIds(connectionName, tableName, storageSettings, userIdentity);
                    if (tablePartitions == null) {
                        continue;
                    }
                    result.addAll(tablePartitions);
                }
                return result;
            }
            else if (storageSettings.getPartitioningPattern() == TablePartitioningPattern.CM) {
                List<ParquetPartitionId> tablePartitions = getStoredPartitionsIds(connectionName, null, storageSettings, userIdentity);
                return tablePartitions;
            } else {
                throw new IllegalArgumentException("Partitioning pattern " + storageSettings.getPartitioningPattern() + "  not supported.");
            }
        }
    }

    /**
     * Gets ids of partitions that are currently stored for a given connection and table names, provided storage settings to know where to look.
     * @param connectionName  Connection name.
     * @param tableName       Table name.
     * @param storageSettings File storage settings.
     * @param userIdentity User identity that specifies the data domain.
     * @return List of partition ids. Null if parameters are invalid (e.g. target directory doesn't exist).
     */
    @Override
    public List<ParquetPartitionId> getStoredPartitionsIds(String connectionName,
                                                           PhysicalTableName tableName,
                                                           FileStorageSettings storageSettings,
                                                           UserDomainIdentity userIdentity) {
        try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(storageSettings.getTableType(), userIdentity.getDataDomainFolder())) {
            Path homeRelativeStoragePath = Path.of(BuiltInFolderNames.DATA, storageSettings.getDataSubfolderName());

            String hivePartitionFolderName = HivePartitionPathUtility.makeHivePartitionPath(
                    new ParquetPartitionId(userIdentity.getDataDomainFolder(), storageSettings.getTableType(), connectionName, tableName, null));
            Path tablePartitionsPath = homeRelativeStoragePath.resolve(hivePartitionFolderName);

            List<HomeFolderPath> tableStoredFolders = this.localUserHomeFileStorageService.listFolders(
                    HomeFolderPathUtility.createFromFilesystemPath(tablePartitionsPath));
            if (tableStoredFolders == null) {
                return null;
            }

            return tableStoredFolders.stream()
                    .map(homeFolderPath -> homeFolderPath.getTopFolder().getFileSystemName())
                    .filter(HivePartitionPathUtility::validHivePartitionMonthFolderName)
                    .map(HivePartitionPathUtility::monthFromHivePartitionFolderName)
                    .map(month -> new ParquetPartitionId(userIdentity.getDataDomainFolder(), storageSettings.getTableType(), connectionName, tableName, month))
                    .collect(Collectors.toList());
        }
    }
}
