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
package com.dqops.metadata.id;

import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Hierarchy ID class that identifies objects in the metadata tree.
 */
public class HierarchyId {
    private static final HierarchyId ROOT = new HierarchyId();
    private final Object[] elements;

    /**
     * Creates a hierarchy id, given an array of keys on the path.
     * @param elements Array of keys.
     */
    public HierarchyId(Object... elements) {
        this.elements = elements;
    }

    /**
     * Creates a hierarchy ID given the hierarchy ID of the parent and the new of the child element (field name or a key name in the dictionary).
     * @param parent Parent hierarchy id.
     * @param element Child name.
     */
    public HierarchyId(HierarchyId parent, Object element) {
		this.elements = Arrays.copyOf(parent.elements, parent.elements.length + 1);
		this.elements[this.elements.length - 1] = element;
    }

    /**
     *  Returns a root (no path) hierarchy ID that is used on the UserHome.
     * @return Root hierarchy ID object for the UserHome.
     */
    public static HierarchyId getRoot() {
        return ROOT;
    }

    /**
     * Creates a child hierarchy id given a parent hierarchy id and a child object.
     * @param parentHierarchyId Parent hierarchy id. Null value is accepted.
     * @param childName Child name (child object).
     * @return Child hierarchy id that is a copy of the parent path concatenated with the child name or null when the parent hierarchy id is null.
     */
    public static HierarchyId makeChildOrNull(HierarchyId parentHierarchyId, Object childName) {
        if (parentHierarchyId == null) {
            return null;
        }

        return new HierarchyId(parentHierarchyId, childName);
    }

    /**
     * Creates a new hierarchy id for a connection node.
     * @param connectionName Connection name.
     * @return Hierarchy id for a connection.
     */
    public static HierarchyId makeHierarchyIdForConnection(String connectionName) {
        HierarchyId hierarchyId = new HierarchyId("connections", connectionName);
        return hierarchyId;
    }

    /**
     * Creates a new hierarchy id for a table node.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Hierarchy id for a table.
     */
    public static HierarchyId makeHierarchyIdForTable(String connectionName, PhysicalTableName physicalTableName) {
        HierarchyId hierarchyId = new HierarchyId("connections", connectionName, "tables", physicalTableName);
        return hierarchyId;
    }

    /**
     * Returns a path element at the given index.
     * @param index 0-based index of the hierarchy path element to retrieve.
     * @return Hierarchy path element.
     */
    public Object get(int index) {
        return this.elements[index];
    }

    /**
     * Returns the last (top-most) path element.
     * @return Last (deepest) path element.
     */
    public Object getLast() {
        return this.elements[this.elements.length - 1];
    }

    /**
     * Returns the length of the hierarchy path.
     * @return Number of elements on the path.
     */
    public int size() {
        return this.elements.length;
    }

    /**
     * Checks if the given hierarchy id has the same path as the current object, but is longer and is a child or grant/grant+++ child of the current hierarchy id.
     * @param descendantCandidate Hierarchy id of a potential child (or deeper descendant) that is tested.
     * @return true when the candidate is a descendant, false if it comes from a different parent path.
     */
    public boolean isMyDescendant(final HierarchyId descendantCandidate) {
        if (descendantCandidate == null) {
            return false;
        }

        if (descendantCandidate.elements.length <= this.elements.length) {
            return false;
        }

        for (int i = 0; i < this.elements.length; i++) {
            if (!Objects.equals(this.elements[i], descendantCandidate.elements[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds all nodes on path following child node ids in the hierarchy ID, starting from the <code>rootNode</code> node.
     * @param rootNode Root node (start node).
     * @return Hierarchy nodes on the path.
     */
    public HierarchyNode[] getNodesOnPath(HierarchyNode rootNode) {
        HierarchyNode[] nodesOnPath = new HierarchyNode[this.elements.length - rootNode.getHierarchyId().size()];
        HierarchyNode currentNode = rootNode;
        for (int i = rootNode.getHierarchyId().size(); i < this.elements.length; i++) {
            HierarchyNode childNode = currentNode.getChild(this.elements[i]);
            if (childNode == null) {
                throw new NoSuchElementException("Cannot find child named " + this.elements[i] + " on object " + currentNode);
            }
            nodesOnPath[i - rootNode.getHierarchyId().size()] = childNode;
            currentNode = childNode;
        }

        return nodesOnPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HierarchyId that = (HierarchyId) o;
        return Arrays.equals(elements, that.elements);
    }

    /**
     * Returns a regular 32-bit hash. This has should be used only internally by Java collection classes, using also equals to avoid hash collisions.
     * @return 32-bit hash.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    /**
     * Calculate a 64-bit hash of the path.
     * @return 64-bit hash.
     */
    public long hashCode64() {
        List<HashCode> elementHashes = Arrays.stream(this.elements)
                .map(element -> Hashing.farmHashFingerprint64().hashString(element.toString(), StandardCharsets.UTF_8))
                .collect(Collectors.toList());
        return Math.abs(Hashing.combineOrdered(elementHashes).asLong()); // we return only positive hashes which limits the hash space to 2^63, but positive hashes are easier for users
    }

    /**
     * Returns a string representation of the hierarchy id path as key1/key2/key3/...
     * @return String representation of the path.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.elements.length; i++) {
            Object element = this.elements[i];
            if (sb.length() > 0) {
                sb.append('/');
            }
            sb.append(element);
        }
        return sb.toString();
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public HierarchyId clone() {
        return new HierarchyId(this.elements.clone());
    }

    /**
     * Creates a hierarchy id model.
     * @return Hierarchy ID model.
     */
    public HierarchyIdModel toHierarchyIdModel() {
        return new HierarchyIdModel(this.elements);
    }

    /**
     * Retrieves the connection name from a hierarchy id.
     * @return Connection name or null when the hierarchy id is not within the connections hierarchy.
     */
    @JsonIgnore
    public String getConnectionName() {
        if (this.elements.length < 2) {
            return null;
        }

        if (!Objects.equals(this.elements[0], "connections")) {
            return null;
        }

        return (String)this.elements[1];
    }

    /**
     * Retrieves the physical table name from a hierarchy id.
     * @return Physical table name or null when the hierarchy id is not within the connections / [connection] / tables hierarchy.
     */
    @JsonIgnore
    public PhysicalTableName getPhysicalTableName() {
        if (this.elements.length < 4) {
            return null;
        }

        if (!Objects.equals(this.elements[0], "connections")) {
            return null;
        }

        if (!Objects.equals(this.elements[2], "tables")) {
            return null;
        }

        return (PhysicalTableName)this.elements[3];
    }

    /**
     * Retrieves the column name from a hierarchy id.
     * @return PColumn name or null when the hierarchy id is not within the connections / [connection] / tables / [tablename] / columns / [here is the column name] hierarchy.
     */
    @JsonIgnore
    public String getColumnName() {
        if (this.elements.length < 7) {
            return null;
        }

        if (!Objects.equals(this.elements[0], "connections")) {
            return null;
        }

        if (!Objects.equals(this.elements[2], "tables")) {
            return null;
        }

        if (!Objects.equals(this.elements[4], "spec")) {
            return null;
        }

        if (!Objects.equals(this.elements[5], "columns")) {
            return null;
        }

        return (String)this.elements[6];
    }

    /**
     * Creates a hierarchy id for the parent node, which is a copy of the current hierarchy id, but without the last element.
     * @return Hierarchy id of the parent node.
     */
    @JsonIgnore
    public HierarchyId getParentHierarchyId() {
        Object[] parentHierarchyElements = new Object[this.elements.length - 1];
        for (int i = 0; i < parentHierarchyElements.length; i++) {
            parentHierarchyElements[i] = this.elements[i];
        }

        return new HierarchyId(parentHierarchyElements);
    }
}
