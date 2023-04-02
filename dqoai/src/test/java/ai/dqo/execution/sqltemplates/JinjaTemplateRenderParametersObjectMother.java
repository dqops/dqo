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
package ai.dqo.execution.sqltemplates;

import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResultObjectMother;

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

        JinjaTemplateRenderParameters renderParameters = JinjaTemplateRenderParameters.createFromTrimmedObjects(
                sensorExecutionRunParameters, sensorDefinitions);
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
        String sensorName = sensorExecutionRunParameters.getSensorParameters().getSensorDefinitionName(
                sensorExecutionRunParameters.getCheck(), sensorExecutionRunParameters.getProfiler());
        SensorDefinitionFindResult sensorDefinitions =
                SensorDefinitionFindResultObjectMother.findDqoHomeSensorDefinition(sensorName, sensorExecutionRunParameters.getConnection().getProviderType());

        JinjaTemplateRenderParameters renderParameters = JinjaTemplateRenderParameters.createFromTrimmedObjects(
                sensorExecutionRunParameters, sensorDefinitions);
        return renderParameters;
    }
}
