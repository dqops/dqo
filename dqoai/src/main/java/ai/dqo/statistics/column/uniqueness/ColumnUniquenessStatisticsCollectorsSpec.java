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
package ai.dqo.statistics.column.uniqueness;

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
 * Category of column level statistics collectors that are analysing uniqueness.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("unique_count", o -> o.uniqueCount);
            put("unique_percent", o -> o.uniquePercent);
            put("duplicate_count", o -> o.duplicateCount);
            put("duplicate_percent", o -> o.duplicatePercent);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that counts unique (distinct) column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessUniqueCountStatisticsCollectorSpec uniqueCount = new ColumnUniquenessUniqueCountStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that measure the percentage of unique (distinct) column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessUniquePercentStatisticsCollectorSpec uniquePercent = new ColumnUniquenessUniquePercentStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that counts duplicate column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDuplicateCountStatisticsCollectorSpec duplicateCount = new ColumnUniquenessDuplicateCountStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that measure the percentage of duplicate column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDuplicatePercentStatisticsCollectorSpec duplicatePercent = new ColumnUniquenessDuplicatePercentStatisticsCollectorSpec();

    /**
     * Returns the profiler configuration that counts unique (distinct) values.
     * @return Profiler for the distinct count.
     */
    public ColumnUniquenessUniqueCountStatisticsCollectorSpec getUniqueCount() {
        return uniqueCount;
    }

    /**
     * Sets a reference to a unique values count profiler.
     * @param uniqueCount Unique (distinct) values count profiler.
     */
    public void setUniqueCount(ColumnUniquenessUniqueCountStatisticsCollectorSpec uniqueCount) {
        this.setDirtyIf(!Objects.equals(this.uniqueCount, uniqueCount));
        this.uniqueCount = uniqueCount;
        this.propagateHierarchyIdToField(uniqueCount, "unique_count");
    }

    /**
     * Returns the profiler configuration that measure the percentage of unique (distinct) values.
     * @return Profiler for the unique (distinct) percent.
     */
    public ColumnUniquenessUniquePercentStatisticsCollectorSpec getUniquePercent() {
        return uniquePercent;
    }

    /**
     * Sets a reference to a unique values percent profiler.
     * @param uniquePercent Unique (distinct) percent profiler.
     */
    public void setUniquePercent(ColumnUniquenessUniquePercentStatisticsCollectorSpec uniquePercent) {
        this.setDirtyIf(!Objects.equals(this.uniquePercent, uniquePercent));
        this.uniquePercent = uniquePercent;
        this.propagateHierarchyIdToField(uniquePercent, "unique_percent");
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
