/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.core.incidents;

import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;
import tech.tablesaw.api.Row;

import java.time.Instant;

/**
 * Notification message payload that is posted (HTTP POST) to a notification endpoint with the details of a new or updated data quality incident.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@ToString
public class IncidentNotificationMessage {
    /**
     * Incident id (primary key).
     */
    @JsonPropertyDescription("Incident ID - the primary key that identifies each data quality incident.")
    private String incidentId;

    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name affected by a data quality incident.")
    private String connection;

    /**
     * Schema name affected by a data quality incident.
     */
    @JsonPropertyDescription("Schema name affected by a data quality incident.")
    private String schema;

    /**
     * Table name affected by a data quality incident.
     */
    @JsonPropertyDescription("Table name affected by a data quality incident.")
    private String table;

    /**
     * Table priority of the table that was affected by a data quality incident.
     */
    @JsonPropertyDescription("Table priority of the table that was affected by a data quality incident.")
    private Integer tablePriority;

    /**
     * Data quality incident hash that identifies similar incidents on the same incident grouping level.
     */
    @JsonPropertyDescription("Data quality incident hash that identifies similar incidents on the same incident grouping level.")
    private Long incidentHash;

    /**
     * The UTC timestamp when the data quality incident was first seen.
     */
    @JsonPropertyDescription("The UTC timestamp when the data quality incident was first seen.")
    private Instant firstSeen;

    /**
     * The UTC timestamp when the data quality incident was last seen.
     */
    @JsonPropertyDescription("The UTC timestamp when the data quality incident was last seen.")
    private Instant lastSeen;

    /**
     * The UTC timestamp when the data quality incident is valid until. All new failed data quality check results until that date will be included in this incident, unless the incident status is changed to resolved, so a new incident must be created.
     */
    @JsonPropertyDescription("The UTC timestamp when the data quality incident is valid until. All new failed data quality check results until that date will be included in this incident, unless the incident status is changed to resolved, so a new incident must be created.")
    private Instant incidentUntil;

    /**
     * The data stream name that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data group name that was affected by a data quality incident. " +
            "The data group names are created from the values of columns and tags configured in the data grouping configuration. " +
            "An example data group when grouping a static tag \"customers\"  as the first level grouping and a *country* column value for the second grouping level is " +
            "*customers / UK*.")
    private String dataGroupName;

    /**
     * The data quality dimension that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data quality dimension that was affected by a data quality incident.")
    private String qualityDimension;

    /**
     * The data quality check category that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data quality check category that was affected by a data quality incident.")
    private String checkCategory;

    /**
     * The data quality check type that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data quality check type that was affected by a data quality incident.")
    private String checkType;

    /**
     * The data quality check name that was affected by a data quality incident.
     */
    @JsonPropertyDescription("The data quality check name that was affected by a data quality incident.")
    private String checkName;

    /**
     * The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.
     */
    @JsonPropertyDescription("The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.")
    private int highestSeverity;

    /**
     * The total number of failed data quality checks that were seen when the incident was raised for the first time.
     */
    @JsonPropertyDescription("The total number of failed data quality checks that were seen when the incident was raised for the first time.")
    private int failedChecksCount;

    /**
     * The link (url) to a ticket in an external system that is tracking this incident.
     */
    @JsonPropertyDescription("The link (url) to a ticket in an external system that is tracking this incident.")
    public String issueUrl;

    /**
     * Incident status.
     */
    @JsonPropertyDescription("Incident status.")
    private IncidentStatus status;

    /**
     * Notification text in Markdown format that contains the most important fields from the class.
     */
    @JsonPropertyDescription("Notification text in Markdown format that contains the most important fields from the class.")
    private String text;

    /**
     * Creates a new incident notification message from a single row of the "incidents" table. The column names are defined in {@link com.dqops.data.incidents.factory.IncidentsColumnNames} class.
     * @param messageParameters Incident notification message parameters with Tablesaw row.
     * @return Incident notification message.
     */
    public static IncidentNotificationMessage fromIncidentRow(
            IncidentNotificationMessageParameters messageParameters,
            IncidentNotificationMessageTextCreator textCreator) {

        Row incidentRow = messageParameters.getIncidentRow();

        IncidentNotificationMessage message = new IncidentNotificationMessage();
        message.setIncidentId(incidentRow.getString(IncidentsColumnNames.ID_COLUMN_NAME));
        message.setConnection(messageParameters.getConnectionName());
        message.setSchema(incidentRow.getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME));
        message.setTable(incidentRow.getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME)) {
            message.setTablePriority(incidentRow.getInt(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME));
        }
        message.setIncidentHash(incidentRow.getLong(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME));
        message.setFirstSeen(incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME));
        message.setLastSeen(incidentRow.getInstant(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME));
        message.setIncidentUntil(incidentRow.getInstant(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME)) {
            message.setDataGroupName(incidentRow.getString(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME)) {
            message.setQualityDimension(incidentRow.getString(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME)) {
            message.setCheckCategory(incidentRow.getString(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME)) {
            message.setCheckType(incidentRow.getString(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME)) {
            message.setCheckName(incidentRow.getString(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME)) {
            message.setIssueUrl(incidentRow.getString(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME));
        }
        message.setHighestSeverity(incidentRow.getInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME));
        message.setFailedChecksCount(incidentRow.getInt(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME));
        message.setStatus(IncidentStatus.valueOf(incidentRow.getString(IncidentsColumnNames.STATUS_COLUMN_NAME)));

        message.setText(textCreator.prepareText(messageParameters));

        return message;
    }

    @Override
    public String toString() {
        return "IncidentNotificationMessage{" +
                "incidentId='" + incidentId + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
