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
package ai.dqo.profiling.column.nulls;

import ai.dqo.data.profilingresults.factory.ProfilerDataScope;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.profiling.AbstractProfilerSpec;
import ai.dqo.sensors.column.nulls.ColumnNullsNullCountSensorParametersSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column null count profiler.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsCountProfilerSpec extends AbstractProfilerSpec<ColumnNullsNullCountSensorParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsCountProfilerSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractProfilerSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Profiler parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsNullCountSensorParametersSpec parameters = new ColumnNullsNullCountSensorParametersSpec();

    /**
     * Returns the configuration of the sensor that performs profiling.
     * @return Sensor specification.
     */
    @Override
    public ColumnNullsNullCountSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets the sensor parameters instance.
     * @param parameters Sensor parameters instance.
     */
    public void setParameters(ColumnNullsNullCountSensorParametersSpec parameters) {
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
     * Returns the data scope that this profiler supports. The value decides if the whole table is analyzed or each data stream defined by the default
     * data stream mapping on a table is analyzed.
     *
     * @return Data scope to analyze: the whole table or each data stream separately.
     */
    @Override
    public ProfilerDataScope getDataScope() {
        return ProfilerDataScope.table;
    }
}
