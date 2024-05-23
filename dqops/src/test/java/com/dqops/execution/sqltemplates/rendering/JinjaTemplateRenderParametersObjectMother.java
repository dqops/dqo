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
