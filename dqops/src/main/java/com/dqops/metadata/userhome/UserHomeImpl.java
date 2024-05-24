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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.credentials.SharedCredentialListImpl;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternListImpl;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternListImpl;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionListImpl;
import com.dqops.metadata.definitions.sensors.SensorDefinitionListImpl;
import com.dqops.metadata.dictionaries.DictionaryListImpl;
import com.dqops.metadata.fileindices.FileIndexList;
import com.dqops.metadata.fileindices.FileIndexListImpl;
import com.dqops.metadata.id.*;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapperImpl;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.settings.SettingsWrapperImpl;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentWebhookNotificationsWrapperImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Root user home model for reading and managing the definitions in the user's home.
 */
public class UserHomeImpl implements UserHome, Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<UserHomeImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(ChildHierarchyNodeFieldMap.empty()) {
        {
			put("connections", o -> o.connections);
			put("sensors", o -> o.sensors);
			put("rules", o -> o.rules);
            put("checks", o -> o.checks);
            put("settings", o -> o.settings);
            put("credentials", o -> o.credentials);
            put("dictionaries", o -> o.dictionaries);
            put("file_indices", o -> o.fileIndices);
            put("dashboards", o -> o.dashboards);
            put("default_schedules", o -> o.defaultSchedules);
            put("table_default_checks_patterns", o -> o.tableDefaultChecksPatterns);
            put("column_default_checks_patterns", o -> o.columnDefaultChecksPatterns);
            put("default_notification_webhooks", o -> o.defaultNotificationWebhooks);
        }
    };

    private UserDomainIdentity userIdentity;
    @JsonIgnore
    private HierarchyId hierarchyId = HierarchyId.getRoot();
    private ConnectionListImpl connections;
    private SensorDefinitionListImpl sensors;
    private RuleDefinitionListImpl rules;
    private CheckDefinitionListImpl checks;
    private SettingsWrapperImpl settings;
    private SharedCredentialListImpl credentials;
    private DictionaryListImpl dictionaries;
    private FileIndexList fileIndices;
    private DashboardFolderListSpecWrapperImpl dashboards;

    /**
     * Configuration of the default schedules that are assigned to new connections to data sources that are imported.
     * The settings that are configured take precedence over configuration from the DQOps command line parameters and environment variables.
     */
    private MonitoringSchedulesWrapperImpl defaultSchedules;

    /**
     * The collection of default checks configurations for tables matching a pattern.
     */
    private TableDefaultChecksPatternListImpl tableDefaultChecksPatterns;

    /**
     * The collection of default checks configurations for columns matching a pattern.
     */
    private ColumnDefaultChecksPatternListImpl columnDefaultChecksPatterns;

    /**
     * The default notification webhooks.
     */
    private DefaultIncidentWebhookNotificationsWrapperImpl defaultNotificationWebhooks;

    @JsonIgnore
    private boolean dirty;

    @JsonIgnore
    private boolean readOnly;

    /**
     * Creates a default user home implementation.
     * @param userIdentity User identity that specifies the calling user and the data domain.
     * @param readOnly The user home is opened in a read-only mode.
     */
    public UserHomeImpl(UserDomainIdentity userIdentity, boolean readOnly) {
        this.userIdentity = userIdentity;
		this.setConnections(new ConnectionListImpl(readOnly));
		this.setSensors(new SensorDefinitionListImpl(readOnly));
		this.setRules(new RuleDefinitionListImpl(readOnly));
        this.setChecks(new CheckDefinitionListImpl(readOnly));
        this.setSettings(new SettingsWrapperImpl(readOnly));
        this.setCredentials(new SharedCredentialListImpl(readOnly));
        this.setDictionaries(new DictionaryListImpl(readOnly));
        this.setFileIndices(new FileIndexListImpl(readOnly));
        this.setDashboards(new DashboardFolderListSpecWrapperImpl(readOnly));
        this.setDefaultSchedules(new MonitoringSchedulesWrapperImpl(readOnly));
        this.setTableDefaultChecksPatterns(new TableDefaultChecksPatternListImpl(readOnly));
        this.setColumnDefaultChecksPatterns(new ColumnDefaultChecksPatternListImpl(readOnly));
        this.setDefaultNotificationWebhooks(new DefaultIncidentWebhookNotificationsWrapperImpl(readOnly));
        this.readOnly = readOnly;
    }

    /**
     * Creates a user home implementation with alternative implementations (file based) of collections.
     *
     * @param userIdentity User identity that specifies the calling user and the data domain.
     * @param connections Collection of connections.
     * @param sensors Collection of sensor definitions.
     * @param rules Collection of custom rule definitions.
     * @param checks Collection of custom check definitions.
     * @param settings User local settings.
     * @param credentials Collection of shared credentials.
     * @param dictionaries Collection of data dictionaries.
     * @param fileIndices File synchronization indexes.
     * @param dashboards Custom dashboards wrapper.
     * @param schedules Default monitoring schedules wrapper.
     * @param tableDefaultChecksPatterns Default table-level checks.
     * @param columnDefaultChecksPatterns Default column-level checks.
     * @param readOnly Make the user home read-only.
     */
    public UserHomeImpl(UserDomainIdentity userIdentity,
                        ConnectionListImpl connections,
                        SensorDefinitionListImpl sensors,
                        RuleDefinitionListImpl rules,
                        CheckDefinitionListImpl checks,
                        SettingsWrapperImpl settings,
                        SharedCredentialListImpl credentials,
                        DictionaryListImpl dictionaries,
                        FileIndexListImpl fileIndices,
                        DashboardFolderListSpecWrapperImpl dashboards,
                        MonitoringSchedulesWrapperImpl schedules,
                        TableDefaultChecksPatternListImpl tableDefaultChecksPatterns,
                        ColumnDefaultChecksPatternListImpl columnDefaultChecksPatterns,
                        DefaultIncidentWebhookNotificationsWrapperImpl notificationWebhooks,
                        boolean readOnly) {
        this.userIdentity = userIdentity;
		this.setConnections(connections);
		this.setSensors(sensors);
		this.setRules(rules);
        this.setChecks(checks);
        this.setSettings(settings);
        this.setCredentials(credentials);
        this.setDictionaries(dictionaries);
        this.setFileIndices(fileIndices);
        this.setDashboards(dashboards);
        this.setDefaultSchedules(schedules);
        this.setTableDefaultChecksPatterns(tableDefaultChecksPatterns);
        this.setColumnDefaultChecksPatterns(columnDefaultChecksPatterns);
        this.setDefaultNotificationWebhooks(notificationWebhooks);
        if (readOnly) {
            makeReadOnly(true);
        }
    }

    /**
     * Returns the user identity for whom the user home was opened. Also identifies the data domain, which is a folder with a copy of the DQOps user home for a given data domain.
     *
     * @return User identity.
     */
    @Override
    public UserDomainIdentity getUserIdentity() {
        return this.userIdentity;
    }

    /**
     * Returns a collection of connections.
     * @return Collection of connections.
     */
    public ConnectionListImpl getConnections() {
        return connections;
    }

    /**
     * Changes the collection of connections.
     * @param connections New collection of connections.
     */
    public void setConnections(ConnectionListImpl connections) {
        this.connections = connections;
        if (connections != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "connections");
            connections.setHierarchyId(childHierarchyId);
            assert FIELDS.get("connections").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns a collection of sensor definitions in the user home folder.
     * @return Collection of user's sensor definitions.
     */
    public SensorDefinitionListImpl getSensors() {
        return sensors;
    }

    /**
     * Changes the collection of sensor definitions.
     * @param sensors New collection of sensor definitions.
     */
    public void setSensors(SensorDefinitionListImpl sensors) {
        this.sensors = sensors;
        if (sensors != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "sensors");
            sensors.setHierarchyId(childHierarchyId);
            assert FIELDS.get("sensors").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns a collection of custom rules in the user home folder.
     * @return Collection of user's custom rules.
     */
    @Override
    public RuleDefinitionList getRules() {
        return rules;
    }

    /**
     * Changes the collection of custom rules.
     * @param rules New collection of custom rules.
     */
    public void setRules(RuleDefinitionListImpl rules) {
        this.rules = rules;
        if (rules != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "rules");
            rules.setHierarchyId(childHierarchyId);
            assert FIELDS.get("rules").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns a collection of custom check definitions.
     * @return Collection of custom check definitions.
     */
    @Override
    public CheckDefinitionListImpl getChecks() {
        return checks;
    }

    /**
     * Changes the collection of custom check definitions.
     * @param checks New collection of custom check definitions.
     */
    public void setChecks(CheckDefinitionListImpl checks) {
        this.checks = checks;
        if (checks != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "checks");
            checks.setHierarchyId(childHierarchyId);
            assert FIELDS.get("checks").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns settings.
     * @return Settings.
     */
    @Override
    public SettingsWrapper getSettings() {
        return settings;
    }

    /**
     * Set the settings wrapper.
     * @param settings Settings wrapper.
     */
    public void setSettings(SettingsWrapperImpl settings) {
        this.settings = settings;
        if (settings != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "settings");
            settings.setHierarchyId(childHierarchyId);
            assert FIELDS.get("settings").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns the collection of local shared credentials.
     * @return Collection of local shared credentials.
     */
    @Override
    public SharedCredentialListImpl getCredentials() {
        return credentials;
    }

    /**
     * Sets a reference to the collection of local credentials.
     * @param credentials Collection of local credentials.
     */
    public void setCredentials(SharedCredentialListImpl credentials) {
        this.credentials = credentials;
        if (credentials != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "credentials");
            credentials.setHierarchyId(childHierarchyId);
            assert FIELDS.get("credentials").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns a collection of data dictionary CSV files.
     * @return Collection of data dictionaries.
     */
    @Override
    public DictionaryListImpl getDictionaries() {
        return dictionaries;
    }

    /**
     * Sets a reference to the collection of data dictionaries.
     * @param dictionaries Collection of data dictionaries.
     */
    public void setDictionaries(DictionaryListImpl dictionaries) {
        this.dictionaries = dictionaries;
        if (dictionaries != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "dictionaries");
            dictionaries.setHierarchyId(childHierarchyId);
            assert FIELDS.get("dictionaries").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns a collection of file indices in the user home folder.
     * @return Collection of file indices.
     */
    @Override
    public FileIndexList getFileIndices() {
        return this.fileIndices;
    }

    /**
     * Changes the collection of file indices.
     * @param fileIndices New collection of file indices.
     */
    public void setFileIndices(FileIndexListImpl fileIndices) {
        this.fileIndices = fileIndices;
        if (fileIndices != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "file_indices");
            fileIndices.setHierarchyId(childHierarchyId);
            assert FIELDS.get("file_indices").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns a collection of custom dashboards in the user home folder.
     * @return Collection of user's custom dashboards.
     */
    public DashboardFolderListSpecWrapperImpl getDashboards() {
        return dashboards;
    }


    /**
     * Changes the collection of custom dashboards.
     * @param dashboards New collection of custom dashboards.
     */
    public void setDashboards(DashboardFolderListSpecWrapperImpl dashboards) {
        this.dashboards = dashboards;
        if (dashboards != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "dashboards");
            dashboards.setHierarchyId(childHierarchyId);
            assert FIELDS.get("dashboards").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns the default configuration of schedules in the user home folder.
     * @return Collection of user's the default configuration of schedules.
     */
    public MonitoringSchedulesWrapperImpl getDefaultSchedules() {
        return defaultSchedules;
    }

    /**
     * Changes the collection of custom monitoring schedules.
     * @param defaultSchedules New collection of custom monitoring schedules.
     */
    public void setDefaultSchedules(MonitoringSchedulesWrapperImpl defaultSchedules) {
        this.defaultSchedules = defaultSchedules;
        if (defaultSchedules != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "default_schedules");
            defaultSchedules.setHierarchyId(childHierarchyId);
            assert FIELDS.get("default_schedules").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns the collection of default table checks.
     * @return A collection of default table checks.
     */
    @Override
    public TableDefaultChecksPatternListImpl getTableDefaultChecksPatterns() {
        return tableDefaultChecksPatterns;
    }

    /**
     * Sets a container of default table-level checks.
     * @param tableDefaultChecksPatterns Default table-level checks.
     */
    public void setTableDefaultChecksPatterns(TableDefaultChecksPatternListImpl tableDefaultChecksPatterns) {
        this.tableDefaultChecksPatterns = tableDefaultChecksPatterns;
        if (tableDefaultChecksPatterns != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "table_default_checks_patterns");
            tableDefaultChecksPatterns.setHierarchyId(childHierarchyId);
            assert FIELDS.get("table_default_checks_patterns").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns the collection of default column checks.
     * @return A collection of default column checks.
     */
    @Override
    public ColumnDefaultChecksPatternListImpl getColumnDefaultChecksPatterns() {
        return columnDefaultChecksPatterns;
    }

    /**
     * Sets a collection of default column checks.
     * @param columnDefaultChecksPatterns Default column checks.
     */
    public void setColumnDefaultChecksPatterns(ColumnDefaultChecksPatternListImpl columnDefaultChecksPatterns) {
        this.columnDefaultChecksPatterns = columnDefaultChecksPatterns;
        if (columnDefaultChecksPatterns != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "column_default_checks_patterns");
            columnDefaultChecksPatterns.setHierarchyId(childHierarchyId);
            assert FIELDS.get("column_default_checks_patterns").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Returns the default notification webhooks. Configuration is stored in the user home folder.
     * @return User's default notification webhooks.
     */
    public DefaultIncidentWebhookNotificationsWrapperImpl getDefaultNotificationWebhook() {
        return defaultNotificationWebhooks;
    }

    /**
     * Sets the default configuration of notification webhooks.
     * @param defaultNotificationWebhooks The default notification webhooks.
     */
    public void setDefaultNotificationWebhooks(DefaultIncidentWebhookNotificationsWrapperImpl defaultNotificationWebhooks) {
        this.defaultNotificationWebhooks = defaultNotificationWebhooks;
        if (this.defaultNotificationWebhooks != null) {
            HierarchyId childHierarchyId = new HierarchyId(this.hierarchyId, "default_notification_webhooks");
            this.defaultNotificationWebhooks.setHierarchyId(childHierarchyId);
            assert FIELDS.get("default_notification_webhooks").apply(this).getHierarchyId().equals(childHierarchyId);
        }
    }

    /**
     * Flushes an object to a persistent store.
     */
    @Override
    public void flush() {
        // synchronize changes back to the virtual file system
		this.getConnections().flush();
		this.getSensors().flush();
		this.getRules().flush();
        this.getChecks().flush();
        this.getSettings().flush();
        this.getCredentials().flush();
        this.getDictionaries().flush();
        this.getFileIndices().flush();
        this.getDashboards().flush();
        this.getDefaultSchedules().flush();
        this.getTableDefaultChecksPatterns().flush();
        this.getColumnDefaultChecksPatterns().flush();
        this.getDefaultNotificationWebhook().flush();

        this.clearDirty(false); // children that were saved should be already not dirty, the next assert will detect forgotten instances
        assert !this.isDirty() : "Dirty node: " + this.findDirty(this).getHierarchyId().toString();
    }

    /**
     * Scans the node tree in-depth to find the first dirty object.
     * Returns that dirty node or null, when no node is dirty.
     * @param node Start node to start the scan.
     * @return Dirty node or null, when the node is not dirty.
     */
    private static HierarchyNode findDirty(HierarchyNode node) {
        ArrayList<HierarchyNode> children = new ArrayList<>();
        for (HierarchyNode childNode : node.children()) {
            children.add(childNode);
        }

        for (HierarchyNode childNode : children) {
            HierarchyNode dirtyChild = findDirty(childNode);
            if (dirtyChild != null) {
                return dirtyChild;
            }
        }

        if (node.isDirty()) {
            boolean revalidatedIsDirty = node.isDirty();  // this line is here not because it is needed, but to make it easier to put a breakpoint
            return node;
        }

        return null;
    }

    /**
     * Returns the hierarchy ID of this node.
     *
     * @return Hierarchy ID of this node.
     */
    @Override
    public HierarchyId getHierarchyId() {
        return this.hierarchyId;
    }

    /**
     * Replaces the hierarchy ID. A new hierarchy ID is also propagated to all child nodes.
     *
     * @param hierarchyId New hierarchy ID.
     */
    @Override
    public void setHierarchyId(HierarchyId hierarchyId) {
        assert hierarchyId != null;
        this.hierarchyId = hierarchyId;
		FIELDS.propagateHierarchyIdToChildren(this, hierarchyId);
    }

    /**
     * Returns a named child. It is a named object in an object map (column map, test map) or a field name.
     *
     * @param childName Child name.
     * @return Child node.
     */
    @Override
    public HierarchyNode getChild(Object childName) {
        return FIELDS.getFieldGetter(childName.toString()).apply(this);
    }

    /**
     * Returns an iterable that iterates over child nodes.
     *
     * @return Iterable to iterate over child nodes.
     */
    @Override
    public Iterable<HierarchyNode> children() {
        return new FieldIterable(this, FIELDS);
    }

    /**
     * Sets the dirty flag to true.
     */
    @Override
    public void setDirty() {
		this.dirty = true;
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        if (this.dirty) {
            return true;
        }

        for (HierarchyNode child : this.children()) {
            if (child.isDirty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
		this.dirty = false;

        if (propagateToChildren) {
            for (HierarchyNode child : this.children()) {
                child.clearDirty(true);
            }
        }
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Finds a connection wrapper on the given hierarchy path.
     *
     * @param nestedHierarchyId Hierarchy id path to a check or any other element inside a connection.
     * @return Connection wrapper on the path to the node.
     */
    @Override
    public ConnectionWrapper findConnectionFor(HierarchyId nestedHierarchyId) {
        if (nestedHierarchyId.size() < 2) {
            return null;
        }

        ConnectionList connectionList = this.getExpectedChild(nestedHierarchyId.get(0), ConnectionList.class);
        if (connectionList == null) {
            return null;
        }

        ConnectionWrapper connectionWrapper = connectionList.getExpectedChild(nestedHierarchyId.get(1), ConnectionWrapper.class);
        return connectionWrapper;
    }

    /**
     * Finds a table wrapper on the given hierarchy path.
     *
     * @param nestedHierarchyId Hierarchy id path to a check or any other element inside a connection + table.
     * @return Table wrapper on the path to the node.
     */
    @Override
    public TableWrapper findTableFor(HierarchyId nestedHierarchyId) {
        ConnectionWrapper connectionWrapper = findConnectionFor(nestedHierarchyId);
        if (connectionWrapper == null) {
            return null;
        }

        TableList tableList = connectionWrapper.getExpectedChild(nestedHierarchyId.get(2), TableList.class);
        if (tableList == null) {
            return null;
        }

        TableWrapper tableWrapper = tableList.getExpectedChild(nestedHierarchyId.get(3), TableWrapper.class);
        return tableWrapper;
    }

    /**
     * Finds a node identified by the hierarchy id.
     *
     * @param hierarchyId Hierarchy id path to the node.
     * @return Node that was found.
     */
    @Override
    public HierarchyNode findNode(HierarchyId hierarchyId) {
        HierarchyNode currentNode = this;
        for (int i = 0; i < hierarchyId.size() && currentNode != null; i++) {
            Object nodeId = hierarchyId.get(i);
            currentNode = currentNode.getChild(nodeId);
        }

        return currentNode;
    }

    /**
     * Finds the first node on the path from the user home to the <code>leafNode</code> that is of <code>parentType</code> class.
     * @param leafNode Leaf node to follow on the path.
     * @param parentType Expected class type.
     * @return Instance of a class of the target type.
     * @param <T> Target type.
     */
    @Override
    public <T extends HierarchyNode> T findNodeOnPathOfType(HierarchyNode leafNode, Class<T> parentType) {
        List<HierarchyNode> nodesOnPath = List.of(leafNode.getHierarchyId().getNodesOnPath(this));
        Optional<T> optionalTargetNode = Lists.reverse(nodesOnPath)
                .stream()
                .filter(n -> parentType.isAssignableFrom(n.getClass()))
                .map(n -> (T)n)
                .findFirst();

        return optionalTargetNode.orElse(null);
    }

    /**
     * Finds a column wrapper on the given hierarchy path.
     *
     * @param nestedHierarchyId Hierarchy id path to a check or any other element inside a connection / table / column.
     * @return Column specification on the path to the node.
     * May return null if the hierarchy id is not inside any column definition
     * (it is for example a hierarchy id of a check defined at a whole table level, not a column level).
     */
    @Override
    public ColumnSpec findColumnFor(HierarchyId nestedHierarchyId) {
        TableWrapper tableWrapper = findTableFor(nestedHierarchyId);
        if (tableWrapper == null) {
            return null;
        }

        TableSpec tableSpec = tableWrapper.getExpectedChild(nestedHierarchyId.get(4), TableSpec.class);
        if (tableSpec == null) {
            return null;
        }

        ColumnSpecMap columnSpecMap = tableSpec.getExpectedChild(nestedHierarchyId.get(5), ColumnSpecMap.class);
        if (columnSpecMap == null) {
            return null;
        }

        ColumnSpec columnSpec = columnSpecMap.getExpectedChild(nestedHierarchyId.get(6), ColumnSpec.class);
        return columnSpec;
    }

    /**
     * Performs a deep clone of the object.
     *
     * @return Deep clone of the object.
     */
    @Override
    public UserHomeImpl deepClone() {
        try {
            UserHomeImpl cloned = (UserHomeImpl) super.clone();
            if (cloned.sensors != null) {
                cloned.sensors = (SensorDefinitionListImpl) cloned.sensors.deepClone();
            }
            if (cloned.rules != null) {
                cloned.rules = (RuleDefinitionListImpl) cloned.rules.deepClone();
            }
            if (cloned.checks != null) {
                cloned.checks = (CheckDefinitionListImpl) cloned.checks.deepClone();
            }
            if (cloned.connections != null) {
                cloned.connections = (ConnectionListImpl) cloned.connections.deepClone();
            }
            if (cloned.settings != null) {
                cloned.settings = (SettingsWrapperImpl) cloned.settings.deepClone();
            }
            if (cloned.credentials != null) {
                cloned.credentials = (SharedCredentialListImpl) cloned.credentials.deepClone();
            }
            if (cloned.dictionaries != null) {
                cloned.dictionaries = (DictionaryListImpl) cloned.dictionaries.deepClone();
            }
            if (cloned.dashboards != null) {
                cloned.dashboards = (DashboardFolderListSpecWrapperImpl) cloned.dashboards.deepClone();
            }
            if (cloned.defaultSchedules != null) {
                cloned.defaultSchedules = (MonitoringSchedulesWrapperImpl) cloned.defaultSchedules.deepClone();
            }
            if (cloned.tableDefaultChecksPatterns != null) {
                cloned.tableDefaultChecksPatterns = (TableDefaultChecksPatternListImpl) cloned.tableDefaultChecksPatterns.deepClone();
            }
            if (cloned.columnDefaultChecksPatterns != null) {
                cloned.columnDefaultChecksPatterns = (ColumnDefaultChecksPatternListImpl) cloned.columnDefaultChecksPatterns.deepClone();
            }
            if (cloned.defaultNotificationWebhooks != null) {
                cloned.defaultNotificationWebhooks = (DefaultIncidentWebhookNotificationsWrapperImpl) cloned
                        .defaultNotificationWebhooks.deepClone();
            }
            // NOTE: the file index is not cloned... it has a different lifecycle

            cloned.dirty = false;

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new UnsupportedOperationException("Cannot clone object", ex);
        }
    }

    /**
     * Check if the object is frozen (read only). A read-only object cannot be modified.
     *
     * @return True when the object is read-only and trying to apply a change will return an error.
     */
    @Override
    @JsonIgnore
    public boolean isReadOnly() {
        return this.readOnly;
    }

    /**
     * Sets the read-only flag on the current object, and optionally on child objects.
     *
     * @param propagateToChildren When true, makes also the child objects as read-only.
     */
    @Override
    @JsonIgnore
    public void makeReadOnly(boolean propagateToChildren) {
        if (!this.readOnly) {
            this.readOnly = true;
            if (propagateToChildren) {
                for (HierarchyNode element : this.children()) {
                    element.makeReadOnly(true);
                }
            }
        }
    }

    /**
     * Clears the child node, setting a null value.
     *
     * @param childName Child name.
     */
    @Override
    public void detachChildNode(Object childName) {
        // do nothing, makes no sense
    }
}
