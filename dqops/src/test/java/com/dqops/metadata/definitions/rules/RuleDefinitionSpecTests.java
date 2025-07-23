/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.rules;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;

@SpringBootTest
public class RuleDefinitionSpecTests extends BaseTest {
    private RuleDefinitionSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new RuleDefinitionSpec();
    }

    @Test
    void isDirty_whenParamsSet_thenIsDirtyIsTrue() {
        LinkedHashMap hashMap = new LinkedHashMap<String, String>();
        hashMap.put("test", "test");
		this.sut.setParameters(hashMap);
        Assertions.assertEquals(hashMap, this.sut.getParameters());
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenParamsObjectSameAsCurrentSet_thenIsDirtyIsFalse() {
        LinkedHashMap hashMap = new LinkedHashMap<String, String>();
        hashMap.put("test", "test");
		this.sut.setParameters(hashMap);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setParameters(hashMap);
        Assertions.assertFalse(this.sut.isDirty());
    }
}
