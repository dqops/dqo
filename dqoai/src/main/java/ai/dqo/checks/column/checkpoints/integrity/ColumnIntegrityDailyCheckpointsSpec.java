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
package ai.dqo.checks.column.checkpoints.integrity;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.integrity.*;
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
public class ColumnIntegrityDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_foreign_key_not_match_count", o -> o.dailyCheckpointForeignKeyNotMatchCount);
            put("daily_checkpoint_foreign_key_match_percent", o -> o.dailyCheckpointForeignKeyMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnIntegrityForeignKeyNotMatchCountCheckSpec dailyCheckpointForeignKeyNotMatchCount;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyCheckpointForeignKeyMatchPercent;

    /**
     * Returns an integrity value not match count check specification.
     * @return Integrity value not match count check specification.
     */
    public ColumnIntegrityForeignKeyNotMatchCountCheckSpec getDailyCheckpointForeignKeyNotMatchCount() {
        return dailyCheckpointForeignKeyNotMatchCount;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param dailyCheckpointForeignKeyNotMatchCount Integrity value not match count check specification.
     */
    public void setDailyCheckpointForeignKeyNotMatchCount(ColumnIntegrityForeignKeyNotMatchCountCheckSpec dailyCheckpointForeignKeyNotMatchCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointForeignKeyNotMatchCount, dailyCheckpointForeignKeyNotMatchCount));
        this.dailyCheckpointForeignKeyNotMatchCount = dailyCheckpointForeignKeyNotMatchCount;
        propagateHierarchyIdToField(dailyCheckpointForeignKeyNotMatchCount, "daily_checkpoint_foreign_key_not_match_count");
    }

    /**
     * Returns an integrity value match percent check specification.
     * @return Integrity value match percent check specification.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getDailyCheckpointForeignKeyMatchPercent() {
        return dailyCheckpointForeignKeyMatchPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param dailyCheckpointForeignKeyMatchPercent Integrity value match percent check specification.
     */
    public void setDailyCheckpointForeignKeyMatchPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyCheckpointForeignKeyMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointForeignKeyMatchPercent, dailyCheckpointForeignKeyMatchPercent));
        this.dailyCheckpointForeignKeyMatchPercent = dailyCheckpointForeignKeyMatchPercent;
        propagateHierarchyIdToField(dailyCheckpointForeignKeyMatchPercent, "daily_checkpoint_foreign_key_match_percent");
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
