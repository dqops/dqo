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
package ai.dqo.sensors.table.schema;

import ai.dqo.execution.sqltemplates.JinjaSqlTemplateSensorRunner;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Table schema data quality sensor detects if the list and order of columns have changed on the table.
 * The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns and the order of the columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableColumnListOrderedHashSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableColumnListOrderedHashSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
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
        return "table/schema/column_list_ordered_hash";
    }

    /**
     * Returns the default sensor runner class that will be used to execute this sensor.
     * The default sensor runner is {@link JinjaSqlTemplateSensorRunner}.
     *
     * @return The default sensor runner class.
     */
    @Override
    public Class<?> getSensorRunnerClass() {
        return TableColumnListOrderedHashSensorRunner.class;
    }

    /**
     * Returns true if the sensor supports data streams. The default value is true.
     *
     * @return True when the sensor supports data streams.
     */
    @Override
    @JsonIgnore
    public boolean getSupportsDataStreams() {
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
