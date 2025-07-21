/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.sensors;

import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Data quality sensor definition spec wrapper.
 */
public class SensorDefinitionWrapperImpl extends AbstractElementWrapper<String, SensorDefinitionSpec> implements SensorDefinitionWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<SensorDefinitionWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
			put("provider_sensors", o -> o.providerSensors);
        }
    };

    @JsonIgnore
    private String name;
    @JsonIgnore
    protected ProviderSensorDefinitionListImpl providerSensors;

    /**
     * Creates a default sensor definition wrapper.
     */
    public SensorDefinitionWrapperImpl() {
        this.providerSensors = new ProviderSensorDefinitionListImpl(false);
    }

    public SensorDefinitionWrapperImpl(boolean readOnly) {
        super(readOnly);
        this.providerSensors = new ProviderSensorDefinitionListImpl(readOnly);
    }

    /**
     * Creates a sensor definition wrapper given the sensor name.
     * @param name Sensor name.
     * @param readOnly Make the wrapper read-only.
     */
    public SensorDefinitionWrapperImpl(String name, boolean readOnly) {
        this(readOnly);
        this.name = name;
    }

    /**
     * Gets the data quality sensor name.
     * @return Data quality sensor name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a data quality sensor name.
     * @param name Data quality sensor name.
     */
    public void setName(String name) {
        assert this.name == null || Objects.equals(this.name, name); // cannot change the name
        this.name = name;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public String getObjectName() {
        return this.getName();
    }

    /**
     * Returns a list of provider specific sensors.
     * @return List of provider specific sensors.
     */
    @Override
    public ProviderSensorDefinitionList getProviderSensors() {
        return providerSensors;
    }

    /**
     * Sets an instance of the provider's sensor collection.
     * @param providerSensors New provider sensor collection.
     */
    public void setProviderSensors(ProviderSensorDefinitionListImpl providerSensors) {
		this.setDirtyIf(this.providerSensors != null && this.providerSensors != providerSensors);    // this is a special case, we are not making the object modified
        this.providerSensors = providerSensors;
        if (providerSensors != null && this.getHierarchyId() != null) {
			this.propagateHierarchyIdToField(providerSensors, "provider_sensors");
        }
    }

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    @Override
    public void flush() {
		this.getProviderSensors().flush();
        super.flush();
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public SensorDefinitionWrapper clone() {
        return (SensorDefinitionWrapper)super.deepClone();
    }
}
