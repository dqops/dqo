package com.dqops.core.incidents.message;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Data;
import tech.tablesaw.api.Row;

/**
 * Parameter object used for incident notification message.
 */
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
