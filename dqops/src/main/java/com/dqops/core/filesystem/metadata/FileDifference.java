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

import java.nio.file.Path;
import java.util.Objects;

/**
 * Identifies a pair of a local file and a remote (other) file that differs.
 */
public class FileDifference {
    private FileMetadata oldFile;
    private FileMetadata newFile;

    /**
     * Creates a file difference.
     * @param oldFile Compared file (usually a local file). May be null if the local file is missing and that is the difference.
     * @param newFile Other file (usually a remote file). May be null if the remote file is missing and that is the difference.
     */
    public FileDifference(FileMetadata oldFile, FileMetadata newFile) {
        assert oldFile == null || newFile == null || Objects.equals(oldFile.getRelativePath(), newFile.getRelativePath());

        this.oldFile = oldFile;
        this.newFile = newFile;
    }

    /**
     * Returns the compared file (usually a local file). May be null if the local file is missing and that is the difference.
     * @return Source (local) file or null when the file is missing.
     */
    public FileMetadata getOldFile() {
        return oldFile;
    }

    /**
     * Returns the other file (usually a remote file). May be null if the remote file is missing and that is the difference.
     * @return Other (remote) file or null when the file is missing.
     */
    public FileMetadata getNewFile() {
        return newFile;
    }

    /**
     * The current file was deleted since the last status (stored int the <code>otherFile</code>.
     * @return True when the current file was deleted.
     */
    public boolean isCurrentDeleted() {
        return this.oldFile != null && this.newFile == null;
    }

    /**
     * The current file is new and was not known before.
     * @return True when the current file is new.
     */
    public boolean isCurrentNew() {
        return this.oldFile == null && this.newFile != null;
    }

    /**
     * Checks that the file was changed.
     * @return True when the file was changed.
     */
    public boolean isCurrentChanged() {
        return this.oldFile != null && this.newFile != null;
    }

    /**
     * Returns the relative path of the file in the difference.
     * @return Relative path to the file.
     */
    public Path getRelativePath() {
        if (this.oldFile != null) {
            return this.oldFile.getRelativePath();
        }

        return this.newFile.getRelativePath();
    }
}
