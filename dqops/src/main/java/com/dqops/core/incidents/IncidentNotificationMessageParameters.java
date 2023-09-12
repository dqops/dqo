package com.dqops.core.incidents;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Data;
import tech.tablesaw.api.Row;

@Data
@Builder
public class IncidentNotificationMessageParameters {

    /**
     * Incident row - a row from the Incident's Tablesaw row.
     */
    @JsonPropertyDescription("Incident row - a row from the Incident's Tablesaw row.")
    private Row incidentRow;

    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

}
