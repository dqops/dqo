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
package ai.dqo.statistics.column.range;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.statistics.AbstractStatisticsCollectorCategorySpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Category of column level statistics collectors that are analysing the range of values (min, max).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnRangeStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnRangeStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("min_value", o -> o.minValue);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that finds the minimum value in the column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnRangeMinValueStatisticsCollectorSpec minValue = new ColumnRangeMinValueStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that finds the maximum value in the column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnRangeMaxValueStatisticsCollectorSpec maxValue = new ColumnRangeMaxValueStatisticsCollectorSpec();

    /**
     * Returns the configuration of the profiler that finds the minimum value in the column.
     * @return Minimum value profiler.
     */
    public ColumnRangeMinValueStatisticsCollectorSpec getMinValue() {
        return minValue;
    }

    /**
     * Sets a reference to a profiler that finds the minimum value in a column.
     * @param minValue Min value profiler.
     */
    public void setMinValue(ColumnRangeMinValueStatisticsCollectorSpec minValue) {
        this.setDirtyIf(!Objects.equals(this.minValue, minValue));
        this.minValue = minValue;
        this.propagateHierarchyIdToField(minValue, "min_value");
    }

    /**
     * Returns the configuration of the profiler that finds the maximum value in the column.
     * @return Maximum value profiler.
     */
    public ColumnRangeMaxValueStatisticsCollectorSpec getMaxValue() {
        return maxValue;
    }

    /**
     * Sets a reference to a profiler that finds the maximum value in a column.
     * @param maxValue Max value profiler.
     */
    public void setMaxValue(ColumnRangeMaxValueStatisticsCollectorSpec maxValue) {
        this.setDirtyIf(!Objects.equals(this.maxValue, maxValue));
        this.maxValue = maxValue;
        this.propagateHierarchyIdToField(maxValue, "max_value");
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
