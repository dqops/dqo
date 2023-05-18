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
            put("sum_anomaly_7d", o -> o.sumAnomaly7Days);
            put("sum_anomaly_30d", o -> o.sumAnomaly30Days);
            put("sum_anomaly_60d", o -> o.sumAnomaly60Days);
        }
    };

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnAnomalySumChange7DaysCheckSpec sumAnomaly7Days;

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalySumChange30DaysCheckSpec sumAnomaly30Days;

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalySumChange60DaysCheckSpec sumAnomaly60Days;


    /**
     * Returns a sum anomaly 7 days check specification.
     * @return Sum anomaly 7 days check specification.
     */
    public ColumnAnomalySumChange7DaysCheckSpec getSumAnomaly7Days() {
        return sumAnomaly7Days;
    }

    /**
     * Sets a new specification of a sum anomaly 7 days check.
     * @param sumAnomaly7Days Sum anomaly 7 days check specification.
     */
    public void setSumAnomaly7Days(ColumnAnomalySumChange7DaysCheckSpec sumAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.sumAnomaly7Days, sumAnomaly7Days));
        this.sumAnomaly7Days = sumAnomaly7Days;
        propagateHierarchyIdToField(sumAnomaly7Days, "sum_anomaly_7d");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnAnomalySumChange30DaysCheckSpec getSumAnomaly30Days() {
        return sumAnomaly30Days;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param sumAnomaly30Days Sum anomaly 30 days check specification.
     */
    public void setSumAnomaly30Days(ColumnAnomalySumChange30DaysCheckSpec sumAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.sumAnomaly30Days, sumAnomaly30Days));
        this.sumAnomaly30Days = sumAnomaly30Days;
        propagateHierarchyIdToField(sumAnomaly30Days, "sum_anomaly_30d");
    }

    /**
     * Returns a sum anomaly 60 days check specification.
     * @return Sum anomaly 60 days check specification.
     */
    public ColumnAnomalySumChange60DaysCheckSpec getSumAnomaly60Days() {
        return sumAnomaly60Days;
    }

    /**
     * Sets a new specification of a sum anomaly 60 days check.
     * @param sumAnomaly60Days Sum anomaly 60 days check specification.
     */
    public void setSumAnomaly60Days(ColumnAnomalySumChange60DaysCheckSpec sumAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.sumAnomaly60Days, sumAnomaly60Days));
        this.sumAnomaly60Days = sumAnomaly60Days;
        propagateHierarchyIdToField(sumAnomaly60Days, "sum_anomaly_60d");
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
