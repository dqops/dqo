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
package com.dqops.checks.table.monitoring.uniqueness;

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
 * Container of table level daily monitoring for uniqueness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableUniquenessDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableUniquenessDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_duplicate_record_count", o -> o.dailyDuplicateRecordCount);
            put("daily_duplicate_record_percent", o -> o.dailyDuplicateRecordPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordCountCheckSpec dailyDuplicateRecordCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordPercentCheckSpec dailyDuplicateRecordPercent;

    /**
     * Returns a duplicate record count check.
     * @return Duplicate record count check.
     */
    public TableDuplicateRecordCountCheckSpec getDailyDuplicateRecordCount() {
        return dailyDuplicateRecordCount;
    }

    /**
     * Sets a new definition of a duplicate record count check.
     * @param dailyDuplicateRecordCount Duplicate record count check.
     */
    public void setDailyDuplicateRecordCount(TableDuplicateRecordCountCheckSpec dailyDuplicateRecordCount) {
		this.setDirtyIf(!Objects.equals(this.dailyDuplicateRecordCount, dailyDuplicateRecordCount));
        this.dailyDuplicateRecordCount = dailyDuplicateRecordCount;
		this.propagateHierarchyIdToField(dailyDuplicateRecordCount, "daily_duplicate_record_count");
    }

    /**
     * Returns a duplicate record percent check.
     * @return Duplicate record percent check.
     */
    public TableDuplicateRecordPercentCheckSpec getDailyDuplicateRecordPercent() {
        return dailyDuplicateRecordPercent;
    }

    /**
     * Sets a new definition of a duplicate record percent check.
     * @param dailyDuplicateRecordPercent Duplicate record percent check.
     */
    public void setDailyDuplicateRecordPercent(TableDuplicateRecordPercentCheckSpec dailyDuplicateRecordPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyDuplicateRecordPercent, dailyDuplicateRecordPercent));
        this.dailyDuplicateRecordPercent = dailyDuplicateRecordPercent;
        this.propagateHierarchyIdToField(dailyDuplicateRecordPercent, "daily_duplicate_record_percent");
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
    public TableUniquenessDailyMonitoringChecksSpec deepClone() {
        return (TableUniquenessDailyMonitoringChecksSpec)super.deepClone();
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

    public static class TableUniquenessDailyMonitoringChecksSpecSampleFactory implements SampleValueFactory<TableUniquenessDailyMonitoringChecksSpec> {
        @Override
        public TableUniquenessDailyMonitoringChecksSpec createSample() {
            return new TableUniquenessDailyMonitoringChecksSpec() {{
                setDailyDuplicateRecordCount(new TableDuplicateRecordCountCheckSpec.TableDuplicateRecordCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
