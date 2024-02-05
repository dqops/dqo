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
package com.dqops.data.errors.normalization;

import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.utils.tables.TableColumnUtility;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;

/**
 * Describes the dataset (dataframe) returned from a failed sensor. Identifies the time series column, data stream columns, etc.
 * The columns are normalized.
 */
public class ErrorsNormalizedResult extends SensorReadoutsNormalizedResult {
    private final TextColumn readoutIdColumn;
    private final TextColumn errorMessageColumn;
    private final TextColumn errorSourceColumn;
    private final DateTimeColumn errorTimestampColumn;


    /**
     * Creates a check error dataset, extracting key columns.
     * WARNING: this method has an intended side result - it adds missing columns to the table.
     *
     * @param table Sorted table with check errors - may be modified by adding missing columns.
     */
    public ErrorsNormalizedResult(Table table) {
        this(table, true);
    }

    /**
     * Creates a check error dataset, extracting key columns.
     *
     * @param table Sorted table with check errors - may be modified by adding missing columns.
     * @param addColumWhenMissing Adds the columns if they are missing.
     */
    public ErrorsNormalizedResult(Table table, boolean addColumWhenMissing) {
        super(table, addColumWhenMissing);
        this.readoutIdColumn = TableColumnUtility.getOrAddTextColumn(table, ErrorsColumnNames.READOUT_ID_COLUMN_NAME, addColumWhenMissing);
        this.errorMessageColumn = TableColumnUtility.getOrAddTextColumn(table, ErrorsColumnNames.ERROR_MESSAGE_COLUMN_NAME, addColumWhenMissing);
        this.errorSourceColumn = TableColumnUtility.getOrAddTextColumn(table, ErrorsColumnNames.ERROR_SOURCE_COLUMN_NAME, addColumWhenMissing);
        this.errorTimestampColumn = TableColumnUtility.getOrAddDateTimeColumn(table, ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME, addColumWhenMissing);
    }

    /**
     * Returns an error with a unique ID of the sensor readout. There may be multiple rows with the same readout_id, especially for monitoring and partitioned checks.
     * @return A column with a readout ID that is the same ID that would be used as the "ID" column in sensor_readouts or rule_results.
     */
    public TextColumn getReadoutIdColumn() {
        return readoutIdColumn;
    }

    /**
     * Returns the column that contains the error message.
     * @return Error message column.
     */
    public TextColumn getErrorMessageColumn() {
        return errorMessageColumn;
    }

    /**
     * Returns the column that returns the error source, values are in {@link com.dqops.data.errors.factory.ErrorSource} - sensor or rule.
     * @return Error source column.
     */
    public TextColumn getErrorSourceColumn() {
        return errorSourceColumn;
    }

    /**
     * Returns the error timestamp column. It is the time when the error happened, but it is stored as a local timestamp of the DQOps instance that executed the failed check.
     * This column is used as a partitioning key to store monthly parquet partitions.
     * @return Error timestamp column.
     */
    public DateTimeColumn getErrorTimestampColumn() {
        return errorTimestampColumn;
    }
}
