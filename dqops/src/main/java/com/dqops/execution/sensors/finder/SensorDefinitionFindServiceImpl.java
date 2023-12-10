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
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.HomeType;
import com.dqops.metadata.userhome.UserHome;
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
     * @param executionContext Check execution context with references to both the user home and dqo home.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @return Provider sensor definition.
     */
    public SensorDefinitionFindResult findProviderSensorDefinition(
            ExecutionContext executionContext, String sensorName, ProviderType providerType) {
        UserHome userHome = executionContext.getUserHomeContext() != null ? executionContext.getUserHomeContext().getUserHome() : null;
        DqoHome dqoHome = executionContext.getDqoHomeContext().getDqoHome();

        String jinjaFileNameRelativeToHome = sensorName + "/" + providerType.name() + ".sql.jinja2";
        String dataDomain = executionContext.getUserHomeContext().getUserIdentity().getDataDomainFolder();
        HomeFilePath jinjaFileHomePath = HomeFilePath.fromFilePath(dataDomain, jinjaFileNameRelativeToHome);

        if (userHome != null) {
            SensorDefinitionWrapper userSensorDefinitionWrapper = userHome.getSensors().getByObjectName(sensorName, true);
            if (userSensorDefinitionWrapper != null) {
                ProviderSensorDefinitionWrapper userProviderSensorDefinitionWrapper =
                        userSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
                if (userProviderSensorDefinitionWrapper != null) {
                    boolean userHomeIsLocalFileSystem = executionContext.getUserHomeContext().getHomeRoot() != null &&
                            executionContext.getUserHomeContext().getHomeRoot().isLocalFileSystem();

                    return new SensorDefinitionFindResult(userSensorDefinitionWrapper.getSpec(),
                            userProviderSensorDefinitionWrapper.getSpec(),
                            userHomeIsLocalFileSystem ? null : userProviderSensorDefinitionWrapper.getSqlTemplate(),  // return a sql template as a string only when the file is not stored in a file system
                            userProviderSensorDefinitionWrapper.getSqlTemplateLastModified(),
                            providerType,
                            HomeType.USER_HOME,
                            userHomeIsLocalFileSystem ? jinjaFileHomePath : null);
                }
            }
        }

        SensorDefinitionWrapper builtinSensorDefinitionWrapper = dqoHome.getSensors().getByObjectName(sensorName, true);
        if (builtinSensorDefinitionWrapper == null) {
            return null;
        }

        ProviderSensorDefinitionWrapper builtinProviderSensorDefinitionWrapper =
                builtinSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
        if (builtinProviderSensorDefinitionWrapper == null) {
            return null;
        }

        boolean dqoHomeIsLocalFileSystem = executionContext.getDqoHomeContext().getHomeRoot() != null &&
                executionContext.getDqoHomeContext().getHomeRoot().isLocalFileSystem();

        return new SensorDefinitionFindResult(
                builtinSensorDefinitionWrapper.getSpec(),
                builtinProviderSensorDefinitionWrapper.getSpec(),
                dqoHomeIsLocalFileSystem ? null : builtinProviderSensorDefinitionWrapper.getSqlTemplate(),
                builtinProviderSensorDefinitionWrapper.getSqlTemplateLastModified(),
                providerType,
                HomeType.DQO_HOME,
                dqoHomeIsLocalFileSystem ? jinjaFileHomePath : null);
    }
}
