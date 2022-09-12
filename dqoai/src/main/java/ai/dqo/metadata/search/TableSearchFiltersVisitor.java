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

import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import com.google.common.base.Strings;

import java.util.List;
import java.util.Objects;

/**
 * Visitor for {@link TableSearchFilters} that finds the correct nodes.
 */
public class TableSearchFiltersVisitor extends AbstractSearchVisitor {
    private final TableSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Table search filters.
     */
    public TableSearchFiltersVisitor(TableSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a list of connections.
     *
     * @param connectionList List of connections.
     * @param parameter      Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionList connectionList, List<HierarchyNode> parameter) {
        String connectionNameFilter = this.filters.getConnectionName();
        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.isSearchPattern(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        // exact connection name given, let's find it
        ConnectionWrapper connectionWrapper = connectionList.getByObjectName(connectionNameFilter, true);
        if (connectionWrapper == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseChildNode(connectionWrapper);
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionWrapper Connection wrapper.
     * @param parameter         Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, List<HierarchyNode> parameter) {
        String connectionNameFilter = this.filters.getConnectionName();
        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.matchSearchPattern(connectionWrapper.getName(), connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a collection of tables inside a connection.
     *
     * @param tableList Table list.
     * @param parameter Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableList tableList, List<HierarchyNode> parameter) {
        String schemaTableName = this.filters.getSchemaTableName();
        if (Strings.isNullOrEmpty(schemaTableName)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(schemaTableName);
        if (physicalTableName.isSearchPattern()) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        TableWrapper tableWrapper = tableList.getByObjectName(physicalTableName, true);
        if (tableWrapper == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseChildNode(tableWrapper);
    }

    /**
     * Accepts a table wrapper (lazy loader).
     *
     * @param tableWrapper Table wrapper.
     * @param parameter    Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableWrapper tableWrapper, List<HierarchyNode> parameter) {
        String schemaTableName = this.filters.getSchemaTableName();

        if (!Strings.isNullOrEmpty(schemaTableName)) {
            PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(schemaTableName);

            if (!tableWrapper.getPhysicalTableName().matchPattern(physicalTableName)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }
        else {
            parameter.add(tableWrapper);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        Boolean enabledFilter = this.filters.getEnabled();
        if (enabledFilter != null) {
            if (enabledFilter && tableSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && !tableSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (tableSpec.isDisabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        parameter.add(tableWrapper);

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
