package ai.dqo.rest.models.checks;

import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;

/**
 * Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIFieldModel", description = "Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.")
public class UIFieldModel {
    @JsonPropertyDescription("Field name that matches the field name (snake_case) used in the YAML specification.")
    private ParameterDefinitionSpec definition;

    @JsonPropertyDescription("Field value for a string field.")
    private String stringValue;

    @JsonPropertyDescription("Field value for a boolean field.")
    private Boolean booleanValue;

    @JsonPropertyDescription("Field value for an integer (32-bit) field.")
    private Integer integerValue;

    @JsonPropertyDescription("Field value for a long (64-bit) field.")
    private Long longValue;

    @JsonPropertyDescription("Field value for a double field.")
    private Double doubleValue;

    @JsonPropertyDescription("Field value for a datetime field.")
    private Instant dateTimeValue;

    @JsonPropertyDescription("Field value for a column name field.")
    private String columnNameValue;

    @JsonPropertyDescription("Field value for an enum (choice) field.")
    private String enumValue;
}
