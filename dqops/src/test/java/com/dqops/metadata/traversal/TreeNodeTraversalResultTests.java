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

import com.dqops.BaseTest;
import com.dqops.metadata.sources.TableSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TreeNodeTraversalResultTests extends BaseTest {
    @Test
    void traverseChildNode_whenCalled_thenCreatesResultWithNavigationToChild() {
        TableSpec node = new TableSpec();
        TreeNodeTraversalResult result = TreeNodeTraversalResult.traverseSelectedChildNodes(node);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TreeTraverseAction.TRAVERSE_SELECTED_CHILDREN, result.getAction());
        Assertions.assertTrue(result.getSelectedChildren().contains(node));
    }
}
