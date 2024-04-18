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
