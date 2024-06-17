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
package com.dqops.core.synchronization.status;

import com.dqops.core.synchronization.contract.DqoRoot;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Model that describes the current synchronization status for each folder.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class CloudSynchronizationFoldersStatusModel implements Cloneable {
    /**
     * The synchronization status of the "sources" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \"sources\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus sources = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the "sensors" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \"sensors\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus sensors = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the "rules" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \"rules\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus rules = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the "checks" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \"checks\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus checks = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the "settings" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \"settings\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus settings = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the ".credentials" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \".credentials\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus credentials = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the "dictionaries" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \"dictionaries\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus dictionaries = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the "patterns" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \"patterns\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus patterns = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the ".data/sensor_readouts" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \".data/sensor_readouts\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus dataSensorReadouts = FolderSynchronizationStatus.unchanged; // when using snake case, the enums in typescript/swagger will use _, making this enum the same as "data_sensor_readouts" in DqoRoot

    /**
     * The synchronization status of the ".data/check_results" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \".data/check_results\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus dataCheckResults = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the ".data/statistics" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \".data/statistics\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus dataStatistics = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the ".data/errors" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \".data/errors\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus dataErrors = FolderSynchronizationStatus.unchanged;

    /**
     * The synchronization status of the ".data/incidents" folder.
     */
    @JsonPropertyDescription("The synchronization status of the \".data/incidents\" folder.")
    @Setter(AccessLevel.NONE)
    private FolderSynchronizationStatus dataIncidents = FolderSynchronizationStatus.unchanged;

    /**
     * Returns the folder status for a given folder.
     * @param folderRoot Folder name.
     * @return Folder status.
     */
    public FolderSynchronizationStatus getFolderStatus(DqoRoot folderRoot) {
        switch (folderRoot) {
            case sensors:
                return sensors;
            case rules:
                return rules;
            case checks:
                return checks;
            case sources:
                return sources;
            case settings:
                return settings;
            case credentials:
                return credentials;
            case dictionaries:
                return dictionaries;
            case patterns:
                return patterns;
            case data_sensor_readouts:
                return dataSensorReadouts;
            case data_check_results:
                return dataCheckResults;
            case data_errors:
                return dataErrors;
            case data_statistics:
                return dataStatistics;
            case data_incidents:
                return dataIncidents;
            case data_error_samples:
            case _indexes:
            case _local_settings:
                return null;
            default:
                throw new RuntimeException("Unsupported enum: " + folderRoot);
        }
    }

    /**
     * Sets the new folder status  for a given folder.
     * @param folderRoot Folder name.
     * @param newStatus New folder status.
     */
    public void setFolderStatus(DqoRoot folderRoot, FolderSynchronizationStatus newStatus) {
        switch (folderRoot) {
            case sensors:
                this.sensors = newStatus;
                return;
            case rules:
                this.rules = newStatus;
                return;
            case checks:
                this.checks = newStatus;
                return;
            case sources:
                this.sources = newStatus;
                return;
            case settings:
                this.settings = newStatus;
                return;
            case credentials:
                this.credentials = newStatus;
                return;
            case dictionaries:
                this.dictionaries = newStatus;
                return;
            case patterns:
                this.patterns = newStatus;
                return;
            case data_sensor_readouts:
                this.dataSensorReadouts = newStatus;
                return;
            case data_check_results:
                this.dataCheckResults = newStatus;
                return;
            case data_errors:
                this.dataErrors = newStatus;
                return;
            case data_statistics:
                this.dataStatistics = newStatus;
                return;
            case data_incidents:
                this.dataIncidents = newStatus;
                return;
            case data_error_samples: // TODO: not synchronized yet
            case _indexes:
            case _local_settings:
                return;
            default:
                throw new RuntimeException("Unsupported enum: " + folderRoot);
        }
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public CloudSynchronizationFoldersStatusModel clone() {
        try {
            return (CloudSynchronizationFoldersStatusModel)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
