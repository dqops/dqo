/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.incidents.message;

import com.dqops.BaseTest;
import com.dqops.core.dqocloud.login.InstanceCloudLoginServiceObjectMother;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.incidents.factory.IncidentsTableFactoryObjectMother;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class IncidentNotificationMessageMarkdownFormatterImplTest extends BaseTest {

    private IncidentNotificationMessageMarkdownFormatterImpl sut;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    @BeforeEach
    void setUp() {
        this.defaultTimeZoneProvider = new DefaultTimeZoneProviderStub();

        this.sut = new IncidentNotificationMessageMarkdownFormatterImpl(
                InstanceCloudLoginServiceObjectMother.getDefault(),
                defaultTimeZoneProvider);
    }

    IncidentNotificationMessageParameters createSampleMessageParameters(Instant instant, IncidentStatus incidentStatus, String issueUrl){

        Table table = IncidentsTableFactoryObjectMother.createEmptyNormalizedTable("test_table_name");
        Row row = table.appendRow();

        row.setString(IncidentsColumnNames.ID_COLUMN_NAME, "1");
        row.setString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, "schema_here");
        row.setString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME, "table_name_here");
        row.setInt(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME, 2);
        row.setLong(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME, 3);
        row.setInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME, instant);
        row.setInstant(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME, instant);
        row.setInstant(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME, instant);
        row.setString(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, "Reasonableness");
        row.setString(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME, "volume");
        row.setString(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME, "");
        row.setString(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME, "");
        row.setString(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME, issueUrl);
        row.setInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME, 3);
        row.setInt(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME, 10);
        row.setString(IncidentsColumnNames.STATUS_COLUMN_NAME, incidentStatus.name());

        IncidentNotificationMessageParameters messageParameters = IncidentNotificationMessageParameters
                .builder()
                .incidentRow(row)
                .connectionName("connection_name")
                .build();

        return messageParameters;
    }

    @Test
    void prepareText_fromMessageParametersOfOpenedIncident_generatesValidMessage() {
        ((DefaultTimeZoneProviderStub)defaultTimeZoneProvider).setTimeZone(ZoneId.of("+02:00"));

        Instant instant = LocalDateTime
                .of(2023, 9, 1, 12, 30, 20)
                .toInstant(ZoneOffset.UTC);

        IncidentNotificationMessage notificationMessage = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);

        String message = sut.prepareText(notificationMessage);

        assertNotNull(message);
        assertEquals("""
                               > New incident detected in <http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail | schema_here.table_name_here> table.
                               > \s
                               > Data source: connection_name\s
                               > First seen: 2023-09-01 14:30:20 (GMT+2)\s
                               > Quality dimension: Reasonableness\s
                               > Check category: volume\s
                               > Highest severity: fatal\s
                               > Total data quality issues: 10\s
                               > Table priority: 2\s
                               > \s
                               > <http://localhost:8888/incidents/connection_name/2023/9/1 | View in DQOps>\s
                        """.replaceAll("\\s+", ""),
                message.replaceAll("\\s+", "")
        );
    }

    @Test
    void prepareText_fromMessageParametersOfAcknowledgedIncident_generatesValidMessage() {
        ((DefaultTimeZoneProviderStub)defaultTimeZoneProvider).setTimeZone(ZoneId.of("+02:00"));

        Instant instant = LocalDateTime
                .of(2023, 9, 1, 12, 30, 20)
                .toInstant(ZoneOffset.UTC);

        IncidentNotificationMessage notificationMessage = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.acknowledged);

        String message = sut.prepareText(notificationMessage);

        assertNotNull(message);
        assertEquals("""
                               > The incident in <http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail | schema_here.table_name_here> table has been acknowledged.\s
                               > \s
                               > Data source: connection_name\s
                               > First seen: 2023-09-01 14:30:20 (GMT+2)\s
                               > Last seen: 2023-09-01 14:30:20 (GMT+2)\s
                               > Quality dimension: Reasonableness\s
                               > Check category: volume\s
                               > Highest severity: fatal\s
                               > Total data quality issues: 10\s
                               > Table priority: 2\s
                               > \s
                               > <http://localhost:8888/incidents/connection_name/2023/9/1 | View in DQOps>\s
                        """.replaceAll("\\s+", ""),
                message.replaceAll("\\s+", "")
        );
    }

    @Test
    void prepareText_fromMessageParametersWithIssueUrl_generatesValidMessage() {
        ((DefaultTimeZoneProviderStub)defaultTimeZoneProvider).setTimeZone(ZoneId.of("+02:00"));

        Instant instant = LocalDateTime
                .of(2023, 9, 1, 12, 30, 20)
                .toInstant(ZoneOffset.UTC);

        IncidentNotificationMessage notificationMessage = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.acknowledged);
        notificationMessage.setIssueUrl("https://www.google.com");

        String message = sut.prepareText(notificationMessage);

        assertNotNull(message);
        assertEquals("""
                               > The incident in <http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail | schema_here.table_name_here> table has been acknowledged.\s
                               > \s
                               > Data source: connection_name\s
                               > First seen: 2023-09-01 14:30:20 (GMT+2)\s
                               > Last seen: 2023-09-01 14:30:20 (GMT+2)\s
                               > Quality dimension: Reasonableness\s
                               > Check category: volume\s
                               > Highest severity: fatal\s
                               > Total data quality issues: 10\s
                               > Table priority: 2\s
                               > Issue url: <https://www.google.com | LINK>\s
                               > \s
                               > <http://localhost:8888/incidents/connection_name/2023/9/1 | View in DQOps>\s
                        """.replaceAll("\\s+", ""),
                message.replaceAll("\\s+", "")
        );
    }

    @Test
    void prepareText_forTimeZoneWithNegativeOffset_generatesValidMessage() {
        ((DefaultTimeZoneProviderStub)defaultTimeZoneProvider).setTimeZone(ZoneId.of("-08:00"));

        Instant instant = LocalDateTime
                .of(2023, 9, 1, 12, 30, 20)
                .toInstant(ZoneOffset.UTC);

        IncidentNotificationMessage notificationMessage = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);

        String message = sut.prepareText(notificationMessage);

        assertNotNull(message);
        assertEquals("""
                               > New incident detected in <http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail | schema_here.table_name_here> table.
                               > \s
                               > Data source: connection_name\s
                               > First seen: 2023-09-01 04:30:20 (GMT-8)\s
                               > Quality dimension: Reasonableness\s
                               > Check category: volume\s
                               > Highest severity: fatal\s
                               > Total data quality issues: 10\s
                               > Table priority: 2\s
                               > \s
                               > <http://localhost:8888/incidents/connection_name/2023/9/1 | View in DQOps>\s
                        """.replaceAll("\\s+", ""),
                message.replaceAll("\\s+", "")
        );
    }

    @Test
    void prepareText_incidentMessageWithMessageSet_generatesValidMessage() {
        ((DefaultTimeZoneProviderStub)defaultTimeZoneProvider).setTimeZone(ZoneId.of("-08:00"));

        Instant instant = LocalDateTime
                .of(2023, 9, 1, 12, 30, 20)
                .toInstant(ZoneOffset.UTC);

        IncidentNotificationMessage notificationMessage = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        notificationMessage.setMessage("A very important information about the configuration of the notification.");

        String message = sut.prepareText(notificationMessage);

        assertNotNull(message);
        assertEquals("""
                               > New incident detected in <http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail | schema_here.table_name_here> table.
                               > \s
                               > Data source: connection_name\s
                               > First seen: 2023-09-01 04:30:20 (GMT-8)\s
                               > Quality dimension: Reasonableness\s
                               > Check category: volume\s
                               > Highest severity: fatal\s
                               > Total data quality issues: 10\s
                               > Table priority: 2\s
                               > Message: A very important information about the configuration of the notification.\s
                               > \s
                               > <http://localhost:8888/incidents/connection_name/2023/9/1 | View in DQOps>\s
                        """.replaceAll("\\s+", ""),
                message.replaceAll("\\s+", "")
        );
    }

}
