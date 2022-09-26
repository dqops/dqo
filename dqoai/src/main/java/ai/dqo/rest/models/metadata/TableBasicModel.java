package ai.dqo.rest.models.metadata;

import ai.dqo.metadata.sources.TableOwnerSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableTargetSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Table basic model returned by the rest api that is limited only to the basic fields, excluding nested nodes.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableBasicModel", description = "Basic table model with a subset of parameters, excluding all nested objects.")
public class TableBasicModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Physical table details (a physical schema name and a physical table name)")
    private TableTargetSpec target = new TableTargetSpec();

    @JsonPropertyDescription("Disables all data quality checks on the table. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("Stage name.")
    private String stage;

    @JsonPropertyDescription("SQL WHERE clause added to the sensor queries.")
    private String filter;

    @JsonPropertyDescription("Table owner information like the data steward name or the business application name.")
    private TableOwnerSpec owner;

    /**
     * Creates a basic table model from a table specification by cherry-picking relevant fields.
     * @param connectionName Connection name to store in the model.
     * @param tableSpec      Source table specification.
     * @return Basic table model.
     */
    public static TableBasicModel fromTableSpecification(String connectionName, TableSpec tableSpec) {
        return new TableBasicModel() {{
            setConnectionName(connectionName);
            setTarget(tableSpec.getTarget());
            setDisabled(tableSpec.isDisabled());
            setStage(tableSpec.getStage());
            setFilter(tableSpec.getFilter());
            setOwner(tableSpec.getOwner());
        }};
    }

    /**
     * Updates a table specification by copying all fields. The {@see TableSpec#target field is not updated}.
     * @param targetTableSpec Target table specification to update.
     */
    public void copyToTableSpecification(TableSpec targetTableSpec) {
        targetTableSpec.setDisabled(this.isDisabled());
        targetTableSpec.setStage(this.getStage());
        targetTableSpec.setFilter(this.getFilter());
        targetTableSpec.setOwner(this.getOwner());
    }
}
