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

import java.util.function.Function;

/**
 * Lambda function that is called on every visited (traversed) hierarchy tree node.
 * The function returns true if child nodes should also be traversed, false if the traversal should not go deeper and should be stopped at the current hierarchy node.
 */
public interface VisitHierarchyNodeFunc extends Function<HierarchyNode, TreeNodeTraversalResult> {
}
