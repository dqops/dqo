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

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for all specs in the tree. Provides basic dirty checking.
 */
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDirtyTrackingSpec implements DirtyStatus, ReadOnlyStatus {
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private boolean dirty;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private boolean readOnly;

    /**
     * Check if the object is dirty (has changes).
     * @return True when the object is dirty and has modifications.
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets the dirty flag to true.
     */
    public void setDirty() {
		this.dirty = true;
    }

    /**
     * Sets the dirty flag to true if the parameter is true. Otherwise preserves the current dirty status.
     * @param hasChanged When true, sets the dirty flag to true.
     */
    @JsonIgnore
    public void setDirtyIf(boolean hasChanged) {
        if (this.readOnly) {
            throw new ReadOnlyObjectModifiedException(this);
        }

        if (hasChanged) {
			this.dirty = true;
        }
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    public void clearDirty(boolean propagateToChildren) {
		this.dirty = false;
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
        this.readOnly = true;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        BaseDirtyTrackingSpec cloned = (BaseDirtyTrackingSpec) super.clone();
        cloned.readOnly = false;
        return cloned;
    }
}
