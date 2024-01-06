/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.rules.percentile;

import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if a data quality sensor readout value is probable under
 * the estimated normal distribution based on the increments of previous values gathered
 * within a time window.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class AnomalyDifferencingPercentileMovingAverageRule05ParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<AnomalyDifferencingPercentileMovingAverageRule05ParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    @JsonPropertyDescription("Probability that the current sensor readout will achieve values within" +
            " the mean according to the distribution of the previous values gathered within the time window." +
            " In other words, the inter-quantile range around the mean of the estimated normal distribution." +
            " Set the time window at the threshold level for all severity levels (warning, error, fatal) at once." +
            " The default is a time window of 90 periods (days, etc.), but at least 30 readouts must exist" +
            " to run the calculation. You can change the default value by modifying prediction_time_window parameter" +
            "in Definitions section.")
    private Double anomalyPercent = 0.5;

    /**
     * Default constructor.
     */
    public AnomalyDifferencingPercentileMovingAverageRule05ParametersSpec() {
    }

    /**
     * Probability that the current sensor readout will achieve values greater
     * than it would be expected from the estimated distribution based on
     * the previous values gathered within the time window.
     * @return The upper percentile of the estimated normal distribution.
     */
    public Double getAnomalyPercent() {
        return anomalyPercent;
    }

    /**
     * Sets the upper percentile threshold of the estimated normal distribution.
     * @param anomalyPercent Percentage of values in the right tail that should be considered erroneous.
     */
    public void setAnomalyPercent(Double anomalyPercent) {
        this.setDirtyIf(!Objects.equals(this.anomalyPercent, anomalyPercent));
        this.anomalyPercent = anomalyPercent;
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
        return "percentile/anomaly_differencing_percentile_moving_average";
    }
}
