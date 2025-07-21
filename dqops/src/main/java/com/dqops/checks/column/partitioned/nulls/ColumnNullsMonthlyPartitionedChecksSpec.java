/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.partitioned.nulls;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.nulls.*;
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
 * Container of nulls data quality partitioned checks on a column level that are checking monthly partitions or rows for each day of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_nulls_count", o -> o.monthlyPartitionNullsCount);
            put("monthly_partition_nulls_percent", o -> o.monthlyPartitionNullsPercent);
            put("monthly_partition_not_nulls_count", o -> o.monthlyPartitionNotNullsCount);
            put("monthly_partition_not_nulls_percent", o -> o.monthlyPartitionNotNullsPercent);

            put("monthly_partition_empty_column_found", o -> o.monthlyPartitionEmptyColumnFound);
        }
    };

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a max_count threshold. Stores a separate data quality check result for each monthly partition.")
    private ColumnNullsCountCheckSpec monthlyPartitionNullsCount;

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a max_percent threshold. Stores a separate data quality check result for each monthly partition.")
    private ColumnNullsPercentCheckSpec monthlyPartitionNullsPercent;

    @JsonPropertyDescription("Verifies that a column contains a minimum number of non-null values. The default value of the *min_count* parameter is 1 to detect at least one value in a monitored column. Raises a data quality issue when the count of non-null values is below min_count. Stores a separate data quality check result for each monthly partition.")
    private ColumnNotNullsCountCheckSpec monthlyPartitionNotNullsCount;

    @JsonPropertyDescription("Detects columns that contain too many non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is above max_percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnNotNullsPercentCheckSpec monthlyPartitionNotNullsPercent;

    @JsonPropertyDescription("Detects empty columns that contain only null values. Counts the number of rows that have non-null values. Raises a data quality issue when the column is empty. Stores a separate data quality check result for each monthly partition.")
    private ColumnEmptyColumnFoundCheckSpec monthlyPartitionEmptyColumnFound;

    /**
     * Returns a nulls count check.
     * @return Nulls count check.
     */
    public ColumnNullsCountCheckSpec getMonthlyPartitionNullsCount() {
        return monthlyPartitionNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param monthlyPartitionNullsCount Nulls count check.
     */
    public void setMonthlyPartitionNullsCount(ColumnNullsCountCheckSpec monthlyPartitionNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullsCount, monthlyPartitionNullsCount));
        this.monthlyPartitionNullsCount = monthlyPartitionNullsCount;
        propagateHierarchyIdToField(monthlyPartitionNullsCount, "monthly_partition_nulls_count");
    }

    /**
     * Returns a nulls percent check.
     * @return Nulls percent check.
     */
    public ColumnNullsPercentCheckSpec getMonthlyPartitionNullsPercent() {
        return monthlyPartitionNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param monthlyPartitionNullsPercent Nulls percent check.
     */
    public void setMonthlyPartitionNullsPercent(ColumnNullsPercentCheckSpec monthlyPartitionNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullsPercent, monthlyPartitionNullsPercent));
        this.monthlyPartitionNullsPercent = monthlyPartitionNullsPercent;
        propagateHierarchyIdToField(monthlyPartitionNullsPercent, "monthly_partition_nulls_percent");
    }

    /**
     * Returns a not nulls count check.
     * @return Not nulls count check.
     */
    public ColumnNotNullsCountCheckSpec getMonthlyPartitionNotNullsCount() {
        return monthlyPartitionNotNullsCount;
    }

    /**
     * Sets a new definition of a not nulls count check.
     * @param monthlyPartitionNotNullsCount Not nulls count check.
     */
    public void setMonthlyPartitionNotNullsCount(ColumnNotNullsCountCheckSpec monthlyPartitionNotNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNotNullsCount, monthlyPartitionNotNullsCount));
        this.monthlyPartitionNotNullsCount = monthlyPartitionNotNullsCount;
        propagateHierarchyIdToField(monthlyPartitionNotNullsCount, "monthly_partition_not_nulls_count");
    }

    /**
     * Returns a not nulls percent check.
     * @return Not nulls percent check.
     */
    public ColumnNotNullsPercentCheckSpec getMonthlyPartitionNotNullsPercent() {
        return monthlyPartitionNotNullsPercent;
    }

    /**
     * Sets a new definition of a not nulls percent check.
     * @param monthlyPartitionNotNullsPercent Not nulls percent check.
     */
    public void setMonthlyPartitionNotNullsPercent(ColumnNotNullsPercentCheckSpec monthlyPartitionNotNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNotNullsPercent, monthlyPartitionNotNullsPercent));
        this.monthlyPartitionNotNullsPercent = monthlyPartitionNotNullsPercent;
        propagateHierarchyIdToField(monthlyPartitionNotNullsPercent, "monthly_partition_not_nulls_percent");
    }

    /**
     * Returns an empty column found check specification.
     * @return Empty column found check specification.
     */
    public ColumnEmptyColumnFoundCheckSpec getMonthlyPartitionEmptyColumnFound() {
        return monthlyPartitionEmptyColumnFound;
    }

    /**
     * Sets an empty column found check specification
     * @param monthlyPartitionEmptyColumnFound Empty column found check specification.
     */
    public void setMonthlyPartitionEmptyColumnFound(ColumnEmptyColumnFoundCheckSpec monthlyPartitionEmptyColumnFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionEmptyColumnFound, monthlyPartitionEmptyColumnFound));
        this.monthlyPartitionEmptyColumnFound = monthlyPartitionEmptyColumnFound;
        propagateHierarchyIdToField(monthlyPartitionEmptyColumnFound, "monthly_partition_empty_column_found");
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

    public static class ColumnNullsMonthlyPartitionedChecksSpecSampleFactory implements SampleValueFactory<ColumnNullsMonthlyPartitionedChecksSpec> {
        @Override
        public ColumnNullsMonthlyPartitionedChecksSpec createSample() {
            return new ColumnNullsMonthlyPartitionedChecksSpec() {{
                setMonthlyPartitionNullsCount(new ColumnNullsCountCheckSpec.ColumnNullsCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
