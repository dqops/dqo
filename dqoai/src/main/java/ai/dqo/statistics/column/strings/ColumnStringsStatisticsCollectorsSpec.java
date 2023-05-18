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
package ai.dqo.statistics.column.strings;

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
 * Category of column level statistics collector that are analysing strings.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("string_max_length", o -> o.stringMaxLength);
            put("string_mean_length", o -> o.stringMeanLength);
            put("string_min_length", o -> o.stringMinLength);
            put("string_datatype_detect", o -> o.stringDatatypeDetect);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that finds the maximum string length.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsStringMaxLengthStatisticsCollectorSpec stringMaxLength = new ColumnStringsStringMaxLengthStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that finds the mean string length.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsStringMeanLengthStatisticsCollectorSpec stringMeanLength = new ColumnStringsStringMeanLengthStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that finds the min string length.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsStringMinLengthStatisticsCollectorSpec stringMinLength = new ColumnStringsStringMinLengthStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that detects datatype.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsStringDatatypeDetectStatisticsCollectorSpec stringDatatypeDetect = new ColumnStringsStringDatatypeDetectStatisticsCollectorSpec();

    /**
     * Returns the profiler configuration that finds the length of the longest string.
     * @return Profiler for the max length.
     */
    public ColumnStringsStringMaxLengthStatisticsCollectorSpec getStringMaxLength() {
        return stringMaxLength;
    }

    /**
     * Sets a reference to a max string length profiler.
     * @param stringMaxLength Max string length profiler.
     */
    public void setStringMaxLength(ColumnStringsStringMaxLengthStatisticsCollectorSpec stringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.stringMaxLength, stringMaxLength));
        this.stringMaxLength = stringMaxLength;
        this.propagateHierarchyIdToField(stringMaxLength, "string_max_length");
    }

    /**
     * Returns the profiler configuration that finds the mean length of the string.
     * @return Profiler for the mean length.
     */
    public ColumnStringsStringMeanLengthStatisticsCollectorSpec getStringMeanLength() {
        return stringMeanLength;
    }

    /**
     * Sets a reference to a mean string length profiler.
     * @param stringMeanLength Mean string length profiler.
     */
    public void setStringMeanLength(ColumnStringsStringMeanLengthStatisticsCollectorSpec stringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.stringMeanLength, stringMeanLength));
        this.stringMeanLength = stringMeanLength;
        this.propagateHierarchyIdToField(stringMeanLength, "string_mean_length");
    }

    /**
     * Returns the profiler configuration that finds the min length of the string.
     * @return Profiler for the min length.
     */
    public ColumnStringsStringMinLengthStatisticsCollectorSpec getStringMinLength() {
        return stringMinLength;
    }

    /**
     * Sets a reference to a min string length profiler.
     * @param stringMinLength Min string length profiler.
     */
    public void setStringMinLength(ColumnStringsStringMinLengthStatisticsCollectorSpec stringMinLength) {
        this.setDirtyIf(!Objects.equals(this.stringMinLength, stringMinLength));
        this.stringMinLength = stringMinLength;
        this.propagateHierarchyIdToField(stringMinLength, "string_min_length");
    }

    /**
     * Returns the profiler configuration that detect datatype.
     * @return Profiler for the datatype detect.
     */
    public ColumnStringsStringDatatypeDetectStatisticsCollectorSpec getStringDatatypeDetect() {
        return stringDatatypeDetect;
    }

    /**
     * Sets a reference to a datatype detect profiler.
     * @param stringDatatypeDetect Datatype detect profiler.
     */
    public void setStringDatatypeDetect(ColumnStringsStringDatatypeDetectStatisticsCollectorSpec stringDatatypeDetect) {
        this.setDirtyIf(!Objects.equals(this.stringDatatypeDetect, stringDatatypeDetect));
        this.stringDatatypeDetect = stringDatatypeDetect;
        this.propagateHierarchyIdToField(stringDatatypeDetect, "string_datatype_detect");
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
