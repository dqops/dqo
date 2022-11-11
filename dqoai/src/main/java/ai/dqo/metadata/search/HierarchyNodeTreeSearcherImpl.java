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
package ai.dqo.metadata.search;

import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
     * @param startNode Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param checkSearchFilters Search filters.
     * @return Collection of check nodes that passed the filter.
     */
    public Collection<AbstractCheckDeprecatedSpec> findLegacyChecks(HierarchyNode startNode, CheckSearchFilters checkSearchFilters) {
        LegacyCheckSearchFiltersVisitor searchFilterVisitor = checkSearchFilters.createLegacyCheckSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataStreamSearcherObject dataStreamSearcherObject = new DataStreamSearcherObject();
        SearchParameterObject searchParameterObject = new SearchParameterObject(matchingNodes, dataStreamSearcherObject, labelsSearcherObject);
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, searchParameterObject));

        return (List<AbstractCheckDeprecatedSpec>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for checks in the tree.
     *
     * @param startNode          Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param checkSearchFilters Search filters.
     * @return Collection of check nodes that passed the filter.
     */
    @Override
    public Collection<AbstractCheckSpec> findChecks(HierarchyNode startNode, CheckSearchFilters checkSearchFilters) {
        CheckSearchFiltersVisitor searchFilterVisitor = checkSearchFilters.createCheckSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        LabelsSearcherObject labelsSearcherObject = new LabelsSearcherObject();
        DataStreamSearcherObject dataStreamSearcherObject = new DataStreamSearcherObject();
        SearchParameterObject searchParameterObject = new SearchParameterObject(matchingNodes, dataStreamSearcherObject, labelsSearcherObject);
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor,
                searchParameterObject));

        return (List<AbstractCheckSpec>)(ArrayList<?>)matchingNodes;
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
        DataStreamSearcherObject dataStreamSearcherObject = new DataStreamSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, new SearchParameterObject(matchingNodes, dataStreamSearcherObject, labelsSearcherObject)));

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
        DataStreamSearcherObject dataStreamSearcherObject = new DataStreamSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, new SearchParameterObject(matchingNodes, dataStreamSearcherObject, labelsSearcherObject)));

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
        DataStreamSearcherObject dataStreamSearcherObject = new DataStreamSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, new SearchParameterObject(matchingNodes, dataStreamSearcherObject, labelsSearcherObject)));

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
        DataStreamSearcherObject dataStreamSearcherObject = new DataStreamSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, new SearchParameterObject(matchingNodes, dataStreamSearcherObject, labelsSearcherObject)));

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
        DataStreamSearcherObject dataStreamSearcherObject = new DataStreamSearcherObject();
		this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, new SearchParameterObject(matchingNodes, dataStreamSearcherObject, labelsSearcherObject)));

        return (List<RuleDefinitionSpec>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for recurring schedules specs in the tree.
     *
     * @param startNode                      Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param recurringScheduleSearchFilters Search filters.
     * @return Collection of recurring schedules specs nodes that passed the filter.
     */
    @Override
    public Collection<RecurringScheduleSpec> findSchedules(HierarchyNode startNode, RecurringScheduleSearchFilters recurringScheduleSearchFilters) {
        RecurringScheduleSearchFiltersVisitor searchFilterVisitor = recurringScheduleSearchFilters.createSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, new SearchParameterObject(matchingNodes, null, null)));

        return (List<RecurringScheduleSpec>)(ArrayList<?>)matchingNodes;
    }

    /**
     * Search for all nodes that have a schedule defined and are not disabled. Schedule roots are nodes that have a schedule, so all nested checks should be executed
     * within that schedule.
     * Possible types of returned nodes are: {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckDeprecatedSpec}
     *
     * @param startNode                  Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.)
     * @param scheduleRootsSearchFilters Search filters.
     * @return Collection of nodes of type {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckDeprecatedSpec} that may have a custom schedule defined.
     */
    @Override
    public Collection<HierarchyNode> findScheduleRoots(HierarchyNode startNode, ScheduleRootsSearchFilters scheduleRootsSearchFilters) {
        ScheduleRootsSearchFiltersVisitor searchFilterVisitor = scheduleRootsSearchFilters.createSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, new SearchParameterObject(matchingNodes, null, null)));

        return matchingNodes;
    }

    /**
     * Traverses a scheduled node (connection, table, column, check) and collects all enabled checks that would be executed as part of this schedule.
     *
     * @param startNode                    Start node to begin search. It could be the user home root or any other nested node (ConnectionSpec, TableSpec, etc.).
     *                                     The root node must have a schedule defined and it must match the schedule (cron expression) in the filter.
     *                                     Possible start nodes are: {@link ConnectionSpec}, {@link TableSpec}, {@link ColumnSpec} or {@link AbstractCheckDeprecatedSpec}
     * @param scheduledChecksSearchFilters Search filters to find all nested checks that would be included in the schedule.
     * @return Collection of check nodes that passed the filter.
     */
    @Override
    public Collection<AbstractCheckDeprecatedSpec> findScheduledChecks(HierarchyNode startNode, ScheduledChecksSearchFilters scheduledChecksSearchFilters) {
        if (startNode instanceof ConnectionSpec) {
            ConnectionSpec connectionSpec = (ConnectionSpec) startNode;
            assert connectionSpec.getSchedule() != null &&
                    Objects.equals(connectionSpec.getSchedule(), scheduledChecksSearchFilters.getSchedule());
        }
        else if (startNode instanceof TableSpec) {
            TableSpec tableSpec = (TableSpec) startNode;
            assert tableSpec.getScheduleOverride() != null &&
                    Objects.equals(tableSpec.getScheduleOverride(), scheduledChecksSearchFilters.getSchedule());
        }
        else if (startNode instanceof ColumnSpec) {
            ColumnSpec columnSpec = (ColumnSpec) startNode;
            assert columnSpec.getScheduleOverride() != null &&
                    Objects.equals(columnSpec.getScheduleOverride(), scheduledChecksSearchFilters.getSchedule());
        }
        else if (startNode instanceof AbstractCheckDeprecatedSpec) {
            AbstractCheckDeprecatedSpec checkSpec = (AbstractCheckDeprecatedSpec) startNode;
            assert checkSpec.getScheduleOverride() != null &&
                    Objects.equals(checkSpec.getScheduleOverride(), scheduledChecksSearchFilters.getSchedule());
        }
        else {
            throw new IllegalArgumentException("startNode");
        }

        ScheduledChecksSearchFiltersVisitor searchFilterVisitor = scheduledChecksSearchFilters.createSearchFilterVisitor();
        ArrayList<HierarchyNode> matchingNodes = new ArrayList<>();
        this.hierarchyNodeTreeWalker.traverseHierarchyNodeTree(startNode, node -> node.visit(searchFilterVisitor, new SearchParameterObject(matchingNodes, null, null)));

        return (List<AbstractCheckDeprecatedSpec>)(ArrayList<?>)matchingNodes;
    }
}
