/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.traversal;

import com.dqops.metadata.id.HierarchyNode;

/**
 * Hierarchy tree node traversal helper. Walks the hierarchy tree.
 */
public interface HierarchyNodeTreeWalker {
    /**
     * Traverses a hierarchy node tree starting from the given node.
     * @param node Start node.
     * @param onNodeTraverse Lambda called on each node. The lambda function should return true to traverse children. False or null prevents traversing child nodes.
     * @return true when traversal should be continued, false if after visiting a child node, the code decided to stop the traversal of the whole tree (the right node was found, there is no need to continue traversal)
     */
    boolean traverseHierarchyNodeTree(HierarchyNode node, VisitHierarchyNodeFunc onNodeTraverse);
}
