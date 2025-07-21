/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.lineage;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableLineageSourceSpecListTests extends BaseTest {
    private TableLineageSourceSpecList sut;

    @BeforeEach
    void setUp() {
        this.sut = new TableLineageSourceSpecList();
    }

    @Test
    void getByObjectName_whenObjectAddedWithAdd_thenCanBeRetrieved() {
        TableLineageSourceSpec spec = new TableLineageSourceSpec() {{
           setSourceConnection("conn");
           setSourceSchema("schema");
           setSourceTable("tab");
        }};
        this.sut.add(spec);

        Assertions.assertEquals(1, this.sut.size());

        TableLineageSourceSpec foundObject = this.sut.getByObjectName(new TableLineageSource("conn", "schema", "tab"), false);
        Assertions.assertSame(spec, foundObject);
    }
}
