package ai.dqo.rest.models.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * UI model that returns the form definition and the form data to edit all checks within a single DAMA dimension (category).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIQualityDimensionModel", description = "UI model that returns the form definition and the form data to edit all checks within a single DAMA dimension (category).")
public class UIQualityDimensionModel {
    @JsonPropertyDescription("Data quality dimension name.")
    private String qualityDimension;

    @JsonPropertyDescription("List of data quality checks within the dimension.")
    private List<UICheckModel> checks;
}
