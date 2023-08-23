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
package com.dqops.core.scheduler.quartz;

import com.dqops.core.scheduler.JobSchedulerException;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
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
    public ZonedDateTime getTimeOfNextExecution(MonitoringScheduleSpec scheduleSpec) {
        if (Strings.isNullOrEmpty(scheduleSpec.getCronExpression())) {
            return null;
        }

        Trigger scheduleTrigger;
        try {
            scheduleTrigger = triggerFactory.createTrigger(scheduleSpec, JobKeys.DUMMY);
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
