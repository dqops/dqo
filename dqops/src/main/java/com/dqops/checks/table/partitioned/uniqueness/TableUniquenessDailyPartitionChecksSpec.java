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
 * Container of table level daily partition for uniqueness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableUniquenessDailyPartitionChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableUniquenessDailyPartitionChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_duplicate_record_count", o -> o.dailyPartitionDuplicateRecordCount);
            put("daily_partition_duplicate_record_percent", o -> o.dailyPartitionDuplicateRecordPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordCountCheckSpec dailyPartitionDuplicateRecordCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordPercentCheckSpec dailyPartitionDuplicateRecordPercent;

    /**
     * Returns a duplicate record count check.
     * @return Duplicate record count check.
     */
    public TableDuplicateRecordCountCheckSpec getDailyPartitionDuplicateRecordCount() {
        return dailyPartitionDuplicateRecordCount;
    }

    /**
     * Sets a new definition of a duplicate record count check.
     * @param dailyPartitionDuplicateRecordCount Duplicate record count check.
     */
    public void setDailyPartitionDuplicateRecordCount(TableDuplicateRecordCountCheckSpec dailyPartitionDuplicateRecordCount) {
		this.setDirtyIf(!Objects.equals(this.dailyPartitionDuplicateRecordCount, dailyPartitionDuplicateRecordCount));
        this.dailyPartitionDuplicateRecordCount = dailyPartitionDuplicateRecordCount;
		this.propagateHierarchyIdToField(dailyPartitionDuplicateRecordCount, "daily_partition_duplicate_record_count");
    }

    /**
     * Returns a duplicate record percent check.
     * @return Duplicate record percent check.
     */
    public TableDuplicateRecordPercentCheckSpec getDailyPartitionDuplicateRecordPercent() {
        return dailyPartitionDuplicateRecordPercent;
    }

    /**
     * Sets a new definition of a duplicate record percent check.
     * @param dailyPartitionDuplicateRecordPercent Duplicate record percent check.
     */
    public void setDailyPartitionDuplicateRecordPercent(TableDuplicateRecordPercentCheckSpec dailyPartitionDuplicateRecordPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDuplicateRecordPercent, dailyPartitionDuplicateRecordPercent));
        this.dailyPartitionDuplicateRecordPercent = dailyPartitionDuplicateRecordPercent;
        this.propagateHierarchyIdToField(dailyPartitionDuplicateRecordPercent, "daily_partition_duplicate_record_percent");
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
    public TableUniquenessDailyPartitionChecksSpec deepClone() {
        return (TableUniquenessDailyPartitionChecksSpec)super.deepClone();
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
        return CheckTimeScale.daily;
    }

    public static class TableUniquenessDailyPartitionChecksSpecSampleFactory implements SampleValueFactory<TableUniquenessDailyPartitionChecksSpec> {
        @Override
        public TableUniquenessDailyPartitionChecksSpec createSample() {
            return new TableUniquenessDailyPartitionChecksSpec() {{
                setDailyPartitionDuplicateRecordCount(new TableDuplicateRecordCountCheckSpec.TableDuplicateRecordCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
