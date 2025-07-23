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

/**
 * Hierarchy node tree traverse action to take after visiting the current hierarchy node.
 * Decides if traversal should be continued for all child nodes, one child node, stopped or children should not be traversed.
 */
public enum TreeTraverseAction {
    /**
     * Continue visiting tree nodes by traversing (visiting) all child hierarchy nodes.
     */
    TRAVERSE_CHILDREN,

    /**
     * Continue the tree traversal, but do not visit the children of the current node.
     */
    SKIP_CHILDREN,

    /**
     * Traverse only selected nodes and skip all it's sibling nodes.
     */
    TRAVERSE_SELECTED_CHILDREN,

    /**
     * Stop the hierarchy tree traversal, we have found what we are looking for (usually when searching for the first occurrence of a matching node).
     */
    STOP_TRAVERSAL
}
