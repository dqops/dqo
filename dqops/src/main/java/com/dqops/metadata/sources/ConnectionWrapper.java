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
package com.dqops.metadata.sources;

import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;
import com.dqops.metadata.scheduling.SchedulingRootNode;

/**
 * Connection spec wrapper.
 */
public interface ConnectionWrapper extends ElementWrapper<ConnectionSpec>, ObjectName<String>, SchedulingRootNode {
    /**
     * Gets the connection name.
     * @return Connection name.
     */
    String getName();

    /**
     * Sets a connection name.
     * @param name Connection name.
     */
    void setName(String name);

    /**
     * Returns a list of tables.
     * @return List of tables.
     */
    TableList getTables();
}
