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
package com.dqops.core.synchronization.fileexchange;

import com.dqops.cloud.rest.model.RefreshedPartitionModel;
import com.dqops.core.filesystem.metadata.FileDifference;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.data.storage.HivePartitionPathUtility;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Parameter object that identifies a target table and a list of modified connections, tables and days of the months (the first day of the month) that should be reloaded.
 */
public class TargetTableModifiedPartitions {
    private DqoRoot targetTable;
    private Set<String> affectedConnections = new LinkedHashSet<>();
    private Set<String> affectedTables = new LinkedHashSet<>();
    private Set<LocalDate> affectedMonths = new LinkedHashSet<>();
    private Set<RefreshedPartitionModel> affectedPartitions = new LinkedHashSet<>();

    /**
     * Creates an object that identifies the target table and a list of modified months, table folders and month folders.
     * @param targetTable Target table.
     */
    public TargetTableModifiedPartitions(DqoRoot targetTable) {
        this.targetTable = targetTable;
    }

    /**
     * Returns the root table that identifies the target table that was modified.
     * @return Target table.
     */
    public DqoRoot getTargetTable() {
        return targetTable;
    }

    /**
     * A set of unique connection names that have changes. When the list is empty, all connections should be refreshed.
     * The caller should add additional unique connections that were modified.
     * @return A set of connections.
     */
    public Set<String> getAffectedConnections() {
        return affectedConnections;
    }

    /**
     * A set of unique target name names (schema.table) that have changes. When the list is empty, all tables should be refreshed.
     * The caller should add additional unique tables that were modified.
     * @return A set of table names.
     */
    public Set<String> getAffectedTables() {
        return affectedTables;
    }

    /**
     * A set of unique days of the month (the first day of the month) that have changes. When the list is empty, all months should be refreshed.
     * The caller should add additional unique months that were modified.
     * @return A set of month's dates.
     */
    public Set<LocalDate> getAffectedMonths() {
        return affectedMonths;
    }

    /**
     * Returns a set of affected partitions, that identify exactly the connection, table and a monthly partition.
     * @return Set of unique partitions that were affected.
     */
    public Set<RefreshedPartitionModel> getAffectedPartitions() {
        return affectedPartitions;
    }

    /**
     * Sets a reference to a set of affected partitions.
     * @param affectedPartitions Collection of affected partitions.
     */
    public void setAffectedPartitions(Set<RefreshedPartitionModel> affectedPartitions) {
        this.affectedPartitions = affectedPartitions;
    }

    /**
     * Checks if there are any changes that must be refreshed. If at least one connection, table or month was modified, we will refresh it.
     * @return True when there are any changes, false if no files (parquet files for a table partition) were modified.
     */
    public boolean hasAnyChanges() {
        if (!this.affectedConnections.isEmpty()) {
            return true;
        }

        if (!this.affectedTables.isEmpty()) {
            return true;
        }

        if (!this.affectedMonths.isEmpty()) {
            return true;
        }

        if (!this.affectedPartitions.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Adds all unique changes to the list of affected connections, tables and months.
     * @param localChanges Collection of local changes.
     */
    public void addModifications(Collection<FileDifference> localChanges) {
        for (FileDifference fileDifference : localChanges) {
            Path relativePath = fileDifference.getRelativePath();
            RefreshedPartitionModel affectedPartition = null;

            for (int nameIndex = 0; nameIndex < relativePath.getNameCount() - 1; nameIndex++) {
                String folderName = relativePath.getName(nameIndex).toString();

                if (HivePartitionPathUtility.validHivePartitionConnectionFolderName(folderName)) {
                    String connectionName = HivePartitionPathUtility.connectionFromHivePartitionFolderName(folderName);
                    this.affectedConnections.add(connectionName);
                    if (affectedPartition == null) {
                        affectedPartition = new RefreshedPartitionModel();
                    }
                    affectedPartition.setConnection(connectionName);
                }
                else if (HivePartitionPathUtility.validHivePartitionTableFolderName(folderName)) {
                    String tableName = HivePartitionPathUtility.tableFromHivePartitionFolderName(folderName).toBaseFileName();
                    this.affectedTables.add(tableName);
                    if (affectedPartition == null) {
                        affectedPartition = new RefreshedPartitionModel();
                    }
                    affectedPartition.setSchemaTableName(tableName);
                } else if (HivePartitionPathUtility.validHivePartitionMonthFolderName(folderName)) {
                    LocalDate monthDate = HivePartitionPathUtility.monthFromHivePartitionFolderName(folderName);
                    this.affectedMonths.add(monthDate);
                    if (affectedPartition == null) {
                        affectedPartition = new RefreshedPartitionModel();
                    }
                    affectedPartition.setMonth(monthDate);
                }
            }

            if (affectedPartition != null) {
                this.affectedPartitions.add(affectedPartition);
            }
        }
    }
}
