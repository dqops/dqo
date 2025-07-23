/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.table.uniqueness;

import com.dqops.metadata.fields.ControlDisplayHint;
import com.dqops.metadata.fields.DisplayHint;
import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Table sensor that executes a duplicate record count query.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableDuplicateRecordCountSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableDuplicateRecordCountSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("A list of columns used for uniqueness record duplicate verification.")
    @SampleValues(values = { "id", "created_at" })
    @RequiredField
    @ControlDisplayHint(DisplayHint.column_names)
    private List<String> columns;

    /**
     * Returns given values from user.
     * @return values.
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * Sets a List given from user.
     * @param columns values given from user.
     */
    public void setColumns(List<String> columns) {
        this.setDirtyIf(!Objects.equals(this.columns, columns));
        this.columns = columns != null ? Collections.unmodifiableList(columns) : null;
    }

    /**
     * Sensor name used by this sensor parameters.
     */
    public static final String SENSOR_NAME = "table/uniqueness/duplicate_record_count";

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
     * Returns the default value that is used when the sensor returned no rows.
     *
     * @return Default value to use when the sensor returned no rows.
     */
    @Override
    @JsonIgnore
    public Double getDefaultValue() {
        return 0.0;
    }

    public static class TableDuplicateRecordCountSensorParametersSpecSampleFactory implements SampleValueFactory<TableDuplicateRecordCountSensorParametersSpec> {
        @Override
        public TableDuplicateRecordCountSensorParametersSpec createSample() {
            return new TableDuplicateRecordCountSensorParametersSpec();
        }
    }
}
