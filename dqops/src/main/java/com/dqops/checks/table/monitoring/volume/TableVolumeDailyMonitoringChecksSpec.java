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
package com.dqops.checks.table.monitoring.volume;

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
 * Container of table level daily monitoring for volume data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableVolumeDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableVolumeDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_row_count", o -> o.dailyRowCount);
            put("daily_row_count_anomaly", o -> o.dailyRowCountAnomaly);

            put("daily_row_count_change", o -> o.dailyRowCountChange);
            put("daily_row_count_change_1_day", o -> o.dailyRowCountChange1Day);
            put("daily_row_count_change_7_days", o -> o.dailyRowCountChange7Days);
            put("daily_row_count_change_30_days", o -> o.dailyRowCountChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that the tested table has at least a minimum accepted number of rows. " +
            "The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. " +
            "When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows." +
            "Stores the most recent captured row count value for each day when the row count was evaluated.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec dailyRowCount;

    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during the last 90 days.")
    private TableRowCountAnomalyDifferencingCheckSpec dailyRowCountAnomaly;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.")
    private TableRowCountChangeCheckSpec dailyRowCountChange;

    @JsonProperty("daily_row_count_change_1_day")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.")
    private TableRowCountChange1DayCheckSpec dailyRowCountChange1Day;

    @JsonProperty("daily_row_count_change_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.")
    private TableRowCountChange7DaysCheckSpec dailyRowCountChange7Days;

    @JsonProperty("daily_row_count_change_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.")
    private TableRowCountChange30DaysCheckSpec dailyRowCountChange30Days;


    /**
     * Returns the row count check configuration.
     * @return Row count check specification.
     */
    public TableRowCountCheckSpec getDailyRowCount() {
        return dailyRowCount;
    }

    /**
     * Sets the row count check.
     * @param dailyRowCount New row count check.
     */
    public void setDailyRowCount(TableRowCountCheckSpec dailyRowCount) {
		this.setDirtyIf(!Objects.equals(this.dailyRowCount, dailyRowCount));
        this.dailyRowCount = dailyRowCount;
		this.propagateHierarchyIdToField(dailyRowCount, "daily_row_count");
    }

    /**
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableRowCountAnomalyDifferencingCheckSpec getDailyRowCountAnomaly() {
        return dailyRowCountAnomaly;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param dailyRowCountAnomaly Row count anomaly 60 days check.
     */
    public void setDailyRowCountAnomaly(TableRowCountAnomalyDifferencingCheckSpec dailyRowCountAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountAnomaly, dailyRowCountAnomaly));
        this.dailyRowCountAnomaly = dailyRowCountAnomaly;
        propagateHierarchyIdToField(dailyRowCountAnomaly, "daily_row_count_anomaly");
    }

    /**
     * Returns the row count change check.
     * @return Row count change check.
     */
    public TableRowCountChangeCheckSpec getDailyRowCountChange() {
        return dailyRowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param dailyRowCountChange Row count change check.
     */
    public void setDailyRowCountChange(TableRowCountChangeCheckSpec dailyRowCountChange) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountChange, dailyRowCountChange));
        this.dailyRowCountChange = dailyRowCountChange;
        propagateHierarchyIdToField(dailyRowCountChange, "daily_row_count_change");
    }

    /**
     * Returns the row count change since yesterday check.
     * @return Row count change since yesterday check.
     */
    public TableRowCountChange1DayCheckSpec getDailyRowCountChange1Day() {
        return dailyRowCountChange1Day;
    }

    /**
     * Sets a new row count change since yesterday check.
     * @param dailyRowCountChange1Day Row count change since yesterday check.
     */
    public void setDailyRowCountChange1Day(TableRowCountChange1DayCheckSpec dailyRowCountChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountChange1Day, dailyRowCountChange1Day));
        this.dailyRowCountChange1Day = dailyRowCountChange1Day;
        propagateHierarchyIdToField(dailyRowCountChange1Day, "daily_row_count_change_1_day");
    }

    /**
     * Returns the row count change since 7 days check.
     * @return Row count change since 7 days check.
     */
    public TableRowCountChange7DaysCheckSpec getDailyRowCountChange7Days() {
        return dailyRowCountChange7Days;
    }

    /**
     * Sets a new row count change since 7 days check.
     * @param dailyRowCountChange7Days Row count change since 7 days check.
     */
    public void setDailyRowCountChange7Days(TableRowCountChange7DaysCheckSpec dailyRowCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountChange7Days, dailyRowCountChange7Days));
        this.dailyRowCountChange7Days = dailyRowCountChange7Days;
        propagateHierarchyIdToField(dailyRowCountChange7Days, "daily_row_count_change_7_days");
    }

    /**
     * Returns the row count change since 30 days check.
     * @return Row count change since 30 days check.
     */
    public TableRowCountChange30DaysCheckSpec getDailyRowCountChange30Days() {
        return dailyRowCountChange30Days;
    }

    /**
     * Sets a new row count change since 30 days check.
     * @param dailyRowCountChange30Days Row count change since 30 days check.
     */
    public void setDailyRowCountChange30Days(TableRowCountChange30DaysCheckSpec dailyRowCountChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountChange30Days, dailyRowCountChange30Days));
        this.dailyRowCountChange30Days = dailyRowCountChange30Days;
        propagateHierarchyIdToField(dailyRowCountChange30Days, "daily_row_count_change_30_days");
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
    public TableVolumeDailyMonitoringChecksSpec deepClone() {
        return (TableVolumeDailyMonitoringChecksSpec)super.deepClone();
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
        return CheckType.monitoring;
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

    public static class TableVolumeDailyMonitoringChecksSpecSampleFactory implements SampleValueFactory<TableVolumeDailyMonitoringChecksSpec> {
        @Override
        public TableVolumeDailyMonitoringChecksSpec createSample() {
            return new TableVolumeDailyMonitoringChecksSpec() {{
                setDailyRowCount(new TableRowCountCheckSpec.TableRowCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
