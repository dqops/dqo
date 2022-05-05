/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.sensors.runners;

import ai.dqo.metadata.definitions.sensors.ProviderSensorRunnerType;

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
