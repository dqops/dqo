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

        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(1, incidentCountsModel.getCountFromLast7days());
        Assertions.assertEquals(1, incidentCountsModel.getCurrentMonthCount());
        Assertions.assertEquals(0, incidentCountsModel.getPreviousMonthCount());
    }

    @Test
    void verifyAddition_whenOccurredLastMonth_isCounted() {

        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(defaultTimeZoneProvider.getDefaultTimeZoneId());

        Instant lastMonth = LocalDateTime.now().minusMonths(1).atZone(defaultTimeZoneProvider.getDefaultTimeZoneId()).toInstant();

        incidentCountsModel.processCountIncrementation(lastMonth);

        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast7days());
        Assertions.assertEquals(0, incidentCountsModel.getCurrentMonthCount());
        Assertions.assertEquals(1, incidentCountsModel.getPreviousMonthCount());
    }
    @Test
    void verifyAddition_whenOccurredTwoAndHalfMonthAgo_isNotCounted() {

        IncidentCountsModel incidentCountsModel = IncidentCountsModel.createInstance(defaultTimeZoneProvider.getDefaultTimeZoneId());

        Instant twoAndHalfMonthsAgo = Instant.now().minusSeconds(45 * 24 * 60 * 60);

        incidentCountsModel.processCountIncrementation(twoAndHalfMonthsAgo);

        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast24h());
        Assertions.assertEquals(0, incidentCountsModel.getCountFromLast7days());
        Assertions.assertEquals(0, incidentCountsModel.getCurrentMonthCount());
        Assertions.assertEquals(0, incidentCountsModel.getPreviousMonthCount());
    }

}