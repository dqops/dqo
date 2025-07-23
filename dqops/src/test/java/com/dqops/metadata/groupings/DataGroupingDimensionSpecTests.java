/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.groupings;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataGroupingDimensionSpecTests extends BaseTest {
    private DataGroupingDimensionSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new DataGroupingDimensionSpec();
    }

    @Test
    void getSource_whenNewObject_thenHasDefaultValueTag() {
        Assertions.assertNotNull(this.sut.getSource());
        Assertions.assertEquals(DataGroupingDimensionSource.tag, this.sut.getSource());
    }

    @Test
    void isDefault_whenNoValuesAssigned_thenReturnsFalseBecauseSourceIsAssignedValueToForceSerialization() {
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenSourceConfigured_thenReturnsFalse() {
        this.sut.setSource(DataGroupingDimensionSource.tag);
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenStaticValueConfigured_thenReturnsFalse() {
        this.sut.setTag("abc");
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenColumnConfigured_thenReturnsFalse() {
        this.sut.setColumn("country");
        Assertions.assertFalse(this.sut.isDefault());
    }
}
