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
import com.dqops.metadata.labels.LabelSetSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnSpecTests extends BaseTest {
    private ColumnSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new ColumnSpec();
    }

    @Test
    void isDirty_whenDisableSet_thenIsDirtyIsTrue() {
		this.sut.setDisabled(true);
        Assertions.assertTrue(this.sut.isDisabled());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameDisableBooleanAsCurrentSet_thenIsDirtyIsFalse() {
		this.sut.setDisabled(true);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setDisabled(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenLabelsSetSpecSet_thenIsDirtyIsTrue() {
        LabelSetSpec labelSetSpec = new LabelSetSpec();
		this.sut.setLabels(labelSetSpec);
        Assertions.assertEquals(this.sut.getLabels(), labelSetSpec);
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameLabelsSetSpecObjectAsCurrentSet_thenIsDirtyIsFalse() {
        LabelSetSpec labelSetSpec = new LabelSetSpec();
		this.sut.setLabels(labelSetSpec);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setLabels(labelSetSpec);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenColumnTypeSnapshotSpecSet_thenIsDirtyIsTrue() {
        ColumnTypeSnapshotSpec columnTypeSnapshotSpec = new ColumnTypeSnapshotSpec();
		this.sut.setTypeSnapshot(columnTypeSnapshotSpec);
        Assertions.assertEquals(this.sut.getTypeSnapshot(), columnTypeSnapshotSpec);
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameColumnTypeSnapshotSpecAsCurrentSet_thenIsDirtyIsFalse() {
        ColumnTypeSnapshotSpec columnTypeSnapshotSpec = new ColumnTypeSnapshotSpec();
		this.sut.setTypeSnapshot(columnTypeSnapshotSpec);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setTypeSnapshot(columnTypeSnapshotSpec);
        Assertions.assertFalse(this.sut.isDirty());
    }
}
