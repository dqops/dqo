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
