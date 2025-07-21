/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.statistics.column.sampling;

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
