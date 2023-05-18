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
import ai.dqo.checks.column.checkspecs.anomaly.ColumnAnomalySumChange30DaysCheckSpec;
import ai.dqo.checks.column.checkspecs.anomaly.ColumnAnomalySumChange60DaysCheckSpec;
import ai.dqo.checks.column.checkspecs.anomaly.ColumnAnomalySumChange7DaysCheckSpec;
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
            put("daily_sum_anomaly_7d", o -> o.dailySumAnomaly7Days);
            put("daily_sum_anomaly_30d", o -> o.dailySumAnomaly30Days);
            put("daily_sum_anomaly_60d", o -> o.dailySumAnomaly60Days);
        }
    };

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnAnomalySumChange7DaysCheckSpec dailySumAnomaly7Days;

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalySumChange30DaysCheckSpec dailySumAnomaly30Days;

    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalySumChange60DaysCheckSpec dailySumAnomaly60Days;


    /**
     * Returns a sum anomaly 7 days check specification.
     * @return Sum anomaly 7 days check specification.
     */
    public ColumnAnomalySumChange7DaysCheckSpec getDailySumAnomaly7Days() {
        return dailySumAnomaly7Days;
    }

    /**
     * Sets a new specification of a sum anomaly 7 days check.
     * @param dailySumAnomaly7Days Sum anomaly 7 days check specification.
     */
    public void setDailySumAnomaly7Days(ColumnAnomalySumChange7DaysCheckSpec dailySumAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumAnomaly7Days, dailySumAnomaly7Days));
        this.dailySumAnomaly7Days = dailySumAnomaly7Days;
        propagateHierarchyIdToField(dailySumAnomaly7Days, "sum_anomaly_7d");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnAnomalySumChange30DaysCheckSpec getDailySumAnomaly30Days() {
        return dailySumAnomaly30Days;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param dailySumAnomaly30Days Sum anomaly 30 days check specification.
     */
    public void setDailySumAnomaly30Days(ColumnAnomalySumChange30DaysCheckSpec dailySumAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumAnomaly30Days, dailySumAnomaly30Days));
        this.dailySumAnomaly30Days = dailySumAnomaly30Days;
        propagateHierarchyIdToField(dailySumAnomaly30Days, "sum_anomaly_30d");
    }

    /**
     * Returns a sum anomaly 60 days check specification.
     * @return Sum anomaly 60 days check specification.
     */
    public ColumnAnomalySumChange60DaysCheckSpec getDailySumAnomaly60Days() {
        return dailySumAnomaly60Days;
    }

    /**
     * Sets a new specification of a sum anomaly 60 days check.
     * @param dailySumAnomaly60Days Sum anomaly 60 days check specification.
     */
    public void setDailySumAnomaly60Days(ColumnAnomalySumChange60DaysCheckSpec dailySumAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumAnomaly60Days, dailySumAnomaly60Days));
        this.dailySumAnomaly60Days = dailySumAnomaly60Days;
        propagateHierarchyIdToField(dailySumAnomaly60Days, "sum_anomaly_60d");
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
