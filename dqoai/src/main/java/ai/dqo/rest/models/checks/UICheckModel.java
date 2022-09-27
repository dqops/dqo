package ai.dqo.rest.models.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * UI model that returns the form definition and the form data to edit a single data quality check.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UICheckModel", description = "UI model that returns the form definition and the form data to edit a single data quality check.")
public class UICheckModel {
    @JsonPropertyDescription("List of fields for editing the sensor parameters.")
    private List<UIFieldModel> sensorParameters;

    // TODO: list of rules and additional information like overridden time series or dimensions
}
