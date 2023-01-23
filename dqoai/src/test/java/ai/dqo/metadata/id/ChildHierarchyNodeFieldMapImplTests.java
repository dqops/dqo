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
package ai.dqo.metadata.id;

import ai.dqo.BaseTest;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableTargetSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChildHierarchyNodeFieldMapImplTests extends BaseTest {
    private ChildHierarchyNodeFieldMapImpl<TableSpec> sut;

    @BeforeEach
    void setUp() {
		this.sut = new ChildHierarchyNodeFieldMapImpl<TableSpec>(ChildHierarchyNodeFieldMap.empty()) {
            {
				put("target", t -> t.getTarget());
            }
        };
    }

    @Test
    void getFieldGetter_whenCalledOnExistingField_thenReturnsGetter() {
        TableSpec tableSpec = new TableSpec();
        TableTargetSpec target = new TableTargetSpec();
        tableSpec.setTarget(target);

        GetHierarchyChildNodeFunc<HierarchyNode> fieldGetter = this.sut.getFieldGetter("target");
        Assertions.assertNotNull(fieldGetter);
        Assertions.assertSame(target, fieldGetter.apply(tableSpec));
    }

    @Test
    void propagateHierarchyIdToChildren_whenHierarchyGiven_thenPropagatesToFieldsUsingTheirFieldPaths() {
        HierarchyId parentHierarchyId = new HierarchyId("parent");
        TableSpec tableSpec = new TableSpec();
        TableTargetSpec target = new TableTargetSpec();
        tableSpec.setTarget(target);

		this.sut.propagateHierarchyIdToChildren(tableSpec, parentHierarchyId);
        HierarchyId childHierarchyId = target.getHierarchyId();
        Assertions.assertNotNull(childHierarchyId);
        Assertions.assertTrue(parentHierarchyId.isMyDescendant(childHierarchyId));
        Assertions.assertEquals(2, childHierarchyId.size());
        Assertions.assertEquals("target", childHierarchyId.getLast());
    }
}
