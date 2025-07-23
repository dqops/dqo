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

import com.dqops.metadata.id.HierarchyNode;
import com.dqops.utils.exceptions.DqoRuntimeException;

/**
 * An exception thrown when a read-only specification object is modified.
 */
public class ReadOnlyObjectModifiedException extends DqoRuntimeException {
    private HierarchyNode node;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ReadOnlyObjectModifiedException(HierarchyNode node) {
        super("Read-only object modified: " + (node.getHierarchyId() != null ? node.getHierarchyId().toString() : ""));
        this.node = node;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ReadOnlyObjectModifiedException(Object obj) {
        super("Read-only object modified: " + obj);
        HierarchyNode node = obj instanceof HierarchyNode ? (HierarchyNode)obj : null;
        this.node = node;
    }

    /**
     * Returns the metadata node that was modified, but it was read-only.
     * @return Metadata node.
     */
    public HierarchyNode getNode() {
        return node;
    }
}
