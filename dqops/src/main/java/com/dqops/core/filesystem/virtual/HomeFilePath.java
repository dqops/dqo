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
import org.apache.commons.lang3.StringUtils;
import org.apache.parquet.Strings;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Virtual file path to a file.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HomeFilePath implements Cloneable {
    private HomeFolderPath folder;
    private String fileName;

    public HomeFilePath() {
    }

    /**
     * Creates a file path.
     * @param folder Folder path to the folder that contains the file.
     * @param fileName Physical file name.
     */
    public HomeFilePath(HomeFolderPath folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    /**
     * Creates a home file path given a path in the form: folder/subfolder/subsubfolder/filename.abc
     * All names in the filePath are considered as file system names that are encoded for file system safety.
     * @param dataDomain Data domain name.
     * @param filePath File path to a file.
     * @return Home file path.
     */
    public static HomeFilePath fromFilePath(String dataDomain, String filePath) {
        String[] filePathComponents = StringUtils.split(filePath, '/');
        FolderName[] folderNames = new FolderName[filePathComponents.length - 1];
        for (int i = 0; i < filePathComponents.length - 1; i++) {
            folderNames[i] = FolderName.fromFileSystemName(filePathComponents[i]);
        }
        HomeFolderPath folderPath = new HomeFolderPath(dataDomain, folderNames);
        return new HomeFilePath(folderPath, filePathComponents[filePathComponents.length - 1]);
    }

    /**
     * Returns the path to the containing folder.
     * @return Folder path.
     */
    public HomeFolderPath getFolder() {
        return folder;
    }

    /**
     * Sets the folder path.
     * @param folder Folder path.
     */
    public void setFolder(HomeFolderPath folder) {
        this.folder = folder;
    }

    /**
     * Returns a physical file name.
     * @return Physical file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the physical file name.
     * @param fileName Physical file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Converts the virtual file path to a java I/O path.
     * @return Java I/O path that is relative to the home folder.
     */
    public Path toRelativePath() {
        if (!this.folder.isEmpty() || !Strings.isNullOrEmpty(this.folder.getDataDomain())) {
            Path folderPath = this.folder.toRelativePath();
            return folderPath.resolve(this.fileName);
        }

        return Path.of(this.fileName);
    }

    /**
     * Returns a string representation of the path.
     * @return Path string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.folder.toString());
        sb.append(this.fileName);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeFilePath that = (HomeFilePath) o;
        return folder.equals(that.folder) && fileName.equals(that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folder, fileName);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public HomeFilePath clone() {
        try {
            HomeFilePath cloned = (HomeFilePath) super.clone();
            if (cloned.folder != null) {
                cloned.folder = cloned.folder.clone();
            }

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }
}
