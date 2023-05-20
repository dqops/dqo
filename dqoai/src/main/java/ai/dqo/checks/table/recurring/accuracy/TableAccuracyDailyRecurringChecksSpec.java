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
package ai.dqo.checks.table.recurring.accuracy;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.accuracy.TableAccuracyRowCountMatchPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableAccuracyDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAccuracyDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_row_count_match_percent", o -> o.dailyRowCountMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies the row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each day when the data quality check was evaluated.")
    private TableAccuracyRowCountMatchPercentCheckSpec dailyRowCountMatchPercent;

    /**
     * Returns a row count match percent check.
     * @return Row count match percent check.
     */
    public TableAccuracyRowCountMatchPercentCheckSpec getDailyRowCountMatchPercent() {
        return dailyRowCountMatchPercent;
    }

    /**
     * Sets the reference to a row count match percent check.
     * @param dailyRowCountMatchPercent Row count match percent check.
     */
    public void setDailyRowCountMatchPercent(TableAccuracyRowCountMatchPercentCheckSpec dailyRowCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountMatchPercent, dailyRowCountMatchPercent));
        this.dailyRowCountMatchPercent = dailyRowCountMatchPercent;
        propagateHierarchyIdToField(dailyRowCountMatchPercent, "daily_row_count_match_percent");
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