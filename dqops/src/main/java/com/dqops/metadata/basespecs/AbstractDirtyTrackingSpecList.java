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
package com.dqops.metadata.basespecs;

import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.utils.serialization.YamlNotRenderWhenDefault;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * List of spec objects with dirty tracing and hierarchy support.
 */
public abstract class AbstractDirtyTrackingSpecList<V extends HierarchyNode>
        extends AbstractList<V> implements HierarchyNode, YamlNotRenderWhenDefault {
    @JsonIgnore
    private ArrayList<V> list = new ArrayList<>();
    @JsonIgnore
    private boolean dirty;
    @JsonIgnore
    private boolean readOnly;
    @JsonIgnore
    private HierarchyId hierarchyId;

    /**
     * Check if the object is dirty (has changes).
     * @return True when the object is dirty and has modifications.
     */
    public boolean isDirty() {
        if (dirty) {
            return true;
        }

        for (V value : this) {
            if (value.isDirty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the dirty flag to true.
     */
    public void setDirty() {
        if (this.readOnly) {
            throw new ReadOnlyObjectModifiedException(this);
        }
		this.dirty = true;
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    public void clearDirty(boolean propagateToChildren) {
		this.dirty = false;
        if (propagateToChildren) {
            for (V value : this) {
                value.clearDirty(true);
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
    public boolean isDefault() {
        return this.list.size() == 0;
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
        if (this.size() > 0) {
            for (int i = 0; i < this.size(); i++) {
                V entry = this.get(i);
                entry.setHierarchyId(new HierarchyId(hierarchyId, i));
            }
        }
    }

    /**
     * Returns a named child. It is a named object in an object map (column map, test map) or a field name.
     *
     * @param childName Child name.
     * @return Child node.
     */
    @Override
    public HierarchyNode getChild(Object childName) {
        return this.get((Integer)childName);
    }

    /**
     * Returns an iterable that iterates over child nodes.
     *
     * @return Iterable to iterate over child nodes.
     */
    @Override
    public Iterable<HierarchyNode> children() {
        return (Iterable<HierarchyNode>) this; // type erasure, it is an iterable (collection) of descendants of HierarchyNode
    }

    /**
     * Clears the child node, setting a null value.
     *
     * @param childName Child name.
     */
    @Override
    public void detachChildNode(Object childName) {
        Integer index = (Integer) childName;
        remove(index);
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @param element
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     * @implSpec This implementation always throws an
     * {@code UnsupportedOperationException}.
     */
    @Override
    public V set(int index, V element) {
		setDirty();
        if (this.hierarchyId != null && element != null) {
            element.setHierarchyId(new HierarchyId(this.hierarchyId, index));
        }
        return this.list.set(index, element);
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @param element
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     * @implSpec This implementation always throws an
     * {@code UnsupportedOperationException}.
     */
    @Override
    public void add(int index, V element) {
		setDirty();
		this.list.add(index, element);
        if (this.hierarchyId != null && element != null) {
            element.setHierarchyId(new HierarchyId(this.hierarchyId, size()));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     * @implSpec This implementation always throws an
     * {@code UnsupportedOperationException}.
     */
    @Override
    public V remove(int index) {
		setDirty();
        V removedElement = this.list.remove(index);
        if (this.hierarchyId != null) {
            for (int i = index; i < size(); i++) {
				this.list.get(i).setHierarchyId(new HierarchyId(this.hierarchyId, i));
            }
        }
        return removedElement;
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public V get(int index) {
        return this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the {@code clear} operation
     *                                       is not supported by this list
     * @implSpec This implementation calls {@code removeRange(0, size())}.
     *
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} unless {@code remove(int
     * index)} or {@code removeRange(int fromIndex, int toIndex)} is
     * overridden.
     */
    @Override
    public void clear() {
		this.setDirty();
        super.clear();
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
                for (V element : this.list) {
                    element.makeReadOnly(true);
                }
            }
        }
    }

    /**
     * Performs a deep clone of the object.
     *
     * @return Deep clone of the object.
     */
    @Override
    public AbstractDirtyTrackingSpecList<V> deepClone() {
        try {
            AbstractDirtyTrackingSpecList<V> cloned = (AbstractDirtyTrackingSpecList<V>) super.clone();
            cloned.list = new ArrayList<>();
            cloned.dirty = false;
            cloned.readOnly = false;

            if (this.list.size() == 0) {
                return cloned;
            }

            for (V childNode : this.list) {
                V clonedChild = (V)childNode.deepClone();
                cloned.list.add(clonedChild);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new UnsupportedOperationException("Cannot clone the object ", ex);
        }
    }
}
