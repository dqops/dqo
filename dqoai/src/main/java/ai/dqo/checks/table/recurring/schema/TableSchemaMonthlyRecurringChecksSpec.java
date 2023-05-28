/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.checks.table.recurring.schema;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.schema.TableColumnCountChangedCheckSpec;
import ai.dqo.checks.table.checkspecs.schema.TableColumnCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured volume data quality checks on a table level that are executed as a monthly recurring (checkpoint) checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSchemaMonthlyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSchemaMonthlyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_column_count", o -> o.monthlyColumnCount);
            put("monthly_column_count_changed", o -> o.monthlyColumnCountChanged);
        }
    };

    @JsonPropertyDescription("Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.")
    private TableColumnCountCheckSpec monthlyColumnCount;

    @JsonPropertyDescription("Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.")
    private TableColumnCountChangedCheckSpec monthlyColumnCountChanged;

    /**
     * Returns a column count check.
     * @return Column count check.
     */
    public TableColumnCountCheckSpec getMonthlyColumnCount() {
        return monthlyColumnCount;
    }

    /**
     * Sets a new definition of a column count check.
     * @param monthlyColumnCount Column count check.
     */
    public void setMonthlyColumnCount(TableColumnCountCheckSpec monthlyColumnCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyColumnCount, monthlyColumnCount));
        this.monthlyColumnCount = monthlyColumnCount;
        propagateHierarchyIdToField(monthlyColumnCount, "monthly_column_count");
    }

    /**
     * Returns the configuration of a column count changed check.
     * @return Column count changed check.
     */
    public TableColumnCountChangedCheckSpec getMonthlyColumnCountChanged() {
        return monthlyColumnCountChanged;
    }

    /**
     * Sets the new definition of a column count changed check.
     * @param monthlyColumnCountChanged Column count changed check.
     */
    public void setMonthlyColumnCountChanged(TableColumnCountChangedCheckSpec monthlyColumnCountChanged) {
        this.setDirtyIf(!Objects.equals(this.monthlyColumnCountChanged, monthlyColumnCountChanged));
        this.monthlyColumnCountChanged = monthlyColumnCountChanged;
        propagateHierarchyIdToField(monthlyColumnCountChanged, "monthly_column_count_changed");
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
}
