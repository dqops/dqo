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

import com.dqops.metadata.basespecs.AbstractIndexingList;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.List;

/**
 * Data quality sensor definition collection. Tracks the status of the child elements (addition, removal).
 */
public class SensorDefinitionListImpl extends AbstractIndexingList<String, SensorDefinitionWrapper> implements SensorDefinitionList {
    public SensorDefinitionListImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     *
     * @param sensorName Object name (sensor name).
     * @return Created and detached new instance with the object name assigned.
     */
    @Override
    protected SensorDefinitionWrapper createNewElement(String sensorName) {
        SensorDefinitionWrapperImpl checkDefinitionWrapper = new SensorDefinitionWrapperImpl();
        checkDefinitionWrapper.setName(sensorName);
        checkDefinitionWrapper.setSpec(new SensorDefinitionSpec());
        checkDefinitionWrapper.setStatus(InstanceStatus.ADDED);
        return checkDefinitionWrapper;
    }

    /**
     * Removes a source model. The source is marked for deletion and will be removed on flush.
     *
     * @param sensorName Data quality check name to remove.
     * @return True when the model will be removed, false otherwise.
     */
    @Override
    public boolean remove(String sensorName) {
        SensorDefinitionWrapper sensorDefinitionWrapper = this.getByObjectName(sensorName, true);
        if (sensorDefinitionWrapper == null) {
            return false;
        }
        return this.remove(sensorDefinitionWrapper);
    }

    /**
     * Returns the collection as an immutable list.
     *
     * @return List of data quality check definitions.
     */
    @Override
    public List<SensorDefinitionWrapper> toList() {
        return List.copyOf(this);
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
}
