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
package com.dqops.sensors.column.text;

import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Column level sensor that calculates the percentage of text values with a length below the indicated length in a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextTextLengthInRangePercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextTextLengthInRangePercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Sets a minimum text length")
    private int minLength = 5;

    @JsonPropertyDescription("Sets a maximum text length")
    private int maxLength = 100;

    /** Returns a minimum string length range.
     *
     * @return lowerLengthBound
     */
    public int getMinLength() {
        return minLength;
    }

    /** Sets a minimum string length range.
     *
     * @param minLength
     */
    public void setMinLength(int minLength) {
        this.setDirtyIf(this.minLength != minLength);
        this.minLength = minLength;
    }

    /** Returns a maximum string length range.
     *
     * @return Return a upperLengthBound
     */
    public int getMaxLength() {
        return maxLength;
    }

    /** Sets a maximum string length range.
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        this.setDirtyIf(this.maxLength != maxLength);
        this.maxLength = maxLength;
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
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     *
     * @return Sensor definition name.
     */
    @Override
    public String getSensorDefinitionName() {
        return "column/text/text_length_in_range_percent";
    }
}
