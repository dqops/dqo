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

package ai.dqo.data.models;

import ai.dqo.data.storage.ParquetPartitionId;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

/**
 * Compiled results of the "data delete".
 */
public class DataDeleteResult {
    private Map<ParquetPartitionId, DataDeleteResultPartition> partitionResults = new HashMap<>();

    public Map<ParquetPartitionId, DataDeleteResultPartition> getPartitionResults() {
        return partitionResults;
    }

    /**
     * Append results from other {@link DataDeleteResult} object into this one.
     * @param other Other instance of this class, that doesn't have any PartitionId in common.
     */
    public void concat(DataDeleteResult other) {
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
