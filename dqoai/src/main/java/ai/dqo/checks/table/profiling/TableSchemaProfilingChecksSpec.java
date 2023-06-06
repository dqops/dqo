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
import ai.dqo.checks.table.checkspecs.schema.*;
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
            put("column_list_changed", o -> o.columnListChanged);
            put("column_list_or_order_changed", o -> o.columnListOrOrderChanged);
            put("column_types_changed", o -> o.columnTypesChanged);
        }
    };

    @JsonPropertyDescription("Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).")
    private TableSchemaColumnCountCheckSpec columnCount;

    @JsonPropertyDescription("Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.")
    private TableSchemaColumnCountChangedCheckSpec columnCountChanged;

    @JsonPropertyDescription("Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.")
    private TableSchemaColumnListChangedCheckSpec columnListChanged;

    @JsonPropertyDescription("Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.")
    private TableSchemaColumnListOrOrderChangedCheckSpec columnListOrOrderChanged;

    @JsonPropertyDescription("Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.")
    private TableSchemaColumnTypesChangedCheckSpec columnTypesChanged;

    /**
     * Returns a column count check.
     * @return Column count check.
     */
    public TableSchemaColumnCountCheckSpec getColumnCount() {
        return columnCount;
    }

    /**
     * Sets a new definition of a column count check.
     * @param columnCount Column count check.
     */
    public void setColumnCount(TableSchemaColumnCountCheckSpec columnCount) {
        this.setDirtyIf(!Objects.equals(this.columnCount, columnCount));
        this.columnCount = columnCount;
        propagateHierarchyIdToField(columnCount, "column_count");
    }

    /**
     * Returns the configuration of a column count changed check.
     * @return Column count changed check.
     */
    public TableSchemaColumnCountChangedCheckSpec getColumnCountChanged() {
        return columnCountChanged;
    }

    /**
     * Sets the new definition of a column count changed check.
     * @param columnCountChanged Column count changed check.
     */
    public void setColumnCountChanged(TableSchemaColumnCountChangedCheckSpec columnCountChanged) {
        this.setDirtyIf(!Objects.equals(this.columnCountChanged, columnCountChanged));
        this.columnCountChanged = columnCountChanged;
        propagateHierarchyIdToField(columnCountChanged, "column_count_changed");
    }

    /**
     * Returns the configuration of the column list changed check.
     * @return Column list changed check.
     */
    public TableSchemaColumnListChangedCheckSpec getColumnListChanged() {
        return columnListChanged;
    }

    /**
     * Sets the check that detects changes to the list of columns.
     * @param columnListChanged Column list changed check.
     */
    public void setColumnListChanged(TableSchemaColumnListChangedCheckSpec columnListChanged) {
        this.setDirtyIf(!Objects.equals(this.columnListChanged, columnListChanged));
        this.columnListChanged = columnListChanged;
        propagateHierarchyIdToField(columnListChanged, "column_list_changed");
    }

    /**
     * Returns the check that detects if the list or order of columns have changed.
     * @return List or order of columns changed check.
     */
    public TableSchemaColumnListOrOrderChangedCheckSpec getColumnListOrOrderChanged() {
        return columnListOrOrderChanged;
    }

    /**
     * Sets the check that detects if the list or order of columns have changed.
     * @param columnListOrOrderChanged List or order of columns changed check.
     */
    public void setColumnListOrOrderChanged(TableSchemaColumnListOrOrderChangedCheckSpec columnListOrOrderChanged) {
        this.setDirtyIf(!Objects.equals(this.columnListOrOrderChanged, columnListOrOrderChanged));
        this.columnListOrOrderChanged = columnListOrOrderChanged;
        propagateHierarchyIdToField(columnListOrOrderChanged, "column_list_or_order_changed");
    }

    /**
     * Returns the column types changed check.
     * @return Column types changed check.
     */
    public TableSchemaColumnTypesChangedCheckSpec getColumnTypesChanged() {
        return columnTypesChanged;
    }

    /**
     * Sets the column types changed check.
     * @param columnTypesChanged Column types changed check.
     */
    public void setColumnTypesChanged(TableSchemaColumnTypesChangedCheckSpec columnTypesChanged) {
        this.setDirtyIf(!Objects.equals(this.columnTypesChanged, columnTypesChanged));
        this.columnTypesChanged = columnTypesChanged;
        propagateHierarchyIdToField(columnTypesChanged, "column_types_changed");
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
