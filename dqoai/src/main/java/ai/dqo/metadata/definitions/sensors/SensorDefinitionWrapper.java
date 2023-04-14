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
package ai.dqo.metadata.definitions.sensors;

import ai.dqo.metadata.basespecs.ElementWrapper;
import ai.dqo.metadata.basespecs.ObjectName;

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
