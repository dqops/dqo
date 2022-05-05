/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.id;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HierarchyId that = (HierarchyId) o;
        return Arrays.equals(elements, that.elements);
    }

    /**
     * Returns a regular 32 bit hash. This has should be used only internally by Java collection classes, using also equals to avoid hash collisions.
     * @return 32 bit hash.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    /**
     * Calculate a 64 bit hash of the path.
     * @return 64 bit hash.
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
}
