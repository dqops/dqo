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
package com.dqops.core.filesystem.virtual;

import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Virtual file tree node in the virtual file system.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class FileTreeNode implements Cloneable {
    private HomeFilePath filePath;
    private FileContent content; // the file content is loaded lazy, only when requested
    private FileTreeNodeStatus status = FileTreeNodeStatus.NOT_LOADED;

    public FileTreeNode() {
    }

    /**
     * Creates a file tree node given a file system context and a file path.
     * @param filePath Virtual file path.
     */
    public FileTreeNode(HomeFilePath filePath) {
        this.filePath = filePath;
    }

    /**
     * Creates a file tree node with a file content and file kind.
     * @param filePath Virtual file path.
     * @param content File content.
     * @param status File status.
     */
    public FileTreeNode(HomeFilePath filePath, FileContent content, FileTreeNodeStatus status) {
        this.filePath = filePath;
        this.content = content;
        this.status = status;
    }

    /**
     * Returns a virtual file path of the file inside the home folder.
     * @return Virtual file path.
     */
    public HomeFilePath getFilePath() {
        return filePath;
    }

    /**
     * Sets the virtual file path of the file.
     * @param filePath Virtual file path.
     */
    public void setFilePath(HomeFilePath filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the node status (new, deleted, modified, etc.).
     * @return File node status.
     */
    public FileTreeNodeStatus getStatus() {
        return status;
    }

    /**
     * Sets the virtual file status. Do not call this method unless it is necessary. This setter is provided for the deserializer only.
     * @param status New status.
     */
    public void setStatus(FileTreeNodeStatus status) {
        this.status = status;
    }

    /**
     * Clears the file node status as a file that was loaded but is not modified.
     */
    public void setStatusAsUnmodified() {
        assert this.content != null;
		this.status = FileTreeNodeStatus.LOADED_NOT_MODIFIED;
    }

    /**
     * Retrieves the file content. A local file system implementation will load the file from the file system.
     * The file content is cached in the node for any following calls.
     * @return File content loaded from the file.
     */
    public FileContent getContent() {
        return content;
    }

    /**
     * Sets a file content
     * @param content
     */
    public void setContent(FileContent content) {
        this.content = content;
    }

    /**
     * Sets a file content and a status.
     * @param content New file content.
     * @param status File status.
     */
    public void setContent(FileContent content, FileTreeNodeStatus status) {
        this.content = content;
        this.status = status;
    }

    /**
     * Mark the file for delayed deletion.
     */
    public void markForDeletion() {
        if (this.status != FileTreeNodeStatus.DELETED) {
			this.status = FileTreeNodeStatus.TO_BE_DELETED;
        }
    }

    /**
     * Changes (replaces) the file content and changes the status to modified. Deleted files are resurrected (status is new),
     * awaiting deletion files are back changed to a modified status.
     * @param newContent New file content.
     */
    public void changeContent(FileContent newContent) {
        if ((this.status == FileTreeNodeStatus.LOADED_NOT_MODIFIED || this.status ==FileTreeNodeStatus.MODIFIED) &&
                Objects.equals(this.content, newContent)) {
            this.status = FileTreeNodeStatus.LOADED_NOT_MODIFIED;
            return; // ignore saving, no changes
        }

		this.content = newContent;
        switch (this.status) {
            case NOT_LOADED:  // consider as modified
            case LOADED_NOT_MODIFIED:
            case TO_BE_DELETED:  // cancel deletion
				this.status = FileTreeNodeStatus.MODIFIED;
                break;
            case NEW:
            case MODIFIED:
                // preserve the current status
                break;
            case DELETED: // resurrect
				this.status = FileTreeNodeStatus.NEW;
                break;
        }
    }

    /**
     * Flushes any changes (file added, modified, deleted) to the file system.
     */
    public void flush() {
        // the default implementation simply clears the status, derived classes should not call it and implement their own flush

        if (this.status == FileTreeNodeStatus.TO_BE_DELETED) {
			this.status = FileTreeNodeStatus.DELETED;
            return;
        }

        if (this.status != FileTreeNodeStatus.NOT_LOADED) {
			this.status = FileTreeNodeStatus.LOADED_NOT_MODIFIED;
        }
    }

    /**
     * Checks if the file three node is using a local file system (returns true) or only a virtual file system and all files are in memory.
     * Virtual file system does not have files stored on the disk and jinja2 templates cannot be evaluated by providing a path to the file.
     * @return True when the file node is backed by a real file system (local disk). False when it is just a virtual, in-memory file node without any disk storage.
     */
    public boolean isLocalFileSystem() {
        return false;
    }

    /**
     * Returns a physical path to the file. Returns a non-null object only when the file is using a real file system (local file system, disk based).
     * Returns null when the file uses just an in-memory virtual file system.
     * @return Physical, absolute file path to the file.
     */
    public Path getPhysicalAbsolutePath() {
        return null;
    }

    @Override
    public String toString() {
        return "FileTreeNode{" +
                "filePath=" + filePath +
                ", status=" + status +
                '}';
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public FileTreeNode clone() {
        try {
            return (FileTreeNode) super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }
}
