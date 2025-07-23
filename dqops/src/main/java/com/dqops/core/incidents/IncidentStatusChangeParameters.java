/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.incidents;

import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
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
    private ConnectionIncidentGroupingSpec incidentGrouping;

    public IncidentStatusChangeParameters() {
    }

    public IncidentStatusChangeParameters(String connectionName,
                                          int firstSeenYear,
                                          int firstSeenMonth,
                                          String incidentId,
                                          IncidentStatus newIncidentStatus,
                                          ConnectionIncidentGroupingSpec incidentGrouping ) {
        this.connectionName = connectionName;
        this.firstSeenYear = firstSeenYear;
        this.firstSeenMonth = firstSeenMonth;
        this.incidentId = incidentId;
        this.newIncidentStatus = newIncidentStatus;
        this.incidentGrouping = incidentGrouping;
    }
}
