/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler;

import com.dqops.core.scheduler.quartz.JobKeys;
import com.dqops.core.scheduler.synchronize.JobSchedulesDelta;
import com.dqops.core.scheduler.schedules.UniqueSchedulesCollection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionReportingMode;
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
     * Returns the reporting mode for running the checks by the scheduler.
     * @return Reporting mode used when running the checks.
     */
    StatisticsCollectorExecutionReportingMode getCollectStatisticsReportingMode();

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
     * @param dataDomain Data domain name. Leave null to refresh all data domains.
     */
    void triggerMetadataSynchronization(String dataDomain);

    /**
     * Analyzes the list of active data domains for which the job scheduler is scheduling jobs.
     * Adds or removes jobs for all nested data domains.
     */
    void reconcileScheduledDomains();

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
     * @param dataDomainName Data domain name.
     * @return Returns a list of active schedules.
     */
    UniqueSchedulesCollection getActiveSchedules(JobKey jobKey, String dataDomainName);

    /**
     * Applies changes for a new list of schedules, new triggers are added, unused triggers are removed.
     *
     * @param schedulesDelta Schedule delta with schedules to add and schedules to remove.
     * @param jobKey         Target job to configure. Jobs are specified in the {@link JobKeys}
     * @param dataDomainName Data domain name.
     */
    void applyScheduleDeltaToJob(JobSchedulesDelta schedulesDelta, JobKey jobKey, String dataDomainName);
}
