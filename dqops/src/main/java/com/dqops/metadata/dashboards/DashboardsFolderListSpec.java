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
package com.dqops.metadata.dashboards;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecList;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

/**
 * List of dashboard folders.
 */
public class DashboardsFolderListSpec extends AbstractDirtyTrackingSpecList<DashboardsFolderSpec> implements Cloneable, InvalidYamlStatusHolder {
    @JsonIgnore
    private Instant fileLastModified;

    @JsonIgnore
    private String yamlParsingError;

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }


    /**
     * Returns the last modification date of the dashboard list file.
     * @return The lat modification date of the dashboard list file.
     */
    public Instant getFileLastModified() {
        return fileLastModified;
    }

    /**
     * Sets the last modification date of the dashboard list file.
     * @param fileLastModified Last modification date of the dashboards file.
     */
    public void setFileLastModified(Instant fileLastModified) {
        this.fileLastModified = fileLastModified;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DashboardsFolderListSpec deepClone() {
        DashboardsFolderListSpec cloned = new DashboardsFolderListSpec();
        cloned.setFileLastModified(this.fileLastModified);
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (DashboardsFolderSpec folderSpec : this) {
            cloned.add(folderSpec.deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }

    /**
     * Finds a folder by name.
     * @param folderName Folder specification when the folder was found or null when the folder is missing.
     * @return Folder specification or null.
     */
    public DashboardsFolderSpec getFolderByName(String folderName) {
        for (DashboardsFolderSpec folder : this) {
            if (Objects.equals(folderName, folder.getFolderName())) {
                return folder;
            }
        }

        return null;
    }

    /**
     * Collects all similar dashboards from all subfolders.
     * @param allSimilarDashboardsContainer Target container where the dashboards should be added.
     * @param rootNode Root node, used to find and resolve folder names. When the value is null, uses self as the top-most container.
     */
    public void collectSimilarDashboards(AllSimilarDashboardsContainer allSimilarDashboardsContainer, HierarchyNode rootNode) {
        HierarchyNode effectiveRootNode = rootNode != null ? rootNode : this;

        for (DashboardsFolderSpec dashboardsFolderSpec : this) {
            dashboardsFolderSpec.collectSimilarDashboards(allSimilarDashboardsContainer, effectiveRootNode);
        }
    }

    /**
     * Returns or creates a new child folder.
     * @param childFolderName Child folder name to find or create.
     * @return Child folder.
     */
    public DashboardsFolderSpec getOrCreateChildFolder(String childFolderName) {
        DashboardsFolderSpec childFolder = this.getFolderByName(childFolderName);
        if (childFolder == null) {
            childFolder = new DashboardsFolderSpec(childFolderName);
            childFolder.getFolders().setFileLastModified(this.fileLastModified);
            this.add(childFolder);
        }

        return childFolder;
    }

    /**
     * Finds all folders on the path, created and adds folders that are not present.
     * @param folderPath Collection of folder names on the path.
     * @return Final folder specification that was added, it is the deepest folder from the path.
     */
    public DashboardsFolderSpec getOrCreateFolderPath(Collection<String> folderPath) {
        DashboardsFolderListSpec currentFolderList = this;
        DashboardsFolderSpec resultFolder = null;

        for (String folderName : folderPath) {
            DashboardsFolderSpec childFolderByName = currentFolderList.getFolderByName(folderName);
            if (childFolderByName == null) {
                childFolderByName = new DashboardsFolderSpec(folderName);
                childFolderByName.getFolders().setFileLastModified(this.fileLastModified);
                currentFolderList.add(childFolderByName);
            }
            resultFolder = childFolderByName;
            currentFolderList = childFolderByName.getFolders();
        }

        return resultFolder;
    }

    /**
     * Creates a copy of this dashboard list, but expands all templated dashboards that have multiple possible parameter values.
     * @return A copy of the dashboard list, but with expanded dashboards.
     */
    public DashboardsFolderListSpec createExpandedDashboardTree() {
        DashboardsFolderListSpec expandedFolderList = new DashboardsFolderListSpec();
        expandedFolderList.setHierarchyId(this.getHierarchyId());
        expandedFolderList.setFileLastModified(this.fileLastModified);

        for (DashboardsFolderSpec folderSpec : this) {
            DashboardsFolderSpec expandedFolder = folderSpec.createExpandedDashboardFolder();
            expandedFolderList.add(expandedFolder);
        }

        return expandedFolderList;
    }

    /**
     * Sorts the list of folders per folder name. Sorts also nested dashboards and folders inside nested dashboards.
     */
    public void sort() {
        this.sort(Comparator.comparing(DashboardsFolderSpec::getFolderName));
        for (DashboardsFolderSpec folderSpec : this) {
            folderSpec.sort();
        }
    }

    /**
     * Creates a smart copy of the dashboard tree, merging the list of dashboards with <code>otherFolderList</code> that
     * contains user defined dashboards.
     * @param otherFolderList Other list of dashboards (user defined dashboards) to merge.
     * @return Creates the same object instance when the <code>otherFolderList</code> has no other dashboard folders or a new instance that contains a copy of dashboards.
     */
    public DashboardsFolderListSpec merge(DashboardsFolderListSpec otherFolderList) {
        if (otherFolderList == null || otherFolderList.size() == 0) {
            return this;
        }

        DashboardsFolderListSpec cloned = new DashboardsFolderListSpec();
        if (this.fileLastModified != null && otherFolderList.fileLastModified != null) {
            cloned.setFileLastModified(this.fileLastModified.isAfter(otherFolderList.fileLastModified) ? this.fileLastModified : otherFolderList.fileLastModified);
        } else {
            cloned.setFileLastModified(this.fileLastModified);
        }

        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId());
        }

        for (DashboardsFolderSpec folderSpec : this) {
            DashboardsFolderSpec otherFolder = otherFolderList.getFolderByName(folderSpec.getFolderName());
            if (otherFolder != null) {
                DashboardsFolderSpec mergedFolder = folderSpec.merge(otherFolder);
                cloned.add(mergedFolder);
            } else {
                cloned.add(folderSpec);
            }
        }

        for (DashboardsFolderSpec otherFolder : otherFolderList) {
            if (cloned.getFolderByName(otherFolder.getFolderName()) != null) {
                continue; // folder already merged
            }

            cloned.add(otherFolder);
        }

        return cloned;
    }

    /**
     * Retrieves a dashboard, traversing through the folders.
     * @param dashboardName Dashboard name to find.
     * @param folders Array of folders to traverse.
     * @return The dashboard specification or null when a folder is missing or the dashboard was not found.
     */
    public DashboardSpec getDashboard(String dashboardName, String... folders) {
        if (folders == null || folders.length == 0 || folders[0] == null) {
            return null;
        }

        DashboardsFolderSpec firstFolder = this.getFolderByName(folders[0]);
        if (firstFolder == null) {
            return null;
        }


        if (folders.length == 1) {
            return firstFolder.getDashboards().getDashboardByName(dashboardName);
        }

        String[] nestedFolder = Arrays.copyOfRange(folders, 1, folders.length);
        return firstFolder.getFolders().getDashboard(dashboardName, nestedFolder);
    }
}
