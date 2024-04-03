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
package com.dqops.core.scheduler;

import com.dqops.core.scheduler.quartz.JobKeys;
import com.dqops.core.scheduler.synchronize.JobSchedulesDelta;
import com.dqops.core.scheduler.schedules.UniqueSchedulesCollection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import org.quartz.JobKey;
import org.quartz.Scheduler;

/**
 * Job scheduling root class that manages an instance of a Quartz scheduler.
 */
public interface JobSchedulerService {
    /**
     * Returns the file synchronization mode that was configured for the scheduler.
     * @return File synchronization reporting mode.
     */
    FileSystemSynchronizationReportingMode getSynchronizationMode();

    /**
     * Returns the reporting mode for running the checks by the scheduler.
     * @return Reporting mode during the check execution.
     */
    CheckRunReportingMode getCheckRunReportingMode();

    /**
     * Checks if the job scheduler is started.
     * @return True - the job scheduler is running, false - it is not started.
     */
    boolean isStarted();

    /**
     * Initializes and starts the scheduler.
     * @param synchronizationMode File synchronization reporting mode.
     * @param checkRunReportingMode Check execution reporting mode.
     */
    void start(FileSystemSynchronizationReportingMode synchronizationMode, CheckRunReportingMode checkRunReportingMode);

    /**
     * Stops the job scheduler, but with an option to start it again.
     */
    void stop();


    /**
     * Stops the scheduler.
     */
    void shutdown();

    /**
     * Triggers the metadata synchronization job on the job scheduler. Calls the cloud sync and reload the metadata to detect new schedules.
     */
    void triggerMetadataSynchronization();

    /**
     * Returns the default job scheduler. The scheduler must be started first.
     *
     * @return Quartz scheduler instance.
     */
    Scheduler getScheduler();

    /**
     * Retrieves all schedules configured on triggers for a given job.
     *
     * @param jobKey Job key for one of the known jobs like the run checks or scan metadata.
     * @return Returns a list of active schedules.
     */
    UniqueSchedulesCollection getActiveSchedules(JobKey jobKey);

    /**
     * Applies changes for a new list of schedules, new triggers are added, unused triggers are removed.
     *
     * @param schedulesDelta Schedule delta with schedules to add and schedules to remove.
     * @param jobKey         Target job to configure. Jobs are specified in the {@link JobKeys}
     */
    void applyScheduleDeltaToJob(JobSchedulesDelta schedulesDelta, JobKey jobKey);
}
