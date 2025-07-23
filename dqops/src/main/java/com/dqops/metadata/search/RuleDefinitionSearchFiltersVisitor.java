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

import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import com.google.common.base.Strings;

/**
 * Visitor for {@link RuleDefinitionSearchFilters} that finds the correct nodes.
 */
public class RuleDefinitionSearchFiltersVisitor extends AbstractSearchVisitor<SearchParameterObject> {
    private final RuleDefinitionSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Rule search filters.
     */
    public RuleDefinitionSearchFiltersVisitor(RuleDefinitionSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a list of rules.
     *
     * @param ruleDefinitionList List of rules.
     * @param parameter      Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionList ruleDefinitionList, SearchParameterObject parameter) {
        String ruleNameFilter = this.filters.getRuleName();
        if (Strings.isNullOrEmpty(ruleNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.isSearchPattern(ruleNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        // exact rule name given, let's find it
        RuleDefinitionWrapper ruleDefinitionWrapper = ruleDefinitionList.getByObjectName(ruleNameFilter, true);
        if (ruleDefinitionWrapper == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(ruleDefinitionWrapper);
    }

    /**
     * Accepts a rule definition wrapper (lazy loader).
     *
     * @param ruleDefinitionWrapper Rule definition wrapper.
     * @param parameter         Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionWrapper ruleDefinitionWrapper, SearchParameterObject parameter) {
        String ruleNameFilter = this.filters.getRuleName();
        if(!this.filters.getEnabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (Strings.isNullOrEmpty(ruleNameFilter)) {
            parameter.getNodes().add(ruleDefinitionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (StringPatternComparer.matchSearchPattern(ruleDefinitionWrapper.getRuleName(), ruleNameFilter)) {
            parameter.getNodes().add(ruleDefinitionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a rule definition spec (lazy loader).
     *
     * @param ruleDefinitionSpec Rule definition wrapper.
     * @param parameter         Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionSpec ruleDefinitionSpec, SearchParameterObject parameter) {
        parameter.getNodes().add(ruleDefinitionSpec);
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
