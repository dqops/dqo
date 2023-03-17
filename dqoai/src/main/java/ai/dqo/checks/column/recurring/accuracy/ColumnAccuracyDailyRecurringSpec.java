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
package ai.dqo.checks.column.recurring.accuracy;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.accuracy.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAccuracyDailyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyDailyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_total_sum_match_percent", o -> o.dailyTotalSumMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnAccuracyTotalSumMatchPercentCheckSpec dailyTotalSumMatchPercent;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnAccuracyTotalSumMatchPercentCheckSpec getDailyTotalSumMatchPercent() {
        return dailyTotalSumMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy total sum match percent check.
     * @param dailyTotalSumMatchPercent Accuracy total sum match percent check specification.
     */
    public void setDailyTotalSumMatchPercent(ColumnAccuracyTotalSumMatchPercentCheckSpec dailyTotalSumMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTotalSumMatchPercent, dailyTotalSumMatchPercent));
        this.dailyTotalSumMatchPercent = dailyTotalSumMatchPercent;
        propagateHierarchyIdToField(dailyTotalSumMatchPercent, "daily_total_sum_match_percent");
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