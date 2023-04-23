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
package ai.dqo.core.incidents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

/**
 * Notification message payload that is posted (HTTP POST) to a notification endpoint with the details of a new data quality incident.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class NewIncidentNotificationMessage {
    /**
     * Row index in the Tablesaw table with row indexes. Used to find the total count of failed checks in the incident, because there could be more failed checks in the incident.
     */
    @JsonIgnore
    private int newIncidentRowIndex;

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
     * The UTC timestamp when the data quality incident was first seen
     */
    @JsonPropertyDescription("The UTC timestamp when the data quality incident was first seen.")
    private Instant firstSeen;

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
}
