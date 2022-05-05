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
package ai.dqo.metadata.dqohome;

import ai.dqo.metadata.basespecs.Flushable;
import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionList;
import ai.dqo.metadata.id.HierarchyNode;

/**
 * Dqo.io home model to access the content of the DQO_HOME folder. Provides access to the data in the application home, like built-in rules and sensor definitions.
 * The actual implementation can use a local file system, a virtual file system or a database.
 */
public interface DqoHome extends Flushable, HierarchyNode {
    /**
     * Returns a list of sensor definitions.
     * @return Collection of sensor definitions.
     */
    SensorDefinitionList getSensors();

    /**
     * Returns a list of rules definitions.
     * @return Collection of sensor definitions.
     */
    RuleDefinitionList getRules();
}
