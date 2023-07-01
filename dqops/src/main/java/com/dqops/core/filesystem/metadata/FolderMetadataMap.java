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
package com.dqops.core.filesystem.metadata;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Dictionary of subfolders inside the current folder.
 */
public class FolderMetadataMap extends AbstractCollection<FolderMetadata> {
    private Map<String, FolderMetadata> folders = new TreeMap<>();
    private boolean frozen;

    /**
     * {@inheritDoc}
     *
     * @param folderMetadata
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IllegalStateException         {@inheritDoc}
     * @implSpec This implementation always throws an
     * {@code UnsupportedOperationException}.
     */
    @Override
    public synchronized boolean add(FolderMetadata folderMetadata) {
        assert !frozen;
        String folderName = folderMetadata.getFolderName();
        this.folders.put(folderName, folderMetadata);
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
        FolderMetadata folderMetadata = (FolderMetadata)o;
        return this.folders.remove(folderMetadata.getFolderName()) != null;
    }

    /**
     * Returns a child folder given the folder name.
     * @param folderName Folder name.
     * @return Folder metadata or null when not found.
     */
    public synchronized FolderMetadata get(String folderName) {
        return this.folders.get(folderName);
    }

    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection
     */
    @Override
    public Iterator<FolderMetadata> iterator() {
        return this.folders.values().iterator();
    }

    /**
     * Returns the size (number) of subfolders.
     * @return Number of subfolders.
     */
    @Override
    public synchronized int size() {
        return this.folders.size();
    }

    /**
     * Freezes the current collection of children and makes it read only.
     */
    public synchronized void freeze() {
        if (this.frozen) {
            return;
        }
        assert this.folders instanceof TreeMap;

        this.frozen = true;

        for (FolderMetadata folderMetadata : this.folders.values()) {
            folderMetadata.freeze();
        }
        this.folders = Collections.unmodifiableMap(this.folders);
    }

    /**
     * Returns true if the collection is frozen.
     * @return Collection of folders is frozen.
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Clones the current dictionary as an unfrozen copy.
     * Folders inside the collection are still frozen and should be replaced when needed.
     * @return Cloned mutable copy of the collection.
     */
    public synchronized FolderMetadataMap cloneUnfrozen() {
        FolderMetadataMap cloned = new FolderMetadataMap();
        cloned.folders.putAll(this.folders);
        return cloned;
    }

    /**
     * Returns a mutable (unfrozen) folder.
     * @param folderName Folder name.
     * @return Unfrozen folder metadata that could be mutated (files added, child folders added).
     */
    public synchronized FolderMetadata getMutable(String folderName) {
        if (this.frozen) {
            throw new FileSystemMetadataFrozenException("Cannot retrieve a mutable folder from a frozen folder map");
        }

        FolderMetadata folderMetadata = this.folders.get(folderName);
        if (folderMetadata == null) {
            return null;
        }

        if (!folderMetadata.isFrozen()) {
            return folderMetadata;
        }

        FolderMetadata unfrozenCopy = folderMetadata.cloneUnfrozen();
        this.folders.put(folderName, unfrozenCopy); // replacing with an unfrozen copy

        return unfrozenCopy;
    }

    /**
     * Calculates a 64-bit hash of all the files.
     * @return 64-bit farm hash.
     */
    public synchronized HashCode calculateHash64() {
        if (this.folders.size() == 0) {
            return HashCode.fromLong(1L);
        }

        HashCode hashCode = Hashing.combineOrdered(
                this.folders.values()
                        .stream()
                        .map(fm -> HashCode.fromLong(fm.getHash()))
                        .collect(Collectors.toList())
        );
        return hashCode;
    }

    /**
     * Checks if the given folder name is stored in the folder list.
     * @param folderName Folder name.
     * @return True when the file was found, false when the file is missing.
     */
    public synchronized boolean containsFolderName(String folderName) {
        return this.folders.containsKey(folderName);
    }

    /**
     * Removes a folder given a folder name.
     * @param folderName Folder name.
     * @return True when the file was removed, false when it was not present.
     */
    public synchronized boolean removeByFolderName(String folderName) {
        return this.folders.remove(folderName) != null;
    }

    /**
     * Drops outstanding child folders to match the limit. Outstanding folders are just detached.
     * @param limit Limit of child folders to keep.
     * @param isValidFolderName Lambda function used to filter the folder names.
     * @param sortingOrder Sorting order for selecting the tables.
     */
    public void truncateToLimit(int limit, Function<String, Boolean> isValidFolderName, FolderTruncationMode sortingOrder) {
        if (this.folders.size() <= limit) {
            return;
        }

        List<String> foldersToDetach = this.folders.keySet().stream()
                .filter(isValidFolderName::apply)
                .sorted(sortingOrder == FolderTruncationMode.DESCENDING_SORTED_FIRST ? Comparator.naturalOrder() : Comparator.reverseOrder())
                .limit(this.folders.size() - limit)
                .collect(Collectors.toList());

        foldersToDetach.forEach(folderName -> this.removeByFolderName(folderName));
    }
}
