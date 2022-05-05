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
