/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.monitoring.schema;

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
 * Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSchemaDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSchemaDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_column_exists", o -> o.dailyColumnExists);
            put("daily_column_type_changed", o -> o.dailyColumnTypeChanged);
        }
    };

    @JsonPropertyDescription("Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.")
    private ColumnSchemaColumnExistsCheckSpec dailyColumnExists;

    @JsonPropertyDescription("Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.")
    private ColumnSchemaTypeChangedCheckSpec dailyColumnTypeChanged;

    /**
     * Returns a column exists check specification.
     * @return Column exists check specification.
     */
    public ColumnSchemaColumnExistsCheckSpec getDailyColumnExists() {
        return dailyColumnExists;
    }

    /**
     * Sets the column exists check specification.
     * @param dailyColumnExists Column exists check specification.
     */
    public void setDailyColumnExists(ColumnSchemaColumnExistsCheckSpec dailyColumnExists) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnExists, dailyColumnExists));
        this.dailyColumnExists = dailyColumnExists;
        propagateHierarchyIdToField(dailyColumnExists, "daily_column_exists");
    }

    /**
     * Returns the check configuration that detects if the column type has changed.
     * @return Column type has changed.
     */
    public ColumnSchemaTypeChangedCheckSpec getDailyColumnTypeChanged() {
        return dailyColumnTypeChanged;
    }

    /**
     * Sets the check that detects if the column type hash changed.
     * @param dailyColumnTypeChanged Column type has changed check.
     */
    public void setDailyColumnTypeChanged(ColumnSchemaTypeChangedCheckSpec dailyColumnTypeChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnTypeChanged, dailyColumnTypeChanged));
        this.dailyColumnTypeChanged = dailyColumnTypeChanged;
        propagateHierarchyIdToField(dailyColumnTypeChanged, "daily_column_type_changed");
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
    public ColumnSchemaDailyMonitoringChecksSpec deepClone() {
        return (ColumnSchemaDailyMonitoringChecksSpec)super.deepClone();
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