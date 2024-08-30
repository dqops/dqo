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
package com.dqops.checks.table.partitioned.uniqueness;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.uniqueness.TableDuplicateRecordCountCheckSpec;
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
        }
    };

    @JsonPropertyDescription("Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordCountCheckSpec monthlyPartitionDuplicateRecordCount;

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
