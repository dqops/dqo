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
package com.dqops.metadata.traversal;

import com.dqops.metadata.id.HierarchyNode;
import com.dqops.utils.serialization.YamlDeserializationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Hierarchy tree node traversal helper. Walks the hierarchy tree.
 */
@Component
@Slf4j
public class HierarchyNodeTreeWalkerImpl implements HierarchyNodeTreeWalker {
    /**
     * Traverses a hierarchy node tree starting from the given node.
     * @param node Start node.
     * @param onNodeTraverse Lambda called on each node. The lambda function should return true to traverse children. False or null prevents traversing child nodes.
     * @return true when traversal should be continued, false if after visiting a child node, the code decided to stop the traversal of the whole tree (the right node was found, there is no need to continue traversal)
     */
    public boolean traverseHierarchyNodeTree(HierarchyNode node, VisitHierarchyNodeFunc onNodeTraverse) {
        TreeNodeTraversalResult result = onNodeTraverse.apply(node);
        if (result == null) {
            result = TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        TreeTraverseAction action = result.getAction();
        if (action == TreeTraverseAction.TRAVERSE_CHILDREN) {
            for (HierarchyNode childNode : node.children()) {
                try {
                    if (!traverseHierarchyNodeTree(childNode, onNodeTraverse)) {
                        return false;
                    }
                }
                catch (YamlDeserializationException ex) {
                    // Corrupted YAML file, skipping, the file was logged already
                }
            }
            return true;
        }

        if (action == TreeTraverseAction.SKIP_CHILDREN) {
            return true;
        }

        if (action == TreeTraverseAction.STOP_TRAVERSAL) {
            return false;
        }

        if (action == TreeTraverseAction.TRAVERSE_SELECTED_CHILDREN) {
            List<HierarchyNode> selectedChildren = result.getSelectedChildren();
            for (HierarchyNode selectedChildNode : selectedChildren) {
                if (selectedChildNode == null) {
                    continue;
                }

                if (!traverseHierarchyNodeTree(selectedChildNode, onNodeTraverse)) {
                    return false;
                }
            }
            return true;
        }

        throw new IllegalArgumentException("Unknown traversal action.");
    }
}
