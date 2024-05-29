package com.dqops.metadata.sources.fileformat;

import com.dqops.metadata.basespecs.ReadOnlyObjectModifiedException;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.YamlNotRenderWhenDefault;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class FilePathListSpec extends AbstractList<String> implements HierarchyNode, YamlNotRenderWhenDefault {

    @JsonIgnore
    private ArrayList<String> filePaths = new ArrayList<>();
    @JsonIgnore
    private boolean dirty;
    @JsonIgnore
    private boolean readOnly;
    @JsonIgnore
    private HierarchyId hierarchyId;

    @Override
    public String get(int index) {
        return filePaths.get(index);
    }

    @Override
    public int size() {
        return filePaths.size();
    }

    @Override
    public boolean add(String s) {
        setDirty();
        return this.filePaths.add(s);
    }

    /**
     * {@inheritDoc}
     *
     * @param o
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @implSpec This implementation iterates over the collection looking for the
     * specified element.  If it finds the element, it removes the element
     * from the collection using the iterator's remove method.
     *
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} if the iterator returned by this
     * collection's iterator method does not implement the {@code remove}
     * method and this collection contains the specified object.
     */
    @Override
    public boolean remove(Object o) {
        boolean removed = this.filePaths.remove(o);
        if (removed) {
            setDirty();
        }
        return removed;
    }

    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection
     */
    @Override
    public Iterator<String> iterator() {
        return this.filePaths.iterator();
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    /**
     * Sets the dirty flag to true.
     */
    @Override
    public void setDirty() {
        if (this.readOnly) {
            throw new ReadOnlyObjectModifiedException(this);
        }
        this.dirty = true;
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        this.dirty = false;
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
        this.hierarchyId = hierarchyId;
    }

    /**
     * Returns a named child. It is a named object in an object map (column map, test map) or a field name.
     *
     * @param childName Child name.
     * @return Child node.
     */
    @Override
    public HierarchyNode getChild(Object childName) {
        return null;
    }

    /**
     * Returns an iterable that iterates over child nodes.
     *
     * @return Iterable to iterate over child nodes.
     */
    @Override
    public Iterable<HierarchyNode> children() {
        return Collections.emptyList();
    }

    /**
     * Clears the child node, setting a null value.
     *
     * @param childName Child name.
     */
    @Override
    public void detachChildNode(Object childName) {
        // do nothing
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public FilePathListSpec deepClone() {
        FilePathListSpec cloned = new FilePathListSpec();
        cloned.filePaths = (ArrayList<String>) this.filePaths.clone();
        cloned.clearDirty(false);
        cloned.readOnly = false;
        return cloned;
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        return this.size() == 0;
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
        this.readOnly = true;
    }
}
