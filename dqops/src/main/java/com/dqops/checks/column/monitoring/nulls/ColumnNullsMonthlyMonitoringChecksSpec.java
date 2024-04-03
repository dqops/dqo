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
package com.dqops.checks.column.monitoring.nulls;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.nulls.ColumnNotNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNotNullsPercentCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsPercentCheckSpec;
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
 * Container of nulls data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_nulls_count", o -> o.monthlyNullsCount);
            put("monthly_nulls_percent", o -> o.monthlyNullsPercent);
            put("monthly_not_nulls_count", o -> o.monthlyNotNullsCount);
            put("monthly_not_nulls_percent", o -> o.monthlyNotNullsPercent);
        }
    };

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a max_count threshold.. Stores the most recent count check result for each month when the data quality check was evaluated.")
    private ColumnNullsCountCheckSpec monthlyNullsCount;

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a max_percent threshold. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnNullsPercentCheckSpec monthlyNullsPercent;

    @JsonPropertyDescription("Detects empty columns that contain only null values. Counts the number of rows that have non-null values. Raises a data quality issue when the count of non-null values is below min_count. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnNotNullsCountCheckSpec monthlyNotNullsCount;

    @JsonPropertyDescription("Detects incomplete columns that contain too few non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is below min_percentage. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnNotNullsPercentCheckSpec monthlyNotNullsPercent;

    /**
     * Returns a nulls count check specification.
     * @return Nulls count check specification.
     */
    public ColumnNullsCountCheckSpec getMonthlyNullsCount() {
        return monthlyNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param monthlyNullsCount Nulls count check specification.
     */
    public void setMonthlyNullsCount(ColumnNullsCountCheckSpec monthlyNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullsCount, monthlyNullsCount));
        this.monthlyNullsCount = monthlyNullsCount;
        propagateHierarchyIdToField(monthlyNullsCount, "monthly_nulls_count");
    }

    /**
     * Returns a nulls percent check specification.
     * @return Nulls percent check specification.
     */
    public ColumnNullsPercentCheckSpec getMonthlyNullsPercent() {
        return monthlyNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param monthlyNullsPercent Nulls percent check specification.
     */
    public void setMonthlyNullsPercent(ColumnNullsPercentCheckSpec monthlyNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullsPercent, monthlyNullsPercent));
        this.monthlyNullsPercent = monthlyNullsPercent;
        propagateHierarchyIdToField(monthlyNullsPercent, "monthly_nulls_percent");
    }

    /**
     * Returns not nulls count check specification.
     * @return Not nulls count check specification.
     */
    public ColumnNotNullsCountCheckSpec getMonthlyNotNullsCount() {
        return monthlyNotNullsCount;
    }

    /**
     * Sets a new definition of a not nulls count check.
     * @param monthlyNotNullsCount Not nulls count check specification.
     */
    public void setMonthlyNotNullsCount(ColumnNotNullsCountCheckSpec monthlyNotNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyNotNullsCount, monthlyNotNullsCount));
        this.monthlyNotNullsCount = monthlyNotNullsCount;
        propagateHierarchyIdToField(monthlyNotNullsCount, "monthly_not_nulls_count");
    }

    /**
     * Returns a not nulls percent check specification.
     * @return Not nulls percent check specification.
     */
    public ColumnNotNullsPercentCheckSpec getMonthlyNotNullsPercent() {
        return monthlyNotNullsPercent;
    }

    /**
     * Sets a new definition of a not nulls percent check.
     * @param monthlyNotNullsPercent Not nulls percent check specification.
     */
    public void setMonthlyNotNullsPercent(ColumnNotNullsPercentCheckSpec monthlyNotNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNotNullsPercent, monthlyNotNullsPercent));
        this.monthlyNotNullsPercent = monthlyNotNullsPercent;
        propagateHierarchyIdToField(monthlyNotNullsPercent, "monthly_not_nulls_percent");
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
    public ColumnNullsMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnNullsMonthlyMonitoringChecksSpec)super.deepClone();
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
        return CheckTimeScale.monthly;
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

    public static class ColumnNullsMonthlyMonitoringChecksSpecSampleFactory implements SampleValueFactory<ColumnNullsMonthlyMonitoringChecksSpec> {
        @Override
        public ColumnNullsMonthlyMonitoringChecksSpec createSample() {
            return new ColumnNullsMonthlyMonitoringChecksSpec() {{
                setMonthlyNullsCount(new ColumnNullsCountCheckSpec.ColumnNullsCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
