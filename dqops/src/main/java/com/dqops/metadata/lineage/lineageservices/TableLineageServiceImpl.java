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
     * @param referenceTable The start table.
     * @param upstreamLineage True when the data lineage should be calculated for upstream tables.
     * @param downstreamLineage True when the data lineage (the impact radius) should be calculated for downstream tables.
     * @return Table lineage model.
     */
    @Override
    public TableLineageModel buildDataLineageModel(DomainConnectionTableKey referenceTable,
                                                   boolean upstreamLineage,
                                                   boolean downstreamLineage) {
        TableLineageModel tableLineageModel = new TableLineageModel();
        tableLineageModel.setRelativeTable(referenceTable);
        LinkedHashMap<DomainConnectionTableKey, TableCurrentDataQualityStatusModel> upstreamTableStatuses = new LinkedHashMap<>();
        LinkedHashSet<DomainConnectionTableKey> visitedTables = new LinkedHashSet<>();
        LinkedHashSet<DomainConnectionTableKey> onStackTables = new LinkedHashSet<>();

        TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus = this.tableStatusCache.getCurrentTableStatus(
                referenceTable, null);

        if (upstreamLineage) {
            upstreamCombinedQualityStatus = collectUpstreamLineage(
                    referenceTable, tableLineageModel, upstreamTableStatuses, visitedTables, onStackTables);
        }

        if (downstreamLineage) {
            LinkedHashSet<DomainConnectionTableKey> upstreamOnStackTables = new LinkedHashSet<>(visitedTables); // upstream tables that will be collected, if referenced from downstream tables, will be treated as a cycle and ignored
            collectDownstreamLineage(upstreamCombinedQualityStatus, referenceTable, tableLineageModel, visitedTables, upstreamOnStackTables);
        }

        return tableLineageModel;
    }

    /**
     * Collects upstream table lineage using a depth-first method.
     * @param targetTable Current table to include.
     * @param tableLineageModel Target table lineage model to add data flows.
     * @param visitedUpstreamTableStatuses Dictionary of combined table quality statues of already visited tables, in case that other tables also use their statuses (dual paths).
     * @param visitedTables A set of tables that were already visited and should not be visited again to avoid duplication.
     * @param onStackTables Tables that are on the stack. Used to detect cycles in the graph.
     * @return The combined data quality status of all upstream tables.
     */
    public TableCurrentDataQualityStatusModel collectUpstreamLineage(
            DomainConnectionTableKey targetTable,
            TableLineageModel tableLineageModel,
            Map<DomainConnectionTableKey, TableCurrentDataQualityStatusModel> visitedUpstreamTableStatuses,
            LinkedHashSet<DomainConnectionTableKey> visitedTables, 
            LinkedHashSet<DomainConnectionTableKey> onStackTables) {
        TableCurrentDataQualityStatusModel resultStatus = this.tableStatusCache.getCurrentTableStatus(
                targetTable, null);

        if (resultStatus == null) {
            resultStatus = new TableCurrentDataQualityStatusModel() {{
                setTableExist(false);
            }};
        } else {
            resultStatus = resultStatus.deepClone();
        }

        visitedTables.add(targetTable);
        onStackTables.add(targetTable);

        TableLineageCacheEntry tableLineageEntry = this.tableLineageCache.getTableLineageEntry(targetTable);
        if (tableLineageEntry != null) {
            if (tableLineageEntry.getStatus() != TableLineageRefreshStatus.LOADED) {
                resultStatus.setTableExist(false); // some missing information, requires reload
            }

            Set<DomainConnectionTableKey> upstreamSourceTables = tableLineageEntry.getUpstreamSourceTables();
            for (DomainConnectionTableKey upstreamTableKey : upstreamSourceTables) {
                TableCurrentDataQualityStatusModel upstreamOnlyQualityStatus = this.tableStatusCache.getCurrentTableStatus(upstreamTableKey, null);

                if (visitedTables.contains(upstreamTableKey)) {
                    if (!onStackTables.contains(upstreamTableKey)) {
                        TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus = visitedUpstreamTableStatuses.get(upstreamTableKey);
                        TableLineageFlowModel flowFromVisitedTable = new TableLineageFlowModel(upstreamTableKey, targetTable,
                                upstreamOnlyQualityStatus, upstreamCombinedQualityStatus);
                        tableLineageModel.getFlows().add(flowFromVisitedTable);

                        resultStatus.appendResultsFromUpstreamTable(upstreamCombinedQualityStatus);
                    } else {
                        // cycle in the data lineage, ignoring this path
                    }
                } else {
                    TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus = collectUpstreamLineage(
                            upstreamTableKey, tableLineageModel, visitedUpstreamTableStatuses, visitedTables, onStackTables);
                    TableLineageFlowModel flowFromVisitedTable = new TableLineageFlowModel(upstreamTableKey, targetTable,
                            upstreamOnlyQualityStatus, upstreamCombinedQualityStatus);
                    tableLineageModel.getFlows().add(flowFromVisitedTable);
                    resultStatus.appendResultsFromUpstreamTable(upstreamCombinedQualityStatus);
                }
            }
        } else {
            resultStatus.setTableExist(false); // some missing information, requires reload
        }

        onStackTables.remove(targetTable);
        visitedUpstreamTableStatuses.put(targetTable, resultStatus);
        return resultStatus;
    }

    /**
     * Collects downstream table lineage.
     * @param upstreamCombinedQualityStatus The combined data quality status of the current table, that will impact downstream tables.
     * @param sourceTable The start table to traverse.
     * @param tableLineageModel Target data lineage model to append data flows.
     * @param visitedTables Collection of already visited nodes. They will not be processed again.
     * @param onStackTables Collection of nodes that are on the stack. References to these nodes should be ignored. Initially, all upstream nodes are added.
     */
    public void collectDownstreamLineage(TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus,
                                         DomainConnectionTableKey sourceTable,
                                         TableLineageModel tableLineageModel,
                                         LinkedHashSet<DomainConnectionTableKey> visitedTables,
                                         LinkedHashSet<DomainConnectionTableKey> onStackTables) {
        visitedTables.add(sourceTable);
        onStackTables.add(sourceTable);

        TableLineageCacheEntry tableLineageEntry = this.tableLineageCache.getTableLineageEntry(sourceTable);

        if (tableLineageEntry != null) {
            if (tableLineageEntry.getStatus() != TableLineageRefreshStatus.LOADED) {
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
                                downstreamOnlyQualityStatus, downstreamCombinedQualityStatus);
                        tableLineageModel.getFlows().add(flowToVisitedDownstreamTable);

                    } else {
                        // cycle or back reference to the upstream tables, ignoring
                    }
                } else {
                    TableLineageFlowModel flowToDownstreamTable = new TableLineageFlowModel(sourceTable, downstreamTableKey,
                            downstreamOnlyQualityStatus, downstreamCombinedQualityStatus);
                    tableLineageModel.getFlows().add(flowToDownstreamTable);

                    collectDownstreamLineage(downstreamCombinedQualityStatus, sourceTable, tableLineageModel, visitedTables, onStackTables);
                }
            }
        } else {
            upstreamCombinedQualityStatus.setTableExist(false); // some missing information, requires reload
        }

        onStackTables.remove(sourceTable);
    }
}
