/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.quartz;

import com.dqops.metadata.scheduling.CronScheduleSpec;

import java.time.ZonedDateTime;

/**
 * Service for utility methods regarding schedules.
 */
public interface SchedulesUtilityService {
    /**
     * Gets the time of the upcoming execution.
     * @param scheduleSpec Schedule spec for which to get the scheduled time.
     * @return Date and time of the next execution. Null if <code>scheduleSpec</code> is invalid.
     */
    ZonedDateTime getTimeOfNextExecution(CronScheduleSpec scheduleSpec);
}
