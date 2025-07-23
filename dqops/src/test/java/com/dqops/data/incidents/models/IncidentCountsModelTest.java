/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.incidents.models;

import com.dqops.BaseTest;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootTest
class IncidentCountsModelTest extends BaseTest {

    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    @BeforeEach
    void setUp() {
        this.defaultTimeZoneProvider = DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider();
    }

    @Test
    void verifyAddition_whenOccurredMomentAgo_countIt() {

        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(defaultTimeZoneProvider.getDefaultTimeZoneId());

        Instant momentAgo = Instant.now().minusSeconds(1);

        incidentCountsModel.processCountIncrementation(momentAgo);

        Assertions.assertEquals(1, incidentCountsModel.getTotalCount());
        Assertions.assertEquals(1, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(1, incidentCountsModel.getCountFromLast7days());
        Assertions.assertEquals(1, incidentCountsModel.getCurrentMonthCount());
        Assertions.assertEquals(0, incidentCountsModel.getPreviousMonthCount());
    }

    @Test
    void verifyAddition_whenOccurredTwoDaysAgo_isCounted() {

        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(defaultTimeZoneProvider.getDefaultTimeZoneId());

        Instant toDaysAgo = Instant.now().minusSeconds(2 * 24 * 60 * 60);

        incidentCountsModel.processCountIncrementation(toDaysAgo);

        Assertions.assertEquals(1, incidentCountsModel.getTotalCount());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(1, incidentCountsModel.getCountFromLast7days());
    }

    @Test
    void verifyAddition_whenOccurredSevenDaysAgoAtDayStart_isCounted() {
        ZoneId zoneId = defaultTimeZoneProvider.getDefaultTimeZoneId();
        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(zoneId);

        Instant sevenDaysAgoDayStart = Instant.now().atZone(zoneId).minusDays(7).toLocalDate().atStartOfDay().plusHours(1).atZone(zoneId).toInstant();

        incidentCountsModel.processCountIncrementation(sevenDaysAgoDayStart);

        Assertions.assertEquals(1, incidentCountsModel.getTotalCount());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(1, incidentCountsModel.getCountFromLast7days());
    }

    @Test
    void verifyAddition_whenOccurredLastMonth_isCounted() {

        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(defaultTimeZoneProvider.getDefaultTimeZoneId());

        Instant lastMonth = LocalDateTime.now().minusMonths(1).atZone(defaultTimeZoneProvider.getDefaultTimeZoneId()).toInstant();

        incidentCountsModel.processCountIncrementation(lastMonth);

        Assertions.assertEquals(1, incidentCountsModel.getTotalCount());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast7days());
        Assertions.assertEquals(0, incidentCountsModel.getCurrentMonthCount());
        Assertions.assertEquals(1, incidentCountsModel.getPreviousMonthCount());
    }

    @Test
    void verifyAddition_whenOccurredThreeAndHalfMonthAgo_isNotCounted() {

        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(defaultTimeZoneProvider.getDefaultTimeZoneId());

        Instant twoAndHalfMonthsAgo = Instant.now().minusSeconds(75 * 24 * 60 * 60);

        incidentCountsModel.processCountIncrementation(twoAndHalfMonthsAgo);

        Assertions.assertEquals(1, incidentCountsModel.getTotalCount());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast7days());
        Assertions.assertEquals(0, incidentCountsModel.getCurrentMonthCount());
        Assertions.assertEquals(0, incidentCountsModel.getPreviousMonthCount());
    }

    @Test
    void verifyAddition_whenOccurredOnMonthBeginningPositiveZone_isCounted() {

        ZoneId nearZeroPositiveZone = ZoneId.of("Europe/Warsaw");

        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(ZoneId.of("Europe/Warsaw"));

        Instant monthAgoFirstDay = Instant.now().atZone(nearZeroPositiveZone).minusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay(nearZeroPositiveZone).toInstant();

        incidentCountsModel.processCountIncrementation(monthAgoFirstDay);

        Assertions.assertEquals(1, incidentCountsModel.getTotalCount());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast7days());
        Assertions.assertEquals(0, incidentCountsModel.getCurrentMonthCount());
        Assertions.assertEquals(1, incidentCountsModel.getPreviousMonthCount());
    }

    @Test
    void verifyAddition_whenOccurredOnMonthBeginningNegativeZone_isCounted() {

        ZoneId nearZeroPositiveZone = ZoneId.of("US/Eastern");

        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(ZoneId.of("US/Eastern"));

        Instant monthAgoFirstDay = Instant.now().atZone(nearZeroPositiveZone).minusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay(nearZeroPositiveZone).toInstant();

        incidentCountsModel.processCountIncrementation(monthAgoFirstDay);

        Assertions.assertEquals(1, incidentCountsModel.getTotalCount());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast7days());
        Assertions.assertEquals(0, incidentCountsModel.getCurrentMonthCount());
        Assertions.assertEquals(1, incidentCountsModel.getPreviousMonthCount());
    }

}