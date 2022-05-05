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
import ai.dqo.core.filesystem.virtual.HomeFilePath;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.HomeType;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.stereotype.Component;

/**
 * Provider sensor definition wrapper finder. Tries to find a sensor definition for a given provider first in the user home, then in the DQO_HOME (built-in sensors).
 */
@Component
public class SensorDefinitionFindServiceImpl implements SensorDefinitionFindService {
    /**
     * Finds a provider specific sensor definition of a given sensor and provider type.
     * First tries to find a custom sensor definition (or a built-in sensor definition override in the user home).
     * If a sensor implementation was not found in the user home then finds the definition in the default dqo home.
     * @param checkExecutionContext Check execution context with references to both the user home and dqo home.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @return Provider sensor definition.
     */
    public SensorDefinitionFindResult findProviderSensorDefinition(
			CheckExecutionContext checkExecutionContext, String sensorName, ProviderType providerType) {
        UserHome userHome = checkExecutionContext.getUserHomeContext().getUserHome();
        DqoHome dqoHome = checkExecutionContext.getDqoHomeContext().getDqoHome();

        String jinjaFileNameRelativeToHome = sensorName + "/" + providerType.name() + ".sql.jinja2";
        HomeFilePath jinjaFileHomePath = HomeFilePath.fromFilePath(jinjaFileNameRelativeToHome);

        SensorDefinitionWrapper userSensorDefinitionWrapper = userHome.getSensors().getByObjectName(sensorName, true);
        if (userSensorDefinitionWrapper != null) {
            ProviderSensorDefinitionWrapper userProviderSensorDefinitionWrapper =
                    userSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
            if (userProviderSensorDefinitionWrapper != null) {
                boolean userHomeIsLocalFileSystem = checkExecutionContext.getUserHomeContext().getHomeRoot() != null &&
                        checkExecutionContext.getUserHomeContext().getHomeRoot().isLocalFileSystem();

                return new SensorDefinitionFindResult(userSensorDefinitionWrapper.getSpec(),
                        userProviderSensorDefinitionWrapper.getSpec(),
                        userHomeIsLocalFileSystem ? null : userProviderSensorDefinitionWrapper.getSqlTemplate(),  // return a sql template as a string only when the file is not stored in a file system
                        providerType,
                        HomeType.USER_HOME,
                        userHomeIsLocalFileSystem ? jinjaFileHomePath : null);
            }
        }

        SensorDefinitionWrapper builtinSensorDefinitionWrapper = dqoHome.getSensors().getByObjectName(sensorName, true);
        if (builtinSensorDefinitionWrapper == null) {
            return null;
        }

        ProviderSensorDefinitionWrapper builtinProviderSensorDefinitionWrapper =
                builtinSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
        if (builtinSensorDefinitionWrapper == null) {
            return null;
        }

        boolean dqoHomeIsLocalFileSystem = checkExecutionContext.getDqoHomeContext().getHomeRoot() != null &&
                checkExecutionContext.getDqoHomeContext().getHomeRoot().isLocalFileSystem();

        return new SensorDefinitionFindResult(
                builtinSensorDefinitionWrapper.getSpec(),
                builtinProviderSensorDefinitionWrapper.getSpec(),
                dqoHomeIsLocalFileSystem ? null : builtinProviderSensorDefinitionWrapper.getSqlTemplate(),
                providerType,
                HomeType.DQO_HOME,
                dqoHomeIsLocalFileSystem ? jinjaFileHomePath : null);
    }
}
