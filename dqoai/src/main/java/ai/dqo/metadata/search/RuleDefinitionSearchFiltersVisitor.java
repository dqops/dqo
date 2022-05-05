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

import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import com.google.common.base.Strings;

import java.util.List;

/**
 * Visitor for {@link RuleDefinitionSearchFilters} that finds the correct nodes.
 */
public class RuleDefinitionSearchFiltersVisitor extends AbstractSearchVisitor {
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
     * @param parameter      Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionList ruleDefinitionList, List<HierarchyNode> parameter) {
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

        return TreeNodeTraversalResult.traverseChildNode(ruleDefinitionWrapper);
    }

    /**
     * Accepts a rule definition wrapper (lazy loader).
     *
     * @param ruleDefinitionWrapper Rule definition wrapper.
     * @param parameter         Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionWrapper ruleDefinitionWrapper, List<HierarchyNode> parameter) {
        String ruleNameFilter = this.filters.getRuleName();
        if(!this.filters.getEnabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (Strings.isNullOrEmpty(ruleNameFilter)) {
            parameter.add(ruleDefinitionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (StringPatternComparer.matchSearchPattern(ruleDefinitionWrapper.getRuleName(), ruleNameFilter)) {
            parameter.add(ruleDefinitionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a rule definition spec (lazy loader).
     *
     * @param ruleDefinitionSpec Rule definition wrapper.
     * @param parameter         Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionSpec ruleDefinitionSpec, List<HierarchyNode> parameter) {
        parameter.add(ruleDefinitionSpec);
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
