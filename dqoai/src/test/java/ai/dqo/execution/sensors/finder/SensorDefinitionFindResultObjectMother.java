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
package ai.dqo.execution.sensors.finder;

import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;

/**
 * Sensor definition object mother, finds rule definitions.
 */
public class SensorDefinitionFindResultObjectMother {
    /**
     * Finds a sensor definition in the default DQO_HOME. Can find only built-in sensor definitions.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @return Sensor definition and provider sensor definition retrieved from the DQO_HOME.
     */
    public static SensorDefinitionFindResult findDqoHomeSensorDefinition(String sensorName, ProviderType providerType) {
        SensorDefinitionFindService sensorDefinitionFindService = SensorDefinitionFindServiceObjectMother.getSensorDefinitionFindService();
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();

        CheckExecutionContext checkExecutionContext = new CheckExecutionContext(inMemoryFileHomeContext, dqoHomeContext);
        SensorDefinitionFindResult providerSensorDefinition = sensorDefinitionFindService.findProviderSensorDefinition(checkExecutionContext, sensorName, providerType);
        return providerSensorDefinition;
    }

    /**
     * Finds a sensor definition searching in both the provided user home context and the default DQO_HOME.
     * @param userHomeContext User home context to search for the sensor definition.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @return Sensor definition and provider sensor definition retrieved from the user home or DQO_HOME (whichever has the definition first).
     */
    public static SensorDefinitionFindResult findSensorDefinition(UserHomeContext userHomeContext, String sensorName, ProviderType providerType) {
        SensorDefinitionFindService sensorDefinitionFindService = SensorDefinitionFindServiceObjectMother.getSensorDefinitionFindService();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();

        CheckExecutionContext checkExecutionContext = new CheckExecutionContext(userHomeContext, dqoHomeContext);
        SensorDefinitionFindResult providerSensorDefinition = sensorDefinitionFindService.findProviderSensorDefinition(checkExecutionContext, sensorName, providerType);
        return providerSensorDefinition;
    }
}
