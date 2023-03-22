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
package ai.dqo.data.checkresults.snapshot;

import ai.dqo.data.checkresults.factory.CheckResultsTableFactory;
import ai.dqo.data.storage.ParquetPartitionStorageService;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

/**
 * Check result snapshot service. Creates snapshots connected to a persistent storage.
 */
@Component
public class CheckResultsSnapshotFactoryImpl implements CheckResultsSnapshotFactory {
    private final ParquetPartitionStorageService storageService;
    private final CheckResultsTableFactory checkResultsTableFactory;

    /**
     * Dependency injection constructor.
     * @param storageService Storage service implementation.
     */
    @Autowired
    public CheckResultsSnapshotFactoryImpl(
            ParquetPartitionStorageService storageService,
			CheckResultsTableFactory checkResultsTableFactory) {
        this.storageService = storageService;
        this.checkResultsTableFactory = checkResultsTableFactory;
    }

    /**
     * Creates an empty snapshot that is connected to the check result storage service that will load requested months on demand.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Rule results snapshot connected to a storage service.
     */
    @Override
    public CheckResultsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName) {
        Table newRuleResults = this.checkResultsTableFactory.createEmptyCheckResultsTable("new_check_results");
        return new CheckResultsSnapshot(connectionName, physicalTableName, this.storageService, newRuleResults);
    }

    /**
     * Creates an empty, read-only snapshot that is connected to the check results storage service that will load requested months on demand.
     * The snapshot contains only selected columns.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param columnNames       Array of column names to load from parquet files. Other columns will not be loaded.
     * @return Rule result snapshot connected to a storage service.
     */
    @Override
    public CheckResultsSnapshot createReadOnlySnapshot(String connectionName, PhysicalTableName physicalTableName, String[] columnNames) {
        Table templateRuleResults = this.checkResultsTableFactory.createEmptyCheckResultsTable("template_check_results");
        return new CheckResultsSnapshot(connectionName, physicalTableName, this.storageService, columnNames, templateRuleResults);
    }
}
