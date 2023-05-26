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
package ai.dqo.checks.table.profiling;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.volume.*;
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
 * Container of built-in preconfigured volume data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableProfilingVolumeChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableProfilingVolumeChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("row_count", o -> o.rowCount);
            put("row_count_anomaly_7_days", o -> o.rowCountAnomaly7Days);
            put("row_count_anomaly_30_days", o -> o.rowCountAnomaly30Days);
            put("row_count_anomaly_60_days", o -> o.rowCountAnomaly60Days);
            put("row_count_change", o -> o.rowCountChange);
            put("row_count_change_yesterday", o -> o.rowCountChangeYesterday);
            put("row_count_change_7_days", o -> o.rowCountChange7Days);
            put("row_count_change_30_days", o -> o.rowCountChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that the number of rows in a table does not exceed the minimum accepted count.")
    private TableRowCountCheckSpec rowCount;

    @JsonProperty("row_count_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.")
    private TableAnomalyRowCountChange7DaysCheckSpec rowCountAnomaly7Days;

    @JsonProperty("row_count_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.")
    private TableAnomalyRowCountChange30DaysCheckSpec rowCountAnomaly30Days;

    @JsonProperty("row_count_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.")
    private TableAnomalyRowCountChange60DaysCheckSpec rowCountAnomaly60Days;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.")
    private TableChangeRowCountCheckSpec rowCountChange;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.")
    private TableChangeRowCountSinceYesterdayCheckSpec rowCountChangeYesterday;

    @JsonProperty("row_count_change_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.")
    private TableChangeRowCountSince7DaysCheckSpec rowCountChange7Days;

    @JsonProperty("row_count_change_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.")
    private TableChangeRowCountSince30DaysCheckSpec rowCountChange30Days;

    
    /**
     * Returns a row count check.
     * @return Row count check.
     */
    public TableRowCountCheckSpec getRowCount() {
        return rowCount;
    }

    /**
     * Sets a new definition of a row count check.
     * @param rowCount Row count check.
     */
    public void setRowCount(TableRowCountCheckSpec rowCount) {
        this.setDirtyIf(!Objects.equals(this.rowCount, rowCount));
        this.rowCount = rowCount;
        propagateHierarchyIdToField(rowCount, "row_count");
    }

    /**
     * Returns the row count anomaly 7 days check.
     * @return Row count anomaly 7 days check.
     */
    public TableAnomalyRowCountChange7DaysCheckSpec getRowCountAnomaly7Days() {
        return rowCountAnomaly7Days;
    }

    /**
     * Sets a new row count anomaly 7 days check.
     * @param rowCountAnomaly7Days Row count anomaly 7 days check.
     */
    public void setRowCountAnomaly7Days(TableAnomalyRowCountChange7DaysCheckSpec rowCountAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.rowCountAnomaly7Days, rowCountAnomaly7Days));
        this.rowCountAnomaly7Days = rowCountAnomaly7Days;
        propagateHierarchyIdToField(rowCountAnomaly7Days, "row_count_anomaly_7_days");
    }

    /**
     * Returns the row count anomaly 30 days check.
     * @return Row count anomaly 30 days check.
     */
    public TableAnomalyRowCountChange30DaysCheckSpec getRowCountAnomaly30Days() {
        return rowCountAnomaly30Days;
    }

    /**
     * Sets a new row count anomaly 30 days check.
     * @param rowCountAnomaly30Days Row count anomaly 30 days check.
     */
    public void setRowCountAnomaly30Days(TableAnomalyRowCountChange30DaysCheckSpec rowCountAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.rowCountAnomaly30Days, rowCountAnomaly30Days));
        this.rowCountAnomaly30Days = rowCountAnomaly30Days;
        propagateHierarchyIdToField(rowCountAnomaly30Days, "row_count_anomaly_30_days");
    }

    /**
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableAnomalyRowCountChange60DaysCheckSpec getRowCountAnomaly60Days() {
        return rowCountAnomaly60Days;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param rowCountAnomaly60Days Row count anomaly 60 days check.
     */
    public void setRowCountAnomaly60Days(TableAnomalyRowCountChange60DaysCheckSpec rowCountAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.rowCountAnomaly60Days, rowCountAnomaly60Days));
        this.rowCountAnomaly60Days = rowCountAnomaly60Days;
        propagateHierarchyIdToField(rowCountAnomaly60Days, "row_count_anomaly_60_days");
    }

    /**
     * Returns the row count change check.
     * @return Row count change check.
     */
    public TableChangeRowCountCheckSpec getRowCountChange() {
        return rowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param rowCountChange Row count change check.
     */
    public void setRowCountChange(TableChangeRowCountCheckSpec rowCountChange) {
        this.setDirtyIf(!Objects.equals(this.rowCountChange, rowCountChange));
        this.rowCountChange = rowCountChange;
        propagateHierarchyIdToField(rowCountChange, "row_count_change");
    }

    /**
     * Returns the row count change since yesterday check.
     * @return Row count change since yesterday check.
     */
    public TableChangeRowCountSinceYesterdayCheckSpec getRowCountChangeYesterday() {
        return rowCountChangeYesterday;
    }

    /**
     * Sets a new row count change since yesterday check.
     * @param rowCountChangeYesterday Row count change since yesterday check.
     */
    public void setRowCountChangeYesterday(TableChangeRowCountSinceYesterdayCheckSpec rowCountChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.rowCountChangeYesterday, rowCountChangeYesterday));
        this.rowCountChangeYesterday = rowCountChangeYesterday;
        propagateHierarchyIdToField(rowCountChangeYesterday, "row_count_change_yesterday");
    }

    /**
     * Returns the row count change since 7 days check.
     * @return Row count change since 7 days check.
     */
    public TableChangeRowCountSince7DaysCheckSpec getRowCountChange7Days() {
        return rowCountChange7Days;
    }

    /**
     * Sets a new row count change since 7 days check.
     * @param rowCountChange7Days Row count change since 7 days check.
     */
    public void setRowCountChange7Days(TableChangeRowCountSince7DaysCheckSpec rowCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.rowCountChange7Days, rowCountChange7Days));
        this.rowCountChange7Days = rowCountChange7Days;
        propagateHierarchyIdToField(rowCountChange7Days, "row_count_change_7_days");
    }

    /**
     * Returns the row count change since 30 days check.
     * @return Row count change since 30 days check.
     */
    public TableChangeRowCountSince30DaysCheckSpec getRowCountChange30Days() {
        return rowCountChange30Days;
    }

    /**
     * Sets a new row count change since 30 days check.
     * @param rowCountChange30Days Row count change since 30 days check.
     */
    public void setRowCountChange30Days(TableChangeRowCountSince30DaysCheckSpec rowCountChange30Days) {
        this.setDirtyIf(!Objects.equals(this.rowCountChange30Days, rowCountChange30Days));
        this.rowCountChange30Days = rowCountChange30Days;
        propagateHierarchyIdToField(rowCountChange30Days, "row_count_change_30_days");
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
