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
