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
package com.dqops.metadata.basespecs;

import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.dao.DuplicateKeyException;

import java.util.*;

/**
 * Base collection that is tracking changes and indexes elements by name.
 */
public abstract class AbstractIndexingList<K, V extends ObjectName<K> & Flushable & InstanceStatusTracking & HierarchyNode>
        extends AbstractList<V> implements HierarchyNode {
    @JsonIgnore
    private List<V> list = new ArrayList<>();
    @JsonIgnore
    private Map<K, V> index = new LinkedHashMap<>();
    @JsonIgnore
    private List<V> deleted = new ArrayList<>();
    @JsonIgnore
    private boolean loaded;
    @JsonIgnore
    private HierarchyId hierarchyId;
    @JsonIgnore
    private boolean dirty;
    @JsonIgnore
    private boolean readOnly;
    @JsonIgnore
    private final Object lock = new Object();

    /**
     * Creates a new, mutable list.
     */
    public AbstractIndexingList() {
    }

    /**
     * Creates a new list, configuring the read-only status.
     * @param readOnly Make the list read-only.
     */
    public AbstractIndexingList(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * Finds an existing object given the object name.
     * @param key Object name.
     * @param loadAllWhenMissing Forces loading all elements from the persistence store when the element is missing. When false, then simply checks if the element is in the dictionary.
     * @return Existing object (model wrapper) or null when the object was not found.
     */
    public V getByObjectName(K key, boolean loadAllWhenMissing) {
        V result = this.index.get(key);
        if (result == null) {
            if (loadAllWhenMissing) {
				loadOnce();
                result = this.index.get(key);
            }
        }
        return result;
    }

    /**
     * Returns a list of deleted elements.
     * @return Deleted elements.
     */
    public List<V> getDeleted() {
        return deleted;
    }

    /**
     * Returns the hierarchy ID of this node.
     *
     * @return Hierarchy ID of this node.
     */
    @Override
    public HierarchyId getHierarchyId() {
        return this.hierarchyId;
    }

    /**
     * Replaces the hierarchy ID. A new hierarchy ID is also propagated to all child nodes.
     *
     * @param hierarchyId New hierarchy ID.
     */
    @Override
    public void setHierarchyId(HierarchyId hierarchyId) {
        assert hierarchyId != null;
        this.hierarchyId = hierarchyId;
        if (this.list.size() > 0) {
            for (V element : this.list) {
                element.setHierarchyId(new HierarchyId(hierarchyId, element.getObjectName()));
            }
        }
    }

    /**
     * Returns a named child. It is a named object in an object map (column map, test map) or a field name.
     *
     * @param childName Child name.
     * @return Child node.
     */
    @Override
    public HierarchyNode getChild(Object childName) {
        return this.getByObjectName((K)childName, true);
    }

    /**
     * Returns an iterable that iterates over child nodes.
     *
     * @return Iterable to iterate over child nodes.
     */
    @Override
    public Iterable<HierarchyNode> children() {
        return (Iterable<HierarchyNode>)(Object)this; // java type erasure at runtime will see that this is an iterable of a HierarchyNode subclass
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V set(int index, V element) {
        if (this.readOnly) {
            throw new ReadOnlyObjectModifiedException(this);
        }
		this.loadOnce();
        V old = this.list.get(index);
        K objectName = element.getObjectName();
        assert objectName != null;
        if (this.index.get(objectName) != null) {
            throw new DuplicateKeyException("Object " + objectName + " is already indexed");
        }
        K oldKey = old.getObjectName();
        this.index.remove(oldKey);
        this.index.put(objectName, element);
        if (this.hierarchyId != null) {
            element.setHierarchyId(new HierarchyId(this.hierarchyId, objectName));
        }
        if (element.getStatus() != InstanceStatus.UNCHANGED) {
			this.setDirty();
        }
        return this.list.set(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, V element) {
        if (this.readOnly) {
            throw new ReadOnlyObjectModifiedException(this);
        }
        K objectName = element.getObjectName();
        assert objectName != null;
        if (this.index.containsKey(objectName)) {
            throw new DuplicateKeyException("Object " + objectName + " is already indexed");
        }
        if (this.deleted.size() > 0) {
            Optional<V> deleted = this.deleted.stream().filter(w -> Objects.equals(w.getObjectName(), objectName)).findFirst();
            if(deleted.isPresent()) {
                this.deleted.remove(deleted.get());
            }
        }
        this.index.put(objectName, element);
		this.list.add(index, element);
        if (element.getStatus() != InstanceStatus.UNCHANGED) {
			this.setDirty();
        }
        if (this.hierarchyId != null) {
            element.setHierarchyId(new HierarchyId(this.hierarchyId, objectName));
        }
    }

    /**
     * Simply adds a node without triggering a full load of all other models.
     * @param element Element to be added.
     * @return true when the object was added, false when an object with the same name was present and was preserved
     */
    protected boolean addWithoutFullLoad(V element) {
        K objectName = element.getObjectName();
        assert objectName != null;
        if (this.index.containsKey(objectName)) {
            return false;
        }
        if (this.deleted.size() > 0) {
            Optional<V> deleted = this.deleted.stream().filter(w -> Objects.equals(w.getObjectName(), objectName)).findFirst();
            if(deleted.isPresent()) {
                this.deleted.remove(deleted.get()); // resurrect
            }
        }
		this.index.put(objectName, element);
		this.list.add(element);
        if (this.hierarchyId != null) {
            element.setHierarchyId(new HierarchyId(this.hierarchyId, objectName));
        }
        if (element.getStatus() != InstanceStatus.NOT_TOUCHED) {
			this.setDirty();
        }

        if (this.readOnly) {
            element.makeReadOnly(true);
        }
        return true;
    }

    /**
     * Creates a new element instance that is marked as new and should be saved on flush.
     * @param key Object key.
     * @return Created object instance.
     */
    public V createAndAddNew(K key) {
        if (this.readOnly) {
            throw new ReadOnlyObjectModifiedException(this);
        }
        V existingElement = this.getByObjectName(key, true);
        if (existingElement != null) {
            throw new DuplicateKeyException("Object with this name already exist");
        }
        if (this.deleted.size() > 0) {
            Optional<V> deleted = this.deleted.stream().filter(w -> Objects.equals(w.getObjectName(), key)).findFirst();
            if(deleted.isPresent()) {
                this.deleted.remove(deleted.get()); // resurrect
            }
        }
        V newElement = createNewElement(key);
        newElement.setStatus(InstanceStatus.ADDED);
		this.index.put(key, newElement);
		this.list.add(newElement);
        if (this.hierarchyId != null) {
            newElement.setHierarchyId(new HierarchyId(this.hierarchyId, key));
        }
		this.setDirty();
        return newElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        if (this.readOnly) {
            throw new ReadOnlyObjectModifiedException(this);
        }
        ObjectName<K> objectNameHolder = (ObjectName<K>)o;
        K objectName = objectNameHolder.getObjectName();
        V existingElement = getByObjectName(objectName, true);
        if (existingElement == null) {
            return false;
        }
		this.index.remove(objectName);
        boolean removedFromList = this.list.remove(o);
        assert removedFromList;
		this.deleted.add((V)o);
		this.setDirty();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(int index) {
        if (this.readOnly) {
            throw new ReadOnlyObjectModifiedException(this);
        }
        V existing = this.list.get(index);
        K objectName = existing.getObjectName();
        this.index.remove(objectName);
		this.deleted.add(existing);
		this.setDirty();
        return this.list.remove(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(int index) {
        return this.list.get(index);
    }

    /**
     * Returns the size of the collection. A call to this method will trigger a full load and will load all elements
     * from the persistence store (files or database).
     * @return Total count of elements.
     */
    @Override
    public int size() {
		this.loadOnce();
        return this.list.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<V> iterator() {
		this.loadOnce();
        return super.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<V> listIterator(int index) {
		this.loadOnce();
        return super.listIterator(index);
    }

    /**
     * Loads the list once, only when it was not loaded yet.
     */
    protected void loadOnce() {
        synchronized (this.lock) {
            if (this.loaded) {
                return;
            }
            this.loaded = true; // this will avoid calling the load from an iterator again
            this.load();
        }
    }

    /**
     * Loads the elements from the backend source.
     */
    protected void load() {
    }

    /**
     * Flushes all changes to the persistence store (file store or the database).
     */
    public void flush() {
        for (V modelWrapper : this.list) {
            modelWrapper.flush();
        }
		this.clearDirty(false);
    }

    /**
     * Creates a new element given an object name. Derived classes should create a correct object type.
     * @param objectName Object name.
     * @return Created and detached new instance with the object name assigned.
     */
    protected abstract V createNewElement(K objectName);

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        if (this.dirty) {
            return true;
        }

        for (V element : this.list) {
            if (element.isDirty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Sets the dirty flag to true.
     */
    @Override
    public void setDirty() {
		this.dirty = true;
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
		this.dirty = false;
        if (propagateToChildren) {
            for (V element : this.list) {
                element.clearDirty(true);
            }
        }
    }

    /**
     * Check if the object is frozen (read only). A read-only object cannot be modified.
     *
     * @return True when the object is read-only and trying to apply a change will return an error.
     */
    @Override
    @JsonIgnore
    public boolean isReadOnly() {
        return this.readOnly;
    }

    /**
     * Sets the read-only flag on the current object, and optionally on child objects.
     *
     * @param propagateToChildren When true, makes also the child objects as read-only.
     */
    @Override
    @JsonIgnore
    public void makeReadOnly(boolean propagateToChildren) {
        if (!this.readOnly) {
            this.readOnly = true;
            if (propagateToChildren) {
                for (V element : this.list) {
                    element.makeReadOnly(true);
                }
            }
        }
    }

    /**
     * Performs a deep clone of the object.
     *
     * @return Deep clone of the object.
     */
    @Override
    public AbstractIndexingList<K,V> deepClone() {
        try {
            AbstractIndexingList<K,V> cloned = (AbstractIndexingList<K,V>) super.clone();
            cloned.list = new ArrayList<>();
            cloned.index = new LinkedHashMap<>();
            cloned.deleted = new ArrayList<>();
            cloned.dirty = false;
            cloned.readOnly = false;

            if (this.list.size() == 0 && this.deleted.size() == 0) {
                return cloned;
            }

            for (V childNode : this.list) {
                V clonedChild = (V)childNode.deepClone();
                cloned.list.add(clonedChild);
                cloned.index.put(clonedChild.getObjectName(), clonedChild);
            }

            for (V deletedChildNode : this.deleted) {
                V clonedDeletedChild = (V)deletedChildNode.deepClone();
                cloned.deleted.add(clonedDeletedChild);
            }

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new UnsupportedOperationException("Cannot clone the object ", ex);
        }
    }
}
