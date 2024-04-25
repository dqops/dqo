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
package com.dqops.data.checkresults.models;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.data.checkresults.models.CheckResultsOverviewDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@SpringBootTest
public class CheckResultsOverviewDataModelTests extends BaseTest {
    private CheckResultsOverviewDataModel sut;

    @BeforeEach
    void setUp() {
        this.sut = new CheckResultsOverviewDataModel();
    }

    @Test
    void makeTimePeriodDisplayText_whenDayPeriodGMTPlus1AndTimeScaleDaily_thenFormatsDateWithTimezone() {
        LocalDateTime timePeriod = LocalDateTime.of(2023, 02, 25, 0, 0, 0);
        Instant timePeriodUtc = timePeriod.atZone(ZoneId.of("GMT+1")).toInstant();

        String displayText = this.sut.makeTimePeriodDisplayText(timePeriod, timePeriodUtc, CheckTimeScale.daily);
        Assertions.assertEquals("2023-02-25+01:00", displayText);
    }

    @Test
    void makeTimePeriodDisplayText_whenDayPeriodGMTPlus1AndTimeScaleMonthly_thenFormatsYearAndMonthWithTimezone() {
        LocalDateTime timePeriod = LocalDateTime.of(2023, 02, 01, 0, 0, 0);
        Instant timePeriodUtc = timePeriod.atZone(ZoneId.of("GMT+1")).toInstant();

        String displayText = this.sut.makeTimePeriodDisplayText(timePeriod, timePeriodUtc, CheckTimeScale.monthly);
        Assertions.assertEquals("2023-02 +01", displayText);
    }

    @Test
    void makeTimePeriodDisplayText_whenDayPeriodUtcAndTimeScaleMonthly_thenFormatsYearAndMonthWithTimezone() {
        LocalDateTime timePeriod = LocalDateTime.of(2023, 02, 01, 0, 0, 0);
        Instant timePeriodUtc = timePeriod.atOffset(ZoneOffset.UTC).toInstant();

        String displayText = this.sut.makeTimePeriodDisplayText(timePeriod, timePeriodUtc, CheckTimeScale.monthly);
        Assertions.assertEquals("2023-02 UTC", displayText);
    }

    @Test
    void makeTimePeriodDisplayText_whenDayPeriodUtcAndTimeScaleDaily_thenFormatsDateWithTimezone() {
        LocalDateTime timePeriod = LocalDateTime.of(2023, 02, 25, 0, 0, 0);
        Instant timePeriodUtc = timePeriod.atOffset(ZoneOffset.UTC).toInstant();

        String displayText = this.sut.makeTimePeriodDisplayText(timePeriod, timePeriodUtc, CheckTimeScale.daily);
        Assertions.assertEquals("2023-02-25 UTC", displayText);
    }

    @Test
    void makeTimePeriodDisplayText_whenMillisPeriodGMTPlus1AndTimeScaleNull_thenFormatsDateTimeWithTimezone() {
        LocalDateTime timePeriod = LocalDateTime.of(2023, 02, 25, 14, 16, 40, 333000000);
        Instant timePeriodUtc = timePeriod.atZone(ZoneId.of("GMT+1")).toInstant();

        String displayText = this.sut.makeTimePeriodDisplayText(timePeriod, timePeriodUtc, null);
        Assertions.assertEquals("2023-02-25 14:16:40.333+01:00", displayText);
    }
}
