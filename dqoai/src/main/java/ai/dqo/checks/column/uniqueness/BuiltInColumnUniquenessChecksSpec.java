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
package ai.dqo.checks.column.uniqueness;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured uniqueness checks executed on a column level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BuiltInColumnUniquenessChecksSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<BuiltInColumnUniquenessChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("distinct_count", o -> o.distinctCount);
			put("distinct_count_percent", o -> o.distinctCountPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the count of unique values in a column (select count(distinct <column_name>) from <table>) meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDistinctCountCheckSpec distinctCount;

    @JsonPropertyDescription("Verifies that the percent of unique values in a column (select (count(distinct <column_name>) / count(<column_name>)) * 100 from <table> ...) meets the required rules, like a minimum count.")
    private ColumnUniquenessDistinctCountPercentCheckSpec distinctCountPercent;

    /**
     * Returns a minimum distinct count check.
     * @return Distinct count check.
     */
    public ColumnUniquenessDistinctCountCheckSpec getDistinctCount() {
        return distinctCount;
    }

    /**
     * Returns a minimum distinct count percent check.
     * @return Distinct count percent check.
     */
    public ColumnUniquenessDistinctCountPercentCheckSpec getDistinctCountPercent() {
        return distinctCountPercent;
    }

    /**
     * Sets a new definition of a row count check.
     * @param distinctCount Row count check.
     */
    public void setDistinctCount(ColumnUniquenessDistinctCountCheckSpec distinctCount) {
		this.setDirtyIf(!Objects.equals(this.distinctCount, distinctCount));
        this.distinctCount = distinctCount;
		propagateHierarchyIdToField(distinctCount, "distinct_count");
    }

    /**
     * Sets a new definition of a row count percent check.
     * @param distinctCountPercent Row count percent check.
     */
    public void setDistinctCountPercent(ColumnUniquenessDistinctCountPercentCheckSpec distinctCountPercent) {
		this.setDirtyIf(!Objects.equals(this.distinctCountPercent, distinctCountPercent));
        this.distinctCountPercent = distinctCountPercent;
		propagateHierarchyIdToField(distinctCountPercent, "distinct_count_percent");
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

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }
}
