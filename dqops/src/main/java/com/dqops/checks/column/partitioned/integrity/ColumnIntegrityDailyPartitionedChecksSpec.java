/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.partitioned.integrity;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.integrity.ColumnIntegrityForeignKeyMatchPercentCheckSpec;
import com.dqops.checks.column.checkspecs.integrity.ColumnIntegrityLookupKeyNotFoundCountCheckSpec;
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
 * Container of integrity data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_lookup_key_not_found", o -> o.dailyPartitionLookupKeyNotFound);
            put("daily_partition_lookup_key_found_percent", o -> o.dailyPartitionLookupKeyFoundPercent);
        }
    };

    @JsonPropertyDescription("Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys. Stores a separate data quality check result for each daily partition.")
    private ColumnIntegrityLookupKeyNotFoundCountCheckSpec dailyPartitionLookupKeyNotFound;

    @JsonPropertyDescription("Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join. Stores a separate data quality check result for each daily partition.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyPartitionLookupKeyFoundPercent;

    /**
     * Returns an integrity value not match count check.
     * @return Integrity value not match count check.
     */
    public ColumnIntegrityLookupKeyNotFoundCountCheckSpec getDailyPartitionLookupKeyNotFound() {
        return dailyPartitionLookupKeyNotFound;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param dailyPartitionLookupKeyNotFound Integrity value not match count check.
     */
    public void setDailyPartitionLookupKeyNotFound(ColumnIntegrityLookupKeyNotFoundCountCheckSpec dailyPartitionLookupKeyNotFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionLookupKeyNotFound, dailyPartitionLookupKeyNotFound));
        this.dailyPartitionLookupKeyNotFound = dailyPartitionLookupKeyNotFound;
        propagateHierarchyIdToField(dailyPartitionLookupKeyNotFound, "daily_partition_lookup_key_not_found");
    }

    /**
     * Returns an integrity value match percent check.
     * @return Integrity value match percent check.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getDailyPartitionLookupKeyFoundPercent() {
        return dailyPartitionLookupKeyFoundPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param dailyPartitionLookupKeyFoundPercent Integrity value match percent check.
     */
    public void setDailyPartitionLookupKeyFoundPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyPartitionLookupKeyFoundPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionLookupKeyFoundPercent, dailyPartitionLookupKeyFoundPercent));
        this.dailyPartitionLookupKeyFoundPercent = dailyPartitionLookupKeyFoundPercent;
        propagateHierarchyIdToField(dailyPartitionLookupKeyFoundPercent, "daily_partition_lookup_key_found_percent");
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
}
