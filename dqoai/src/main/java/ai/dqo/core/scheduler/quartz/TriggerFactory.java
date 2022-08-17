package ai.dqo.core.scheduler.quartz;

import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
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
     * @return Trigger.
     */
    Trigger createTrigger(RecurringScheduleSpec scheduleSpec, JobKey jobKey);
}
