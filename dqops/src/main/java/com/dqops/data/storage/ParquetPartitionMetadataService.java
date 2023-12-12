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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.sources.PhysicalTableName;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service providing information regarding metadata contained in the partitions of stored data,
 * such as connections, tables and months present in the stored data segment.
 */
public interface ParquetPartitionMetadataService {
    /**
     * Lists all connections present in the directory under specific storage settings.
     *
     * @param storageSettings Storage settings that identify the target partition type.
     * @param userIdentity User identity that specifies the data domain.
     * @return Returns a list of connection names that are currently stored for this storage type.
     */
    List<String> listConnections(FileStorageSettings storageSettings, UserDomainIdentity userIdentity);

    /**
     * Lists all tables present in the directory dedicated to the connection under specific storage settings.
     *
     * @param connectionName  Connection name.
     * @param storageSettings Storage settings that identify the target partition type.
     * @param userIdentity User identity that specifies the data domain.
     * @return Returns a list of physical table names that are currently stored for the connection. Null if connection not found.
     */
    List<PhysicalTableName> listTablesForConnection(String connectionName,
                                                    FileStorageSettings storageSettings,
                                                    UserDomainIdentity userIdentity);

    /**
     * Get the month, furthest in the past, for which partition is stored, given the connection and table names, provided storage settings.
     * @param connectionName  Connection name.
     * @param tableName       Table name.
     * @param storageSettings File storage settings.
     * @param userIdentity User identity that specifies the data domain.
     * @return Optional with the oldest month as local date, if it exists. If not, <code>Optional.empty()</code>.
     */
    Optional<LocalDate> getOldestStoredPartitionMonth(String connectionName,
                                                      PhysicalTableName tableName,
                                                      FileStorageSettings storageSettings,
                                                      UserDomainIdentity userIdentity);

    /**
     * Gets ids of partitions that are currently stored for a given connection name, provided storage settings to know where to look.
     * @param connectionName  Connection name.
     * @param storageSettings File storage settings.
     * @param userIdentity User identity that specifies the data domain.
     * @return List of partition ids. Null if parameters are invalid (e.g. target directory doesn't exist).
     */
    List<ParquetPartitionId> getStoredPartitionsIds(String connectionName,
                                                    FileStorageSettings storageSettings,
                                                    UserDomainIdentity userIdentity);

    /**
     * Gets ids of partitions that are currently stored for a given connection and table names, provided storage settings to know where to look.
     * @param connectionName  Connection name.
     * @param tableName       Table name.
     * @param storageSettings File storage settings.
     * @param userIdentity User identity that specifies the data domain.
     * @return List of partition ids. Null if parameters are invalid (e.g. target directory doesn't exist).
     */
    List<ParquetPartitionId> getStoredPartitionsIds(String connectionName,
                                                    PhysicalTableName tableName,
                                                    FileStorageSettings storageSettings,
                                                    UserDomainIdentity userIdentity);
}
