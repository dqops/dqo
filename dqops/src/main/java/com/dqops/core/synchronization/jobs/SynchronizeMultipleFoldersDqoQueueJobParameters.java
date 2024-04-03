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

import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.dqops.core.synchronization.status.CloudSynchronizationFoldersStatusModel;
import com.dqops.core.synchronization.status.FolderSynchronizationStatus;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Simple object for starting multiple folder synchronization jobs with the same configuration.
 */
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class SynchronizeMultipleFoldersDqoQueueJobParameters implements Cloneable {
    /**
     * File synchronization direction.
     */
    @JsonPropertyDescription("File synchronization direction, the default is full synchronization (push local changes and pull other changes from DQOps Cloud).")
    private FileSynchronizationDirection direction = FileSynchronizationDirection.full;

    /**
     * Force full refresh of native tables in the data quality data warehouse. The default synchronization mode is to refresh only modified data.
     */
    @JsonPropertyDescription("Force full refresh of native tables in the data quality data warehouse. The default synchronization mode is to refresh only modified data.")
    private boolean forceRefreshNativeTables;

    /**
     * Scans the yaml files (with the configuration for connections and tables) and detects new cron schedules. Detected cron schedules are registered in the cron (Quartz) job scheduler.
     */
    @JsonPropertyDescription("Scans the yaml files (with the configuration for connections and tables) and detects new cron schedules. Detected cron schedules are registered in the cron (Quartz) job scheduler.")
    private boolean detectCronSchedules;

    /**
     * The synchronization status of the "sources" folder.
     */
    @JsonPropertyDescription("Synchronize the \"sources\" folder.")
    private boolean sources;

    /**
     * Synchronize the "sensors" folder.
     */
    @JsonPropertyDescription("Synchronize the \"sensors\" folder.")
    private boolean sensors;

    /**
     * Synchronize the "rules" folder.
     */
    @JsonPropertyDescription("Synchronize the \"rules\" folder.")
    private boolean rules;

    /**
     * Synchronize the "checks" folder.
     */
    @JsonPropertyDescription("Synchronize the \"checks\" folder.")
    private boolean checks;

    /**
     * Synchronize the "settings" folder.
     */
    @JsonPropertyDescription("Synchronize the \"settings\" folder.")
    private boolean settings;

    /**
     * Synchronize the ".credentials" folder.
     */
    @JsonPropertyDescription("Synchronize the \".credentials\" folder.")
    private boolean credentials;

    /**
     * Synchronize the "dictionaries" folder.
     */
    @JsonPropertyDescription("Synchronize the \"dictionaries\" folder.")
    private boolean dictionaries;

    /**
     * Synchronize the "patterns" folder.
     */
    @JsonPropertyDescription("Synchronize the \"patterns\" folder.")
    private boolean patterns;

    /**
     * Synchronize the ".data/sensor_readouts" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/sensor_readouts\" folder.")
    private boolean dataSensorReadouts;

    /**
     * Synchronize the ".data/check_results" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/check_results\" folder.")
    private boolean dataCheckResults;

    /**
     * Synchronize the ".data/statistics" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/statistics\" folder.")
    private boolean dataStatistics;

    /**
     * Synchronize the ".data/errors" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/errors\" folder.")
    private boolean dataErrors;

    /**
     * Synchronize the ".data/incidents" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/incidents\" folder.")
    private boolean dataIncidents;

    /**
     * Synchronize all folders that have local changes. When this field is set to true, there is no need to enable synchronization of single folders because DQOps
     * will decide which folders need synchronization (to be pushed to the cloud).
     */
    @JsonPropertyDescription("Synchronize all folders that have local changes. When this field is set to true, there is no need to enable synchronization of single folders " +
            "because DQOps will decide which folders need synchronization (to be pushed to the cloud).")
    private boolean synchronizeFolderWithLocalChanges;


    /**
     * Turns on synchronization of folders that have local changes, based on the changes detected by the synchronization status tracker.
     * @param localFoldersStatusModel Current local folder synchronization status, which identifies which folders have local changes.
     */
    public void synchronizeFoldersWithLocalChanges(CloudSynchronizationFoldersStatusModel localFoldersStatusModel) {
        if (localFoldersStatusModel.getSources() == FolderSynchronizationStatus.changed) {
            this.sources = true;
        }

        if (localFoldersStatusModel.getSensors() == FolderSynchronizationStatus.changed) {
            this.sensors = true;
        }

        if (localFoldersStatusModel.getRules() == FolderSynchronizationStatus.changed) {
            this.rules = true;
        }

        if (localFoldersStatusModel.getChecks() == FolderSynchronizationStatus.changed) {
            this.checks = true;
        }

        if (localFoldersStatusModel.getSettings() == FolderSynchronizationStatus.changed) {
            this.settings = true;
        }

        if (localFoldersStatusModel.getCredentials() == FolderSynchronizationStatus.changed) {
            this.credentials = true;
        }

        if (localFoldersStatusModel.getDictionaries() == FolderSynchronizationStatus.changed) {
            this.dictionaries = true;
        }

        if (localFoldersStatusModel.getPatterns() == FolderSynchronizationStatus.changed) {
            this.patterns = true;
        }

        if (localFoldersStatusModel.getDataSensorReadouts() == FolderSynchronizationStatus.changed) {
            this.dataSensorReadouts = true;
        }

        if (localFoldersStatusModel.getDataCheckResults() == FolderSynchronizationStatus.changed) {
            this.dataCheckResults = true;
        }

        if (localFoldersStatusModel.getDataErrors() == FolderSynchronizationStatus.changed) {
            this.dataErrors = true;
        }

        if (localFoldersStatusModel.getDataStatistics() == FolderSynchronizationStatus.changed) {
            this.dataStatistics = true;
        }

        if (localFoldersStatusModel.getDataIncidents() == FolderSynchronizationStatus.changed) {
            this.dataIncidents = true;
        }
    }

    /**
     * Turns on the flags for synchronizing every folder.
     */
    public void synchronizeAllFolders() {
        this.sources = true;
        this.sensors = true;
        this.rules = true;
        this.checks = true;
        this.settings = true;
        this.credentials = true;
        this.dictionaries = true;
        this.patterns = true;
        this.dataSensorReadouts = true;
        this.dataCheckResults = true;
        this.dataStatistics = true;
        this.dataErrors = true;
        this.dataIncidents = true;
    }

    /**
     * Creates a clone of the parameter object.
     * @return Cloned object.
     */
    public SynchronizeMultipleFoldersDqoQueueJobParameters clone() {
        try {
            return (SynchronizeMultipleFoldersDqoQueueJobParameters) super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }

    public static class SynchronizeMultipleFoldersDqoQueueJobParametersSampleFactory implements SampleValueFactory<SynchronizeMultipleFoldersDqoQueueJobParameters> {
        @Override
        public SynchronizeMultipleFoldersDqoQueueJobParameters createSample() {
            SynchronizeMultipleFoldersDqoQueueJobParameters synchronizeMultipleFoldersDqoQueueJobParameters = new SynchronizeMultipleFoldersDqoQueueJobParameters();
            synchronizeMultipleFoldersDqoQueueJobParameters.synchronizeAllFolders();
            return synchronizeMultipleFoldersDqoQueueJobParameters;
        }
    }
}
