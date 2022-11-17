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
package ai.dqo.rules.averages;

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
@Deprecated
public class PercentMovingAverageRuleThresholdsSpec extends
        AbstractRuleThresholdsSpec<PercentMovingAverageRuleParametersSpec> implements TimeWindowedRule {
    private static final ChildHierarchyNodeFieldMapImpl<PercentMovingAverageRuleThresholdsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleThresholdsSpec.FIELDS) {
        {
            // no need to register, the parent will register
        }
    };

    @JsonPropertyDescription("Rule threshold for a high severity (3) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingAverageRuleParametersSpec high;

    @JsonPropertyDescription("Rule threshold for a low severity (1) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingAverageRuleParametersSpec low;

    @JsonPropertyDescription("Rule threshold for a medium severity (2) alert.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private PercentMovingAverageRuleParametersSpec medium;

    /**
     * Alerting rules configuration that raise "LOW" severity alerts for unsatisfied rules.
     * @return High severity alert rule parameters.
     */
    @Override
    public PercentMovingAverageRuleParametersSpec getHigh(){ return high; }

    /**
     * Alerting rules configuration that raise "LOW" severity alerts for unsatisfied rules.
     * @return Medium severity alert rule parameters.
     */
    @Override
    public PercentMovingAverageRuleParametersSpec getMedium() { return medium;}

    /**
     * Alerting rules configuration that raise "LOW" severity alerts for unsatisfied rules.
     * @return Low severity alert rule parameters.
     */
    @Override
    public PercentMovingAverageRuleParametersSpec getLow() { return low; }



    /**
     * Sets a High severity alert rule set.
     * @param high High severity alert rules.
     */
    public void setHigh(PercentMovingAverageRuleParametersSpec high){
        this.setDirtyIf(!Objects.equals(this.high, high));
        this.high = high;
        propagateHierarchyIdToField(high, "high");

    }

    /**
     * Sets a MEDIUM severity alert rule set.
     * @param medium Medium severity alert rules.
     */
    public void setMedium(PercentMovingAverageRuleParametersSpec medium) {
        this.setDirtyIf(!Objects.equals(this.medium, medium));
        this.medium = medium;
        propagateHierarchyIdToField(medium, "medium");

    }

    /**
     * Sets a LOW severity alert rule set.
     * @param low Low severity alert rules.
     */
    public void setLow(PercentMovingAverageRuleParametersSpec low) {
        this.setDirtyIf(!Objects.equals(this.low, low));
        this.low = low;
        propagateHierarchyIdToField(low, "low");

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
