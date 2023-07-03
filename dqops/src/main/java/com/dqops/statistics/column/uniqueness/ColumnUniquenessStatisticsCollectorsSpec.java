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
package com.dqops.statistics.column.uniqueness;

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
 * Category of column level statistics collectors that are analysing uniqueness.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("distinct_count", o -> o.distinctCount);
            put("distinct_percent", o -> o.distinctPercent);
            put("duplicate_count", o -> o.duplicateCount);
            put("duplicate_percent", o -> o.duplicatePercent);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that counts distinct column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDistinctCountStatisticsCollectorSpec distinctCount = new ColumnUniquenessDistinctCountStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that measure the percentage of distinct column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDistinctPercentStatisticsCollectorSpec distinctPercent = new ColumnUniquenessDistinctPercentStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that counts duplicate column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDuplicateCountStatisticsCollectorSpec duplicateCount = new ColumnUniquenessDuplicateCountStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that measure the percentage of duplicate column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDuplicatePercentStatisticsCollectorSpec duplicatePercent = new ColumnUniquenessDuplicatePercentStatisticsCollectorSpec();

    /**
     * Returns the profiler configuration that counts distinct values.
     * @return Profiler for the distinct count.
     */
    public ColumnUniquenessDistinctCountStatisticsCollectorSpec getDistinctCount() {
        return distinctCount;
    }

    /**
     * Sets a reference to a distinct values count profiler.
     * @param distinctCount Unique (distinct) values count profiler.
     */
    public void setDistinctCount(ColumnUniquenessDistinctCountStatisticsCollectorSpec distinctCount) {
        this.setDirtyIf(!Objects.equals(this.distinctCount, distinctCount));
        this.distinctCount = distinctCount;
        this.propagateHierarchyIdToField(distinctCount, "distinct_count");
    }

    /**
     * Returns the profiler configuration that measure the percentage of distinct values.
     * @return Profiler for the distinct percent.
     */
    public ColumnUniquenessDistinctPercentStatisticsCollectorSpec getDistinctPercent() {
        return distinctPercent;
    }

    /**
     * Sets a reference to a distinct values percent profiler.
     * @param distinctPercent Distinct percent profiler.
     */
    public void setDistinctPercent(ColumnUniquenessDistinctPercentStatisticsCollectorSpec distinctPercent) {
        this.setDirtyIf(!Objects.equals(this.distinctPercent, distinctPercent));
        this.distinctPercent = distinctPercent;
        this.propagateHierarchyIdToField(distinctPercent, "distinct_percent");
    }

    /**
     * Returns the profiler configuration that counts duplicate values.
     * @return Profiler for the duplicate count.
     */
    public ColumnUniquenessDuplicateCountStatisticsCollectorSpec getDuplicateCount() {
        return duplicateCount;
    }

    /**
     * Sets a reference to a duplicate values count profiler.
     * @param duplicateCount Duplicate values count profiler.
     */
    public void setDuplicateCount(ColumnUniquenessDuplicateCountStatisticsCollectorSpec duplicateCount) {
        this.setDirtyIf(!Objects.equals(this.duplicateCount, duplicateCount));
        this.duplicateCount = duplicateCount;
        this.propagateHierarchyIdToField(duplicateCount, "duplicate_count");
    }

    /**
     * Returns the profiler configuration that measure the percentage of duplicate values.
     * @return Profiler for the duplicate percent.
     */
    public ColumnUniquenessDuplicatePercentStatisticsCollectorSpec getDuplicatePercent() {
        return duplicatePercent;
    }

    /**
     * Sets a reference to a duplicate values percent profiler.
     * @param duplicatePercent Duplicate percent profiler.
     */
    public void setDuplicatePercent(ColumnUniquenessDuplicatePercentStatisticsCollectorSpec duplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.duplicatePercent, duplicatePercent));
        this.duplicatePercent = duplicatePercent;
        this.propagateHierarchyIdToField(duplicatePercent, "duplicate_percent");
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
