/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
