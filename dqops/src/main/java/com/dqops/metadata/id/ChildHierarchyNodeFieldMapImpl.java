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

import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.ReflectionService;
import com.dqops.utils.reflection.ReflectionServiceSingleton;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Dictionary of key/value map of property names (field names) in the current class and their property accessors.
 * @param <T>
 */
public class ChildHierarchyNodeFieldMapImpl<T extends HierarchyNode>
        extends HashMap<String, GetHierarchyChildNodeFunc<T>> implements ChildHierarchyNodeFieldMap {
    private final ClassInfo reflectionClassInfo;

    /**
     * Creates a new field map and copies the field map from a superclass field map.
     * @param baseClassFields Field map from a direct superclass.
     */
    public ChildHierarchyNodeFieldMapImpl(ChildHierarchyNodeFieldMap baseClassFields) {
        this();

        for(ChildFieldEntry childFieldEntry : baseClassFields.getChildEntries()) {
			this.put(childFieldEntry.getChildName(), (GetHierarchyChildNodeFunc<T>) childFieldEntry.getGetChildFunc());
        }
    }

    /**
     * Creates an empty field map.
     * NOTE: this method should always be private, always - to avoid missing subclass properties when a field map from a base class was not taken.
     */
    private ChildHierarchyNodeFieldMapImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] typeArguments = genericSuperclass.getActualTypeArguments();

        Type typeArgument1 = typeArguments[0];
        if (typeArgument1 instanceof Class<?> && typeArgument1 != String.class) {
            Class<?> targetClass = (Class<?>) typeArgument1;

            ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();
            this.reflectionClassInfo = reflectionService.getClassInfoForClass(targetClass);
        } else {
            this.reflectionClassInfo = null;
        }
    }

    /**
     * Returns the reflection class info - a reflected information about the class, its fields.
     * @return Reflection class info.
     */
    public ClassInfo getReflectionClassInfo() {
        return reflectionClassInfo;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * (A {@code null} return can also indicate that the map
     * previously associated {@code null} with {@code key}.)
     */
    @Override
    public GetHierarchyChildNodeFunc<T> put(String key, GetHierarchyChildNodeFunc<T> value) {
        assert key != null && key.equals(key.toLowerCase(Locale.ROOT)) : "Do not use upper case (Java property names) in the key names, use snake convention used in YAML";
        return super.put(key, value);
    }

    /**
     * Creates an empty field map. This method should be used only in base spec classes. All other classes should
     * take the list of hierarchy nodes from its superclass.
     * @return Empty field map.
     */
    public static <T extends HierarchyNode> ChildHierarchyNodeFieldMapImpl<T> empty() {
        return new ChildHierarchyNodeFieldMapImpl<T>();
    }

    /**
     * Returns a field getter delegate to read values from a field.
     *
     * @param fieldName Field name
     * @return Field getter delegate (lambda expression) or null when the property was not mapped in the property map.
     */
    @Override
    public GetHierarchyChildNodeFunc<HierarchyNode> getFieldGetter(String fieldName) {
        return (GetHierarchyChildNodeFunc<HierarchyNode>) this.get(fieldName); // Java type erasure magic
    }

    /**
     * Returns an interator over entries in teh field map.
     *
     * @return Iterable of entries.
     */
    @Override
    public Iterable<ChildFieldEntry> getChildEntries() {
        return new ChildFieldEntryIterable();
    }

    /**
     * Assigns a parent hierarchy ID on all child nodes.
     *
     * @param node Hierarchy node whose Hierarchy ID was recently changed and should be propagated to all children.
     * @param newHierarchyId New hierarchy ID on the node.
     */
    @Override
    public void propagateHierarchyIdToChildren(HierarchyNode node, HierarchyId newHierarchyId) {
        if (this.size() == 0) {
            return;
        }

        T castedNode = (T)node;
        for (final Map.Entry<String, GetHierarchyChildNodeFunc<T>> childFieldGetter : entrySet()) {
            final String fieldName = childFieldGetter.getKey();
            final GetHierarchyChildNodeFunc<T> accessor = childFieldGetter.getValue();
            final HierarchyNode childNode = accessor.apply(castedNode);
            if (childNode != null) {
                childNode.setHierarchyId(new HierarchyId(newHierarchyId, fieldName));
            }
        }
    }

    /**
     * Assigns a parent hierarchy ID on all child nodes, skipping the <code>ignoredChild</code> node.
     *
     * @param node Hierarchy node whose Hierarchy ID was recently changed and should be propagated to all children.
     * @param newHierarchyId New hierarchy ID on the node.
     * @param ignoredChild The child node name to ignore.
     */
    @Override
    public void propagateHierarchyIdToChildrenExcept(HierarchyNode node, HierarchyId newHierarchyId, String ignoredChild) {
        if (this.size() == 0) {
            return;
        }

        T castedNode = (T)node;
        for (final Map.Entry<String, GetHierarchyChildNodeFunc<T>> childFieldGetter : entrySet()) {
            final String fieldName = childFieldGetter.getKey();
            if (Objects.equals(fieldName, ignoredChild)) {
                continue;
            }

            final GetHierarchyChildNodeFunc<T> accessor = childFieldGetter.getValue();
            final HierarchyNode childNode = accessor.apply(castedNode);
            if (childNode != null) {
                childNode.setHierarchyId(new HierarchyId(newHierarchyId, fieldName));
            }
        }
    }

    /**
     * Child field entry iterable.
     */
    public class ChildFieldEntryIterable implements Iterable<ChildFieldEntry> {
        /**
         * Creates an iterable instance.
         */
        public ChildFieldEntryIterable() {
        }

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<ChildFieldEntry> iterator() {
            final Iterator<Map.Entry<String, GetHierarchyChildNodeFunc<T>>> mapIterator = entrySet().iterator();
            return new ChildFieldEntryIterator(mapIterator);
        }
    }

    /**
     * Iterator for child field entries in the map.
     */
    public class ChildFieldEntryIterator implements Iterator<ChildFieldEntry> {
        private final Iterator<Map.Entry<String, GetHierarchyChildNodeFunc<T>>> iterator;

        /**
         * Creates a transforming iterator over the iterator returned from the hash map.
         * @param iterator Source iterator.
         */
        public ChildFieldEntryIterator(final Iterator<Map.Entry<String, GetHierarchyChildNodeFunc<T>>> iterator) {
            this.iterator = iterator;
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
            return iterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public ChildFieldEntry next() {
            Map.Entry<String, GetHierarchyChildNodeFunc<T>> entry = this.iterator.next();
            return new ChildFieldEntry(entry.getKey(), (GetHierarchyChildNodeFunc<HierarchyNode>)entry.getValue());
        }
    }
}
