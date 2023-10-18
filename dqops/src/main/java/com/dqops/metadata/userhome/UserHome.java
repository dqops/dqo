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
package com.dqops.metadata.userhome;

import com.dqops.metadata.basespecs.Flushable;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapper;
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.fileindices.FileIndexList;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapper;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.settings.defaultchecks.DefaultObservabilityCheckWrapper;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentWebhookNotificationsWrapper;

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
     * Returns a list of custom check definitions.
     * @return Collection of custom check definitions.
     */
    CheckDefinitionList getChecks();

    /**
     * Returns settings.
     * @return Settings.
     */
    SettingsWrapper getSettings();

    /**
     * Returns a collection of shared credentials.
     * @return Collection of shared credentials.
     */
    SharedCredentialList getCredentials();

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

    /**
     * Finds a node identified by the hierarchy id.
     * @param hierarchyId Hierarchy id path to the node.
     * @return Node that was found.
     */
    HierarchyNode findNode(HierarchyId hierarchyId);

    /**
     * Returns a list of dashboards definitions.
     * @return Collection of dashboards definitions.
     */
    DashboardFolderListSpecWrapper getDashboards();

    /**
     * Returns a default schedules definitions.
     * @return Collection of default schedules definitions.
     */
    MonitoringSchedulesWrapper getDefaultSchedules();

    /**
     * Returns the default configuration of Data Observability checks to be applied on new tables and columns. Configuration is stored in the user home folder.
     * @return User's default data observability checks configuration.
     */
    DefaultObservabilityCheckWrapper getDefaultObservabilityChecks();

    /**
     * Returns the non-null default configuration of Data Observability checks to be applied on new tables and columns. Configuration is stored in the user home folder. When specification does not exist, a new empty one is created.
     * @return Collection of default observability checks definitions.
     */
    DefaultObservabilityCheckWrapper getDefaultObservabilityChecks(boolean createIfNull);

    /**
     * Returns a default notification webhooks.
     * @return Collection of default observability checks definitions.
     */
    DefaultIncidentWebhookNotificationsWrapper getDefaultNotificationWebhook();

}
