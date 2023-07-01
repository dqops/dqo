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
