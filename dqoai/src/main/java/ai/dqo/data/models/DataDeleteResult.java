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

import java.util.HashMap;
import java.util.Map;

/**
 * Compiled results of the "data delete".
 */
public class DataDeleteResult {
    private Map<ParquetPartitionId, DataDeleteResultPartition> partitionResults = new HashMap<>();

    public Map<ParquetPartitionId, DataDeleteResultPartition> getConnectionResults() {
        return partitionResults;
    }
}
