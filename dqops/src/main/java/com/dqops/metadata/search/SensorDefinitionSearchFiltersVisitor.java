/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.search;

import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
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
