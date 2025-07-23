/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.uniqueness.TableDuplicateRecordCountCheckSpec;
import com.dqops.checks.table.checkspecs.uniqueness.TableDuplicateRecordPercentCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured uniqueness data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableUniquenessProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableUniquenessProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_duplicate_record_count", o -> o.profileDuplicateRecordCount);
            put("profile_duplicate_record_percent", o -> o.profileDuplicateRecordPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.")
    private TableDuplicateRecordCountCheckSpec profileDuplicateRecordCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.")
    private TableDuplicateRecordPercentCheckSpec profileDuplicateRecordPercent;


    /**
     * Returns a duplicate record count check.
     * @return Duplicate record count check.
     */
    public TableDuplicateRecordCountCheckSpec getProfileDuplicateRecordCount() {
        return profileDuplicateRecordCount;
    }

    /**
     * Sets a new definition of a duplicate record count check.
     * @param profileDuplicateRecordCount Duplicate record count check.
     */
    public void setProfileDuplicateRecordCount(TableDuplicateRecordCountCheckSpec profileDuplicateRecordCount) {
        this.setDirtyIf(!Objects.equals(this.profileDuplicateRecordCount, profileDuplicateRecordCount));
        this.profileDuplicateRecordCount = profileDuplicateRecordCount;
        propagateHierarchyIdToField(profileDuplicateRecordCount, "profile_duplicate_record_count");
    }

    /**
     * Returns a duplicate record percent check.
     * @return Duplicate record percent check.
     */
    public TableDuplicateRecordPercentCheckSpec getProfileDuplicateRecordPercent() {
        return profileDuplicateRecordPercent;
    }

    /**
     * Sets a new definition of a duplicate record percent check.
     * @param profileDuplicateRecordPercent Duplicate record percent check.
     */
    public void setProfileDuplicateRecordPercent(TableDuplicateRecordPercentCheckSpec profileDuplicateRecordPercent) {
        this.setDirtyIf(!Objects.equals(this.profileDuplicateRecordPercent, profileDuplicateRecordPercent));
        this.profileDuplicateRecordPercent = profileDuplicateRecordPercent;
        propagateHierarchyIdToField(profileDuplicateRecordPercent, "profile_duplicate_record_percent");
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
    public TableUniquenessProfilingChecksSpec deepClone() {
        return (TableUniquenessProfilingChecksSpec)super.deepClone();
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

    public static class TableUniquenessProfilingChecksSpecSampleFactory implements SampleValueFactory<TableUniquenessProfilingChecksSpec> {
        @Override
        public TableUniquenessProfilingChecksSpec createSample() {
            return new TableUniquenessProfilingChecksSpec() {{
                setProfileDuplicateRecordCount(new TableDuplicateRecordCountCheckSpec.TableDuplicateRecordCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
