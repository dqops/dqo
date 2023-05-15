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
package ai.dqo.checks.column.profiling;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.anomaly.*;
import ai.dqo.checks.column.checkspecs.numeric.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level for detecting anomalies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnProfilingAnomalyChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnProfilingAnomalyChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("change_in_sum_percentile_tails_7d", o -> o.changeInSumPercentileTails7DaysCheckSpec);
            put("change_in_sum_percentile_tails_30d", o -> o.changeInSumPercentileTails30DaysCheckSpec);
            put("change_in_sum_percentile_tails_60d", o -> o.changeInSumPercentileTails60DaysCheckSpec);

            put("change_in_sum_percentile_within_7d", o -> o.changeInSumPercentileWithin7DaysCheckSpec);
            put("change_in_sum_percentile_within_30d", o -> o.changeInSumPercentileWithin30DaysCheckSpec);
            put("change_in_sum_percentile_within_60d", o -> o.changeInSumPercentileWithin60DaysCheckSpec);

            put("change_in_sum_stddev_tails_7d", o -> o.changeInSumStddevTails7DaysCheckSpec);
            put("change_in_sum_stddev_tails_30d", o -> o.changeInSumStddevTails30DaysCheckSpec);
            put("change_in_sum_stddev_tails_60d", o -> o.changeInSumStddevTails60DaysCheckSpec);

            put("change_in_sum_stddev_within_7d", o -> o.changeInSumStddevWithin7DaysCheckSpec);
            put("change_in_sum_stddev_within_30d", o -> o.changeInSumStddevWithin30DaysCheckSpec);
            put("change_in_sum_stddev_within_60d", o -> o.changeInSumStddevWithin60DaysCheckSpec);
        }
    };

    private ColumnChangeInSumPercentileTails7DaysCheckSpec changeInSumPercentileTails7DaysCheckSpec;
    private ColumnChangeInSumPercentileTails30DaysCheckSpec changeInSumPercentileTails30DaysCheckSpec;
    private ColumnChangeInSumPercentileTails60DaysCheckSpec changeInSumPercentileTails60DaysCheckSpec;

    private ColumnChangeInSumPercentileWithin7DaysCheckSpec changeInSumPercentileWithin7DaysCheckSpec;
    private ColumnChangeInSumPercentileWithin30DaysCheckSpec changeInSumPercentileWithin30DaysCheckSpec;
    private ColumnChangeInSumPercentileWithin60DaysCheckSpec changeInSumPercentileWithin60DaysCheckSpec;

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count.")
    private ColumnChangeInSumStddevTails7DaysCheckSpec changeInSumStddevTails7DaysCheckSpec;
    private ColumnChangeInSumStddevTails30DaysCheckSpec changeInSumStddevTails30DaysCheckSpec;
    private ColumnChangeInSumStddevTails60DaysCheckSpec changeInSumStddevTails60DaysCheckSpec;

    private ColumnChangeInSumStddevWithin7DaysCheckSpec changeInSumStddevWithin7DaysCheckSpec;
    private ColumnChangeInSumStddevWithin30DaysCheckSpec changeInSumStddevWithin30DaysCheckSpec;
    private ColumnChangeInSumStddevWithin60DaysCheckSpec changeInSumStddevWithin60DaysCheckSpec;

    /**
     * Returns a negative count check specification.
     * @return Negative count check specification.
     */
    public ColumnNegativeCountCheckSpec getNegativeCount() {
        return negativeCount;
    }

    /**
     * Sets a new specification of a negative count check.
     * @param negativeCount Negative count check specification.
     */
    public void setNegativeCount(ColumnNegativeCountCheckSpec negativeCount) {
        this.setDirtyIf(!Objects.equals(this.negativeCount, negativeCount));
        this.negativeCount = negativeCount;
        propagateHierarchyIdToField(negativeCount, "negative_count");
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
