/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.models;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Results of the "data delete" job for the monthly partition.
 */
public class DataDeleteResultPartition {
    @JsonPropertyDescription("The number of rows that were deleted from the partition.")
    private Integer rowsAffectedCount;

    @JsonPropertyDescription("True if a whole partition (a parquet file) was deleted instead of removing only selected rows.")
    private boolean partitionDeleted = false;

    public DataDeleteResultPartition() {
    }

    public DataDeleteResultPartition(Integer rowsAffectedCount, boolean partitionDeleted) {
        this.rowsAffectedCount = rowsAffectedCount;
        this.partitionDeleted = partitionDeleted;
    }

    /**
     * Gets the number of rows that have been deleted for this partition.
     * @return Number of rows affected by the operation.
     */
    public Integer getRowsAffectedCount() {
        return rowsAffectedCount;
    }

    /**
     * Sets the number of rows that have been deleted for this partition.
     * @param rowsAffectedCount Number of rows affected by the operation.
     */
    public void setRowsAffectedCount(Integer rowsAffectedCount) {
        this.rowsAffectedCount = rowsAffectedCount;
    }

    /**
     * Did the operation delete all rows and subsequently deleted the whole partition.
     * @return Partition deleted flag.
     */
    public boolean isPartitionDeleted() {
        return partitionDeleted;
    }

    /**
     * Sets the flag stating whether the operation deleted all rows and subsequently deleted the whole partition.
     * @param partitionDeleted Partition deleted flag.
     */
    public void setPartitionDeleted(boolean partitionDeleted) {
        this.partitionDeleted = partitionDeleted;
    }
}
