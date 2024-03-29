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
package com.dqops.statistics.table.volume;

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
 * Category of volume table level statistics collector (basic profiler).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableVolumeStatisticsCollectorsSpec extends AbstractStatisticsCollectorCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableVolumeStatisticsCollectorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorCategorySpec.FIELDS) {
        {
            put("row_count", o -> o.rowCount);
        }
    };

    @JsonPropertyDescription("Configuration of the row count profiler.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeRowCountStatisticsCollectorSpec rowCount = new TableVolumeRowCountStatisticsCollectorSpec();

    /**
     * Returns the row count profiler specification.
     * @return Row count profiler.
     */
    public TableVolumeRowCountStatisticsCollectorSpec getRowCount() {
        return rowCount;
    }

    /**
     * Sets a reference to a row count profiler.
     * @param rowCount Row count profiler.
     */
    public void setRowCount(TableVolumeRowCountStatisticsCollectorSpec rowCount) {
        this.setDirtyIf(!Objects.equals(this.rowCount, rowCount));
        this.rowCount = rowCount;
        this.propagateHierarchyIdToField(rowCount, "row_count");
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
