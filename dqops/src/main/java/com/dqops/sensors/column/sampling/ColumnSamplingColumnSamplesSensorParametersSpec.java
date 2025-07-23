/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.column.sampling;

import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level sensor that retrieves a column value samples. Column value sampling is used in profiling and in capturing error samples for failed data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSamplingColumnSamplesSensorParametersSpec extends AbstractSensorParametersSpec {
    /**
     * The sensor name used by this sensor parameters.
     */
    public static final String SENSOR_NAME = "column/sampling/column_samples";

    public static final ChildHierarchyNodeFieldMapImpl<ColumnSamplingColumnSamplesSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The limit of results that are returned. The default value is 100 sample values with the highest count (the most popular).")
    @RequiredField
    private Integer limit = 100;

    /**
     * Returns the limit of rows that are returned in the sample.
     * @return The limit of rows in the sample.
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Sets the limit of rows to return in the sample.
     * @param limit The limit of samples to return.
     */
    public void setLimit(Integer limit) {
        this.setDirtyIf(!Objects.equals(this.limit, limit));
        this.limit = limit;
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
