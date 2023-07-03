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
package com.dqops.core.synchronization.jobs;

import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Parameter object for starting a file synchronization job. Identifies the folder and direction that should be synchronized.
 */
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class SynchronizeRootFolderParameters implements Cloneable {
    private DqoRoot folder;
    private FileSynchronizationDirection direction = FileSynchronizationDirection.full;
    private boolean forceRefreshNativeTable;

    /**
     * Default constructor.
     */
    public SynchronizeRootFolderParameters() {
    }

    /**
     * Creates a synchronization parameter given the synchronization direction and a folder name to be synchronized.
     * @param folder Folder to be synchronized.
     * @param direction Synchronization direction.
     * @param forceRefreshNativeTable Force refreshing the native table.
     */
    public SynchronizeRootFolderParameters(DqoRoot folder, FileSynchronizationDirection direction, boolean forceRefreshNativeTable) {
        this.folder = folder;
        this.direction = direction;
        this.forceRefreshNativeTable = forceRefreshNativeTable;
    }

    /**
     * Returns the folder that should be synchronized.
     * @return Folder to be synchronized.
     */
    public DqoRoot getFolder() {
        return folder;
    }

    /**
     * Sets the folder to be synchronized.
     * @param folder Folder to be synchronized.
     */
    public void setFolder(DqoRoot folder) {
        this.folder = folder;
    }

    /**
     * Returns the data synchronization direction. The default is full synchronization, but it could be also download only or upload only.
     * @return File synchronization direction.
     */
    public FileSynchronizationDirection getDirection() {
        return direction;
    }

    /**
     * Sets the file synchronization direction.
     * @param direction File synchronization direction (full, download, upload).
     */
    public void setDirection(FileSynchronizationDirection direction) {
        this.direction = direction;
    }

    /**
     * Returns true if the native table for a data folder must be refreshed, even if there are no changes.
     * @return True when the native table must be refreshed.
     */
    public boolean isForceRefreshNativeTable() {
        return forceRefreshNativeTable;
    }

    /**
     * Sets the flag to refresh a native table for all data.
     * @param forceRefreshNativeTable Refresh a native table for all data.
     */
    public void setForceRefreshNativeTable(boolean forceRefreshNativeTable) {
        this.forceRefreshNativeTable = forceRefreshNativeTable;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public SynchronizeRootFolderParameters clone() throws CloneNotSupportedException {
        try {
            return (SynchronizeRootFolderParameters)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
