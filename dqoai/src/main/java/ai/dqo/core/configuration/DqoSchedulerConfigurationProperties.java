/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.configuration;

import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for DQO that configure the job scheduler. Properties are mapped to the "dqo.scheduler." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.scheduler")
@EqualsAndHashCode(callSuper = false)
public class DqoSchedulerConfigurationProperties implements Cloneable {
    private Boolean start;
    private String scanMetadataCronSchedule;
    private boolean enableCloudSync = true;
    private FileSystemSynchronizationReportingMode synchronizationMode = FileSystemSynchronizationReportingMode.silent;
    private CheckRunReportingMode checkRunMode = CheckRunReportingMode.silent;

    /**
     * Returns the flag if the scheduler should be started on startup (true), disabled permanently (false) or null when it should not be started, unless the application is started with a "run" command.
     * @return True - start the scheduler instantly, false - never start automatically, null - start only when the "run" command was called.
     */
    public Boolean getStart() {
        return start;
    }

    /**
     * Sets the flag if the scheduler should be started on the application startup or disabled.
     * @param start True - start the scheduler instantly, false - never start automatically, null - start only when the "run" command was called.
     */
    public void setStart(Boolean start) {
        this.start = start;
    }

    /**
     * Returns the default cron expression used to scan the metadata for new schedules to run data quality checks.
     * @return Cron expression used to scan the metadata.
     */
    public String getScanMetadataCronSchedule() {
        return scanMetadataCronSchedule;
    }

    /**
     * Sets the scan metadata cron schedule.
     * @param scanMetadataCronSchedule Cron schedule used to scan the metadata.
     */
    public void setScanMetadataCronSchedule(String scanMetadataCronSchedule) {
        this.scanMetadataCronSchedule = scanMetadataCronSchedule;
    }

    /**
     * Returns true if "cloud sync" is enabled during the scheduler jobs.
     * @return True when the synchronization is enabled.
     */
    public boolean isEnableCloudSync() {
        return enableCloudSync;
    }

    /**
     * Sets the flag to enable cloud synchronization.
     * @param enableCloudSync Enable cloud synchronization.
     */
    public void setEnableCloudSync(boolean enableCloudSync) {
        this.enableCloudSync = enableCloudSync;
    }

    /**
     * Returns the default console logging mode for reporting the progress of cloud sync to the console.
     * @return Reporting mode for a repeating cloud sync command.
     */
    public FileSystemSynchronizationReportingMode getSynchronizationMode() {
        return synchronizationMode;
    }

    /**
     * Sets the console logging mode for cloud sync operations performed by the scheduler.
     * @param synchronizationMode Console logging mode for cloud sync operations.
     */
    public void setSynchronizationMode(FileSystemSynchronizationReportingMode synchronizationMode) {
        this.synchronizationMode = synchronizationMode;
    }

    /**
     * Returns the console logging mode used when the scheduler runs checks as jobs.
     * @return Check run logging mode.
     */
    public CheckRunReportingMode getCheckRunMode() {
        return checkRunMode;
    }

    /**
     * Sets the console logging mode for "run checks" jobs executed by the scheduler.
     * @param checkRunMode Check run console logging mode.
     */
    public void setCheckRunMode(CheckRunReportingMode checkRunMode) {
        this.checkRunMode = checkRunMode;
    }

    @Override
    public DqoSchedulerConfigurationProperties clone() {
        try {
            return (DqoSchedulerConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
