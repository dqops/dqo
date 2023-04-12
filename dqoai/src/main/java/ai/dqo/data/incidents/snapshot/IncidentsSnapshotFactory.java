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

package ai.dqo.data.incidents.snapshot;

/**
 * Incidents snapshot service. Creates snapshots connected to a persistent storage.
 */
public interface IncidentsSnapshotFactory {
    /**
     * Creates an empty snapshot that is connected to the incidents storage service that will load requested months on demand.
     *
     * @param connectionName Connection name.
     * @return Incidents snapshot connected to a storage service.
     */
    IncidentsSnapshot createSnapshot(String connectionName);

    /**
     * Creates an empty, read-only snapshot that is connected to the incidets storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     *
     * @param connectionName Connection name.
     * @param columnNames    Array of column names to load from parquet files. Other columns will not be loaded.
     * @return Incidents snapshot connected to a storage service.
     */
    IncidentsSnapshot createReadOnlySnapshot(String connectionName, String[] columnNames);
}
