package ai.dqo.core.scheduler.quartz;

import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import org.quartz.JobDataMap;

/**
 * Job data map adapter that can read and write common properties that are stored as a job parameter for a Quartz job.
 * Complex objects cannot be stored directly in the job data map, so this class will serialize and deserialize them from JSON.
 */
public interface JobDataMapAdapter {
    /**
     * Stores the schedule in the job data map as a serialized JSON.
     * @param dataMap Target data map to store the schedule.
     * @param recurringSchedule Schedule specification to store.
     */
    void setSchedule(JobDataMap dataMap, RunChecksSchedule recurringSchedule);

    /**
     * Retrieves a serialized schedule specification from the job data map.
     * @param jobDataMap Job data map to read.
     * @return Deserialized recurring schedule specification.
     */
    RunChecksSchedule getSchedule(JobDataMap jobDataMap);
}
