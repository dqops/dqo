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

import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.table.volume.TableVolumeRowCountSensorParametersSpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Table row count profiler.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableVolumeRowCountStatisticsCollectorSpec extends AbstractStatisticsCollectorSpec<TableVolumeRowCountSensorParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<TableVolumeRowCountStatisticsCollectorSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorSpec.FIELDS) {
        {
        }
    };

    /**
     * Sensor name used by this collector.
     */
    public static final String SENSOR_NAME = TableVolumeRowCountSensorParametersSpec.SENSOR_NAME;

    @JsonPropertyDescription("Profiler parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeRowCountSensorParametersSpec parameters = new TableVolumeRowCountSensorParametersSpec();

    /**
     * Returns the configuration of the sensor that performs profiling.
     * @return Sensor specification.
     */
    @Override
    public TableVolumeRowCountSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets the sensor parameters instance.
     * @param parameters Sensor parameters instance.
     */
    public void setParameters(TableVolumeRowCountSensorParametersSpec parameters) {
        this.setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters;
        this.propagateHierarchyIdToField(parameters, "parameters");
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
     * Returns the array of supported data type categories that the profiler supports.
     * Table level sensors should return null because table level profilers do not operate on columns and are not sensitive to the profiled column's type.
     *
     * @return Array of supported data types or null when the profiler has no limitations.
     */
    @Override
    public DataTypeCategory[] getSupportedDataTypes() {
        return null;
    }
}
