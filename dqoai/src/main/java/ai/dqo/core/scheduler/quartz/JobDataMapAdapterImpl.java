package ai.dqo.core.scheduler.quartz;

import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.utils.serialization.JsonSerializer;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Job data map adapter that can read and write common properties that are stored as a job parameter for a Quartz job.
 * Complex objects cannot be stored directly in the job data map, so this class will serialize and deserialize them from JSON.
 */
@Component
public class JobDataMapAdapterImpl implements JobDataMapAdapter {
    /**
     * Key used in the {@see JsonDataMap} to store a serialized {@see RecurringScheduleSpec}
     */
    public static final String KEY_SCHEDULE = "schedule";

    private JsonSerializer jsonSerializer;

    /**
     * Creates a job data map adapter using a DQO JSON serializer.
     * @param jsonSerializer DQO JSON serializer.
     */
    @Autowired
    public JobDataMapAdapterImpl(JsonSerializer jsonSerializer) {
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Stores the schedule in the job data map as a serialized JSON.
     * @param dataMap Target data map to store the schedule.
     * @param recurringSchedule Schedule specification to store.
     */
    public void setSchedule(JobDataMap dataMap, RecurringScheduleSpec recurringSchedule) {
        String jsonString = this.jsonSerializer.serialize(recurringSchedule);
        dataMap.put(KEY_SCHEDULE, jsonString);
    }

    /**
     * Retrieves a serialized schedule specification from the job data map.
     * @param jobDataMap Job data map to read.
     * @return Deserialized recurring schedule specification.
     */
    public RecurringScheduleSpec getSchedule(JobDataMap jobDataMap) {
        if (!jobDataMap.containsKey(KEY_SCHEDULE)) {
            return null;
        }

        String scheduleJson = jobDataMap.getString(KEY_SCHEDULE);
        RecurringScheduleSpec recurringSchedule = this.jsonSerializer.deserialize(scheduleJson, RecurringScheduleSpec.class);
        return recurringSchedule;
    }
}
