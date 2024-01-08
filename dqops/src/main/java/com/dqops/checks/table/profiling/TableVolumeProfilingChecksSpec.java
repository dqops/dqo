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
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.volume.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
            put("profile_row_count_anomaly", o -> o.profileRowCountAnomaly);

            put("profile_row_count_change", o -> o.profileRowCountChange);
            put("profile_row_count_change_1_day", o -> o.profileRowCountChange1Day);
            put("profile_row_count_change_7_days", o -> o.profileRowCountChange7Days);
            put("profile_row_count_change_30_days", o -> o.profileRowCountChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that the tested table has at least a minimum accepted number of rows. " +
            "The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. " +
            "When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.")
    private TableRowCountCheckSpec profileRowCount;

    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.")
    private TableRowCountAnomalyDifferencingCheckSpec profileRowCountAnomaly;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.")
    private TableRowCountChangeCheckSpec profileRowCountChange;

    @JsonProperty("profile_row_count_change_1_day")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.")
    private TableRowCountChange1DayCheckSpec profileRowCountChange1Day;

    @JsonProperty("profile_row_count_change_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.")
    private TableRowCountChange7DaysCheckSpec profileRowCountChange7Days;

    @JsonProperty("profile_row_count_change_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.")
    private TableRowCountChange30DaysCheckSpec profileRowCountChange30Days;

    
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
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableRowCountAnomalyDifferencingCheckSpec getProfileRowCountAnomaly() {
        return profileRowCountAnomaly;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param profileRowCountAnomaly Row count anomaly 60 days check.
     */
    public void setProfileRowCountAnomaly(TableRowCountAnomalyDifferencingCheckSpec profileRowCountAnomaly) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountAnomaly, profileRowCountAnomaly));
        this.profileRowCountAnomaly = profileRowCountAnomaly;
        propagateHierarchyIdToField(profileRowCountAnomaly, "profile_row_count_anomaly");
    }

    /**
     * Returns the row count change check.
     * @return Row count change check.
     */
    public TableRowCountChangeCheckSpec getProfileRowCountChange() {
        return profileRowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param profileRowCountChange Row count change check.
     */
    public void setProfileRowCountChange(TableRowCountChangeCheckSpec profileRowCountChange) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountChange, profileRowCountChange));
        this.profileRowCountChange = profileRowCountChange;
        propagateHierarchyIdToField(profileRowCountChange, "profile_row_count_change");
    }

    /**
     * Returns the row count change since yesterday check.
     * @return Row count change since yesterday check.
     */
    public TableRowCountChange1DayCheckSpec getProfileRowCountChange1Day() {
        return profileRowCountChange1Day;
    }

    /**
     * Sets a new row count change since yesterday check.
     * @param profileRowCountChange1Day Row count change since yesterday check.
     */
    public void setProfileRowCountChange1Day(TableRowCountChange1DayCheckSpec profileRowCountChange1Day) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountChange1Day, profileRowCountChange1Day));
        this.profileRowCountChange1Day = profileRowCountChange1Day;
        propagateHierarchyIdToField(profileRowCountChange1Day, "profile_row_count_change_1_day");
    }

    /**
     * Returns the row count change since 7 days check.
     * @return Row count change since 7 days check.
     */
    public TableRowCountChange7DaysCheckSpec getProfileRowCountChange7Days() {
        return profileRowCountChange7Days;
    }

    /**
     * Sets a new row count change since 7 days check.
     * @param profileRowCountChange7Days Row count change since 7 days check.
     */
    public void setProfileRowCountChange7Days(TableRowCountChange7DaysCheckSpec profileRowCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.profileRowCountChange7Days, profileRowCountChange7Days));
        this.profileRowCountChange7Days = profileRowCountChange7Days;
        propagateHierarchyIdToField(profileRowCountChange7Days, "profile_row_count_change_7_days");
    }

    /**
     * Returns the row count change since 30 days check.
     * @return Row count change since 30 days check.
     */
    public TableRowCountChange30DaysCheckSpec getProfileRowCountChange30Days() {
        return profileRowCountChange30Days;
    }

    /**
     * Sets a new row count change since 30 days check.
     * @param profileRowCountChange30Days Row count change since 30 days check.
     */
    public void setProfileRowCountChange30Days(TableRowCountChange30DaysCheckSpec profileRowCountChange30Days) {
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
        return CheckType.profiling;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
    }

    public static class TableVolumeProfilingChecksSpecSampleFactory implements SampleValueFactory<TableVolumeProfilingChecksSpec> {
        @Override
        public TableVolumeProfilingChecksSpec createSample() {
            return new TableVolumeProfilingChecksSpec() {{
                setProfileRowCount(new TableRowCountCheckSpec.TableRowCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
