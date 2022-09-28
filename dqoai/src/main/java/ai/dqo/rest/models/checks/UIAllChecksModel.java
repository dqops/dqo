package ai.dqo.rest.models.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * UI model that returns the form definition and the form data to edit all data quality checks divided by dimensions.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIAllChecksModel", description = "UI model that returns the form definition and the form data to edit all data quality checks divided by dimensions.")
public class UIAllChecksModel {
    @JsonPropertyDescription("List of all data quality dimensions that contain data quality checks inside.")
    private List<UIQualityDimensionModel> qualityDimensions;
}
