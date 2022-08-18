package ai.dqo.core.scheduler.quartz;

import ai.dqo.core.scheduler.JobSchedulerException;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import org.apache.commons.lang3.StringUtils;
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
     * @param scheduleSpec Schedule specification.
     * @param jobKey Job key to identify a predefined job.
     * @return Trigger.
     */
    @Override
    public Trigger createTrigger(RecurringScheduleSpec scheduleSpec, JobKey jobKey) {
        JobDataMap triggerJobData = new JobDataMap();
        jobDataMapAdapter.setSchedule(triggerJobData, scheduleSpec);

        TriggerBuilder<Trigger> triggerBuilder = newTrigger()
                .withIdentity(scheduleSpec.toString())
                .usingJobData(triggerJobData);

        ScheduleBuilder<?> scheduleBuilder = null;

        if (CronExpression.isValidExpression(scheduleSpec.getCronExpression())) {
            scheduleBuilder = cronSchedule(scheduleSpec.getCronExpression());
        }
        else {
            throw new JobSchedulerException("Invalid cron schedule: " + scheduleSpec.getCronExpression());
        }

        Trigger trigger = triggerBuilder
                .withSchedule(scheduleBuilder)
                .startNow()
                .forJob(jobKey)
                .build();

        return trigger;
    }
}
