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
package ai.dqo.checks.column.adhoc;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for nulls.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocNullsChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocNullsChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("nulls_count", o -> o.nullsCount);
            put("nulls_percent", o -> o.nullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the set count.")
    private ColumnNullsCountCheckSpec nullsCount;

    @JsonPropertyDescription("Verifies that the percent of null values in a column does not exceed the set percentage.")
    private ColumnNullsPercentCheckSpec nullsPercent;

    /**
     * Returns a nulls count check specification.
     * @return Nulls count check specification.
     */
    public ColumnNullsCountCheckSpec getNullsCount() {
        return nullsCount;
    }

    /**
     * Sets a new nulls count check  specification.
     * @param nullsCount Nulls count check specification.
     */
    public void setNullsCount(ColumnNullsCountCheckSpec nullsCount) {
        this.setDirtyIf(!Objects.equals(this.nullsCount, nullsCount));
        this.nullsCount = nullsCount;
        propagateHierarchyIdToField(nullsCount, "nulls_count");
    }

    /**
     * Returns a nulls percent check specification.
     * @return Nulls percent check specification.
     */
    public ColumnNullsPercentCheckSpec getNullsPercent() {
        return nullsPercent;
    }

    /**
     * Sets a new percent check specification.
     * @param nullsPercent Nulls percent check specification.
     */
    public void setNullsPercent(ColumnNullsPercentCheckSpec nullsPercent) {
        this.setDirtyIf(!Objects.equals(this.nullsPercent, nullsPercent));
        this.nullsPercent = nullsPercent;
        propagateHierarchyIdToField(nullsPercent, "nulls_percent");
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
