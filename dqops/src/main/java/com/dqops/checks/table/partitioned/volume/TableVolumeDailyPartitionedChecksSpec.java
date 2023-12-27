/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.checks.table.partitioned.volume;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.volume.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of table level date partitioned volume data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableVolumeDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableVolumeDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_row_count", o -> o.dailyPartitionRowCount);
            put("daily_partition_row_count_anomaly_stationary_30_days", o -> o.dailyPartitionRowCountAnomalyStationary30Days);
            put("daily_partition_row_count_anomaly_stationary", o -> o.dailyPartitionRowCountAnomalyStationary);
            put("daily_partition_row_count_change", o -> o.dailyPartitionRowCountChange);
            put("daily_partition_row_count_change_yesterday", o -> o.dailyPartitionRowCountChangeYesterday);
            put("daily_partition_row_count_change_7_days", o -> o.dailyPartitionRowCountChange7Days);
            put("daily_partition_row_count_change_30_days", o -> o.dailyPartitionRowCountChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. " +
            "The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. " +
            "When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec dailyPartitionRowCount;

    @JsonProperty("daily_partition_row_count_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.")
    private TableAnomalyStationaryPartitionRowCount30DaysCheckSpec dailyPartitionRowCountAnomalyStationary30Days;

    @JsonProperty("daily_partition_row_count_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.")
    private TableAnomalyStationaryPartitionRowCountCheckSpec dailyPartitionRowCountAnomalyStationary;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.")
    private TableChangeRowCountCheckSpec dailyPartitionRowCountChange;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.")
    private TableChangeRowCountSinceYesterdayCheckSpec dailyPartitionRowCountChangeYesterday;

    @JsonProperty("daily_partition_row_count_change_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.")
    private TableChangeRowCountSince7DaysCheckSpec dailyPartitionRowCountChange7Days;

    @JsonProperty("daily_partition_row_count_change_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.")
    private TableChangeRowCountSince30DaysCheckSpec dailyPartitionRowCountChange30Days;


    /**
     * Returns the row count check configuration.
     * @return Row count check specification.
     */
    public TableRowCountCheckSpec getDailyPartitionRowCount() {
        return dailyPartitionRowCount;
    }

    /**
     * Sets the row count.
     * @param dailyPartitionRowCount New row count check.
     */
    public void setDailyPartitionRowCount(TableRowCountCheckSpec dailyPartitionRowCount) {
		this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCount, dailyPartitionRowCount));
        this.dailyPartitionRowCount = dailyPartitionRowCount;
		this.propagateHierarchyIdToField(dailyPartitionRowCount, "daily_partition_row_count");
    }

    /**
     * Returns the row count anomaly 30 days check.
     * @return Row count anomaly 30 days check.
     */
    public TableAnomalyStationaryPartitionRowCount30DaysCheckSpec getDailyPartitionRowCountAnomalyStationary30Days() {
        return dailyPartitionRowCountAnomalyStationary30Days;
    }

    /**
     * Sets a new row count anomaly 30 days check.
     * @param dailyPartitionRowCountAnomalyStationary30Days Row count anomaly 30 days check.
     */
    public void setDailyPartitionRowCountAnomalyStationary30Days(TableAnomalyStationaryPartitionRowCount30DaysCheckSpec dailyPartitionRowCountAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomalyStationary30Days, dailyPartitionRowCountAnomalyStationary30Days));
        this.dailyPartitionRowCountAnomalyStationary30Days = dailyPartitionRowCountAnomalyStationary30Days;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomalyStationary30Days, "daily_partition_row_count_anomaly_stationary_30_days");
    }

    /**
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableAnomalyStationaryPartitionRowCountCheckSpec getDailyPartitionRowCountAnomalyStationary() {
        return dailyPartitionRowCountAnomalyStationary;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param dailyPartitionRowCountAnomalyStationary Row count anomaly 60 days check.
     */
    public void setDailyPartitionRowCountAnomalyStationary(TableAnomalyStationaryPartitionRowCountCheckSpec dailyPartitionRowCountAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomalyStationary, dailyPartitionRowCountAnomalyStationary));
        this.dailyPartitionRowCountAnomalyStationary = dailyPartitionRowCountAnomalyStationary;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomalyStationary, "daily_partition_row_count_anomaly_stationary");
    }

    /**
     * Returns the row count change check.
     * @return Row count change check.
     */
    public TableChangeRowCountCheckSpec getDailyPartitionRowCountChange() {
        return dailyPartitionRowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param dailyPartitionRowCountChange Row count change check.
     */
    public void setDailyPartitionRowCountChange(TableChangeRowCountCheckSpec dailyPartitionRowCountChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountChange, dailyPartitionRowCountChange));
        this.dailyPartitionRowCountChange = dailyPartitionRowCountChange;
        propagateHierarchyIdToField(dailyPartitionRowCountChange, "daily_partition_row_count_change");
    }

    /**
     * Returns the row count change since yesterday check.
     * @return Row count change since yesterday check.
     */
    public TableChangeRowCountSinceYesterdayCheckSpec getDailyPartitionRowCountChangeYesterday() {
        return dailyPartitionRowCountChangeYesterday;
    }

    /**
     * Sets a new row count change since yesterday check.
     * @param dailyPartitionRowCountChangeYesterday Row count change since yesterday check.
     */
    public void setDailyPartitionRowCountChangeYesterday(TableChangeRowCountSinceYesterdayCheckSpec dailyPartitionRowCountChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountChangeYesterday, dailyPartitionRowCountChangeYesterday));
        this.dailyPartitionRowCountChangeYesterday = dailyPartitionRowCountChangeYesterday;
        propagateHierarchyIdToField(dailyPartitionRowCountChangeYesterday, "daily_partition_row_count_change_yesterday");
    }

    /**
     * Returns the row count change since 7 days check.
     * @return Row count change since 7 days check.
     */
    public TableChangeRowCountSince7DaysCheckSpec getDailyPartitionRowCountChange7Days() {
        return dailyPartitionRowCountChange7Days;
    }

    /**
     * Sets a new row count change since 7 days check.
     * @param dailyPartitionRowCountChange7Days Row count change since 7 days check.
     */
    public void setDailyPartitionRowCountChange7Days(TableChangeRowCountSince7DaysCheckSpec dailyPartitionRowCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountChange7Days, dailyPartitionRowCountChange7Days));
        this.dailyPartitionRowCountChange7Days = dailyPartitionRowCountChange7Days;
        propagateHierarchyIdToField(dailyPartitionRowCountChange7Days, "daily_partition_row_count_change_7_days");
    }

    /**
     * Returns the row count change since 30 days check.
     * @return Row count change since 30 days check.
     */
    public TableChangeRowCountSince30DaysCheckSpec getDailyPartitionRowCountChange30Days() {
        return dailyPartitionRowCountChange30Days;
    }

    /**
     * Sets a new row count change since 30 days check.
     * @param dailyPartitionRowCountChange30Days Row count change since 30 days check.
     */
    public void setDailyPartitionRowCountChange30Days(TableChangeRowCountSince30DaysCheckSpec dailyPartitionRowCountChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountChange30Days, dailyPartitionRowCountChange30Days));
        this.dailyPartitionRowCountChange30Days = dailyPartitionRowCountChange30Days;
        propagateHierarchyIdToField(dailyPartitionRowCountChange30Days, "daily_partition_row_count_change_30_days");
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
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.table;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.partitioned;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.daily;
    }

    public static class TableVolumeDailyPartitionedChecksSpecSampleFactory implements SampleValueFactory<TableVolumeDailyPartitionedChecksSpec> {
        @Override
        public TableVolumeDailyPartitionedChecksSpec createSample() {
            return new TableVolumeDailyPartitionedChecksSpec() {{
                setDailyPartitionRowCount(new TableRowCountCheckSpec.TableRowCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
