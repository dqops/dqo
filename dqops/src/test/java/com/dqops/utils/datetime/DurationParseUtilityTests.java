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
package com.dqops.utils.datetime;

import com.dqops.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class DurationParseUtilityTests extends BaseTest {
    @Test
    public void parseSimpleDuration_whenEmptyString_thenReturnsNull() {
        Assertions.assertNull(DurationParseUtility.parseSimpleDuration(""));
    }

    @Test
    public void parseSimpleDuration_whenNullString_thenReturnsNull() {
        Assertions.assertNull(DurationParseUtility.parseSimpleDuration(null));
    }

    @Test
    public void parseSimpleDuration_whenJustNumber_thenReturnsDurationSeconds() {
        Duration result = DurationParseUtility.parseSimpleDuration("50");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.of(50L, ChronoUnit.SECONDS), result);
    }

    @Test
    public void parseSimpleDuration_whenJustNumberWithS_thenReturnsDurationSeconds() {
        Duration result = DurationParseUtility.parseSimpleDuration("40s");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.of(40L, ChronoUnit.SECONDS), result);
    }

    @Test
    public void parseSimpleDuration_whenJustNumberWithM_thenReturnsDurationMinutes() {
        Duration result = DurationParseUtility.parseSimpleDuration("30m");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.of(30L, ChronoUnit.MINUTES), result);
    }

    @Test
    public void parseSimpleDuration_whenJustNumberWithH_thenReturnsDurationHours() {
        Duration result = DurationParseUtility.parseSimpleDuration("20H");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.of(20L, ChronoUnit.HOURS), result);
    }
}
