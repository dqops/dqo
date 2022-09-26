package ai.dqo.rest.models.platform;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Object mapped to the default spring error payload (key/values).
 */
@Data
@ApiModel(value = "SpringErrorPayload", description = "Spring error payload that identifies the fields in the error returned by the REST API in case of unexpected errors (exceptions).")
public class SpringErrorPayload {
    @JsonPropertyDescription("Error timestamp as an epoch timestamp.")
    public Long timestamp;

    @JsonPropertyDescription("Optional status code.")
    public Integer status;

    @JsonPropertyDescription("Error name.")
    public String error;

    @JsonPropertyDescription("Optional exception.")
    public String exception;

    @JsonPropertyDescription("Exception's message.")
    public String message;

    @JsonPropertyDescription("Exception's stack trace (optional).")
    public String path;
}
