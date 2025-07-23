/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.conversion;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LongRoundingTests extends BaseTest {
    @Test
    void roundToKeepEffectiveDigits_whenSmallValue_thenReturnsAsIs() {
        Assertions.assertEquals(12L, LongRounding.roundToKeepEffectiveDigits(12L));
        Assertions.assertEquals(123L, LongRounding.roundToKeepEffectiveDigits(123L));
    }

    @Test
    void roundToKeepEffectiveDigits_whenSmallValueNegative_thenReturnsAsIs() {
        Assertions.assertEquals(-12L, LongRounding.roundToKeepEffectiveDigits(-12L));
        Assertions.assertEquals(-123L, LongRounding.roundToKeepEffectiveDigits(-123L));
    }

    @Test
    void roundToKeepEffectiveDigits_whenBiggerValuePositive_thenReturnsRounded() {
        Assertions.assertEquals(1230L, LongRounding.roundToKeepEffectiveDigits(1234L));
        Assertions.assertEquals(123000L, LongRounding.roundToKeepEffectiveDigits(123456L));
    }

    @Test
    void roundToKeepEffectiveDigits_whenBiggerValueNegative_thenReturnsRounded() {
        Assertions.assertEquals(-1230L, LongRounding.roundToKeepEffectiveDigits(-1234L));
        Assertions.assertEquals(-123000L, LongRounding.roundToKeepEffectiveDigits(-123456L));
    }
}
