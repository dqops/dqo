package ai.dqo.rest.models;

import ai.dqo.metadata.sources.ConnectionSpec;
import lombok.Data;

/**
 * Connection model returned by the rest api.
 */
@Data
public class ConnectionModel {
    private String name;
    private ConnectionSpec spec;
}
