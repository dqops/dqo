/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.column.datetime;

import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Column level sensor that calculates the percentage of text values that match an expected date format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextMatchDateFormatPercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextMatchDateFormatPercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    /**
     * Sensor name.
     */
    public static final String SENSOR_NAME = "column/datetime/text_match_date_format_percent";

    @JsonPropertyDescription("Expected date format. The sensor will try to parse the column records and cast the data using this format.")
    @RequiredField
    private DatetimeBuiltInDateFormats dateFormat = DatetimeBuiltInDateFormats.ISO8601;

    /**
     * Returns a desired format to parse the date.
     * @return Date format.
     */
    public DatetimeBuiltInDateFormats getDateFormat() {
        return dateFormat;
    }

    /**
     * Sets a desired format to parse the date.
     * @param dateFormat Date format.
     */
    public void setDateFormat(DatetimeBuiltInDateFormats dateFormat) {
        this.setDirtyIf(this.dateFormat != dateFormat);
        this.dateFormat = dateFormat;
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
        return SENSOR_NAME;
    }
}

