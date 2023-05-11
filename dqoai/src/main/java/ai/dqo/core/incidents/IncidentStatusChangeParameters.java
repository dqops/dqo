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

import ai.dqo.data.incidents.factory.IncidentStatus;
import ai.dqo.metadata.incidents.IncidentGroupingSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

/**
 * Parameter object which identifies a data quality incident and a new incident status that should be assigned to the incident.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
@ToString
public class IncidentStatusChangeParameters {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * The four digit year when the incident was first seen. Incidents are partitioned monthly.
     */
    @JsonPropertyDescription("The four digit year when the incident was first seen. Incidents are partitioned monthly.")
    private int firstSeenYear;

    /**
     * The month (1..12) when the incident was first seen. Incidents are partitioned monthly.
     */
    @JsonPropertyDescription("The month (1..12) when the incident was first seen. Incidents are partitioned monthly.")
    private int firstSeenMonth;

    /**
     * Incident unique id.
     */
    @JsonPropertyDescription("Incident unique id.")
    private String incidentId;

    /**
     * New incident status to be assigned to an incident. Activates a new notification if a status is changed.
     */
    @JsonPropertyDescription("New incident status to be assigned to an incident. Activates a new notification if a status is changed.")
    private IncidentStatus newIncidentStatus;

    /**
     * Incident grouping configuration retrieved from the connection.
     */
    @JsonPropertyDescription("Incident grouping configuration retrieved from the connection.")
    private IncidentGroupingSpec incidentGrouping;

    public IncidentStatusChangeParameters() {
    }

    public IncidentStatusChangeParameters(String connectionName,
                                          int firstSeenYear,
                                          int firstSeenMonth,
                                          String incidentId,
                                          IncidentStatus newIncidentStatus,
                                          IncidentGroupingSpec incidentGrouping ) {
        this.connectionName = connectionName;
        this.firstSeenYear = firstSeenYear;
        this.firstSeenMonth = firstSeenMonth;
        this.incidentId = incidentId;
        this.newIncidentStatus = newIncidentStatus;
        this.incidentGrouping = incidentGrouping;
    }
}
