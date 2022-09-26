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

import ai.dqo.metadata.groupings.DimensionsConfigurationSpec;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import com.google.common.base.Strings;

import java.util.List;

/**
 * Visitor for {@link ColumnSearchFilters} that finds the correct nodes.
 */
public class ColumnSearchFiltersVisitor extends AbstractSearchVisitor {
    private final ColumnSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Column search filters.
     */
    public ColumnSearchFiltersVisitor(ColumnSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a list of connections.
     *
     * @param connectionList List of connections.
     * @param parameter      Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionList connectionList, SearchParameterObject parameter) {
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
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, SearchParameterObject parameter) {
        String connectionNameFilter = this.filters.getConnectionName();

        DimensionSearcherObject dimensionSearcherObject = parameter.getDimensionSearcherObject();
        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();

        labelsSearcherObject.setConnectionLabels(connectionWrapper.getSpec().getLabels());
        dimensionSearcherObject.setConnectionDimension(connectionWrapper.getSpec().getDefaultDimensions());
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
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableList tableList, SearchParameterObject parameter) {
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
     * @param parameter    Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableWrapper tableWrapper, SearchParameterObject parameter) {
        String schemaTableName = this.filters.getSchemaTableName();

        DimensionSearcherObject dimensionSearcherObject = parameter.getDimensionSearcherObject();
        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();

        labelsSearcherObject.setTableLabels(tableWrapper.getSpec().getLabels());
        dimensionSearcherObject.setTableDimension(tableWrapper.getSpec().getDimensions());
        if (Strings.isNullOrEmpty(schemaTableName)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(schemaTableName);
        if (physicalTableName.isSearchPattern()) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        if (tableWrapper.getPhysicalTableName().matchPattern(physicalTableName)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a table specification.
     *
     * @param tableSpec Table specification.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableSpec tableSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        DimensionSearcherObject dimensionSearcherObject = parameter.getDimensionSearcherObject();
        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();

        labelsSearcherObject.setTableLabels(tableSpec.getLabels());
        dimensionSearcherObject.setTableDimension(tableSpec.getDimensions());
        if (enabledFilter != null) {
            if (enabledFilter && tableSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && !tableSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }

            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a column collection (map).
     *
     * @param columnSpecMap Column collection.
     * @param parameter     Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpecMap columnSpecMap, SearchParameterObject parameter) {
        String columnNameFilter = this.filters.getColumnName();
        if (Strings.isNullOrEmpty(columnNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.isSearchPattern(columnNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        // exact column name given, let's find it
        ColumnSpec columnSpec = columnSpecMap.get(columnNameFilter);
        if (columnSpec == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseChildNode(columnSpec);
    }

    /**
     * Accepts a column specification.
     *
     * @param columnSpec Column specification.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpec columnSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        DimensionSearcherObject dimensionSearcherObject = parameter.getDimensionSearcherObject();
        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();

        labelsSearcherObject.setColumnLabels(columnSpec.getLabels());
        dimensionSearcherObject.setColumnDimension(columnSpec.getDimensionsOverride());

        if (enabledFilter != null) {
            if (enabledFilter && columnSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && !columnSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        labelsSearcherObject.setTableLabels(columnSpec.getLabels());
        dimensionSearcherObject.setTableDimension(columnSpec.getDimensionsOverride());

        DimensionsConfigurationSpec overridenDimension = dimensionSearcherObject.getColumnDimension() != null
                ? dimensionSearcherObject.getColumnDimension()
                : dimensionSearcherObject.getTableDimension() != null
                ? dimensionSearcherObject.getTableDimension()
                : dimensionSearcherObject.getConnectionDimension();
        LabelSetSpec overridenLabels = new LabelSetSpec();

        if (labelsSearcherObject.getColumnLabels() != null) {
            overridenLabels.addAll(labelsSearcherObject.getColumnLabels());
        }

        if (labelsSearcherObject.getTableLabels() != null) {
            overridenLabels.addAll(labelsSearcherObject.getTableLabels());
        }

        if (labelsSearcherObject.getConnectionLabels() != null) {
            overridenLabels.addAll(labelsSearcherObject.getConnectionLabels());
        }

        if (!DimensionSearchMatcher.matchAllColumnDimensions(this.filters, overridenDimension)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (!LabelsSearchMatcher.matchColumnLabels(this.filters, overridenLabels)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        String columnNameFilter = this.filters.getColumnName();
        if (Strings.isNullOrEmpty(columnNameFilter)) {
            parameter.getNodes().add(columnSpec);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        String columnName = columnSpec.getHierarchyId().getLast().toString();
        if (StringPatternComparer.matchSearchPattern(columnName, columnNameFilter)) {
            parameter.getNodes().add(columnSpec);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
