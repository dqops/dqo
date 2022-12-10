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

import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import ai.dqo.profiling.AbstractProfilerCategorySpec;
import ai.dqo.profiling.AbstractProfilerSpec;
import ai.dqo.profiling.AbstractRootProfilerContainerSpec;
import ai.dqo.profiling.ProfilerTarget;
import ai.dqo.profiling.column.ColumnProfilerRootCategoriesSpec;
import ai.dqo.profiling.table.TableProfilerRootCategoriesSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import com.google.common.base.Strings;

import java.util.Set;

/**
 * Visitor for {@link ProfilerSearchFilters} that finds profilers to execute.
 */
public class ProfilerSearchFiltersVisitor extends AbstractSearchVisitor {
    private final ProfilerSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Check search filters.
     */
    public ProfilerSearchFiltersVisitor(ProfilerSearchFilters filters) {
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

        return TreeNodeTraversalResult.traverseSelectedChildNodes(connectionWrapper);
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionWrapper Connection wrapper.
     * @param parameter         Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, SearchParameterObject parameter) {
        String connectionNameFilter = this.filters.getConnectionName();

        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setConnectionLabels(connectionWrapper.getSpec().getLabels());

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

        return TreeNodeTraversalResult.traverseSelectedChildNodes(tableWrapper);
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

        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setTableLabels(tableSpec.getLabels());

        if (tableSpec.isDisabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (enabledFilter != null) {
            if (enabledFilter && tableSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && !tableSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        TableProfilerRootCategoriesSpec profiler = tableSpec.getProfiler();
        if (profiler == null) {
            // the default traversal will not see the profiler, we need to create the default profiler and traverse it
            profiler = new TableProfilerRootCategoriesSpec();
            if (tableSpec.getHierarchyId() != null) {
                profiler.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "profiler"));
            }
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(profiler, tableSpec.getColumns());
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

        return TreeNodeTraversalResult.traverseSelectedChildNodes(columnSpec);
    }

    /**
     * Accepts a column specification.
     *
     * @param columnSpec Column specification.
     * @param parameter  Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpec columnSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setColumnLabels(columnSpec.getLabels());

        if (enabledFilter != null) {
            if (enabledFilter && columnSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && !columnSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (columnSpec.isDisabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        String columnNameFilter = this.filters.getColumnName();
        if (!Strings.isNullOrEmpty(columnNameFilter)) {
            String columnName = columnSpec.getHierarchyId().getLast().toString();
            if (!StringPatternComparer.matchSearchPattern(columnName, columnNameFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        ColumnProfilerRootCategoriesSpec profiler = columnSpec.getProfiler();
        if (profiler == null) {
            // the default traversal will not see the profiler, we need to create the default profiler and traverse it
            profiler = new ColumnProfilerRootCategoriesSpec();
            if (columnSpec.getHierarchyId() != null) {
                profiler.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "profiler"));
            }
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(profiler);
    }

    /**
     * Accepts any profiler specification.
     *
     * @param abstractProfilerSpec Data profiler specification (any).
     * @param parameter     Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractProfilerSpec<?> abstractProfilerSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        AbstractSensorParametersSpec sensorParameters = abstractProfilerSpec.getParameters();
        boolean profilerEnabled = !abstractProfilerSpec.isDisabled();
        if (enabledFilter != null) {
            if (enabledFilter && !profilerEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && profilerEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (this.filters.getLabels() != null && this.filters.getLabels().length > 0) {
            LabelSetSpec overriddenLabels = new LabelSetSpec();
            LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
            if (labelsSearcherObject.getColumnLabels() != null) {
                overriddenLabels.addAll(labelsSearcherObject.getColumnLabels());
            }

            if (labelsSearcherObject.getTableLabels() != null) {
                overriddenLabels.addAll(labelsSearcherObject.getTableLabels());
            }

            if (labelsSearcherObject.getConnectionLabels() != null) {
                overriddenLabels.addAll(labelsSearcherObject.getConnectionLabels());
            }
            if (!LabelsSearchMatcher.hasAllLabels(this.filters.getLabels(), overriddenLabels)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (this.filters.getTags() != null && this.filters.getTags().length > 0) {
            DataStreamSearcherObject dataStreamSearcherObject = parameter.getDataStreamSearcherObject();
            DataStreamMappingSpec selectedDataStream = dataStreamSearcherObject.getTableDataStreams().getFirstDataStreamMapping();

            if (!DataStreamsTagsSearchMatcher.matchAllRequiredTags(this.filters.getTags(), selectedDataStream)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (!profilerEnabled) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        String profilerNameFilter = this.filters.getProfilerName();
        if (!Strings.isNullOrEmpty(profilerNameFilter)) {
            String checkName = abstractProfilerSpec.getHierarchyId().getLast().toString();
            if (!StringPatternComparer.matchSearchPattern(checkName, profilerNameFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        String sensorNameFilter = this.filters.getSensorName();
        if (!Strings.isNullOrEmpty(sensorNameFilter)) {
            if (sensorParameters == null) {
                return TreeNodeTraversalResult.SKIP_CHILDREN; // sensor is not configured (has no parameters, we don't know what to run)
            }
            String sensorDefinitionName = sensorParameters.getSensorDefinitionName();
            String sensorEntryName = sensorParameters.getHierarchyId().getLast().toString();
            if (!StringPatternComparer.matchSearchPattern(sensorDefinitionName, sensorNameFilter) &&
                    !StringPatternComparer.matchSearchPattern(sensorEntryName, sensorNameFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        Set<HierarchyId> profilerHierarchyIds = this.filters.getProfilerHierarchyIds();
        if (profilerHierarchyIds != null) {
            if (!profilerHierarchyIds.contains(abstractProfilerSpec.getHierarchyId())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        parameter.getNodes().add(abstractProfilerSpec);

        return TreeNodeTraversalResult.SKIP_CHILDREN; // no need to search any deeper, we have found what we were looking for
    }

    /**
     * Accepts a container of categories of data profilers.
     *
     * @param rootProfilerContainerSpec Container of data profilers that has nested categories (and categories contain profilers).
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRootProfilerContainerSpec rootProfilerContainerSpec, SearchParameterObject parameter) {
        ProfilerTarget targetFilter = this.filters.getTarget();
        if (targetFilter != null) {
            if (rootProfilerContainerSpec.getTarget() != targetFilter) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        return super.accept(rootProfilerContainerSpec, parameter);
    }

    /**
     * Accepts a container of data profilers for a single category.
     *
     * @param profilerCategorySpec      Container of data profilers for a single category.
     * @param parameter                 Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractProfilerCategorySpec profilerCategorySpec, SearchParameterObject parameter) {
        String profilerCategoryFilter = this.filters.getProfilerCategory();
        if (!Strings.isNullOrEmpty(profilerCategoryFilter)) {
            String categoryName = profilerCategorySpec.getHierarchyId().getLast().toString();
            if (!StringPatternComparer.matchSearchPattern(categoryName, profilerCategoryFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        return super.accept(profilerCategorySpec, parameter);
    }
}
