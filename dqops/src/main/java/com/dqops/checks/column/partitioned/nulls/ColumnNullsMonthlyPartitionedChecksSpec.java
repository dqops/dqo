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
package com.dqops.checks.column.partitioned.nulls;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.nulls.ColumnNotNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNotNullsPercentCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsPercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.docs.SampleValueFactory;
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
        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNullsCountCheckSpec monthlyPartitionNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNullsPercentCheckSpec monthlyPartitionNullsPercent;

    @JsonPropertyDescription("Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNotNullsCountCheckSpec monthlyPartitionNotNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNotNullsPercentCheckSpec monthlyPartitionNotNullsPercent;

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
