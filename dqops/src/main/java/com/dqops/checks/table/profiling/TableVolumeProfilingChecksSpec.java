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
package com.dqops.checks.table.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.table.checkspecs.volume.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured volume data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableVolumeProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableVolumeProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_row_count", o -> o.profileRowCount);
            put("profile_row_count_anomaly_differencing_30_days", o -> o.profileRowCountAnomalyDifferencing30Days);
            put("profile_row_count_anomaly_differencing", o -> o.profileRowCountAnomalyDifferencing);
            put("profile_row_count_change", o -> o.profileRowCountChange);
            put("profile_row_count_change_yesterday", o -> o.profileRowCountChangeYesterday);
            put("profile_row_count_change_7_days", o -> o.profileRowCountChange7Days);
            put("profile_row_count_change_30_days", o -> o.profileRowCountChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that the tested table has at least a minimum accepted number of rows. " +
            "The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. " +
            "When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.")
    private TableRowCountCheckSpec profileRowCount;

    @JsonProperty("profile_row_count_anomaly_differencing_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.")
    private TableAnomalyDifferencingRowCount30DaysCheckSpec profileRowCountAnomalyDifferencing30Days;

    @JsonProperty("profile_row_count_anomaly_differencing")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.")
    private TableAnomalyDifferencingRowCountCheckSpec profileRowCountAnomalyDifferencing;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.")
    private TableChangeRowCountCheckSpec profileRowCountChange;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.")
    private TableChangeRowCountSinceYesterdayCheckSpec profileRowCountChangeYesterday;

    @JsonProperty("profile_row_count_change_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.")
    private TableChangeRowCountSince7DaysCheckSpec profileRowCountChange7Days;

    @JsonProperty("profile_row_count_change_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.")
    private TableChangeRowCountSince30DaysCheckSpec profileRowCountChange30Days;

    
    /**
     * Returns a row count check.
     * @return Row count check.
     */
    public TableRowCountCheckSpec getProfileRowCount() {
        return profileRowCount;
    }

    /**
     * Sets a new definition of a row count check.
     * @param profileRowCount Row count check.
     */
    public void setProfileRowCount(TableRowCountCheckSpec profileRowCount) {
        this.setDirtyIf(!Objects.equals(this.profileRowCount, profileRowCount));
        this.profileRowCount = profileRowCount;
        propagateHierarchyIdToField(profileRowCount, "profile_row_count");
    }

    /**
     * Returns the row count anomaly 30 days check.
     * @return Row count anomaly 30 days check.
     */
    public TableAnomalyDifferencingRowCount30DaysCheckSpec getProfileRowCountAnomalyDifferencing30Days() {
        return profileRowCountAnomalyDifferencing30Days;
    }

    /**
     * Sets a new row count anomaly 30 days check.
     * @param profileRowCountAnomalyDifferencing30Days Row count anomaly 30 days check.
     */
    public void setProfileRowCountAnomalyDifferencing30Days(TableAnomalyDifferencingRowCount30DaysCheckSpec profileRowCountAnomalyDifferencing30Days) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountAnomalyDifferencing30Days, profileRowCountAnomalyDifferencing30Days));
        this.profileRowCountAnomalyDifferencing30Days = profileRowCountAnomalyDifferencing30Days;
        propagateHierarchyIdToField(profileRowCountAnomalyDifferencing30Days, "profile_row_count_anomaly_differencing_30_days");
    }

    /**
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableAnomalyDifferencingRowCountCheckSpec getProfileRowCountAnomalyDifferencing() {
        return profileRowCountAnomalyDifferencing;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param profileRowCountAnomalyDifferencing Row count anomaly 60 days check.
     */
    public void setProfileRowCountAnomalyDifferencing(TableAnomalyDifferencingRowCountCheckSpec profileRowCountAnomalyDifferencing) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountAnomalyDifferencing, profileRowCountAnomalyDifferencing));
        this.profileRowCountAnomalyDifferencing = profileRowCountAnomalyDifferencing;
        propagateHierarchyIdToField(profileRowCountAnomalyDifferencing, "profile_row_count_anomaly_differencing");
    }

    /**
     * Returns the row count change check.
     * @return Row count change check.
     */
    public TableChangeRowCountCheckSpec getProfileRowCountChange() {
        return profileRowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param profileRowCountChange Row count change check.
     */
    public void setProfileRowCountChange(TableChangeRowCountCheckSpec profileRowCountChange) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountChange, profileRowCountChange));
        this.profileRowCountChange = profileRowCountChange;
        propagateHierarchyIdToField(profileRowCountChange, "profile_row_count_change");
    }

    /**
     * Returns the row count change since yesterday check.
     * @return Row count change since yesterday check.
     */
    public TableChangeRowCountSinceYesterdayCheckSpec getProfileRowCountChangeYesterday() {
        return profileRowCountChangeYesterday;
    }

    /**
     * Sets a new row count change since yesterday check.
     * @param profileRowCountChangeYesterday Row count change since yesterday check.
     */
    public void setProfileRowCountChangeYesterday(TableChangeRowCountSinceYesterdayCheckSpec profileRowCountChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountChangeYesterday, profileRowCountChangeYesterday));
        this.profileRowCountChangeYesterday = profileRowCountChangeYesterday;
        propagateHierarchyIdToField(profileRowCountChangeYesterday, "profile_row_count_change_yesterday");
    }

    /**
     * Returns the row count change since 7 days check.
     * @return Row count change since 7 days check.
     */
    public TableChangeRowCountSince7DaysCheckSpec getProfileRowCountChange7Days() {
        return profileRowCountChange7Days;
    }

    /**
     * Sets a new row count change since 7 days check.
     * @param profileRowCountChange7Days Row count change since 7 days check.
     */
    public void setProfileRowCountChange7Days(TableChangeRowCountSince7DaysCheckSpec profileRowCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountChange7Days, profileRowCountChange7Days));
        this.profileRowCountChange7Days = profileRowCountChange7Days;
        propagateHierarchyIdToField(profileRowCountChange7Days, "profile_row_count_change_7_days");
    }

    /**
     * Returns the row count change since 30 days check.
     * @return Row count change since 30 days check.
     */
    public TableChangeRowCountSince30DaysCheckSpec getProfileRowCountChange30Days() {
        return profileRowCountChange30Days;
    }

    /**
     * Sets a new row count change since 30 days check.
     * @param profileRowCountChange30Days Row count change since 30 days check.
     */
    public void setProfileRowCountChange30Days(TableChangeRowCountSince30DaysCheckSpec profileRowCountChange30Days) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountChange30Days, profileRowCountChange30Days));
        this.profileRowCountChange30Days = profileRowCountChange30Days;
        propagateHierarchyIdToField(profileRowCountChange30Days, "profile_row_count_change_30_days");
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
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public TableVolumeProfilingChecksSpec deepClone() {
        return (TableVolumeProfilingChecksSpec)super.deepClone();
    }
}
