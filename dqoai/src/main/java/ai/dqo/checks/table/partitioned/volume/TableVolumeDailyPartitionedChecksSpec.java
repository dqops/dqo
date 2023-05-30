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
package ai.dqo.checks.table.partitioned.volume;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.volume.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
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
            put("daily_partition_row_count_anomaly_7_days", o -> o.dailyPartitionRowCountAnomaly7Days);
            put("daily_partition_row_count_anomaly_30_days", o -> o.dailyPartitionRowCountAnomaly30Days);
            put("daily_partition_row_count_anomaly_60_days", o -> o.dailyPartitionRowCountAnomaly60Days);
            put("daily_partition_row_count_change", o -> o.dailyPartitionRowCountChange);
            put("daily_partition_row_count_change_yesterday", o -> o.dailyPartitionRowCountChangeYesterday);
            put("daily_partition_row_count_change_7_days", o -> o.dailyPartitionRowCountChange7Days);
            put("daily_partition_row_count_change_30_days", o -> o.dailyPartitionRowCountChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec dailyPartitionRowCount;

    @JsonProperty("daily_partition_row_count_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table is within a percentile from measurements made during the last 7 days.")
    private TableAnomalyRowCount7DaysCheckSpec dailyPartitionRowCountAnomaly7Days;

    @JsonProperty("daily_partition_row_count_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.")
    private TableAnomalyRowCount30DaysCheckSpec dailyPartitionRowCountAnomaly30Days;

    @JsonProperty("daily_partition_row_count_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table is within a percentile from measurements made during the last 60 days.")
    private TableAnomalyRowCount60DaysCheckSpec dailyPartitionRowCountAnomaly60Days;

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
     * Returns the row count match check.
     * @return Row count match check.
     */
    public TableAnomalyRowCount7DaysCheckSpec getDailyPartitionRowCountAnomaly7Days() {
        return dailyPartitionRowCountAnomaly7Days;
    }

    /**
     * Sets a new row count match check.
     * @param dailyPartitionRowCountAnomaly7Days Row count match check.
     */
    public void setDailyPartitionRowCountAnomaly7Days(TableAnomalyRowCount7DaysCheckSpec dailyPartitionRowCountAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomaly7Days, dailyPartitionRowCountAnomaly7Days));
        this.dailyPartitionRowCountAnomaly7Days = dailyPartitionRowCountAnomaly7Days;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomaly7Days, "daily_partition_row_count_anomaly_7_days");
    }

    /**
     * Returns the row count anomaly 30 days check.
     * @return Row count anomaly 30 days check.
     */
    public TableAnomalyRowCount30DaysCheckSpec getDailyPartitionRowCountAnomaly30Days() {
        return dailyPartitionRowCountAnomaly30Days;
    }

    /**
     * Sets a new row count anomaly 30 days check.
     * @param dailyPartitionRowCountAnomaly30Days Row count anomaly 30 days check.
     */
    public void setDailyPartitionRowCountAnomaly30Days(TableAnomalyRowCount30DaysCheckSpec dailyPartitionRowCountAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomaly30Days, dailyPartitionRowCountAnomaly30Days));
        this.dailyPartitionRowCountAnomaly30Days = dailyPartitionRowCountAnomaly30Days;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomaly30Days, "daily_partition_row_count_anomaly_30_days");
    }

    /**
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableAnomalyRowCount60DaysCheckSpec getDailyPartitionRowCountAnomaly60Days() {
        return dailyPartitionRowCountAnomaly60Days;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param dailyPartitionRowCountAnomaly60Days Row count anomaly 60 days check.
     */
    public void setDailyPartitionRowCountAnomaly60Days(TableAnomalyRowCount60DaysCheckSpec dailyPartitionRowCountAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomaly60Days, dailyPartitionRowCountAnomaly60Days));
        this.dailyPartitionRowCountAnomaly60Days = dailyPartitionRowCountAnomaly60Days;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomaly60Days, "daily_partition_row_count_anomaly_60_days");
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
}
