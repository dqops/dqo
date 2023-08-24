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
import com.dqops.metadata.traversal.HierarchyNodeTreeWalker;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Finder service that traverses the hierarchy node tree and finds requested type of nodes.
 */
@Component
public class HierarchyNodeTreeSearcherImpl implements HierarchyNodeTreeSearcher {
    private final HierarchyNodeTreeWalker hierarchyNodeTreeWalker;

    /**
     * Creates a searcher using a hierarchy tree node traverser.
     * @param hierarchyNodeTreeWalker Tree node traverser.
     */
    @Autowired
    public HierarchyNodeTreeSearcherImpl(HierarchyNodeTreeWalker hierarchyNodeTreeWalker) {
        this.hierarchyNodeTreeWalker = hierarchyNodeTreeWalker;
    }

    /**
     * Search for checks in the tree.
     *
     * @param startNode          Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param checkSearchFilters Search filters.
     * @return Collection of check nodes that passed the filter.
     */
    @Override
    public Collection<AbstractCheckSpec<?,?,?,?>> findChecks(HierarchyNode startNode, CheckSearchFilters checkSearchFilters) {
        CheckSearchFiltersVisitor searchFilterVisitor = checkSearchFilters.createCheckSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = new DataGroupingConfigurationSearcherObject();
        SearchParameterObject searchParameterObject = new SearchParameterObject(matchingNodes, dataGroupingConfigurationSearcherObject, labelsSearcherObject);
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                searchParameterObject));

        return (List<AbstractCheckSpec<?,?,?,?>>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for statistics collectors in the tree.
     *
     * @param startNode             Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param statisticsCollectorSearchFilters Search filters.
     * @return Collection of statistics collector nodes that passed the filter.
     */
    @Override
    public Collection<AbstractStatisticsCollectorSpec<?>> findStatisticsCollectors(HierarchyNode startNode, StatisticsCollectorSearchFilters statisticsCollectorSearchFilters) {
        StatisticsCollectorSearchFiltersVisitor searchFilterVisitor = statisticsCollectorSearchFilters.createProfilerSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = new DataGroupingConfigurationSearcherObject();
        SearchParameterObject searchParameterObject = new SearchParameterObject(matchingNodes, dataGroupingConfigurationSearcherObject, labelsSearcherObject);
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                searchParameterObject));

        return (List<AbstractStatisticsCollectorSpec<?>>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for connection in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param connectionSearchFilters Search filters.
     * @return Collection of connection nodes that passed the filter.
     */
    public Collection<ConnectionSpec> findConnections(HierarchyNode startNode, ConnectionSearchFilters connectionSearchFilters) {
        ConnectionSearchFiltersVisitor searchFilterVisitor = connectionSearchFilters.createSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = new DataGroupingConfigurationSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                new SearchParameterObject(matchingNodes, dataGroupingConfigurationSearcherObject, labelsSearcherObject)));

        return (List<ConnectionSpec>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for table specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param tableSearchFilters Search filters.
     * @return Collection of table specs nodes that passed the filter.
     */
    public Collection<TableWrapper> findTables(HierarchyNode startNode, TableSearchFilters tableSearchFilters) {
        TableSearchFiltersVisitor searchFilterVisitor = tableSearchFilters.createTableSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = new DataGroupingConfigurationSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                new SearchParameterObject(matchingNodes, dataGroupingConfigurationSearcherObject, labelsSearcherObject)));

        return (List<TableWrapper>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for column specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param columnSearchFilters Search filters.
     * @return Collection of table specs nodes that passed the filter.
     */
    public Collection<ColumnSpec> findColumns(HierarchyNode startNode, ColumnSearchFilters columnSearchFilters) {
        ColumnSearchFiltersVisitor searchFilterVisitor = columnSearchFilters.createSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = new DataGroupingConfigurationSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                new SearchParameterObject(matchingNodes, dataGroupingConfigurationSearcherObject, labelsSearcherObject)));

        return (List<ColumnSpec>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for table specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param sensorDefinitionSearchFilters Search filters.
     * @return Collection of table specs nodes that passed the filter.
     */
    public Collection<SensorDefinitionSpec> findSensors(HierarchyNode startNode, SensorDefinitionSearchFilters sensorDefinitionSearchFilters) {
        SensorDefinitionSearchFiltersVisitor searchFilterVisitor = sensorDefinitionSearchFilters.createSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = new DataGroupingConfigurationSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                new SearchParameterObject(matchingNodes, dataGroupingConfigurationSearcherObject, labelsSearcherObject)));

        return (List<SensorDefinitionSpec>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for table specs in the tree.
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param ruleDefinitionSearchFilters Search filters.
     * @return Collection of table specs nodes that passed the filter.
     */
    public Collection<RuleDefinitionSpec> findRules(HierarchyNode startNode, RuleDefinitionSearchFilters ruleDefinitionSearchFilters) {
        RuleDefinitionSearchFiltersVisitor searchFilterVisitor = ruleDefinitionSearchFilters.createSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = new DataGroupingConfigurationSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                new SearchParameterObject(matchingNodes, dataGroupingConfigurationSearcherObject, labelsSearcherObject)));

        return (List<RuleDefinitionSpec>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for monitoring schedules specs in the tree.
     *
     * @param startNode                      Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param monitoringScheduleSearchFilters Search filters.
     * @return Collection of monitoring schedules specs nodes that passed the filter.
     */
    @Override
    public Collection<MonitoringScheduleSpec> findSchedules(HierarchyNode startNode, MonitoringScheduleSearchFilters monitoringScheduleSearchFilters) {
        MonitoringScheduleSearchFiltersVisitor searchFilterVisitor = monitoringScheduleSearchFilters.createSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                new SearchParameterObject(matchingNodes, null, null)));

        return (List<MonitoringScheduleSpec>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for all nodes that have a schedule defined and are not disabled. Schedule roots are nodes that have a schedule, so all nested checks should be executed
     * within that schedule.
     * Possible types of returned nodes are: {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckSpec}
     *
     * @param startNode                  Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param scheduleRootsSearchFilters Search filters.
     * @return Collection of nodes of type {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckSpec} that may have a custom schedule defined.
     */
    @Override
    public FoundResultsCollector<ScheduleRootResult>findScheduleRoots(HierarchyNode startNode, ScheduleRootsSearchFilters scheduleRootsSearchFilters) {
        ScheduleRootsSearchFiltersVisitor searchFilterVisitor = scheduleRootsSearchFilters.createSearchFilterVisitor();
        FoundResultsCollector<ScheduleRootResult> resultsCollector = new FoundResultsCollector<>();
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode,
                node -> node.visit(searchFilterVisitor, resultsCollector));

        return resultsCollector;
    }

    /**
     * Traverses a scheduled node (connection, table, column, check) and collects all enabled checks that would be executed as part of this schedule.
     *
     * @param startNode                    Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.).
     *                                     The root node must have a schedule defined and it must match the schedule (cron expression) in the filter.
     *                                     Possible start nodes are: {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckSpec}
     * @param scheduledChecksSearchFilters Search filters to find all nested checks that would be included in the schedule.
     * @return Collection of check nodes that passed the filter.
     */
    @Override
    public Collection<AbstractCheckSpec<?,?,?,?>> findScheduledChecks(HierarchyNode startNode,
                                                                      ScheduledChecksSearchFilters scheduledChecksSearchFilters) {
        assert startNode instanceof ConnectionSpec || startNode instanceof TableSpec || startNode instanceof AbstractCheckSpec;

        ScheduledChecksSearchFiltersVisitor searchFilterVisitor = scheduledChecksSearchFilters.createSearchFilterVisitor();
        FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> resultsCollector = new FoundResultsCollector<>();
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode,
                node -> node.visit(searchFilterVisitor, resultsCollector));

        return (List<AbstractCheckSpec<?,?,?,?>>)(ArrayList<?>)resultsCollector.getResults();
    }

    /**
     * Finds all maps of table comparison check maps on the table (for all check types) and on columns (also for all check types).
     *
     * @param startNode Start node.
     * @return Connection of comparison check category maps.
     */
    @Override
    public Collection<AbstractComparisonCheckCategorySpecMap<?>> findComparisonCheckCategoryMaps(HierarchyNode startNode) {
        AbstractComparisonCheckCategorySpecMapVisitor visitor = new AbstractComparisonCheckCategorySpecMapVisitor();
        ArrayList<AbstractComparisonCheckCategorySpecMap<?>> foundNodes = new ArrayList<>();
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode,
                node -> node.visit(visitor, foundNodes));
        return foundNodes;
    }
}
