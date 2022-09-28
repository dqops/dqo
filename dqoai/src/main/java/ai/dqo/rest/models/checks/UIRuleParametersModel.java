package ai.dqo.rest.models.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * UI model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIRuleParametersModel", description = "UI model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).")
public class UIRuleParametersModel {
    @JsonPropertyDescription("List of fields for editing the rule parameters like thresholds.")
    private List<UIFieldModel> ruleParameters;

    @JsonPropertyDescription("Disable the rule. The rule will not be evaluated. The sensor will also not be executed if it has no enabled rules.")
    private boolean disable;
}
