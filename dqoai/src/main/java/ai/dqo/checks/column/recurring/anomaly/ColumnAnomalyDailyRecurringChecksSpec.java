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
package ai.dqo.checks.column.recurring.anomaly;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.anomaly.ColumnSumAnomaly30DaysCheckSpec;
import ai.dqo.checks.column.checkspecs.anomaly.ColumnSumAnomaly60DaysCheckSpec;
import ai.dqo.checks.column.checkspecs.anomaly.ColumnSumAnomaly7DaysCheckSpec;
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
public class ColumnAnomalyDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("sum_anomaly_7d", o -> o.sumAnomaly7DaysCheckSpec);
            put("sum_anomaly_30d", o -> o.sumAnomaly30DaysCheckSpec);
            put("sum_anomaly_60d", o -> o.sumAnomaly60DaysCheckSpec);
        }
    };

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnSumAnomaly7DaysCheckSpec sumAnomaly7DaysCheckSpec;

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnSumAnomaly30DaysCheckSpec sumAnomaly30DaysCheckSpec;

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnSumAnomaly60DaysCheckSpec sumAnomaly60DaysCheckSpec;


    /**
     * Returns a sum anomaly 7 days check specification.
     * @return Sum anomaly 7 days check specification.
     */
    public ColumnSumAnomaly7DaysCheckSpec getSumAnomaly7DaysCheckSpec() {
        return sumAnomaly7DaysCheckSpec;
    }

    /**
     * Sets a new specification of a sum anomaly 7 days check.
     * @param sumAnomaly7DaysCheckSpec Sum anomaly 7 days check specification.
     */
    public void setSumAnomaly7DaysCheckSpec(ColumnSumAnomaly7DaysCheckSpec sumAnomaly7DaysCheckSpec) {
        this.setDirtyIf(!Objects.equals(this.sumAnomaly7DaysCheckSpec, sumAnomaly7DaysCheckSpec));
        this.sumAnomaly7DaysCheckSpec = sumAnomaly7DaysCheckSpec;
        propagateHierarchyIdToField(sumAnomaly7DaysCheckSpec, "sum_anomaly_7d");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnSumAnomaly30DaysCheckSpec getSumAnomaly30DaysCheckSpec() {
        return sumAnomaly30DaysCheckSpec;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param sumAnomaly30DaysCheckSpec Sum anomaly 30 days check specification.
     */
    public void setSumAnomaly30DaysCheckSpec(ColumnSumAnomaly30DaysCheckSpec sumAnomaly30DaysCheckSpec) {
        this.setDirtyIf(!Objects.equals(this.sumAnomaly30DaysCheckSpec, sumAnomaly30DaysCheckSpec));
        this.sumAnomaly30DaysCheckSpec = sumAnomaly30DaysCheckSpec;
        propagateHierarchyIdToField(sumAnomaly30DaysCheckSpec, "sum_anomaly_30d");
    }

    /**
     * Returns a sum anomaly 60 days check specification.
     * @return Sum anomaly 60 days check specification.
     */
    public ColumnSumAnomaly60DaysCheckSpec getSumAnomaly60DaysCheckSpec() {
        return sumAnomaly60DaysCheckSpec;
    }

    /**
     * Sets a new specification of a sum anomaly 60 days check.
     * @param sumAnomaly60DaysCheckSpec Sum anomaly 60 days check specification.
     */
    public void setSumAnomaly60DaysCheckSpec(ColumnSumAnomaly60DaysCheckSpec sumAnomaly60DaysCheckSpec) {
        this.setDirtyIf(!Objects.equals(this.sumAnomaly60DaysCheckSpec, sumAnomaly60DaysCheckSpec));
        this.sumAnomaly60DaysCheckSpec = sumAnomaly60DaysCheckSpec;
        propagateHierarchyIdToField(sumAnomaly60DaysCheckSpec, "sum_anomaly_60d");
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
