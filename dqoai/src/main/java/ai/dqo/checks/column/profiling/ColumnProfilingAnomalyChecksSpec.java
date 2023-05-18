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
public class ColumnProfilingAnomalyChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnProfilingAnomalyChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("mean_anomaly_7_days", o -> o.meanAnomaly7Days);
            put("mean_anomaly_30_days", o -> o.meanAnomaly30Days);
            put("mean_anomaly_60_days", o -> o.meanAnomaly60Days);

            put("median_anomaly_7_days", o -> o.medianAnomaly7Days);
            put("median_anomaly_30_days", o -> o.medianAnomaly30Days);
            put("median_anomaly_60_days", o -> o.medianAnomaly60Days);

            put("sum_anomaly_7_days", o -> o.sumAnomaly7Days);
            put("sum_anomaly_30_days", o -> o.sumAnomaly30Days);
            put("sum_anomaly_60_days", o -> o.sumAnomaly60Days);
        }
    };

    @JsonProperty("mean_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnAnomalyMeanChange7DaysCheckSpec meanAnomaly7Days;

    @JsonProperty("mean_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyMeanChange30DaysCheckSpec meanAnomaly30Days;

    @JsonProperty("mean_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalyMeanChange60DaysCheckSpec meanAnomaly60Days;

    @JsonProperty("median_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnAnomalyMedianChange7DaysCheckSpec medianAnomaly7Days;

    @JsonProperty("median_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyMedianChange30DaysCheckSpec medianAnomaly30Days;

    @JsonProperty("median_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalyMedianChange60DaysCheckSpec medianAnomaly60Days;

    @JsonProperty("sum_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.")
    private ColumnAnomalySumChange7DaysCheckSpec sumAnomaly7Days;

    @JsonProperty("sum_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalySumChange30DaysCheckSpec sumAnomaly30Days;

    @JsonProperty("sum_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalySumChange60DaysCheckSpec sumAnomaly60Days;

    /**
     * Returns a mean anomaly 7 days check specification.
     * @return Mean anomaly 7 days check specification.
     */
    public ColumnAnomalyMeanChange7DaysCheckSpec getMeanAnomaly7Days() {
        return meanAnomaly7Days;
    }
    
    /**
     * Sets a new specification of a mean value anomaly 7 days check.
     * @param meanAnomaly7Days Mean value anomaly 7 days check specification.
     */
    public void setMeanAnomaly7Days(ColumnAnomalyMeanChange7DaysCheckSpec meanAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.meanAnomaly7Days, meanAnomaly7Days));
        this.meanAnomaly7Days = meanAnomaly7Days;
        propagateHierarchyIdToField(meanAnomaly7Days, "mean_anomaly_7_days");
    }

    /**
     * Returns a mean value anomaly 30 days check specification.
     * @return Mean value anomaly 30 days check specification.
     */
    public ColumnAnomalyMeanChange30DaysCheckSpec getMeanAnomaly30Days() {
        return meanAnomaly30Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 30 days check.
     * @param meanAnomaly30Days Mean value anomaly 30 days check specification.
     */
    public void setMeanAnomaly30Days(ColumnAnomalyMeanChange30DaysCheckSpec meanAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.meanAnomaly30Days, meanAnomaly30Days));
        this.meanAnomaly30Days = meanAnomaly30Days;
        propagateHierarchyIdToField(meanAnomaly30Days, "mean_anomaly_30_days");
    }

    /**
     * Returns a mean value anomaly 60 days check specification.
     * @return Mean value anomaly 60 days check specification.
     */
    public ColumnAnomalyMeanChange60DaysCheckSpec getMeanAnomaly60Days() {
        return meanAnomaly60Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 60 days check.
     * @param meanAnomaly60Days Mean value anomaly 60 days check specification.
     */
    public void setMeanAnomaly60Days(ColumnAnomalyMeanChange60DaysCheckSpec meanAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.meanAnomaly60Days, meanAnomaly60Days));
        this.meanAnomaly60Days = meanAnomaly60Days;
        propagateHierarchyIdToField(meanAnomaly60Days, "mean_anomaly_60_days");
    }

    /**
     * Returns a median anomaly 7 days check specification.
     * @return Median anomaly 7 days check specification.
     */
    public ColumnAnomalyMedianChange7DaysCheckSpec getMedianAnomaly7Days() {
        return medianAnomaly7Days;
    }

    /**
     * Sets a new specification of a median anomaly 7 days check.
     * @param medianAnomaly7Days Median anomaly 7 days check specification.
     */
    public void setMedianAnomaly7Days(ColumnAnomalyMedianChange7DaysCheckSpec medianAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.medianAnomaly7Days, medianAnomaly7Days));
        this.medianAnomaly7Days = medianAnomaly7Days;
        propagateHierarchyIdToField(medianAnomaly7Days, "median_anomaly_7_days");
    }

    /**
     * Returns a median anomaly 30 days check specification.
     * @return Median anomaly 30 days check specification.
     */
    public ColumnAnomalyMedianChange30DaysCheckSpec getMedianAnomaly30Days() {
        return medianAnomaly30Days;
    }

    /**
     * Sets a new specification of a median anomaly 30 days check.
     * @param medianAnomaly30Days Median anomaly 30 days check specification.
     */
    public void setMedianAnomaly30Days(ColumnAnomalyMedianChange30DaysCheckSpec medianAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.medianAnomaly30Days, medianAnomaly30Days));
        this.medianAnomaly30Days = medianAnomaly30Days;
        propagateHierarchyIdToField(medianAnomaly30Days, "median_anomaly_30_days");
    }

    /**
     * Returns a median anomaly 60 days check specification.
     * @return Median anomaly 60 days check specification.
     */
    public ColumnAnomalyMedianChange60DaysCheckSpec getMedianAnomaly60Days() {
        return medianAnomaly60Days;
    }

    /**
     * Sets a new specification of a median anomaly 60 days check.
     * @param medianAnomaly60Days Median anomaly 60 days check specification.
     */
    public void setMedianAnomaly60Days(ColumnAnomalyMedianChange60DaysCheckSpec medianAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.medianAnomaly60Days, medianAnomaly60Days));
        this.medianAnomaly60Days = medianAnomaly60Days;
        propagateHierarchyIdToField(medianAnomaly60Days, "median_anomaly_60_days");
    }

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
        propagateHierarchyIdToField(sumAnomaly7Days, "sum_anomaly_7_days");
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
        propagateHierarchyIdToField(sumAnomaly30Days, "sum_anomaly_30_days");
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
        propagateHierarchyIdToField(sumAnomaly60Days, "sum_anomaly_60_days");
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
