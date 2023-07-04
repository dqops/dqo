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
package com.dqops.checks.table.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.table.checkspecs.accuracy.TableAccuracyTotalRowCountMatchPercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured accuracy data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableAccuracyProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAccuracyProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("total_row_count_match_percent", o -> o.totalRowCountMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the total row count of the tested table matches the total row count of another (reference) table.")
    private TableAccuracyTotalRowCountMatchPercentCheckSpec totalRowCountMatchPercent;

    /**
     * Returns the total row count match check.
     * @return Total row count match check.
     */
    public TableAccuracyTotalRowCountMatchPercentCheckSpec getTotalRowCountMatchPercent() {
        return totalRowCountMatchPercent;
    }

    /**
     * Sets a new total row count match check.
     * @param totalRowCountMatchPercent Total row count match check.
     */
    public void setTotalRowCountMatchPercent(TableAccuracyTotalRowCountMatchPercentCheckSpec totalRowCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.totalRowCountMatchPercent, totalRowCountMatchPercent));
        this.totalRowCountMatchPercent = totalRowCountMatchPercent;
        propagateHierarchyIdToField(totalRowCountMatchPercent, "total_row_count_match_percent");
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
