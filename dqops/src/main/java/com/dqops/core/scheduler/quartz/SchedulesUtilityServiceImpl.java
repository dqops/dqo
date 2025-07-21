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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.scheduler.JobSchedulerException;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.google.common.base.Strings;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Service for utility methods regarding CRON expressions.
 */
@Service
public class SchedulesUtilityServiceImpl implements SchedulesUtilityService {
    private TriggerFactory triggerFactory;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    @Autowired
    public SchedulesUtilityServiceImpl(TriggerFactory triggerFactory,
                                       DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.triggerFactory = triggerFactory;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Gets the time of the upcoming execution.
     * @param scheduleSpec Schedule spec for which to get the scheduled time.
     * @return Date and time of the next execution. Null if <code>scheduleSpec</code> is invalid.
     */
    @Override
    public ZonedDateTime getTimeOfNextExecution(CronScheduleSpec scheduleSpec) {
        if (Strings.isNullOrEmpty(scheduleSpec.getCronExpression())) {
            return null;
        }

        Trigger scheduleTrigger;
        try {
            scheduleTrigger = triggerFactory.createTrigger(scheduleSpec, JobKeys.DUMMY, UserDomainIdentity.ROOT_DATA_DOMAIN);
        } catch (JobSchedulerException e) {
            return null;
        }

        Date nextExecutionDate = scheduleTrigger.getFireTimeAfter(new Date());
        if (nextExecutionDate == null) {
            return null;
        }

        ZoneId timeZoneId = defaultTimeZoneProvider.getDefaultTimeZoneId();
        return ZonedDateTime.ofInstant(nextExecutionDate.toInstant(), timeZoneId);
    }
}
