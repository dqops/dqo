package ai.dqo.checks.table.timeliness;

import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.averages.PercentMovingAverageRuleThresholdsSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;
/*
TODO min should be replaced with max
 */

/**
 * Table average delay rules (a list of supported rules).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class TableTimelinessAverageDelayRulesSpec extends AbstractRuleSetSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessAverageDelayRulesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleSetSpec.FIELDS) {
        {
            put("moving_average", o -> o.movingAverage);
        }
    };

    @JsonPropertyDescription("Verifies that the timestamp difference between two columns is equals to average (within a delta)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingAverageRuleThresholdsSpec movingAverage;

    /**
     * Moving average thresholds.
     * @return Moving average thresholds.
     */
    public PercentMovingAverageRuleThresholdsSpec getMovingAverage() {
        return movingAverage;
    }

    /**
     * Sets the moving average thresholds.
     * @param movingAverage Moving average thresholds.
     */
    public void setMovingAverage(PercentMovingAverageRuleThresholdsSpec movingAverage) {
        this.setDirtyIf(!Objects.equals(this.movingAverage, movingAverage));
        this.movingAverage = movingAverage;
        this.propagateHierarchyIdToField(movingAverage, "moving_average");
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
}
