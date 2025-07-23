/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.scheduled;

import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.userhome.UserHome;

/**
 * Service that finds all checks that should be executed for a given schedule.
 * Checks are divided by target tables.
 */
public interface ScheduledTargetChecksFindService {
    /**
     * Traverses the user home and finds all checks that should be executed because their schedule
     * or a schedule of their parent nodes (connection, table, column) matches the requested schedule.
     *
     * @param userHome User home to find target checks to execute.
     * @param schedule Schedule to match.
     * @return List of target checks, grouped by a target table.
     */
    ScheduledChecksCollection findChecksForSchedule(UserHome userHome, CronScheduleSpec schedule);
}
