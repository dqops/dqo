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
