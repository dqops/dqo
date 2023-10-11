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
import com.cronutils.mapper.CronMapper;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.apache.parquet.Strings;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

import static com.cronutils.model.CronType.UNIX;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Quartz trigger factory that creates triggers from the schedule configuration.
 */
@Component
public class TriggerFactoryImpl implements TriggerFactory {
    private JobDataMapAdapter jobDataMapAdapter;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;
    private CronMapper cronMapperUnixToQuartz = CronMapper.fromUnixToQuartz();
    private CronDefinition unixCronDefinition = CronDefinitionBuilder.instanceDefinitionFor(UNIX);
    private CronParser unixCronParser = new CronParser(unixCronDefinition);

    /**
     * Creates a Quartz trigger factory that converts DQOps schedules to Quartz cron triggers.
     * @param jobDataMapAdapter DQOps Job data adapter that stores and retrieves additional objects in jobs.
     * @param defaultTimeZoneProvider Default time zone provider.
     */
    @Autowired
    public TriggerFactoryImpl(JobDataMapAdapter jobDataMapAdapter,
                              DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.jobDataMapAdapter = jobDataMapAdapter;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Converts a UNIX style cron expression to Quartz compatible cron expression.
     * @param unixCronExpression Unix style cron expression.
     * @return Quartz compatible cron expression.
     */
    public String convertUnixCronExpressionToQuartzCron(String unixCronExpression) {
        if (Strings.isNullOrEmpty(unixCronExpression)) {
            return null;
        }

        Cron unixCronExpParsed = this.unixCronParser.parse(unixCronExpression);
        Cron quartzCronExpression = this.cronMapperUnixToQuartz.map(unixCronExpParsed);

        String quartzExpressionString = quartzCronExpression.asString();
        return quartzExpressionString;
    }

    /**
     * Creates a Quartz trigger for a given schedule.
     * @param schedule Schedule specification.
     * @param jobKey Job key to identify a predefined job.
     * @return Trigger.
     */
    @Override
    public Trigger createTrigger(MonitoringScheduleSpec schedule, JobKey jobKey) {
        JobDataMap triggerJobData = new JobDataMap();
        jobDataMapAdapter.setSchedule(triggerJobData, schedule);

        TriggerBuilder<Trigger> triggerBuilder = newTrigger()
                .withIdentity(schedule.toString())
                .usingJobData(triggerJobData);

        ScheduleBuilder<?> scheduleBuilder = null;

        String unixCronExpression = schedule.getCronExpression();
        String quartzCronExpression = null;

        try {
            quartzCronExpression = convertUnixCronExpressionToQuartzCron(unixCronExpression);
        }
        catch (Exception ex) {
            throw new JobSchedulerException("Invalid cron schedule: " + unixCronExpression);
        }

        if (CronExpression.isValidExpression(quartzCronExpression)) {
            TimeZone timeZone = TimeZone.getTimeZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId());
            scheduleBuilder = cronSchedule(quartzCronExpression)
                    .inTimeZone(timeZone);
        }
        else {
            throw new JobSchedulerException("Invalid cron schedule: " + unixCronExpression);
        }

        Trigger trigger = triggerBuilder
                .withSchedule(scheduleBuilder)
                .startNow()
                .forJob(jobKey)
                .build();

        return trigger;
    }
}
