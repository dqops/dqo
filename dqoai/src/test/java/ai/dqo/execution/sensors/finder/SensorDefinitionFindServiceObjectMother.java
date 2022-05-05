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

/**
 * Object mother for a sensor definition finder. Can also find sensor definitions.
 */
public class SensorDefinitionFindServiceObjectMother {
    /**
     * Creates a sensor definition find service.
     * @return Sensor definition find service.
     */
    public static SensorDefinitionFindService getSensorDefinitionFindService() {
        return new SensorDefinitionFindServiceImpl();
    }
}
