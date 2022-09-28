package ai.dqo.rest.models.checks;

import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * UI model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIRuleThresholdsModel", description = "UI model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).")
public class UIRuleThresholdsModel {
    /**
     * Field name that matches the field name (snake_case) used in the YAML specification.
     */
    @JsonPropertyDescription("Rule field name (snake_case) used in the YAML specification to configure the rule in the code.")
    private String fieldName;

    @JsonPropertyDescription("Field display name that should be shown as a user friendly name of the rule.")
    private String displayName;

    @JsonPropertyDescription("Help text (full description) that will be shown to the user as a hint when the user clicks a help (question mark) icon.")
    private String helpHext;

    @JsonPropertyDescription("Relative path to the rule definition (a python file name) within the rules folder in the DQO Home or User Home.")
    private String rulePath;

    @JsonPropertyDescription("List of fields for editing the rule parameters like thresholds.")
    private List<UIFieldModel> ruleParameters;

    @JsonPropertyDescription("Time window configuration for rules that require historic data for evaluation. The time window is configured as the number of previous time periods that are required to evaluate a sensor. The time period granularity (day, hour, etc.) is configured as a time_series configuration on the sensor.")
    private RuleTimeWindowSettingsSpec timeWindow;

    @JsonPropertyDescription("Disable the rule at all severity levels.")
    private boolean disable;
}
