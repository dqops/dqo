/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.id;

import com.dqops.BaseTest;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TimestampColumnsSpec;
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
				put("timestamp_columns", t -> t.getTimestampColumns());
            }
        };
    }

    @Test
    void getFieldGetter_whenCalledOnExistingField_thenReturnsGetter() {
        TableSpec tableSpec = new TableSpec();
        TimestampColumnsSpec timestampColumns = new TimestampColumnsSpec();
        tableSpec.setTimestampColumns(timestampColumns);

        GetHierarchyChildNodeFunc<HierarchyNode> fieldGetter = this.sut.getFieldGetter("timestamp_columns");
        Assertions.assertNotNull(fieldGetter);
        Assertions.assertSame(timestampColumns, fieldGetter.apply(tableSpec));
    }

    @Test
    void propagateHierarchyIdToChildren_whenHierarchyGiven_thenPropagatesToFieldsUsingTheirFieldPaths() {
        HierarchyId parentHierarchyId = new HierarchyId("parent");
        TableSpec tableSpec = new TableSpec();
        TimestampColumnsSpec timestampColumns = new TimestampColumnsSpec();
        tableSpec.setTimestampColumns(timestampColumns);

		this.sut.propagateHierarchyIdToChildren(tableSpec, parentHierarchyId);
        HierarchyId childHierarchyId = timestampColumns.getHierarchyId();
        Assertions.assertNotNull(childHierarchyId);
        Assertions.assertTrue(parentHierarchyId.isMyDescendant(childHierarchyId));
        Assertions.assertEquals(2, childHierarchyId.size());
        Assertions.assertEquals("timestamp_columns", childHierarchyId.getLast());
    }
}
