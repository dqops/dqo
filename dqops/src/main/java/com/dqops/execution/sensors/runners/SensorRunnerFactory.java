/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors.runners;

import com.dqops.metadata.definitions.sensors.ProviderSensorRunnerType;

/**
 * Sensor runner factory.
 */
public interface SensorRunnerFactory {
    /**
     * Gets an instance of a sensor runner given the class name.
     * @param sensorType Sensor type.
     * @param sensorRunnerClassName Sensor runner class name (optional, only for a custom java class).
     * @return Sensor runner class name.
     */
    AbstractSensorRunner getSensorRunner(ProviderSensorRunnerType sensorType, String sensorRunnerClassName);
}
