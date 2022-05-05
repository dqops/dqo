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
package ai.dqo.metadata.definitions.sensors;

import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.basespecs.ElementWrapper;
import ai.dqo.metadata.basespecs.ObjectName;

/**
 * Provider specific data quality sensor definition spec wrapper.
 */
public interface ProviderSensorDefinitionWrapper extends ElementWrapper<ProviderSensorDefinitionSpec>, ObjectName<ProviderType> {
    /**
     * Gets the data provider type.
     * @return Data provider type that supports this variant of the sensor.
     */
    ProviderType getProvider();

    /**
     * Sets a data provider type that supports this sensor.
     * @param providerType Data provider type.
     */
    void setProvider(ProviderType providerType);

    /**
     * Returns the SQL template for the template. A check may not have an SQL template and could be implemented
     * as a python module instead.
     * @return Sql Template.
     */
    String getSqlTemplate();

    /**
     * Sets a sql template used by this provider.
     * @param sqlTemplate Sql template string.
     */
    void setSqlTemplate(String sqlTemplate);
}
