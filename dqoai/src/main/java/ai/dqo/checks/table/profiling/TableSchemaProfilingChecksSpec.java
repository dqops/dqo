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
package ai.dqo.checks.table.profiling;

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
 * Container of built-in preconfigured volume data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSchemaProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSchemaProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("column_count", o -> o.columnCount);
            put("column_count_changed", o -> o.columnCountChanged);
        }
    };

    @JsonPropertyDescription("Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).")
    private TableColumnCountCheckSpec columnCount;

    @JsonPropertyDescription("Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.")
    private TableColumnCountChangedCheckSpec columnCountChanged;

    /**
     * Returns a column count check.
     * @return Column count check.
     */
    public TableColumnCountCheckSpec getColumnCount() {
        return columnCount;
    }

    /**
     * Sets a new definition of a column count check.
     * @param columnCount Column count check.
     */
    public void setColumnCount(TableColumnCountCheckSpec columnCount) {
        this.setDirtyIf(!Objects.equals(this.columnCount, columnCount));
        this.columnCount = columnCount;
        propagateHierarchyIdToField(columnCount, "column_count");
    }

    /**
     * Returns the configuration of a column count changed check.
     * @return Column count changed check.
     */
    public TableColumnCountChangedCheckSpec getColumnCountChanged() {
        return columnCountChanged;
    }

    /**
     * Sets the new definition of a column count changed check.
     * @param columnCountChanged Column count changed check.
     */
    public void setColumnCountChanged(TableColumnCountChangedCheckSpec columnCountChanged) {
        this.setDirtyIf(!Objects.equals(this.columnCountChanged, columnCountChanged));
        this.columnCountChanged = columnCountChanged;
        propagateHierarchyIdToField(columnCountChanged, "column_count_changed");
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
