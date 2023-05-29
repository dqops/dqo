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
package ai.dqo.checks.column.recurring.datetime;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.datetime.ColumnDateValuesInFuturePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.datetime.ColumnDatetimeValueInRangeDatePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of date-time data quality recurring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_date_values_in_future_percent", o -> o.dailyDateValuesInFuturePercent);
            put("daily_datetime_value_in_range_date_percent", o -> o.dailyDatetimeValueInRangeDatePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDateValuesInFuturePercentCheckSpec dailyDateValuesInFuturePercent;

    @JsonPropertyDescription("Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDatetimeValueInRangeDatePercentCheckSpec dailyDatetimeValueInRangeDatePercent;

    /**
     * Returns a date values in future percent check specification.
     * @return Date values in future percent check specification.
     */
    public ColumnDateValuesInFuturePercentCheckSpec getDailyDateValuesInFuturePercent() {
        return dailyDateValuesInFuturePercent;
    }

    /**
     * Sets a new definition of a date values in future percent check.
     * @param dailyDateValuesInFuturePercent Date values in future percent check specification.
     */
    public void setDailyDateValuesInFuturePercent(ColumnDateValuesInFuturePercentCheckSpec dailyDateValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyDateValuesInFuturePercent, dailyDateValuesInFuturePercent));
        this.dailyDateValuesInFuturePercent = dailyDateValuesInFuturePercent;
        propagateHierarchyIdToField(dailyDateValuesInFuturePercent, "daily_date_values_in_future_percent");
    }

    /**
     * Returns a datetime value in range date percentage check.
     * @return Maximum datetime value in range date percentage check.
     */
    public ColumnDatetimeValueInRangeDatePercentCheckSpec getDailyDatetimeValueInRangeDatePercent() {
        return dailyDatetimeValueInRangeDatePercent;
    }

    /**
     * Sets a new definition of a datetime value in range date percentage check.
     * @param dailyDatetimeValueInRangeDatePercent Datetime value in range date percentage check.
     */
    public void setDailyDatetimeValueInRangeDatePercent(ColumnDatetimeValueInRangeDatePercentCheckSpec dailyDatetimeValueInRangeDatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyDatetimeValueInRangeDatePercent, dailyDatetimeValueInRangeDatePercent));
        this.dailyDatetimeValueInRangeDatePercent = dailyDatetimeValueInRangeDatePercent;
        propagateHierarchyIdToField(dailyDatetimeValueInRangeDatePercent, "daily_datetime_value_in_range_date_percent");
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