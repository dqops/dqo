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
package com.dqops.statistics.column;

import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.statistics.AbstractRootStatisticsCollectorsContainerSpec;
import com.dqops.statistics.StatisticsCollectorTarget;
import com.dqops.statistics.column.nulls.ColumnNullsStatisticsCollectorsSpec;
import com.dqops.statistics.column.range.ColumnRangeStatisticsCollectorsSpec;
import com.dqops.statistics.column.sampling.ColumnSamplingStatisticsCollectorsSpec;
import com.dqops.statistics.column.text.ColumnTextStatisticsCollectorsSpec;
import com.dqops.statistics.column.uniqueness.ColumnUniquenessStatisticsCollectorsSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of statistics collector categories for profiling columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStatisticsCollectorsRootCategoriesSpec extends AbstractRootStatisticsCollectorsContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStatisticsCollectorsRootCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootStatisticsCollectorsContainerSpec.FIELDS) {
        {
            put("nulls", o -> o.nulls);
            put("text", o -> o.text);
            put("uniqueness", o -> o.uniqueness);
            put("range", o -> o.range);
            put("sampling", o -> o.sampling);
        }
    };

    @JsonPropertyDescription("Configuration of null values profilers on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsStatisticsCollectorsSpec nulls = new ColumnNullsStatisticsCollectorsSpec();

    @JsonPropertyDescription("Configuration of text column profilers on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextStatisticsCollectorsSpec text = new ColumnTextStatisticsCollectorsSpec();

    @JsonPropertyDescription("Configuration of profilers that analyse uniqueness of values (distinct count).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessStatisticsCollectorsSpec uniqueness = new ColumnUniquenessStatisticsCollectorsSpec();

    @JsonPropertyDescription("Configuration of profilers that analyse the range of values (min, max).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnRangeStatisticsCollectorsSpec range = new ColumnRangeStatisticsCollectorsSpec();

    @JsonPropertyDescription("Configuration of profilers that collect the column samples.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSamplingStatisticsCollectorsSpec sampling = new ColumnSamplingStatisticsCollectorsSpec();

    /**
     * Returns the configuration of null detection profilers.
     * @return Null detection profilers.
     */
    public ColumnNullsStatisticsCollectorsSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the instance of a nulls detection profilers category.
     * @param nulls Nulls detection profiler category.
     */
    public void setNulls(ColumnNullsStatisticsCollectorsSpec nulls) {
        this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
        this.propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the configuration of string (text) profilers that analyse text columns.
     * @return Strings profilers specification.
     */
    public ColumnTextStatisticsCollectorsSpec getStrings() {
        return text;
    }

    /**
     * Sets a reference to a string (text) profilers category.
     * @param text Container of string profilers.
     */
    public void setStrings(ColumnTextStatisticsCollectorsSpec text) {
        this.setDirtyIf(!Objects.equals(this.text, text));
        this.text = text;
        this.propagateHierarchyIdToField(text, "text");
    }

    /**
     * Returns the configuration of uniqueness profilers.
     * @return Uniqueness profilers.
     */
    public ColumnUniquenessStatisticsCollectorsSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets a reference to a uniqueness profiler container.
     * @param uniqueness Uniqueness profilers.
     */
    public void setUniqueness(ColumnUniquenessStatisticsCollectorsSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        this.propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the configuration of range profilers (min, max, etc.).
     * @return Range profilers configuration.
     */
    public ColumnRangeStatisticsCollectorsSpec getRange() {
        return range;
    }

    /**
     * Sets a reference to a range profiler configuration.
     * @param range Range profilers.
     */
    public void setRange(ColumnRangeStatisticsCollectorsSpec range) {
        this.setDirtyIf(!Objects.equals(this.range, range));
        this.range = range;
        this.propagateHierarchyIdToField(range, "range");
    }

    /**
     * Returns the reference to a container of column sampling profilers category.
     * @return The container of data sampling profilers.
     */
    public ColumnSamplingStatisticsCollectorsSpec getSampling() {
        return sampling;
    }

    /**
     * Sets a reference to a container of data sampling profiler category.
     * @param sampling Data sampling profiler category.
     */
    public void setSampling(ColumnSamplingStatisticsCollectorsSpec sampling) {
        this.setDirtyIf(!Objects.equals(this.sampling, sampling));
        this.sampling = sampling;
        this.propagateHierarchyIdToField(sampling, "sampling");
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
     * Returns the type of the target (table or column).
     *
     * @return Target type.
     */
    @Override
    public StatisticsCollectorTarget getTarget() {
        return StatisticsCollectorTarget.column;
    }
}
