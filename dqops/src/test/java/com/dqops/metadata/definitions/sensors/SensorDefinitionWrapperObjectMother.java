/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
