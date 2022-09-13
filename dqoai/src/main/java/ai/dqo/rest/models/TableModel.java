package ai.dqo.rest.models;

import ai.dqo.metadata.sources.TableSpec;
import lombok.Data;

/**
 * Table model that returns the specification of a single table in the REST Api.
 */
@Data
public class TableModel {
    private String connectionName;
    private TableSpec spec;
}
