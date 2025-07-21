/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.userhome;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.similarity.SimilarTableModel;
import com.dqops.metadata.basespecs.Flushable;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapper;
import com.dqops.metadata.policies.column.ColumnQualityPolicyList;
import com.dqops.metadata.policies.table.TableQualityPolicyList;
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.dictionaries.DictionaryList;
import com.dqops.metadata.fileindices.FileIndexList;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapper;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexList;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentNotificationsWrapper;

import java.util.List;

/**
 * User home model. Provides access to the data in the user home. The actual implementation can use a local file system,
 * a virtual file system or a database.
 */
public interface UserHome extends Flushable, HierarchyNode {
    /**
     * Returns the user identity for whom the user home was opened. Also identifies the data domain, which is a folder with a copy of the DQOps user home for a given data domain.
     * @return User identity.
     */
    UserDomainIdentity getUserIdentity();

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
     * Returns a collection of data dictionary CSV files.
     * @return Collection of data dictionaries.
     */
    DictionaryList getDictionaries();

    /**
     * Returns a list of file indexes.
     * @return List of file indexes.
     */
    FileIndexList getFileIndices();

    /**
     * Returns a list of connection similarity indices for each connection.
     * @return List of connection similarity indices.
     */
    ConnectionSimilarityIndexList getConnectionSimilarityIndices();

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
     * Finds the first node on the path from the user home to the <code>leafNode</code> that is of <code>parentType</code> class.
     * @param leafNode Leaf node to follow on the path.
     * @param parentType Expected class type.
     * @return Instance of a class of the target type.
     * @param <T> Target type.
     */
    <T extends HierarchyNode> T findNodeOnPathOfType(HierarchyNode leafNode, Class<T> parentType);

    /**
     * Finds tables that are similar to a given table.
     * @param connectionName Connection name where the table is present.
     * @param referenceTable Reference table to find similar tables.
     * @param maxResults Maximum number of results to return.
     * @param maxDifferencesPercent The maximum difference percent.
     * @return List of tables that are similar.
     */
    List<SimilarTableModel> findTablesSimilarTo(String connectionName,
                                                PhysicalTableName referenceTable,
                                                int maxResults,
                                                double maxDifferencesPercent);

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
     * Returns a collection of named patterns with the default configuration of table-level checks.
     * @return Collection of table-level default checks patterns.
     */
    TableQualityPolicyList getTableQualityPolicies();

    /**
     * Returns a collection of named patterns with the default configuration of column-level checks.
     * @return Collection of column-level default checks patterns.
     */
    ColumnQualityPolicyList getColumnQualityPolicies();

    /**
     * Returns a default notification addresses.
     * @return Collection of default observability checks definitions.
     */
    DefaultIncidentNotificationsWrapper getDefaultIncidentNotifications();

    /**
     * Initiates loading all connections to cache them and let label and other services find relevant data.
     * Loading will continue in the background.
     */
    void warmUpConnections();

    /**
     * Initiates loading all connections to cache them and let label, quality status, data lineage and other services find relevant information.
     * Loading will continue in the background.
     */
    void warmUpTables();
}
