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
package ai.dqo.metadata.sources;

import ai.dqo.metadata.basespecs.ElementWrapper;
import ai.dqo.metadata.basespecs.ObjectName;

/**
 * Table spec wrapper.
 */
public interface TableWrapper extends ElementWrapper<TableSpec>, ObjectName<PhysicalTableName> {
    /**
     * Gets the physical table name that was decoded from the spec file name.
     * @return Physical table name decoded from the file name.
     */
    PhysicalTableName getPhysicalTableName();

    /**
     * Sets a physical table name that is used to generate a file name.
     * @param physicalTableName Physical table name used to generate a yaml file.
     */
    void setPhysicalTableName(PhysicalTableName physicalTableName);
}
