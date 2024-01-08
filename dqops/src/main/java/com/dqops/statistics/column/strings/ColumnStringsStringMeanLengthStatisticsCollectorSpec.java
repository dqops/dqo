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
package com.dqops.statistics.column.strings;

import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.column.text.ColumnStringsStringMeanLengthSensorParametersSpec;
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
 * Column profiler that finds the mean string length.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsStringMeanLengthStatisticsCollectorSpec extends AbstractStatisticsCollectorSpec<ColumnStringsStringMeanLengthSensorParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsStringMeanLengthStatisticsCollectorSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Profiler parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsStringMeanLengthSensorParametersSpec parameters = new ColumnStringsStringMeanLengthSensorParametersSpec();

    /**
     * Returns the configuration of the sensor that performs profiling.
     * @return Sensor specification.
     */
    @Override
    public ColumnStringsStringMeanLengthSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets the sensor parameters instance.
     * @param parameters Sensor parameters instance.
     */
    public void setParameters(ColumnStringsStringMeanLengthSensorParametersSpec parameters) {
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
        return new DataTypeCategory[] { DataTypeCategory.string };
    }
}
