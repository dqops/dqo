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
import com.dqops.connectors.DataTypeCategory;
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
            put("daily_partition_row_count_anomaly", o -> o.dailyPartitionRowCountAnomaly);

            put("daily_partition_row_count_change", o -> o.dailyPartitionRowCountChange);
            put("daily_partition_row_count_change_1_day", o -> o.dailyPartitionRowCountChange1Day);
            put("daily_partition_row_count_change_7_days", o -> o.dailyPartitionRowCountChange7Days);
            put("daily_partition_row_count_change_30_days", o -> o.dailyPartitionRowCountChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. " +
            "The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the partition is not empty.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec dailyPartitionRowCount;

    @JsonPropertyDescription("Detects outstanding partitions whose volume (the row count) differs too much from the average daily partition size. It uses time series anomaly detection to find the outliers in the partition volume during the last 90 days.")
    private TableRowCountAnomalyStationaryPartitionCheckSpec dailyPartitionRowCountAnomaly;

    @JsonPropertyDescription("Detects when the partition's volume (row count) change between the current daily partition and the previous partition exceeds the maximum accepted change percentage.")
    private TableRowCountChangeCheckSpec dailyPartitionRowCountChange;

    @JsonProperty("daily_partition_row_count_change_1_day")
    @JsonPropertyDescription("Detects when the partition volume change (increase or decrease of the row count) since yesterday's daily partition exceeds the maximum accepted change percentage. ")
    private TableRowCountChange1DayCheckSpec dailyPartitionRowCountChange1Day;

    @JsonProperty("daily_partition_row_count_change_7_days")
    @JsonPropertyDescription("This check verifies that the percentage of change in the partition's volume (row count) since seven days ago is below the maximum accepted percentage. Verifying a volume change since a value a week ago overcomes the effect of weekly seasonability.")
    private TableRowCountChange7DaysCheckSpec dailyPartitionRowCountChange7Days;

    @JsonProperty("daily_partition_row_count_change_30_days")
    @JsonPropertyDescription("This check verifies that the percentage of change in the partition's volume (row count) since thirty days ago is below the maximum accepted percentage. Comparing the current row count to a value 30 days ago overcomes the effect of monthly seasonability.")
    private TableRowCountChange30DaysCheckSpec dailyPartitionRowCountChange30Days;


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
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableRowCountAnomalyStationaryPartitionCheckSpec getDailyPartitionRowCountAnomaly() {
        return dailyPartitionRowCountAnomaly;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param dailyPartitionRowCountAnomaly Row count anomaly 60 days check.
     */
    public void setDailyPartitionRowCountAnomaly(TableRowCountAnomalyStationaryPartitionCheckSpec dailyPartitionRowCountAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomaly, dailyPartitionRowCountAnomaly));
        this.dailyPartitionRowCountAnomaly = dailyPartitionRowCountAnomaly;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomaly, "daily_partition_row_count_anomaly");
    }

    /**
     * Returns the row count change check.
     * @return Row count change check.
     */
    public TableRowCountChangeCheckSpec getDailyPartitionRowCountChange() {
        return dailyPartitionRowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param dailyPartitionRowCountChange Row count change check.
     */
    public void setDailyPartitionRowCountChange(TableRowCountChangeCheckSpec dailyPartitionRowCountChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountChange, dailyPartitionRowCountChange));
        this.dailyPartitionRowCountChange = dailyPartitionRowCountChange;
        propagateHierarchyIdToField(dailyPartitionRowCountChange, "daily_partition_row_count_change");
    }

    /**
     * Returns the row count change since yesterday check.
     * @return Row count change since yesterday check.
     */
    public TableRowCountChange1DayCheckSpec getDailyPartitionRowCountChange1Day() {
        return dailyPartitionRowCountChange1Day;
    }

    /**
     * Sets a new row count change since yesterday check.
     * @param dailyPartitionRowCountChange1Day Row count change since yesterday check.
     */
    public void setDailyPartitionRowCountChange1Day(TableRowCountChange1DayCheckSpec dailyPartitionRowCountChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountChange1Day, dailyPartitionRowCountChange1Day));
        this.dailyPartitionRowCountChange1Day = dailyPartitionRowCountChange1Day;
        propagateHierarchyIdToField(dailyPartitionRowCountChange1Day, "daily_partition_row_count_change_1_day");
    }

    /**
     * Returns the row count change since 7 days check.
     * @return Row count change since 7 days check.
     */
    public TableRowCountChange7DaysCheckSpec getDailyPartitionRowCountChange7Days() {
        return dailyPartitionRowCountChange7Days;
    }

    /**
     * Sets a new row count change since 7 days check.
     * @param dailyPartitionRowCountChange7Days Row count change since 7 days check.
     */
    public void setDailyPartitionRowCountChange7Days(TableRowCountChange7DaysCheckSpec dailyPartitionRowCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountChange7Days, dailyPartitionRowCountChange7Days));
        this.dailyPartitionRowCountChange7Days = dailyPartitionRowCountChange7Days;
        propagateHierarchyIdToField(dailyPartitionRowCountChange7Days, "daily_partition_row_count_change_7_days");
    }

    /**
     * Returns the row count change since 30 days check.
     * @return Row count change since 30 days check.
     */
    public TableRowCountChange30DaysCheckSpec getDailyPartitionRowCountChange30Days() {
        return dailyPartitionRowCountChange30Days;
    }

    /**
     * Sets a new row count change since 30 days check.
     * @param dailyPartitionRowCountChange30Days Row count change since 30 days check.
     */
    public void setDailyPartitionRowCountChange30Days(TableRowCountChange30DaysCheckSpec dailyPartitionRowCountChange30Days) {
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

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
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
