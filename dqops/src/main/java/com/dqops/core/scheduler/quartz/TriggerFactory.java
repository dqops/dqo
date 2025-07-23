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
import org.quartz.JobKey;
import org.quartz.Trigger;

/**
 * Quartz trigger factory that creates triggers from the schedule configuration.
 */
public interface TriggerFactory {
    /**
     * Creates a Quartz trigger for a given schedule.
     * @param scheduleSpec Schedule specification.
     * @param jobKey Job key to identify a predefined job.
     * @param dataDomainName Data domain name.
     * @return Trigger.
     */
    Trigger createTrigger(CronScheduleSpec scheduleSpec, JobKey jobKey, String dataDomainName);
}
