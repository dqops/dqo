/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
