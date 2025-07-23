/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.metadata;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Dictionary of child files inside the current folder.
 */
public class FileMetadataMap extends AbstractCollection<FileMetadata> {
    private Map<String, FileMetadata> files = new TreeMap<>();
    private boolean frozen;

    /**
     * {@inheritDoc}
     *
     * @param fileMetadata
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IllegalStateException         {@inheritDoc}
     * @implSpec This implementation always throws an
     * {@code UnsupportedOperationException}.
     */
    @Override
    public synchronized boolean add(FileMetadata fileMetadata) {
        assert !frozen;
        String fileName = fileMetadata.getFileName();
        this.files.put(fileName, fileMetadata);
        return true;
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
    public synchronized boolean remove(Object o) {
        FileMetadata fileMetadata = (FileMetadata)o;
        return this.files.remove(fileMetadata.getFileName()) != null;
    }

    /**
     * Returns a child file given the file name.
     * @param fileName File name.
     * @return File metadata or null when not found.
     */
    public synchronized FileMetadata get(String fileName) {
        return this.files.get(fileName);
    }

    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection
     */
    @Override
    public Iterator<FileMetadata> iterator() {
        return this.files.values().iterator();
    }

    /**
     * Returns the size (number) of subfolders.
     * @return Number of subfolders.
     */
    @Override
    public synchronized int size() {
        return this.files.size();
    }

    /**
     * Freezes the current collection of children and makes it read only.
     */
    public synchronized void freeze() {
        if (this.frozen) {
            return;
        }
        assert this.files instanceof TreeMap;

        this.frozen = true;
        this.files = Collections.unmodifiableMap(this.files);
    }

    /**
     * Returns true if the collection is frozen.
     * @return Collection of files is frozen.
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Clones the current dictionary as an unfrozen copy.
     * @return Cloned mutable copy of the collection.
     */
    public synchronized FileMetadataMap cloneUnfrozen() {
        FileMetadataMap cloned = new FileMetadataMap();
        cloned.files.putAll(this.files);
        return cloned;
    }

    /**
     * Calculates a 64-bit hash of all the files.
     * @return 64-bit farm hash.
     */
    public synchronized HashCode calculateHash64() {
        if (this.files.size() == 0) {
            return HashCode.fromLong(2L);
        }

        HashCode hashCode = Hashing.combineOrdered(
                this.files.values()
                        .stream()
                        .map(FileMetadata::calculateHash64)
                        .collect(Collectors.toList())
        );
        return hashCode;
    }

    /**
     * Checks if the given file name is stored in the file list.
     * @param fileName File name.
     * @return True when the file was found, false when the file is missing.
     */
    public synchronized boolean containsFileName(String fileName) {
        return this.files.containsKey(fileName);
    }

    /**
     * Removes a file given a file name.
     * @param fileName File name.
     * @return True when the file was removed, false when it was not present.
     */
    public synchronized boolean removeByFileName(String fileName) {
        return this.files.remove(fileName) != null;
    }
}
