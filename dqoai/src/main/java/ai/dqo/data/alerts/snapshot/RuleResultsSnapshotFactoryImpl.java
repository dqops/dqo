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
package ai.dqo.data.alerts.snapshot;

import ai.dqo.data.alerts.factory.RuleResultsTableFactory;
import ai.dqo.data.alerts.filestorage.RuleResultsFileStorageService;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

/**
 * Sensor reading snapshot service. Creates snapshots connected to a persistent storage.
 */
@Component
public class RuleResultsSnapshotFactoryImpl implements RuleResultsSnapshotFactory {
    private final RuleResultsFileStorageService storageService;
    private final RuleResultsTableFactory ruleResultsTableFactory;

    /**
     * Dependency injection constructor.
     * @param storageService Storage service implementation.
     */
    @Autowired
    public RuleResultsSnapshotFactoryImpl(
			RuleResultsFileStorageService storageService,
			RuleResultsTableFactory ruleResultsTableFactory) {
        this.storageService = storageService;
        this.ruleResultsTableFactory = ruleResultsTableFactory;
    }

    /**
     * Creates an empty snapshot that is connected to the rule result storage service that will load requested months on demand.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Rule results snapshot connected to a storage service.
     */
    public RuleResultsSnapshot createSnapshot(String connectionName, PhysicalTableName physicalTableName) {
        Table newAlerts = this.ruleResultsTableFactory.createEmptyRuleResultsTable("new_alerts");
        return new RuleResultsSnapshot(connectionName, physicalTableName, this.storageService, newAlerts);
    }
}
