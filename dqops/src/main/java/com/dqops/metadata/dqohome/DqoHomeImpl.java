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
package com.dqops.metadata.dqohome;

import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionListImpl;
import com.dqops.metadata.definitions.sensors.SensorDefinitionListImpl;
import com.dqops.metadata.id.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Root dqo.io home model for reading and managing the definitions in the application's home (DQO_HOME).
 * Those are built in rule, sensor and dashboard definitions.
 */
public class DqoHomeImpl implements DqoHome, Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<DqoHomeImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(ChildHierarchyNodeFieldMap.empty()) {
        {
			put("sensors", o -> o.sensors);
			put("rules", o -> o.rules);
            put("checks", o -> o.checks);
			put("dashboards", o -> o.dashboards);
        }
    };

    @JsonIgnore
    private HierarchyId hierarchyId = HierarchyId.getRoot();
    private RuleDefinitionListImpl rules;
    private SensorDefinitionListImpl sensors;
    private CheckDefinitionListImpl checks;
    private DashboardFolderListSpecWrapperImpl dashboards;
    @JsonIgnore
    private boolean dirty;
    @JsonIgnore
    private boolean readOnly;
    /**
     * Creates a default dqo home implementation.
     * @param readOnly Open the object in read-only mode.
     */
    public DqoHomeImpl(boolean readOnly) {
		this.setSensors(new SensorDefinitionListImpl(readOnly));
		this.setRules(new RuleDefinitionListImpl(readOnly));
        this.setChecks(new CheckDefinitionListImpl(readOnly));
        this.setDashboards(new DashboardFolderListSpecWrapperImpl(readOnly));
        this.readOnly = readOnly;
    }

    /**
     * Creates a dqo.io home implementation with alternative implementations (file based) of collections.
     * @param sensors Collection of sensor definitions.
     * @param rules Collection of custom rule definitions.
     * @param checks Collection of check definitions.
     * @param dashboards Collection of dashboard definitions.
     * @param readOnly Make the home read-only.
     */
    public DqoHomeImpl(SensorDefinitionListImpl sensors,
                       RuleDefinitionListImpl rules,
                       CheckDefinitionListImpl checks,
                       DashboardFolderListSpecWrapperImpl dashboards,
                       boolean readOnly) {
		this.setSensors(sensors);
		this.setRules(rules);
        this.setChecks(checks);
        this.setDashboards(dashboards);
        if (readOnly) {
            makeReadOnly(true);
        }
    }

    /**
     * Returns a collection of sensor definitions in the user home folder.
     * @return Collection of user's sensor definitions.
     */
    @Override
    public SensorDefinitionListImpl getSensors() {
        return sensors;
    }

    /**
     * Changes the collection of check definitions.
     * @param sensors New collection of check definitions.
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
     * Returns a collection of check definitions.
     * @return Collection of check definitions.
     */
    @Override
    public CheckDefinitionListImpl getChecks() {
        return checks;
    }

    /**
     * Changes the collection of check definitions.
     * @param checks New collection of check definitions.
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
     * Returns a collection of default dashboards in the user home folder.
     * @return Collection of default dashboards.
     */
    public DashboardFolderListSpecWrapperImpl getDashboards() {
        return dashboards;
    }


    /**
     * Changes the collection of default dashboards.
     * @param dashboards New collection of default dashboards.
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
		this.getSensors().flush();
		this.getRules().flush();
        this.getChecks().flush();
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
     * Performs a deep clone of the object.
     *
     * @return Deep clone of the object.
     */
    @Override
    public DqoHomeImpl deepClone() {
        try {
            DqoHomeImpl cloned = (DqoHomeImpl) super.clone();
            if (cloned.sensors != null) {
                cloned.sensors = (SensorDefinitionListImpl) cloned.sensors.deepClone();
            }
            if (cloned.rules != null) {
                cloned.rules = (RuleDefinitionListImpl) cloned.rules.deepClone();
            }
            if (cloned.checks != null) {
                cloned.checks = (CheckDefinitionListImpl) cloned.checks.deepClone();
            }
            if (cloned.dashboards != null) {
                cloned.dashboards = (DashboardFolderListSpecWrapperImpl) cloned.dashboards.deepClone();
            }
            cloned.dirty = false;
            cloned.readOnly = false;

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
}
