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
public class LabelSetSpecTests extends BaseTest {
    private LabelSetSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new LabelSetSpec();
    }

    @Test
    void isDirty_whenStartDone_thenIsDirtyIsFalse() {
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenClearDirtyDone_thenIsDirtyIsFalse() {
		this.sut.setDirty();
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }
}
