/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.statistics.column.text;

import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.statistics.AbstractStatisticsCollectorCategorySpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Category of column level statistics collector that are analysing text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("text_max_length", o -> o.textMaxLength);
            put("text_mean_length", o -> o.textMeanLength);
            put("text_min_length", o -> o.textMinLength);
            put("text_datatype_detect", o -> o.textDatatypeDetect);
            put("text_min_word_count", o -> o.textMinWordCount);
            put("text_max_word_count", o -> o.textMaxWordCount);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that finds the maximum text length.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextTextMaxLengthStatisticsCollectorSpec textMaxLength = new ColumnTextTextMaxLengthStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that finds the mean text length.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextTextMeanLengthStatisticsCollectorSpec textMeanLength = new ColumnTextTextMeanLengthStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that finds the min text length.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextTextMinLengthStatisticsCollectorSpec textMinLength = new ColumnTextTextMinLengthStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that detects datatype.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextTextDatatypeDetectStatisticsCollectorSpec textDatatypeDetect = new ColumnTextTextDatatypeDetectStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that finds the estimated minimum word count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextMinWordCountStatisticsCollectorSpec textMinWordCount = new ColumnTextMinWordCountStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that finds the estimated maximum word count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextMaxWordCountStatisticsCollectorSpec textMaxWordCount = new ColumnTextMaxWordCountStatisticsCollectorSpec();

    /**
     * Returns the profiler configuration that finds the length of the longest text.
     * @return Profiler for the max length.
     */
    public ColumnTextTextMaxLengthStatisticsCollectorSpec getTextMaxLength() {
        return textMaxLength;
    }

    /**
     * Sets a reference to a max text length profiler.
     * @param textMaxLength Max text length profiler.
     */
    public void setTextMaxLength(ColumnTextTextMaxLengthStatisticsCollectorSpec textMaxLength) {
        this.setDirtyIf(!Objects.equals(this.textMaxLength, textMaxLength));
        this.textMaxLength = textMaxLength;
        this.propagateHierarchyIdToField(textMaxLength, "text_max_length");
    }

    /**
     * Returns the profiler configuration that finds the mean length of the text.
     * @return Profiler for the mean length.
     */
    public ColumnTextTextMeanLengthStatisticsCollectorSpec getTextMeanLength() {
        return textMeanLength;
    }

    /**
     * Sets a reference to a mean text length profiler.
     * @param textMeanLength Mean text length profiler.
     */
    public void setTextMeanLength(ColumnTextTextMeanLengthStatisticsCollectorSpec textMeanLength) {
        this.setDirtyIf(!Objects.equals(this.textMeanLength, textMeanLength));
        this.textMeanLength = textMeanLength;
        this.propagateHierarchyIdToField(textMeanLength, "text_mean_length");
    }

    /**
     * Returns the profiler configuration that finds the min length of the text.
     * @return Profiler for the min length.
     */
    public ColumnTextTextMinLengthStatisticsCollectorSpec getTextMinLength() {
        return textMinLength;
    }

    /**
     * Sets a reference to a min text length profiler.
     * @param textMinLength Min text length profiler.
     */
    public void setTextMinLength(ColumnTextTextMinLengthStatisticsCollectorSpec textMinLength) {
        this.setDirtyIf(!Objects.equals(this.textMinLength, textMinLength));
        this.textMinLength = textMinLength;
        this.propagateHierarchyIdToField(textMinLength, "text_min_length");
    }

    /**
     * Returns the profiler configuration that detect datatype.
     * @return Profiler for the datatype detect.
     */
    public ColumnTextTextDatatypeDetectStatisticsCollectorSpec getTextDatatypeDetect() {
        return textDatatypeDetect;
    }

    /**
     * Sets a reference to a datatype detect profiler.
     * @param textDatatypeDetect Datatype detect profiler.
     */
    public void setTextDatatypeDetect(ColumnTextTextDatatypeDetectStatisticsCollectorSpec textDatatypeDetect) {
        this.setDirtyIf(!Objects.equals(this.textDatatypeDetect, textDatatypeDetect));
        this.textDatatypeDetect = textDatatypeDetect;
        this.propagateHierarchyIdToField(textDatatypeDetect, "text_datatype_detect");
    }

    /**
     * Returns the profiler that estimates the minimum word count.
     * @return The profiler of the estimated minimum word count.
     */
    public ColumnTextMinWordCountStatisticsCollectorSpec getTextMinWordCount() {
        return textMinWordCount;
    }

    /**
     * Configures a profiler that estimates a minimum word count.
     * @param textMinWordCount A profiler that estimates a minimum word count.
     */
    public void setTextMinWordCount(ColumnTextMinWordCountStatisticsCollectorSpec textMinWordCount) {
        this.setDirtyIf(!Objects.equals(this.textMinWordCount, textMinWordCount));
        this.textMinWordCount = textMinWordCount;
        this.propagateHierarchyIdToField(textMinWordCount, "text_min_word_count");
    }

    /**
     * Returns the profiler that estimates the maximum word count.
     * @return The profiler of the estimated maximum word count.
     */
    public ColumnTextMaxWordCountStatisticsCollectorSpec getTextMaxWordCount() {
        return textMaxWordCount;
    }

    /**
     * Configures a profiler that estimates a maximum word count.
     * @param textMaxWordCount A profiler that estimates a maximum word count.
     */
    public void setTextMaxWordCount(ColumnTextMaxWordCountStatisticsCollectorSpec textMaxWordCount) {
        this.setDirtyIf(!Objects.equals(this.textMaxWordCount, textMaxWordCount));
        this.textMaxWordCount = textMaxWordCount;
        this.propagateHierarchyIdToField(textMaxWordCount, "text_max_word_count");
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
