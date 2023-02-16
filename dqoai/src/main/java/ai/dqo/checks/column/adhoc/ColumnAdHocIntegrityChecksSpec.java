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
 * Container of built-in preconfigured data quality checks on a column level that are checking for integrity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocIntegrityChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocIntegrityChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("foreign_key_not_match_count", o -> o.foreignKeyNotMatchCount);
            put("foreign_key_match_percent", o -> o.foreignKeyMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.")
    private ColumnIntegrityForeignKeyNotMatchCountCheckSpec foreignKeyNotMatchCount;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec foreignKeyMatchPercent;

    /**
     * Returns an integrity value not match count check specification.
     * @return Integrity value not match count check specification.
     */
    public ColumnIntegrityForeignKeyNotMatchCountCheckSpec getForeignKeyNotMatchCount() {
        return foreignKeyNotMatchCount;
    }

    /**
     * Sets integrity value not match count check specification.
     * @param foreignKeyNotMatchCount Integrity value not match count check specification.
     */
    public void setForeignKeyNotMatchCount(ColumnIntegrityForeignKeyNotMatchCountCheckSpec foreignKeyNotMatchCount) {
        this.setDirtyIf(!Objects.equals(this.foreignKeyNotMatchCount, foreignKeyNotMatchCount));
        this.foreignKeyNotMatchCount = foreignKeyNotMatchCount;
        propagateHierarchyIdToField(foreignKeyNotMatchCount, "foreign_key_not_match_count");
    }

    /**
     * Returns an integrity value match percent check specification.
     * @return Integrity value match percent check specification.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getForeignKeyMatchPercent() {
        return foreignKeyMatchPercent;
    }

    /**
     * Sets integrity value match percent check specification.
     * @param foreignKeyMatchPercent Integrity value match percent check specification.
     */
    public void setForeignKeyMatchPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec foreignKeyMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.foreignKeyMatchPercent, foreignKeyMatchPercent));
        this.foreignKeyMatchPercent = foreignKeyMatchPercent;
        propagateHierarchyIdToField(foreignKeyMatchPercent, "foreign_key_match_percent");
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