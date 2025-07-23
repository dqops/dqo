/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.datetime;

import com.dqops.BaseTest;
import com.dqops.metadata.timeseries.TimePeriodGradient;
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
