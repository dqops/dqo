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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

/**
 * Parameter object which identifies a data quality incident and a new issue url that should be stored in the incident.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
@ToString
public class IncidentIssueUrlChangeParameters {
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
     * New incident issueUrl value to be saved.
     */
    @JsonPropertyDescription("New incident issueUrl value to be saved.")
    private String newIssueUrl;

    public IncidentIssueUrlChangeParameters() {
    }

    public IncidentIssueUrlChangeParameters(String connectionName,
                                            int firstSeenYear,
                                            int firstSeenMonth,
                                            String incidentId,
                                            String newIssueUrl) {
        this.connectionName = connectionName;
        this.firstSeenYear = firstSeenYear;
        this.firstSeenMonth = firstSeenMonth;
        this.incidentId = incidentId;
        this.newIssueUrl = newIssueUrl;
    }
}
