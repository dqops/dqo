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
package ai.dqo.metadata.userhome;

import ai.dqo.metadata.basespecs.Flushable;
import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionList;
import ai.dqo.metadata.fileindices.FileIndexList;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.settings.SettingsWrapper;
import ai.dqo.metadata.sources.*;

/**
 * User home model. Provides access to the data in the user home. The actual implementation can use a local file system,
 * a virtual file system or a database.
 */
public interface UserHome extends Flushable, HierarchyNode {
    /**
     * Returns a list of connections.
     * @return Collection of connections.
     */
    ConnectionList getConnections();

    /**
     * Returns a list of sensor definitions.
     * @return Collection of sensor definitions.
     */
    SensorDefinitionList getSensors();

    /**
     * Returns a list of custom rules.
     * @return Collection of custom rules.
     */
    RuleDefinitionList getRules();

    /**
     * Returns settings.
     * @return Settings.
     */
    SettingsWrapper getSettings();

    /**
     * Returns a list of file indexes.
     * @return List of file indexes.
     */
    FileIndexList getFileIndices();

    /**
     * Finds a connection wrapper on the given hierarchy path.
     * @param nestedHierarchyId Hierarchy id path to a check or any other element inside a connection.
     * @return Connection wrapper on the path to the node.
     */
    ConnectionWrapper findConnectionFor(HierarchyId nestedHierarchyId);

    /**
     * Finds a table wrapper on the given hierarchy path.
     * @param nestedHierarchyId Hierarchy id path to a check or any other element inside a connection + table.
     * @return Table wrapper on the path to the node.
     */
    TableWrapper findTableFor(HierarchyId nestedHierarchyId);

    /**
     * Finds a column wrapper on the given hierarchy path.
     * @param nestedHierarchyId Hierarchy id path to a check or any other element inside a connection / table / column.
     * @return Column specification on the path to the node.
     * May return null if the hierarchy id is not inside any column definition
     * (it is for example a hierarchy id of a check defined at a whole table level, not a column level).
     */
    ColumnSpec findColumnFor(HierarchyId nestedHierarchyId);
}
