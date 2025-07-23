/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
