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
package ai.dqo.checks.column.recurring.nulls;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.nulls.*;
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
public class ColumnNullsDailyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsDailyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_nulls_count", o -> o.dailyNullsCount);
            put("daily_nulls_percent", o -> o.dailyNullsPercent);
            put("daily_not_nulls_count", o -> o.dailyNotNullsCount);
            put("daily_not_nulls_percent", o -> o.dailyNotNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNullsCountCheckSpec dailyNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNullsPercentCheckSpec dailyNullsPercent;

    @JsonPropertyDescription("Verifies that the number of not null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNotNullsCountCheckSpec dailyNotNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of not nulls in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNotNullsPercentCheckSpec dailyNotNullsPercent;

    /**
     * Returns nulls count check specification.
     * @return Nulls count check specification.
     */
    public ColumnNullsCountCheckSpec getDailyNullsCount() {
        return dailyNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param dailyNullsCount Nulls count check specification.
     */
    public void setDailyNullsCount(ColumnNullsCountCheckSpec dailyNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsCount, dailyNullsCount));
        this.dailyNullsCount = dailyNullsCount;
        propagateHierarchyIdToField(dailyNullsCount, "daily_nulls_count");
    }

    /**
     * Returns a nulls percent check specification.
     * @return Nulls percent check specification.
     */
    public ColumnNullsPercentCheckSpec getDailyNullsPercent() {
        return dailyNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param dailyNullsPercent Nulls percent check specification.
     */
    public void setDailyNullsPercent(ColumnNullsPercentCheckSpec dailyNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercent, dailyNullsPercent));
        this.dailyNullsPercent = dailyNullsPercent;
        propagateHierarchyIdToField(dailyNullsPercent, "daily_nulls_percent");
    }

    /**
     * Returns not nulls count check specification.
     * @return Not nulls count check specification.
     */
    public ColumnNotNullsCountCheckSpec getDailyNotNullsCount() {
        return dailyNotNullsCount;
    }

    /**
     * Sets a new definition of a not nulls count check.
     * @param dailyNotNullsCount Not nulls count check specification.
     */
    public void setDailyNotNullsCount(ColumnNotNullsCountCheckSpec dailyNotNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyNotNullsCount, dailyNotNullsCount));
        this.dailyNotNullsCount = dailyNotNullsCount;
        propagateHierarchyIdToField(dailyNotNullsCount, "daily_not_nulls_count");
    }

    /**
     * Returns a not nulls percent check specification.
     * @return Not nulls percent check specification.
     */
    public ColumnNotNullsPercentCheckSpec getDailyNotNullsPercent() {
        return dailyNotNullsPercent;
    }

    /**
     * Sets a new definition of a not nulls percent check.
     * @param dailyNotNullsPercent Not nulls percent check specification.
     */
    public void setDailyNotNullsPercent(ColumnNotNullsPercentCheckSpec dailyNotNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNotNullsPercent, dailyNotNullsPercent));
        this.dailyNotNullsPercent = dailyNotNullsPercent;
        propagateHierarchyIdToField(dailyNotNullsPercent, "daily_not_nulls_percent");
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
