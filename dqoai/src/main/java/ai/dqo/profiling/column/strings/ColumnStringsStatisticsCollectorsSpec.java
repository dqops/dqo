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
package ai.dqo.profiling.column.strings;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.profiling.AbstractStatisticsCollectorCategorySpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Category of column level statistics collector that are analysing strings.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("string_max_length", o -> o.stringMaxLength);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that finds the maximum string length.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsMaxLengthStatisticsCollectorSpec stringMaxLength = new ColumnStringsMaxLengthStatisticsCollectorSpec();

    /**
     * Returns the profiler configuration that finds the length of the longest string.
     * @return Profiler for the max length.
     */
    public ColumnStringsMaxLengthStatisticsCollectorSpec getStringMaxLength() {
        return stringMaxLength;
    }

    /**
     * Sets a reference to a max string length profiler.
     * @param stringMaxLength Max string length profiler.
     */
    public void setStringMaxLength(ColumnStringsMaxLengthStatisticsCollectorSpec stringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.stringMaxLength, stringMaxLength));
        this.stringMaxLength = stringMaxLength;
        this.propagateHierarchyIdToField(stringMaxLength, "string_max_length");
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
