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
import ai.dqo.checks.column.strings.ColumnMaxStringLengthBelowCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringLengthAboveCheckSpec;
import ai.dqo.checks.column.strings.ColumnMeanStringLengthBetweenCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMeanStringLengthBetweenCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for string.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocStringsChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocStringsChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("max_string_length_below", o -> o.maxStringLengthBelow);
            put("min_string_length_above", o -> o.minStringLengthAbove);
            put("mean_string_length_between", o -> o.meanStringLengthBetween);
            put("max_string_empty_percent", o -> o.maxStringEmptyPercent);
            put("mean_string_length_between", o -> o.meanStringLengthBetween);
            put("max_string_empty_percent", o -> o.maxStringEmptyPercent);
            put("max_string_empty_count", o -> o.maxStringEmptyCount);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length.")
    private ColumnMaxStringLengthBelowCheckSpec maxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length.")
    private ColumnMinStringLengthAboveCheckSpec minStringLengthAbove;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length.")
    private ColumnMeanStringLengthBetweenCheckSpec meanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.")
    private ColumnMaxStringEmptyPercentCheckSpec maxStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length.")
    private ColumnMeanStringLengthBetweenCheckSpec meanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.")
    private ColumnMaxStringEmptyPercentCheckSpec maxStringEmptyPercent;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity.")
    private ColumnMaxStringEmptyCountCheckSpec maxStringEmptyCount;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnMaxStringLengthBelowCheckSpec getMaxStringLengthBelow() {
        return maxStringLengthBelow;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param maxStringLengthBelow Maximum string length below check.
     */
    public void setMaxStringLengthBelow(ColumnMaxStringLengthBelowCheckSpec maxStringLengthBelow) {
        this.setDirtyIf(!Objects.equals(this.maxStringLengthBelow, maxStringLengthBelow));
        this.maxStringLengthBelow = maxStringLengthBelow;
        propagateHierarchyIdToField(maxStringLengthBelow, "max_string_length_below");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnMinStringLengthAboveCheckSpec getMinStringLengthAbove() {
        return minStringLengthAbove;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param minStringLengthAbove Minimum string length above check.
     */
    public void setMinStringLengthAbove(ColumnMinStringLengthAboveCheckSpec minStringLengthAbove) {
        this.setDirtyIf(!Objects.equals(this.minStringLengthAbove, minStringLengthAbove));
        this.minStringLengthAbove = minStringLengthAbove;
        propagateHierarchyIdToField(minStringLengthAbove, "min_string_length_above");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnMeanStringLengthBetweenCheckSpec getMeanStringLengthBetween() {
        return meanStringLengthBetween;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param meanStringLengthBetween Mean string length between check.
     */
    public void setMeanStringLengthBetween(ColumnMeanStringLengthBetweenCheckSpec meanStringLengthBetween) {
        this.setDirtyIf(!Objects.equals(this.meanStringLengthBetween, meanStringLengthBetween));
        this.meanStringLengthBetween = meanStringLengthBetween;
        propagateHierarchyIdToField(meanStringLengthBetween, "mean_string_length_between");
    }

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnMaxStringEmptyPercentCheckSpec getMaxStringEmptyPercent() {
        return maxStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param maxStringEmptyPercent Maximum string length below check.
     */
    public void setMaxStringEmptyPercent(ColumnMaxStringEmptyPercentCheckSpec maxStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.maxStringEmptyPercent, maxStringEmptyPercent));
        this.maxStringEmptyPercent = maxStringEmptyPercent;
        propagateHierarchyIdToField(maxStringEmptyPercent, "max_string_empty_percent");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnMeanStringLengthBetweenCheckSpec getMeanStringLengthBetween() {
        return meanStringLengthBetween;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param meanStringLengthBetween Mean string length between check.
     */
    public void setMeanStringLengthBetween(ColumnMeanStringLengthBetweenCheckSpec meanStringLengthBetween) {
        this.setDirtyIf(!Objects.equals(this.meanStringLengthBetween, meanStringLengthBetween));
        this.meanStringLengthBetween = meanStringLengthBetween;
        propagateHierarchyIdToField(meanStringLengthBetween, "mean_string_length_between");
    }

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnMaxStringEmptyPercentCheckSpec getMaxStringEmptyPercent() {
        return maxStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param maxStringEmptyPercent Maximum string length below check.
     */
    public void setMaxStringEmptyPercent(ColumnMaxStringEmptyPercentCheckSpec maxStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.maxStringEmptyPercent, maxStringEmptyPercent));
        this.maxStringEmptyPercent = maxStringEmptyPercent;
        propagateHierarchyIdToField(maxStringEmptyPercent, "max_string_empty_percent");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnMaxStringEmptyCountCheckSpec getMaxStringEmptyCount() {
        return maxStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param maxStringEmptyCount Max string empty count check.
     */
    public void setMaxStringEmptyCount(ColumnMaxStringEmptyCountCheckSpec maxStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.maxStringEmptyCount, maxStringEmptyCount));
        this.maxStringEmptyCount = maxStringEmptyCount;
        propagateHierarchyIdToField(maxStringEmptyCount, "max_string_empty_count");
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
