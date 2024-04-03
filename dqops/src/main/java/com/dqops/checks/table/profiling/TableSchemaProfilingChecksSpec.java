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
import com.dqops.checks.table.checkspecs.schema.*;
import com.dqops.connectors.DataTypeCategory;
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
 * Container of built-in preconfigured volume data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSchemaProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSchemaProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_column_count", o -> o.profileColumnCount);
            put("profile_column_count_changed", o -> o.profileColumnCountChanged);
            put("profile_column_list_changed", o -> o.profileColumnListChanged);
            put("profile_column_list_or_order_changed", o -> o.profileColumnListOrOrderChanged);
            put("profile_column_types_changed", o -> o.profileColumnTypesChanged);
        }
    };

    @JsonPropertyDescription("Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).")
    private TableSchemaColumnCountCheckSpec profileColumnCount;

    @JsonPropertyDescription("Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.")
    private TableSchemaColumnCountChangedCheckSpec profileColumnCountChanged;

    @JsonPropertyDescription("Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.")
    private TableSchemaColumnListChangedCheckSpec profileColumnListChanged;

    @JsonPropertyDescription("Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.")
    private TableSchemaColumnListOrOrderChangedCheckSpec profileColumnListOrOrderChanged;

    @JsonPropertyDescription("Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.")
    private TableSchemaColumnTypesChangedCheckSpec profileColumnTypesChanged;

    /**
     * Returns a column count check.
     * @return Column count check.
     */
    public TableSchemaColumnCountCheckSpec getProfileColumnCount() {
        return profileColumnCount;
    }

    /**
     * Sets a new definition of a column count check.
     * @param profileColumnCount Column count check.
     */
    public void setProfileColumnCount(TableSchemaColumnCountCheckSpec profileColumnCount) {
        this.setDirtyIf(!Objects.equals(this.profileColumnCount, profileColumnCount));
        this.profileColumnCount = profileColumnCount;
        propagateHierarchyIdToField(profileColumnCount, "profile_column_count");
    }

    /**
     * Returns the configuration of a column count changed check.
     * @return Column count changed check.
     */
    public TableSchemaColumnCountChangedCheckSpec getProfileColumnCountChanged() {
        return profileColumnCountChanged;
    }

    /**
     * Sets the new definition of a column count changed check.
     * @param profileColumnCountChanged Column count changed check.
     */
    public void setProfileColumnCountChanged(TableSchemaColumnCountChangedCheckSpec profileColumnCountChanged) {
        this.setDirtyIf(!Objects.equals(this.profileColumnCountChanged, profileColumnCountChanged));
        this.profileColumnCountChanged = profileColumnCountChanged;
        propagateHierarchyIdToField(profileColumnCountChanged, "profile_column_count_changed");
    }

    /**
     * Returns the configuration of the column list changed check.
     * @return Column list changed check.
     */
    public TableSchemaColumnListChangedCheckSpec getProfileColumnListChanged() {
        return profileColumnListChanged;
    }

    /**
     * Sets the check that detects changes to the list of columns.
     * @param profileColumnListChanged Column list changed check.
     */
    public void setProfileColumnListChanged(TableSchemaColumnListChangedCheckSpec profileColumnListChanged) {
        this.setDirtyIf(!Objects.equals(this.profileColumnListChanged, profileColumnListChanged));
        this.profileColumnListChanged = profileColumnListChanged;
        propagateHierarchyIdToField(profileColumnListChanged, "profile_column_list_changed");
    }

    /**
     * Returns the check that detects if the list or order of columns have changed.
     * @return List or order of columns changed check.
     */
    public TableSchemaColumnListOrOrderChangedCheckSpec getProfileColumnListOrOrderChanged() {
        return profileColumnListOrOrderChanged;
    }

    /**
     * Sets the check that detects if the list or order of columns have changed.
     * @param profileColumnListOrOrderChanged List or order of columns changed check.
     */
    public void setProfileColumnListOrOrderChanged(TableSchemaColumnListOrOrderChangedCheckSpec profileColumnListOrOrderChanged) {
        this.setDirtyIf(!Objects.equals(this.profileColumnListOrOrderChanged, profileColumnListOrOrderChanged));
        this.profileColumnListOrOrderChanged = profileColumnListOrOrderChanged;
        propagateHierarchyIdToField(profileColumnListOrOrderChanged, "profile_column_list_or_order_changed");
    }

    /**
     * Returns the column types changed check.
     * @return Column types changed check.
     */
    public TableSchemaColumnTypesChangedCheckSpec getProfileColumnTypesChanged() {
        return profileColumnTypesChanged;
    }

    /**
     * Sets the column types changed check.
     * @param profileColumnTypesChanged Column types changed check.
     */
    public void setProfileColumnTypesChanged(TableSchemaColumnTypesChangedCheckSpec profileColumnTypesChanged) {
        this.setDirtyIf(!Objects.equals(this.profileColumnTypesChanged, profileColumnTypesChanged));
        this.profileColumnTypesChanged = profileColumnTypesChanged;
        propagateHierarchyIdToField(profileColumnTypesChanged, "profile_column_types_changed");
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
    public TableSchemaProfilingChecksSpec deepClone() {
        return (TableSchemaProfilingChecksSpec)super.deepClone();
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
}
