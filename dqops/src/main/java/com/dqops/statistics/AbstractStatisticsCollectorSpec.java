/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.statistics;

import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Base class for special data statistics collector checks that capture basic profiling metrics from tables and columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractStatisticsCollectorSpec<S extends AbstractSensorParametersSpec> extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractStatisticsCollectorSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("parameters", o -> o.getParameters());
        }
    };

    @JsonPropertyDescription("Disables this profiler. Only enabled profilers are executed during a profiling process.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    /**
     * Returns the sensor parameters spec object that identifies the sensor definition to use and contains parameters.
     * @return Sensor parameters.
     */
    public abstract S getParameters();

    /**
     * Checks if the data profiler is disabled.
     * @return True when the profiler is disabled.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled flag on a profiler.
     * @param disabled Disabled flag. False - the profiler will not be executed, true - the profiler is included in the default profiling set.
     */
    public void setDisabled(boolean disabled) {
        this.setDirtyIf(this.disabled != disabled);
        this.disabled = disabled;
    }
    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public AbstractStatisticsCollectorSpec deepClone() {
        AbstractStatisticsCollectorSpec cloned = (AbstractStatisticsCollectorSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and trimmed version of the object. A trimmed and cloned copy is passed to the sensor.
     * All configurable variables that may use a secret value or environment variable expansion in the form ${ENV_VAR} are also expanded.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public AbstractStatisticsCollectorSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        AbstractStatisticsCollectorSpec cloned = this.deepClone();
        return cloned;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Returns the profiling category name retrieved from the category field name used to store a container of profiler categories
     * in the metadata.
     * @return Profiler category name.
     */
    @JsonIgnore
    public String getCategoryName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.get(hierarchyId.size() - 2).toString();
    }

    /**
     * Returns the data profiler name (YAML compliant) that is used as a field name on a profiler category class.
     * @return Profiler name, for example "row_count", etc.
     */
    @JsonIgnore
    public String getProfilerName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.getLast().toString();
    }

    /**
     * Returns the array of supported data type categories that the profiler supports.
     * Table level sensors should return null because table level profilers do not operate on columns and are not sensitive to the profiled column's type.
     * @return Array of supported data types or null when the profiler has no limitations.
     */
    @JsonIgnore
    public abstract DataTypeCategory[] getSupportedDataTypes(); // TODO: the list of supported data types could be moved to the sensor definition yaml to support full extensibility
}
