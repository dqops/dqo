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
package com.dqops.checks.table.monitoring.schema;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.schema.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured volume data quality checks on a table level that are executed as a daily monitoring (checkpoint) checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSchemaDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSchemaDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_column_count", o -> o.dailyColumnCount);
            put("daily_column_count_changed", o -> o.dailyColumnCountChanged);
            put("daily_column_list_changed", o -> o.dailyColumnListChanged);
            put("daily_column_list_or_order_changed", o -> o.dailyColumnListOrOrderChanged);
            put("daily_column_types_changed", o -> o.dailyColumnTypesChanged);
        }
    };

    @JsonPropertyDescription("Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.")
    private TableSchemaColumnCountCheckSpec dailyColumnCount;

    @JsonPropertyDescription("Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.")
    private TableSchemaColumnCountChangedCheckSpec dailyColumnCountChanged;

    @JsonPropertyDescription("Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.")
    private TableSchemaColumnListChangedCheckSpec dailyColumnListChanged;

    @JsonPropertyDescription("Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.")
    private TableSchemaColumnListOrOrderChangedCheckSpec dailyColumnListOrOrderChanged;

    @JsonPropertyDescription("Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.")
    private TableSchemaColumnTypesChangedCheckSpec dailyColumnTypesChanged;

    /**
     * Returns a column count check.
     * @return Column count check.
     */
    public TableSchemaColumnCountCheckSpec getDailyColumnCount() {
        return dailyColumnCount;
    }

    /**
     * Sets a new definition of a column count check.
     * @param dailyColumnCount Column count check.
     */
    public void setDailyColumnCount(TableSchemaColumnCountCheckSpec dailyColumnCount) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnCount, dailyColumnCount));
        this.dailyColumnCount = dailyColumnCount;
        propagateHierarchyIdToField(dailyColumnCount, "daily_column_count");
    }

    /**
     * Returns the configuration of a column count changed check.
     * @return Column count changed check.
     */
    public TableSchemaColumnCountChangedCheckSpec getDailyColumnCountChanged() {
        return dailyColumnCountChanged;
    }

    /**
     * Sets the new definition of a column count changed check.
     * @param dailyColumnCountChanged Column count changed check.
     */
    public void setDailyColumnCountChanged(TableSchemaColumnCountChangedCheckSpec dailyColumnCountChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnCountChanged, dailyColumnCountChanged));
        this.dailyColumnCountChanged = dailyColumnCountChanged;
        propagateHierarchyIdToField(dailyColumnCountChanged, "daily_column_count_changed");
    }

    /**
     * Returns the configuration of the column list changed check.
     * @return Column list changed check.
     */
    public TableSchemaColumnListChangedCheckSpec getDailyColumnListChanged() {
        return dailyColumnListChanged;
    }

    /**
     * Sets the check that detects changes to the list of columns.
     * @param dailyColumnListChanged Column list changed check.
     */
    public void setDailyColumnListChanged(TableSchemaColumnListChangedCheckSpec dailyColumnListChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnListChanged, dailyColumnListChanged));
        this.dailyColumnListChanged = dailyColumnListChanged;
        propagateHierarchyIdToField(dailyColumnListChanged, "daily_column_list_changed");
    }

    /**
     * Returns the check that detects if the list or order of columns have changed.
     * @return List or order of columns changed check.
     */
    public TableSchemaColumnListOrOrderChangedCheckSpec getDailyColumnListOrOrderChanged() {
        return dailyColumnListOrOrderChanged;
    }

    /**
     * Sets the check that detects if the list or order of columns have changed.
     * @param dailyColumnListOrOrderChanged List or order of columns changed check.
     */
    public void setDailyColumnListOrOrderChanged(TableSchemaColumnListOrOrderChangedCheckSpec dailyColumnListOrOrderChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnListOrOrderChanged, dailyColumnListOrOrderChanged));
        this.dailyColumnListOrOrderChanged = dailyColumnListOrOrderChanged;
        propagateHierarchyIdToField(dailyColumnListOrOrderChanged, "daily_column_list_or_order_changed");
    }

    /**
     * Returns the column types changed check.
     * @return Column types changed check.
     */
    public TableSchemaColumnTypesChangedCheckSpec getDailyColumnTypesChanged() {
        return dailyColumnTypesChanged;
    }

    /**
     * Sets the column types changed check.
     * @param dailyColumnTypesChanged Column types changed check.
     */
    public void setDailyColumnTypesChanged(TableSchemaColumnTypesChangedCheckSpec dailyColumnTypesChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnTypesChanged, dailyColumnTypesChanged));
        this.dailyColumnTypesChanged = dailyColumnTypesChanged;
        propagateHierarchyIdToField(dailyColumnTypesChanged, "daily_column_types_changed");
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
    public TableSchemaDailyMonitoringChecksSpec deepClone() {
        return (TableSchemaDailyMonitoringChecksSpec)super.deepClone();
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
}
