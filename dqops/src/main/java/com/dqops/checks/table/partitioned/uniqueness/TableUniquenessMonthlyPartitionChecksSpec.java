/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.partitioned.uniqueness;

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
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of table level monthly partition for uniqueness data quality checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableUniquenessMonthlyPartitionChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableUniquenessMonthlyPartitionChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_duplicate_record_count", o -> o.monthlyPartitionDuplicateRecordCount);
            put("monthly_partition_duplicate_record_percent", o -> o.monthlyPartitionDuplicateRecordPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordCountCheckSpec monthlyPartitionDuplicateRecordCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordPercentCheckSpec monthlyPartitionDuplicateRecordPercent;

    /**
     * Returns a duplicate record count check.
     * @return Duplicate record count check.
     */
    public TableDuplicateRecordCountCheckSpec getMonthlyPartitionDuplicateRecordCount() {
        return monthlyPartitionDuplicateRecordCount;
    }

    /**
     * Sets a new definition of a duplicate record count check.
     * @param monthlyPartitionDuplicateRecordCount Duplicate record count check.
     */
    public void setMonthlyPartitionDuplicateRecordCount(TableDuplicateRecordCountCheckSpec monthlyPartitionDuplicateRecordCount) {
		this.setDirtyIf(!Objects.equals(this.monthlyPartitionDuplicateRecordCount, monthlyPartitionDuplicateRecordCount));
        this.monthlyPartitionDuplicateRecordCount = monthlyPartitionDuplicateRecordCount;
		this.propagateHierarchyIdToField(monthlyPartitionDuplicateRecordCount, "monthly_partition_duplicate_record_count");
    }

    /**
     * Returns a duplicate record percent check.
     * @return Duplicate record percent check.
     */
    public TableDuplicateRecordPercentCheckSpec getMonthlyPartitionDuplicateRecordPercent() {
        return monthlyPartitionDuplicateRecordPercent;
    }

    /**
     * Sets a new definition of a duplicate record percent check.
     * @param monthlyPartitionDuplicateRecordPercent Duplicate record percent check.
     */
    public void setMonthlyPartitionDuplicateRecordPercent(TableDuplicateRecordPercentCheckSpec monthlyPartitionDuplicateRecordPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDuplicateRecordPercent, monthlyPartitionDuplicateRecordPercent));
        this.monthlyPartitionDuplicateRecordPercent = monthlyPartitionDuplicateRecordPercent;
        this.propagateHierarchyIdToField(monthlyPartitionDuplicateRecordPercent, "monthly_partition_duplicate_record_percent");
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
    public TableUniquenessMonthlyPartitionChecksSpec deepClone() {
        return (TableUniquenessMonthlyPartitionChecksSpec)super.deepClone();
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
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }

    public static class TableUniquenessMonthlyPartitionChecksSpecSampleFactory implements SampleValueFactory<TableUniquenessMonthlyPartitionChecksSpec> {
        @Override
        public TableUniquenessMonthlyPartitionChecksSpec createSample() {
            return new TableUniquenessMonthlyPartitionChecksSpec() {{
                setMonthlyPartitionDuplicateRecordCount(new TableDuplicateRecordCountCheckSpec.TableDuplicateRecordCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
