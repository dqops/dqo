package ai.dqo.rest.models.metadata;

import ai.dqo.metadata.sources.TableSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Full table model that returns the specification of a single table in the REST Api.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableModel", description = "Full table model, including all nested objects like columns or checks.")
public class TableModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Table hash that identifies the table using a unique hash code.")
    private Long tableHash;

    @JsonPropertyDescription("Full table specification including all nested information, the table name is inside the 'target' property.")
    private TableSpec spec;
}
