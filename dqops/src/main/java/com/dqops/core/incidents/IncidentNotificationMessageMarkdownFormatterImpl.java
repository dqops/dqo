package com.dqops.core.incidents;

import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A creator for text field of incident notification message in markdown format.
 */
@Component
public class IncidentNotificationMessageMarkdownFormatterImpl implements IncidentNotificationMessageMarkdownFormatter {

    private static final String KEY_VALUE_FORMAT = "%s" + ": %s";
    private static final String NEW_LINE = " \n";

    private final InstanceCloudLoginService instanceCloudLoginService;
    private final DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * @param instanceCloudLoginService Instance cloud login service.
     * @param defaultTimeZoneProvider Default time zone provider.
     */
    @Autowired
    public IncidentNotificationMessageMarkdownFormatterImpl(InstanceCloudLoginService instanceCloudLoginService,
                                                            DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.instanceCloudLoginService = instanceCloudLoginService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Prepares string for text field of notification message in markdown format, which is built from multiple fields from its parameters.
     * @param messageParameters A container with parameters that are used to build text field and make up links to application.
     * @return Markdown formatted string
     */
    public String prepareText(IncidentNotificationMessageParameters messageParameters){

        Row incidentRow = messageParameters.getIncidentRow();

        String fullTableNameWithLink = formatToLink(
                prepareUrlToTable(messageParameters),
                incidentRow.getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME)
                        + "."
                        + incidentRow.getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME)
        );

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBlockQuotedLine(prepareHeader(
                IncidentStatus.valueOf(incidentRow.getString(IncidentsColumnNames.STATUS_COLUMN_NAME)),
                fullTableNameWithLink
        )));
        stringBuilder.append(getBlockQuotedLine(""));

        stringBuilder.append(extractInstantWithFormatting(incidentRow, IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME));
        if(!incidentRow.getString(IncidentsColumnNames.STATUS_COLUMN_NAME).equals(IncidentStatus.open.name())){
            stringBuilder.append(extractInstantWithFormatting(incidentRow, IncidentsColumnNames.LAST_SEEN_COLUMN_NAME));
        }
        stringBuilder.append(extractStringWithFormatting(incidentRow, IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
        stringBuilder.append(extractStringWithFormatting(incidentRow, IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
        stringBuilder.append(String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                readableColumnName(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME),
                RuleSeverityLevel.fromSeverityLevel(
                        incidentRow.getInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME)).name()));
        stringBuilder.append(
                String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                        "Total data quality issues",
                        incidentRow.getInt(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME)));
        stringBuilder.append(extractIntWithFormatting(incidentRow, IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME));

        if (!incidentRow.isMissing(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME)) {
            stringBuilder.append( String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                    readableColumnName(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME),
                    formatToLink(incidentRow.getString(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME), "LINK")));
        }

        stringBuilder.append(extractStringWithFormatting(incidentRow, IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME));
        stringBuilder.append(extractStringWithFormatting(incidentRow, IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME));
        stringBuilder.append(extractStringWithFormatting(incidentRow, IncidentsColumnNames.CHECK_NAME_COLUMN_NAME));

        stringBuilder.append(getBlockQuotedLine(""));

        stringBuilder.append(getBlockQuotedLine(formatToLink(
                prepareUrlToIncident(messageParameters),
                "View in DQOps"
        )));

        return stringBuilder.toString();
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
        stringBuilder.append(getBlockQuotedLine(prepareHeader(notificationMessage.getStatus(),
                fullTableNameWithLink
        )));
        stringBuilder.append(getBlockQuotedLine(""));

        stringBuilder.append(extractInstantWithFormatting(notificationMessage.getFirstSeen(), IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME));
        if(!notificationMessage.getStatus().equals(IncidentStatus.open)){
            stringBuilder.append(extractInstantWithFormatting(notificationMessage.getLastSeen(), IncidentsColumnNames.LAST_SEEN_COLUMN_NAME));
        }
        stringBuilder.append(extractStringWithFormatting(notificationMessage.getQualityDimension(), IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
        stringBuilder.append(extractStringWithFormatting(notificationMessage.getCheckCategory(), IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
        stringBuilder.append(String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                readableColumnName(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME),
                RuleSeverityLevel.fromSeverityLevel(notificationMessage.getHighestSeverity()).name()));
        stringBuilder.append(
                String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                        "Total data quality issues",
                        notificationMessage.getFailedChecksCount()));
        stringBuilder.append(extractIntWithFormatting(notificationMessage.getTablePriority(), IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME));

        if (notificationMessage.getIssueUrl() != null && !notificationMessage.getIssueUrl().isEmpty()) {
            stringBuilder.append( String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                    readableColumnName(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME),
                    formatToLink(notificationMessage.getIssueUrl(), "LINK")));
        }

        stringBuilder.append(extractStringWithFormatting(notificationMessage.getDataGroupName(), IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME));
        stringBuilder.append(extractStringWithFormatting(notificationMessage.getCheckType(), IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME));
        stringBuilder.append(extractStringWithFormatting(notificationMessage.getCheckName(), IncidentsColumnNames.CHECK_NAME_COLUMN_NAME));

        stringBuilder.append(getBlockQuotedLine(""));

        stringBuilder.append(getBlockQuotedLine(formatToLink(
                prepareUrlToIncident(notificationMessage),
                "View in DQOps"
        )));

        return stringBuilder.toString();
    }

    /**
     * Prepares an url to an incident in application UI instance.
     * @param messageParameters Message parameters
     * @return A complete URL to an incident
     */
    private String prepareUrlToIncident(IncidentNotificationMessageParameters messageParameters){
        Row incidentRow = messageParameters.getIncidentRow();
        return instanceCloudLoginService.getReturnBaseUrl()
                + "/incidents/"
                + URLEncoder.encode(messageParameters.getConnectionName(), StandardCharsets.UTF_8) + "/"
                + incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME)
                        .atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId()).getYear() + "/"
                + incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME)
                        .atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId()).getMonthValue() + "/"
                + incidentRow.getString(IncidentsColumnNames.ID_COLUMN_NAME);
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
     * @param messageParameters Message parameters
     * @return A complete URL to table details
     */
    private String prepareUrlToTable(IncidentNotificationMessageParameters messageParameters){
        return instanceCloudLoginService.getReturnBaseUrl()
                + "/sources/connection/" + URLEncoder.encode(messageParameters.getConnectionName(), StandardCharsets.UTF_8)
                + "/schema/" + URLEncoder.encode(messageParameters.getIncidentRow().getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME), StandardCharsets.UTF_8)
                + "/table/" + URLEncoder.encode(messageParameters.getIncidentRow().getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME), StandardCharsets.UTF_8)
                + "/detail";
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
        String commonPart = String.format("in %s table", fullTableName);
        if(incidentStatus.equals(IncidentStatus.open)){
            return "New incident detected " + commonPart + ".";
        }
        return "The incident " + commonPart + " has been " + incidentStatus.name() + ".";
    }

    /**
     * Extracts a string value from selected column for Row object and applies formatting.
     * @param incidentRow Row object
     * @param incidentsColumnName Column name used for extraction
     * @return A formatted string value from selected column. If it does not exist a blank string is returned.
     */
    private String extractStringWithFormatting(Row incidentRow, String incidentsColumnName){
        if (!incidentRow.isMissing(incidentsColumnName)) {
            return String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                    readableColumnName(incidentsColumnName),
                    incidentRow.getString(incidentsColumnName));
        }
        return "";
    }

    /**
     * Extracts a string value from selected column for Row object and applies formatting.
     * @param value String value
     * @param incidentsColumnName Column name used for extraction
     * @return A formatted string value from selected column. If it does not exist a blank string is returned.
     */
    private String extractStringWithFormatting(String value, String incidentsColumnName){
        if (value != null && !value.isEmpty()) {
            return String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                    readableColumnName(incidentsColumnName),
                    value);
        }
        return "";
    }

    /**
     * Extracts a string value of instant from selected column for Row object and applies formatting.
     * @param incidentRow Row object
     * @param incidentsColumnName Column name used for extraction
     * @return A formatted date time with GMT from selected column.
     */
    private String extractInstantWithFormatting(Row incidentRow, String incidentsColumnName){
        Instant instant = incidentRow.getInstant(incidentsColumnName);
        ZonedDateTime zonedDateTime = instant.atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId());
        ZoneOffset zoneOffset = this.defaultTimeZoneProvider.getDefaultTimeZoneId().getRules().getOffset(instant);
        int hours = zoneOffset.getTotalSeconds() / 3600;

        return String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                readableColumnName(incidentsColumnName),
                zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + " (GMT" + (hours > 0 ? "+":"") + hours + ")");
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

        return String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                readableColumnName(incidentsColumnName),
                zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + " (GMT" + (hours > 0 ? "+":"") + hours + ")");
    }

    /**
     * Extracts a string value of int from selected column for Row object and applies formatting.
     * @param incidentRow
     * @param incidentsColumnName
     * @return A formatted int from selected column. If contains a negative value a blank string is returned.
     */
    private String extractIntWithFormatting(Row incidentRow, String incidentsColumnName){
        int value = incidentRow.getInt(incidentsColumnName);
        if(value > 0){
            return String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                    readableColumnName(incidentsColumnName),
                    value);
        }
        return "";
    }

    /**
     * Applies formatting.
     * @param value
     * @param incidentsColumnName
     * @return A formatted int from selected column. If contains a negative value a blank string is returned.
     */
    private String extractIntWithFormatting(int value, String incidentsColumnName){
        if(value > 0){
            return String.format(getBlockQuotedLine(KEY_VALUE_FORMAT),
                    readableColumnName(incidentsColumnName),
                    value);
        }
        return "";
    }

    /**
     * Applies capitalizaiton and removed underscores.
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
     * Formats a line of text to block
     * @param text text to be formatted
     * @return Formatted text
     */
    private String getBlockQuotedLine(String text){
        return "> " + text + NEW_LINE;
    }

    /**
     * Formats the string to a link. Adds special characters: "<" at the beginning, "|" between link and text and ">" at the end.
     * @return Link formatted text
     */
    private String formatToLink(String link, String text){
        return "<" + link + " | " + text + ">";
    }

}
