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
package ai.dqo.metadata.traversal;

import ai.dqo.metadata.id.HierarchyNode;

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
