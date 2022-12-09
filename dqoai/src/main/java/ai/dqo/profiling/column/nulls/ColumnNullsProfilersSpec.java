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
package ai.dqo.profiling.column.nulls;

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
 * Category of column level profilers that are detecting nulls.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsProfilersSpec extends AbstractProfilerCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsProfilersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractProfilerCategorySpec.FIELDS) {
        {
            put("nulls_count", o -> o.nullsCount);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that counts null column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsCountProfilerSpec nullsCount = new ColumnNullsCountProfilerSpec();

    /**
     * Returns the nulls count profiler configuration.
     * @return Nulls count profiler configuration.
     */
    public ColumnNullsCountProfilerSpec getNullsCount() {
        return nullsCount;
    }

    /**
     * Sets the nulls count profiler configuration.
     * @param nullsCount Nulls count profiler configuration.
     */
    public void setNullsCount(ColumnNullsCountProfilerSpec nullsCount) {
        this.setDirtyIf(!Objects.equals(this.nullsCount, nullsCount));
        this.nullsCount = nullsCount;
        this.propagateHierarchyIdToField(nullsCount, "nulls_count");
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
