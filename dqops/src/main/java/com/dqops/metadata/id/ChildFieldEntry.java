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

/**
 * Hierarchy child node name and hierarchy node instance, returned from a child iterator on a HierarchyNode.
 */
public class ChildFieldEntry {
    private final String childName;
    private final GetHierarchyChildNodeFunc<HierarchyNode> getChildFunc;

    /**
     * Creates a child field node entry.
     * @param childName Child name. It is a field name or an entry name in an object map.
     * @param getChildFunc Lambda for extracting the child node.
     */
    public ChildFieldEntry(String childName, GetHierarchyChildNodeFunc<HierarchyNode> getChildFunc) {
        this.childName = childName;
        this.getChildFunc = getChildFunc;
    }

    /**
     * Returns the child node name (field name or a key name in an object map).
     * @return Child name.
     */
    public String getChildName() {
        return childName;
    }

    /**
     * Returns the child hierarchy node extraction lambda.
     * @return Lambda for extracting the child node.
     */
    public GetHierarchyChildNodeFunc<HierarchyNode> getGetChildFunc() {
        return getChildFunc;
    }
}
