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
package com.dqops.statistics.column.nulls;

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
 * Category of column level statistics collectors that are detecting nulls.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("nulls_count", o -> o.nullsCount);
            put("nulls_percent", o -> o.nullsPercent);
            put("not_nulls_count", o -> o.notNullsCount);
            put("not_nulls_percent", o -> o.notNullsPercent);
        }
    };

    @JsonPropertyDescription("Configuration of the profiler that counts null column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsNullsCountStatisticsCollectorSpec nullsCount = new ColumnNullsNullsCountStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that measures the percentage of null values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsNullsPercentStatisticsCollectorSpec nullsPercent = new ColumnNullsNullsPercentStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that counts not null column values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsNotNullsCountStatisticsCollectorSpec notNullsCount = new ColumnNullsNotNullsCountStatisticsCollectorSpec();

    @JsonPropertyDescription("Configuration of the profiler that measures the percentage of not null values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsNotNullsPercentStatisticsCollectorSpec notNullsPercent = new ColumnNullsNotNullsPercentStatisticsCollectorSpec();

    /**
     * Returns the nulls count profiler configuration.
     * @return Nulls count profiler configuration.
     */
    public ColumnNullsNullsCountStatisticsCollectorSpec getNullsCount() {
        return nullsCount;
    }

    /**
     * Sets the nulls count profiler configuration.
     * @param nullsCount Nulls count profiler configuration.
     */
    public void setNullsCount(ColumnNullsNullsCountStatisticsCollectorSpec nullsCount) {
        this.setDirtyIf(!Objects.equals(this.nullsCount, nullsCount));
        this.nullsCount = nullsCount;
        this.propagateHierarchyIdToField(nullsCount, "nulls_count");
    }

    /**
     * Returns the profiler that measures the percentage of null values in a column.
     * @return Nulls percentage profiler.
     */
    public ColumnNullsNullsPercentStatisticsCollectorSpec getNullsPercent() {
        return nullsPercent;
    }

    /**
     * Sets the reference to a nulls percentage profiler.
     * @param nullsPercent Nulls percentage profiler.
     */
    public void setNullsPercent(ColumnNullsNullsPercentStatisticsCollectorSpec nullsPercent) {
        this.setDirtyIf(!Objects.equals(this.nullsPercent, nullsPercent));
        this.nullsPercent = nullsPercent;
        this.propagateHierarchyIdToField(nullsPercent, "nulls_percent");
    }

    /**
     * Returns the profiler that counts not null column values.
     * @return Not null column value profiler.
     */
    public ColumnNullsNotNullsCountStatisticsCollectorSpec getNotNullsCount() {
        return notNullsCount;
    }

    /**
     * Sets the profiler that counts not null values.
     * @param notNullsCount Not null count profiler.
     */
    public void setNotNullsCount(ColumnNullsNotNullsCountStatisticsCollectorSpec notNullsCount) {
        this.setDirtyIf(!Objects.equals(this.notNullsCount, notNullsCount));
        this.notNullsCount = notNullsCount;
        this.propagateHierarchyIdToField(notNullsCount, "not_null_count");
    }

    /**
     * Returns the profiler that measures the percentage of not null values in a column.
     * @return Not nulls percentage profiler.
     */
    public ColumnNullsNotNullsPercentStatisticsCollectorSpec getNotNullsPercent() {
        return notNullsPercent;
    }

    /**
     * Sets the reference to a not nulls percentage profiler.
     * @param notNullsPercent Not nulls percentage profiler.
     */
    public void setNotNullsPercent(ColumnNullsNotNullsPercentStatisticsCollectorSpec notNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.notNullsPercent, notNullsPercent));
        this.notNullsPercent = notNullsPercent;
        this.propagateHierarchyIdToField(notNullsPercent, "not_nulls_percent");
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
