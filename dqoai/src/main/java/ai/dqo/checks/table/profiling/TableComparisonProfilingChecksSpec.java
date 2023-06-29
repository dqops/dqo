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

import ai.dqo.checks.comparison.AbstractTableComparisonCheckCategorySpec;
import ai.dqo.checks.comparison.ComparisonCheckRules;
import ai.dqo.checks.comparison.TableCompareCheckType;
import ai.dqo.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableComparisonProfilingChecksSpec extends AbstractTableComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableComparisonProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractTableComparisonCheckCategorySpec.FIELDS) {
        {
            put("row_count_match", o -> o.rowCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.")
    private TableComparisonRowCountMatchCheckSpec rowCountMatch;

    /**
     * Returns the row count match check.
     * @return Row count match check.
     */
    public TableComparisonRowCountMatchCheckSpec getRowCountMatch() {
        return rowCountMatch;
    }

    /**
     * Sets a new row count match check.
     * @param rowCountMatch Row count match check.
     */
    public void setRowCountMatch(TableComparisonRowCountMatchCheckSpec rowCountMatch) {
        this.setDirtyIf(!Objects.equals(this.rowCountMatch, rowCountMatch));
        this.rowCountMatch = rowCountMatch;
        propagateHierarchyIdToField(rowCountMatch, "row_count_match");
    }

    /**
     * Returns the check specification for the given check type or null when it is not present and <code>createWhenMissing</code> is false.
     *
     * @param tableCompareCheckType Compare check type.
     * @param createWhenMissing     When true and the check specification is not present, it is created, added to the check compare container and returned.
     * @return Check specification or null (when <code>createWhenMissing</code> is false).
     */
    @Override
    public ComparisonCheckRules getCheckSpec(TableCompareCheckType tableCompareCheckType, boolean createWhenMissing) {
        switch (tableCompareCheckType) {
            case row_count_match: {
                if (this.rowCountMatch == null) {
                    if (createWhenMissing) {
                        this.setRowCountMatch(new TableComparisonRowCountMatchCheckSpec());
                    }
                }

                return this.rowCountMatch;
            }
            default:
                return null;
        }
    }

    /**
     * Removes the check specification for the given check.
     *
     * @param tableCompareCheckType Check type.
     */
    @Override
    public void removeCheckSpec(TableCompareCheckType tableCompareCheckType) {
        switch (tableCompareCheckType) {
            case row_count_match:
                this.setRowCountMatch(null);
                break;
        }
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
