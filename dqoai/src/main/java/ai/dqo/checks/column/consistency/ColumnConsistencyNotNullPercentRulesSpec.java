/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.checks.column.consistency;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.averages.PercentMovingAverageRuleThresholdsSpec;
import ai.dqo.rules.stdev.PercentMovingStdevRuleThresholdsSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import java.util.Objects;
/**
 * Column not null percent rules (a list of supported rules).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConsistencyNotNullPercentRulesSpec extends AbstractRuleSetSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConsistencyNotNullPercentRulesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleSetSpec.FIELDS) {
        {
            put("moving_average", o -> o.movingAverage);
            put("moving_stdev", o -> o.movingStdev);
        }
    };
    @JsonPropertyDescription("Verifies that the timestamp difference between two columns is equals to average (within a delta)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingAverageRuleThresholdsSpec movingAverage;
    @JsonPropertyDescription("Verifies that the timestamp difference between two columns is equals to average (within a delta)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingStdevRuleThresholdsSpec movingStdev;
    /**
     * Moving average thresholds.
     * @return Moving average thresholds.
     */
    public PercentMovingAverageRuleThresholdsSpec getMovingAverage() {
        return movingAverage;
    }
    /**
     * Moving stdev thresholds.
     * @return Moving stdev thresholds.
     */
    public PercentMovingStdevRuleThresholdsSpec getMovingStdev() {
        return movingStdev;
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
     * Sets the Moving stdev  thresholds.
     * @param movingStdev Moving stdev thresholds.
     */
    public void setMovingStdev(PercentMovingStdevRuleThresholdsSpec movingStdev) {
        this.setDirtyIf(!Objects.equals(this.movingStdev, movingStdev));
        this.movingStdev = movingStdev;
        this.propagateHierarchyIdToField(movingStdev, "moving_stdev");
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