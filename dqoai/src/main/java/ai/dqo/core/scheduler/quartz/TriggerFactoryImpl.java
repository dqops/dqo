package ai.dqo.core.scheduler.quartz;

import ai.dqo.core.scheduler.JobSchedulerException;
import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.utils.datetime.TimeZoneUtility;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Quartz trigger factory that creates triggers from the schedule configuration.
 */
@Component
public class TriggerFactoryImpl implements TriggerFactory {
    private JobDataMapAdapter jobDataMapAdapter;

    @Autowired
    public TriggerFactoryImpl(JobDataMapAdapter jobDataMapAdapter) {
        this.jobDataMapAdapter = jobDataMapAdapter;
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
        if (CronExpression.isValidExpression(recurringSchedule.getCronExpression())) {
            scheduleBuilder = cronSchedule(recurringSchedule.getCronExpression())
                    .inTimeZone(schedule.getJavaTimeZone());
        }
        else {
            throw new JobSchedulerException("Invalid cron schedule: " + recurringSchedule.getCronExpression());
        }

        Trigger trigger = triggerBuilder
                .withSchedule(scheduleBuilder)
                .startNow()
                .forJob(jobKey)
                .build();

        return trigger;
    }
}
