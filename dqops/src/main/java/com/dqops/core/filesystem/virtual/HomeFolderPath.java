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

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.DqoUserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Local folder path that is a list of subfolder name from the root folder to an actual folder in the home folder.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HomeFolderPath extends ArrayList<FolderName> {
    private final String dataDomain;

    /**
     * Creates an empty path to the root home folder.
     * @param dataDomain The name of the data domain.
     */
    @Autowired
    public HomeFolderPath(String dataDomain) {
        this.dataDomain = dataDomain;
    }

    /**
     * Creates a folder path as a list of sub-folder names that start at the home root folder.
     * @param dataDomain The name of the data domain.
     * @param c Collection of folder names.
     */
    public HomeFolderPath(String dataDomain, Collection<? extends FolderName> c) {
        super(c);
        this.dataDomain = dataDomain;
    }

    /**
     * Creates a folder path as an array of sub-folder names that start at the home root folder.
     * @param dataDomain The name of the data domain.
     * @param folders Array of folder names.
     */
    public HomeFolderPath(String dataDomain, FolderName... folders) {
        this.dataDomain = dataDomain;
		for (FolderName folderName : folders) {
			this.add(folderName);
        }
    }

    /**
     * Returns the name of the data domain, which is the domain whose DQOps user home is referenced.
     * @return Data domain name.
     */
    public String getDataDomain() {
        return dataDomain;
    }

    /**
     * Return the top most folder name.
     * @return Top most folder name.
     */
    public FolderName getTopFolder() {
        if (this.size() > 0) {
            return this.get(this.size() - 1);
        }

        return null;
    }

    /**
     * Returns a folder path to the parent folder.
     * @return Parent folder path or null if this is the root folder.
     */
    public HomeFolderPath getParentFolder() {
        if (this.size() == 0) {
            return null;
        }

        return new HomeFolderPath(this.dataDomain, this.subList(0, this.size() - 1));
    }

    /**
     * Performs a deep clone of the object.
     * @return Deep cloned object.
     */
    @Override
    public HomeFolderPath clone() {
        HomeFolderPath cloned = new HomeFolderPath(this.dataDomain);
        for(FolderName folder : this) {
            FolderName clonedFolder = folder.clone();
            cloned.add(clonedFolder);
        }
        return cloned;
    }

    /**
     * Converts the object to a relative path inside the home folder. Includes also the path to a data domain subfolder.
     * @return Relative path.
     */
    public Path toRelativePath() {
        if (this.size() == 0) {
            if (!Objects.equals(DqoUserIdentity.DEFAULT_DATA_DOMAIN, this.dataDomain)) {
                return Path.of("./");
            } else {
                return Path.of(BuiltInFolderNames.DATA_DOMAINS, FileNameSanitizer.encodeForFileSystem(this.dataDomain));
            }
        }

        String[] pathElements = new String[this.size() - 1];
        for (int i = 1; i < this.size(); i++) {
            pathElements[i -1] = this.get(i).getFileSystemName();
        }

        Path relativePath = Path.of(this.get(0).getFileSystemName(), pathElements);
        if (!Objects.equals(DqoUserIdentity.DEFAULT_DATA_DOMAIN, this.dataDomain)) {
            relativePath = Path.of(BuiltInFolderNames.DATA_DOMAINS, FileNameSanitizer.encodeForFileSystem(this.dataDomain)).resolve(relativePath);
        }

        return relativePath;
    }

    /**
     * Extract the physical folder names on the whole folder path as an array of strings.
     * @return Folder names as string elements.
     */
    public String[] toPhysicalPathElements() {
        String[] pathElements = new String[this.size()];
        for (int i = 0; i < this.size(); i++) {
            pathElements[i] = this.get(i).getFileSystemName();
        }

        return pathElements;
    }

    /**
     * Creates a new folder path to a subfolder.
     * @param subFolder New folder path instance that points to a subfolder.
     * @return Folder path to a subfolder.
     */
    public HomeFolderPath resolveSubfolder(FolderName subFolder) {
        HomeFolderPath cloned = this.clone();
        cloned.add(subFolder);
        return cloned;
    }

    /**
     * Creates a new file path to a physical file in the current folder.
     * @param fileSystemName File system name of a file in the current folder.
     * @return File path to a file in the folder.
     */
    public HomeFilePath resolveFile(String fileSystemName) {
        return new HomeFilePath(this.clone(), fileSystemName);
    }

    /**
     * Creates a folder path that is a copy of the current folder path, but the first pathElementIndex path elements
     * are missing. The returned folder is effectively relative to the pathElementIndex-nth parent folder of the current folder.
     * @param pathElementIndex Number of starting folder names to remove in the returned folder.
     * @return Relative folder to the pathElementIndex-nth parent folder.
     */
    public HomeFolderPath extractSubFolderAt(int pathElementIndex) {
        return new HomeFolderPath(this.dataDomain, this.subList(pathElementIndex, this.size()));
    }

    /**
     * Returns a string representation of the object.
     * @return Folder path as a string.
     */
    @Override
    public String toString() {
        if (this.size() == 0) {
            return ".";
        }

        String[] pathElements = toPhysicalPathElements();
        return String.join("/", pathElements);
    }

    /**
     * Converts the folder path to an object name. The object name for a folder is just a folder path like "folder1/subfolder1/subsufolder1",
     * however the folder names are considered as encoded for file system safety and will be decoded (unsanitized) before the name is concatenated by '/'
     * @return Unsanitized path string.
     */
    public String getFullObjectName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            if (i > 0) {
                sb.append('/');
            }
            sb.append(this.get(i).getObjectName());
        }

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @param o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeFolderPath that = (HomeFolderPath) o;
        if (!Objects.equals(this.dataDomain, that.dataDomain)) return false;
        if (this.size() != that.size()) return false;
        for(int i = 0; i < size(); i++) {
            if (!Objects.equals(this.get(i), that.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = this.dataDomain.hashCode();
        for (FolderName folderName : this) {
            hash = (hash * 29) ^ folderName.hashCode();
        }
        return hash;
    }
}
