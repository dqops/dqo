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
