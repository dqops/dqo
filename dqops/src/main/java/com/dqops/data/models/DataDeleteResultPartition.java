/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
