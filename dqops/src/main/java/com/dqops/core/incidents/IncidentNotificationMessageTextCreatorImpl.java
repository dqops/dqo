package com.dqops.core.incidents;

import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class IncidentNotificationMessageTextCreatorImpl implements IncidentNotificationMessageTextCreator {

    private final String keyValueFormat = "*%s*: %s";
    private final String newLine = " \n";

    private final InstanceCloudLoginService instanceCloudLoginService;
    private final DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * @param instanceCloudLoginService Instance cloud login service.
     * @param defaultTimeZoneProvider
     */
    @Autowired
    public IncidentNotificationMessageTextCreatorImpl(InstanceCloudLoginService instanceCloudLoginService,
                                                      DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.instanceCloudLoginService = instanceCloudLoginService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    public String prepareText(IncidentNotificationMessageParameters messageParameters){

        Row incidentRow = messageParameters.getIncidentRow();

        String fullTableNameWithLink = "<" + getUrlToFullTableName(messageParameters, incidentRow)
                + "|" + incidentRow.getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME)
                + "."
                + incidentRow.getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME) + ">";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBlockQuotedLine(prepareHeaderMessage(
                IncidentStatus.valueOf(incidentRow.getString(IncidentsColumnNames.STATUS_COLUMN_NAME)),
                fullTableNameWithLink
        )));
        stringBuilder.append(getBlockQuotedLine(""));

        stringBuilder.append(nameValueCreatorForInstant(incidentRow, IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME));
        if(!incidentRow.getString(IncidentsColumnNames.STATUS_COLUMN_NAME).equals(IncidentStatus.open.name())){
            stringBuilder.append(nameValueCreatorForInstant(incidentRow, IncidentsColumnNames.LAST_SEEN_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
        }

        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
        }
        String.format(getBlockQuotedLine(keyValueFormat),
                readableColumnName(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME),
                RuleSeverityLevel.fromSeverityLevel(
                        incidentRow.getInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME)).name()
        );
        stringBuilder.append(nameValueCreatorForInt(incidentRow, IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME));


        if (!incidentRow.isMissing(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForInt(incidentRow, IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.ISSUE_URL_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.CHECK_NAME_COLUMN_NAME));
        }

        stringBuilder.append(getBlockQuotedLine(""));

        stringBuilder.append("> "
                + "<" + getUrlToIncident(messageParameters, incidentRow)
                + "| *View in DQOps*>");
        stringBuilder.append(newLine);

        return stringBuilder.toString();
    }

    private String getUrlToIncident(IncidentNotificationMessageParameters messageParameters, Row incidentRow){
        return instanceCloudLoginService.getReturnBaseUrl()
                + "incidents/"
                + messageParameters.getConnectionName() + "/"
                + incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME)
                        .atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId()).getYear() + "/"
                + incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME)
                        .atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId()).getMonthValue() + "/"
                + incidentRow.getString(IncidentsColumnNames.ID_COLUMN_NAME);
    }

    private String getUrlToFullTableName(IncidentNotificationMessageParameters messageParameters, Row incidentRow){
        return instanceCloudLoginService.getReturnBaseUrl()
                + "sources/connection/" + messageParameters.getConnectionName()
                + "/schema/" + incidentRow.getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME)
                + "/table/" + incidentRow.getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME) + "/detail";
    }

    private String prepareHeaderMessage(IncidentStatus incidentStatus,
                                        String fullTableName){
        String commonPart = String.format("in %s table", fullTableName);
        if(incidentStatus.equals(IncidentStatus.open)){
            return "*New incident detected " + commonPart + ".*";
        }
        return "*The incident " + commonPart + " has been " + incidentStatus.name() + ".*";
    }

    private String nameValueCreatorForString(Row incidentRow, String incidentsColumnName){
        return String.format(getBlockQuotedLine(keyValueFormat),
                readableColumnName(incidentsColumnName),
                incidentRow.getString(incidentsColumnName));
    }

    private String nameValueCreatorForInstant(Row incidentRow, String incidentsColumnName){
        Instant instant = incidentRow.getInstant(incidentsColumnName);
        ZonedDateTime zonedDateTime = instant.atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId());
        ZoneOffset zoneOffset = this.defaultTimeZoneProvider.getDefaultTimeZoneId().getRules().getOffset(instant);
        int hours = zoneOffset.getTotalSeconds() / 3600;

        return String.format(getBlockQuotedLine(keyValueFormat),
                readableColumnName(incidentsColumnName),
                zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + " (GTM" + (hours > 0 ? "+":"") + hours + ")");
    }

    private String nameValueCreatorForInt(Row incidentRow, String incidentsColumnName){
        return String.format(getBlockQuotedLine(keyValueFormat),
                readableColumnName(incidentsColumnName),
                incidentRow.getInt(incidentsColumnName));
    }

    private String readableColumnName(String columnName){
        return columnName.substring(0, 1).toUpperCase()
                + columnName.substring(1)
                        .replace("_"," ");
    }

    private String getBlockQuotedLine(String text){
        return "> " + text + newLine;
    }
}
