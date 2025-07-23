/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnTypeSnapshotSpecTests extends BaseTest {
    private ColumnTypeSnapshotSpec sut;

    @BeforeEach
    void setUp() {
    	this.sut = new ColumnTypeSnapshotSpec();
    }

    @Test
    void fromType_whenNull_thenReturnsNullType() {
        ColumnTypeSnapshotSpec spec = ColumnTypeSnapshotSpec.fromType(null);
        Assertions.assertNotNull(spec);
        Assertions.assertNull(spec.getColumnType());
    }

    @Test
    void fromType_whenEmptyString_thenReturnsNullType() {
        ColumnTypeSnapshotSpec spec = ColumnTypeSnapshotSpec.fromType("");
        Assertions.assertNotNull(spec);
        Assertions.assertNull(spec.getColumnType());
    }

    @Test
    void fromType_whenSimpleType_thenReturnsColumnTypeNameAsIs() {
        ColumnTypeSnapshotSpec spec = ColumnTypeSnapshotSpec.fromType("INT64");
        Assertions.assertNotNull(spec);
        Assertions.assertEquals("INT64", spec.getColumnType());
        Assertions.assertNull(spec.getLength());
        Assertions.assertNull(spec.getPrecision());
        Assertions.assertNull(spec.getScale());
    }

    @Test
    void fromType_whenTypeWithUnparseableLength_thenReturnsColumnTypeCorrectly() {
        ColumnTypeSnapshotSpec spec = ColumnTypeSnapshotSpec.fromType("VARCHAR(MAX)");
        Assertions.assertNotNull(spec);
        Assertions.assertEquals("VARCHAR", spec.getColumnType());
        Assertions.assertNull(spec.getLength());
        Assertions.assertNull(spec.getPrecision());
        Assertions.assertNull(spec.getScale());
    }

    @Test
    void fromType_whenTypeWithLength_thenReturnsColumnTypeNameAndLength() {
        ColumnTypeSnapshotSpec spec = ColumnTypeSnapshotSpec.fromType("VARCHAR(200)");
        Assertions.assertNotNull(spec);
        Assertions.assertEquals("VARCHAR", spec.getColumnType());
        Assertions.assertEquals(200, spec.getLength());
        Assertions.assertNull(spec.getPrecision());
        Assertions.assertNull(spec.getScale());
    }

    @Test
    void fromType_whenTypeWithPrecisionAndScale_thenReturnsColumnTypeNameAndPrecisionAndScale() {
        ColumnTypeSnapshotSpec spec = ColumnTypeSnapshotSpec.fromType("NUMERIC(20, 5)");
        Assertions.assertNotNull(spec);
        Assertions.assertEquals("NUMERIC", spec.getColumnType());
        Assertions.assertNull(spec.getLength());
        Assertions.assertEquals(20, spec.getPrecision());
        Assertions.assertEquals(5, spec.getScale());
    }
}
