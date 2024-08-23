package com.dqops.core.incidents.message;

import com.dqops.BaseTest;
import com.dqops.core.dqocloud.login.InstanceCloudLoginServiceObjectMother;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class IncidentNotificationMessageHtmlFormatterImplTest extends BaseTest {

    private IncidentNotificationHtmlMessageFormatterImpl sut;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    @BeforeEach
    void setUp() {
        this.defaultTimeZoneProvider = new DefaultTimeZoneProviderStub();

        this.sut = new IncidentNotificationHtmlMessageFormatterImpl(
                InstanceCloudLoginServiceObjectMother.getDefault(),
                defaultTimeZoneProvider);
    }


    @Test
    void prepareText_fromNotificationMessageParametersOfOpenedIncident_generatesValidMessage() {
        ((DefaultTimeZoneProviderStub)defaultTimeZoneProvider).setTimeZone(ZoneId.of("+02:00"));

        Instant instant = LocalDateTime
                .of(2023, 9, 1, 12, 30, 20)
                .toInstant(ZoneOffset.UTC);

        IncidentNotificationMessage notificationMessage = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);

        String message = sut.prepareText(notificationMessage);

        assertNotNull(message);

        assertEquals("""
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>DQOps Incident Notification</title>
                                <style>
                                    body {
                                        font-family: Arial, sans-serif;
                                        margin: 0;
                                        padding: 0;
                                        background-color: #f6f6f6;
                                    }
                                    .container {
                                        width: 100%;
                                        max-width: 600px;
                                        margin: 0 auto;
                                        background-color: #ffffff;
                                        padding: 20px;
                                        border: 1px solid #dddddd;
                                    }
                                    .logo {
                                        text-align: center;
                                        padding: 10px 0;
                                        border-bottom: 2px solid #000;
                                    }
                                    .content {
                                        padding: 20px;
                                        text-align: left;
                                    }
                                    .footer {
                                        text-align: center;
                                        padding: 10px 0;
                                        color: #777777;
                                        font-size: 12px;
                                    }
                                    a {
                                        text-decoration: none;
                                    }
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                    <div class="logo">
                                        <img src="https://dqops.com/wp-content/uploads/2023/06/DQOps_logo_180x47.png" alt="Company Logo">
                                    </div>
                                    <div class="content">
                                        <p><b>New incident detected in the <a href="http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail">schema_here.table_name_here</a> table.</b></p>
                                        <p></p>
                                        <p><b>Data source:</b> connection_name</p>
                                        <p><b>First seen:</b> 2023-09-01 14:30:20 (GMT+2)</p>
                                        <p><b>Quality dimension:</b> Reasonableness</p>
                                        <p><b>Check category:</b> volume</p>
                                        <p><b>Highest severity:</b> fatal</p>
                                        <p><b>Total data quality issues:</b> 10</p>
                                        <p><b>Table priority:</b> 2</p>
                                        <p></p>
                                        <p><a href="http://localhost:8888/incidents/connection_name/2023/9/1">View in DQOps</a></p>
                                    </div>
                                    <div class="footer">
                                        <p>&copy; 2024 DQOps. All rights reserved.</p>
                                        <p><a href="https://dqops.com/docs/working-with-dqo/managing-data-quality-incidents-with-dqops/#configure-incidents">Configure incidents</a></p>
                                    </div>
                                </div>
                            </body>
                        </html>""",
                message
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
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>DQOps Incident Notification</title>
                                <style>
                                    body {
                                        font-family: Arial, sans-serif;
                                        margin: 0;
                                        padding: 0;
                                        background-color: #f6f6f6;
                                    }
                                    .container {
                                        width: 100%;
                                        max-width: 600px;
                                        margin: 0 auto;
                                        background-color: #ffffff;
                                        padding: 20px;
                                        border: 1px solid #dddddd;
                                    }
                                    .logo {
                                        text-align: center;
                                        padding: 10px 0;
                                        border-bottom: 2px solid #000;
                                    }
                                    .content {
                                        padding: 20px;
                                        text-align: left;
                                    }
                                    .footer {
                                        text-align: center;
                                        padding: 10px 0;
                                        color: #777777;
                                        font-size: 12px;
                                    }
                                    a {
                                        text-decoration: none;
                                    }
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                    <div class="logo">
                                        <img src="https://dqops.com/wp-content/uploads/2023/06/DQOps_logo_180x47.png" alt="Company Logo">
                                    </div>
                                    <div class="content">
                                        <p><b>The incident in the <a href="http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail">schema_here.table_name_here</a> table has been acknowledged.</b></p>
                                        <p></p>
                                        <p><b>Data source:</b> connection_name</p>
                                        <p><b>First seen:</b> 2023-09-01 14:30:20 (GMT+2)</p>
                                        <p><b>Last seen:</b> 2023-09-01 14:30:20 (GMT+2)</p>
                                        <p><b>Quality dimension:</b> Reasonableness</p>
                                        <p><b>Check category:</b> volume</p>
                                        <p><b>Highest severity:</b> fatal</p>
                                        <p><b>Total data quality issues:</b> 10</p>
                                        <p><b>Table priority:</b> 2</p>
                                        <p></p>
                                        <p><a href="http://localhost:8888/incidents/connection_name/2023/9/1">View in DQOps</a></p>
                                    </div>
                                    <div class="footer">
                                        <p>&copy; 2024 DQOps. All rights reserved.</p>
                                        <p><a href="https://dqops.com/docs/working-with-dqo/managing-data-quality-incidents-with-dqops/#configure-incidents">Configure incidents</a></p>
                                    </div>
                                </div>
                            </body>
                        </html>""",
                message
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
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>DQOps Incident Notification</title>
                                <style>
                                    body {
                                        font-family: Arial, sans-serif;
                                        margin: 0;
                                        padding: 0;
                                        background-color: #f6f6f6;
                                    }
                                    .container {
                                        width: 100%;
                                        max-width: 600px;
                                        margin: 0 auto;
                                        background-color: #ffffff;
                                        padding: 20px;
                                        border: 1px solid #dddddd;
                                    }
                                    .logo {
                                        text-align: center;
                                        padding: 10px 0;
                                        border-bottom: 2px solid #000;
                                    }
                                    .content {
                                        padding: 20px;
                                        text-align: left;
                                    }
                                    .footer {
                                        text-align: center;
                                        padding: 10px 0;
                                        color: #777777;
                                        font-size: 12px;
                                    }
                                    a {
                                        text-decoration: none;
                                    }
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                    <div class="logo">
                                        <img src="https://dqops.com/wp-content/uploads/2023/06/DQOps_logo_180x47.png" alt="Company Logo">
                                    </div>
                                    <div class="content">
                                        <p><b>The incident in the <a href="http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail">schema_here.table_name_here</a> table has been acknowledged.</b></p>
                                        <p></p>
                                        <p><b>Data source:</b> connection_name</p>
                                        <p><b>First seen:</b> 2023-09-01 14:30:20 (GMT+2)</p>
                                        <p><b>Last seen:</b> 2023-09-01 14:30:20 (GMT+2)</p>
                                        <p><b>Quality dimension:</b> Reasonableness</p>
                                        <p><b>Check category:</b> volume</p>
                                        <p><b>Highest severity:</b> fatal</p>
                                        <p><b>Total data quality issues:</b> 10</p>
                                        <p><b>Table priority:</b> 2</p>
                                        <p><b>Issue url:</b> <a href="https://www.google.com">LINK</a></p>
                                        <p></p>
                                        <p><a href="http://localhost:8888/incidents/connection_name/2023/9/1">View in DQOps</a></p>
                                    </div>
                                    <div class="footer">
                                        <p>&copy; 2024 DQOps. All rights reserved.</p>
                                        <p><a href="https://dqops.com/docs/working-with-dqo/managing-data-quality-incidents-with-dqops/#configure-incidents">Configure incidents</a></p>
                                    </div>
                                </div>
                            </body>
                        </html>""",
                message
        );
    }

    @Test
    void prepareText_forTimeZoneWithNegativeOffset_generatesValidMessage() {
        ((DefaultTimeZoneProviderStub)defaultTimeZoneProvider).setTimeZone(ZoneId.of("-08:00"));

        Instant instant = LocalDateTime
                .of(2023, 9, 1, 12, 30, 20)
                .toInstant(ZoneOffset.UTC);

        IncidentNotificationMessage notificationMessage = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.acknowledged);

        String message = sut.prepareText(notificationMessage);

        assertNotNull(message);
        assertEquals("""
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>DQOps Incident Notification</title>
                                <style>
                                    body {
                                        font-family: Arial, sans-serif;
                                        margin: 0;
                                        padding: 0;
                                        background-color: #f6f6f6;
                                    }
                                    .container {
                                        width: 100%;
                                        max-width: 600px;
                                        margin: 0 auto;
                                        background-color: #ffffff;
                                        padding: 20px;
                                        border: 1px solid #dddddd;
                                    }
                                    .logo {
                                        text-align: center;
                                        padding: 10px 0;
                                        border-bottom: 2px solid #000;
                                    }
                                    .content {
                                        padding: 20px;
                                        text-align: left;
                                    }
                                    .footer {
                                        text-align: center;
                                        padding: 10px 0;
                                        color: #777777;
                                        font-size: 12px;
                                    }
                                    a {
                                        text-decoration: none;
                                    }
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                    <div class="logo">
                                        <img src="https://dqops.com/wp-content/uploads/2023/06/DQOps_logo_180x47.png" alt="Company Logo">
                                    </div>
                                    <div class="content">
                                        <p><b>The incident in the <a href="http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail">schema_here.table_name_here</a> table has been acknowledged.</b></p>
                                        <p></p>
                                        <p><b>Data source:</b> connection_name</p>
                                        <p><b>First seen:</b> 2023-09-01 04:30:20 (GMT-8)</p>
                                        <p><b>Last seen:</b> 2023-09-01 04:30:20 (GMT-8)</p>
                                        <p><b>Quality dimension:</b> Reasonableness</p>
                                        <p><b>Check category:</b> volume</p>
                                        <p><b>Highest severity:</b> fatal</p>
                                        <p><b>Total data quality issues:</b> 10</p>
                                        <p><b>Table priority:</b> 2</p>
                                        <p></p>
                                        <p><a href="http://localhost:8888/incidents/connection_name/2023/9/1">View in DQOps</a></p>
                                    </div>
                                    <div class="footer">
                                        <p>&copy; 2024 DQOps. All rights reserved.</p>
                                        <p><a href="https://dqops.com/docs/working-with-dqo/managing-data-quality-incidents-with-dqops/#configure-incidents">Configure incidents</a></p>
                                    </div>
                                </div>
                            </body>
                        </html>""",
                message
        );
    }

    @Test
    void prepareText_incidentMessageWithMessageSet_generatesValidMessage() {
        ((DefaultTimeZoneProviderStub)defaultTimeZoneProvider).setTimeZone(ZoneId.of("-08:00"));

        Instant instant = LocalDateTime
                .of(2023, 9, 1, 12, 30, 20)
                .toInstant(ZoneOffset.UTC);

        IncidentNotificationMessage notificationMessage = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.acknowledged);
        notificationMessage.setMessage("A very important information about the configuration of the notification.");

        String message = sut.prepareText(notificationMessage);

        assertNotNull(message);
        assertEquals("""
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>DQOps Incident Notification</title>
                                <style>
                                    body {
                                        font-family: Arial, sans-serif;
                                        margin: 0;
                                        padding: 0;
                                        background-color: #f6f6f6;
                                    }
                                    .container {
                                        width: 100%;
                                        max-width: 600px;
                                        margin: 0 auto;
                                        background-color: #ffffff;
                                        padding: 20px;
                                        border: 1px solid #dddddd;
                                    }
                                    .logo {
                                        text-align: center;
                                        padding: 10px 0;
                                        border-bottom: 2px solid #000;
                                    }
                                    .content {
                                        padding: 20px;
                                        text-align: left;
                                    }
                                    .footer {
                                        text-align: center;
                                        padding: 10px 0;
                                        color: #777777;
                                        font-size: 12px;
                                    }
                                    a {
                                        text-decoration: none;
                                    }
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                    <div class="logo">
                                        <img src="https://dqops.com/wp-content/uploads/2023/06/DQOps_logo_180x47.png" alt="Company Logo">
                                    </div>
                                    <div class="content">
                                        <p><b>The incident in the <a href="http://localhost:8888/sources/connection/connection_name/schema/schema_here/table/table_name_here/detail">schema_here.table_name_here</a> table has been acknowledged.</b></p>
                                        <p></p>
                                        <p><b>Data source:</b> connection_name</p>
                                        <p><b>First seen:</b> 2023-09-01 04:30:20 (GMT-8)</p>
                                        <p><b>Last seen:</b> 2023-09-01 04:30:20 (GMT-8)</p>
                                        <p><b>Quality dimension:</b> Reasonableness</p>
                                        <p><b>Check category:</b> volume</p>
                                        <p><b>Highest severity:</b> fatal</p>
                                        <p><b>Total data quality issues:</b> 10</p>
                                        <p><b>Table priority:</b> 2</p>
                                        <p><b>Message:</b> A very important information about the configuration of the notification.</p>
                                        <p></p>
                                        <p><a href="http://localhost:8888/incidents/connection_name/2023/9/1">View in DQOps</a></p>
                                    </div>
                                    <div class="footer">
                                        <p>&copy; 2024 DQOps. All rights reserved.</p>
                                        <p><a href="https://dqops.com/docs/working-with-dqo/managing-data-quality-incidents-with-dqops/#configure-incidents">Configure incidents</a></p>
                                    </div>
                                </div>
                            </body>
                        </html>""",
                message
        );
    }

}
