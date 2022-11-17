/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.scheduler.quartz;

import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
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
    public void setSchedule(JobDataMap dataMap, RunChecksSchedule recurringSchedule) {
        String jsonString = this.jsonSerializer.serialize(recurringSchedule);
        dataMap.put(KEY_SCHEDULE, jsonString);
    }

    /**
     * Retrieves a serialized schedule specification from the job data map.
     * @param jobDataMap Job data map to read.
     * @return Deserialized recurring schedule specification.
     */
    public RunChecksSchedule getSchedule(JobDataMap jobDataMap) {
        if (jobDataMap == null || !jobDataMap.containsKey(KEY_SCHEDULE)) {
            return null;
        }

        String scheduleJson = jobDataMap.getString(KEY_SCHEDULE);
        RunChecksSchedule recurringSchedule = this.jsonSerializer.deserialize(scheduleJson, RunChecksSchedule.class);
        return recurringSchedule;
    }
}
