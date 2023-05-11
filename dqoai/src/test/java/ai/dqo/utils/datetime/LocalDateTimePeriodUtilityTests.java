/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.datetime;

import ai.dqo.BaseTest;
import ai.dqo.metadata.groupings.TimePeriodGradient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class LocalDateTimePeriodUtilityTests extends BaseTest {
    @Test
    void calculateDifferenceInPeriodsCount_whenYears_thenCalculatesCorrectValue() {
        Assertions.assertEquals(2, LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                LocalDateTime.of(2020, 3, 8, 12, 34, 55),
                LocalDateTime.of(2022, 3, 8, 12, 34, 59), TimePeriodGradient.year));
    }

    @Test
    void calculateDifferenceInPeriodsCount_whenQuarters_thenCalculatesCorrectValue() {
        Assertions.assertEquals(8, LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0), TimePeriodGradient.quarter));
    }

    @Test
    void calculateDifferenceInPeriodsCount_whenMonths_thenCalculatesCorrectValue() {
        Assertions.assertEquals(24, LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0), TimePeriodGradient.month));
    }

    @Test
    void calculateDifferenceInPeriodsCount_whenWeeks_thenCalculatesCorrectValue() {
        Assertions.assertEquals(104, LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0), TimePeriodGradient.week));
    }

    @Test
    void calculateDifferenceInPeriodsCount_whenDays_thenCalculatesCorrectValue() {
        Assertions.assertEquals(731, LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0), TimePeriodGradient.day));
    }

    @Test
    void calculateDifferenceInPeriodsCount_whenHour_thenCalculatesCorrectValue() {
        Assertions.assertEquals(17544, LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0), TimePeriodGradient.hour));
    }

    @Test
    void calculateLocalDateTimeMinusTimePeriods_whenYear_thenCalculatesCorrectDate() {
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), 2, TimePeriodGradient.year));
    }

    @Test
    void calculateLocalDateTimeMinusTimePeriods_whenQuarter_thenCalculatesCorrectDate() {
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), 8, TimePeriodGradient.quarter));
    }

    @Test
    void calculateLocalDateTimeMinusTimePeriods_whenMonth_henCalculatesCorrectDate() {
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), 24, TimePeriodGradient.month));
    }

    @Test
    void calculateLocalDateTimeMinusTimePeriods_whenWeek_henCalculatesCorrectDate() {
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                        LocalDateTime.of(2022, 1, 15, 0, 0, 0), 2, TimePeriodGradient.week));
    }

    @Test
    void calculateLocalDateTimeMinusTimePeriods_whenDay_henCalculatesCorrectDate() {
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                        LocalDateTime.of(2022, 1, 15, 0, 0, 0), 14, TimePeriodGradient.day));
    }

    @Test
    void calculateLocalDateTimeMinusTimePeriods_whenHour_henCalculatesCorrectDate() {
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 14, 10, 0, 0),
                LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                        LocalDateTime.of(2022, 1, 15, 0, 0, 0), 14, TimePeriodGradient.hour));
    }
}
