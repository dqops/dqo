/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.storage.parquet;

import com.dqops.utils.exceptions.DqoRuntimeException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Custom hadoop path to access files in the in-memory file system.
 */
public class DqoInMemoryPath extends Path {
    private FileSystem fileSystem;

    public DqoInMemoryPath(URI aUri, FileSystem fileSystem) {
        super(aUri);
//        assert Objects.equals(aUri.getScheme(), "ramfs");
        this.fileSystem = fileSystem;
    }

    public DqoInMemoryPath(String path, FileSystem fileSystem) {
        this(makeUri(path), fileSystem);
    }

    private static URI makeUri(String path) {
        try {
            return new URI(path);
        }
        catch (URISyntaxException ex) {
            throw new DqoRuntimeException(ex);
        }
    }

    /**
     * Returns a given in-memory file system instance, to separate in-memory file spaces.
     * @param conf Hadoop configuration, ignored.
     * @return File system instance.
     */
    @Override
    public FileSystem getFileSystem(Configuration conf) {
        return this.fileSystem;
    }
}
