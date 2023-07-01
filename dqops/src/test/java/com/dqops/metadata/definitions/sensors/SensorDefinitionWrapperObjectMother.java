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
package com.dqops.metadata.definitions.sensors;

import com.dqops.connectors.ProviderType;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.dqohome.DqoHomeObjectMother;

/**
 * Object mother that can retrieve sensor definitions from the DQO_HOME or a local user home.
 */
public class SensorDefinitionWrapperObjectMother {
    /**
     * Finds a sensor definition in the DQO_HOME.
     * @param sensorName Sensor name to find.
     * @return Sensor definition wrapper.
     */
    public static SensorDefinitionWrapper findDqoHomeSensorDefinition(String sensorName) {
        DqoHome dqoHome = DqoHomeObjectMother.getDqoHome();
        SensorDefinitionWrapper sensorDefinitionWrapper = dqoHome.getSensors().getByObjectName(sensorName, true);
        return sensorDefinitionWrapper;
    }

    /**
     * Returns a provider specific sensor definition wrapper that is defined in the DQO_HOME.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @return Provider specific sensor definition.
     */
    public static ProviderSensorDefinitionWrapper findProviderDqoHomeSensorDefinition(String sensorName, ProviderType providerType) {
        SensorDefinitionWrapper sensorDefinition = findDqoHomeSensorDefinition(sensorName);
        if (sensorDefinition == null) {
            return null;
        }

        ProviderSensorDefinitionList providerSensors = sensorDefinition.getProviderSensors();
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                providerSensors.getByObjectName(providerType, true);
        return providerSensorDefinitionWrapper;
    }
}
