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
package ai.dqo.sensors.table.timeliness;

import ai.dqo.metadata.fields.ControlType;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.column.AbstractColumnSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level sensor that calculates the datetime difference between datetime columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated  // needs serious refactoring
public class TableTimelinessAverageDelaySensorParametersSpec extends AbstractColumnSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessAverageDelaySensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The first DateTime column used to calculate the time difference. If a column's format is not datetime, column will be cast to datetime.")
    @ControlType(ParameterDataType.column_name_type)
    private String column1;

    @JsonPropertyDescription("The second DateTime column used to calculate the time difference. If a column's format is not datetime, column will be cast to datetime.")
    @ControlType(ParameterDataType.column_name_type)
    private String column2;

    @JsonPropertyDescription("Field used to set a time scale to measure a datetime difference. A DAY is a default. Accepted scales are the following: SECOND, MINUTE, HOUR, DAY")
    private BuiltInTimeScale timeScale = BuiltInTimeScale.DAY;

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
        return "table/timeliness/average_delay";
    }

    /**
     * Returns the time scale in which we can measure a time difference.
     *
     * @return timeScale
     */
    public BuiltInTimeScale getTimeScale() {
        return timeScale;
    }

    /**
     * Sets a timeScale parameter that defines scale of datetime difference.
     *
     * @param timeScale
     */
    public void setTimeScale(BuiltInTimeScale timeScale) {
        this.setDirtyIf(!Objects.equals(this.timeScale, timeScale));
        this.timeScale = timeScale;

    }

    /**
     * Returns a column1Datetime that indicates a column used to calculate datetime difference.
     *
     * @return column1Datetime
     */
    public String getColumn1() {
        return column1;
    }

    /**
     *  Sets a first column used to calculate time difference.
     *
     * @param column1
     */
    public void setColumn1(String column1) {
        this.setDirtyIf(!Objects.equals(this.column1, column1));
        this.column1 = column1;
    }

    /**
     * Returns a column2Datetime that indicates a column used to calculate datetime difference.
     *
     * @return column2Datetime
     */
    public String getColumn2() {
        return column2;
    }

    /**
     *  Sets a second column used to calculate time difference.
     *
     * @param column2
     */
    public void setColumn2(String column2) {
        this.setDirtyIf(!Objects.equals(this.column2, column2));
        this.column2 = column2;
    }
}
