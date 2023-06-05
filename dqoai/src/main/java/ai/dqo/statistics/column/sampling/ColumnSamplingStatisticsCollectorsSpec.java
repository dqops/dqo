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
package ai.dqo.statistics.column.sampling;

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
 * Category of column level statistics collector that are capturing the column samples.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSamplingStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSamplingStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("column_samples", o -> o.columnSamples);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that finds the maximum string length.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSamplingColumnSamplesStatisticsCollectorSpec columnSamples = new ColumnSamplingColumnSamplesStatisticsCollectorSpec();


    /**
     * Returns the profiler configuration that finds the length of the longest string.
     * @return Profiler for the max length.
     */
    public ColumnSamplingColumnSamplesStatisticsCollectorSpec getColumnSamples() {
        return columnSamples;
    }

    /**
     * Sets a reference to a max string length profiler.
     * @param columnSamples Max string length profiler.
     */
    public void setColumnSamples(ColumnSamplingColumnSamplesStatisticsCollectorSpec columnSamples) {
        this.setDirtyIf(!Objects.equals(this.columnSamples, columnSamples));
        this.columnSamples = columnSamples;
        this.propagateHierarchyIdToField(columnSamples, "column_samples");
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
