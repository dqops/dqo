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
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;

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

        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);
        SensorDefinitionFindResult providerSensorDefinition = sensorDefinitionFindService.findProviderSensorDefinition(executionContext, sensorName, providerType);
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

        ExecutionContext executionContext = new ExecutionContext(userHomeContext, dqoHomeContext);
        SensorDefinitionFindResult providerSensorDefinition = sensorDefinitionFindService.findProviderSensorDefinition(executionContext, sensorName, providerType);
        return providerSensorDefinition;
    }
}
