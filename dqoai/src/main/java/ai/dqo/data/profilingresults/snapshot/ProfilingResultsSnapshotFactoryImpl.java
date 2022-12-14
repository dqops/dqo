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
package ai.dqo.data.profilingresults.snapshot;

import ai.dqo.data.profilingresults.factory.ProfilingResultsTableFactory;
import ai.dqo.data.storage.ParquetPartitionStorageService;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

/**
 * Profiling results snapshot service. Creates snapshots connected to a persistent storage.
 */
@Component
public class ProfilingResultsSnapshotFactoryImpl implements ProfilingResultsSnapshotFactory {
    private final ParquetPartitionStorageService storageService;
    private final ProfilingResultsTableFactory profilingResultsTableFactory;

    /**
     * Dependency injection constructor.
     * @param storageService Storage service implementation.
     * @param profilingResultsTableFactory Profiling results template table factory.
     */
    @Autowired
    public ProfilingResultsSnapshotFactoryImpl(
            ParquetPartitionStorageService storageService,
            ProfilingResultsTableFactory profilingResultsTableFactory) {
        this.storageService = storageService;
        this.profilingResultsTableFactory = profilingResultsTableFactory;
    }

    /**
     * Creates an empty snapshot that is connected to the profiling result storage service that will load requested months on demand.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Profiling results snapshot connected to a storage service.
     */
    @Override
    public ProfilingResultsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName) {
        Table newProfilingResultsTable = this.profilingResultsTableFactory.createEmptyProfilingResultsTable("new_profiling_results");
        return new ProfilingResultsSnapshot(connectionName, physicalTableName, this.storageService, newProfilingResultsTable);
    }
}
