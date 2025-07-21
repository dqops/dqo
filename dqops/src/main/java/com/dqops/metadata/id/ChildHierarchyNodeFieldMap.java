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

import com.dqops.utils.reflection.ClassInfo;

/**
 * Interface implemented by a property dictionary map that is not generic and provides uniform access (type independent) to all property maps.
 */
public interface ChildHierarchyNodeFieldMap {
    /**
     * Creates an empty field map. This method should be used only in base spec classes. All other classes should
     * take the list of hierarchy nodes from its superclass.
     * @return Empty field map.
     */
    static ChildHierarchyNodeFieldMap empty() {
        return ChildHierarchyNodeFieldMapImpl.empty();
    }

    /**
     * Returns the reflection class info - a reflected information about the class, its fields.
     * @return Reflection class info.
     */
    ClassInfo getReflectionClassInfo();

    /**
     * Returns a field getter delegate to read values from a field.
     * @param fieldName Field name
     * @return Field getter delegate (lambda expression) or null when the property was not mapped in the property map.
     */
    GetHierarchyChildNodeFunc<HierarchyNode> getFieldGetter(String fieldName);

    /**
     * Assigns a parent hierarchy ID on all child nodes.
     * @param node Hierarchy node whose Hierarchy ID was recently changed and should be propagated to all children.
     * @param newHierarchyId New hierarchy ID on the node.
     */
    void propagateHierarchyIdToChildren(HierarchyNode node, HierarchyId newHierarchyId);

    /**
     * Assigns a parent hierarchy ID on all child nodes, skipping the <code>ignoredChild</code> node.
     *
     * @param node Hierarchy node whose Hierarchy ID was recently changed and should be propagated to all children.
     * @param newHierarchyId New hierarchy ID on the node.
     * @param ignoredChild The child node name to ignore.
     */
    void propagateHierarchyIdToChildrenExcept(HierarchyNode node, HierarchyId newHierarchyId, String ignoredChild);

    /**
     * Returns an interator over entries in the field map.
     * @return Iteratable of entries.
     */
    Iterable<ChildFieldEntry> getChildEntries();
}
