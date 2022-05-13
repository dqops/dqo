package ai.dqo.rules.stdev;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.rules.TimeWindowedRule;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
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
public class PercentileMovingStdevRuleThresholdsSpec extends AbstractRuleThresholdsSpec<PercentileMovingStdevRuleParametersSpec> implements TimeWindowedRule {

    private static final ChildHierarchyNodeFieldMapImpl<PercentileMovingStdevRuleThresholdsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleThresholdsSpec.FIELDS) {
        {

        }
    };

    @JsonPropertyDescription("Rule threshold for a high severity (3) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentileMovingStdevRuleParametersSpec high;

    @JsonPropertyDescription("Rule threshold for a medium severity (2) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentileMovingStdevRuleParametersSpec medium;

    @JsonPropertyDescription("Rule threshold for a medium severity (1) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentileMovingStdevRuleParametersSpec low;

    /**
     * Alerting rules configuration that raise "HIGH" severity alerts for unsatisfied rules.
     * @return High severity alert rule parameters.
     */
    @Override
    public PercentileMovingStdevRuleParametersSpec getHigh() {
        return high;
    }

    /**
     * Alerting rules configuration that raise "MEDIUM" severity alerts for unsatisfied rules.
     * @return Medium severity alert rule parameters.
     */
    @Override
    public PercentileMovingStdevRuleParametersSpec getMedium() {
        return medium;
    }

    /**
     * Alerting rules configuration that raise "LOW" severity alerts for unsatisfied rules.
     * @return High severity alert rule parameters.
     */
    @Override
    public PercentileMovingStdevRuleParametersSpec getLow() {
        return low;
    }

    /**
     * Sets a High severity alert rule set.
     * @param high High severity alert rules.
     */
    public void setHigh(PercentileMovingStdevRuleParametersSpec high) {
        this.setDirtyIf(!Objects.equals(this.high, high));
        this.high = high;
        propagateHierarchyIdToField(high, "high");
    }

    /**
     * Sets a MEDIUM severity alert rule set.
     * @param medium Medium severity alert rules.
     */
    public void setMedium(PercentileMovingStdevRuleParametersSpec medium) {
        this.setDirtyIf(!Objects.equals(this.medium, medium));
        this.medium = medium;
        propagateHierarchyIdToField(medium, "medium");
    }

    /**
     * Sets a LOW severity alert rule set.
     * @param low Low severity alert rules.
     */
    public void setLow(PercentileMovingStdevRuleParametersSpec low) {
        this.setDirtyIf(!Objects.equals(this.low, low));
        this.low = low;
        propagateHierarchyIdToField(low, "low");
    }

    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }
}
