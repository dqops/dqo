/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResultObjectMother;

/**
 * Object mother for JinjaTemplateRenderParameters.
 */
public class JinjaTemplateRenderParametersObjectMother {
    /**
     * Cretes jinja render parameters for a sensor run parameters using a given sensor name and provider type.
     * @param sensorExecutionRunParameters Sensor execution run parameters.
     * @param sensorName Sensor name.
     * @param providerType Provider type.
     * @return Jinja render parameters.
     */
    public static JinjaTemplateRenderParameters createForRunParametersAndBuiltInSensor(
			SensorExecutionRunParameters sensorExecutionRunParameters, String sensorName, ProviderType providerType) {
        SensorDefinitionFindResult sensorDefinitions =
                SensorDefinitionFindResultObjectMother.findDqoHomeSensorDefinition(sensorName, providerType);

        JinjaTemplateRenderParametersProvider jinjaTemplateRenderParametersProvider = new JinjaTemplateRenderParametersProviderImpl(null);
        JinjaTemplateRenderParameters renderParameters = jinjaTemplateRenderParametersProvider.createFromTrimmedObjects(
                null, sensorExecutionRunParameters, sensorDefinitions);
        return renderParameters;
    }

    /**
     * Cretes jinja render parameters for a sensor run parameters and provider type, extracting the sensor name from the sensor parameter.
     * @param sensorExecutionRunParameters Sensor execution run parameters.
     * @return Jinja render parameters.
     */
    public static JinjaTemplateRenderParameters createForRunParameters(
			SensorExecutionRunParameters sensorExecutionRunParameters) {
        assert sensorExecutionRunParameters.getSensorParameters() != null;
        String sensorName = sensorExecutionRunParameters.getEffectiveSensorRuleNames().getSensorName();
        SensorDefinitionFindResult sensorDefinitions =
                SensorDefinitionFindResultObjectMother.findDqoHomeSensorDefinition(sensorName, sensorExecutionRunParameters.getConnection().getProviderType());

        JinjaTemplateRenderParametersProvider jinjaTemplateRenderParametersProvider = new JinjaTemplateRenderParametersProviderImpl(null);
        JinjaTemplateRenderParameters renderParameters = jinjaTemplateRenderParametersProvider.createFromTrimmedObjects(
                null, sensorExecutionRunParameters, sensorDefinitions);
        return renderParameters;
    }
}
