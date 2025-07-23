/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.synchronize;

import com.dqops.core.scheduler.schedules.UniqueSchedulesCollection;

/**
 * Schedule detection service that scans the metadata and compares the current list of schedules used by the scheduler
 * with the current schedules. Removes outdated schedules.
 */
public interface ScheduleChangeFinderService {
    /**
     * Loads all CRON schedules for running data quality checks that are configured in the metadata and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     *
     * @param currentRunningSchedules Current running schedules.
     * @param dataDomainName Data domain name.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    JobSchedulesDelta findRunChecksSchedulesToAddOrRemove(UniqueSchedulesCollection currentRunningSchedules, String dataDomainName);

    /**
     * Loads all CRON schedules for running profiling checks and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     *
     * @param currentRunningSchedules Current running schedules for profiling.
     * @param dataDomainName Data domain name.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    JobSchedulesDelta findProfilingSchedulesToAddOrRemove(UniqueSchedulesCollection currentRunningSchedules, String dataDomainName);

    /**
     * Loads all CRON schedules for importing tables (they are configured on connections) and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     *
     * @param currentRunningSchedules Current running schedules for profiling.
     * @param dataDomainName Data domain name.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    JobSchedulesDelta findImportTablesSchedulesToAddOrRemove(UniqueSchedulesCollection currentRunningSchedules, String dataDomainName);
}
