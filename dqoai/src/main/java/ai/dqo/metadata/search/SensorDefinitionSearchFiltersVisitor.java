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

import ai.dqo.metadata.definitions.sensors.SensorDefinitionList;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import com.google.common.base.Strings;

/**
 * Visitor for {@link SensorDefinitionSearchFilters} that finds the correct nodes.
 */
public class SensorDefinitionSearchFiltersVisitor extends AbstractSearchVisitor<SearchParameterObject> {
    private final SensorDefinitionSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Rule search filters.
     */
    public SensorDefinitionSearchFiltersVisitor(SensorDefinitionSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a list of rules.
     *
     * @param sensorDefinitionList List of sensors.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SensorDefinitionList sensorDefinitionList, SearchParameterObject parameter) {
        String sensorNameFilter = this.filters.getSensorName();
        if (Strings.isNullOrEmpty(sensorNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.isSearchPattern(sensorNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        // exact sensor name given, let's find it
        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDefinitionList.getByObjectName(sensorNameFilter, true);
        if (sensorDefinitionWrapper == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(sensorDefinitionWrapper);
    }

    /**
     * Accepts a sensor definition wrapper (lazy loader).
     *
     * @param sensorDefinitionWrapper Rule definition wrapper.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SensorDefinitionWrapper sensorDefinitionWrapper, SearchParameterObject parameter) {
        String sensorNameFilter = this.filters.getSensorName();
        if(!this.filters.getEnabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (Strings.isNullOrEmpty(sensorNameFilter)) {
            parameter.getNodes().add(sensorDefinitionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (StringPatternComparer.matchSearchPattern(sensorDefinitionWrapper.getName(), sensorNameFilter)) {
            parameter.getNodes().add(sensorDefinitionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a sensor definition wrapper (lazy loader).
     *
     * @param sensorDefinitionSpec Rule definition wrapper.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SensorDefinitionSpec sensorDefinitionSpec, SearchParameterObject parameter) {
        parameter.getNodes().add(sensorDefinitionSpec);
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
