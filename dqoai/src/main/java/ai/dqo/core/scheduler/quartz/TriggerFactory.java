package ai.dqo.core.scheduler.quartz;

import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
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
    Trigger createTrigger(RunChecksSchedule scheduleSpec, JobKey jobKey);
}
