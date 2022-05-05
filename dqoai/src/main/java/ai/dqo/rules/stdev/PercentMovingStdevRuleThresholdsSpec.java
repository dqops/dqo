package ai.dqo.rules.stdev;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.rules.TimeWindowedRule;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Rule thresholds for a current value that is between the moving average range by X percent.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class PercentMovingStdevRuleThresholdsSpec extends AbstractRuleThresholdsSpec<PercentMovingStdevRuleParametersSpec> implements TimeWindowedRule {

    private static final ChildHierarchyNodeFieldMapImpl<PercentMovingStdevRuleThresholdsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleThresholdsSpec.FIELDS) {
        {

        }
    };

    @JsonPropertyDescription("Rule threshold for a high severity (3) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingStdevRuleParametersSpec high;

    @JsonPropertyDescription("Rule threshold for a medium severity (2) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingStdevRuleParametersSpec medium;

    @JsonPropertyDescription("Rule threshold for a medium severity (1) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingStdevRuleParametersSpec low;

    //TODO: !!!Add comments!!!
    /**
     *
     * @return
     */
    @Override
    public PercentMovingStdevRuleParametersSpec getHigh() {
        return high;
    }

    /**
     *
     * @return
     */
    @Override
    public PercentMovingStdevRuleParametersSpec getMedium() {
        return medium;
    }

    /**
     *
     * @return
     */
    @Override
    public PercentMovingStdevRuleParametersSpec getLow() {
        return low;
    }

    /**
     *
     * @param high
     */
    public void setHigh(PercentMovingStdevRuleParametersSpec high) {
        this.setDirtyIf(!Objects.equals(this.high, high));
        this.high = high;
        propagateHierarchyIdToField(high, "high");
    }

    /**
     *
     * @param medium
     */
    public void setMedium(PercentMovingStdevRuleParametersSpec medium) {
        this.setDirtyIf(!Objects.equals(this.medium, medium));
        this.medium = medium;
        propagateHierarchyIdToField(medium, "medium");
    }

    /**
     *
     * @param low
     */
    public void setLow(PercentMovingStdevRuleParametersSpec low) {
        this.setDirtyIf(!Objects.equals(this.low, low));
        this.low = low;
        propagateHierarchyIdToField(low, "low");
    }

    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }
}
