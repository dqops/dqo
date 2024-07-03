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

import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.data.normalization.CommonColumnNames;

/**
 * Configuration for the parquet file storage for each type of supported table (sensor readouts, rule results, etc.)
 */
public class FileStorageSettings {
    /**
     * An array of the default column names that are copied for updated rows from the old row, to the new version of the row.
     */
    public static final String[] DEFAULT_COPIED_COLUMN_NAMES = {
            CommonColumnNames.CREATED_AT_COLUMN_NAME,
            CommonColumnNames.CREATED_BY_COLUMN_NAME
        };

    private DqoRoot tableType;
    private String dataSubfolderName;
    private String parquetFileName;
    private String timePeriodColumnName;
    private String idStringColumnName;
    private String[] copiedColumnNames;
    private TablePartitioningPattern partitioningPattern;

    /**
     * Creates a file storage configuration for a single type of table that is stored as parquet files.
     * @param tableType Table type identified as a folder inside the .data folder.
     * @param dataSubfolderName The name of a subfolder inside the .data folder where the table data (parquet files) are stored.
     * @param parquetFileName The name of a parquet file that is stored in each partition's folder.
     * @param timePeriodColumnName {@link tech.tablesaw.api.DateTimeColumn} that stores the exact time that is used for monthly partitioning
     *                             (but it is not the column that stores date of the first day of the month).
     * @param idStringColumnName Column name (of String type) that stores the primary key for each row, that is used to find rows to be deleted.
     * @param copiedColumnNames An array of column names that are copied for updated rows from the old row to the new row. By default, we should copy the created_at and created_by column values.
     * @param partitioningPattern Table partitioning format.
     */
    public FileStorageSettings(DqoRoot tableType,
                               String dataSubfolderName,
                               String parquetFileName,
                               String timePeriodColumnName,
                               String idStringColumnName,
                               String[] copiedColumnNames,
                               TablePartitioningPattern partitioningPattern) {
        this.tableType = tableType;
        this.dataSubfolderName = dataSubfolderName;
        this.parquetFileName = parquetFileName;
        this.timePeriodColumnName = timePeriodColumnName;
        this.idStringColumnName = idStringColumnName;
        this.copiedColumnNames = copiedColumnNames;
        this.partitioningPattern = partitioningPattern;
    }

    /**
     * Returns the table type identified as a folder inside the .data folder.
     * @return Returns the table type (as a dqo root).
     */
    public DqoRoot getTableType() {
        return tableType;
    }

    /**
     * Returns the name of a subfolder inside the .data folder where the table data (parquet files) are stored.
     * @return The folder name inside the .data folder.
     */
    public String getDataSubfolderName() {
        return dataSubfolderName;
    }

    /**
     * Returns the name of a parquet file that is stored in each partition's folder.
     * @return Parquet file name.
     */
    public String getParquetFileName() {
        return parquetFileName;
    }

    /**
     * Returns the {@link tech.tablesaw.api.DateTimeColumn} that stores the exact time that is used for monthly partitioning
     * (but it is not the column that stores date of the first day of the month)
     * @return DateTime column name used for date partitioning (by months).
     */
    public String getTimePeriodColumnName() {
        return timePeriodColumnName;
    }

    /**
     * Returns the name of a String column  {@link tech.tablesaw.api.TextColumn} that stores the primary key of each row.
     * Used to find rows to be deleted.
     * @return ID column name.
     */
    public String getIdStringColumnName() {
        return idStringColumnName;
    }

    /**
     * Returns an array of column names that are copied from the old row to the new version of the row for updated rows.
     * @return Array of rows to copy.
     */
    public String[] getCopiedColumnNames() {
        return this.copiedColumnNames;
    }

    /**
     * Returns the table partitioning mode. Supported modes are CTM (connection/table/month) and CM (connection/month).
     * @return Table partitioning mode.
     */
    public TablePartitioningPattern getPartitioningPattern() {
        return partitioningPattern;
    }
}
