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
