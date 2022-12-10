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
package ai.dqo.profiling.column.uniqueness;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.profiling.AbstractProfilerCategorySpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Category of column level profilers that are analysing uniqueness.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessProfilersSpec extends AbstractProfilerCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessProfilersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractProfilerCategorySpec.FIELDS) {
        {
            put("unique_values_count", o -> o.uniqueValuesCount);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that counts unique (distinct) column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessUniqueValuesCountProfilerSpec uniqueValuesCount = new ColumnUniquenessUniqueValuesCountProfilerSpec();

    /**
     * Returns the profiler configuration that counts unique (distinct) values.
     * @return Profiler for the distinct count.
     */
    public ColumnUniquenessUniqueValuesCountProfilerSpec getUniqueValuesCount() {
        return uniqueValuesCount;
    }

    /**
     * Sets a reference to a unique values count profiler.
     * @param uniqueValuesCount Unique (distinct) values count profiler.
     */
    public void setUniqueValuesCount(ColumnUniquenessUniqueValuesCountProfilerSpec uniqueValuesCount) {
        this.setDirtyIf(!Objects.equals(this.uniqueValuesCount, uniqueValuesCount));
        this.uniqueValuesCount = uniqueValuesCount;
        this.propagateHierarchyIdToField(uniqueValuesCount, "unique_values_count");
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
