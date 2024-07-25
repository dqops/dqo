package com.dqops.core.incidents.message;

import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A creator for text field of incident notification message in html format.
 */
@Component
public class IncidentNotificationHtmlMessageFormatterImpl implements IncidentNotificationHtmlMessageFormatter {

    private static final String KEY_VALUE_FORMAT = "<b>%s:</b> %s";
    private static final String NEW_LINE = "\n";

    private final InstanceCloudLoginService instanceCloudLoginService;
    private final DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * @param instanceCloudLoginService Instance cloud login service.
     * @param defaultTimeZoneProvider Default time zone provider.
     */
    @Autowired
    public IncidentNotificationHtmlMessageFormatterImpl(InstanceCloudLoginService instanceCloudLoginService,
                                                            DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.instanceCloudLoginService = instanceCloudLoginService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Prepares string for text field of notification message, which is built from multiple fields from it's parameters.
     * @param notificationMessage A notification message.
     * @return Markdown formatted string
     */
    public String prepareText(IncidentNotificationMessage notificationMessage){

        String fullTableNameWithLink = formatToLink(
                prepareUrlToTable(notificationMessage),
                notificationMessage.getSchema() + "." + notificationMessage.getTable()
        );

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getFormatParagraph(bold(prepareHeader(notificationMessage.getStatus(),
                fullTableNameWithLink
        ))));
        stringBuilder.append(getFormatParagraph(""));

        stringBuilder.append(extractInstantWithFormatting(notificationMessage.getFirstSeen(), IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME));
        if(!notificationMessage.getStatus().equals(IncidentStatus.open)){
            stringBuilder.append(extractInstantWithFormatting(notificationMessage.getLastSeen(), IncidentsColumnNames.LAST_SEEN_COLUMN_NAME));
        }
        stringBuilder.append(extractStringWithFormatting(notificationMessage.getQualityDimension(), IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
        stringBuilder.append(extractStringWithFormatting(notificationMessage.getCheckCategory(), IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
        stringBuilder.append(String.format(getFormatParagraph(KEY_VALUE_FORMAT),
                readableColumnName(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME),
                RuleSeverityLevel.fromSeverityLevel(notificationMessage.getHighestSeverity()).name()));
        stringBuilder.append(
                String.format(getFormatParagraph(KEY_VALUE_FORMAT),
                        "Total data quality issues",
                        notificationMessage.getFailedChecksCount()));
        if(notificationMessage.getTablePriority() != null){
            stringBuilder.append(extractIntWithFormatting(notificationMessage.getTablePriority(), IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME));
        }

        if (notificationMessage.getIssueUrl() != null && !notificationMessage.getIssueUrl().isEmpty()) {
            stringBuilder.append( String.format(getFormatParagraph(KEY_VALUE_FORMAT),
                    readableColumnName(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME),
                    formatToLink(notificationMessage.getIssueUrl(), "LINK")));
        }

        if(notificationMessage.getDataGroupName() != null && !notificationMessage.getDataGroupName().isEmpty()){
            stringBuilder.append(extractStringWithFormatting(notificationMessage.getDataGroupName(), IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME));
        }

        if(notificationMessage.getCheckType() != null && !notificationMessage.getCheckType().isEmpty()) {
            stringBuilder.append(extractStringWithFormatting(notificationMessage.getCheckType(), IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME));
        }

        if(notificationMessage.getCheckName() != null && !notificationMessage.getCheckName().isEmpty()) {
            stringBuilder.append(extractStringWithFormatting(notificationMessage.getCheckName(), IncidentsColumnNames.CHECK_NAME_COLUMN_NAME));
        }

        stringBuilder.append(getFormatParagraph(""));

        stringBuilder.append(getFormatParagraph(formatToLink(
                prepareUrlToIncident(notificationMessage),
                "View in DQOps"
        )));

        return wrapInHtml(stringBuilder.toString());
    }

    private String wrapInHtml(String mainMessage){
        String htmlMessage = """
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
                %s
                            </div>
                            <div class="footer">
                                <p>&copy; 2024 DQOps. All rights reserved.</p>
                                <p><a href="https://dqops.com/docs/working-with-dqo/managing-data-quality-incidents-with-dqops/#configure-incidents">Configure incidents</a></p>
                            </div>
                        </div>
                    </body>
                </html>""".replace("%s\n", mainMessage);

        return htmlMessage;
    }

    /**
     * Prepares an url to an incident in application UI instance.
     * @param notificationMessage Notification message
     * @return A complete URL to an incident
     */
    private String prepareUrlToIncident(IncidentNotificationMessage notificationMessage){
        return instanceCloudLoginService.getReturnBaseUrl()
                + "/incidents/"
                + URLEncoder.encode(notificationMessage.getConnection(), StandardCharsets.UTF_8) + "/"
                + notificationMessage.getFirstSeen().atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId()).getYear() + "/"
                + notificationMessage.getFirstSeen().atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId()).getMonthValue() + "/"
                + notificationMessage.getIncidentId();
    }

    /**
     * Prepares an url to table's details in application UI instance.
     * @param notificationMessage Notification message with details.
     * @return A complete URL to table details
     */
    private String prepareUrlToTable(IncidentNotificationMessage notificationMessage){
        return instanceCloudLoginService.getReturnBaseUrl()
                + "/sources/connection/" + URLEncoder.encode(notificationMessage.getConnection(), StandardCharsets.UTF_8)
                + "/schema/" + URLEncoder.encode(notificationMessage.getSchema(), StandardCharsets.UTF_8)
                + "/table/" + URLEncoder.encode(notificationMessage.getTable(), StandardCharsets.UTF_8)
                + "/detail";
    }

    /**
     * Prepares header sentence of text field being built. The header is variable depending on incidentStatus
     * @param incidentStatus Used to choose which header text to use.
     * @param fullTableName Table name identified with a schema.
     * @return Header sentence
     */
    private String prepareHeader(IncidentStatus incidentStatus,
                                 String fullTableName){
        if(incidentStatus.equals(IncidentStatus.open)){
            return "New incident detected in the " + fullTableName + " table.";
        }
        return "The incident in the " + fullTableName + " table has been " + incidentStatus.name() + ".";
    }

    /**
     * Extracts a string value from selected column for Row object and applies formatting.
     * @param incidentRow Row object
     * @param incidentsColumnName Column name used for extraction
     * @return A formatted string value from selected column. If it does not exist a blank string is returned.
     */
    private String extractStringWithFormatting(String incidentRow, String incidentsColumnName){
        if (incidentRow != null) {
            return String.format(getFormatParagraph(KEY_VALUE_FORMAT),
                    readableColumnName(incidentsColumnName),
                    incidentRow);
        }
        return "";
    }

    /**
     * Extracts a string value of instant from selected column for Row object and applies formatting.
     * @param instant Instant time.
     * @param incidentsColumnName Column name used for extraction.
     * @return A formatted date time with GMT from selected column.
     */
    private String extractInstantWithFormatting(Instant instant, String incidentsColumnName){
        ZonedDateTime zonedDateTime = instant.atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId());
        ZoneOffset zoneOffset = this.defaultTimeZoneProvider.getDefaultTimeZoneId().getRules().getOffset(instant);
        int hours = zoneOffset.getTotalSeconds() / 3600;

        return String.format(getFormatParagraph(KEY_VALUE_FORMAT),
                readableColumnName(incidentsColumnName),
                zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + " (GMT" + (hours > 0 ? "+":"") + hours + ")");
    }

    /**
     * Applies formatting.
     * @param value
     * @param incidentsColumnName
     * @return A formatted int from selected column. If contains a negative value a blank string is returned.
     */
    private String extractIntWithFormatting(int value, String incidentsColumnName){
        if(value > 0){
            return String.format(getFormatParagraph(KEY_VALUE_FORMAT),
                    readableColumnName(incidentsColumnName),
                    value);
        }
        return "";
    }

    /**
     * Applies capitalization and removed underscores.
     * @param columnName
     * @return User friendly column name
     */
    private String readableColumnName(String columnName){
        return capitalize(columnName).replace("_"," ");
    }

    /**
     * Capitalizes first word in text
     * @param text text
     * @return Modified text
     */
    private String capitalize(String text){
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * Bolds text
     * @param text text
     * @return Modified text
     */
    private String bold(String text){
        return String.format("<b>%s</b>", text);
    }


    /**
     * Formats a line of text to block
     * @param text text to be formatted
     * @return Formatted text
     */
    private String getFormatParagraph(String text){
        return "                <p>" + text + "</p>" + NEW_LINE;
    }

    /**
     * Formats the string to a link. Adds special characters: "<" at the beginning, "|" between link and text and ">" at the end.
     * @return Link formatted text
     */
    private String formatToLink(String link, String text){
        return String.format("<a href=\"%s\">%s</a>", link, text);
    }

}
