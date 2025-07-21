/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.fileindices;

import com.dqops.core.synchronization.contract.DqoRoot;
import com.google.common.base.Enums;
import com.google.common.base.Optional;

import java.util.Locale;

/**
 * File index key. Identifies a single file index file.
 */
public class FileIndexName {
    private DqoRoot dqoRoot;
    private FileLocation location;

    /**
     * Creates a file index key object.
     * @param dqoRoot  File index type.
     * @param location File location (local files or remote files).
     */
    public FileIndexName(DqoRoot dqoRoot, FileLocation location) {
        this.dqoRoot = dqoRoot;
        this.location = location;
    }

    /**
     * Creates a file index name given a base name. A base name is the file name of the index file before the .dqofidx.json extension.
     * It looks like indexname.local or indexname.remote
     * @param baseFileName Base file name to parse.
     * @return File index name object.
     */
    public static FileIndexName fromBaseFileName(String baseFileName) {
        int indexOfDot = baseFileName.indexOf('.');
        if (indexOfDot < 1 || indexOfDot > baseFileName.length() - ".local".length()) {
            throw new IllegalArgumentException("Index file name is invalid");
        }

        String indexName = baseFileName.substring(0, indexOfDot).toLowerCase(Locale.ROOT);
        Optional<DqoRoot> dqoRootOptional = Enums.getIfPresent(DqoRoot.class, indexName);
        if (!dqoRootOptional.isPresent()) {
            return null;
        }

        String locationName = baseFileName.substring(indexOfDot + 1);
        FileLocation fileLocation = FileLocation.valueOf(locationName);
        return new FileIndexName(dqoRootOptional.get(), fileLocation);
    }

    /**
     * Returns a base file name to be used to name the index file.
     * @return Base file name.
     */
    public String toBaseFileName() {
        return this.dqoRoot.toString().toLowerCase(Locale.ROOT) + "." + this.location.toString();
    }

    /**
     * Returns the root folder type. Roots are: sources, custom rules, custom sensors, sensor readouts and rule results.
     * @return Root folder type.
     */
    public DqoRoot getDqoRoot() {
        return dqoRoot;
    }

    /**
     * Returns the location of the files (local files or remote files).
     * @return Local or remote.
     */
    public FileLocation getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileIndexName that = (FileIndexName) o;

        if (!dqoRoot.equals(that.dqoRoot)) return false;
        return location == that.location;
    }

    @Override
    public int hashCode() {
        int result = dqoRoot.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return dqoRoot.toString().toLowerCase(Locale.ROOT) + '.' + location.toString();
    }
}
