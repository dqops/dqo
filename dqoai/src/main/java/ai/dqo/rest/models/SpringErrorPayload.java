package ai.dqo.rest.models;

import lombok.Data;

/**
 * Object mapped to the default spring error payload (key/values).
 */
@Data
public class SpringErrorPayload {
    public long timestamp;
    public int status;
    public String error;
    public String exception;
    public String message;
    public String path;
}
