package ai.dqo.rest.models.metadata;

import ai.dqo.metadata.sources.ConnectionSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Connection model returned by the rest api.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ConnectionModel", description = "Full connection model")
public class ConnectionModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Connection hash that identifies the connection using a unique hash code.")
    private Long connectionHash;

    @JsonPropertyDescription("Full connection specification, including all nested objects (but not a list of tables).")
    private ConnectionSpec spec;
}
