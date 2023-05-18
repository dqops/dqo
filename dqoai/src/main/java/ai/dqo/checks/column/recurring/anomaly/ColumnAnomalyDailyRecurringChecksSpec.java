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
import ai.dqo.checks.column.checkspecs.anomaly.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
            put("daily_mean_anomaly_7_days", o -> o.dailyMeanAnomaly7Days);
            put("daily_mean_anomaly_30_days", o -> o.dailyMeanAnomaly30Days);
            put("daily_mean_anomaly_60_days", o -> o.dailyMeanAnomaly60Days);
            
            put("daily_median_anomaly_7_days", o -> o.dailyMedianAnomaly7Days);
            put("daily_median_anomaly_30_days", o -> o.dailyMedianAnomaly30Days);
            put("daily_median_anomaly_60_days", o -> o.dailyMedianAnomaly60Days);

            put("daily_sum_anomaly_7_days", o -> o.dailySumAnomaly7Days);
            put("daily_sum_anomaly_30_days", o -> o.dailySumAnomaly30Days);
            put("daily_sum_anomaly_60_days", o -> o.dailySumAnomaly60Days);
        }
    };

    @JsonProperty("daily_mean_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnAnomalyMeanChange7DaysCheckSpec dailyMeanAnomaly7Days;

    @JsonProperty("daily_mean_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyMeanChange30DaysCheckSpec dailyMeanAnomaly30Days;

    @JsonProperty("daily_mean_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalyMeanChange60DaysCheckSpec dailyMeanAnomaly60Days;

    @JsonProperty("daily_median_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnAnomalyMedianChange7DaysCheckSpec dailyMedianAnomaly7Days;

    @JsonProperty("daily_median_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyMedianChange30DaysCheckSpec dailyMedianAnomaly30Days;

    @JsonProperty("daily_median_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalyMedianChange60DaysCheckSpec dailyMedianAnomaly60Days;

    @JsonProperty("daily_sum_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnAnomalySumChange7DaysCheckSpec dailySumAnomaly7Days;

    @JsonProperty("daily_sum_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalySumChange30DaysCheckSpec dailySumAnomaly30Days;

    @JsonProperty("daily_sum_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalySumChange60DaysCheckSpec dailySumAnomaly60Days;

    /**
     * Returns a mean value anomaly 7 days check specification.
     * @return Mean value anomaly 7 days check specification.
     */
    public ColumnAnomalyMeanChange7DaysCheckSpec getDailyMeanAnomaly7Days() {
        return dailyMeanAnomaly7Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 7 days check.
     * @param dailyMeanAnomaly7Days Mean value anomaly 7 days check specification.
     */
    public void setDailyMeanAnomaly7Days(ColumnAnomalyMeanChange7DaysCheckSpec dailyMeanAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanAnomaly7Days, dailyMeanAnomaly7Days));
        this.dailyMeanAnomaly7Days = dailyMeanAnomaly7Days;
        propagateHierarchyIdToField(dailyMeanAnomaly7Days, "daily_mean_anomaly_7_days");
    }

    /**
     * Returns a mean value anomaly 30 days check specification.
     * @return Mean value anomaly 30 days check specification.
     */
    public ColumnAnomalyMeanChange30DaysCheckSpec getDailyMeanAnomaly30Days() {
        return dailyMeanAnomaly30Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 30 days check.
     * @param dailyMeanAnomaly30Days Mean value anomaly 30 days check specification.
     */
    public void setDailyMeanAnomaly30Days(ColumnAnomalyMeanChange30DaysCheckSpec dailyMeanAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanAnomaly30Days, dailyMeanAnomaly30Days));
        this.dailyMeanAnomaly30Days = dailyMeanAnomaly30Days;
        propagateHierarchyIdToField(dailyMeanAnomaly30Days, "daily_mean_anomaly_30_days");
    }

    /**
     * Returns a mean value anomaly 60 days check specification.
     * @return Mean value anomaly 60 days check specification.
     */
    public ColumnAnomalyMeanChange60DaysCheckSpec getDailyMeanAnomaly60Days() {
        return dailyMeanAnomaly60Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 60 days check.
     * @param dailyMeanAnomaly60Days Mean value anomaly 60 days check specification.
     */
    public void setDailyMeanAnomaly60Days(ColumnAnomalyMeanChange60DaysCheckSpec dailyMeanAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanAnomaly60Days, dailyMeanAnomaly60Days));
        this.dailyMeanAnomaly60Days = dailyMeanAnomaly60Days;
        propagateHierarchyIdToField(dailyMeanAnomaly60Days, "daily_mean_anomaly_60_days");
    }

    /**
     * Returns a median anomaly 7 days check specification.
     * @return Median anomaly 7 days check specification.
     */
    public ColumnAnomalyMedianChange7DaysCheckSpec getDailyMedianAnomaly7Days() {
        return dailyMedianAnomaly7Days;
    }

    /**
     * Sets a new specification of a median anomaly 7 days check.
     * @param dailyMedianAnomaly7Days Median anomaly 7 days check specification.
     */
    public void setDailyMedianAnomaly7Days(ColumnAnomalyMedianChange7DaysCheckSpec dailyMedianAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianAnomaly7Days, dailyMedianAnomaly7Days));
        this.dailyMedianAnomaly7Days = dailyMedianAnomaly7Days;
        propagateHierarchyIdToField(dailyMedianAnomaly7Days, "daily_median_anomaly_7_days");
    }

    /**
     * Returns a median anomaly 30 days check specification.
     * @return Median anomaly 30 days check specification.
     */
    public ColumnAnomalyMedianChange30DaysCheckSpec getDailyMedianAnomaly30Days() {
        return dailyMedianAnomaly30Days;
    }

    /**
     * Sets a new specification of a median anomaly 30 days check.
     * @param dailyMedianAnomaly30Days Median anomaly 30 days check specification.
     */
    public void setDailyMedianAnomaly30Days(ColumnAnomalyMedianChange30DaysCheckSpec dailyMedianAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianAnomaly30Days, dailyMedianAnomaly30Days));
        this.dailyMedianAnomaly30Days = dailyMedianAnomaly30Days;
        propagateHierarchyIdToField(dailyMedianAnomaly30Days, "daily_median_anomaly_30_days");
    }

    /**
     * Returns a median anomaly 60 days check specification.
     * @return Median anomaly 60 days check specification.
     */
    public ColumnAnomalyMedianChange60DaysCheckSpec getDailyMedianAnomaly60Days() {
        return dailyMedianAnomaly60Days;
    }

    /**
     * Sets a new specification of a median anomaly 60 days check.
     * @param dailyMedianAnomaly60Days Median anomaly 60 days check specification.
     */
    public void setDailyMedianAnomaly60Days(ColumnAnomalyMedianChange60DaysCheckSpec dailyMedianAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianAnomaly60Days, dailyMedianAnomaly60Days));
        this.dailyMedianAnomaly60Days = dailyMedianAnomaly60Days;
        propagateHierarchyIdToField(dailyMedianAnomaly60Days, "daily_median_anomaly_60_days");
    }

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
        propagateHierarchyIdToField(dailySumAnomaly7Days, "daily_sum_anomaly_7_days");
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
        propagateHierarchyIdToField(dailySumAnomaly30Days, "daily_sum_anomaly_30_days");
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
        propagateHierarchyIdToField(dailySumAnomaly60Days, "daily_sum_anomaly_60_days");
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
