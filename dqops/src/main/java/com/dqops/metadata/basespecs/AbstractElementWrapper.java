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

import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Wrapper class that holds a model instance. Delays the loading until the actual model is retrieved. Also tracks
 * the change status (added, removed, modified).
 * @param <K> Model key type used to find the model by a key.
 * @param <V> Model type.
 */
public abstract class AbstractElementWrapper<K, V extends DirtyStatus & HierarchyNode> extends AbstractSpec
        implements ObjectName<K>, Flushable, InstanceStatusTracking {

    /**
     * Field map collection.
     */
    public static final ChildHierarchyNodeFieldMapImpl<AbstractElementWrapper<?,?>> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("spec", o -> o.getSpec());  // getSpec() to force lazy load, otherwise not loaded elements will not be returned
        }
    };

    private V spec;
    @JsonIgnore
    private InstanceStatus status = InstanceStatus.UNCHANGED;

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public abstract K getObjectName();

    /**
     * Returns a model instance that is stored in the model wrapper. Derived classes should override this method
     * and load the model from a persistent storage.
     * @return Model object.
     */
    public V getSpec() {
        return spec;
    }

    /**
     * Sets a model object. The first call (when the model was null) simply stores the model.
     * The next call will change the status to {@link InstanceStatus#MODIFIED}.
     * @param spec New model.
     */
    public void setSpec(V spec) {
        if (this.spec != null && this.spec != spec) {
			setDirty();
        }
        if (this.spec == null && this.status == InstanceStatus.UNCHANGED && spec != null) {
            spec.clearDirty(false);
        }
        if (this.spec != null && this.status != InstanceStatus.ADDED) {
			this.status = InstanceStatus.MODIFIED;  // mark as modified
        }
        this.spec = spec; // TODO: consider storing a replaced model in another field, maybe we will need to perform comparison to detect changes
        if (spec != null) {
			this.propagateHierarchyIdToField(spec, "spec");
        }
    }

    /**
     * Returns the status of the node.
     * @return Object status (added, modified, deleted, etc).
     */
    public InstanceStatus getStatus() {
        return status;
    }

    /**
     * Changes the status of the model.
     * @param status New status.
     */
    public void setStatus(InstanceStatus status) {
        this.status = status;
    }

    /**
     * Sets a modified instance status if the object is unmodified and the argument modifiedIf is true.
     * @param modifiedIf When true, marks the entity as modified and requiring flush.
     */
    public void setModifiedIf(boolean modifiedIf) {
        if (!modifiedIf) {
            return;
        }

        if (this.status == InstanceStatus.UNCHANGED) {
			this.status = InstanceStatus.MODIFIED;
			this.setDirty();
        }
    }

    /**
     * Marks the object for deletion. The status changes to {@link InstanceStatus#TO_BE_DELETED}.
     */
    public void markForDeletion() {
        if (this.status == InstanceStatus.ADDED) {
			this.status = InstanceStatus.DELETED; // maybe it is not right, but the file is missing anyway
            return;
        }

        if (this.status == InstanceStatus.DELETED) {
            return;
        }

		this.status = InstanceStatus.TO_BE_DELETED;
		this.setDirty();
    }

    /**
     * Flushes changes to the persistent storage. Derived classes (that are based on a real persistence store) should override
     * this method and perform a store specific serialization.
     */
    public void flush() {
        if (this.spec != null && this.spec.isDirty() && this.status == InstanceStatus.UNCHANGED) {
			this.setStatus(InstanceStatus.MODIFIED);
			this.clearDirty(true);
        }

        switch (this.status) {
            case ADDED:
            case MODIFIED:
				this.status = InstanceStatus.UNCHANGED;
                break;
            case TO_BE_DELETED:
				this.status = InstanceStatus.DELETED;
                break;
        }

		this.clearDirty(false); // deleted...
    }

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public AbstractSpec deepClone() {
        try {
            AbstractElementWrapper<K,V> cloned = (AbstractElementWrapper<K,V>)super.clone();
            if (cloned.status == InstanceStatus.MODIFIED) {
                cloned.status = InstanceStatus.UNCHANGED;
            }

            if (this.spec != null) {
                V specDeepClone = (V)this.spec.deepClone();
                cloned.setSpec(specDeepClone);
            }

            cloned.clearDirty(false);
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new UnsupportedOperationException("Cannot clone the object ", ex);
        }
    }

    /**
     * Compares this object to another object. Avoids triggering the load of specification.
     * @param o Object to compare to.
     * @return True - objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        AbstractElementWrapper<K, V>  other = (AbstractElementWrapper<K, V>)o;
        return Objects.equals(this.spec, other.spec);
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        if (this.spec == null && this.status == InstanceStatus.UNCHANGED) {
            return this.isDirty(false);
        }

        return super.isDirty();
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     *
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        if (this.spec == null) {
            super.clearDirty(false);
            return;
        }

        super.clearDirty(propagateToChildren);
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        if (this.spec == null && this.status == InstanceStatus.UNCHANGED) {
            return true; // but it does not matter, we would never call isDefault to test if a wrapper is default, because wrappers are wrappers... not specification roots that are saved
        }

        return super.isDefault();
    }

    /**
     * Converts the object to a string - returns the full object hierarchical ID.
     * @return Object id.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + (this.getHierarchyId() != null ? this.getHierarchyId().toString() : "(detached)");
    }
}
