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
import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.metadata.sources.PhysicalTableName;
import org.apache.parquet.Strings;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class that detects and parses hive partitioning folder names.
 */
public class HivePartitionPathUtility {
    private static final Pattern HIVE_PARTITION_CONNECTION_PATTERN =
            Pattern.compile(ParquetPartitioningKeys.CONNECTION + "=(.*)");
    private static final Pattern HIVE_PARTITION_TABLE_PATTERN =
            Pattern.compile(ParquetPartitioningKeys.SCHEMA_TABLE + "=(.*)");
    private static final Pattern HIVE_PARTITION_MONTH_PATTERN =
            Pattern.compile(ParquetPartitioningKeys.MONTH + "=(\\d{4}-(0\\d|1[0-2])-([0-2]\\d|3[0-1]))");

    private HivePartitionPathUtility() {
    }

    /**
     * Creates a hive compatible partition path (folder) for the file.
     * If provided <code>partitionId</code> only partially identifies the partition, the resulting path is also partial.
     * @param partitionId Partition id that identifies the connection, table (with a schema) and the month.
     * @return Hive compatible partition folder path, followed by '/'.
     */
    public static String makeHivePartitionPath(ParquetPartitionId partitionId) {
        // TODO: HivePartitionPaths should be refactored from the ground-up, to make easily serializable and deserializable.
        StringBuilder stringBuilder = new StringBuilder();

        if (!Strings.isNullOrEmpty(partitionId.getDataDomain())) {
            stringBuilder.append(BuiltInFolderNames.DATA_DOMAINS);
            stringBuilder.append('/');
            stringBuilder.append(FileNameSanitizer.encodeForFileSystem(partitionId.getDataDomain()));
            stringBuilder.append('/');
        }

        String connectionName = partitionId.getConnectionName();
        if (connectionName == null) {
            return stringBuilder.toString();
        }

        stringBuilder.append(ParquetPartitioningKeys.CONNECTION);
        stringBuilder.append('=');
        String encodedConnection = FileNameSanitizer.encodeForFileSystem(connectionName);
        stringBuilder.append(encodedConnection);
        stringBuilder.append('/');

        PhysicalTableName tableName = partitionId.getTableName();
        if (tableName != null) {
            stringBuilder.append(ParquetPartitioningKeys.SCHEMA_TABLE);
            stringBuilder.append('=');
            String encodedTable = FileNameSanitizer.encodeForFileSystem(tableName.toString());
            stringBuilder.append(encodedTable);
            stringBuilder.append('/');
        }

        LocalDate month = partitionId.getMonth();
        if (month == null) {
            return stringBuilder.toString();
        }

        stringBuilder.append(ParquetPartitioningKeys.MONTH);
        stringBuilder.append('=');
        stringBuilder.append(month);
        stringBuilder.append('/');

        String hivePartitionPath = stringBuilder.toString();
        return hivePartitionPath;
    }

    /**
     * Checks if the {@code folderName} is a valid hive partition folder name for a connection.
     * @param folderName Folder name to validate.
     * @return True if the name is a valid hive partition folder name. False otherwise.
     */
    public static boolean validHivePartitionConnectionFolderName(String folderName) {
        return HIVE_PARTITION_CONNECTION_PATTERN.asMatchPredicate().test(folderName);
    }

    /**
     * Checks if the {@code folderName} is a valid hive partition folder name for a table.
     * @param folderName Folder name to validate.
     * @return True if the name is a valid hive partition folder name. False otherwise.
     */
    public static boolean validHivePartitionTableFolderName(String folderName) {
        return HIVE_PARTITION_TABLE_PATTERN.asMatchPredicate().test(folderName);
    }

    /**
     * Checks if the {@code folderName} is a valid hive partition folder name for a month.
     * @param folderName Folder name to validate.
     * @return True if the name is a valid hive partition folder name. False otherwise.
     */
    public static boolean validHivePartitionMonthFolderName(String folderName) {
        return HIVE_PARTITION_MONTH_PATTERN.asMatchPredicate().test(folderName);
    }

    /**
     * Extracts the connection from the folder name in hive partition.
     * @param connectionFolderName Folder name representing the partitions for the connection.
     * @return The connection to which the {@code connectionFolderName} refers to. Null if folder name is incorrect.
     */
    public static String connectionFromHivePartitionFolderName(String connectionFolderName) {
        Matcher matcher = HIVE_PARTITION_CONNECTION_PATTERN.matcher(connectionFolderName);
        if (!matcher.find()) {
            return null;
        }
        String connectionNameString = matcher.group(1);
        String decodedName = FileNameSanitizer.decodeFileSystemName(connectionNameString);
        return decodedName;
    }

    /**
     * Extracts the table from the folder name in hive partition.
     * @param tableFolderName Folder name representing the partitions for the table.
     * @return The connection to which the {@code tableFolderName} refers to. Null if folder name is incorrect.
     */
    public static PhysicalTableName tableFromHivePartitionFolderName(String tableFolderName) {
        Matcher matcher = HIVE_PARTITION_TABLE_PATTERN.matcher(tableFolderName);
        if (!matcher.find()) {
            return null;
        }
        String tableNameString = matcher.group(1);
        String decodedName = FileNameSanitizer.decodeFileSystemName(tableNameString);
        return PhysicalTableName.fromBaseFileName(decodedName);
    }

    /**
     * Extracts the month from the folder name in hive partition.
     * @param monthFolderName Folder name representing the partition for the month.
     * @return The month to which the {@code monthFolderName} refers to. Null if folder name is incorrect.
     */
    public static LocalDate monthFromHivePartitionFolderName(String monthFolderName) {
        Matcher matcher = HIVE_PARTITION_MONTH_PATTERN.matcher(monthFolderName);
        if (!matcher.find()) {
            return null;
        }
        String localDateString = matcher.group(1);
        return LocalDate.parse(localDateString);
    }
}
