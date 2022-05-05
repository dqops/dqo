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
package ai.dqo.metadata.basespecs;

import ai.dqo.metadata.id.*;
import ai.dqo.utils.serialization.YamlNotRenderWhenDefault;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for all spec classes in the tree. Provides basic dirty checking.
 */
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSpec extends BaseDirtyTrackingSpec implements HierarchyNode, YamlNotRenderWhenDefault {
    /**
     * Default empty field map.
     */
    public static final ChildHierarchyNodeFieldMapImpl<AbstractSpec> FIELDS = (ChildHierarchyNodeFieldMapImpl<AbstractSpec>) ChildHierarchyNodeFieldMap.empty();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HierarchyId hierarchyId;

    /**
     * Returns the hierarchy ID of this node.
     *
     * @return Hierarchy ID of this node.
     */
    @Override
    public HierarchyId getHierarchyId() {
        return hierarchyId;
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
		propagateHierarchyIdToFields(hierarchyId);
    }

    /**
     * Assigns the new hierarchy ID on child nodes.
     * @param hierarchyId New hierarchy id of the current node that should be propagated to the field getter map.
     */
    protected void propagateHierarchyIdToFields(HierarchyId hierarchyId) {
        ChildHierarchyNodeFieldMap childFieldMap = this.getChildMap();
        childFieldMap.propagateHierarchyIdToChildren(this, hierarchyId);
    }

    /**
     * Returns the child map on the spec class with all fields.
     * @return Return the field map.
     */
    protected abstract ChildHierarchyNodeFieldMap getChildMap();

    /**
     * Returns a named child. It is a named object in an object map (column map, test map) or a field name.
     *
     * @param childName Child name.
     * @return Child node.
     */
    @Override
    public HierarchyNode getChild(Object childName) {
        ChildHierarchyNodeFieldMap childFieldMap = this.getChildMap();
        assert (childName.toString() != null) : "child name is null";
        assert (childFieldMap.getFieldGetter(childName.toString()) != null) : "child name missing, verify that the field name in the field name is correct";
        return childFieldMap.getFieldGetter(childName.toString()).apply(this);
    }

    /**
     * Propagates a hierarchy ID to a child node, creating a child hierarchy ID that is the hierarchy ID of this node with an extra element, the field name.
     * @param childNode Child node.
     * @param fieldName Field name.
     */
    protected void propagateHierarchyIdToField(HierarchyNode childNode, String fieldName) {
        if (childNode == null || this.hierarchyId == null) {
            return;
        }

        HierarchyId childHierarchyId = new HierarchyId(this.getHierarchyId(), fieldName);
        childNode.setHierarchyId(childHierarchyId);

        assert getChild(fieldName) != null && getChild(fieldName).getHierarchyId().equals(childHierarchyId);
    }

    /**
     * Returns an iterable that iterates over child nodes.
     *
     * @return Iterable to iterate over child nodes.
     */
    @Override
    public Iterable<HierarchyNode> children() {
        return new FieldIterable(this, getChildMap());
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    @JsonIgnore
    public boolean isDirty() {
        if (super.isDirty()) {
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
        super.clearDirty(propagateToChildren);

        if (propagateToChildren) {
            for (HierarchyNode child : this.children()) {
                child.clearDirty(true);
            }
        }
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    @JsonIgnore
    public boolean isDefault() {
        for (HierarchyNode child : this.children()) {
            if (child == null) {
                continue;
            }
            if (child instanceof YamlNotRenderWhenDefault) {
                YamlNotRenderWhenDefault notRenderWhenDefaultChild = (YamlNotRenderWhenDefault) child;
				boolean childIsDefault = notRenderWhenDefaultChild.isDefault();
                if (!childIsDefault) {
                    return false;
                }
            }
            return false; // non default child found
        }

        return true;
    }
}
