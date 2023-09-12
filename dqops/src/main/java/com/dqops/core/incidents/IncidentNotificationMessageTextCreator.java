package com.dqops.core.incidents;

import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import tech.tablesaw.api.Row;

import java.time.ZoneId;

public class IncidentNotificationMessageTextCreator {

    private static final String boldedKeySimpleValueString = "*%s*: %s";
    private static final String newLine = " \n";

    public static String prepareText(IncidentNotificationMessageParameters messageParameters){

        Row incidentRow = messageParameters.getIncidentRow();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prepareHeaderMessage(IncidentStatus.valueOf(incidentRow.getString(IncidentsColumnNames.STATUS_COLUMN_NAME))));
        stringBuilder.append(newLine);

        stringBuilder.append(String.format(getBlockQuotedLine(boldedKeySimpleValueString),
                "Connection name",
                messageParameters.getConnectionName()));

        stringBuilder.append(getBlockQuotedLine(
                String.format(boldedKeySimpleValueString,
                        "Full table name",
                        "<" + getUrlToFullTableName(messageParameters, incidentRow)
                                + "|" + incidentRow.getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME)
                                + "."
                                + incidentRow.getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME) + ">")
        ));

        stringBuilder.append(nameValueCreatorForInt(incidentRow, IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME));
        stringBuilder.append(nameValueCreatorForInstant(incidentRow, IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME));
        stringBuilder.append(nameValueCreatorForInstant(incidentRow, IncidentsColumnNames.LAST_SEEN_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.CHECK_NAME_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForString(incidentRow, IncidentsColumnNames.ISSUE_URL_COLUMN_NAME));
        }
        stringBuilder.append(nameValueCreatorForInt(incidentRow, IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME)) {
            stringBuilder.append(nameValueCreatorForInt(incidentRow, IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME));
        }

        stringBuilder.append(newLine);

        stringBuilder.append(">>> "
                + "<" + getUrlToIncident(messageParameters, incidentRow)
                + "| *View issue in DQOps*>");
        stringBuilder.append(newLine);

        return stringBuilder.toString();
    }

    private static String getUrlToIncident(IncidentNotificationMessageParameters messageParameters,
                                           Row incidentRow){
        return messageParameters.getBaseUrlOfDqoInstance()
                + "incidents/"
                + messageParameters.getConnectionName() + "/"
                + incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).atZone(ZoneId.systemDefault()).getYear() + "/"
                + incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME).atZone(ZoneId.systemDefault()).getMonthValue() + "/"
                + incidentRow.getString(IncidentsColumnNames.ID_COLUMN_NAME);
    }

    private static String getUrlToFullTableName(IncidentNotificationMessageParameters messageParameters,
                                                Row incidentRow){
        return messageParameters.getBaseUrlOfDqoInstance()
                + "sources/connection/" + messageParameters.getConnectionName()
                + "/schema/" + incidentRow.getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME)
                + "/table/" + incidentRow.getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME) + "/detail";
    }

    private static String prepareHeaderMessage(IncidentStatus incidentStatus){
        switch (incidentStatus){
            case open:
                return "*New data quality issue detected*";
            case acknowledged:
                return "*New data quality issue detected*";
            case resolved:
                return "*New data quality issue detected*";
            case muted:
                return "*New data quality issue detected*";
        }
        return "";
    }

    private static String nameValueCreatorForString(Row incidentRow, String incidentsColumnName){
        return String.format(getBlockQuotedLine(boldedKeySimpleValueString),
                readableColumnName(incidentsColumnName),
                incidentRow.getString(incidentsColumnName));
    }

    private static String nameValueCreatorForInstant(Row incidentRow, String incidentsColumnName){
        return String.format(getBlockQuotedLine(boldedKeySimpleValueString),
                readableColumnName(incidentsColumnName),
                incidentRow.getInstant(incidentsColumnName).atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId().getRules().getOffset(timePeriod)).toInstant(););
    }

    private static String nameValueCreatorForInt(Row incidentRow, String incidentsColumnName){
        return String.format(getBlockQuotedLine(boldedKeySimpleValueString),
                readableColumnName(incidentsColumnName),
                incidentRow.getInt(incidentsColumnName));
    }

    private static String nameValueCreatorForLong(Row incidentRow, String incidentsColumnName){
        return String.format(getBlockQuotedLine(boldedKeySimpleValueString),
                readableColumnName(incidentsColumnName),
                incidentRow.getLong(incidentsColumnName));
    }

    private static String readableColumnName(String columnName){
        return columnName.substring(0, 1).toUpperCase()
                + columnName.substring(1)
                        .replace("_"," ");
    }

    private static String getBlockQuotedLine(String text){
        return "> " + text + newLine;
    }
}
