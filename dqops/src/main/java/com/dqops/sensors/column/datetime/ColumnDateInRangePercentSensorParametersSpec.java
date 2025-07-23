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

import com.dqops.checks.column.checkspecs.datetime.ColumnDateInRangePercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Column level sensor that finds the percentage of date values that are outside an accepted range. This sensor detects presence of fake or corrupted dates such as 1900-01-01 or 2099-12-31.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDateInRangePercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDateInRangePercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    /**
     * Sensor name.
     */
    public static final String SENSOR_NAME = "column/datetime/date_in_range_percent";

    @JsonPropertyDescription("The earliest accepted date.")
    private LocalDate minDate = ColumnDateInRangePercentCheckSpec.EARLIEST_ACCEPTED_DATE_BY_RULE_MINER;

    @JsonPropertyDescription("The latest accepted date.")
    private LocalDate maxDate = LocalDate.of(2099, 12, 30);

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

    /**
     * Returns the lower bounds value of the range.
     * @return Lower bound of the range.
     */
    public LocalDate getMinDate() {
        return minDate;
    }

    /**
     * Sets the lower bounds value of the range.
     * @param minDate Lower bound of the range.
     */
    public void setMinDate(LocalDate minDate) {
        this.setDirtyIf(!Objects.equals(this.minDate, minDate));
        this.minDate = minDate;
    }

    /**
     * Returns the upper bounds value for the range.
     * @return Upper bound of the range.
     */
    public LocalDate getMaxDate() {
        return maxDate;
    }

    /**
     * Sets the upper bounds value for the range.
     * @param maxDate Upper bound of the range.
     */
    public void setMaxDate(LocalDate maxDate) {
        this.setDirtyIf(!Objects.equals(this.maxDate, maxDate));
        this.maxDate = maxDate;
    }
}
