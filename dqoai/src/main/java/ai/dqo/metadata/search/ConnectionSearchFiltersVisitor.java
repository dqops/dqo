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

import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import com.google.common.base.Strings;

/**
 * Visitor for {@link ConnectionSearchFilters} that finds the correct nodes.
 */
public class ConnectionSearchFiltersVisitor extends AbstractSearchVisitor {
    private final ConnectionSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Connection search filters.
     */
    public ConnectionSearchFiltersVisitor(ConnectionSearchFilters filters) {
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
     * @param parameter         Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, SearchParameterObject parameter) {
        String connectionNameFilter = this.filters.getConnectionName();
        parameter.getLabelsSearcherObject().setConnectionLabels(connectionWrapper.getSpec().getLabels());
        parameter.getDimensionSearcherObject().setConnectionDataStreams(connectionWrapper.getSpec().getDefaultDataStreams());
        if (!DataStreamsMappingSearchMatcher.matchAllConnectionDataStreamsMapping(this.filters, connectionWrapper.getSpec().getDefaultDataStreams())) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (!LabelsSearchMatcher.matchConnectionLabels(this.filters, connectionWrapper.getSpec().getLabels())) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            parameter.getNodes().add(connectionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;        }

        if (StringPatternComparer.matchSearchPattern(connectionWrapper.getName(), connectionNameFilter)) {
            parameter.getNodes().add(connectionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionSpec Connection wrapper.
     * @param parameter         Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionSpec connectionSpec, SearchParameterObject parameter) {
        String connectionNameFilter = this.filters.getConnectionName();
        parameter.getLabelsSearcherObject().setConnectionLabels(connectionSpec.getLabels());
        parameter.getDimensionSearcherObject().setConnectionDataStreams(connectionSpec.getDefaultDataStreams());
        if (!DataStreamsMappingSearchMatcher.matchAllConnectionDataStreamsMapping(this.filters, connectionSpec.getDefaultDataStreams())) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (!LabelsSearchMatcher.matchConnectionLabels(this.filters, connectionSpec.getLabels())) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if(!this.filters.getEnabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (!DataStreamsMappingSearchMatcher.matchAllConnectionDataStreamsMapping(this.filters, connectionSpec.getDefaultDataStreams())) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (!LabelsSearchMatcher.matchConnectionLabels(this.filters, connectionSpec.getLabels())) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            parameter.getNodes().add(connectionSpec);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (StringPatternComparer.matchSearchPattern(connectionSpec.getConnectionName(), connectionNameFilter)) {
            parameter.getNodes().add(connectionSpec);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (connectionNameFilter.equals(connectionSpec.getConnectionName())) {
            parameter.getNodes().add(connectionSpec);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
