package ai.dqo.rest.models.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Rule basic model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "RuleBasicModel", description = "Rule basic model")
public class RuleBasicModel {

    @JsonPropertyDescription("Rule name")
    private String ruleName;

    @JsonPropertyDescription("Full rule name")
    private String fullRuleName;

    @JsonPropertyDescription("This rule has is a built-in rule.")
    public boolean custom;

    public RuleBasicModel(){}

}
