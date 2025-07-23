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

import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;

/**
 * Data quality sensor definition spec wrapper.
 */
public interface SensorDefinitionWrapper extends ElementWrapper<SensorDefinitionSpec>, ObjectName<String> {
    /**
     * Gets the data quality sensor name.
     * @return Data quality sensor name.
     */
    String getName();

    /**
     * Sets a data quality sensor name.
     * @param name Data quality sensor name.
     */
    void setName(String name);

    /**
     * Returns a list of provider specific implementations.
     * @return List of provider specific implementations.
     */
    ProviderSensorDefinitionList getProviderSensors();

    /**
     * Creates a deep clone of the object.
     * @return Deep cloned object.
     */
    SensorDefinitionWrapper clone();
}
