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

import com.dqops.core.scheduler.JobSchedulerException;
import com.dqops.metadata.scheduling.CronScheduleSpec;
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
     * @param dataDomainName Data domain name.
     * @return Trigger.
     */
    @Override
    public Trigger createTrigger(CronScheduleSpec schedule, JobKey jobKey, String dataDomainName) {
        JobDataMap triggerJobData = new JobDataMap();
        jobDataMapAdapter.setSchedule(triggerJobData, schedule);
        jobDataMapAdapter.setDataDomain(triggerJobData, dataDomainName);

        String key = "job=" + jobKey.getName() + ",cron=" + schedule.getCronExpression() + ",domain=" + dataDomainName;
        TriggerBuilder<Trigger> triggerBuilder = newTrigger()
                .withIdentity(key)
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
