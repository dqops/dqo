        package ai.dqo.rules.stdev;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.AbstractRuleParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if a data quality sensor reading value is not above X percent of the moving average of a time window.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BelowStdevMultiply30DaysRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<BelowStdevMultiply30DaysRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Maximum percent (e.q. 3%) that the current sensor reading could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readings must exist to run the calculation.")
    private Double stdevMultiplierBelow;

    /**
     * Default constructor.
     */
    public BelowStdevMultiply30DaysRuleParametersSpec() {
        this.stdevMultiplierBelow = null;
    }

    /**
     * Multipled factor used to calculate a multipled stdev.
     * @return Multiple factor used to calculate a multipled stdev.
     */
    public Double getStdevMultiplierBelow() {
        return stdevMultiplierBelow;
    }

    /**
     * Sets multiple factor to caulculate multipled stdev.
     * @param stdevMultiplierBelow Multiple factor.
     */
    public void setStdevMultiplierBelow(Double stdevMultiplierBelow) {
        this.setDirtyIf(!Objects.equals(this.stdevMultiplierBelow, stdevMultiplierBelow));
        this.stdevMultiplierBelow = stdevMultiplierBelow;
    }


    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     *
     * @return Rule definition name (python module name without .py extension).
     */
    @Override
    public String getRuleDefinitionName() {
        return "stdev/below_stdev_multiply_30_days";
    }
}