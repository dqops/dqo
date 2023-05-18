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
package ai.dqo.checks.column.recurring.integrity;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.integrity.ColumnIntegrityForeignKeyMatchPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.integrity.ColumnIntegrityForeignKeyNotMatchCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of integrity data quality recurring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_foreign_key_not_match_count", o -> o.dailyForeignKeyNotMatchCount);
            put("daily_foreign_key_match_percent", o -> o.dailyForeignKeyMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnIntegrityForeignKeyNotMatchCountCheckSpec dailyForeignKeyNotMatchCount;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyForeignKeyMatchPercent;

    /**
     * Returns an integrity value not match count check specification.
     * @return Integrity value not match count check specification.
     */
    public ColumnIntegrityForeignKeyNotMatchCountCheckSpec getDailyForeignKeyNotMatchCount() {
        return dailyForeignKeyNotMatchCount;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param dailyForeignKeyNotMatchCount Integrity value not match count check specification.
     */
    public void setDailyForeignKeyNotMatchCount(ColumnIntegrityForeignKeyNotMatchCountCheckSpec dailyForeignKeyNotMatchCount) {
        this.setDirtyIf(!Objects.equals(this.dailyForeignKeyNotMatchCount, dailyForeignKeyNotMatchCount));
        this.dailyForeignKeyNotMatchCount = dailyForeignKeyNotMatchCount;
        propagateHierarchyIdToField(dailyForeignKeyNotMatchCount, "daily_foreign_key_not_match_count");
    }

    /**
     * Returns an integrity value match percent check specification.
     * @return Integrity value match percent check specification.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getDailyForeignKeyMatchPercent() {
        return dailyForeignKeyMatchPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param dailyForeignKeyMatchPercent Integrity value match percent check specification.
     */
    public void setDailyForeignKeyMatchPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyForeignKeyMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyForeignKeyMatchPercent, dailyForeignKeyMatchPercent));
        this.dailyForeignKeyMatchPercent = dailyForeignKeyMatchPercent;
        propagateHierarchyIdToField(dailyForeignKeyMatchPercent, "daily_foreign_key_match_percent");
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
