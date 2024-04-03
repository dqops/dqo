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
package com.dqops.metadata.search;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;

import java.util.Collection;

/**
 * Finder service that traverses the hierarchy node tree and finds requested type of nodes.
 */
public interface HierarchyNodeTreeSearcher {
    /**
     * Search for checks in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param checkSearchFilters Search filters.
     * @return Collection of check nodes that passed the filter.
     */
    Collection<AbstractCheckSpec<?, ?, ?, ?>> findChecks(HierarchyNode startNode, CheckSearchFilters checkSearchFilters);

    /**
     * Search for statistics collectors in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param statisticsCollectorSearchFilters Search filters.
     * @return Collection of profilers nodes that passed the filter.
     */
    Collection<AbstractStatisticsCollectorSpec<?>> findStatisticsCollectors(HierarchyNode startNode, StatisticsCollectorSearchFilters statisticsCollectorSearchFilters);

    /**
     * Search for connection in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param connectionSearchFilters Search filters.
     * @return Collection of connection nodes that passed the filter.
     */
    Collection<ConnectionSpec> findConnections(HierarchyNode startNode, ConnectionSearchFilters connectionSearchFilters);

    /**
     * Search for table specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param tableSearchFilters Search filters.
     * @return Collection of table specs nodes that passed the filter.
     */
    Collection<TableWrapper> findTables(HierarchyNode startNode, TableSearchFilters tableSearchFilters);

    /**
     * Search for column specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param columnSearchFilters Search filters.
     * @return Collection of table specs nodes that passed the filter.
     */
    Collection<ColumnSpec> findColumns(HierarchyNode startNode, ColumnSearchFilters columnSearchFilters);

    /**
     * Search for table specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param sensorDefinitionSearchFilters Search filters.
     * @return Collection of table specs nodes that passed the filter.
     */
    Collection<SensorDefinitionSpec> findSensors(HierarchyNode startNode, SensorDefinitionSearchFilters sensorDefinitionSearchFilters);

    /**
     * Search for table specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param ruleDefinitionSearchFilters Search filters.
     * @return Collection of table specs nodes that passed the filter.
     */
    Collection<RuleDefinitionSpec> findRules(HierarchyNode startNode, RuleDefinitionSearchFilters ruleDefinitionSearchFilters);

    /**
     * Search for monitoring schedules specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param monitoringScheduleSearchFilters Search filters.
     * @return Collection of monitoring schedules specs nodes that passed the filter.
     */
    Collection<MonitoringScheduleSpec> findSchedules(HierarchyNode startNode, MonitoringScheduleSearchFilters monitoringScheduleSearchFilters);

    /**
     * Search for all nodes that have a schedule defined and are not disabled. Schedule roots are nodes that have a schedule, so all nested checks should be executed
     * within that schedule.
     * Possible types of returned nodes are: {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckSpec}
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param scheduleRootsSearchFilters Search filters.
     * @return Collection of nodes of type {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckSpec} that may have a custom schedule defined.
     */
    FoundResultsCollector<ScheduleRootResult> findScheduleRoots(HierarchyNode startNode, ScheduleRootsSearchFilters scheduleRootsSearchFilters);

    /**
     * Traverses a scheduled node (connection, table, column, check) and collects all enabled checks that would be executed as part of this schedule.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.).
     *                  The root node must have a schedule defined and it must match the schedule (cron expression) in the filter.
     *                  Possible start nodes are: {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckSpec}
     * @param scheduledChecksSearchFilters Search filters to find all nested checks that would be included in the schedule.
     * @return Collection of check nodes that passed the filter.
     */
    Collection<AbstractCheckSpec<?,?,?,?>> findScheduledChecks(HierarchyNode startNode, ScheduledChecksSearchFilters scheduledChecksSearchFilters);

    /**
     * Finds all maps of table comparison check maps on the table (for all check types) and on columns (also for all check types).
     * @param startNode Start node.
     * @return Connection of comparison check category maps.
     */
    Collection<AbstractComparisonCheckCategorySpecMap<?>> findComparisonCheckCategoryMaps(HierarchyNode startNode);
}
