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

/**
 * Iterable that iterates spec files with field maps. Iterates over non-null fields in the iterated node.
 */
public class FieldIterable implements Iterable<HierarchyNode> {
    private final HierarchyNode iteratedNode;
    private final ChildHierarchyNodeFieldMap fieldMap;

    /**
     * Creates an iterable instance that iterates fields in a given hierarchy node.
     * @param iteratedNode Iterated node.
     */
    public FieldIterable(HierarchyNode iteratedNode, ChildHierarchyNodeFieldMap fieldMap) {
        this.iteratedNode = iteratedNode;
        this.fieldMap = fieldMap;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<HierarchyNode> iterator() {
        return new FieldIterator(fieldMap, iteratedNode);
    }
}
