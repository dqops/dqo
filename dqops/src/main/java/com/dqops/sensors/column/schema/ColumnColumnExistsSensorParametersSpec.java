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
package com.dqops.sensors.column.schema;

import com.dqops.execution.sqltemplates.rendering.JinjaSqlTemplateSensorRunner;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Column level data quality sensor that reads the metadata of the table from the data source and checks if the column name exists on the table.
 * Returns 1.0 when the column was found, 0.0 when the column is missing.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnColumnExistsSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnColumnExistsSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

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
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     *
     * @return Sensor definition name.
     */
    @Override
    public String getSensorDefinitionName() {
        return "column/schema/column_exists";
    }

    /**
     * Returns the default sensor runner class that will be used to execute this sensor.
     * The default sensor runner is {@link JinjaSqlTemplateSensorRunner}.
     *
     * @return The default sensor runner class.
     */
    @Override
    public Class<?> getSensorRunnerClass() {
        return ColumnColumnExistsSensorRunner.class;
    }

    /**
     * Returns true if the sensor supports data streams. The default value is true.
     *
     * @return True when the sensor supports data streams.
     */
    @Override
    @JsonIgnore
    public boolean getSupportsDataGrouping() {
        return false;
    }

    /**
     * Returns true if the sensor supports partitioned checks. The default value is true.
     *
     * @return True when the sensor support partitioned checks.
     */
    @Override
    @JsonIgnore
    public boolean getSupportsPartitionedChecks() {
        return false;
    }

    /**
     * Returns true if the sensor is a special type of a sensor that does not have an SQL template (for example, a column_count check)
     * and is automatically supported on all providers.
     *
     * @return True when the sensor supports any provider, false when it requires an SQL template or a custom configured provider sensor yaml file to be properly configured.
     */
    @Override
    @JsonIgnore
    public boolean getAlwaysSupportedOnAllProviders() {
        return true;
    }
}
