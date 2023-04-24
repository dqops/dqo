/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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

package ai.dqo.data.incidents.services.models;

import ai.dqo.data.incidents.factory.IncidentStatus;
import ai.dqo.data.incidents.factory.IncidentsColumnNames;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import tech.tablesaw.api.Row;

import java.time.Instant;
import java.time.temporal.ChronoField;

/**
 * Data quality incident model shown on an incident details screen.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentModel {
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
     * The year when the incident was first seen. This value is required to load an incident's monthly partition.
     */
    @JsonPropertyDescription("The year when the incident was first seen. This value is required to load an incident's monthly partition.")
    private int year;

    /**
     * The month when the incident was first seen. This value is required to load an incident's monthly partition.
     */
    @JsonPropertyDescription("The month when the incident was first seen. This value is required to load an incident's monthly partition.")
    private int month;

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
    @JsonPropertyDescription("The data stream name that was affected by a data quality incident.")
    private String dataStreamName;

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
     * Incident status.
     */
    @JsonPropertyDescription("Incident status.")
    private IncidentStatus status;

    /**
     * Creates a new incident notification message from a single row of the "incidents" table. The column names are defined in {@link IncidentsColumnNames} class.
     * @param incidentRow Incident row - a row from the Incident's Tablesaw row.
     * @param connectionName Connection name.
     * @return Incident list model instance.
     */
    public static IncidentModel fromIncidentRow(Row incidentRow, String connectionName) {
        IncidentModel model = new IncidentModel();
        model.setIncidentId(incidentRow.getString(IncidentsColumnNames.ID_COLUMN_NAME));
        model.setConnection(connectionName);
        model.setSchema(incidentRow.getString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME));
        model.setTable(incidentRow.getString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME)) {
            model.setTablePriority(incidentRow.getInt(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME));
        }
        model.setIncidentHash(incidentRow.getLong(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME));
        Instant firstSeen = incidentRow.getInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);
        model.setFirstSeen(firstSeen);
        model.setYear(firstSeen.get(ChronoField.YEAR));
        model.setMonth(firstSeen.get(ChronoField.MONTH_OF_YEAR));
        model.setLastSeen(incidentRow.getInstant(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME));
        model.setIncidentUntil(incidentRow.getInstant(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME));
        if (!incidentRow.isMissing(IncidentsColumnNames.DATA_STREAM_NAME_COLUMN_NAME)) {
            model.setDataStreamName(incidentRow.getString(IncidentsColumnNames.DATA_STREAM_NAME_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME)) {
            model.setQualityDimension(incidentRow.getString(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME)) {
            model.setCheckCategory(incidentRow.getString(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME)) {
            model.setCheckType(incidentRow.getString(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME));
        }
        if (!incidentRow.isMissing(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME)) {
            model.setCheckName(incidentRow.getString(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME));
        }
        model.setHighestSeverity(incidentRow.getInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME));
        model.setFailedChecksCount(incidentRow.getInt(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME));
        model.setStatus(IncidentStatus.valueOf(incidentRow.getString(IncidentsColumnNames.STATUS_COLUMN_NAME)));

        return model;
    }
}
