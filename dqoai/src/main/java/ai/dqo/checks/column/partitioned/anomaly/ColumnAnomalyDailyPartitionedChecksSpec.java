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
public class ColumnAnomalyDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_mean_anomaly_7d", o -> o.dailyPartitionMeanAnomaly7Days);
            put("daily_partition_mean_anomaly_30d", o -> o.dailyPartitionMeanAnomaly30Days);
            put("daily_partition_mean_anomaly_60d", o -> o.dailyPartitionMeanAnomaly60Days);

            put("daily_partition_median_anomaly_7d", o -> o.dailyPartitionMedianAnomaly7Days);
            put("daily_partition_median_anomaly_30d", o -> o.dailyPartitionMedianAnomaly30Days);
            put("daily_partition_median_anomaly_60d", o -> o.dailyPartitionMedianAnomaly60Days);

            put("daily_partition_sum_anomaly_7d", o -> o.dailyPartitionSumAnomaly7Days);
            put("daily_partition_sum_anomaly_30d", o -> o.dailyPartitionSumAnomaly30Days);
            put("daily_partition_sum_anomaly_60d", o -> o.dailyPartitionSumAnomaly60Days);
        }
    };

    @JsonPropertyDescription("Verifies that the mean value in a column is within a percentile from measurements made during the last 7 days.")
    private ColumnAnomalyMean7DaysCheckSpec dailyPartitionMeanAnomaly7Days;

    @JsonPropertyDescription("Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.")
    private ColumnAnomalyMean30DaysCheckSpec dailyPartitionMeanAnomaly30Days;

    @JsonPropertyDescription("Verifies that the mean value in a column is within a percentile from measurements made during the last 60 days.")
    private ColumnAnomalyMean60DaysCheckSpec dailyPartitionMeanAnomaly60Days;

    @JsonPropertyDescription("Verifies that the median in a column is within a percentile from measurements made during the last 7 days.")
    private ColumnAnomalyMedian7DaysCheckSpec dailyPartitionMedianAnomaly7Days;

    @JsonPropertyDescription("Verifies that the median in a column is within a percentile from measurements made during the last 30 days.")
    private ColumnAnomalyMedian30DaysCheckSpec dailyPartitionMedianAnomaly30Days;

    @JsonPropertyDescription("Verifies that the median in a column is within a percentile from measurements made during the last 60 days.")
    private ColumnAnomalyMedian60DaysCheckSpec dailyPartitionMedianAnomaly60Days;

    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 7 days.")
    private ColumnAnomalySum7DaysCheckSpec dailyPartitionSumAnomaly7Days;

    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.")
    private ColumnAnomalySum30DaysCheckSpec dailyPartitionSumAnomaly30Days;

    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 60 days.")
    private ColumnAnomalySum60DaysCheckSpec dailyPartitionSumAnomaly60Days;
    

    /**
     * Returns a mean value anomaly 7 days check specification.
     * @return Mean value anomaly 7 days check specification.
     */
    public ColumnAnomalyMean7DaysCheckSpec getDailyPartitionMeanAnomaly7Days() {
        return dailyPartitionMeanAnomaly7Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 7 days check.
     * @param dailyPartitionMeanAnomaly7Days Mean value anomaly 7 days check specification.
     */
    public void setDailyPartitionMeanAnomaly7Days(ColumnAnomalyMean7DaysCheckSpec dailyPartitionMeanAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanAnomaly7Days, dailyPartitionMeanAnomaly7Days));
        this.dailyPartitionMeanAnomaly7Days = dailyPartitionMeanAnomaly7Days;
        propagateHierarchyIdToField(dailyPartitionMeanAnomaly7Days, "daily_partition_mean_anomaly_7d");
    }

    /**
     * Returns a mean value anomaly 30 days check specification.
     * @return Mean value anomaly 30 days check specification.
     */
    public ColumnAnomalyMean30DaysCheckSpec getDailyPartitionMeanAnomaly30Days() {
        return dailyPartitionMeanAnomaly30Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 30 days check.
     * @param dailyPartitionMeanAnomaly30Days Mean value anomaly 30 days check specification.
     */
    public void setDailyPartitionMeanAnomaly30Days(ColumnAnomalyMean30DaysCheckSpec dailyPartitionMeanAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanAnomaly30Days, dailyPartitionMeanAnomaly30Days));
        this.dailyPartitionMeanAnomaly30Days = dailyPartitionMeanAnomaly30Days;
        propagateHierarchyIdToField(dailyPartitionMeanAnomaly30Days, "daily_partition_mean_anomaly_30d");
    }

    /**
     * Returns a mean value anomaly 60 days check specification.
     * @return Mean value anomaly 60 days check specification.
     */
    public ColumnAnomalyMean60DaysCheckSpec getDailyPartitionMeanAnomaly60Days() {
        return dailyPartitionMeanAnomaly60Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 60 days check.
     * @param dailyPartitionMeanAnomaly60Days Mean value anomaly 60 days check specification.
     */
    public void setDailyPartitionMeanAnomaly60Days(ColumnAnomalyMean60DaysCheckSpec dailyPartitionMeanAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanAnomaly60Days, dailyPartitionMeanAnomaly60Days));
        this.dailyPartitionMeanAnomaly60Days = dailyPartitionMeanAnomaly60Days;
        propagateHierarchyIdToField(dailyPartitionMeanAnomaly60Days, "daily_partition_mean_anomaly_60d");
    }

    /**
     * Returns a median anomaly 7 days check specification.
     * @return Median anomaly 7 days check specification.
     */
    public ColumnAnomalyMedian7DaysCheckSpec getDailyPartitionMedianAnomaly7Days() {
        return dailyPartitionMedianAnomaly7Days;
    }

    /**
     * Sets a new specification of a median anomaly 7 days check.
     * @param dailyPartitionMedianAnomaly7Days Median anomaly 7 days check specification.
     */
    public void setDailyPartitionMedianAnomaly7Days(ColumnAnomalyMedian7DaysCheckSpec dailyPartitionMedianAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianAnomaly7Days, dailyPartitionMedianAnomaly7Days));
        this.dailyPartitionMedianAnomaly7Days = dailyPartitionMedianAnomaly7Days;
        propagateHierarchyIdToField(dailyPartitionMedianAnomaly7Days, "daily_partition_median_anomaly_7d");
    }

    /**
     * Returns a median anomaly 30 days check specification.
     * @return Median anomaly 30 days check specification.
     */
    public ColumnAnomalyMedian30DaysCheckSpec getDailyPartitionMedianAnomaly30Days() {
        return dailyPartitionMedianAnomaly30Days;
    }

    /**
     * Sets a new specification of a median anomaly 30 days check.
     * @param dailyPartitionMedianAnomaly30Days Median anomaly 30 days check specification.
     */
    public void setDailyPartitionMedianAnomaly30Days(ColumnAnomalyMedian30DaysCheckSpec dailyPartitionMedianAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianAnomaly30Days, dailyPartitionMedianAnomaly30Days));
        this.dailyPartitionMedianAnomaly30Days = dailyPartitionMedianAnomaly30Days;
        propagateHierarchyIdToField(dailyPartitionMedianAnomaly30Days, "daily_partition_median_anomaly_30d");
    }

    /**
     * Returns a median anomaly 60 days check specification.
     * @return Median anomaly 60 days check specification.
     */
    public ColumnAnomalyMedian60DaysCheckSpec getDailyPartitionMedianAnomaly60Days() {
        return dailyPartitionMedianAnomaly60Days;
    }

    /**
     * Sets a new specification of a median anomaly 60 days check.
     * @param dailyPartitionMedianAnomaly60Days Median anomaly 60 days check specification.
     */
    public void setDailyPartitionMedianAnomaly60Days(ColumnAnomalyMedian60DaysCheckSpec dailyPartitionMedianAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianAnomaly60Days, dailyPartitionMedianAnomaly60Days));
        this.dailyPartitionMedianAnomaly60Days = dailyPartitionMedianAnomaly60Days;
        propagateHierarchyIdToField(dailyPartitionMedianAnomaly60Days, "daily_partition_median_anomaly_60d");
    }

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
        propagateHierarchyIdToField(dailyPartitionSumAnomaly7Days, "daily_partition_sum_anomaly_7d");
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
        propagateHierarchyIdToField(dailyPartitionSumAnomaly30Days, "daily_partition_sum_anomaly_30d");
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
        propagateHierarchyIdToField(dailyPartitionSumAnomaly60Days, "daily_partition_sum_anomaly_60d");
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
