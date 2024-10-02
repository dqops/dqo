/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

package com.dqops.metadata.lineage.lineageservices;

import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
import com.dqops.metadata.lineage.lineagecache.TableLineageCache;
import com.dqops.metadata.lineage.lineagecache.TableLineageCacheEntry;
import com.dqops.metadata.lineage.lineagecache.TableLineageRefreshStatus;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Table data lineage service that returns a data lineage graph around a given table.
 */
@Component
public class TableLineageServiceImpl implements TableLineageService {
    private final TableLineageCache tableLineageCache;
    private final TableStatusCache tableStatusCache;

    /**
     * Default dependency injection constructor.
     * @param tableLineageCache Table data lineage cache.
     * @param tableStatusCache Table data quality status cache.
     */
    @Autowired
    public TableLineageServiceImpl(TableLineageCache tableLineageCache,
                                   TableStatusCache tableStatusCache) {
        this.tableLineageCache = tableLineageCache;
        this.tableStatusCache = tableStatusCache;
    }

    /**
     * Returns a data lineage in a form of a flattened graph, returning all data flows (from table A to table B),
     * and the data quality status.
     * @param userHome User home to find if tables are present.
     * @param referenceTable The start table.
     * @param upstreamLineage True when the data lineage should be calculated for upstream tables.
     * @param downstreamLineage True when the data lineage (the impact radius) should be calculated for downstream tables.
     * @return Table lineage model.
     */
    @Override
    public TableLineageModel buildDataLineageModel(UserHome userHome,
                                                   DomainConnectionTableKey referenceTable,
                                                   boolean upstreamLineage,
                                                   boolean downstreamLineage) {
        TableLineageModel tableLineageModel = new TableLineageModel();
        tableLineageModel.setRelativeTable(referenceTable);
        LinkedHashMap<DomainConnectionTableKey, TableCurrentDataQualityStatusModel> upstreamTableStatuses = new LinkedHashMap<>();
        LinkedHashSet<DomainConnectionTableKey> visitedTables = new LinkedHashSet<>();
        LinkedHashSet<DomainConnectionTableKey> onStackTables = new LinkedHashSet<>();

        TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus = this.tableStatusCache.getCurrentTableStatus(
                referenceTable, null);

        visitedTables.add(referenceTable);

        if (upstreamLineage) {
            upstreamCombinedQualityStatus = collectUpstreamLineage(
                    userHome, referenceTable, tableLineageModel, upstreamTableStatuses, visitedTables, onStackTables);
            tableLineageModel.setRelativeTableCumulativeQualityStatus(upstreamCombinedQualityStatus);
        }

        if (downstreamLineage) {
            LinkedHashSet<DomainConnectionTableKey> upstreamOnStackTables = new LinkedHashSet<>(visitedTables); // upstream tables that will be collected, if referenced from downstream tables, will be treated as a cycle and ignored
            collectDownstreamLineage(userHome, upstreamCombinedQualityStatus, referenceTable, tableLineageModel, visitedTables, upstreamOnStackTables);
        }

        for (TableLineageFlowModel flowModel : tableLineageModel.getFlows()) {
            flowModel.setSourceTableQualityStatus(flowModel.getSourceTableQualityStatus() != null ?
                    flowModel.getSourceTableQualityStatus().shallowCloneWithoutCheckResultsAndColumns() : null);
            flowModel.setTargetTableQualityStatus(flowModel.getTargetTableQualityStatus() != null ?
                    flowModel.getTargetTableQualityStatus().shallowCloneWithoutCheckResultsAndColumns() : null);
            flowModel.setUpstreamCombinedQualityStatus(flowModel.getUpstreamCombinedQualityStatus() != null ?
                    flowModel.getUpstreamCombinedQualityStatus().shallowCloneWithoutCheckResultsAndColumns() : null);
        }

        return tableLineageModel;
    }

    /**
     * Collects upstream table lineage using a depth-first method.
     * @param userHome User home to find the tables.
     * @param targetTable Current table to include.
     * @param tableLineageModel Target table lineage model to add data flows.
     * @param visitedUpstreamTableStatuses Dictionary of combined table quality statues of already visited tables, in case that other tables also use their statuses (dual paths).
     * @param visitedTables A set of tables that were already visited and should not be visited again to avoid duplication.
     * @param onStackTables Tables that are on the stack. Used to detect cycles in the graph.
     * @return The combined data quality status of all upstream tables.
     */
    public TableCurrentDataQualityStatusModel collectUpstreamLineage(
            UserHome userHome,
            DomainConnectionTableKey targetTable,
            TableLineageModel tableLineageModel,
            Map<DomainConnectionTableKey, TableCurrentDataQualityStatusModel> visitedUpstreamTableStatuses,
            LinkedHashSet<DomainConnectionTableKey> visitedTables, 
            LinkedHashSet<DomainConnectionTableKey> onStackTables) {
        TableCurrentDataQualityStatusModel targetTableQualityStatus = this.tableStatusCache.getCurrentTableStatus(
                targetTable, null);

        if (targetTableQualityStatus == null || !checkIfTableExists(userHome, targetTable)) {
            tableLineageModel.setDataLineageFullyLoaded(false);
        }

        TableCurrentDataQualityStatusModel resultStatus = targetTableQualityStatus == null ?
                new TableCurrentDataQualityStatusModel() {{
                    setTableExist(false);
                }} : targetTableQualityStatus.deepClone();

        visitedTables.add(targetTable);
        onStackTables.add(targetTable);

        TableLineageCacheEntry tableLineageEntry = this.tableLineageCache.getTableLineageEntry(targetTable);
        if (tableLineageEntry != null) {
            if (tableLineageEntry.getStatus() != TableLineageRefreshStatus.LOADED) {
                tableLineageModel.setDataLineageFullyLoaded(false);
                resultStatus.setTableExist(false); // some missing information, requires reload
            }

            Set<DomainConnectionTableKey> upstreamSourceTables = tableLineageEntry.getUpstreamSourceTables();
            for (DomainConnectionTableKey upstreamTableKey : upstreamSourceTables) {
                TableCurrentDataQualityStatusModel upstreamOnlyQualityStatus = this.tableStatusCache.getCurrentTableStatus(upstreamTableKey, null);
                if (upstreamOnlyQualityStatus == null) {
                    upstreamOnlyQualityStatus = new TableCurrentDataQualityStatusModel() {{
                        setTableExist(false);
                    }};
                }

                if (visitedTables.contains(upstreamTableKey)) {
                    if (!onStackTables.contains(upstreamTableKey)) {
                        TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus = visitedUpstreamTableStatuses.get(upstreamTableKey);
                        TableLineageFlowModel flowFromVisitedTable = new TableLineageFlowModel(upstreamTableKey, targetTable,
                                upstreamOnlyQualityStatus, targetTableQualityStatus, upstreamCombinedQualityStatus);
                        tableLineageModel.getFlows().add(flowFromVisitedTable);

                        resultStatus.appendResultsFromUpstreamTable(upstreamCombinedQualityStatus);
                    } else {
                        // cycle in the data lineage, ignoring this path
                    }
                } else {
                    TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus = collectUpstreamLineage(
                            userHome, upstreamTableKey, tableLineageModel, visitedUpstreamTableStatuses, visitedTables, onStackTables);
                    TableLineageFlowModel flowFromVisitedTable = new TableLineageFlowModel(upstreamTableKey, targetTable,
                            upstreamOnlyQualityStatus, targetTableQualityStatus, upstreamCombinedQualityStatus);
                    tableLineageModel.getFlows().add(flowFromVisitedTable);
                    resultStatus.appendResultsFromUpstreamTable(upstreamCombinedQualityStatus);
                }
            }
        } else {
            tableLineageModel.setDataLineageFullyLoaded(false);
            resultStatus.setTableExist(false); // some missing information, requires reload
        }

        onStackTables.remove(targetTable);
        visitedUpstreamTableStatuses.put(targetTable, resultStatus);
        return resultStatus;
    }

    /**
     * Collects downstream table lineage.
     * @param userHome User home to find the tables.
     * @param upstreamCombinedQualityStatus The combined data quality status of the current table, that will impact downstream tables.
     * @param sourceTable The start table to traverse.
     * @param tableLineageModel Target data lineage model to append data flows.
     * @param visitedTables Collection of already visited nodes. They will not be processed again.
     * @param onStackTables Collection of nodes that are on the stack. References to these nodes should be ignored. Initially, all upstream nodes are added.
     */
    public void collectDownstreamLineage(UserHome userHome,
                                         TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus,
                                         DomainConnectionTableKey sourceTable,
                                         TableLineageModel tableLineageModel,
                                         LinkedHashSet<DomainConnectionTableKey> visitedTables,
                                         LinkedHashSet<DomainConnectionTableKey> onStackTables) {
        visitedTables.add(sourceTable);
        onStackTables.add(sourceTable);

        TableCurrentDataQualityStatusModel sourceTableQualityStatus = this.tableStatusCache.getCurrentTableStatus(sourceTable, null);
        if (sourceTableQualityStatus == null || !checkIfTableExists(userHome, sourceTable)) {
            tableLineageModel.setDataLineageFullyLoaded(false);
        }

        TableLineageCacheEntry tableLineageEntry = this.tableLineageCache.getTableLineageEntry(sourceTable);

        if (tableLineageEntry != null) {
            if (tableLineageEntry.getStatus() != TableLineageRefreshStatus.LOADED) {
                tableLineageModel.setDataLineageFullyLoaded(false);
                upstreamCombinedQualityStatus.setTableExist(false); // some missing information, requires reload
            }

            Set<DomainConnectionTableKey> downstreamTargetTables = tableLineageEntry.getDownstreamTargetTables();
            for (DomainConnectionTableKey downstreamTableKey : downstreamTargetTables) {
                TableCurrentDataQualityStatusModel downstreamOnlyQualityStatus = this.tableStatusCache.getCurrentTableStatus(downstreamTableKey, null);
                TableCurrentDataQualityStatusModel downstreamCombinedQualityStatus =
                        downstreamOnlyQualityStatus != null ? downstreamOnlyQualityStatus.deepClone() : new TableCurrentDataQualityStatusModel();
                downstreamCombinedQualityStatus.appendResultsFromUpstreamTable(upstreamCombinedQualityStatus);

                if (visitedTables.contains(downstreamTableKey)) {
                    if (!onStackTables.contains(downstreamTableKey)) {
                        // another path to a target table, we will add this path, but the combined quality statuses of tables below that target table will not be upgraded to receive this status
                        // TODO: we could rewrite the combined statues of all downstream tables... but that would be a little complex

                        TableLineageFlowModel flowToVisitedDownstreamTable = new TableLineageFlowModel(sourceTable, downstreamTableKey,
                                sourceTableQualityStatus, downstreamOnlyQualityStatus, downstreamCombinedQualityStatus);
                        tableLineageModel.getFlows().add(flowToVisitedDownstreamTable);

                    } else {
                        // cycle or back reference to the upstream tables, ignoring
                    }
                } else {
                    TableLineageFlowModel flowToDownstreamTable = new TableLineageFlowModel(sourceTable, downstreamTableKey,
                            sourceTableQualityStatus, downstreamOnlyQualityStatus, downstreamCombinedQualityStatus);
                    tableLineageModel.getFlows().add(flowToDownstreamTable);

                    collectDownstreamLineage(userHome, downstreamCombinedQualityStatus, downstreamTableKey, tableLineageModel, visitedTables, onStackTables);
                }
            }
        } else {
            tableLineageModel.setDataLineageFullyLoaded(false);
            upstreamCombinedQualityStatus.setTableExist(false); // some missing information, requires reload
        }

        onStackTables.remove(sourceTable);
    }

    /**
     * Checks if the table exists.
     * @param userHome User home.
     * @param tableKey Table key. Must be in the same data domain as the user home.
     * @return True when the table exists.
     */
    public boolean checkIfTableExists(UserHome userHome, DomainConnectionTableKey tableKey) {
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(tableKey.getConnectionName(), true);
        if (connectionWrapper == null || connectionWrapper.getSpec() == null) {
            return false;
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(tableKey.getPhysicalTableName(), true);
        return tableWrapper != null && tableWrapper.getSpec() != null;
    }
}
