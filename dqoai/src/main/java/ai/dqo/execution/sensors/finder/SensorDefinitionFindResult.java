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
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.storage.localfiles.HomeType;
import lombok.EqualsAndHashCode;

/**
 * Sensor definition search result.
 */
@EqualsAndHashCode(callSuper = false)
public class SensorDefinitionFindResult {
    private final HomeType home;
    private final SensorDefinitionSpec sensorDefinitionSpec;
    private final ProviderSensorDefinitionSpec providerSensorDefinitionSpec;
    private final HomeFilePath templateFilePath;
    private final String sqlTemplateText;
    private final ProviderType providerType;

    /**
     * Creates a sensor search result.
     * @param sensorDefinitionSpec Found sensor definition specification (we need just the specification).
     * @param providerSensorDefinitionSpec Provider specific sensor definition.
     * @param sqlTemplateText Jinja sql template text (if the sensor is defined as a Jinja2 template).
     * @param providerType Provider type.
     * @param home Home type where the template was found.
     * @param templateFilePath Template file path in the home folder.
     */
    public SensorDefinitionFindResult(SensorDefinitionSpec sensorDefinitionSpec,
									  ProviderSensorDefinitionSpec providerSensorDefinitionSpec,
									  String sqlTemplateText,
									  ProviderType providerType,
									  HomeType home,
									  HomeFilePath templateFilePath) {
        this.sensorDefinitionSpec = sensorDefinitionSpec;
        this.providerSensorDefinitionSpec = providerSensorDefinitionSpec;
        this.sqlTemplateText = sqlTemplateText;
        this.providerType = providerType;
        this.home = home;
        this.templateFilePath = templateFilePath;
    }

    /**
     * Sensor definition specification.
     * @return Sensor definition.
     */
    public SensorDefinitionSpec getSensorDefinitionSpec() {
        return sensorDefinitionSpec;
    }

    /**
     * Provider specific sensor definition.
     * @return Provider specific sensor definition.
     */
    public ProviderSensorDefinitionSpec getProviderSensorDefinitionSpec() {
        return providerSensorDefinitionSpec;
    }

    /**
     * Jinja2 SQL template text - if the sensor is implemented as a template.
     * @return Sql template.
     */
    public String getSqlTemplateText() {
        return sqlTemplateText;
    }

    /**
     * Returns the provider type.
     * @return Provider type.
     */
    public ProviderType getProviderType() { return providerType; }

    /**
     * Home type where the sensor definition (template) was found.
     * @return Home type, only for sensors stored in the local file system.
     */
    public HomeType getHome() {
        return home;
    }

    /**
     * Template file path in respective home (user home or DQO_HOME) where a sensor template is located. It is a path to a jinja2 template.
     * @return Path to the jinja2 template of the sensor.
     */
    public HomeFilePath getTemplateFilePath() {
        return templateFilePath;
    }
}
