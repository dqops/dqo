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

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

/**
 * Folder name wrapper. Contains a pair of a file system (sanitized) and raw (unsanitized) folder name.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FolderName implements Cloneable {
    private String fileSystemName;
    private String objectName;

    public FolderName() {
    }

    /**
     * Creates a folder name.
     * @param fileSystemName Physical folder name (sanitized).
     * @param objectName Unsanitized folder name.
     */
    public FolderName(String fileSystemName, String objectName) {
        this.fileSystemName = fileSystemName;
        this.objectName = objectName;
    }

    /**
     * Returns the file system (sanitized) folder name.
     * @return File system folder name.
     */
    public String getFileSystemName() {
        return fileSystemName;
    }

    /**
     * Sets a file system folder name.
     * @param fileSystemName File system (sanitized) folder name.
     */
    public void setFileSystemName(String fileSystemName) {
        this.fileSystemName = fileSystemName;
    }

    /**
     * Returns a raw folder name.
     * @return Raw folder name that may contain characters that are not valid for a folder name.
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Changes an object name.
     * @param objectName New raw folder name.
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * Creates a new instance of a folder name given a raw object name (an unsafe, real object name like a source name).
     * The physical file name is derived from the object name by creating a file system safe (sanitized) folder name.
     * @param objectName Raw object name.
     * @return Local folder name with both the physical folder name and a raw name.
     */
    public static FolderName fromObjectName(String objectName) {
        return new FolderName(FileNameSanitizer.encodeForFileSystem(objectName), objectName);
    }

    /**
     * Creates a folder name given a sanitized folder name (file system name). The folder name may contain special characters
     * that are not safe for the file system and are encoded as %AB where AB are HEX values of the ASCII character code.
     * @param fileSystemName File system name of a folder.
     * @return Local folder name.
     */
    public static FolderName fromFileSystemName(String fileSystemName) {
        return new FolderName(fileSystemName, FileNameSanitizer.decodeFileSystemName(fileSystemName));
    }

    /**
     * Returns a deep clone of the object.
     * @return Deep cloned instance.
     */
    @Override
    public FolderName clone() {
        try {
            return (FolderName)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderName that = (FolderName) o;
        return Objects.equals(fileSystemName, that.fileSystemName) && Objects.equals(objectName, that.objectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileSystemName, objectName);
    }
}
