/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.dqohome;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DqoHomeImplTests extends BaseTest {
    private DqoHomeImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new DqoHomeImpl(false);
    }

    @Test
    void getSensors_whenCalled_thenReturnsSensorList() {
        Assertions.assertNotNull(this.sut.getSensors());
    }

    @Test
    void getRules_whenCalled_thenReturnsRulesList() {
        Assertions.assertNotNull(this.sut.getRules());
    }
}
