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

import ai.dqo.metadata.sources.PhysicalTableName;

import java.time.LocalDate;

/**
 * Utility for operations related to creating and reading hive partition paths.
 */
public interface HivePartitionPathUtility {
    /**
     * Creates a hive compatible partition path (folder) for the file.
     * If provided <code>partitionId</code> only partially identifies the partition, the resulting path is also partial.
     * @param partitionId Partition id that identifies the connection, table (with a schema) and the month.
     * @return Hive compatible partition folder path, followed by '/'.
     */
    String makeHivePartitionPath(ParquetPartitionId partitionId);

    /**
     * Checks if the {@code folderName} is a valid hive partition folder name for a connection.
     * @param folderName Folder name to validate.
     * @return True if the name is a valid hive partition folder name. False otherwise.
     */
    boolean validHivePartitionConnectionFolderName(String folderName);

    /**
     * Checks if the {@code folderName} is a valid hive partition folder name for a table.
     * @param folderName Folder name to validate.
     * @return True if the name is a valid hive partition folder name. False otherwise.
     */
    boolean validHivePartitionTableFolderName(String folderName);

    /**
     * Checks if the {@code folderName} is a valid hive partition folder name for a month.
     * @param folderName Folder name to validate.
     * @return True if the name is a valid hive partition folder name. False otherwise.
     */
    boolean validHivePartitionMonthFolderName(String folderName);

    /**
     * Extracts the connection from the folder name in hive partition.
     * @param connectionFolderName Folder name representing the partitions for the connection.
     * @return The connection to which the {@code connectionFolderName} refers to. Null if folder name is incorrect.
     */
    String connectionFromHivePartitionFolderName(String connectionFolderName);

    /**
     * Extracts the table from the folder name in hive partition.
     * @param tableFolderName Folder name representing the partitions for the table.
     * @return The connection to which the {@code tableFolderName} refers to. Null if folder name is incorrect.
     */
    PhysicalTableName tableFromHivePartitionFolderName(String tableFolderName);

    /**
     * Extracts the month from the folder name in hive partition.
     * @param monthFolderName Folder name representing the partition for the month.
     * @return The month to which the {@code monthFolderName} refers to. Null if folder name is incorrect.
     */
    LocalDate monthFromHivePartitionFolderName(String monthFolderName);
}
