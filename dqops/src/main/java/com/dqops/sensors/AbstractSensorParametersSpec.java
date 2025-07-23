/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.execution.sqltemplates.rendering.JinjaSqlTemplateSensorRunner;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for parameters for all sensors. This specification object is used to configure a sensor.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSensorParametersSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String filter;

    /**
     * Returns an optional WHERE clause filter used by this check for SQL sensors.
     * @return Filter.
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Sets an optional filter added to the WHERE clause.
     * @param filter Optional filter.
     */
    public void setFilter(String filter) {
		setDirtyIf(!Objects.equals(this.filter, filter));
        this.filter = filter;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public AbstractSensorParametersSpec deepClone() {
        AbstractSensorParametersSpec cloned = (AbstractSensorParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     * @return Sensor definition name.
     */
    @JsonIgnore
    public abstract String getSensorDefinitionName();

    /**
     * Creates a cloned and trimmed version of the object. A trimmed and cloned copy is passed to the sensor.
     * All configurable variables that may use a secret value or environment variable expansion in the form ${ENV_VAR} are also expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Cloned and expanded copy of the object.
     */
    public AbstractSensorParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        AbstractSensorParametersSpec cloned = (AbstractSensorParametersSpec)super.deepClone();
        cloned.filter = secretValueProvider.expandValue(cloned.filter, lookupContext);

        ClassInfo reflectionClassInfo = this.getChildMap().getReflectionClassInfo();
        for (FieldInfo fieldInfo : reflectionClassInfo.getFields()) {
            if (fieldInfo.getDataType() == ParameterDataType.string_list_type) {
                Object rawFieldValue = fieldInfo.getRawFieldValue(cloned);
                if (rawFieldValue instanceof List) {
                    List<String> originalList = (List<String>)rawFieldValue;
                    List<String> expandedList = secretValueProvider.expandList(originalList, lookupContext);
                    fieldInfo.setFieldValue(expandedList, cloned);
                }
            }
        }

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
     * Returns the default sensor runner class that will be used to execute this sensor.
     * The default sensor runner is {@link JinjaSqlTemplateSensorRunner}.
     * @return The default sensor runner class.
     */
    @JsonIgnore
    public Class<?> getSensorRunnerClass() {
        return JinjaSqlTemplateSensorRunner.class;
    }

    /**
     * Returns true if the sensor supports data grouping. The default value is true.
     * @return True when the sensor supports data grouping.
     */
    @JsonIgnore
    public boolean getSupportsDataGrouping() {
       return true;
    }

    /**
     * Returns true if the sensor supports partitioned checks. The default value is true.
     * @return True when the sensor support partitioned checks.
     */
    @JsonIgnore
    public boolean getSupportsPartitionedChecks() {
        return true;
    }

    /**
     * Returns true if the sensor is a special type of a sensor that does not have an SQL template (for example, a column_count check)
     * and is automatically supported on all providers.
     * @return True when the sensor supports any provider, false when it requires an SQL template or a custom configured provider sensor yaml file to be properly configured.
     */
    @JsonIgnore
    public boolean getAlwaysSupportedOnAllProviders() {
        return false;
    }

    /**
     * Returns the default value that is used when the sensor returned no rows.
     * @return Default value to use when the sensor returned no rows.
     */
    @JsonIgnore
    public Double getDefaultValue() { return null;}

    /**
     * Returns true if the sensor is a timeliness sensor that requires an event timestamp column. The default value is false.
     * @return True the sensor requires an event timestamp column.
     */
    @JsonIgnore
    public boolean getRequiresEventTimestampColumn() {
        return false;
    }

    /**
     * Returns true if the sensor is a timeliness sensor that requires an ingestion timestamp column. The default value is false.
     * @return True the sensor requires an ingestion timestamp column.
     */
    @JsonIgnore
    public boolean getRequiresIngestionTimestampColumn() {
        return false;
    }
}
