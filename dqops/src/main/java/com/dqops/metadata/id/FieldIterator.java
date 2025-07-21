/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.id;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Field map iterator that iterates over fields defined on an object and retrieves only non-null child hierarchy nodes.
 */
public class FieldIterator implements Iterator<HierarchyNode> {
    private final Iterator<ChildFieldEntry> fieldIterator;
    private final HierarchyNode target;
    private HierarchyNode next;

    /**
     * Creates a field iterator.
     * @param fieldMap Field map to iterate over.
     * @param target Target object to retrieve fields values from.
     */
    public FieldIterator(ChildHierarchyNodeFieldMap fieldMap, HierarchyNode target) {
		this.fieldIterator = fieldMap.getChildEntries().iterator();
        this.target = target;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        while (fieldIterator.hasNext()) {
			this.next = fieldIterator.next().getGetChildFunc().apply(this.target);
            if (this.next != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public HierarchyNode next() {
        return this.next;
    }
}
