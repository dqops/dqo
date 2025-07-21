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

import java.util.List;

/**
 * Object returned as a result of a tree traversal. Decides how to continue the tree traversal, but it can also point
 * a single child node to visit.
 */
public final class TreeNodeTraversalResult {
    private final TreeTraverseAction action;
    private final List<HierarchyNode> selectedChildren;

    /**
     * Continue visiting tree nodes by traversing (visiting) all child hierarchy nodes.
     */
    public static final TreeNodeTraversalResult TRAVERSE_CHILDREN = new TreeNodeTraversalResult(TreeTraverseAction.TRAVERSE_CHILDREN);

    /**
     * Continue the tree traversal, but do not visit the children of the current node.
     */
    public static final TreeNodeTraversalResult SKIP_CHILDREN = new TreeNodeTraversalResult(TreeTraverseAction.SKIP_CHILDREN);

    /**
     * Stop the hierarchy tree traversal, we have found what we are looking for (usually when searching for the first occurrence of a matching node).
     */
    public static final TreeNodeTraversalResult STOP_TRAVERSAL = new TreeNodeTraversalResult(TreeTraverseAction.STOP_TRAVERSAL);

    /**
     * Creates a tree traversal result with just an action.
     * @param action Action to perform.
     */
    private TreeNodeTraversalResult(TreeTraverseAction action) {
        this.action = action;
		this.selectedChildren = null;
    }

    /**
     * Tree traversal result to visit just one child node.
     * @param action Action to remember, should be TRAVERSE_ONE_CHILD.
     * @param selectedChild Selected child node.
     */
    private TreeNodeTraversalResult(TreeTraverseAction action, HierarchyNode... selectedChild) {
        assert selectedChild != null && action == TreeTraverseAction.TRAVERSE_SELECTED_CHILDREN;
        this.action = action;
        this.selectedChildren = List.of(selectedChild);
    }

    /**
     * Creates a tree traversal result that points a single child node that should be traversed.
     * @param childNodes Selected child node.
     * @return Tree traversal result with a single child node.
     */
    public static TreeNodeTraversalResult traverseSelectedChildNodes(HierarchyNode... childNodes) {
        return new TreeNodeTraversalResult(TreeTraverseAction.TRAVERSE_SELECTED_CHILDREN, childNodes);
    }

    /**
     * Tree traversal action.
     * @return Action.
     */
    public TreeTraverseAction getAction() {
        return action;
    }

    /**
     * Selected child node to traverse when a TRAVERSE_SELECTED_CHILDREN was selected.
     * @return Child node to jump to, skipping sibling nodes.
     */
    public List<HierarchyNode> getSelectedChildren() {
        return selectedChildren;
    }
}
