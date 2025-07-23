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

import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;

import java.util.List;

/**
 * Search visitor that will find all check containers of table comparison checks ({@link AbstractComparisonCheckCategorySpecMap}).
 */
public class AbstractComparisonCheckCategorySpecMapVisitor extends AbstractSearchVisitor<List<AbstractComparisonCheckCategorySpecMap<?>>> {
    /**
     * Accepts a map of comparison checks for a named comparison.
     *
     * @param abstractComparisonCheckCategorySpecMap Comparison map with checks.
     * @param parameter                              Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractComparisonCheckCategorySpecMap<?> abstractComparisonCheckCategorySpecMap,
                                          List<AbstractComparisonCheckCategorySpecMap<?>> parameter) {
        parameter.add(abstractComparisonCheckCategorySpecMap);
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
