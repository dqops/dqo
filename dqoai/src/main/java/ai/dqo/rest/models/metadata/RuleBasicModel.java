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

    @JsonPropertyDescription("This rule has a custom (user level) definition.")
    public boolean custom;

    public RuleBasicModel(){}

    public RuleBasicModel(String ruleName, boolean custom) {
        this.ruleName = ruleName;
        this.custom = custom;
    }

    public String getRuleName() {
        return ruleName;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

}
