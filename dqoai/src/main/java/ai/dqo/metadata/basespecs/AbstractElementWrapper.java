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

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
        AbstractElementWrapper<K,V> cloned = (AbstractElementWrapper<K,V>)super.deepClone();
        if (cloned.status == InstanceStatus.MODIFIED) {
            cloned.status = InstanceStatus.UNCHANGED;
        }
        return cloned;
    }
}
