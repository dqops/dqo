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
package ai.dqo.checks.column.profiling;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDuplicateCountCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnUniqueCountCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnUniquePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for negative values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnProfilingUniquenessChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnProfilingUniquenessChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("unique_count", o -> o.uniqueCount);
            put("unique_percent", o -> o.uniquePercent);
            put("duplicate_count", o -> o.duplicateCount);
            put("duplicate_percent", o -> o.duplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count.")
    private ColumnUniqueCountCheckSpec uniqueCount;

    @JsonPropertyDescription("Verifies that the percentage of unique values in a column does not exceed the minimum accepted percent.")
    private ColumnUniquePercentCheckSpec uniquePercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.")
    private ColumnDuplicateCountCheckSpec duplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.")
    private ColumnDuplicatePercentCheckSpec duplicatePercent;

    /**
     * Returns a unique count check specification.
     * @return Unique count check specification.
     */
    public ColumnUniqueCountCheckSpec getUniqueCount() {
        return uniqueCount;
    }

    /**
     * Sets a new specification of a unique count check.
     * @param uniqueCount Unique count check specification.
     */
    public void setUniqueCount(ColumnUniqueCountCheckSpec uniqueCount) {
        this.setDirtyIf(!Objects.equals(this.uniqueCount, uniqueCount));
        this.uniqueCount = uniqueCount;
        propagateHierarchyIdToField(uniqueCount, "unique_count");
    }

    /**
     * Returns a unique percent check specification.
     * @return Unique percent check specification.
     */
    public ColumnUniquePercentCheckSpec getUniquePercent() {
        return uniquePercent;
    }

    /**
     * Sets a new specification of a unique percent check.
     * @param uniquePercent Unique percent check specification.
     */
    public void setUniquePercent(ColumnUniquePercentCheckSpec uniquePercent) {
        this.setDirtyIf(!Objects.equals(this.uniquePercent, uniquePercent));
        this.uniquePercent = uniquePercent;
        propagateHierarchyIdToField(uniquePercent, "unique_percent");
    }

    /**
     * Returns a duplicate count check specification.
     * @return Duplicate count check specification.
     */
    public ColumnDuplicateCountCheckSpec getDuplicateCount() {
        return duplicateCount;
    }

    /**
     * Sets a new specification of a duplicate count check.
     * @param duplicateCount Duplicate count check specification.
     */
    public void setDuplicateCount(ColumnDuplicateCountCheckSpec duplicateCount) {
        this.setDirtyIf(!Objects.equals(this.duplicateCount, duplicateCount));
        this.duplicateCount = duplicateCount;
        propagateHierarchyIdToField(duplicateCount, "duplicate_count");
    }

    /**
     * Returns a duplicate percent check specification.
     * @return Duplicate percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getDuplicatePercent() {
        return duplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate percent check.
     * @param duplicatePercent Duplicate percent check specification.
     */
    public void setDuplicatePercent(ColumnDuplicatePercentCheckSpec duplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.duplicatePercent, duplicatePercent));
        this.duplicatePercent = duplicatePercent;
        propagateHierarchyIdToField(duplicatePercent, "duplicate_percent");
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
