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
package ai.dqo.services.sensor;


import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionList;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.sensors.SensorBasicModel;
import ai.dqo.rest.models.sensors.SensorFolderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for mapping a SensorFolderModel (REST model) object to a SensorBasicModel (Rest model) object.
 */
@Service
public class SensorFolderModelServiceImpl implements SensorFolderModelService {

    private DqoHomeContextFactory dqoHomeContextFactory;
    private UserHomeContextFactory userHomeContextFactory;
    private final static String SENSORS = "Sensors";


    /**
     * Creates an instance of a service by injecting dependencies.
     * @param dqoHomeContextFactory      Dqo home context factory.
     * @param userHomeContextFactory      User home context factory.
     */
    @Autowired
    public SensorFolderModelServiceImpl(DqoHomeContextFactory dqoHomeContextFactory,
                                        UserHomeContextFactory userHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     Retrieves DQO sensors from the user home and DQO home, and creates a SensorFolderModel containing them.
     @return A SensorFolderModel containing the DQO sensors from the user home and DQO home.
     */
    public SensorFolderModel getDqoSensors() {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SensorFolderModel sensorFolderModel = new SensorFolderModel(SENSORS);

        userHome.getSensors().forEach(sensorDefinitionWrapper -> {
            Map<ProviderType, SensorBasicModel> userHomeSensorBasicModel = new HashMap<>();
            sensorDefinitionWrapper.getProviderSensors().forEach(providerSensorDefinitionWrapper -> {
                ai.dqo.rest.models.sensors.SensorBasicModel sensorBasicModel = new SensorBasicModel();
                if (providerSensorDefinitionWrapper != null) {
                    sensorBasicModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
                    sensorBasicModel.setProviderSensorDefinitionSpec(providerSensorDefinitionWrapper.getSpec());
                    sensorBasicModel.setCustom(true);
                }
                userHomeSensorBasicModel.put(providerSensorDefinitionWrapper.getProvider(), sensorBasicModel);
            });

            sensorFolderModel.withDqoSensor(sensorDefinitionWrapper.getName(), userHomeSensorBasicModel);
        });

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        SensorDefinitionList dqoHomeSensorDefinitionList = dqoHome.getSensors();

        dqoHome.getSensors().forEach(sensorDefinitionWrapper -> {
            SensorDefinitionWrapper dqoHomeSensorDefinitionWrapper = dqoHomeSensorDefinitionList.getByObjectName(sensorDefinitionWrapper.getName(), true);
            Map<ProviderType, SensorBasicModel> dqoHomeSensorBasicModel = new HashMap<>();
            for (ProviderType providerType : ProviderType.values()) {
                ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper = dqoHomeSensorDefinitionWrapper.getProviderSensors().getByObjectName(providerType, true);
                SensorBasicModel sensorBasicModel = new SensorBasicModel();
                if (providerSensorDefinitionWrapper != null) {
                    sensorBasicModel.setSqlTemplate(providerSensorDefinitionWrapper.getSqlTemplate());
                    sensorBasicModel.setProviderSensorDefinitionSpec(providerSensorDefinitionWrapper.getSpec());
                }
                dqoHomeSensorBasicModel.put(providerType, sensorBasicModel);
            }
            sensorFolderModel.withDqoSensor(sensorDefinitionWrapper.getName(), dqoHomeSensorBasicModel);
        });
        return sensorFolderModel;
    }

}
