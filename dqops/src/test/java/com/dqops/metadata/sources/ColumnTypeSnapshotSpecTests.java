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
