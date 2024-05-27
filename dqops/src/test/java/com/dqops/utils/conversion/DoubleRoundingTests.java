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

package com.dqops.utils.conversion;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DoubleRoundingTests extends BaseTest {
    @Test
    void roundToKeepMaxEffectiveDigits_when0_thenReturnsZero() {
        Assertions.assertEquals(0.0, DoubleRounding.roundToKeepMaxEffectiveDigits(0.0, 1));
        Assertions.assertEquals(0.0, DoubleRounding.roundToKeepMaxEffectiveDigits(0.0, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when1_thenReturnsValue() {
        Assertions.assertEquals(1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(1.0, 1));
        Assertions.assertEquals(1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(1.0, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus1_thenReturnsValue() {
        Assertions.assertEquals(-1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-1.0, 1));
        Assertions.assertEquals(-1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-1.0, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when1234_thenReturnsValue() {
        Assertions.assertEquals(1234.0, DoubleRounding.roundToKeepMaxEffectiveDigits(1234.0, 1));
        Assertions.assertEquals(1234.0, DoubleRounding.roundToKeepMaxEffectiveDigits(1234.0, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus1234_thenReturnsValue() {
        Assertions.assertEquals(-1234.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-1234.0, 1));
        Assertions.assertEquals(-1234.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-1234.0, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when123456789012_thenReturnsValue() {
        Assertions.assertEquals(123456789012.0, DoubleRounding.roundToKeepMaxEffectiveDigits(123456789012.0, 1));
        Assertions.assertEquals(123456789012.0, DoubleRounding.roundToKeepMaxEffectiveDigits(123456789012.0, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus123456789012_thenReturnsValue() {
        Assertions.assertEquals(-123456789012.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-123456789012.0, 1));
        Assertions.assertEquals(-123456789012.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-123456789012.0, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when1_1_thenReturnsValue() {
        Assertions.assertEquals(1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(1.1, 1));
        Assertions.assertEquals(1.1, DoubleRounding.roundToKeepMaxEffectiveDigits(1.1, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when1_11_thenReturnsValue() {
        Assertions.assertEquals(1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(1.11, 1));
        Assertions.assertEquals(1.11, DoubleRounding.roundToKeepMaxEffectiveDigits(1.11, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when1_111_thenReturnsValue() {
        Assertions.assertEquals(1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(1.111, 1));
        Assertions.assertEquals(1.11, DoubleRounding.roundToKeepMaxEffectiveDigits(1.111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus1_1_thenReturnsValue() {
        Assertions.assertEquals(-1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-1.1, 1));
        Assertions.assertEquals(-1.1, DoubleRounding.roundToKeepMaxEffectiveDigits(-1.1, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus1_11_thenReturnsValue() {
        Assertions.assertEquals(-1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-1.11, 1));
        Assertions.assertEquals(-1.11, DoubleRounding.roundToKeepMaxEffectiveDigits(-1.11, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus1_111_thenReturnsValue() {
        Assertions.assertEquals(-1.0, DoubleRounding.roundToKeepMaxEffectiveDigits(-1.111, 1));
        Assertions.assertEquals(-1.11, DoubleRounding.roundToKeepMaxEffectiveDigits(-1.111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when0_1_thenReturnsValue() {
        Assertions.assertEquals(0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(0.1, 1));
        Assertions.assertEquals(0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(0.1, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when0_11_thenReturnsValue() {
        Assertions.assertEquals(0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(0.11, 1));
        Assertions.assertEquals(0.11, DoubleRounding.roundToKeepMaxEffectiveDigits(0.11, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when0_111_thenReturnsValue() {
        Assertions.assertEquals(0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(0.111, 1));
        Assertions.assertEquals(0.111, DoubleRounding.roundToKeepMaxEffectiveDigits(0.111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when0_1111_thenReturnsValue() {
        Assertions.assertEquals(0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(0.1111, 1));
        Assertions.assertEquals(0.111, DoubleRounding.roundToKeepMaxEffectiveDigits(0.1111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus0_1_thenReturnsValue() {
        Assertions.assertEquals(-0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.1, 1));
        Assertions.assertEquals(-0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.1, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus0_11_thenReturnsValue() {
        Assertions.assertEquals(-0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.11, 1));
        Assertions.assertEquals(-0.11, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.11, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus0_111_thenReturnsValue() {
        Assertions.assertEquals(-0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.111, 1));
        Assertions.assertEquals(-0.111, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus0_1111_thenReturnsValue() {
        Assertions.assertEquals(-0.1, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.1111, 1));
        Assertions.assertEquals(-0.111, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.1111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when0_011_thenReturnsValue() {
        Assertions.assertEquals(0.01, DoubleRounding.roundToKeepMaxEffectiveDigits(0.011, 1));
        Assertions.assertEquals(0.011, DoubleRounding.roundToKeepMaxEffectiveDigits(0.011, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when0_0111_thenReturnsValue() {
        Assertions.assertEquals(0.01, DoubleRounding.roundToKeepMaxEffectiveDigits(0.0111, 1));
        Assertions.assertEquals(0.0111, DoubleRounding.roundToKeepMaxEffectiveDigits(0.0111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_when0_01111_thenReturnsValue() {
        Assertions.assertEquals(0.01, DoubleRounding.roundToKeepMaxEffectiveDigits(0.01111, 1));
        Assertions.assertEquals(0.0111, DoubleRounding.roundToKeepMaxEffectiveDigits(0.01111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus0_011_thenReturnsValue() {
        Assertions.assertEquals(-0.01, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.011, 1));
        Assertions.assertEquals(-0.011, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.011, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus0_0111_thenReturnsValue() {
        Assertions.assertEquals(-0.01, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.0111, 1));
        Assertions.assertEquals(-0.0111, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.0111, 3));
    }

    @Test
    void roundToKeepMaxEffectiveDigits_whenMinus0_01111_thenReturnsValue() {
        Assertions.assertEquals(-0.01, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.01111, 1));
        Assertions.assertEquals(-0.0111, DoubleRounding.roundToKeepMaxEffectiveDigits(-0.01111, 3));
    }
}
