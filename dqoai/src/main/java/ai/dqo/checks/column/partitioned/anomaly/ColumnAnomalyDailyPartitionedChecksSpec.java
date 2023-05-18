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
package ai.dqo.checks.column.partitioned.anomaly;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.anomaly.ColumnAnomalySum30DaysCheckSpec;
import ai.dqo.checks.column.checkspecs.anomaly.ColumnAnomalySum60DaysCheckSpec;
import ai.dqo.checks.column.checkspecs.anomaly.ColumnAnomalySum7DaysCheckSpec;
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
public class ColumnAnomalyDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_sum_anomaly_7d", o -> o.dailyPartitionSumAnomaly7Days);
            put("daily_partition_sum_anomaly_30d", o -> o.dailyPartitionSumAnomaly30Days);
            put("daily_partition_sum_anomaly_60d", o -> o.dailyPartitionSumAnomaly60Days);
        }
    };

    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 7 days.")
    private ColumnAnomalySum7DaysCheckSpec dailyPartitionSumAnomaly7Days;

    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.")
    private ColumnAnomalySum30DaysCheckSpec dailyPartitionSumAnomaly30Days;

    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 60 days.")
    private ColumnAnomalySum60DaysCheckSpec dailyPartitionSumAnomaly60Days;


    /**
     * Returns a sum anomaly 7 days check specification.
     * @return Sum anomaly 7 days check specification.
     */
    public ColumnAnomalySum7DaysCheckSpec getDailyPartitionSumAnomaly7Days() {
        return dailyPartitionSumAnomaly7Days;
    }

    /**
     * Sets a new specification of a sum anomaly 7 days check.
     * @param dailyPartitionSumAnomaly7Days Sum anomaly 7 days check specification.
     */
    public void setDailyPartitionSumAnomaly7Days(ColumnAnomalySum7DaysCheckSpec dailyPartitionSumAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumAnomaly7Days, dailyPartitionSumAnomaly7Days));
        this.dailyPartitionSumAnomaly7Days = dailyPartitionSumAnomaly7Days;
        propagateHierarchyIdToField(dailyPartitionSumAnomaly7Days, "sum_anomaly_7d");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnAnomalySum30DaysCheckSpec getDailyPartitionSumAnomaly30Days() {
        return dailyPartitionSumAnomaly30Days;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param dailyPartitionSumAnomaly30Days Sum anomaly 30 days check specification.
     */
    public void setDailyPartitionSumAnomaly30Days(ColumnAnomalySum30DaysCheckSpec dailyPartitionSumAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumAnomaly30Days, dailyPartitionSumAnomaly30Days));
        this.dailyPartitionSumAnomaly30Days = dailyPartitionSumAnomaly30Days;
        propagateHierarchyIdToField(dailyPartitionSumAnomaly30Days, "sum_anomaly_30d");
    }

    /**
     * Returns a sum anomaly 60 days check specification.
     * @return Sum anomaly 60 days check specification.
     */
    public ColumnAnomalySum60DaysCheckSpec getDailyPartitionSumAnomaly60Days() {
        return dailyPartitionSumAnomaly60Days;
    }

    /**
     * Sets a new specification of a sum anomaly 60 days check.
     * @param dailyPartitionSumAnomaly60Days Sum anomaly 60 days check specification.
     */
    public void setDailyPartitionSumAnomaly60Days(ColumnAnomalySum60DaysCheckSpec dailyPartitionSumAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumAnomaly60Days, dailyPartitionSumAnomaly60Days));
        this.dailyPartitionSumAnomaly60Days = dailyPartitionSumAnomaly60Days;
        propagateHierarchyIdToField(dailyPartitionSumAnomaly60Days, "sum_anomaly_60d");
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
