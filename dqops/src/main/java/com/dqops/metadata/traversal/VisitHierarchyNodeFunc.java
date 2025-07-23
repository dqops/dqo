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

import java.util.function.Function;

/**
 * Lambda function that is called on every visited (traversed) hierarchy tree node.
 * The function returns true if child nodes should also be traversed, false if the traversal should not go deeper and should be stopped at the current hierarchy node.
 */
public interface VisitHierarchyNodeFunc extends Function<HierarchyNode, TreeNodeTraversalResult> {
}
