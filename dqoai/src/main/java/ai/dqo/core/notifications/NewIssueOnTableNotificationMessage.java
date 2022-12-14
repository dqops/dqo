package ai.dqo.core.notifications;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Set;

/**
 * Notification message payload that is posted (HTTP POST) to a notification endpoint.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class NewIssueOnTableNotificationMessage {
    private String connection;
    private String schema;
    private String table;
    private Set<String> qualityChecks; // TODO: divide by severity level into three groups
    private Set<String> affectedColumns;
}
