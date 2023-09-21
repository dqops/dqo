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

import com.dqops.metadata.credentials.SharedCredentialListImpl;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionListImpl;
import com.dqops.metadata.definitions.sensors.SensorDefinitionListImpl;
import com.dqops.metadata.fileindices.FileIndexList;
import com.dqops.metadata.fileindices.FileIndexListImpl;
import com.dqops.metadata.id.*;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.settings.SettingsWrapperImpl;
import com.dqops.metadata.sources.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
            put("file_indices", o -> o.fileIndices);
            put("dashboards", o -> o.dashboards);
        }
    };

    @JsonIgnore
    private HierarchyId hierarchyId = HierarchyId.getRoot();
    private ConnectionListImpl connections;
    private SensorDefinitionListImpl sensors;
    private RuleDefinitionListImpl rules;
    private CheckDefinitionListImpl checks;
    private SettingsWrapperImpl settings;
    private SharedCredentialListImpl credentials;
    private FileIndexList fileIndices;
    private DashboardFolderListSpecWrapperImpl dashboards;
    @JsonIgnore
    private boolean dirty;

    /**
     * Creates a default user home implementation.
     */
    public UserHomeImpl() {
		this.setConnections(new ConnectionListImpl());
		this.setSensors(new SensorDefinitionListImpl());
		this.setRules(new RuleDefinitionListImpl());
        this.setChecks(new CheckDefinitionListImpl());
        this.setSettings(new SettingsWrapperImpl());
        this.setCredentials(new SharedCredentialListImpl());
        this.setFileIndices(new FileIndexListImpl());
        this.setDashboards(new DashboardFolderListSpecWrapperImpl());
    }

    /**
     * Creates a user home implementation with alternative implementations (file based) of collections.
     * @param connections Collection of connections.
     * @param sensors Collection of sensor definitions.
     * @param rules Collection of custom rule definitions.
     * @param checks Collection of custom check definitions.
     * @param settings User local settings.
     * @param credentials Collection of shared credentials.
     * @param fileIndices File synchronization indexes.
     * @param dashboards Custom dashboards wrapper.
     */
    public UserHomeImpl(ConnectionListImpl connections,
                        SensorDefinitionListImpl sensors,
                        RuleDefinitionListImpl rules,
                        CheckDefinitionListImpl checks,
                        SettingsWrapperImpl settings,
                        SharedCredentialListImpl credentials,
                        FileIndexListImpl fileIndices,
                        DashboardFolderListSpecWrapperImpl dashboards) {
		this.setConnections(connections);
		this.setSensors(sensors);
		this.setRules(rules);
        this.setChecks(checks);
        this.setSettings(settings);
        this.setCredentials(credentials);
        this.setFileIndices(fileIndices);
        this.setDashboards(dashboards);
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
        this.getFileIndices().flush();
        this.getDashboards().flush();

        this.clearDirty(false); // children that were saved should be already not dirty, the next assert will detect forgotten instances
        assert !this.isDirty();
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

        for(HierarchyNode child : this.children()) {
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
            if (cloned.dashboards != null) {
                cloned.dashboards = (DashboardFolderListSpecWrapperImpl) cloned.dashboards.deepClone();
            }
            // NOTE: the file index is not cloned... it has a different lifecycle

            cloned.dirty = false;

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new UnsupportedOperationException("Cannot clone object", ex);
        }
    }
}
