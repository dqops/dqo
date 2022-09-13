package ai.dqo.rest.models;

import lombok.Data;

/**
 * Schema model that is returned by the REST API. Describes a single unique schema name.
 */
@Data
public class SchemaModel {
    private String connectionName;
    private String schemaName;
}
