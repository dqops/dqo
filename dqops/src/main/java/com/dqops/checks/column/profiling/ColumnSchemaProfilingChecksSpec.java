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
package com.dqops.checks.column.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import com.dqops.checks.column.checkspecs.schema.ColumnSchemaTypeChangedCheckSpec;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking the column schema.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSchemaProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSchemaProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_column_exists", o -> o.profileColumnExists);
            put("profile_column_type_changed", o -> o.profileColumnTypeChanged);
        }
    };

    @JsonPropertyDescription("Checks the metadata of the monitored table and verifies if the column exists.")
    private ColumnSchemaColumnExistsCheckSpec profileColumnExists;

    @JsonPropertyDescription("Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.")
    private ColumnSchemaTypeChangedCheckSpec profileColumnTypeChanged;


    /**
     * Returns a column exists check specification.
     * @return Column exists check specification.
     */
    public ColumnSchemaColumnExistsCheckSpec getProfileColumnExists() {
        return profileColumnExists;
    }

    /**
     * Sets the column exists check specification.
     * @param profileColumnExists Column exists check specification.
     */
    public void setProfileColumnExists(ColumnSchemaColumnExistsCheckSpec profileColumnExists) {
        this.setDirtyIf(!Objects.equals(this.profileColumnExists, profileColumnExists));
        this.profileColumnExists = profileColumnExists;
        propagateHierarchyIdToField(profileColumnExists, "profile_column_exists");
    }

    /**
     * Returns the check configuration that detects if the column type has changed.
     * @return Column type has changed.
     */
    public ColumnSchemaTypeChangedCheckSpec getProfileColumnTypeChanged() {
        return profileColumnTypeChanged;
    }

    /**
     * Sets the check that detects if the column type hash changed.
     * @param profileColumnTypeChanged Column type has changed check.
     */
    public void setProfileColumnTypeChanged(ColumnSchemaTypeChangedCheckSpec profileColumnTypeChanged) {
        this.setDirtyIf(!Objects.equals(this.profileColumnTypeChanged, profileColumnTypeChanged));
        this.profileColumnTypeChanged = profileColumnTypeChanged;
        propagateHierarchyIdToField(profileColumnTypeChanged, "profile_column_type_changed");
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
    public ColumnSchemaProfilingChecksSpec deepClone() {
        return (ColumnSchemaProfilingChecksSpec)super.deepClone();
    }

    /**
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.column;
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