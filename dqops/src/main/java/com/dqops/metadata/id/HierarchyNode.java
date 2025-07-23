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

import com.dqops.metadata.basespecs.DirtyStatus;
import com.dqops.metadata.basespecs.ReadOnlyStatus;
import com.dqops.utils.serialization.YamlNotRenderWhenDefault;
import org.apache.commons.collections.IteratorUtils;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface implemented by objects that are represented on the hierarchy ID tree.
 */
public interface HierarchyNode extends DirtyStatus, ReadOnlyStatus {
    /**
     * Find an element in an array that is of a given data type or a derived type.
     * @param nodes Array of hierarchy nodes to search inside.
     * @param targetType Target type to find or a base type.
     * @return Found node or null.
     * @param <T> Target type.
     */
    static <T extends HierarchyNode> T findNodeOfType(HierarchyNode[] nodes, Class<T> targetType) {
        for (HierarchyNode node : nodes) {
            if (targetType.isAssignableFrom(node.getClass())) {
                return (T)node;
            }
        }

        return null;
    }

    /**
     * Find an element in an array that matches a predicate.
     * @param nodes Array of hierarchy nodes to search inside.
     * @param filter Filter predicate
     * @return Found node or null.
     */
    static HierarchyNode findNode(HierarchyNode[] nodes, Predicate<HierarchyNode> filter) {
        for (HierarchyNode node : nodes) {
            if (filter.test(node)) {
                return node;
            }
        }

        return null;
    }

    /**
     * Detach all child nodes that are default (empty) and will not be rendered into YAML anyway.
     * The purpose of this method is to get rid of extra nodes that were created for a short time to avoid a serialization/deserialization approach for dropping empty nodes.
     */
    default void detachEmptyChildNodes() {
        for (HierarchyNode childNode : (List<HierarchyNode>)IteratorUtils.toList(this.children().iterator())) {
            childNode.detachEmptyChildNodes();

            if (childNode instanceof YamlNotRenderWhenDefault) {
                YamlNotRenderWhenDefault defaultNode = (YamlNotRenderWhenDefault)childNode;
                if (defaultNode.isDefault()) {
                    this.detachChildNode(childNode.getHierarchyId().getLast());
                }
            }
        }
    }

    /**
     * Retrieves a child node given an expected object type that we want to extract.
     * Returns a child that has the class type as expected or is a subclass of the expected type.
     * @param childName Child name.
     * @param expectedType Expected class of the child.
     * @param <T> Expected type that is returned.
     * @return Child node if it is of the required type or null if the child was not found or it is a different class.
     */
    default <T> T getExpectedChild(Object childName, Class<T> expectedType) {
        HierarchyNode childNode = this.getChild(childName);
        if (childNode == null) {
            return null;
        }

        if (expectedType.isAssignableFrom(childNode.getClass())) {
            return (T)childNode;
        }

        return null;
    }

    /**
     * Returns the hierarchy ID of this node.
     * @return Hierarchy ID of this node.
     */
    HierarchyId getHierarchyId();

    /**
     * Replaces the hierarchy ID. A new hierarchy ID is also propagated to all child nodes.
     * @param hierarchyId New hierarchy ID.
     */
    void setHierarchyId(HierarchyId hierarchyId);

    /**
     * Returns a named child. It is a named object in an object map (column map, test map) or a field name.
     * @param childName Child name.
     * @return Child node.
     */
    HierarchyNode getChild(Object childName);

    /**
     * Returns an iterable that iterates over child nodes.
     * @return Iterable to iterate over child nodes.
     */
    Iterable<HierarchyNode> children();

    /**
     * Clears the child node, setting a null value.
     * @param childName Child name.
     */
    void detachChildNode(Object childName);

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     * @param visitor Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @param <P> Parameter type.
     * @param <R> Result type.
     * @return Result value returned by an "accept" method of the visitor.
     */
    <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter);

    /**
     * Performs a deep clone of the hierarchy node object.
     * @return Deep cloned object.
     */
    HierarchyNode deepClone();
}
