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

import com.dqops.data.storage.ParquetPartitionId;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.apache.commons.lang3.NotImplementedException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Compiled results of the "data delete".
 */
public class DeleteStoredDataResult {
    @JsonPropertyDescription("Dictionary of partitions that where deleted or updated when the rows were deleted.")
    private Map<ParquetPartitionId, DataDeleteResultPartition> partitionResults = new LinkedHashMap<>();

    public Map<ParquetPartitionId, DataDeleteResultPartition> getPartitionResults() {
        return partitionResults;
    }

    /**
     * Append results from other {@link DeleteStoredDataResult} object into this one.
     * @param other Other instance of this class, that doesn't have any PartitionId in common.
     */
    public void concat(DeleteStoredDataResult other) {
        if (other == null || other.getPartitionResults() == null) {
            return;
        }

        for (Map.Entry<ParquetPartitionId, DataDeleteResultPartition> partitionEntry: other.partitionResults.entrySet()) {
            ParquetPartitionId partitionId = partitionEntry.getKey();

            if (partitionResults.containsKey(partitionId)) {
                throw new NotImplementedException("No two entries with same PartitionIds should exist in separate DataDeleteResult objects.");
            }
            else {
                partitionResults.put(partitionId, partitionEntry.getValue());
            }
        }
    }
}
