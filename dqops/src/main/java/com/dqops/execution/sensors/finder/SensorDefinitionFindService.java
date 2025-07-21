/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors.finder;

import com.dqops.connectors.ProviderType;
import com.dqops.execution.ExecutionContext;

/**
 * Provider sensor definition wrapper finder. Tries to find a sensor definition for a given provider first in the user home, then in the DQO_HOME (built-in sensors).
 */
public interface SensorDefinitionFindService {
    /**
     * Finds a provider specific sensor definition of a given sensor and provider type.
     * First tries to find a custom sensor definition (or a built-in sensor definition override in the user home).
     * If a sensor implementation was not found in the user home then finds the definition in the default dqo home.
     * @param executionContext Check execution context with references to both the user home and dqo home.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @return Provider sensor definition.
     */
    SensorDefinitionFindResult findProviderSensorDefinition(
            ExecutionContext executionContext, String sensorName, ProviderType providerType);
}
