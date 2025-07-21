/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.statistics.table.schema;

import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.table.schema.TableColumnCountSensorParametersSpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Table column count profiler.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSchemaColumnCountStatisticsCollectorSpec extends AbstractStatisticsCollectorSpec<TableColumnCountSensorParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<TableSchemaColumnCountStatisticsCollectorSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractStatisticsCollectorSpec.FIELDS) {
        {
        }
    };

    /**
     * Sensor name used by this collector.
     */
    public static final String SENSOR_NAME = TableColumnCountSensorParametersSpec.SENSOR_NAME;

    @JsonPropertyDescription("Profiler parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableColumnCountSensorParametersSpec parameters = new TableColumnCountSensorParametersSpec();

    /**
     * Returns the configuration of the sensor that performs profiling.
     * @return Sensor specification.
     */
    @Override
    public TableColumnCountSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets the sensor parameters instance.
     * @param parameters Sensor parameters instance.
     */
    public void setParameters(TableColumnCountSensorParametersSpec parameters) {
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
     * Returns the array of supported data type categories that the profiler supports.
     * Table level sensors should return null because table level profilers do not operate on columns and are not sensitive to the profiled column's type.
     *
     * @return Array of supported data types or null when the profiler has no limitations.
     */
    @Override
    public DataTypeCategory[] getSupportedDataTypes() {
        return null;
    }
}
