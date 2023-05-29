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
 * Container of built-in preconfigured volume data quality checks on a table level that are executed as a daily recurring (checkpoint) checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSchemaDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSchemaDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_column_count", o -> o.dailyColumnCount);
            put("daily_column_count_changed", o -> o.dailyColumnCountChanged);
        }
    };

    @JsonPropertyDescription("Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.")
    private TableColumnCountCheckSpec dailyColumnCount;

    @JsonPropertyDescription("Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.")
    private TableColumnCountChangedCheckSpec dailyColumnCountChanged;

    /**
     * Returns a column count check.
     * @return Column count check.
     */
    public TableColumnCountCheckSpec getDailyColumnCount() {
        return dailyColumnCount;
    }

    /**
     * Sets a new definition of a column count check.
     * @param dailyColumnCount Column count check.
     */
    public void setDailyColumnCount(TableColumnCountCheckSpec dailyColumnCount) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnCount, dailyColumnCount));
        this.dailyColumnCount = dailyColumnCount;
        propagateHierarchyIdToField(dailyColumnCount, "daily_column_count");
    }

    /**
     * Returns the configuration of a column count changed check.
     * @return Column count changed check.
     */
    public TableColumnCountChangedCheckSpec getDailyColumnCountChanged() {
        return dailyColumnCountChanged;
    }

    /**
     * Sets the new definition of a column count changed check.
     * @param dailyColumnCountChanged Column count changed check.
     */
    public void setDailyColumnCountChanged(TableColumnCountChangedCheckSpec dailyColumnCountChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyColumnCountChanged, dailyColumnCountChanged));
        this.dailyColumnCountChanged = dailyColumnCountChanged;
        propagateHierarchyIdToField(dailyColumnCountChanged, "daily_column_count_changed");
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
