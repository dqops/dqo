/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
