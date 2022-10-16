package ai.dqo.core.scheduler.quartz;

import ai.dqo.core.scheduler.JobSchedulerException;
import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import com.cronutils.mapper.CronMapper;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.apache.parquet.Strings;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cronutils.model.CronType.UNIX;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Quartz trigger factory that creates triggers from the schedule configuration.
 */
@Component
public class TriggerFactoryImpl implements TriggerFactory {
    private JobDataMapAdapter jobDataMapAdapter;
    private CronMapper cronMapperUnixToQuartz = CronMapper.fromUnixToQuartz();
    private CronDefinition unixCronDefinition = CronDefinitionBuilder.instanceDefinitionFor(UNIX);
    private CronParser unixCronParser = new CronParser(unixCronDefinition);


    @Autowired
    public TriggerFactoryImpl(JobDataMapAdapter jobDataMapAdapter) {
        this.jobDataMapAdapter = jobDataMapAdapter;
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
    public Trigger createTrigger(RunChecksSchedule schedule, JobKey jobKey) {
        JobDataMap triggerJobData = new JobDataMap();
        jobDataMapAdapter.setSchedule(triggerJobData, schedule);

        TriggerBuilder<Trigger> triggerBuilder = newTrigger()
                .withIdentity(schedule.toString())
                .usingJobData(triggerJobData);

        ScheduleBuilder<?> scheduleBuilder = null;

        RecurringScheduleSpec recurringSchedule = schedule.getRecurringSchedule();
        String unixCronExpression = recurringSchedule.getCronExpression();
        String quartzCronExpression = null;

        try {
            quartzCronExpression = convertUnixCronExpressionToQuartzCron(unixCronExpression);
        }
        catch (Exception ex) {
            throw new JobSchedulerException("Invalid cron schedule: " + unixCronExpression);
        }

        if (CronExpression.isValidExpression(quartzCronExpression)) {
            scheduleBuilder = cronSchedule(quartzCronExpression)
                    .inTimeZone(schedule.getJavaTimeZone());
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
