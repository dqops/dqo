package ai.dqo.rest.models;

import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import lombok.Data;

/**
 * Table model that returns the specification of a single column in the REST Api.
 */
@Data
public class ColumnModel {
    private String connectionName;
    private PhysicalTableName table;
    private String columnName;
    private ColumnSpec spec;
}
