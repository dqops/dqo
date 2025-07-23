/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.profiling;

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
 * Container of built-in preconfigured data quality checks on a column level that are checking for integrity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_lookup_key_not_found", o -> o.profileLookupKeyNotFound);
            put("profile_lookup_key_found_percent", o -> o.profileLookupKeyFoundPercent);
        }
    };

    @JsonPropertyDescription("Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys.")
    private ColumnIntegrityLookupKeyNotFoundCountCheckSpec profileLookupKeyNotFound;

    @JsonPropertyDescription("Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec profileLookupKeyFoundPercent;

    /**
     * Returns an integrity value not match count check specification.
     * @return Integrity value not match count check specification.
     */
    public ColumnIntegrityLookupKeyNotFoundCountCheckSpec getProfileLookupKeyNotFound() {
        return profileLookupKeyNotFound;
    }

    /**
     * Sets integrity value not match count check specification.
     * @param profileLookupKeyNotFound Integrity value not match count check specification.
     */
    public void setProfileLookupKeyNotFound(ColumnIntegrityLookupKeyNotFoundCountCheckSpec profileLookupKeyNotFound) {
        this.setDirtyIf(!Objects.equals(this.profileLookupKeyNotFound, profileLookupKeyNotFound));
        this.profileLookupKeyNotFound = profileLookupKeyNotFound;
        propagateHierarchyIdToField(profileLookupKeyNotFound, "profile_lookup_key_not_found");
    }

    /**
     * Returns an integrity value match percent check specification.
     * @return Integrity value match percent check specification.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getProfileLookupKeyFoundPercent() {
        return profileLookupKeyFoundPercent;
    }

    /**
     * Sets integrity value match percent check specification.
     * @param profileLookupKeyFoundPercent Integrity value match percent check specification.
     */
    public void setProfileLookupKeyFoundPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec profileLookupKeyFoundPercent) {
        this.setDirtyIf(!Objects.equals(this.profileLookupKeyFoundPercent, profileLookupKeyFoundPercent));
        this.profileLookupKeyFoundPercent = profileLookupKeyFoundPercent;
        propagateHierarchyIdToField(profileLookupKeyFoundPercent, "profile_lookup_key_found_percent");
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