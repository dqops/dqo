/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.storage;

import tech.tablesaw.api.Table;

/**
 * Container for the data loaded from a single partition.
 */
public class LoadedMonthlyPartition {
    private ParquetPartitionId partitionId;
    private long lastModified;  // file modification timestamp of the loaded file
    private Table data;

    /**
     * Creates a wrapper over one loaded partition.
     * @param partitionId Partition id - identifies the partition (month, schema.table, month).
     * @param lastModified Last parquet file modification timestamp. Used to detect concurrent file modifications.
     * @param data Table data or null when the partition is empty (no file).
     */
    public LoadedMonthlyPartition(ParquetPartitionId partitionId, long lastModified, Table data) {
        this.partitionId = partitionId;
        this.lastModified = lastModified;
        this.data = data;
    }

    /**
     * Creates a wrapper over one empty partition with no data.
     * @param partitionId Partition id - identifies the partition (month, schema.table, month).
     */
    public LoadedMonthlyPartition(ParquetPartitionId partitionId) {
        this.partitionId = partitionId;
        this.lastModified = 0L;
        this.data = null;
    }

    /**
     * Returns the partition identifier (month, schema.table, month).
     * @return Partition identifier.
     */
    public ParquetPartitionId getPartitionId() {
        return partitionId;
    }

    /**
     * Returns the last modification timestamp of the file that was loaded from disk.
     * @return Last modification date.
     */
    public long getLastModified() {
        return lastModified;
    }

    /**
     * Returns the data from the partition, as a tablesaw table. Returns null if the partition is empty.
     * @return Partition's data that was loaded from the disk or null when the partition was not found.
     */
    public Table getData() {
        return data;
    }
}
